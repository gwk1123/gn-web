package com.gn.web.source.entity;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class RebateMoney {

    /**
     * 规则前返点 单位%
     */
    private BigDecimal ruleBeforeCommission;

    /**
     * 规则前返现金
     */
    private BigDecimal ruleBeforeAmount;

    /**
     * 规则后返点 单位%
     */
    private BigDecimal ruleAfterCommission;

    /**
     * 规则后返现金
     */
    private BigDecimal ruleAfterAmount;

}
