package com.gn.web.manual.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * <p>
 * 政策基础舱位
 * </p>
 *
 * @author gwk
 * @since 2021-01-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("base_cabin")
@ApiModel(value="BaseCabin对象", description="政策基础舱位")
public class BaseCabin extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "航司")
    private String airline;

    @ApiModelProperty(value = "舱等")
    private String cabinGrade;

    @ApiModelProperty(value = "子舱位")
    private String cabin;

    @ApiModelProperty(value = "生效日期 yyyy-mm-dd，新增时默认取当天")
    private LocalDate startTime;

    @ApiModelProperty(value = "失效日期 yyyy-mm-dd，新增时默认取2099-12-31")
    private LocalDate endTime;

    @ApiModelProperty(value = "版本控制")
    private Integer version;

    @ApiModelProperty(value = "备注说明")
    private String remark;

    @ApiModelProperty(value = "优先排序序号")
    private Integer sortSeq;
}
