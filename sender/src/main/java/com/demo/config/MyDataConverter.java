package com.demo.config;

import io.temporal.api.common.v1.Payload;
import io.temporal.api.common.v1.Payloads;
import io.temporal.api.failure.v1.Failure;
import io.temporal.common.converter.DataConverter;
import io.temporal.common.converter.DataConverterException;
import io.temporal.common.converter.PayloadConverter;
import io.temporal.common.converter.ProtobufJsonPayloadConverter;
import io.temporal.failure.TemporalFailure;

import javax.annotation.Nonnull;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

public class MyDataConverter implements DataConverter {
    @Override
    public <T> Optional<Payload> toPayload(T value) throws DataConverterException {
        return Optional.empty();
    }

    @Override
    public <T> T fromPayload(Payload payload, Class<T> valueClass, Type valueType) throws DataConverterException {
        return null;
    }

    @Override
    public Optional<Payloads> toPayloads(Object... values) throws DataConverterException {
        return Optional.empty();
    }

    @Override
    public <T> T fromPayloads(int index, Optional<Payloads> content, Class<T> valueType, Type valueGenericType) throws DataConverterException {
        return null;
    }

    @Nonnull
    @Override
    public TemporalFailure failureToException(@Nonnull Failure failure) {
        return null;
    }

    @Nonnull
    @Override
    public Failure exceptionToFailure(@Nonnull Throwable throwable) {
        return null;
    }
}
