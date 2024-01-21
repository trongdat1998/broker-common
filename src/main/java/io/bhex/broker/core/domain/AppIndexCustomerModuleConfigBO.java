package io.bhex.broker.core.domain;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

/**
 * @ProjectName:
 * @Package:
 * @Author: yuehao  <hao.yue@bhex.com>
 * @CreateDate: 2020/11/20 上午11:26
 * @Copyright（C）: 2018 BHEX Inc. All rights reserved.
 */
@Data
@Builder
@ToString
public class AppIndexCustomerModuleConfigBO {
    private Long brokerId;
    private String moduleName;
    private String locale;
    private String content;
}
