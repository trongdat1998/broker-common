package io.bhex.broker.core.domain.aws;

import com.google.common.collect.Lists;
import lombok.Data;

import java.util.List;

@Data
public class AwsSQSProperties {

    private String accessKey;
    private String secretKey;
    private String regionName;
    private List<QueueConsumerConfig> consumerConfig = Lists.newArrayList();
    private List<QueueProducerConfig> producerConfig = Lists.newArrayList();

    @Data
    public static class QueueConsumerConfig {
        private String queueUrl;
        private String consumerName;
    }

    @Data
    public static class QueueProducerConfig {
        private String queueUrl;
        private String producerName;
    }

}
