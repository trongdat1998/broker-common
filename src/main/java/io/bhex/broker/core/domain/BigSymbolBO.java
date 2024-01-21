package io.bhex.broker.core.domain;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@ToString
public class BigSymbolBO {

    private Long id;
    private Long orgId;
    private Integer symbolIndex;
    private String symbol;
    private String symbolId;
    private String symbolName;
    private Integer baseTokenIndex;
    private String baseToken;
    private String baseTokenId;
    private String baseTokenName;
    private Integer quoteTokenIndex;
    private String quoteToken;
    private String quoteTokenId;
    private String quoteTokenName;

    private Boolean allowMargin;// 0 未开通杠杆  1 开通杠杆

    // allowTrade=true 之后再判断allowBuy 和 allowSell
    private Boolean saasAllowTrade; // saas允许交易
    private Boolean saasBrokerAllowTrade; // saas对broker操作允许交易
    private Boolean allowTrade; // 允许交易

    private Boolean saasAllowBuy;
    private Boolean allowBuy; // 0 允许买
    private Boolean saasAllowSell;
    private Boolean allowSell; // 0 允许卖


    private Boolean allowMarket;
    private Boolean allowPlan;
    private Boolean hideFromOpenapi; // 关闭openapi展示此币对
    private Boolean forbidOpenapiTrade; // 禁止openapi交易此币对
    private Integer status; // 是否启用

    // 基础手续费配置
    private String minInterestFeeRate;
    private String takerMinFeeRate;
    private String makerMinFeeRate;
    private String makerBonusRate;
    private String takerPayForwardFeeRate;
    private String makerPayForwardFeeRate;

    private Integer saasStatus;

    private Boolean isBass;
    private Boolean isAggregate;
    private Boolean isTest;
    private Boolean isMainstream;

    private Integer customOrder;
    private Integer indexShow;
    private Integer indexShowOrder;
    private Integer indexRecommendOrder;
    private Integer favoriteRecommendOrder;
    private Integer showStatus; // 前端显示状态 1-显示 0-隐藏

    private Integer category; // 1币币, 3期权, 4期货
    private Boolean isETF;
    private ETFSymbol etfSymbol;
    private String marginPriceIndex;
    private MarginSymbol marginSymbol; // 杠杆symbol属性
    private OptionSymbol optionSymbol; // 期权symbol属性
    private FuturesSymbol futuresSymbol;

    private Long filterTime; // 用于配置过滤历史k线
    private Integer filterTopStatus; // topN涨幅榜展示状态 1 过滤 0不过滤
    private Long labelId;

    private Integer pricePrecisionScale;
    private BigDecimal pricePrecision; //
    private Integer priceStep = 1; //
    // 相对价格限制
    private Boolean relativePriceLimit;
    private BigDecimal relativeUpperBound;
    private BigDecimal relativeLowerBound;
    // 绝对价格限制
    private Boolean absolutePriceLimit;
    private BigDecimal absoluteMinPrice;
    private BigDecimal absoluteMaxPrice;

    private Integer quantityPrecisionScale;
    private BigDecimal quantityPrecision;
    private Integer quantityStep = 1;
    private BigDecimal minQuantity;
    @Builder.Default
    private BigDecimal maxQuantity = BigDecimal.ZERO;

    private int amountPrecisionScale;
    private BigDecimal amountPrecision; // for quotePrecision, multiple of pricePrecision and quantityPrecision
    private int amountStep; // minCommonMultiple of priceStep and quantityStep
    private BigDecimal minAmount;
    @Builder.Default
    private BigDecimal maxAmount = BigDecimal.ZERO;

    private Boolean symbolPriceProtect;
    private Long protectDeadline;
    private BigDecimal protectUpperBound;
    private BigDecimal protectLowerBound;

    private Long onlineTime;
    private Long tradingTime; // 竞价时间

    private Integer digitMergeValue; //
    private String digitMergeList;

    private Long matchOrgId; // 用于共享深度

    private Long created;
    private Long updated;

