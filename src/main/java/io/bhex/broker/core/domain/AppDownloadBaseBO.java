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
public class AppDownloadBaseBO {
    private String googlePlayDownloadUrl;
    private String testflightDownloadUrl;
    private String appStoreDownloadUrl;
    private String androidDownloadUrl;
    private String iosDownloadUrl;
    private String iosDownloadPageUrl;
    private String androidDownloadPageUrl;
    private String androidGuideImageUrl;
    private String iosGuideImageUrl;
}
