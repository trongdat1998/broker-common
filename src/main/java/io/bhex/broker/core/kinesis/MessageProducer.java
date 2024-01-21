package io.bhex.broker.core.kinesis;

import software.amazon.awssdk.services.kinesis.model.PutRecordResponse;

import java.util.concurrent.CompletableFuture;

public interface MessageProducer {

    PutRecordResponse sendMessage(byte[] byteMessage);

    PutRecordResponse sendMessage(String message);

    CompletableFuture<PutRecordResponse> sendMessageAsync(byte[] byteMessage);

    CompletableFuture<PutRecordResponse> sendMessageAsync(String message);

}
