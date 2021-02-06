package com.gn.web.source.service.impl;

import com.alibaba.fastjson.JSON;
import com.gn.web.common.constant.DirectConstants;
import com.gn.web.manual.entity.OtaRule;
import com.gn.web.source.entity.ChannelVo;
import com.gn.web.source.entity.SourceData;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;

public class OtaRuleFilter {

    /**
     * 对黑名单过滤
     * @param otaPolicyRuleOne
     * @param mlSourceData
     * @return
     */
    public static boolean otaPolicyRuleBlack(List<OtaRule> otaPolicyRuleOne, SourceData mlSourceData) {
        if (CollectionUtils.isEmpty(otaPolicyRuleOne)) {
            return false;
        }
        return otaPolicyRuleOne.stream().filter(Objects::nonNull).anyMatch(a ->
                (StringUtils.isEmpty(a.getAirline()) || a.getAirline().contains(mlSourceData.getAirline())) && //航司
                        (StringUtils.isEmpty(a.getCabin()) || matchCabinAny(a.getCabin(), mlSourceData)) && //舱位
                        (StringUtils.isEmpty(a.getDepAirport()) || DirectConstants.AIRPORT_ALL.equals(a.getDepAirport()) || a.getDepAirport().contains(mlSourceData.getDepCity())) && //出发地
                        (StringUtils.isEmpty(a.getArrAirport()) || DirectConstants.AIRPORT_ALL.equals(a.getArrAirport()) || a.getArrAirport().contains(mlSourceData.getArrCity())) && //目的地
                        (StringUtils.isEmpty(a.getParameter1()) || matchFlightNumberAny(a.getParameter1(), mlSourceData)) && //航班号
                        (StringUtils.isEmpty(a.getSourceType()) || a.getSourceType().contains(mlSourceData.getSourceType())) && //数据源
                        (StringUtils.isEmpty(a.getChannel()) || a.getChannel().contains(mlSourceData.getChannel())) //渠道

        );
    }

    //舱位匹配(黑名单)
    public static boolean matchCabinAny(String cabin, SourceData mlSourceData) {
        return mlSourceData.getSourceDataSegments().stream().filter(Objects::nonNull).anyMatch(f -> ("/"+cabin+"/").contains("/"+f.getCabin()+"/"));
    }

    //航班号(黑名单)
    public static boolean matchFlightNumberAny(String flightNumber, SourceData mlSourceData) {
        return mlSourceData.getSourceDataSegments().stream().filter(Objects::nonNull).anyMatch(f -> !StringUtils.isEmpty(f.getFlightNumber()) && flightNumber.contains(f.getFlightNumber()));
    }

    /**
     * 对白名单过滤
     * @param otaPolicyRuleOne
     * @param mlSourceData
     * @return
     */
    public static boolean otaPolicyRuleWhite(List<OtaRule> otaPolicyRuleOne, SourceData mlSourceData) {
        if (CollectionUtils.isEmpty(otaPolicyRuleOne)) {
            return true;
        }
        return otaPolicyRuleOne.stream().filter(Objects::nonNull).anyMatch(a ->
                (StringUtils.isEmpty(a.getAirline()) || a.getAirline().contains(mlSourceData.getAirline())) && //航司
                        (StringUtils.isEmpty(a.getCabin()) || matchCabinAll(a.getCabin(), mlSourceData)) && //舱位
                        (StringUtils.isEmpty(a.getDepAirport()) || DirectConstants.AIRPORT_ALL.equals(a.getDepAirport()) || a.getDepAirport().contains(mlSourceData.getDepCity())) && //出发地
                        (StringUtils.isEmpty(a.getArrAirport()) || DirectConstants.AIRPORT_ALL.equals(a.getArrAirport()) || a.getArrAirport().contains(mlSourceData.getArrCity())) && //目的地
                        (StringUtils.isEmpty(a.getParameter1()) || matchFlightNumberAll(a.getParameter1(), mlSourceData)) && //航班号
                        (StringUtils.isEmpty(a.getSourceType()) || a.getSourceType().contains(mlSourceData.getSourceType())) && //数据源
                        (StringUtils.isEmpty(a.getChannel()) || a.getChannel().contains(mlSourceData.getChannel())) //渠道

        );
    }

    //舱位匹配(白名单)
    public static boolean matchCabinAll(String cabin, SourceData mlSourceData) {
        return mlSourceData.getSourceDataSegments().stream().filter(Objects::nonNull).allMatch(f -> ("/"+cabin+"/").contains("/"+f.getCabin()+"/"));
    }


    //航班号(白名单)
    public static boolean matchFlightNumberAll(String flightNumber, SourceData mlSourceData) {
        return mlSourceData.getSourceDataSegments().stream().filter(Objects::nonNull).allMatch(f -> !StringUtils.isEmpty(f.getFlightNumber()) && flightNumber.contains(f.getFlightNumber()));
    }

    //数据源过滤
//    public static Boolean filterSourceType(MlSourceDataSwtich sourceDataSwtich, SourceData mlSourceData ){
//        if(StringUtils.isEmpty(sourceDataSwtich.getChannel())){
//            return false;
//        }
//
//        ChannelVo channelVo = JSON.parseObject(sourceDataSwtich.getChannel(), ChannelVo.class);
//            if ((!StringUtils.isEmpty(channelVo.getWebSite()) && channelVo.getWebSite().toUpperCase().contains(mlSourceData.getSourceType())) &&
//                    (StringUtils.isEmpty(channelVo.getWebSiteChannel()) || (!StringUtils.isEmpty(channelVo.getWebSiteChannel()) && channelVo.getWebSiteChannel().toUpperCase().contains(mlSourceData.getChannel())))) {
//                return true;
//            }
//
//            if ((!StringUtils.isEmpty(channelVo.getB2b()) && channelVo.getWebSite().toUpperCase().contains(mlSourceData.getSourceType())) &&
//                    (StringUtils.isEmpty(channelVo.getB2bChannel()) || (!StringUtils.isEmpty(channelVo.getB2bChannel()) && channelVo.getB2bChannel().toUpperCase().contains(mlSourceData.getChannel())))) {
//                return true;
//            }
//
//            if ((!StringUtils.isEmpty(channelVo.getFd()) && channelVo.getFd().toUpperCase().contains(mlSourceData.getSourceType())) &&
//                    (StringUtils.isEmpty(channelVo.getFdChannel()) || (!StringUtils.isEmpty(channelVo.getFdChannel()) && channelVo.getFdChannel().toUpperCase().contains(mlSourceData.getChannel())))) {
//                return true;
//            }
//
//            if ((!StringUtils.isEmpty(channelVo.getMl()) && channelVo.getMl().toUpperCase().contains(mlSourceData.getSourceType())) &&
//                    (StringUtils.isEmpty(channelVo.getMlChannel()) || (!StringUtils.isEmpty(channelVo.getMlChannel()) && channelVo.getMlChannel().toUpperCase().contains(mlSourceData.getChannel())))) {
//                return true;
//            }
//            return false;
//    }


}
