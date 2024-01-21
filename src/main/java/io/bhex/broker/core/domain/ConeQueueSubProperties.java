package io.bhex.broker.core.domain;

import lombok.Data;

import java.util.List;

@Data
public class ConeQueueSubProperties {

    private String host;
    private int port;
    private List<SubTopic> subTopic;

    @Data
    public static class SubTopic {

        private String topic;
        private String messageHandlerName;

    }

}
