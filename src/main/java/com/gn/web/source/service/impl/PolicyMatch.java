package com.gn.web.source.service.impl;

import com.gn.web.common.constant.DirectConstants;
import com.gn.web.manual.entity.SiteConfig;
import com.gn.web.source.entity.SourceData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

/**
 * 匹配政策 true-匹配;false-不匹配
 */
public class PolicyMatch {

    private final static String FORMAT_PATTERN4="yyyyMMdd";
    private static Logger logger = LoggerFactory.getLogger(PolicyMatch.class);

    /**
     * 数据源
     *
     * @param sourceType
     * @param dataSourceType
     * @return
     */
    public static Boolean matchSourceType(String sourceType, String dataSourceType) {
        if (StringUtils.isEmpty(sourceType)) {
            return true;
        }
        if (sourceType.contains(dataSourceType)) {
            return true;
        }
        return false;
    }


    /**
     * 数据渠道代码
     *
     * @param channel
     * @param dataChannel
     * @return
     */
    public static Boolean matchChannel(String channel, String dataChannel) {
        if (StringUtils.isEmpty(channel)) {
            return true;
        }
        if (channel.contains(dataChannel)) {
            return true;
        }
        return false;
    }


    /**
     * OTA站点
     *
     * @param otaSiteCode
     * @param otaSearch
     * @return
     */
    public static Boolean matchOtaSiteCode(String otaSiteCode, String otaSearch) {
        if (StringUtils.isEmpty(otaSiteCode)) {
            return true;
        }
        if (otaSiteCode.contains(otaSearch)) {
            return true;
        }
        return false;
    }


    /**
     * 航司
     *
     * @param airline
     * @param dataAirline
     * @return
     */
    public static Boolean matchAirline(String airline, String dataAirline) {
        if (StringUtils.isEmpty(airline)) {
            return true;
        }
        if (airline.contains(dataAirline)) {
            return true;
        }
        return false;
    }


    /**
     * 舱位匹配
     *
     * @param cabin
     * @param mlSourceData
     * @return
     */
    public static boolean matchCabin(String cabin, SourceData mlSourceData) {
        if (StringUtils.isEmpty(cabin)) {
            return true;
        }
        return mlSourceData.getSourceDataSegments().stream().filter(Objects::nonNull).anyMatch(f -> !StringUtils.isEmpty(f.getCabin()) && cabin.contains(f.getCabin()));
    }

    /**
     * 航班号
     *
     * @param flightNumber
     * @param mlSourceData
     * @return
     */
    public static boolean matchFlightNumber(String flightNumber, SourceData mlSourceData) {
        if (StringUtils.isEmpty(flightNumber)) {
            return true;
        }
        return mlSourceData.getSourceDataSegments().stream().filter(Objects::nonNull).anyMatch(f -> !StringUtils.isEmpty(f.getCabin()) && flightNumber.contains(f.getCabin()));
    }

    /**
     * 出发地、目的地
     *
     * @param airport
     * @param dataAirport
     * @return
     */
    public static Boolean matchAirport(String airport, String dataAirport) {
        if (StringUtils.isEmpty(airport)) {
            return true;
        }
        if (airport.contains(DirectConstants.AIRPORT_ALL) || airport.contains(dataAirport)) {
            return true;
        }
        return false;
    }

    /**
     * 销售日期或旅行日期范围
     *
     * @param startData
     * @param endDate
     * @return
     */
    public static Boolean matchSaleStartEndDate(LocalDate startData, LocalDate endDate,String newDateStr) {
        LocalDate newDate = null;
        if(StringUtils.isEmpty(newDateStr)){
            newDate = LocalDate.now();
        }else {
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern(FORMAT_PATTERN4);
            newDate = LocalDate.parse(newDateStr, fmt);
        }
        if (startData == null && endDate == null) {
            return true;
        } else if (startData == null && endDate != null && (endDate.isAfter(newDate) || endDate.isEqual(newDate))) {
            return true;
        } else if (startData != null && endDate == null && (startData.isBefore(newDate) || startData.isEqual(newDate))) {
            return true;
        } else if ((endDate.isAfter(newDate) || endDate.isEqual(newDate)) && (startData.isBefore(newDate) || startData.isEqual(newDate))) {
            return true;
        }
        return false;
    }

    /**
     * 班期
     * @param travelStartDate 旅行开始时间 起飞时间
     * @return
     */
    public static Boolean matchWeekLimit(String weekLimit, String travelStartDate) {

        Date newDate = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        try {
            newDate = sdf.parse(travelStartDate);
        }catch (Exception e){
            e.printStackTrace();
            logger.error("旅行日期范围解析异常:{}",travelStartDate);
        }
        Calendar c = Calendar.getInstance();
        c.setTime(newDate);
        int dayForWeek = 0;
        if (c.get(Calendar.DAY_OF_WEEK) == 1) {
            dayForWeek = 7;
        } else {
            dayForWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
        }
        if (StringUtils.isEmpty(weekLimit) || weekLimit.contains(String.valueOf(dayForWeek))) {
            return true;
        }
        return false;
    }


