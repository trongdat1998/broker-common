package io.bhex.broker.core.domain;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@Builder
@ToString
public class TradingAreaBO {

    private Long id;
    private Long orgId;
    private Integer type; // 1 币币交易区 2 杠杆交易区 3 期权交易区 4 合约交易区
    private List<TradingAreaMultiLanguage> multiLanguageNames; // 交易区名称多语言支持
    private String icon; // 交易区icon (如果需要的话)
    private Boolean indexShow;
    private Integer customOrder; // 交易区排序
    private Long parentId; // 父节点id, root节点的parentId=0
    private List<String> symbolIds; // 交易区下面的交易币对, symbolIdList，按照顺序排序
    private List<String> symbols; // 冗余的设置为symbolNameList

    @Data
    @Builder
    public static class TradingAreaMultiLanguage {
        private String language;
        private String name;
    }

}
