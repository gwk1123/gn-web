package com.gn.web.source.entity;

import lombok.Data;

@Data
public class PushPolicyType {

    /**
     * 是否可以支付 0-是；1-否
      */
    private String payFlag;


    /**
     * 否可以生成PNR 0-是；1-否
     */
    private String pnrFlag;

    /**
     * 是否PAT校验 0-是；1-否
     */
    private String patFlag;

    /**
     * 儿童是否适用 0-是；1-否
     */
    private String childFlag;

    /**
     * 运价规则
     */
    private String priceRule;

}