    /**
     * 报价类型
     *
     * @param shareFlag
     * @param dataRePriceType
     * @return
     */
    public static Boolean matchShareFlag(String shareFlag, String dataRePriceType) {
        if (StringUtils.isEmpty(dataRePriceType) || StringUtils.isEmpty(shareFlag)
                || DirectConstants.SHARE_FLAG_ALL.equals(shareFlag) || dataRePriceType.contains(shareFlag)) {
            return true;
        }
        return false;
    }


    /**
     * 起飞时间段
     *
     * @param depTimeStr
     * @param dataDepTimeStr
     * @return
     */
    public static Boolean matchDepTime(String depTimeStr, String dataDepTimeStr) {
        if (StringUtils.isEmpty(depTimeStr) || StringUtils.isEmpty(dataDepTimeStr)) {
            return true;
        }
        String[] dataDepTimes = depTimeStr.split("-");
        LocalTime depTime = LocalTime.parse(dataDepTimeStr);
        dataDepTimes[0] = StringUtils.isEmpty(dataDepTimes[0]) ? "00:00" : dataDepTimes[0];
        dataDepTimes[1] = StringUtils.isEmpty(dataDepTimes[1]) ? "23:59" : dataDepTimes[1];
        LocalTime depStartTime = LocalTime.parse(dataDepTimes[0]);
        LocalTime depEndTime = LocalTime.parse(dataDepTimes[1]);
        if (depTime.compareTo(depStartTime) >= 0 && depEndTime.compareTo(depTime) >= 0) {
            return true;
        }
        return false;
    }


    /**
     * 产品类型（与数据源开关配置做对比）
     *
     * @param productType
     * @param sourceDataSwtich
     * @return
     */
    public static Boolean matchProductType(String productType, SiteConfig sourceDataSwtich, String dataProductType) {
        if (StringUtils.isEmpty(productType) && StringUtils.isEmpty(sourceDataSwtich.getProductType())) {
            return true;
        }
        Boolean swtichFlay =true;
        if(!StringUtils.isEmpty(sourceDataSwtich.getProductType())) {
            swtichFlay = !StringUtils.isEmpty(dataProductType) && ("/"+sourceDataSwtich.getProductType()+"/").contains(("/"+dataProductType+"/"));
        }
        Boolean policyFlay =true;
        if(!StringUtils.isEmpty(productType)){
            policyFlay= !StringUtils.isEmpty(dataProductType) && ("/"+productType+"/").contains(("/"+dataProductType+"/"));
        }
        return swtichFlay && policyFlay;
    }


    /**
     * 提前出票时限+当前时间和起飞时间做对比
     */


    /**
     * 限制航班号
     *
     * @param flightNumLimit
     * @param flightNum
     * @param mlSourceData
     * @return
     */
    public static Boolean matchFlightNumExclude(String flightNumLimit, String flightNum, SourceData mlSourceData) {
        if (StringUtils.isEmpty(flightNumLimit) || DirectConstants.FLIGHT_NUM_LIMIT_ALL.equals(flightNumLimit) ||
                StringUtils.isEmpty(flightNum)) {
            return true;
        }
        if (containFlightNum(flightNum, mlSourceData, flightNumLimit)) {
            return false;
        }
        return true;
    }

    /**
     * 适用航班号
     *
     * @param flightNumLimit
     * @param flightNum
     * @param mlSourceData
     * @return
     */
    public static Boolean matchFlightNumApplicable(String flightNumLimit, String flightNum, SourceData mlSourceData) {
        if (StringUtils.isEmpty(flightNumLimit) || DirectConstants.FLIGHT_NUM_LIMIT_ALL.equals(flightNumLimit) ||
                StringUtils.isEmpty(flightNum)) {
            return true;
        }
        if (containFlightNum(flightNum, mlSourceData, flightNumLimit)) {
            return true;
        }
        return false;
    }


    /**
     * 航班号限制 0-不限制 1-适用航班号 2-排除航班号
     *
     * @param flightNum
     * @param mlSourceData
     * @param type
     * @return
     */
    public static Boolean containFlightNum(String flightNum, SourceData mlSourceData, String type) {
        Map<String, String> dataFlightNum = new ConcurrentHashMap<>();
        Stream.of(flightNum.split("/")).forEach(e -> {
            dataFlightNum.put(e, e);
        });
        if ("2".equals(type)) {
            return mlSourceData.getSourceDataSegments().stream().anyMatch(a -> (
                    !StringUtils.isEmpty(a) && dataFlightNum.containsKey(a)
            ));
        }
        if ("1".equals(type)) {
            return mlSourceData.getSourceDataSegments().stream().allMatch(a -> (
                    !StringUtils.isEmpty(a) && dataFlightNum.containsKey(a)
            ));
        }
        return false;
    }

