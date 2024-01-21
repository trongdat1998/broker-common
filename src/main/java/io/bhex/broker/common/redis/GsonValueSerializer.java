package io.bhex.broker.common.redis;

import com.google.common.base.Charsets;
import com.google.gson.reflect.TypeToken;
import io.bhex.broker.common.util.JsonUtil;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;

public final class GsonValueSerializer<T> implements RedisSerializer<T> {

    private final Type type;

    public GsonValueSerializer(Class<T> clazz) {
        this.type = clazz;
    }

    public GsonValueSerializer(TypeToken<T> typeToken) {
        this.type = typeToken.getType();
    }

    @Override
    public byte[] serialize(T t) throws SerializationException {
        if (t == null) {
            throw new SerializationException("value is null");
        }
        if (this.type instanceof Class && !((Class)this.type).isInstance(t)) {
            throw new SerializationException("value is invalid class " + t.getClass() + ", expect " + this.type);
        }
        return JsonUtil.defaultGson().toJson(t).getBytes(Charsets.UTF_8);
    }

    @Override
    public T deserialize(byte[] bytes) throws SerializationException {
        if (bytes == null || bytes.length <= 0) {
            return null;
        }
        return JsonUtil.defaultGson().fromJson(new InputStreamReader(new ByteArrayInputStream(bytes), Charsets.UTF_8), this.type);
    }
}
