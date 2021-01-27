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
 * 全平台调价
 * </p>
 *
 * @author gwk
 * @since 2021-01-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("common_price")
@ApiModel(value="CommonPrice对象", description="全平台调价")
public class CommonPrice extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "数据来源 WEBSITE,B2B,FD,NFD,ML-自用 多个用英文/分开")
    private String sourceType;

    @ApiModelProperty(value = "数据渠道代码 FUYAO-扶摇,NH-南航,FD,NFD,ML 多个用英文/分开")
    private String channel;

    @ApiModelProperty(value = "OTA站点代码 多个用英文的/分开")
    private String otaSiteCode;

    @ApiModelProperty(value = "留钱")
    private BigDecimal retPrice;

    @ApiModelProperty(value = "版本控制")
    private Integer version;

    @ApiModelProperty(value = "备注说明")
    private String remark;


}
