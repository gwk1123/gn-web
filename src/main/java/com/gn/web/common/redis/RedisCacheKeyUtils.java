package com.gn.web.common.redis;

import com.gn.web.common.constant.DirectConstants;
import com.gn.web.manual.entity.PolicyGlobal;
import com.gn.web.manual.entity.PolicyInfo;
import com.gn.web.manual.entity.ResignConfig;

public class RedisCacheKeyUtils {

    public static String policyGlobalSetKey( PolicyGlobal policyGlobal){
        StringBuffer  val = new StringBuffer(DirectConstants.POLICY_GLOBAL);
        val.append(":site:");
        val.append(policyGlobal.getOtaSiteCode());
        val.append(":s:");
        val.append(policyGlobal.getSourceType());
        val.append(":a:");
        val.append(policyGlobal.getAirline());
        val.append(":dep:");
        val.append(policyGlobal.getDepAirport());
        val.append(":arr");
        val.append(policyGlobal.getArrAirport());
        return val.toString();
    }

    public static String policyInfoSetKey( PolicyInfo policyInfo){
        StringBuffer  val = new StringBuffer(DirectConstants.POLICY_INFO);
        val.append(":site:");
        val.append(policyInfo.getOtaSiteCode());
        val.append(":s:");
        val.append(policyInfo.getSourceType());
        val.append(":a:");
        val.append(policyInfo.getAirline());
        val.append(":dep:");
        val.append(policyInfo.getDepAirport());
        val.append(":arr");
        val.append(policyInfo.getArrAirport());
        return val.toString();
    }

    public static String resignConfigSetKey( ResignConfig resignConfig){
        StringBuffer  val = new StringBuffer(DirectConstants.RESIGN_CONFIG);
        val.append(":site:");
        val.append(resignConfig.getOtaSiteCode());
        val.append(":a:");
        val.append(resignConfig.getAirline());
        return val.toString();
    }

}
