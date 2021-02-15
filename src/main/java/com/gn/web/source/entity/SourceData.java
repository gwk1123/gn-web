package com.gn.web.source.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 政策数据源
 * </p>
 *
 * @author gwk
 * @since 2021-02-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("source_data")
@ApiModel(value="SourceData对象", description="政策数据源")
public class SourceData implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "数据来源 WEBSITE,B2B,FD,NFD,ML-自用")
    private String sourceType;

    @ApiModelProperty(value = "渠道代码 FUYAO-扶摇,NH-南航,FD,NFD,ML")
    private String channel;

    @ApiModelProperty(value = "数据来源方式  0-系统 1-人工")
    private Boolean sourceWay;

    @ApiModelProperty(value = "数据源政策类型  0-不限 1-BSP 2-B2B")
    private Integer policyType;

    @ApiModelProperty(value = "行程类型 0-全部 1-单程 2-往返 3-联程")
    private Integer tripType;

    @ApiModelProperty(value = "销售航司 CA")
    private String airline;

    @ApiModelProperty(value = "销售开始日期 格式：yyyy-MM-dd")
    private LocalDate saleStartDate;

    @ApiModelProperty(value = "销售结束日期 格式：yyyy-MM-dd")
    private LocalDate saleEndDate;

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

    @ApiModelProperty(value = "FD公布运价")
    private BigDecimal publicPrice;

    @ApiModelProperty(value = "数据源销售价格")
    private BigDecimal salePrice;

    private BigDecimal differencePrice;

    @ApiModelProperty(value = "数据源政策返点")
    private BigDecimal policyFd;

    @ApiModelProperty(value = "数据源FD折扣 单位%")
    private BigDecimal fdDiscount;

    @ApiModelProperty(value = "座位数")
    private Integer seatNum;

    @ApiModelProperty(value = "舱位说明")
    private String cabinNote;

    @ApiModelProperty(value = "数据源授权office(平台供应officeNo)")
    private String authOffice;

    @ApiModelProperty(value = "数据源政策批量ID")
    private String batchId;

    @ApiModelProperty(value = "数据源政策ID(平台政策编号)")
    private String policyId;

    @ApiModelProperty(value = "数据源政策备注")
    private String policyRemark;

    @ApiModelProperty(value = "数据源政策最后更新时间")
    private Date policyLastTime;

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

    @ApiModelProperty(value = "旅行日期开始 yyyy-mm-dd")
    private LocalDate travelStartDate;

    @ApiModelProperty(value = "旅行日期结束 yyyy-mm-dd")
    private LocalDate travelEndDate;

    @ApiModelProperty(value = "出发地")
    private String depCity;

    @ApiModelProperty(value = "目的地")
    private String arrCity;

    @ApiModelProperty(value = "方案唯一key，航司-出发地-目的地-舱位1-舱位2")
    private String uniqueKey;

    @TableField(exist = false)
    private List<SourceDataSegment> sourceDataSegments;
}
