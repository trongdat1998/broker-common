package io.bhex.broker.core.domain;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

/**
 * @ProjectName:
 * @Package:
 * @Author: yuehao  <hao.yue@bhex.com>
 * @CreateDate: 2020/11/28 上午9:48
 * @Copyright（C）: 2018 BHEX Inc. All rights reserved.
 */

@Data
@Builder
@ToString
public class SubBusinessSubjectBO {
    private Long id;
    private Long orgId;
    private Integer parentSubject; // 父流水类型
    private Integer subject; // 流水类型
    private String subjectName; // 流水名称
    private String language; // 语言
    private Integer status; // 状态  1 启用  0 禁用
    private Long created;
    private Long updated;
}
