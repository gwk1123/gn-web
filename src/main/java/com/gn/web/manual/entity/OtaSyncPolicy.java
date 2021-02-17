package com.gn.web.manual.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * OTA政策同步
 * </p>
 *
 * @author gwk
 * @since 2021-02-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ota_sync_policy")
@ApiModel(value="OtaSyncPolicy对象", description="OTA政策同步")
public class OtaSyncPolicy extends BaseEntity{

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "OTA站点代码")
    private String otaSiteCode;

    @ApiModelProperty(value = "数据来源 WEBSITE,B2B,FD,NFD,ML-自用")
    private String sourceType;

    @ApiModelProperty(value = "渠道代码 FUYAO-扶摇,NH-南航,FD,NFD,ML")
    private String channel;

    @ApiModelProperty(value = "数据源库存ID ")
    private Long sourceId;

    @ApiModelProperty(value = "销售开始日期 格式：yyyy-MM-dd")
    private LocalDate saleStartDate;

    @ApiModelProperty(value = "销售结束日期 格式：yyyy-MM-dd")
    private LocalDate saleEndDate;

    @ApiModelProperty(value = "适用起飞时间段 格式 HHMM-HHMM 多个用英文的/分开")
    private String depTimeLimit;

    @ApiModelProperty(value = "班期限制 格式：1/2/3/4/5/6/7")
    private String weekLimit;

    @ApiModelProperty(value = "最早提前出票时限 正整数，大于等于0")
    private Integer beforeTicketDay;

    @ApiModelProperty(value = "最晚提前出票时限 正整数，大于等于0")
    private Integer latestTicketDay;

    @ApiModelProperty(value = "政策工作时间 格式：HHMM-HHMM 多个用英文的/分开")
    private String workTime;

    @ApiModelProperty(value = "政策废票时间 格式：HHMM-HHMM 多个用英文的/分开")
    private String voidTime;

    @ApiModelProperty(value = "是否共享政策 0:非共享 1:共享")
    private String shareFlag;

    @ApiModelProperty(value = "废票标识 0-可废票 1-不可废票")
    private String voidTicketFlag;

    @ApiModelProperty(value = "是否需要换编码 0-不换编码 1-换编码")
    private Integer changePnrFlag;

    @ApiModelProperty(value = "销售类型 -1:无限制 1:散客 2:团队")
    private Integer saleType;

    @ApiModelProperty(value = "产品类型")
    private String productType;

    @ApiModelProperty(value = "最小乘客人数")
    private Integer minPassNumber;

    @ApiModelProperty(value = "最大乘客人数")
    private Integer maxPassNumber;

    @ApiModelProperty(value = "最小购买年龄")
    private Integer minBuyAge;

    @ApiModelProperty(value = "最大购买年龄")
    private Integer maxBuyAge;

    @ApiModelProperty(value = "座位数")
    private String seatNumn;

    @ApiModelProperty(value = "FD折扣 单位%")
    private BigDecimal fdDiscount;

    @ApiModelProperty(value = "全平台全局规则ID")
    private Long  policyGlobalId;

    @ApiModelProperty(value = "调价ID")
    private Long policyInfoId;

    @ApiModelProperty(value = "站点ID")
    private Long siteConfigId;

    @ApiModelProperty(value = "规则前返点 单位%")
    private BigDecimal ruleBeforeCommission;

    @ApiModelProperty(value = "规则前返现金")
    private BigDecimal ruleBeforeAmount;

    @ApiModelProperty(value = "规则后返点 单位%")
    private BigDecimal ruleAfterCommission;

    @ApiModelProperty(value = "规则后返现金")
    private BigDecimal ruleAfterAmount;

    @ApiModelProperty(value = "FD公布运价")
    private BigDecimal publicPrice;

    @ApiModelProperty(value = "政策原始价格(政策平台供应推送价格)")
    private BigDecimal policyOriginalPrice;

    @ApiModelProperty(value = "政策估算价格(原始价格经过计算返点后的成本价格)")
    private BigDecimal policyEstimatePrice;

    @ApiModelProperty(value = "OTA发布价格(发布到OTA，经过规则返点计算后的价格)")
    private BigDecimal otaPushPrice;

    @ApiModelProperty(value = "OTA发布留钱(发布到OTA，政策估算价与FD价格差)")
    private BigDecimal otaPushRetPrice;

    @ApiModelProperty(value = "OTA发布返点 单位%")
    private BigDecimal otaPushRetCommission;

    @ApiModelProperty(value = "ota政策类型 具体参考系统参数配置")
    private String otaPolicyType;

    @ApiModelProperty(value = "OTA首次发布时间")
    private LocalDateTime otaPushFirstTime;

    @ApiModelProperty(value = "OTA最后发布时间")
    private LocalDateTime otaPushLastTime;

    @ApiModelProperty(value = "OTA发布政策ID")
    private String otaPushPolicyId;

    @ApiModelProperty(value = "OTA政策通知结果时间")
    private LocalDateTime otaNoticeTime;

    @ApiModelProperty(value = "OTA政策发布状态 0-未发布 1-待发布(状态值预留使用) 2-推送中  3-推送成功 4-推送失败 5-发布成功 6-发布失败")
    private Integer otaSyncStatus;

    @ApiModelProperty(value = "OTA政策修改状态 0-未修改 1-待修改(内部政策已修改) 2-推送中  3-推送成功 4-推送失败 5-修改成功 6-修改失败")
    private Integer otaModifyStatus;

    @ApiModelProperty(value = "OTA政策删除状态 0-未删除 1-待删除(内部政策已删除) 3-推送中  3-推送成功 4-推送失败 5-删除成功 6-删除失败")
    private Integer otaDeleteStatus;

    @ApiModelProperty(value = "OTA删除时间")
    private LocalDateTime otaDeleteTime;

    @ApiModelProperty(value = "OTA发布备注")
    private String otaSyncReason;

    @ApiModelProperty(value = "ota扩展字段(适用不同平台的API接口属性)")
    private String otaExtendFileds;

    @ApiModelProperty(value = "退改签说明")
    private String backNote;

    @ApiModelProperty(value = "舱位说明")
    private String cabinNote;

    @ApiModelProperty(value = "出票备注")
    private String ticketRemark;

    @ApiModelProperty(value = "版本控制")
    private Integer version;

    @ApiModelProperty(value = "备注说明")
    private String remark;

    private Integer commonPriceFlag;

    @ApiModelProperty(value = "旅行时间开始 yyyy-mm-dd")
    private LocalDate travelStartDate;

    @ApiModelProperty(value = "旅行时间结束 yyyy-mm-dd")
    private LocalDate travelEndDate;

    @ApiModelProperty(value = "行程类型 0-全部 1-单程 2-往返 3-联程")
    private String tripType;

    @ApiModelProperty(value = "销售航司 CA")
    private String airline;

    @ApiModelProperty(value = "出发地")
    private String depCity;

    @ApiModelProperty(value = "目的地")
    private String arrCity;

    private String uniqueKey;

    @ApiModelProperty(value = "全平台调价ID")
    private String commonPriceIds;

    @ApiModelProperty(value = "OTA推送产品类型")
    private String productStyle;

    @ApiModelProperty(value = "竞价空间")
    private String bidSpace;

    @TableField(exist = false)
    private List<OtaSyncPolicySegment> sourceDataSegments;

}
