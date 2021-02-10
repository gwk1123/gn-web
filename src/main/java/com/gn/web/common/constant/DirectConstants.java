package com.gn.web.common.constant;

public class DirectConstants {


    /**
     * 正常
     */
    public static final String NORMAL = "0";

    /**
     * 失效
     */
    public static final String FAILURE = "1";

    /**
     * 删除
     */
    public static final String DELETE = "2";

    /**
     * 所有
     */
    public static final String ALL = "ALL";

    /**
     * 行程类型不限
     */
    public static final String  TRIP_TYPE_ALL = "0";

    /**
     * 政策航司为空表示所有
     */
    public static final String POLICY_AIRLINE_ALL = "ALL";

    public static final String  DEDUPLICATION = "web_delete_key";

    public static final String SITE_CONFIG = "site_config";

    public static final String POLICY_GLOBAL = "policy_global";

    public static final String POLICY_INFO = "policy_info";

    public static final String OTA_RULE_0 = "OTA-0"; //规则OTA-0(白名单)

    public static final String OTA_RULE_1 = "OTA-1"; //规则OTA-1(黑名单)

    public static final String OTA_RULE_2 = "OTA-2"; //规则OTA-2(政策类型)

    public static final String OTA_RULE_3 = "OTA-3"; //规则OTA-3(OTA发布价格)

    public static final String OTA_RULE_4 = "OTA-4"; //规则OTA-4(OTA运价规则)

    public static final String OTA_RULE_5 = "OTA-5"; //规则OTA-4(OTA舱位替换)

    public static final String AIRPORT_ALL = "999"; //出发地、目的地是全国

    public static final String FD_DATA = "FD_DATA";

    public static final String NFD_DATA = "NFD_DATA";
    public static final String  SHARE_FLAG_ALL = "2"; //报价类型:全部

    public static final String  FLIGHT_NUM_LIMIT_ALL = "0"; //航班号限制:不限制

    public static final String TWO_WAY = "双向";

    public static final String  ML_COMMON_PRICE = "ML_COMMON_PRICE"; //全平台调价

    public static final String DEL_SYNC_POLICY_URL = "http://192.168.1.30:8888/api/policy/manual/deletePolicy"; //全平台调价

    public static final String OTA_SITE_CODE_LY ="ly";

    public static final String GW = "GW";

    public static final String B2B = "B2B";

    public static final String FD = "FD";

    public static final String NFD = "NFD";

    public static final String WEEK_LIMIT = "1/2/3/4/5/6/7";

}
