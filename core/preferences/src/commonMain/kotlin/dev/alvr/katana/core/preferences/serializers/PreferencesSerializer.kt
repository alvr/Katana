package dev.alvr.katana.core.preferences.serializers

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.okio.OkioSerializer
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.protobuf.ProtoBuf
import okio.BufferedSink
import okio.BufferedSource
import okio.use

@OptIn(ExperimentalEncodingApi::class, ExperimentalSerializationApi::class)
internal class PreferencesSerializer<T>(
    private val serializer: KSerializer<T>,
    defaultValue: () -> T,
) : OkioSerializer<T> {
    override val defaultValue: T by lazy { defaultValue() }

    override suspend fun readFrom(source: BufferedSource): T =
        operation("reading preferences") {
            val input = source.use { buffered -> Base64.decode(buffered.readByteArray()) }
            ProtoBuf.decodeFromByteArray(serializer, input)
        }

    override suspend fun writeTo(t: T, sink: BufferedSink) {
        operation("writing preferences") {
            val output = Base64.encodeToByteArray(ProtoBuf.encodeToByteArray(serializer, t))
            sink.use { buffered -> buffered.write(output) }
        }
    }

    @Suppress("TooGenericExceptionCaught")
    private inline fun <R> operation(message: String, block: () -> R): R = try {
        block()
    } catch (e: Throwable) {
        throw CorruptionException(message, e)
    }
}
