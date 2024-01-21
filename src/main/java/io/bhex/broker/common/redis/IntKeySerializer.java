package io.bhex.broker.common.redis;

import com.google.common.base.Charsets;
import com.google.common.primitives.Bytes;
import com.google.common.primitives.Ints;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

public final class IntKeySerializer implements RedisSerializer<Integer> {

    private final byte[] prefixBytes;

    public IntKeySerializer(String prefix) {
        this.prefixBytes = prefix.getBytes(Charsets.UTF_8);
    }

    @Override
    public byte[] serialize(Integer i) throws SerializationException {
        return Bytes.concat(this.prefixBytes, Integer.toString(i).getBytes(Charsets.UTF_8));
    }

    @Override
    public Integer deserialize(byte[] bytes) throws SerializationException {
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
        Integer i = Ints.tryParse(str);
        if (i == null) {
            throw new SerializationException("key is not int value. str:'" + str + "'");
        }
        return i;
    }

}
