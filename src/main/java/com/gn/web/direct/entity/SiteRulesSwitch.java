package com.gn.web.direct.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 站点规则开关
 * </p>
 *
 * @author gwk
 * @since 2020-09-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="SiteRulesSwitch对象", description="站点规则开关")
public class SiteRulesSwitch extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * gds otsSiteCode
     */
    @ApiModelProperty(value = "对规则开关进行分组")
    private String groupKey;

    /**
     * 规则类型
     */
    @ApiModelProperty(value = "键")
    private String parameterKey;

    @ApiModelProperty(value = "参数名称")
    private String parameterName;

    @ApiModelProperty(value = "值")
    private String parameterValue;

    @ApiModelProperty(value = "备注说明")
    private String remark;



}
