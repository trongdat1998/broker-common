package io.bhex.broker.core.domain.aws;

import com.google.common.collect.Lists;
import lombok.Data;

import java.util.List;

@Data
public class AwsKinesisProperties {

    private String accessKey;
    private String secretKey;
    private String regionName;
    private List<StreamConsumerConfig> consumerConfig = Lists.newArrayList();
    private List<StreamProducerConfig> producerConfig = Lists.newArrayList();


    @Data
    public static class StreamConsumerConfig {
        private String streamName;
        private String consumerName;
    }

    @Data
    public static class StreamProducerConfig {
        private String streamName;
        private String producerName;
    }

}
