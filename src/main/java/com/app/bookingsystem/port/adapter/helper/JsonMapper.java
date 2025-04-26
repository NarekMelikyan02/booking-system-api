package com.app.bookingsystem.port.adapter.helper;

import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Nonnull;
import org.springframework.stereotype.Component;

@Component
public class JsonMapper
{

    private final ObjectMapper objectMapper;

    public JsonMapper(ObjectMapper objectMapper) {this.objectMapper = objectMapper;}

    public <T> T deserialize(@Nonnull String json, @Nonnull Class<T> clazz)
    {
        try
        {
            return this.objectMapper.readValue(json, clazz);
        }
        catch (JsonProcessingException e)
        {
            throw new IllegalArgumentException(e);
        }
    }

    public <T> T deserialize(@Nonnull String json, @Nonnull TypeReference<T> typeReference)
    {
        try
        {
            return this.objectMapper.readValue(json, typeReference);
        }
        catch (JsonProcessingException e)
        {
            throw new IllegalArgumentException(e);
        }
    }

    public <T> String serialize(@Nonnull T object)
    {
        try
        {
            return this.objectMapper.writeValueAsString(object);
        }
        catch (JsonProcessingException e)
        {
            throw new IllegalArgumentException(e);
        }
    }

    public <T> List<T> fromJsonElements(@Nonnull String[] jsonArray, @Nonnull TypeReference<T> typeReference)
    {
        try
        {
            return Arrays.stream(jsonArray)
                .map(element -> this.deserialize(element, typeReference))
                .toList();
        }
        catch (RuntimeException e)
        {
            throw new IllegalArgumentException(e);
        }
    }
}