    @Data
    @Builder
    public static class ETFSymbol {
        private Integer contractSymbolIndex;
        private String contractSymbol;
        private String contractSymbolId;
        private BigDecimal etfPrice;
        private Boolean isLong;
        private Integer leverage;
        private Long time;
        private Integer underlyingIndexSymbolIndex;
        private String underlyingIndexSymbol;
        private String underlyingIndexSymbolId;
        private BigDecimal underlyingIndexPrice;
    }

    // category=3
    @Data
    @Builder
    public static class OptionSymbol {
        // symbolName Multi-Language
        private List<SymbolMultiLanguage> languages;
        private Long issueDate; // 生效/上线日期
        private Long settlementDate; // 到期/交割时间
        private Integer isCall; // call or put
        private String maxPayOff; // 最大赔付
        private String strikePrice; // 行权价
        private String coinToken; // 计价货币
        private String indexToken; // 标的指数
        private OptionSymbolSettleStatus settleStatus; // 交割状态
        private OptionSymbolType type; // 期权类型：CALL=看涨，PUT=看跌

        private String underlyingId; // 标的id
    }

    // category=4
    @Data
    @Builder
    public static class FuturesSymbol {
        // symbolName Multi-Language
        private List<SymbolMultiLanguage> languages;
        // 目前没有券商设置允许开平仓的开关
        @Builder.Default
        private Boolean allowOpen = Boolean.TRUE;
        @Builder.Default
        private Boolean allowClose = Boolean.TRUE;
        private Boolean saasAllowOpen;
        private Boolean saasAllowClose;

        private String priceUnit;
        private String quantityUnit;

        private String indexToken; // 指数名字
        private String displayIndexToken; // 用于页面显示指数价格(正向=index_token,反则反之)

        private String displayTokenId;
        private String displayToken;

        private String currency;
        private String displayCurrency;

        private String marginTokenId;
        private String marginToken;
        private Integer marginPrecisionScale;
        private BigDecimal marginPrecision;

        private String underlyingId; // 期货标的id
        private String displayUnderlyingId;  //期货显示标的id，用于显示，不区分正反向

        private Boolean isPerpetual; // 是否永续
        private Boolean isReverse; // 是否反向

        private Integer multiplierPrecisionScale;
        private BigDecimal multiplier;
        //        private Long issueDate; // 生效/上线日期
        private Long settlementDate; // 到期/交割时间，永续合约没这个字段
//        private String coinToken; // 计价货币

        private BigDecimal maxLeverage; // 最大杠杆倍数
        @Builder.Default
        private BigDecimal defaultLeverage = BigDecimal.TEN; // 默认杠杆倍数
        private String leverageRange; // 逗号分割

        private String interestRate; // ?
        private String settleFundingClampRegion; // ?

        private Integer maxBeginnerRiskLevel; // 初始风险限额index
        private Integer maxNormalRiskLevel; // 普通用户风险限额index
        private Integer maxAdvancedRiskLevel; // 高级用户风险限额index
        private Integer maxSpecialRiskLevel; // 特殊用户风险限额index

        private List<FuturesSymbolRiskLimit> riskLimits;
    }

    // 杠杆复用的是币币交易的币对
    public static class MarginSymbol {

    }

    @Data
    @Builder
    public static class SymbolMultiLanguage {
        private String language;
        private String name;
    }

    public enum OptionSymbolSettleStatus {
        SETTLE_NO(0),
        SETTLE_DOING(1),
        SETTLE_DONE(2),
        ;

        private final int status;

        OptionSymbolSettleStatus(int status) {
            this.status = status;
        }

        public int status() {
            return status;
        }
    }

    public enum OptionSymbolType {
        SETTLE_DOING(1),
        SETTLE_DONE(2),
        ;

        private final int type;

        OptionSymbolType(int type) {
            this.type = type;
        }

        public int type() {
            return type;
        }
    }

    @Data
    @Builder
    public static class FuturesSymbolRiskLimit {
        private int index;
        private int limit;
        private BigDecimal initialMarginRate;
        private BigDecimal maintainMarginRate;
    }

    public boolean isSpotSymbol() {
        return getCategory() == 1;
    }

    public boolean isMarginSymbol() {
        return getCategory() == 1 && getAllowMargin();
    }

    public boolean isOptionSymbol() {
        return getCategory() == 3;
    }

    public boolean isFuturesSymbol() {
        return getCategory() == 4;
    }

}
