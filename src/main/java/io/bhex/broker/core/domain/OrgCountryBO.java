package io.bhex.broker.core.domain;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

/**
 * @ProjectName:
 * @Package:
 * @Author: yuehao  <hao.yue@bhex.com>
 * @CreateDate: 2020/11/16 下午4:16
 * @Copyright（C）: 2018 BHEX Inc. All rights reserved.
 */
@Data
@Builder
@ToString
public class OrgCountryBO {
    private Long id;
    private Long orgId;
    private Long countryId;
    private Integer customOrder;
    private Integer allowRegister;
    private Integer allowLogin;
    private Integer allowKyc;
    private Integer allowBindMobile;
    private Integer status;
}