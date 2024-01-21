package io.bhex.broker.core.domain;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

/**
 * @ProjectName:
 * @Package:
 * @Author: yuehao  <hao.yue@bhex.com>
 * @CreateDate: 2020/11/17 上午9:53
 * @Copyright（C）: 2018 BHEX Inc. All rights reserved.
 */
@Data
@Builder
@ToString
public class BannerBO {
    private Long orgId;
    private Integer deviceType;
    private String language;
    private String imageUrl;
    private Integer type;
    private String title;
    private String content;
    private String pageUrl;
    private Integer customOrder;
    private Long publishTime;
    private Integer bannerPosition;
}
