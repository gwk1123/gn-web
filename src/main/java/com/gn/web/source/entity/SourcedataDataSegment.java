package com.gn.web.source.entity;

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
 * 政策数据源行程信息
 * </p>
 *
 * @author gwk
 * @since 2021-02-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sourcedata_data_segment")
@ApiModel(value="SourcedataDataSegment对象", description="政策数据源行程信息")
public class SourcedataDataSegment implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "数据源ID")
    private Long sourceDataId;

    @ApiModelProperty(value = "航程序号")
    private Integer journeySequence;

    @ApiModelProperty(value = "航段序号 1-去程 2-回程")
    private Integer segmentSequence;

    @ApiModelProperty(value = "航班号限制 0-不限制 1-适用 2-排除")
    private Integer flightNumLimit;

    @ApiModelProperty(value = "航班号 格式：8541 多个用英文的/分开")
    private String flightNumber;

    @ApiModelProperty(value = "出发机场 999表示全国")
    private String depAirport;

    @ApiModelProperty(value = "到达机场 999表示全国")
    private String arrAirport;

    @ApiModelProperty(value = "舱等")
    private String cabinGrade;

    @ApiModelProperty(value = "子舱位 格式：Y/B 多个用英文的/分开")
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
    private Boolean codeShare;

    @ApiModelProperty(value = "共享航班号限制 0-不限制 1-适用 2-排除")
    private Integer flightNumShareLimit;

    @ApiModelProperty(value = "共享航班号")
    private String shareFlightNumber;

    @ApiModelProperty(value = "共享航空公司")
    private String shareAirline;

    @ApiModelProperty(value = "承运航司 格式：ZH")
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

    @ApiModelProperty(value = "座位数")
    private Integer seatNum;


}
