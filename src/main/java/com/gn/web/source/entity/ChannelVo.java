package com.gn.web.source.entity;

import lombok.Data;

@Data
public class ChannelVo {

    /**
     * 官网（前端视图vo对象）
     */
    private String webSite;

    /**
     * 官网渠道（前端视图vo对象）
     */
    private String webSiteChannel;

    /**
     * b2b（前端视图vo对象）
     */
    private String b2b;

    /**
     * b2b渠道（前端视图vo对象）
     */
    private String b2bChannel;

    /**
     * fd（前端视图vo对象）
     */
    private String fd;

    /**
     * fd渠道（前端视图vo对象）
     */
    private String fdChannel;

    /**
     * ml（前端视图vo对象）
     */
    private String ml;

    /**
     * ml渠道（前端视图vo对象）
     */
    private String mlChannel;

}
