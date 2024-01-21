package io.bhex.broker.common.redis;

import com.google.common.base.Charsets;
import com.google.common.primitives.Bytes;
import com.google.common.primitives.Longs;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

public final class LongValueSerializer implements RedisSerializer<Long> {

    @Override
    public byte[] serialize(Long l) throws SerializationException {
        if (l == null) {
            throw new SerializationException("value is null");
        }
        return Long.toString(l).getBytes(Charsets.UTF_8);
    }

    @Override
    public Long deserialize(byte[] bytes) throws SerializationException {
        if (bytes == null || bytes.length <= 0) {
            return null;
        }

        String str = new String(bytes, Charsets.UTF_8);
        Long l = Longs.tryParse(str);
        if (l == null) {
            return null;
        }
        return l;
    }

}
