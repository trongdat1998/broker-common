package io.bhex.broker.core.domain;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

/**
 * @ProjectName:
 * @Package:
 * @Author: yuehao  <hao.yue@bhex.com>
 * @CreateDate: 2020/11/17 上午9:54
 * @Copyright（C）: 2018 BHEX Inc. All rights reserved.
 */
@Data
@Builder
@ToString
public class ShareConfigBO {
    private Long brokerId;
    private String logoUrl;
    private String watermarkImageUrl;
    private String title;
    private String description;
    private String downloadUrl;
    private String language;
}