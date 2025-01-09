package dev.alvr.katana.core.preferences.serializers

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.okio.OkioSerializer
import io.kotest.assertions.throwables.shouldThrowExactlyUnit
import io.kotest.core.spec.style.FreeSpec
import io.kotest.core.test.TestCase
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockkObject
import io.mockk.spyk
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.protobuf.ProtoBuf
import okio.Buffer

@OptIn(ExperimentalSerializationApi::class)
internal class PreferencesSerializerTest : FreeSpec() {
    private val serializer = PreferencesSerializer(
            serializer = ColorSerializer,
            defaultValue = { Color() },
        )

    init {
        "writing and reading from the buffer" {
            val source = Buffer()

            serializer.writeTo(Color(0x123456), source)
            serializer.readFrom(source) shouldBe Color(0x123456)
        }

        "reading from an empty buffer" {
            val source = Buffer()

            every { ProtoBuf.decodeFromByteArray<Any>(any(), any()) } throws SerializationException()

            shouldThrowExactlyUnit<CorruptionException> {
                serializer.readFrom(source)
            }.message shouldBe "reading preferences"
        }

        "reading an invalid data" {
            val source = Buffer()
            source.write(byteArrayOf())

            every { ProtoBuf.decodeFromByteArray<Any>(any(), any()) } throws SerializationException()

            shouldThrowExactlyUnit<CorruptionException> {
                serializer.readFrom(source)
            }.message shouldBe "reading preferences"
        }

        "error when writing secure data" {
            val source = Buffer()

            every { ProtoBuf.encodeToByteArray<Any>(any(), any()) } throws SerializationException()

            shouldThrowExactlyUnit<CorruptionException> {
                serializer.writeTo(Color(0x123456), source)
            }.message shouldBe "writing preferences"
        }

        "error when reading secure data" {
            val source = Buffer()

            every { ProtoBuf.encodeToByteArray<Any>(any(), any()) } throws SerializationException()

            shouldThrowExactlyUnit<CorruptionException> {
                serializer.readFrom(source)
            }.message shouldBe "reading preferences"
        }
    }

    override suspend fun beforeEach(testCase: TestCase) {
        mockkObject(ProtoBuf.Default)
    }

    @Serializable(with = ColorSerializer::class)
    private data class Color(val rgb: Int = 0x000000)

    private object ColorSerializer : KSerializer<Color> {
        override val descriptor: SerialDescriptor =
            PrimitiveSerialDescriptor("Color", PrimitiveKind.STRING)

        override fun serialize(encoder: Encoder, value: Color) {
            val string = value.rgb.toString(16).padStart(6, '0')
            encoder.encodeString(string)
        }

        override fun deserialize(decoder: Decoder): Color {
            val string = decoder.decodeString()
            return Color(string.toInt(16))
        }
    }
}
