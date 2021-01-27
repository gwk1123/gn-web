package com.gn.web.manual.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 全平台政策管理
 * </p>
 *
 * @author gwk
 * @since 2021-01-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("policy_info")
@ApiModel(value="PolicyInfo对象", description="全平台政策管理")
public class PolicyInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "OTA站点代码 多个/隔开")
    private String otaSiteCode;

    @ApiModelProperty(value = "数据来源 WEBSITE,B2B,FD,NFD,ML-自用 多个用英文/分开")
    private String sourceType;

    @ApiModelProperty(value = "数据渠道代码 FUYAO-扶摇,NH-南航,FD,NFD,ML 多个用英文/分开")
    private String channel;

    @ApiModelProperty(value = "数据来源方式 0-全部 1-系统 2-人工")
    private Boolean sourceWay;

    @ApiModelProperty(value = "政策规则类型 0-全局 1-明细")
    private Boolean ruleType;

    @ApiModelProperty(value = "航司")
    private String airline;

    @ApiModelProperty(value = "出发机场码 多个用英文的/分开 999表示全国")
    private String depAirport;

    @ApiModelProperty(value = "抵达机场码 多个用英文的/分开 999表示全国")
    private String arrAirport;

    @ApiModelProperty(value = "排除航线 当机场999时适用 格式单向|999-SZX/双向|999-CAN/双向|999-HUZ/双向|999-ZUH")
    private String airRouteExcept;

    @ApiModelProperty(value = "航班号限制 0-不限制 1-适用航班号 2-排除航班号")
    private Integer flightNumLimit;

    @ApiModelProperty(value = "航班号(不包含航司二字码) 格式：123/234 多个用英文的/分开")
    private String flightNum;

    @ApiModelProperty(value = "舱位 多个用英文的/分开")
    private String cabin;

    @ApiModelProperty(value = "舱位排除 多个用英文的/分开")
    private String cabinExcept;

    @ApiModelProperty(value = "销售开始日期 格式：yyyy-MM-dd")
    private Date saleStartDate;

    @ApiModelProperty(value = "销售结束日期 格式：yyyy-MM-dd")
    private Date saleEndDate;

    @ApiModelProperty(value = "旅行开始日期 格式：yyyy-MM-dd")
    private Date travelStartDate;

    @ApiModelProperty(value = "旅行结束日期 格式：yyyy-MM-dd")
    private Date travelEndDate;

    @ApiModelProperty(value = "适用起飞时间段 格式 HHMM-HHMM 多个用英文的/分开")
    private String depTimeLimit;

    @ApiModelProperty(value = "班期限制 格式：1/2/3/4/5/6/7")
    private String weekLimit;

    @ApiModelProperty(value = "最早提前出票时限 正整数，大于等于0")
    private Integer beginValidDay;

    @ApiModelProperty(value = "最晚提前出票时限 正整数，大于等于0")
    private Integer latestValidDay;

    @ApiModelProperty(value = "政策工作时间限制 格式：HHMM-HHMM 多个用英文的/分开")
    private String workTimeLimit;

    @ApiModelProperty(value = "产品类型")
    private String productType;

    @ApiModelProperty(value = "报价类型 0-非共享 1-共享 2-全部")
    private Integer rePriceType;

    @ApiModelProperty(value = "行程类型 0-单程 1-往返 9-全部")
    private Integer tripType;

    @ApiModelProperty(value = "最低票面价")
    private BigDecimal minTicketPrice;

    @ApiModelProperty(value = "最高票面价")
    private BigDecimal maxTicketPrice;

    @ApiModelProperty(value = "前返点 单位%")
    private BigDecimal beforeCommission;

    @ApiModelProperty(value = "前返现金")
    private BigDecimal beforeAmount;

    @ApiModelProperty(value = "后返点 单位%")
    private BigDecimal afterCommission;

    @ApiModelProperty(value = "后返现金")
    private BigDecimal afterAmount;

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "版本控制")
    private Integer version;

    @ApiModelProperty(value = "备注说明")
    private String remark;

    @ApiModelProperty(value = "状态：0-正常,1-暂停(挂起),99-无效(删除)")
    private Integer status;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "最后修改时间")
    private Date updateTime;

    @ApiModelProperty(value = "创建人ID")
    private Long createUserId;

    @ApiModelProperty(value = "创建人名称")
    private String createUserName;

    @ApiModelProperty(value = "修改人ID")
    private Long updateUserId;

    @ApiModelProperty(value = "修改人名称")
    private String updateUserName;

    @ApiModelProperty(value = "出票备注")
    private String ticketRemark;


}
