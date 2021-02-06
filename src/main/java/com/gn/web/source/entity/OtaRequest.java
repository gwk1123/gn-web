package com.gn.web.source.entity;

import lombok.Data;

@Data
public class OtaRequest {


    /**
     * 站点
     */
    private String otaSiteCode;

    /**
     * 行程类型
     */
    private String tripType;

    /**
     *出发机场
     */
    private String fromAirport;

    /**
     * 抵达机场
     */
    private String toAirport;

    /**
     * 出发日期
     */
    private String fromDate;

    /**
     * 抵达日期
     */
    private String retDate;

}
