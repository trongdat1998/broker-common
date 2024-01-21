package io.bhex.broker.core.domain;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class FuturesPositionBO {

    private Long id;
    private Long orgId;
    private Long userId;
    private Long accountId;
    private String symbol;
    private String symbolId;
    private String symbolName;
    /**
     * 仓位索引 0 - 多 /（合并仓位），1 - 空
     * FIXME: 是否改为  （0-独立模式多仓，1-独立模式空仓，2-合并模式）
     */
    private Integer positionIndex;

    /**
     * 仓位类型 0-独立仓位， 1-合并仓位
     */
    private Integer positionType;

    /**
     * 保证金类型 0-逐仓，1-全仓
     */
    private Integer marginType;
    private BigDecimal orderMargin; // 委托保证金
    private BigDecimal holdMargin; // 持仓保证金
    private BigDecimal availableMargin; // 可用保证金（适用于全仓模式）
    private BigDecimal buyOrderQuantity; // 买单挂单数量
    private BigDecimal buyOrderOpenValue; // 买单挂单价值
    private BigDecimal sellOrderQuantity; // 卖单挂单数量
    private BigDecimal sellOrderOpenValue; // 卖单挂单价值
    private BigDecimal holdQuantity; // 持仓数量
    private BigDecimal holdOpenValue; // 持仓开仓价值
    private BigDecimal realizedPnl; // 已实现盈亏
    private BigDecimal liquidationPrice; // 爆仓价
    private BigDecimal bankruptcyPrice; // 破产价

}
