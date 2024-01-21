package io.bhex.broker.common.redis;

import com.google.common.base.Charsets;
import com.google.common.primitives.Bytes;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

public final class StringKeySerializer implements RedisSerializer<String> {

    private final byte[] prefixBytes;

    public StringKeySerializer(String prefix) {
        this.prefixBytes = prefix.getBytes(Charsets.UTF_8);
    }

    @Override
    public byte[] serialize(String s) throws SerializationException {
        return Bytes.concat(this.prefixBytes, s.getBytes(Charsets.UTF_8));
    }

    @Override
    public String deserialize(byte[] bytes) throws SerializationException {
        if (bytes == null) {
            throw new SerializationException("bytes is null");
        }
        if (bytes.length <= 0) {
            throw new SerializationException("bytes is empty");
        }
        if (bytes.length < this.prefixBytes.length) {
            throw new SerializationException("key is not start with '" +
                    new String(this.prefixBytes, Charsets.UTF_8) +
                    "'. key:'" + new String(bytes, Charsets.UTF_8) + "'");
        }
        for (int i = 0; i < this.prefixBytes.length; ++i) {
            if (this.prefixBytes[i] != bytes[i]) {
                throw new SerializationException("key is not start with '" +
                        new String(this.prefixBytes, Charsets.UTF_8) +
                        "'. key:'" + new String(bytes, Charsets.UTF_8) + "'");
            }
        }
        return new String(bytes, this.prefixBytes.length, bytes.length - this.prefixBytes.length, Charsets.UTF_8);
    }

}
