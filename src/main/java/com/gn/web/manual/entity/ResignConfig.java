package com.gn.web.manual.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 *
 * </p>
 *
 * @author gwk
 * @since 2020-11-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="ResignConfig对象", description="")
public class ResignConfig extends BaseEntity{

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "1-退票；2-改签")
    private String type;

    @ApiModelProperty(value = "航司")
    private String airline;

    @ApiModelProperty(value = "舱位")
    private String cabin;

    @ApiModelProperty(value = "出发地")
    private String depCity;

    @ApiModelProperty(value = "目的地")
    private String arrCity;

    @ApiModelProperty(value = "站点")
    private String otaSiteCode;

}
