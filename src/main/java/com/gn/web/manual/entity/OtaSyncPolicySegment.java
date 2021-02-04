package com.gn.web.manual.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

/**
 * <p>
 * OTA政策同步行程信息
 * </p>
 *
 * @author gwk
 * @since 2021-02-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ota_sync_policy_segment")
@ApiModel(value="OtaSyncPolicySegment对象", description="OTA政策同步行程信息")
public class OtaSyncPolicySegment extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "OTA政策同步ID")
    private Long otaSyncId;

    @ApiModelProperty(value = "航程序号")
    private Integer journeySequence;

    @ApiModelProperty(value = "航段序号 1-去程 2-回程")
    private Integer segmentSequence;

    @ApiModelProperty(value = "航班号限制 0-不限制 1-适用 2-排除")
    private Integer flightNumLimit;

    @ApiModelProperty(value = "航班号 格式：8541 多个用英文/分开")
    private String flightNumber;

    @ApiModelProperty(value = "出发机场 999表示全国")
    private String depAirport;

    @ApiModelProperty(value = "到达机场 999表示全国")
    private String arrAirport;

    @ApiModelProperty(value = "舱等")
    private String cabinGrade;

    @ApiModelProperty(value = "子舱位 格式：Y 单个舱位")
    private String cabin;

    @ApiModelProperty(value = "起飞日期(旅行开始日期) 格式：yyyy-MM-dd")
    private LocalDate depDate;

    @ApiModelProperty(value = "抵达日期(旅行结束日期) 格式：yyyy-MM-dd")
    private LocalDate arrDate;

    @ApiModelProperty(value = "起飞时间 格式：HH:MM")
    private String depTime;

    @ApiModelProperty(value = "到达时间 格式：HH:MM")
    private String arrTime;

    @ApiModelProperty(value = "是否共享航班  0:非共享 1:共享")
    private String codeShare;

    @ApiModelProperty(value = "共享航班号限制 0-不限制 1-适用 2-排除")
    private Integer flightNumShareLimit;

    @ApiModelProperty(value = "共享航班号")
    private String shareFlightNumber;

    @ApiModelProperty(value = "共享航空公司")
    private String shareAirline;

    @ApiModelProperty(value = "承运航司 格式：ZH 单个航司")
    private String airline;

    @ApiModelProperty(value = "机型")
    private String aircraftCode;

    @ApiModelProperty(value = "出发航站楼")
    private String depTerminal;

    @ApiModelProperty(value = "到达航站楼")
    private String arrTerminal;

    @ApiModelProperty(value = "飞行时长")
    private Integer durationMinute;

    @ApiModelProperty(value = "经停机场")
    private String stopAirport;

    @ApiModelProperty(value = "经停分钟数")
    private Integer stopMinute;

    @ApiModelProperty(value = "版本控制")
    private Integer version;

    @ApiModelProperty(value = "备注说明")
    private String remark;

    @ApiModelProperty(value = "唯一健主表外键")
    private String uniqueKey;

    @ApiModelProperty(value = "原舱位")
    private String realCabin;


}
