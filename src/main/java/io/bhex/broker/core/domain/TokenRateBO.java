package io.bhex.broker.core.domain;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Map;

@Data
@Builder
public class TokenRateBO {

    private Long orgId;
    private int tokenIndex;
    private String token;
    private String tokenId;
    private String tokenName;
    private Map<String, BigDecimal> baseTokensRate;
    private Map<String, BigDecimal> legalCoinsRate;
    private Long time;

}
