package io.bhex.broker.common.redis;

import com.google.common.base.Charsets;
import com.google.common.primitives.Bytes;
import com.google.common.primitives.Longs;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

public final class LongKeySerializer implements RedisSerializer<Long> {

    private final byte[] prefixBytes;

    public LongKeySerializer(String prefix) {
        this.prefixBytes = prefix.getBytes(Charsets.UTF_8);
    }

    @Override
    public byte[] serialize(Long l) throws SerializationException {
        return Bytes.concat(this.prefixBytes, Long.toString(l).getBytes(Charsets.UTF_8));
    }

    @Override
    public Long deserialize(byte[] bytes) throws SerializationException {
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
        for (int i=0; i<this.prefixBytes.length; ++i) {
            if (this.prefixBytes[i] != bytes[i]) {
                throw new SerializationException("key is not start with '" +
                        new String(this.prefixBytes, Charsets.UTF_8) +
                        "'. key:'" + new String(bytes, Charsets.UTF_8) + "'");
            }
        }
        String str = new String(bytes, this.prefixBytes.length, bytes.length - this.prefixBytes.length, Charsets.UTF_8);
        Long l = Longs.tryParse(str);
        if (l == null) {
            throw new SerializationException("key is not long value. str:'" + str + "'");
        }
        return l;
    }

}
