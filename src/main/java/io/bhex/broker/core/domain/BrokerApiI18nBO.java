package io.bhex.broker.core.domain;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString
public class BrokerApiI18nBO {
    private long orgId;
    
    private String key;

    private String language;

    private String value;
}
