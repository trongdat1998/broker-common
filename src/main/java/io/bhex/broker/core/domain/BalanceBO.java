package io.bhex.broker.core.domain;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class BalanceBO {

    private Long id;
    private Long orgId;
    private Long userId;
    private Long accountId;
    private String token;
    private String tokenId;
    private String tokenName;
    private BigDecimal total;
    private BigDecimal available;
    private BigDecimal frozen;
    private BigDecimal riskBalance; // 风险资产
    private BigDecimal otherFrozen;
    private BigDecimal tradeFrozen;
    private BigDecimal shardFrozen;
    private BigDecimal internalShardFrozen;
    private BigDecimal externalShardFrozen;
    private BigDecimal marginFrozen;
    private BigDecimal externalBalance; // 不计入资产
    private BigDecimal debt; // 负债

}
