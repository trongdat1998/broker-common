package io.bhex.broker.core.domain;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString
public class BrokerLanguageBO {

    private Long orgId;

    private String moduleName;

    private String language;

    private String url;

}
