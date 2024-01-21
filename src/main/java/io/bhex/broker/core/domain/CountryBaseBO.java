package io.bhex.broker.core.domain;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

/**
 * @ProjectName:
 * @Package:
 * @Author: yuehao  <hao.yue@bhex.com>
 * @CreateDate: 2020/11/17 下午2:53
 * @Copyright（C）: 2018 BHEX Inc. All rights reserved.
 */
@Data
@Builder
@ToString
public class CountryBaseBO {
    private Long countryId;
    private String nationalCode;
    private String domainShortName;
    private String currencyCode;
    private Integer forAreaCode;
    private Integer forNationality;
    private String language;
    private String countryName;
    private String indexName;
}