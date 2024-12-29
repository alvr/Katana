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
internal open class PreferencesSerializer<T>(
    private val serializer: KSerializer<T>,
    override val defaultValue: T,
) : OkioSerializer<T> {
    override suspend fun readFrom(source: BufferedSource): T =
        operation("reading preferences") {
            val input = source.use { buffered ->
                buffered.readByteArray()
                    .let { value -> Base64.decode(value) }
                    .readFrom()
            }

            ProtoBuf.decodeFromByteArray(serializer, input)
        }

    override suspend fun writeTo(t: T, sink: BufferedSink) {
        operation("writing preferences") {
            val output = ProtoBuf.encodeToByteArray(serializer, t)
                .writeTo()
                .let { value -> Base64.encodeToByteArray(value) }

            sink.use { buffered -> buffered.write(output) }
        }
    }

    protected open fun ByteArray.readFrom(): ByteArray = this

    protected open fun ByteArray.writeTo(): ByteArray = this

    @Suppress("TooGenericExceptionCaught")
    private inline fun <R> operation(message: String, block: () -> R): R = try {
        block()
    } catch (e: Throwable) {
        throw CorruptionException(message, e)
    }
}
