package io.bhex.broker.core.domain;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

/**
 * @ProjectName:
 * @Package:
 * @Author: yuehao  <hao.yue@bhex.com>
 * @CreateDate: 2020/11/17 上午10:00
 * @Copyright（C）: 2018 BHEX Inc. All rights reserved.
 */
@Data
@Builder
@ToString
public class CustomLabelBO {
    private Long id;
    private Long orgId;
    private Long labelId;
    private String colorCode;
    private String language;
    private String labelValue;
    private Integer labelType;
}
