package io.bhex.broker.core.domain;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

/**
 * @ProjectName:
 * @Package:
 * @Author: yuehao  <hao.yue@bhex.com>
 * @CreateDate: 2020/11/21 下午4:22
 * @Copyright（C）: 2018 BHEX Inc. All rights reserved.
 */
@Data
@Builder
@ToString
public class AppVersionConfigBO {
    private Long orgId;
    private String appId;
    private String appVersion;
    private String deviceType;
    private String deviceVersion;
    private String appChannel;
    private Boolean needUpdate;
    private Boolean needForceUpdate;
    private String downloadUrl;
    private String newFeature;
    private String newVersion;
    private Boolean isLatestVersion;
    private String downloadWebviewUrl;
}
