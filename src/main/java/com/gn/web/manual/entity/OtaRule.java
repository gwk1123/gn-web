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

    @ApiModelProperty(value = "OTA平台代码 CTRIP-携程 FLIGGY-飞猪 QUNAR-去哪儿 LY-同程 WAP133-航班管家 JIULVXING-就旅行 MEITUAN-美团")
    private String otaCode;

    @ApiModelProperty(value = "OTA平台中文")
    private String otaCname;

    @ApiModelProperty(value = "OTA站点代码")
    private String otaSiteCode;

    @ApiModelProperty(value = "OTA站点中文")
    private String otaSiteCname;

    @ApiModelProperty(value = "航司")
    private String airline;

    @ApiModelProperty(value = "ota规则类型OTA-1,OTA-2")
    private String otaRuleType;

    @ApiModelProperty(value = "出发机场码 多个用英文的/分开 999表示全国")
    private String depAirport;

    @ApiModelProperty(value = "抵达机场码 多个用英文的/分开 999表示全国")
    private String arrAirport;

    @ApiModelProperty(value = "舱位 多个用英文的/分开")
    private String cabin;

    @ApiModelProperty(value = "适用类型 0-白名单 1-黑名单")
    private Boolean applyType;

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

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "版本控制")
    private Integer version;

    @ApiModelProperty(value = "备注说明")
    private String remark;

    @ApiModelProperty(value = "优先排序序号")
    private Integer sortSeq;

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

    @ApiModelProperty(value = "数据来源 WEBSITE,B2B,FD,NFD,ML-自用")
    private String sourceType;

    @ApiModelProperty(value = "渠道代码 FUYAO-扶摇,NH-南航,FD,NFD,ML")
    private String channel;


}
