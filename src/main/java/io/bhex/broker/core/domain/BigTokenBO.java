package io.bhex.broker.core.domain;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@ToString
public class BigTokenBO {

    private Long id;
    private Long orgId;
    private int tokenIndex;
    private String token;
    private String tokenId;
    private String tokenName; // 取值是 manage 里面的 local_token_id
    private String tokenFullName;
    private String tokenIcon;
    private List<TokenChainInfo> chainInfoList; // USDT: ERC20 TRC20 OMNI
    private String tokenChainType;

    private Boolean saasAllowDeposit;
    private Boolean saasAllowBrokerDeposit;
    private Boolean allowDeposit;
    private Boolean needAddressTag;
    @Builder.Default
    private BigDecimal minDepositAmount = BigDecimal.ZERO;

    private Boolean saasAllowWithdraw;
    private Boolean saasAllowBrokerWithdraw;
    private Boolean allowWithdraw;
    @Builder.Default
    private BigDecimal minWithdrawAmount = BigDecimal.ZERO;

    private Integer precisionValue;
    private BigDecimal precision;

    private Boolean isMarginToken; // 是否杠杆保证金
    private MarginTokenConfig marginToken; //

    private TokenRateTreeNode rateTreeNode;

    private String withdrawFeeTokenId;
    private BigDecimal extraWithdrawFee;
    private BigDecimal extraWithdrawFeeInner;
    private BigDecimal extraWithdrawFeeCloud;
    private BigDecimal minDepositQuantity;
    private BigDecimal minWithdrawQuantity;

    private Boolean isBaas;
    private Boolean isAggregate;
    private Boolean isTest;
    private Boolean isMainstream;

    private List<DetailMultiLanguage> languages; //币种详情描述url列表

    private Boolean isHighRiskToken;
    private Integer status;
    private Integer showStatus;
    private Integer customOrder;
    private Integer category;
    private Long created;
    private Long updated;

    @Data
    @Builder
    public static class TokenChainInfo {
        private String chainTokenId;
        private String chainType;

        private Boolean saasAllowDeposit;
        private Boolean saasAllowBrokerDeposit;
        private Boolean allowDeposit;

        private Boolean saasAllowWithdraw;
        private Boolean saasAllowBrokerWithdraw;
        private Boolean allowWithdraw;

        private String withdrawFeeTokenId;
        private BigDecimal extraWithdrawFee;
        private BigDecimal extraWithdrawFeeInner;
        private BigDecimal extraWithdrawFeeCloud;
        private BigDecimal minDepositQuantity;
        private BigDecimal minWithdrawQuantity;
    }

    @Data
    @Builder
    public static class TokenRateTreeNode {
        private Long orgId;
        private String sourceTokenId;
        private String targetTokenId;
        private int rateSymbolIndex;
        private String rateSymbolId;
        private BigDecimal fairPrice;
    }

    @Data
    @Builder
    public static class MarginTokenConfig {
        private Boolean isOpen;
        private Boolean canBorrow;
        private BigDecimal convertRate;
        private int leverage;
        private BigDecimal maxBorrowQuantity;
        private BigDecimal minBorrowQuantity;
        private int quantityPrecision;
        private BigDecimal repayMinQuantity;
        private int interestPrecision;
    }

    @Data
    @Builder
    public static class DetailMultiLanguage {
        private String language;
        private String url;
    }
}
