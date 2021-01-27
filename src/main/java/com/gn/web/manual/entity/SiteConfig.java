package com.gn.web.manual.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 政策站点配置
 * </p>
 *
 * @author gwk
 * @since 2021-01-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("site_config")
@ApiModel(value="SiteConfig对象", description="政策站点配置")
public class SiteConfig extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "OTA平台代码 CTRIP-携程 FLIGGY-飞猪 QUNAR-去哪儿 LY-同程 WAP133-航班管家 JIULVXING-就旅行 MEITUAN-美团")
    private String otaCode;

    @ApiModelProperty(value = "OTA平台中文")
    private String otaCodeName;

    @ApiModelProperty(value = "OTA站点代码")
    private String otaSiteCode;

    @ApiModelProperty(value = "OTA站点中文")
    private String otaSiteCodeName;

    @ApiModelProperty(value = "{`api_user_name`,`api_password`,`api_api_key`,`api_channel_id`,`api_result_url`,`do_main_url`}")
    private String apiAccountInfo;

    @ApiModelProperty(value = "最大推送的政策数量单位 万")
    private Integer otaMaxPolicy;

    @ApiModelProperty(value = "版本控制")
    private Integer version;

    @ApiModelProperty(value = "备注说明")
    private String remark;

    @ApiModelProperty(value = "优先排序序号")
    private Integer sortSeq;


}