    /**
     * 票面价区间
     *
     * @param minPrice
     * @param maxPrice
     * @param dataSalePrice
     * @return
     */
    public static Boolean matchSalePrice(BigDecimal minPrice, BigDecimal maxPrice, BigDecimal dataSalePrice) {
        if(dataSalePrice==null || dataSalePrice.compareTo(BigDecimal.ZERO)<=0){
            return false;
        }
        if (minPrice == null && maxPrice == null) {
            return true;
        } else if (minPrice != null && maxPrice== null &&
                minPrice.compareTo(dataSalePrice) <= 0) {
            return true;
        } else if (minPrice == null && maxPrice != null &&
                maxPrice.compareTo(dataSalePrice) >= 0) {
            return true;
        } else if (minPrice != null && maxPrice != null &&
                minPrice.compareTo(dataSalePrice) <= 0 &&
                maxPrice.compareTo(dataSalePrice) >= 0) {
            return true;
        }
        return false;
    }

    /**
     * 行程类型 0-单程 1-往返 9-全部
     *
     * @return
     */
    public static Boolean matchtripType(String tripType, String dataTripType) {
        if (StringUtils.isEmpty(tripType) || DirectConstants.TRIP_TYPE_ALL.equals(tripType) || "0".equals(tripType) ||
                tripType.equals(dataTripType)) {
            return true;
        }
        return false;
    }

    /**
     * 舱位排除
     * @param cabinExcept
     * @param mlSourceData
     * @return
     */
    public static Boolean matchcabinExcept(String cabinExcept, SourceData mlSourceData) {
        if (StringUtils.isEmpty(cabinExcept)) {
            return true;
        }
        return mlSourceData.getSourceDataSegments().stream().filter(Objects::nonNull).anyMatch(f -> !StringUtils.isEmpty(f.getCabin()) && cabinExcept.contains(f.getCabin()));
    }

    /**
     * 排除航线
     * @param depAirPort
     * @param arrAirPort
     * @param airRouteExcept
     * @return
     */
    public static Boolean matchAirRouteExcept(String depAirPort,String arrAirPort,String airRouteExcept){
        if(StringUtils.isEmpty(airRouteExcept)){
            return true;
        }
        Map<String,String> airPortMap = new ConcurrentHashMap<>();
        Stream.of(airRouteExcept.split("/")).forEach(e ->{
            try {
                String[] str = e.split("\\|");
                if (DirectConstants.TWO_WAY.equals(str[0])) {
                    airPortMap.put(str[1], str[1]);
                    String[] airPorts = str[1].split("-");
                    airPortMap.put(airPorts[1] + "-" + airPorts[0], airPorts[1] + "-" + airPorts[0]);
                } else {
                    if(str.length ==1){
                        //XXX-XXX
                        airPortMap.put(str[0],str[0]);
                    }else {
                        //单向|XXX-XXX
                        airPortMap.put(str[1], str[1]);
                    }
                }
            }catch (Exception exception){
                logger.error("航线排除:{},解析异常:{}",airRouteExcept,exception.getMessage());
            }
        });

        //出发机场-抵达机场 出发机场-999 999-抵达机场 999-999
        if((airPortMap.containsKey(depAirPort+"-"+arrAirPort) || airPortMap.containsKey(depAirPort+"-"+DirectConstants.AIRPORT_ALL) ||
                airPortMap.containsKey(DirectConstants.AIRPORT_ALL+"-"+arrAirPort) || airPortMap.containsKey(DirectConstants.AIRPORT_ALL+"-"+DirectConstants.AIRPORT_ALL))){
            return true;
        }
        return false;
    }


    //提前出票时限
    public static Boolean matchValidDay(Integer beginValid,Integer latestValid,String fromDateStr){
        if((beginValid==null || (beginValid != null && beginValid.intValue() ==0)) && (latestValid == null || (latestValid != null && latestValid.intValue() ==0))){
            return true;
        }
        LocalDate newDate= LocalDate.now();
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyyMMdd");
        LocalDate fromDate = LocalDate.parse(fromDateStr, fmt);
        if(beginValid == null && latestValid == null){
            return true;
        }
        if(beginValid != null && (latestValid == null || (latestValid != null && latestValid.intValue() ==0))){
            LocalDate beginValiDay = newDate.plusDays(beginValid);
            if(beginValiDay.compareTo(fromDate)<= 0){
                return true;
            }
        }
        if(beginValid == null && (latestValid != null && latestValid.intValue() !=0)){
            LocalDate latestValidDay = newDate.plusDays(latestValid);
            if(latestValidDay.compareTo(fromDate) >= 0){
                return true;
            }
        }
        if(beginValid != null && (latestValid != null && latestValid.intValue() !=0)){
            LocalDate beginValiDay = newDate.plusDays(beginValid);
            LocalDate latestValidDay = newDate.plusDays(latestValid);
            if(beginValiDay.compareTo(fromDate)<= 0 && latestValidDay.compareTo(fromDate) >= 0){
                return true;
            }
        }
        return false;
    }


    public static List<String> getAirportPriority(String depAirport, String arrAirport){
        List<String> airports = new ArrayList<>(4);
        airports.add(depAirport+"/"+arrAirport);
        airports.add(depAirport+"/"+DirectConstants.AIRPORT_ALL);
        airports.add(DirectConstants.AIRPORT_ALL+"/"+arrAirport);
        airports.add(DirectConstants.AIRPORT_ALL+"/"+DirectConstants.AIRPORT_ALL);
        return airports;
    }

}
