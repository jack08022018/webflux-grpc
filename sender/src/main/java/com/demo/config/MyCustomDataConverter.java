package com.demo.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.protobuf.ByteString;
import io.temporal.api.common.v1.Payload;
import io.temporal.api.common.v1.Payloads;
import io.temporal.api.failure.v1.Failure;
import io.temporal.common.converter.DataConverter;
import io.temporal.common.converter.DataConverterException;
import io.temporal.failure.TemporalFailure;
import reactor.core.publisher.Mono;

import javax.annotation.Nonnull;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;

import static io.temporal.internal.sync.WorkflowInternal.getDataConverter;

public class MyCustomDataConverter implements DataConverter {
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
//    private byte[] serialize(Object value) throws DataConverterException {
//        ObjectMapper objectMapper = new ObjectMapper();
//        try {
//            String json = objectMapper.writeValueAsString(value);
//            return json.getBytes("UTF-8");
//        } catch (Exception e) {
//            throw new DataConverterException("Error serializing object", e);
//        }
//    }
//    public <T> T deserialize(byte[] bytes, Class<T> clazz) throws DataConverterException {
//        try {
//            return new ObjectMapper().readValue(bytes, clazz);
//        } catch (Exception e) {
//            throw new DataConverterException("Failed to deserialize object", e);
//        }
//    }
//
//    @Override
//    public <T> Optional<Payload> toPayload(T value) throws DataConverterException {
//        try {
//            byte[] data = serialize(value);
//            Payload payload = Payload.newBuilder()
//                    .setData(ByteString.copyFrom(data))
//                    .build();
//            return Optional.of(payload);
//        } catch (Exception e) {
//            throw new DataConverterException("Failed to serialize object", e);
//        }
//    }
//
//    @Override
//    public <T> T fromPayload(Payload payload, Class<T> valueClass, Type valueType) throws DataConverterException {
//        try {
//            byte[] bytes = payload.getData().toByteArray();
//            T deserialized = deserialize(bytes, valueClass); // Custom deserialization method
//            return deserialized;
//        } catch (Exception e) {
//            throw new DataConverterException("Failed to deserialize object", e);
//        }
//    }
//
//    @Override
//    public Optional<Payloads> toPayloads(Object... values) throws DataConverterException {
//        System.out.println("YYY");
//        return Optional.empty();
//    }
//
//    @Override
//    public <T> T fromPayloads(int index, Optional<Payloads> content, Class<T> valueType, Type valueGenericType) throws DataConverterException {
//        System.out.println("XXX");
//        return null;
////        if (valueType.isAssignableFrom(Mono.class)) {
////            Payloads payloads = content.orElseThrow(() -> new DataConverterException("Payloads not found"));
////            byte[] bytes = payloads.getPayloads(index).toByteArray();
////            // Convert byte array to Mono<T>
////            // ...
////            Mono<String> mono = getDataConverter().fromPayloads(payloads, Mono.class, String.class);
////            // Cast the Mono<String> object to the generic type T and return it
////            return (T) mono;
////        } else {
////            throw new DataConverterException("Unsupported value type: " + valueType.getName());
////        }
//    }
//
//    @Nonnull
//    @Override
//    public TemporalFailure failureToException(@Nonnull Failure failure) {
//        return null;
//    }
//
//    @Nonnull
//    @Override
//    public Failure exceptionToFailure(@Nonnull Throwable throwable) {
//        return null;
//    }
}
