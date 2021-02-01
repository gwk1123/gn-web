package com.gn.web.manual.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * OTA航线规则
 * </p>
 *
 * @author gwk
 * @since 2021-01-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ota_rule")
@ApiModel(value="OtaRule对象", description="OTA航线规则")
public class OtaRule implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "OTA站点代码")
    private String otaSiteCode;

    @ApiModelProperty(value = "航司")
    private String airline;

    @ApiModelProperty(value = "ota规则类型OTA-1,OTA-2")
    private String ruleType;

    @ApiModelProperty(value = "出发机场码 多个用英文的/分开 999表示全国")
    private String depAirport;

    @ApiModelProperty(value = "抵达机场码 多个用英文的/分开 999表示全国")
    private String arrAirport;

    @ApiModelProperty(value = "舱位 多个用英文的/分开")
    private String cabin;

    @ApiModelProperty(value = "自定义内容一")
    private String parameter1;

    @ApiModelProperty(value = "自定义内容二")
    private String parameter2;

    @ApiModelProperty(value = "自定义内容三")
    private String parameter3;

    @ApiModelProperty(value = "自定义内容四")
    private String parameter4;

    @ApiModelProperty(value = "自定义内容五")
    private String parameter5;

    @ApiModelProperty(value = "自定义内容六")
    private String parameter6;

    @ApiModelProperty(value = "自定义内容七")
    private String parameter7;

    @ApiModelProperty(value = "自定义内容八")
    private String parameter8;

    @ApiModelProperty(value = "版本控制")
    private Integer version;

    @ApiModelProperty(value = "备注说明")
    private String remark;

    @ApiModelProperty(value = "优先排序序号")
    private Integer sortSeq;

    @ApiModelProperty(value = "数据来源 WEBSITE,B2B,FD,NFD,ML-自用")
    private String sourceType;

    @ApiModelProperty(value = "渠道代码 FUYAO-扶摇,NH-南航,FD,NFD,ML")
    private String channel;


}
