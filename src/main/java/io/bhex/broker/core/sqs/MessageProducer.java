package io.bhex.broker.core.sqs;

import com.amazonaws.services.sqs.model.SendMessageResult;

import java.util.Map;
import java.util.concurrent.Future;

public interface MessageProducer {

    SendMessageResult sendMessage(Map<String, String> messageAttribute, String messageBody);

    Future<SendMessageResult> sendMessageAsync(Map<String, String> messageAttribute, String messageBody);

}
