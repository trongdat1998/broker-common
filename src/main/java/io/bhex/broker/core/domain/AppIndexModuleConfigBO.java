package io.bhex.broker.core.domain;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

/**
 * @ProjectName:
 * @Package:
 * @Author: yuehao  <hao.yue@bhex.com>
 * @CreateDate: 2020/11/17 上午10:01
 * @Copyright（C）: 2018 BHEX Inc. All rights reserved.
 */
@Data
@Builder
@ToString
public class AppIndexModuleConfigBO {

    private Long orgId;

    private Integer moduleType;

    private String language;

    private String moduleName;

    private String moduleIcon;

    private Integer jumpType;

    private String jumpUrl;

    private Boolean needLogin;

    private Integer customOrder;

    private Integer loginShow;

    private String darkDefaultIcon;

    private String darkSelectedIcon;

    private String lightDefaultIcon;

    private String lightSelectedIcon;
}
