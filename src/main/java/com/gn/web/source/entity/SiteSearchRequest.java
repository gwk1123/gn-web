package com.gn.web.source.entity;


import com.gn.web.manual.entity.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class SiteSearchRequest {

    /**
     * 站点
     */
    private SiteConfig siteConfig;

    /**
     * ota规则 ota-0(白名单)
     */
    private List<OtaRule> otaRuleWhites;

    /**
     * ota规则 ota-1(黑白名单)
     */
    private List<OtaRule> otaRuleBlacks;

    /**
     * ota规则 ota-2(政策类型)
     */
    private List<OtaRule> otaPolicyTypes;

    /**
     * ota规则 ota-3(OTA政策发布价格)
     */
    private List<OtaRule> otaPublishPrices;

    /**
     * ota规则 ota-4(OTA运价规则(同程))
     */
    private List<OtaRule> otaPriceRules;

    /**
     * ota规则 ota-5(OTA舱位替换)
     */
    private List<OtaRule> otaRealCabins;

    /**
     * 全平台政策
     */
    private List<CommonPrice> commonPrices =new ArrayList<>();

    /**
     * 全平台政策
     */
    private PolicyGlobal policyGlobal;

    /**
     * 获取调价管理政策
     */
    private PolicyInfo policyInfo;

    /**
     * 机场优先级
     */
    List<String> airportPrioritys;

}
