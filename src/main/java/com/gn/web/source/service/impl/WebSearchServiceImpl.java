package com.gn.web.source.service.impl;

import cn.hutool.core.collection.ConcurrentHashSet;
import cn.hutool.core.date.SystemClock;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gn.web.common.constant.DirectConstants;
import com.gn.web.common.redis.RedisCache;
import com.gn.web.common.utils.GzipUtil;
import com.gn.web.manual.entity.*;
import com.gn.web.manual.mapper.OtaSyncPolicyMapper;
import com.gn.web.manual.mapper.OtaSyncPolicySegmentMapper;
import com.gn.web.manual.mapper.SiteConfigMapper;
import com.gn.web.manual.service.OtaSyncPolicyService;
import com.gn.web.source.entity.*;
import com.gn.web.source.service.WebSearchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class WebSearchServiceImpl implements WebSearchService {

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private ObjectMapper objectMapper;

    @Qualifier("asyncExecutor1")
    @Autowired
    private ThreadPoolTaskExecutor asyncExecutor1;

    @Qualifier("asyncExecutor2")
    @Autowired
    private ThreadPoolTaskExecutor asyncExecutor2;

    @Qualifier("asyncExecutor3")
    @Autowired
    private ThreadPoolTaskExecutor asyncExecutor3;

    @Qualifier("asyncExecutor4")
    @Autowired
    private ThreadPoolTaskExecutor asyncExecutor4;

    @Qualifier("asyncExecutor5")
    @Autowired
    private ThreadPoolTaskExecutor asyncExecutor5;


    @Qualifier("asyncExecutor6")
    @Autowired
    private ThreadPoolTaskExecutor asyncExecutor6;

    @Qualifier("asyncExecutor7")
    @Autowired
    private ThreadPoolTaskExecutor asyncExecutor7;

    @Qualifier("asyncExecutor8")
    @Autowired
    private ThreadPoolTaskExecutor asyncExecutor8;

    @Qualifier("asyncExecutor9")
    @Autowired
    private ThreadPoolTaskExecutor asyncExecutor9;



    private OtaSyncPolicyService otaSyncPolicyService;

    private OtaSyncPolicySegmentMapper otaSyncPolicySegmentMapper;

//    private SiteConfigMapper siteConfigMapper;

    private final static String FORMAT_PATTERN3="yyyy-MM-dd";

    private final static String FORMAT_PATTERN4="yyyyMMdd";

    private static Logger logger = LoggerFactory.getLogger(WebSearchServiceImpl.class);


    /**
     * 将销售日期小于今天的数据更改为待删除
     */
//    public void updateOtaDeleteStatus(){
//        LocalDate localDate = LocalDate.now();
//        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//        String dateStr = localDate.format(fmt);
//        MlSourceDataSwtich sourceDataSwtich = new MlSourceDataSwtich();
//        sourceDataSwtich.setStatus(0);
//        sourceDataSwtich.setSwitchFlag("0");
//        List<MlSourceDataSwtich> mlSourceDataSwtiches = siteConfigMapper.selectList(sourceDataSwtich,null);
//        if(!CollectionUtils.isEmpty(mlSourceDataSwtiches)){
//            mlSourceDataSwtiches.stream().filter(Objects::nonNull).forEach(e ->{
//                String tableName = e.getOtaSiteCode().replaceAll("-", "").toLowerCase();
//                mlOtaPolicyMapper.updateOtaDeleteStatus(tableName,dateStr);
//            });
//        }
//    }


    /**
     * 增量更新
     *
     * @param otaRequest
     * @throws Exception
     */
    @Override
    public void tansformSearch(OtaRequest otaRequest) throws Exception {

        try {

            //根据出发地、目的地和起飞时间将数据改为待删除
            OtaSyncPolicy updateOtaPolicy = new OtaSyncPolicy();
//            updateOtaPolicy.setTableName(otaRequest.getOtaSiteCode().replaceAll("-", "").toLowerCase());
            updateOtaPolicy.setTravelStartDate(parseDate(otaRequest.getFromDate()));
            updateOtaPolicy.setDepCity(otaRequest.getFromAirport());
            updateOtaPolicy.setArrCity(otaRequest.getToAirport());
            List<OtaSyncPolicy> otaSyncPolicies= otaSyncPolicyService.selectOtaSyncPolicys(updateOtaPolicy);
            if (!CollectionUtils.isEmpty(otaSyncPolicies)) {
            List<String> uniqueKeys = otaSyncPolicies.stream().map(OtaSyncPolicy::getUniqueKey).collect(Collectors.toList());
                QueryWrapper<OtaSyncPolicy> queryWrapper= new QueryWrapper<>();
                queryWrapper.lambda().in(OtaSyncPolicy::getUniqueKey,uniqueKeys);
                OtaSyncPolicy u = new OtaSyncPolicy();
                u.setUpdateTime(LocalDateTime.now());
                u.setOtaDeleteStatus(1);
                otaSyncPolicyService.update(u,queryWrapper);
            }

            //获取数据源开关配置
            long t1 = SystemClock.now();
            logger.info("请求时间 t1:{}",t1);
            SiteSearchRequest siteSearchRequest = setDataInformation(otaRequest);
            if (siteSearchRequest.getSiteConfig() == null) {
                logger.info("站点:{},开关已关闭", otaRequest.getOtaSiteCode());
                return;
            }
            if (CollectionUtils.isEmpty(siteSearchRequest.getOtaPublishPrices())) {
                logger.info("站点:{},没有配置OTA政策发布价格", otaRequest.getOtaSiteCode());
                return;
            }
            if (CollectionUtils.isEmpty(siteSearchRequest.getOtaPolicyTypes())) {
                logger.info("站点:{},没有配置OTA政策类型", otaRequest.getOtaSiteCode());
                return;
            }

            String redisKey = this.getRedisKey(otaRequest);
            //根据key获取官网数据
            Map<Object, Object> dataMap = redisCache.getHashMap(redisKey);
            if (dataMap.isEmpty() || dataMap.size() == 0) {
                logger.info("缓存key:{},值为空", redisKey);
                return;
            }

            Long t2 = SystemClock.now();
            logger.info("{}请求时间t12:{}",t1, t2 - t1);
            List<String> dataMapStr = dataMap.values().parallelStream().map(m -> {
                return GzipUtil.unCompress((byte[]) m);
            }).collect(Collectors.toList());

            Long t3 = SystemClock.now();
            logger.info("{}请求时间t23:{}",t1, t3 - t2);
            List<SourceData> siteRoutings = new ArrayList<>();
            List<SourceData> finalSiteRoutings = siteRoutings;
            dataMapStr.parallelStream().forEach(e -> {
                try {
                    List<SourceData> siteRouting = objectMapper.readValue(e, new TypeReference<List<SourceData>>() {
                    });
                    finalSiteRoutings.addAll(siteRouting);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            });

            Long t4 = SystemClock.now();
            logger.info("{}请求时间t34:{}",t1, t4 - t3);
            //对数据进行规则过滤
            siteRoutings = siteRoutings.parallelStream().filter(routing -> {
                return otaRuleFilter(siteSearchRequest, routing);
            }).collect(Collectors.toList());

            //将替换舱位规则转成map
            Map<String, List<OtaRule>> realCabinMap = null;
            if(!CollectionUtils.isEmpty(siteSearchRequest.getOtaRealCabins())) {
                realCabinMap = siteSearchRequest.getOtaRealCabins().stream().filter(Objects::nonNull).collect(Collectors.groupingBy(OtaRule::getAirline));
            }

            Long t5 = SystemClock.now();
            logger.info("{}请求时间t45:{},otaPolicyList ={},",t1, t5 - t4,siteRoutings.size());

            //批量获取全平台调价
            Map<String,List<CommonPrice>> commonPriceMap= getCommonPriceMap(siteRoutings);
            Long tt = SystemClock.now();
            logger.info("{}批量获取全平台政策前返耗时:{}",t1,tt-t5);

            //批量获取全平台政策前返
//            List<Object> qo=getCommonPolicies("QF-ALL", "MLCommonPolicy",siteRoutings);
            Long tq = SystemClock.now();
            logger.info("{}批量获取全平台政策前返耗时:{}",t1,tq-tt);
//            Map<String, List<PolicyGlobal>> beforeCommonPolicieMap = null;
//            if(!CollectionUtils.isEmpty(qo)) {
//                beforeCommonPolicieMap = qo.stream().filter(Objects::nonNull).map(m -> objectMapper.convertValue(m, MlCommonPolicy.class)).collect(Collectors.groupingBy(MlCommonPolicy::getAirline));
//            }
            Long tqz = SystemClock.now();
            logger.info("{}转化全平台政策前返耗时:{}",t1,tqz-tq);
            //批量获取全平台政策后返
            List<Object> ho = getCommonPolicies("HF-ALL", "MLCommonPolicy", siteRoutings);
            Long th = SystemClock.now();
            logger.info("{}批量获取全平台政策后返耗时:{}",t1,th-tqz);
            Map<String, List<PolicyGlobal>> afterCommonPolicieMap = null;
            if(!CollectionUtils.isEmpty(ho)) {
                afterCommonPolicieMap = ho.stream().filter(Objects::nonNull).map(m -> objectMapper.convertValue(m, PolicyGlobal.class)).collect(Collectors.groupingBy(PolicyGlobal::getAirline));
            }
            Long thz = SystemClock.now();
            logger.info("{}转化全平台政策后返耗时:{}",t1,thz-th);
            //批量获取调价管理政策
            List<Object> otaObjects = getCommonPolicies(otaRequest.getOtaSiteCode(), "MlOtaPolicyPrice", siteRoutings);
            Long to = SystemClock.now();
            logger.info("{}批量获取调价管理政策耗时:{}",t1,to-thz);
            Map<String, List<PolicyInfo>> otaPolicyPriceMap = null;
            if(!CollectionUtils.isEmpty(otaObjects)) {
                otaPolicyPriceMap = otaObjects.stream().filter(Objects::nonNull).map(m -> objectMapper.convertValue(m, PolicyInfo.class)).collect(Collectors.groupingBy(PolicyInfo::getAirline));
            }
            Long toz = SystemClock.now();
            logger.info("{}转化调价管理政策耗时:{}",t1,toz-to);

            Map<String, List<PolicyGlobal>> finalAfterCommonPolicieMap = afterCommonPolicieMap;
//            Map<String, List<MlCommonPolicy>> finalBeforeCommonPolicieMap = beforeCommonPolicieMap;
            Map<String, List<PolicyInfo>> finalOtaPolicyPriceMap = otaPolicyPriceMap;
            Map<String, List<OtaRule>> finalRealCabinMap = realCabinMap;
            List<OtaSyncPolicy> otaPolicyList = siteRoutings.parallelStream().filter(Objects::nonNull).map(routing -> {

                try {
                    Long t50 = SystemClock.now();
                    //获取政策类型
                    OtaRule otaPolicyType = getRulePolicyType(siteSearchRequest, routing);
                    //获取ota发布价格选择
                    OtaRule otaPublishPrice = getOtaPublishPrice(siteSearchRequest, routing);
                    Long t51 = SystemClock.now();
                    logger.info("{}请求时间t50-51:{}",t1, t51 - t50);
                    if (otaPublishPrice == null || otaPolicyType == null) {
                        logger.info("站点:{},政策类型或ota发布价格选择为空", otaRequest.getOtaSiteCode());
                        return null;
                    }

                    Long t52 = SystemClock.now();
                    //获取全平台政策
//                    List<MlCommonPrice> commonPrices = findCommonPrice(siteSearchRequest, routing, otaRequest);
                    List<CommonPrice> commonPrices = null;
                    if (commonPriceMap != null && commonPriceMap.size() !=0) {
                        commonPrices = getCommonPrice(commonPriceMap.get(routing.getSourceType()), routing, otaRequest);
                    }

                    //全平台政策后返
                    List<PolicyGlobal> afterCommonPolicies = null;
                    if (finalAfterCommonPolicieMap != null && finalAfterCommonPolicieMap.size() !=0) {
                        afterCommonPolicies = finalAfterCommonPolicieMap.get(routing.getAirline());
                    }
                    PolicyGlobal policyGlobal = getCommonPolicie(afterCommonPolicies, routing,
                            otaRequest, siteSearchRequest);
                    Long t54 = SystemClock.now();
                    logger.info("{}请求时间t53-54:{}",t1,t54 - t52);

                    //获取调价管理政策
                    List<PolicyInfo> policyInfos = null;
                    if (finalOtaPolicyPriceMap != null && finalOtaPolicyPriceMap.size() !=0) {
                        policyInfos = finalOtaPolicyPriceMap.get(routing.getAirline());
                    }
                    PolicyInfo policyInfo = getOtaPolicyPric(policyInfos, routing,
                            otaRequest, siteSearchRequest);

                    Long t55 = SystemClock.now();
                    logger.info("{}请求时间t54-55:{}",t1, t55 - t54);

                    //过滤没有匹配的政策
                    if (CollectionUtils.isEmpty(commonPrices) &&  policyGlobal == null && policyInfo == null) {
                        return null;
                    }

                    this.siteSearchRequestInfo(siteSearchRequest, commonPrices, policyGlobal, policyInfo);

                    //计算价格
                    RebateMoney rebateMoney = calculationRebateMoney(commonPrices, policyGlobal, policyInfo);

                    //舱位替换
                    realCabinRule(finalRealCabinMap, routing);

                    //转成同步政策
                    OtaSyncPolicy mlOtaPolicy = transformOtaPolicy(routing, rebateMoney, otaPublishPrice, otaPolicyType, otaRequest, siteSearchRequest);
                    Long t56 = SystemClock.now();
                    logger.info("{}请求时间t55-56:{}",t1, t56 - t55);
                    return mlOtaPolicy;
                } catch (Exception e) {
                    e.printStackTrace();
                    logger.error("转化异常:{}", e.getMessage());
                    return null;
                }
            }).filter(Objects::nonNull).collect(Collectors.toList());

            Long t6 = SystemClock.now();
            logger.info("{}请求时间t56:{}",t1, t6 - t5);

            if (CollectionUtils.isEmpty(otaPolicyList)) {
                return;
            }
            //取最低价
            List<OtaSyncPolicy> otaPolicylowests = lowestPrice(otaPolicyList);

            //同程需要根据运价规则进行处理
            List<OtaSyncPolicy> otaPolicys = dealWithPriceRuleByLy(otaPolicylowests, siteSearchRequest, otaRequest);

            //同步政策批量保存到数据库
//            mlOtaPolicyMapper.insertBatch(otaPolicys, otaRequest.getOtaSiteCode().replaceAll("-", "").toLowerCase());
//            List<MlOtaPolicySegment> otaPolicySegmentList = new ArrayList<>();
//            otaPolicys.stream()
//                    .forEach(m -> {
//                        m.getSourceDataSegments().stream().forEach(i -> {
//                            i.setId(null);
//                            i.setOtaSyncId(m.getId());
//                            i.setCodeShare(false);
//                            i.setVersion(1);
//                            i.setFlightNumber(StringUtils.isEmpty(i.getFlightNumber())?"":
//                                    i.getFlightNumber().replace(i.getAirline(),""));
//                            i.setFlightNumLimit(StringUtils.isEmpty(i.getFlightNumber())?0:1);
//                            i.setUniqueKey(m.getUniqueKey());
//                            i.setTableName(otaRequest.getOtaSiteCode().replaceAll("-", "").toLowerCase());
//                            i.setCreateTime(LocalDateTime.now().toString());
//                            i.setUpdateTime(LocalDateTime.now().toString());
//                            i.setCreateUserName("backPotMan");
//                            i.setUpdateUserName("backPotMan");
//                        });
//                        otaPolicySegmentList.addAll(m.getSourceDataSegments());
//                    });
//            mlOtaPolicySegmentMapper.insertBatch(otaPolicySegmentList, otaRequest.getOtaSiteCode().replaceAll("-", "").toLowerCase());
////            logger.info("线程信息:{}", Thread.currentThread());
//            Long t7 = SystemClock.now();
//            logger.info("{}请求时间t67:{}",t1, t7 - t6);
        } catch (Exception ex) {
            logger.error("异常:{}", ex);
        } finally {
            redisCache.removeHashKey(DirectConstants.DEDUPLICATION, deduplicationKey(otaRequest));
        }

    }

    /**
     * 去重的key
     * @param otaRequest
     * @return
     */
    public static String deduplicationKey(OtaRequest otaRequest){
        StringBuffer stringBuffer=new StringBuffer();
        stringBuffer.append(otaRequest.getFromAirport()).append(otaRequest.getToAirport()).append(otaRequest.getFromDate());
        return stringBuffer.toString();
    }


    public List<OtaSyncPolicy> lowestPrice(List<OtaSyncPolicy> otaPolicys) {
        Map<String, OtaSyncPolicy> routingMap = new ConcurrentHashMap<>();
        otaPolicys.parallelStream().forEach(m -> {
            String sourceType = DirectConstants.FD.equals(m.getSourceType()) ? DirectConstants.FD : DirectConstants.GW;
            String key = m.getSourceDataSegments().stream().sorted(Comparator.comparing(OtaSyncPolicySegment::getJourneySequence))
                    .map(segment -> (
                            sourceType +
                                    segment.getAirline() +
                                    segment.getCabin() +
                                    (StringUtils.isEmpty(segment.getFlightNumber()) ? "" : segment.getFlightNumber()) +
                                    segment.getDepAirport() +
                                    segment.getArrAirport() +
                                    formatDate(segment.getDepDate()) +
                                    (StringUtils.isEmpty(segment.getDepTime()) ? "" : segment.getDepTime())
//                                    segment.getArrDate()
                    )).collect(Collectors.joining("-"));
            if (routingMap.containsKey(key)) {
                //当存在 ，判断价格是否小于原来的方案
                if (m.getOtaPushPrice().compareTo(routingMap.get(key).getOtaPushPrice()) < 0) {
                    //替换原来的方案
                    m.setUniqueKey((key+m.getOtaPolicyType()).replaceAll("-","").replaceAll(":","")
                    .replaceAll(" ",""));
                    routingMap.put(key, m);
                }
            } else {
                m.setUniqueKey((key+m.getOtaPolicyType()).replaceAll("-","").replaceAll(":","")
                        .replaceAll(" ",""));
                routingMap.put(key, m);
            }
        });
        return routingMap.values().stream().collect(Collectors.toList());
    }


    /**
     * 将Date转成String线程安全
     * @param date
     * @return
     */
    public static String formatDate(LocalDate date) {
        return date.format(DateTimeFormatter.ofPattern(FORMAT_PATTERN3));
    }

    /**
     * 将String转成Date线程安全
     * @param str
     * @return
     */
    public static LocalDate parseDate(String str){
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern(FORMAT_PATTERN4);
        LocalDate localDate = LocalDate.parse(str, fmt);
        return localDate;
    }


    /**
     * 转化同步政策的实体
     *
     * @param sourceData
     * @param rebateMoney
     * @param otaPolicyRule
     * @return
     */
    private OtaSyncPolicy transformOtaPolicy(SourceData sourceData, RebateMoney rebateMoney, OtaRule otaPolicyRule,
                                           OtaRule otaPolicyType, OtaRequest otaRequest, SiteSearchRequest siteSearchRequest) throws IOException {

        OtaSyncPolicy otaPolicy = JSON.parseObject(JSON.toJSONString(sourceData), OtaSyncPolicy.class);
        //根据政策类型设置字段
        otaPolicy.setOtaPolicyType(otaPolicyType.getParameter1()); //ota政策类型 具体参考系统参数配置
        otaPolicy.setProductStyle(otaPolicyType.getParameter6()); //OTA推送产品类型
        PushPolicyType pushPolicyType = new PushPolicyType();
        pushPolicyType.setPayFlag(otaPolicyType.getParameter2());
        pushPolicyType.setPnrFlag(otaPolicyType.getParameter3());
        pushPolicyType.setPatFlag(otaPolicyType.getParameter4());
        pushPolicyType.setChildFlag(otaPolicyType.getParameter5());
        otaPolicy.setOtaExtendFileds(JSON.toJSONString(pushPolicyType));
//        otaPolicy.setTableName(otaRequest.getOtaSiteCode().replaceAll("-", "").toLowerCase());

        //当数据源为FD或NFD时，官网价取FD公布运价
        BigDecimal price = (DirectConstants.FD.equals(sourceData.getSourceType()) || DirectConstants.NFD.equals(sourceData.getSourceType())) ?
                sourceData.getPublicPrice() : sourceData.getSalePrice();

        //计算价格
        BigDecimal commission = (rebateMoney.getRuleAfterCommission() == null ? new BigDecimal(0) : rebateMoney.getRuleAfterCommission())
                .add(rebateMoney.getRuleBeforeCommission() == null ? new BigDecimal(0) : rebateMoney.getRuleBeforeCommission());
        BigDecimal money = (rebateMoney.getRuleAfterAmount() == null ? new BigDecimal(0) : rebateMoney.getRuleAfterAmount())
                .add(rebateMoney.getRuleBeforeAmount() == null ? new BigDecimal(0) : rebateMoney.getRuleBeforeAmount());
        BigDecimal policyPrice = (price.multiply(new BigDecimal(1).subtract(commission.divide(new BigDecimal(100))))).add(money);
        policyPrice =policyPrice.setScale(0, BigDecimal.ROUND_UP);

        //当选择数据源价格，OTA发布留钱、OTA发布返点需要赋值 政策估算价格
        if ("2".equals(otaPolicyRule.getParameter6())) {
            otaPolicy.setOtaPushPrice(price);
//            otaPolicy.setOtaPushRetPrice(commission);
//            otaPolicy.setOtaPushRetCommission(money);
//            otaPolicy.setOtaPushRetPrice(money.subtract(sourceData.getSalePrice().multiply(commission.divide(new BigDecimal(100)))));
            otaPolicy.setOtaPushRetPrice(policyPrice.subtract(price));
            otaPolicy.setOtaPushRetCommission(new BigDecimal(0));
        }
        //当选择FD价格，OTA发布留钱、OTA发布返点需要赋值并且当FD的价格为空取数据源价格且保十的倍数 政策估算价格
        if ("1".equals(otaPolicyRule.getParameter6())) {
            BigDecimal fdPrice = sourceData.getPublicPrice();
            if(StringUtils.isEmpty(sourceData.getPublicPrice()) || sourceData.getPublicPrice().compareTo(sourceData.getSalePrice()) <0){
                fdPrice = sourceData.getSalePrice();
            }
            if (sourceData.getPublicPrice() == null || sourceData.getPublicPrice().compareTo(BigDecimal.ZERO) <= 0) {
                fdPrice = roundUp(price);
            }
            otaPolicy.setOtaPushPrice(fdPrice);
//            otaPolicy.setOtaPushRetPrice(money.subtract(fdPrice.multiply(commission.divide(new BigDecimal(100)))));
            otaPolicy.setOtaPushRetPrice(policyPrice.subtract(fdPrice));
            otaPolicy.setOtaPushRetCommission(new BigDecimal(0));
        }
        //当选择政策后的价格，OTA发布留钱、OTA发布返点不需要赋值
        if ("3".equals(otaPolicyRule.getParameter6())) {
            otaPolicy.setOtaPushPrice(policyPrice.setScale(0, BigDecimal.ROUND_UP));
            otaPolicy.setOtaPushRetPrice(new BigDecimal(0));
            otaPolicy.setOtaPushRetCommission(new BigDecimal(0));
        }
        otaPolicy.setPolicyEstimatePrice(policyPrice.setScale(0, BigDecimal.ROUND_UP));
        otaPolicy.setRuleBeforeCommission(rebateMoney.getRuleBeforeCommission());
        otaPolicy.setRuleBeforeAmount(rebateMoney.getRuleBeforeAmount());
        otaPolicy.setRuleAfterCommission(rebateMoney.getRuleAfterCommission());
        otaPolicy.setRuleAfterAmount(rebateMoney.getRuleAfterAmount());
        otaPolicy.setPolicyOriginalPrice(sourceData.getSalePrice());

        otaPolicy.setId(null);
        otaPolicy.setSourceId(sourceData.getId());//数据源Id
        otaPolicy.setTripType(otaRequest.getTripType());//行程类型
        otaPolicy.setOtaSiteCode(otaRequest.getOtaSiteCode()); //站点
        otaPolicy.setPolicyGlobalId(siteSearchRequest.getPolicyGlobal() != null ?
                siteSearchRequest.getPolicyGlobal().getId() : null);//全平台全局规则ID

        otaPolicy.setPolicyInfoId(siteSearchRequest.getPolicyInfo() != null ?
                siteSearchRequest.getPolicyInfo().getId() : null);//调价ID
        otaPolicy.setCommonPriceFlag(CollectionUtils.isEmpty(siteSearchRequest.getCommonPrices()) ? 0 : 1);//全平台政策匹配到其中一条就复制1，否0

        if (!CollectionUtils.isEmpty(siteSearchRequest.getCommonPrices())) {
            String commonPriceIds = siteSearchRequest.getCommonPrices().stream().filter(Objects::nonNull).map(m -> m.getId().toString()).collect(Collectors.joining(","));
            otaPolicy.setCommonPriceIds(commonPriceIds);
        }

        //设置FD公布运价
        if (otaPolicy.getPublicPrice() == null) {
            otaPolicy.setPublicPrice(roundUp(price));
        }

        this.policyTransformOta(siteSearchRequest, otaPolicy);

        String dateTimeStr = "";
        if(!StringUtils.isEmpty(sourceData.getTravelStartDate())){
            String[] timeStr = sourceData.getTravelStartDate().toString().split("-");
            if(timeStr.length ==3){
                dateTimeStr = timeStr[1] + timeStr[2];
            }
        }
        String uuidStr = (sourceData.getChannel()+"#" +sourceData.getDepCity()+"-" + sourceData.getArrCity()+"#"+ (otaRequest.getOtaSiteCode().replaceAll("-", ""))).toUpperCase() +
                "-"+sourceData.getId() + dateTimeStr;

        //同程站点只允许字母和数字 (渠道代码+出发地+目的地+唯一)
        if(otaRequest.getOtaSiteCode().contains(DirectConstants.OTA_SITE_CODE_LY)){
            otaPolicy.setOtaPushPolicyId((uuidStr.replaceAll("#","").replaceAll("-","")));
        }else {
            otaPolicy.setOtaPushPolicyId(uuidStr);
        }
        String remarkInfoStr = remarkInfo(siteSearchRequest, sourceData, otaPolicy.getOtaPushPrice());
        otaPolicy.setTicketRemark(remarkInfoStr);//出票备注
        otaPolicy.setMinPassNumber(1);//最小乘客人数
        otaPolicy.setMaxPassNumber(9);//最大乘客人数
        otaPolicy.setBidSpace(siteSearchRequest.getPolicyInfo()== null?"":siteSearchRequest.getPolicyInfo().getBidSpace()); //竞价空间
        //otaPolicy.setMinBuyAge();//最小购买年龄
        //otaPolicy.setMaxBuyAge();//最大购买年龄
        otaPolicy.setSeatNumn(sourceData.getSeatNum() == null ? (DirectConstants.FD.equals(sourceData.getSourceType()) || DirectConstants.NFD.equals(sourceData.getSourceType())) ? "9" : ""
                : String.valueOf(sourceData.getSeatNum()));//座位数
        otaPolicy.setOtaSyncStatus(0);//OTA政策发布状态 0-未发布 1-待发布(状态值预留使用) 2-推送中  3-推送成功 4-推送失败 5-发布成功 6-发布失败
        otaPolicy.setOtaModifyStatus(0);//OTA政策修改状态 0-未修改 1-待修改(内部政策已修改) 2-推送中  3-推送成功 4-推送失败 5-修改成功 6-修改失败
        otaPolicy.setOtaDeleteStatus(0);//OTA政策删除状态 0-未删除 1-待删除(内部政策已删除) 3-推送中  3-推送成功 4-推送失败 5-删除成功 6-删除失败
        otaPolicy.setStatus("0");
        otaPolicy.setCreateTime(LocalDateTime.now());
        otaPolicy.setUpdateTime(LocalDateTime.now());
        otaPolicy.setCreateUserName("backPotMan");
        otaPolicy.setUpdateUserName("backPotMan");
        return otaPolicy;
    }


    //将政策中的数据赋值到同步政策中
    public void policyTransformOta(SiteSearchRequest siteSearchRequest, OtaSyncPolicy otaPolicy) {
        //设置政策工作时间
        String workTime = null;
        if (siteSearchRequest.getPolicyInfo() != null && !StringUtils.isEmpty(siteSearchRequest.getPolicyInfo().getWorkTimeLimit())) {
            workTime = siteSearchRequest.getPolicyInfo().getWorkTimeLimit();
        } else if (siteSearchRequest.getPolicyGlobal() != null && !StringUtils.isEmpty(siteSearchRequest.getPolicyGlobal().getWorkTimeLimit())) {
            workTime = siteSearchRequest.getPolicyGlobal().getWorkTimeLimit();
        }
        otaPolicy.setWorkTime(workTime);
        //起飞时间段
        String depTimeLimit = null;
        if (siteSearchRequest.getPolicyInfo() != null && !StringUtils.isEmpty(siteSearchRequest.getPolicyInfo().getDepTimeLimit())) {
            depTimeLimit = siteSearchRequest.getPolicyInfo().getDepTimeLimit();
        } else if (siteSearchRequest.getPolicyGlobal() != null && !StringUtils.isEmpty(siteSearchRequest.getPolicyGlobal().getDepTimeLimit())) {
            depTimeLimit = siteSearchRequest.getPolicyGlobal().getDepTimeLimit();
        }
        otaPolicy.setDepTimeLimit(depTimeLimit);
        //班期限制(如果没有则传:1234567)
        String weekLimit = DirectConstants.WEEK_LIMIT;
        if (siteSearchRequest.getPolicyInfo() != null && !StringUtils.isEmpty(siteSearchRequest.getPolicyInfo().getWeekLimit())) {
            weekLimit = siteSearchRequest.getPolicyInfo().getWeekLimit();
        } else if (siteSearchRequest.getPolicyGlobal() != null && !StringUtils.isEmpty(siteSearchRequest.getPolicyGlobal().getWeekLimit())) {
            weekLimit = siteSearchRequest.getPolicyGlobal().getWeekLimit();
        }
        otaPolicy.setWeekLimit(weekLimit);
        //最早提前出票时限
        Integer beginValidDay = null;
        if (siteSearchRequest.getPolicyInfo() != null && siteSearchRequest.getPolicyInfo().getBeginValidDay() != null && siteSearchRequest.getPolicyInfo().getBeginValidDay().intValue() != 0) {
            beginValidDay = siteSearchRequest.getPolicyInfo().getBeginValidDay();
        } else if (siteSearchRequest.getPolicyGlobal() != null && siteSearchRequest.getPolicyGlobal().getBeginValidDay() != null && siteSearchRequest.getPolicyGlobal().getBeginValidDay().intValue() != 0) {
            beginValidDay = siteSearchRequest.getPolicyGlobal().getBeginValidDay();
        }
        otaPolicy.setBeforeTicketDay(beginValidDay);
        //最晚提前出票时限
        Integer latestValidDay = null;
        if (siteSearchRequest.getPolicyInfo() != null && siteSearchRequest.getPolicyInfo().getLatestValidDay() != null && siteSearchRequest.getPolicyInfo().getLatestValidDay().intValue() != 0) {
            latestValidDay = siteSearchRequest.getPolicyInfo().getLatestValidDay();
        } else if (siteSearchRequest.getPolicyGlobal() != null && siteSearchRequest.getPolicyGlobal().getLatestValidDay() != null && siteSearchRequest.getPolicyGlobal().getLatestValidDay().intValue() != 0) {
            latestValidDay = siteSearchRequest.getPolicyGlobal().getLatestValidDay();
        }
        otaPolicy.setLatestTicketDay(latestValidDay);
        //销售时间FD的数据处理
        if(DirectConstants.FD.equals(otaPolicy.getSourceType())){
            otaPolicy.setSaleStartDate(LocalDate.now());
            otaPolicy.setSaleEndDate(otaPolicy.getTravelStartDate());
        }
    }


    public String remarkInfo(SiteSearchRequest siteSearchRequest, SourceData sourceData, BigDecimal otaPushPrice) {
        StringBuffer stringBuffer = new StringBuffer();
        //票面价
        BigDecimal price = (DirectConstants.FD.equals(sourceData.getSourceType()) || DirectConstants.NFD.equals(sourceData.getSourceType())) ?
                sourceData.getPublicPrice() : sourceData.getSalePrice();
        stringBuffer.append(siteSearchRequest.getPolicyInfo() == null ? "" : siteSearchRequest.getPolicyInfo().getTicketRemark() == null ? "" : siteSearchRequest.getPolicyInfo().getTicketRemark()).append("#P:" + price);
        //全平台政策前后返、留钱
        PolicyGlobal beforeCommonPolicie = siteSearchRequest.getPolicyGlobal();
        BigDecimal beforeRebate = new BigDecimal(0);
        BigDecimal beforeMoney = new BigDecimal(0);

        BigDecimal platformRebate = new BigDecimal(0);
        BigDecimal platformMoney = new BigDecimal(0);
        if (beforeCommonPolicie != null) {
            beforeRebate = (beforeCommonPolicie.getBeforeCommission() == null ? new BigDecimal(0) : beforeCommonPolicie.getBeforeCommission())
                    .add(beforeCommonPolicie.getAfterCommission() == null ? new BigDecimal(0) : beforeCommonPolicie.getAfterCommission());
            beforeMoney = (beforeCommonPolicie.getBeforeAmount() == null ? new BigDecimal(0) : beforeCommonPolicie.getBeforeAmount())
                    .add(beforeCommonPolicie.getAfterAmount() == null ? new BigDecimal(0) : beforeCommonPolicie.getAfterAmount());
        }

        platformRebate = beforeRebate;
        platformMoney = beforeMoney;
        stringBuffer.append("#Q:").append(platformRebate);
        stringBuffer.append("#Q:").append(platformMoney);

        //全平台调价
        List<CommonPrice> commonPrices = siteSearchRequest.getCommonPrices();
        BigDecimal commonPrice = new BigDecimal(0);
        if (!CollectionUtils.isEmpty(commonPrices)) {
            commonPrice = commonPrices.stream().filter(Objects::nonNull).map(CommonPrice::getRetPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
        }
        stringBuffer.append("#T:").append("0.0");
        stringBuffer.append("#T:").append(commonPrice);

        //ota调价管理
        PolicyInfo otaPolicyPrice = siteSearchRequest.getPolicyInfo();
        BigDecimal otaRebate = new BigDecimal(0);
        BigDecimal otaMoney = new BigDecimal(0);
        if (otaPolicyPrice != null) {
            otaRebate = (otaPolicyPrice.getBeforeCommission() == null ? new BigDecimal(0) : otaPolicyPrice.getBeforeCommission())
                    .add(otaPolicyPrice.getAfterCommission() == null ? new BigDecimal(0) : otaPolicyPrice.getAfterCommission());
            otaMoney = (otaPolicyPrice.getBeforeAmount() == null ? new BigDecimal(0) : otaPolicyPrice.getBeforeAmount())
                    .add(otaPolicyPrice.getAfterAmount() == null ? new BigDecimal(0) : otaPolicyPrice.getAfterAmount());
        }
        stringBuffer.append("#Q:").append(otaRebate);
        stringBuffer.append("#Q:").append(otaMoney);

        //ota销售价格
        stringBuffer.append("#X:").append(otaPushPrice);
        //渠道
        stringBuffer.append("#").append(sourceData.getChannel());
//        //政策说明优先后返，在前返；只取其中一条
//        String qfRemarks = siteSearchRequest.getBeforeCommonPolicie()== null?"":siteSearchRequest.getBeforeCommonPolicie().getTicketRemark() == null?
//                "":siteSearchRequest.getBeforeCommonPolicie().getTicketRemark();
//        String hfRemarks = siteSearchRequest.getAfterCommonPolicie()== null?"":siteSearchRequest.getAfterCommonPolicie().getTicketRemark() == null?
//                "":siteSearchRequest.getAfterCommonPolicie().getTicketRemark();
//        String policyRemarks = StringUtils.isEmpty(hfRemarks)?qfRemarks:hfRemarks;
//        stringBuffer.append("#").append(policyRemarks);
        return stringBuffer.toString();
    }

    /**
     * 进十取整
     *
     * @param publicPrice
     * @return
     */
    public BigDecimal roundUp(BigDecimal publicPrice) {
        return publicPrice.multiply(new BigDecimal(0.1)).setScale(0, BigDecimal.ROUND_UP).multiply(new BigDecimal(10));
    }


    /**
     * 获取返点和留钱
     *
     * @param commonPrices
     * @param policyGlobal
     * @param otaPolicyPrice
     * @return
     */
    public RebateMoney calculationRebateMoney(List<CommonPrice> commonPrices, PolicyGlobal policyGlobal, PolicyInfo otaPolicyPrice) {
        BigDecimal beforeCommission = null;
        BigDecimal beforeMoney = null;
        BigDecimal afterCommission = null;
        BigDecimal afterMoney = null;
        //全局
        if (!CollectionUtils.isEmpty(commonPrices)) {
            beforeMoney = commonPrices.stream().filter(Objects::nonNull).map(CommonPrice::getRetPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
        }
        //前返
        if (policyGlobal != null) {
            beforeCommission = (beforeCommission == null ? new BigDecimal(0) : beforeCommission).add(policyGlobal.getBeforeCommission());
            beforeMoney = (beforeMoney == null ? new BigDecimal(0) : beforeMoney).add(policyGlobal.getBeforeAmount());
            afterCommission = (afterCommission == null ? new BigDecimal(0) : afterCommission).add(policyGlobal.getAfterCommission());
            afterMoney = (afterMoney == null ? new BigDecimal(0) : afterMoney).add(policyGlobal.getAfterAmount());
        }
        //ota调价
        if (otaPolicyPrice != null) {
            beforeCommission = (beforeCommission == null ? new BigDecimal(0) : beforeCommission).add(otaPolicyPrice.getBeforeCommission());
            beforeMoney = (beforeMoney == null ? new BigDecimal(0) : beforeMoney).add(otaPolicyPrice.getBeforeAmount());
            afterCommission = (afterCommission == null ? new BigDecimal(0) : afterCommission).add(otaPolicyPrice.getAfterCommission());
            afterMoney = (afterMoney == null ? new BigDecimal(0) : afterMoney).add(otaPolicyPrice.getAfterAmount());
        }
        RebateMoney rebateMoney = new RebateMoney();
        rebateMoney.setRuleBeforeCommission(beforeCommission);
        rebateMoney.setRuleBeforeAmount(beforeMoney);
        rebateMoney.setRuleAfterCommission(afterCommission);
        rebateMoney.setRuleAfterAmount(afterMoney);
        return rebateMoney;
    }


    public String getRedisKey(OtaRequest otaRequest) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(otaRequest.getFromAirport());
        stringBuffer.append(otaRequest.getToAirport());
        stringBuffer.append(otaRequest.getFromDate());
        if ("2".equals(otaRequest.getTripType())) {
            stringBuffer.append(otaRequest.getRetDate());
        }
        return stringBuffer.toString();
    }


    public SiteSearchRequest setDataInformation(OtaRequest otaRequest) {
        SiteSearchRequest siteSearchRequest = new SiteSearchRequest();
        SiteConfig mlSourceDataSwtich = (SiteConfig) redisCache.getHash(DirectConstants.SITE_CONFIG, otaRequest.getOtaSiteCode());
        List<OtaRule> otaPolicyRuleWhites = (List<OtaRule>) redisCache.getHashByValues(otaRequest.getOtaSiteCode() + "-" + DirectConstants.OTA_RULE_0);
        List<OtaRule> otaPolicyRuleBlacks = (List<OtaRule>) redisCache.getHashByValues(otaRequest.getOtaSiteCode() + "-" + DirectConstants.OTA_RULE_1);
        List<OtaRule> otaPolicyTypes = (List<OtaRule>) redisCache.getHashByValues(otaRequest.getOtaSiteCode() + "-" + DirectConstants.OTA_RULE_2);
        List<OtaRule> otaPublishPrices = (List<OtaRule>) redisCache.getHashByValues(otaRequest.getOtaSiteCode() + "-" + DirectConstants.OTA_RULE_3);
        List<OtaRule> otaPriceRules = (List<OtaRule>) redisCache.getHashByValues(otaRequest.getOtaSiteCode() + "-" + DirectConstants.OTA_RULE_4);
        List<OtaRule> otaRealCabins = (List<OtaRule>) redisCache.getHashByValues(otaRequest.getOtaSiteCode() + "-" + DirectConstants.OTA_RULE_5);
        List<String> airportPrioritys = PolicyMatch.getAirportPriority(otaRequest.getFromAirport(),otaRequest.getToAirport());
        siteSearchRequest.setAirportPrioritys(airportPrioritys);
        siteSearchRequest.setSiteConfig(mlSourceDataSwtich);
        siteSearchRequest.setOtaRuleWhites(otaPolicyRuleWhites);
        siteSearchRequest.setOtaRuleBlacks(otaPolicyRuleBlacks);
        siteSearchRequest.setOtaPolicyTypes(otaPolicyTypes);
        siteSearchRequest.setOtaPublishPrices(otaPublishPrices);
        siteSearchRequest.setOtaPriceRules(otaPriceRules);
        siteSearchRequest.setOtaRealCabins(otaRealCabins);
        return siteSearchRequest;
    }

    public void siteSearchRequestInfo(SiteSearchRequest siteSearchRequest, List<CommonPrice> commonPrices, PolicyGlobal policyGlobal, PolicyInfo policyInfo) {
        siteSearchRequest.setCommonPrices(commonPrices);
        siteSearchRequest.setPolicyGlobal(policyGlobal);
        siteSearchRequest.setPolicyInfo(policyInfo);
    }


    /**
     * 官网数据进行过滤 true表示对该数据进行过滤，false表示对该数据进行保留
     *
     * @param siteSearchRequest
     * @param mlSourceData
     * @return
     */
    public static boolean otaRuleFilter(SiteSearchRequest siteSearchRequest, SourceData mlSourceData) {

        if (!CollectionUtils.isEmpty(siteSearchRequest.getOtaRuleBlacks())) {
            //黑名单优先
            List<OtaRule> otaRuleBlacks = siteSearchRequest.getOtaRuleBlacks();
            if (OtaRuleFilter.otaPolicyRuleBlack(otaRuleBlacks, mlSourceData)) {
                return false;
            }
        }
        if (!CollectionUtils.isEmpty(siteSearchRequest.getOtaRuleWhites())) {
            //白名单
            List<OtaRule> otaRuleWhites = siteSearchRequest.getOtaRuleWhites();
            if (!OtaRuleFilter.otaPolicyRuleWhite(otaRuleWhites, mlSourceData)) {
                return false;
            }
        }

        //根据数据源开关过滤
//        if(!OtaRuleFilter.filterSourceType(siteSearchRequest.getSiteConfig(),mlSourceData)){
//            return false;
//        }
        return true;
    }

    //获取全局
    public List<CommonPrice> findCommonPrice(SiteSearchRequest siteSearchRequest, SourceData mlSourceData, OtaRequest otaRequest){

        List<CommonPrice> commonPriceList = redisCache.getHashByValues(DirectConstants.ML_COMMON_PRICE + "-" + mlSourceData.getSourceType());
        commonPriceList = Optional.ofNullable(commonPriceList).orElse(new ArrayList<>()).stream().filter(Objects::nonNull)
                .filter(f -> {
                    return matchCommonPrice(f, mlSourceData, otaRequest);
                }).collect(Collectors.toList());
        return commonPriceList;
    }


    public Map<String,List<CommonPrice>> getCommonPriceMap(List<SourceData> mlSourceData){
        List<String> sourceTypes = mlSourceData.stream().filter(Objects::nonNull)
                .filter(distinctByKey(SourceData::getSourceType)).map(m -> m.getSourceType()).collect(Collectors.toList());
        List<CommonPrice> commonPriceList = redisCache.getBatchHash(DirectConstants.ML_COMMON_PRICE,sourceTypes);
        if(!CollectionUtils.isEmpty(commonPriceList)){
            return commonPriceList.stream().filter(Objects::nonNull).collect(Collectors.groupingBy(CommonPrice::getSourceType));
        }
        return null;
    }


    private static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Map<Object,Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }

    //获取全局
    public List<CommonPrice> getCommonPrice(List<CommonPrice> commonPriceList, SourceData mlSourceData, OtaRequest otaRequest){
        if(CollectionUtils.isEmpty(commonPriceList)){
            return null;
        }

        commonPriceList = Optional.ofNullable(commonPriceList).orElse(new ArrayList<>()).stream().filter(Objects::nonNull)
                .filter(f -> {
                    return matchCommonPrice(f, mlSourceData, otaRequest);
                }).collect(Collectors.toList());
        return commonPriceList;
    }



    /**
     * 获取前返点政策
     *
     * @param keyType      QF-ALL :前返;HF-ALL :后返
     * @param mlSourceData
     * @return
     */
    public List<Object> findCommonPolicies(String keyType, String name, SourceData mlSourceData){
        String depArrkey = getPolicyKey(keyType, mlSourceData.getSourceType(), mlSourceData.getAirline(), mlSourceData.getDepCity(), mlSourceData.getArrCity());//机场-机场
        String depAllkey = getPolicyKey(keyType, mlSourceData.getSourceType(), mlSourceData.getAirline(), mlSourceData.getDepCity(), DirectConstants.AIRPORT_ALL);//机场-999
        String allArrkey = getPolicyKey(keyType, mlSourceData.getSourceType(), mlSourceData.getAirline(), DirectConstants.AIRPORT_ALL, mlSourceData.getArrCity());//999-机场
        String allAllkey = getPolicyKey(keyType, mlSourceData.getSourceType(), mlSourceData.getAirline(), DirectConstants.AIRPORT_ALL, DirectConstants.AIRPORT_ALL);//999-999
        Set<Object> keyIds = new HashSet<>();
        keyIds.addAll(redisCache.getSet(depArrkey));
        keyIds.addAll(redisCache.getSet(depAllkey));
        keyIds.addAll(redisCache.getSet(allArrkey));
        keyIds.addAll(redisCache.getSet(allAllkey));
        if (!CollectionUtils.isEmpty(keyIds)) {
            return (List<Object>) redisCache.getHashMap(keyType + "-" + name, keyIds);
        }
        return null;
    }


    /**
     * 批量获取政策
     * @param keyType QF-ALL :前返;HF-ALL :后返
     * @param name
     * @param siteRoutings
     * @return
     */
    public List<Object> getCommonPolicies(String keyType, String name,List<SourceData> siteRoutings){
        if(CollectionUtils.isEmpty(siteRoutings)){
            return null;
        }
        Set<String> allKey =new ConcurrentHashSet<>();
        siteRoutings.stream().filter(Objects::nonNull).forEach(mlSourceData ->{
            String depArrkey = getPolicyKey(keyType, mlSourceData.getSourceType(), mlSourceData.getAirline(), mlSourceData.getDepCity(), mlSourceData.getArrCity());//机场-机场
            String depAllkey = getPolicyKey(keyType, mlSourceData.getSourceType(), mlSourceData.getAirline(), mlSourceData.getDepCity(), DirectConstants.AIRPORT_ALL);//机场-999
            String allArrkey = getPolicyKey(keyType, mlSourceData.getSourceType(), mlSourceData.getAirline(), DirectConstants.AIRPORT_ALL, mlSourceData.getArrCity());//999-机场
            String allAllkey = getPolicyKey(keyType, mlSourceData.getSourceType(), mlSourceData.getAirline(), DirectConstants.AIRPORT_ALL, DirectConstants.AIRPORT_ALL);//999-999
            allKey.add(depArrkey);
            allKey.add(depAllkey);
            allKey.add(allArrkey);
            allKey.add(allAllkey);
        });
        return redisCache.batchHash(keyType + "-" + name,allKey);
    }


    public static String getPolicyKey(String keyType, String sourceType, String airline, String depAirport, String arrAirport) {
        StringBuilder val = new StringBuilder(keyType);
        val.append(":");
        val.append(sourceType);
        val.append("-");
        val.append(airline);
        val.append("-");
        val.append(depAirport);
        val.append("-");
        val.append(arrAirport);
        return val.toString();
    }


    public Boolean matchCommonPrice(CommonPrice mlCommonPrice, SourceData mlSourceData, OtaRequest otaRequest) {
        if (!PolicyMatch.matchChannel(mlCommonPrice.getChannel(), mlSourceData.getChannel())) {
            return false;
        }
        if (!PolicyMatch.matchOtaSiteCode(mlCommonPrice.getOtaSiteCode(), otaRequest.getOtaSiteCode())) {
            return false;
        }
        return true;
    }

    /**
     * 匹配前返
     *
     * @param commonPolicies
     * @param routing
     * @param otaRequest
     * @param siteSearchRequest
     * @return
     */
    public static PolicyGlobal getCommonPolicie(List<PolicyGlobal> commonPolicies, SourceData routing,
                                                  OtaRequest otaRequest, SiteSearchRequest siteSearchRequest) {

        if (CollectionUtils.isEmpty(commonPolicies)) {
            return null;
        }
        PolicyGlobal mlCommonPolicy = null;
        Optional<PolicyGlobal> optional = commonPolicies.parallelStream().filter(Objects::nonNull).filter(f -> {
            return matchComparePrices(f, otaRequest, routing,
                    siteSearchRequest);
        }).min((x, y) -> comparePrices(x, y, routing));
        if (optional.isPresent()) {
            mlCommonPolicy = optional.get();
        }
        return mlCommonPolicy;
    }


    /**
     * 匹配前后返全平台政策
     *
     * @param mlCommonPolicy
     * @return
     */
    public static Boolean matchComparePrices(PolicyGlobal mlCommonPolicy, OtaRequest otaRequest, SourceData routing,
                                             SiteSearchRequest siteSearchRequest) {
        //获取全平台政策是根据数据源、航司、出发地、目的地获取的，所以不需要放入匹配条件中
        //ota站点
        if (!PolicyMatch.matchOtaSiteCode(mlCommonPolicy.getOtaSiteCode(), otaRequest.getOtaSiteCode())) {
            logger.info("ota站点不匹配");
            return false;
        }
        //渠道
        if (!PolicyMatch.matchSourceType(mlCommonPolicy.getSourceType(), routing.getSourceType())) {
            logger.info("数据源");
            return false;
        }

        //渠道
        if (!PolicyMatch.matchChannel(mlCommonPolicy.getChannel(), routing.getChannel())) {
            logger.info("渠道");
            return false;
        }
        //舱位
        if (!PolicyMatch.matchCabin(mlCommonPolicy.getCabin(), routing)) {
            logger.info("舱位");
            return false;
        }
        //销售日期
        if (!PolicyMatch.matchSaleStartEndDate(mlCommonPolicy.getSaleStartDate(), mlCommonPolicy.getSaleEndDate(), null)) {
            logger.info("销售日期");
            return false;
        }
        //旅行日期范围
        if (!PolicyMatch.matchSaleStartEndDate(mlCommonPolicy.getTravelStartDate(), mlCommonPolicy.getTravelEndDate(), otaRequest.getFromDate())) {
            logger.info("旅行日期范围");
            return false;
        }
        //报价类型
        if (!PolicyMatch.matchShareFlag(String.valueOf(mlCommonPolicy.getRePriceType()), routing.getShareFlag())) {
            logger.info("报价类型");
            return false;
        }
        //起飞时间段
//        Map<Integer, MlSourceDataSegment> sourceDataSegmentMap = routing.getSourceDataSegments().stream().collect(
//                Collectors.toMap(MlSourceDataSegment::getJourneySequence, MlSourceDataSegment -> MlSourceDataSegment));
//        if (!PolicyMatch.matchDepTime(mlCommonPolicy.getDepTimeLimit(), sourceDataSegmentMap.get(1).getDepTime())) {
//            logger.info("起飞时间段");
//            return false;
//        }
        //产品类型
        if (!PolicyMatch.matchProductType(mlCommonPolicy.getProductType(), siteSearchRequest.getSiteConfig(),routing.getProductType())) {
            logger.info("产品类型");
            return false;
        }
        //提前出票时限
        if (!PolicyMatch.matchValidDay(mlCommonPolicy.getBeginValidDay(), mlCommonPolicy.getLatestValidDay(), otaRequest.getFromDate())) {
            logger.info("提前出票时限");
            return false;
        }
        //票面价
        BigDecimal price = (DirectConstants.FD.equals(routing.getSourceType()) || DirectConstants.NFD.equals(routing.getSourceType())) ?
                routing.getPublicPrice() : routing.getSalePrice();
        if (!PolicyMatch.matchSalePrice(mlCommonPolicy.getMinTicketPrice(), mlCommonPolicy.getMaxTicketPrice(), price)) {
            logger.info("票面价");
            return false;
        }
        //航班号限制
        if (!PolicyMatch.matchFlightNumApplicable(String.valueOf(mlCommonPolicy.getFlightNumLimit()), mlCommonPolicy.getFlightNum(), routing)) {
            logger.info("航班号限制");
            return false;
        }
        //班期限制
        if (!PolicyMatch.matchWeekLimit(mlCommonPolicy.getWeekLimit(), otaRequest.getFromDate())) {
            logger.info("班期限制");
            return false;
        }
        //航程类型
        if (!PolicyMatch.matchtripType(String.valueOf(mlCommonPolicy.getTripType()), otaRequest.getTripType())) {
            logger.info("航程类型");
            return false;
        }
        //排除舱位
        if (!PolicyMatch.matchcabinExcept(mlCommonPolicy.getCabinExcept(), routing)) {
            logger.info("排除舱位");
            return false;
        }
        //排除航线
        if (!PolicyMatch.matchAirRouteExcept(otaRequest.getFromAirport(), otaRequest.getToAirport(), mlCommonPolicy.getAirRouteExcept())) {
            logger.info("排除航线");
            return false;
        }
        return true;
    }


    /**
     * 前返后返的价格
     *
     * @param o1
     * @param o2
     * @return
     */
    public static int comparePrices(PolicyGlobal o1, PolicyGlobal o2, SourceData routing) {
        BigDecimal price = (DirectConstants.FD.equals(routing.getSourceType()) || DirectConstants.NFD.equals(routing.getSourceType())) ?
                routing.getPublicPrice() : routing.getSalePrice();

        BigDecimal commission1 = o1.getBeforeCommission().add(o1.getAfterCommission());
        BigDecimal amount1 = o1.getBeforeAmount().add(o1.getBeforeAmount());
        BigDecimal commission2 = o2.getBeforeCommission().add(o2.getAfterCommission());
        BigDecimal amount2 = o2.getBeforeAmount().add(o2.getBeforeAmount());
        BigDecimal totalPrice1 = (price.multiply(new BigDecimal(1).subtract(commission1.divide(new BigDecimal(100))))).add(amount1);
        BigDecimal totalPrice2 = (price.multiply(new BigDecimal(1).subtract(commission2.divide(new BigDecimal(100))))).add(amount2);
        return totalPrice1.subtract(totalPrice2).setScale(0, BigDecimal.ROUND_UP).intValue();
    }


    /**
     * 计算Ota调价政策
     *
     * @param otaPolicyPrices
     * @param routing
     * @param otaRequest
     * @param siteSearchRequest
     * @return
     */
    public static PolicyInfo getOtaPolicyPric(List<PolicyInfo> otaPolicyPrices, SourceData routing,
                                                    OtaRequest otaRequest, SiteSearchRequest siteSearchRequest) {

        if (CollectionUtils.isEmpty(otaPolicyPrices)) {
            return null;
        }
        PolicyInfo mlOtaPolicyPrice = null;
        Optional<PolicyInfo> optional = otaPolicyPrices.parallelStream().filter(Objects::nonNull).filter(f -> {
            return matchOtaPolicyPrice(f, otaRequest, routing,
                    siteSearchRequest);
        }).min((x, y) -> compareOtaPrices(x, y, routing));
        if (optional.isPresent()) {
            mlOtaPolicyPrice = optional.get();
        }
        return mlOtaPolicyPrice;
    }

    /**
     * 前返后返的价格
     *
     * @param o1
     * @param o2
     * @return
     */
    public static int compareOtaPrices(PolicyInfo o1, PolicyInfo o2, SourceData routing) {
        BigDecimal price = (DirectConstants.FD.equals(routing.getSourceType()) || DirectConstants.NFD.equals(routing.getSourceType())) ?
                routing.getPublicPrice() : routing.getSalePrice();

        BigDecimal commission1 = o1.getBeforeCommission().add(o1.getAfterCommission());
        BigDecimal amount1 = o1.getBeforeAmount().add(o1.getBeforeAmount());
        BigDecimal commission2 = o2.getBeforeCommission().add(o2.getAfterCommission());
        BigDecimal amount2 = o2.getBeforeAmount().add(o2.getBeforeAmount());
        BigDecimal totalPrice1 = (price.multiply(new BigDecimal(1).subtract(commission1.divide(new BigDecimal(100))))).add(amount1);
        BigDecimal totalPrice2 = (price.multiply(new BigDecimal(1).subtract(commission2.divide(new BigDecimal(100))))).add(amount2);
        return totalPrice1.subtract(totalPrice2).setScale(0, BigDecimal.ROUND_UP).intValue();
    }


    /**
     * 匹配ota调价管理
     *
     * @param mlOtaPolicyPrice
     * @param otaRequest
     * @param routing
     * @param siteSearchRequest
     * @return
     */
    public static Boolean matchOtaPolicyPrice(PolicyInfo mlOtaPolicyPrice, OtaRequest otaRequest, SourceData routing,
                                              SiteSearchRequest siteSearchRequest) {

        //渠道
        if (!PolicyMatch.matchSourceType(mlOtaPolicyPrice.getSourceType(), routing.getSourceType())) {
            logger.info("数据源");
            return false;
        }
        //渠道
        if (!PolicyMatch.matchChannel(mlOtaPolicyPrice.getChannel(), routing.getChannel())) {
            return false;
        }
        //舱位
        if (!PolicyMatch.matchCabin(mlOtaPolicyPrice.getCabin(), routing)) {
            return false;
        }
        //销售日期
        if (!PolicyMatch.matchSaleStartEndDate(mlOtaPolicyPrice.getSaleStartDate(), mlOtaPolicyPrice.getSaleEndDate(), null)) {
            return false;
        }
        //旅行日期范围
        if (!PolicyMatch.matchSaleStartEndDate(mlOtaPolicyPrice.getTravelStartDate(), mlOtaPolicyPrice.getTravelEndDate(), otaRequest.getFromDate())) {
            return false;
        }
        //报价类型
        if (!PolicyMatch.matchShareFlag(String.valueOf(mlOtaPolicyPrice.getRePriceType()), routing.getShareFlag())) {
            return false;
        }
        //起飞时间段
//        Map<Integer, MlSourceDataSegment> sourceDataSegmentMap = routing.getSourceDataSegments().stream().collect(
//                Collectors.toMap(MlSourceDataSegment::getJourneySequence, MlSourceDataSegment -> MlSourceDataSegment));
//        if (!PolicyMatch.matchDepTime(mlOtaPolicyPrice.getDepTimeLimit(), sourceDataSegmentMap.get(1).getDepTime())) {
//            return false;
//        }
        //产品类型
        if (!PolicyMatch.matchProductType(mlOtaPolicyPrice.getProductType(), siteSearchRequest.getSiteConfig(),routing.getProductType())) {
            return false;
        }
        //提前出票时限
        if (!PolicyMatch.matchValidDay(mlOtaPolicyPrice.getBeginValidDay(), mlOtaPolicyPrice.getLatestValidDay(), otaRequest.getFromDate())) {
            return false;
        }
        //票面价
        if (!PolicyMatch.matchSalePrice(mlOtaPolicyPrice.getMinTicketPrice(), mlOtaPolicyPrice.getMaxTicketPrice(), routing.getSalePrice())) {
            return false;
        }
        //航班号限制
        if (!PolicyMatch.matchFlightNumApplicable(String.valueOf(mlOtaPolicyPrice.getFlightNumLimit()), mlOtaPolicyPrice.getFlightNum(), routing)) {
            return false;
        }
        //班期限制
        if (!PolicyMatch.matchWeekLimit(mlOtaPolicyPrice.getWeekLimit(), otaRequest.getFromDate())) {
            return false;
        }
        //航程类型
        if (!PolicyMatch.matchtripType(String.valueOf(mlOtaPolicyPrice.getTripType()), otaRequest.getTripType())) {
            return false;
        }
        //排除舱位
        if (!PolicyMatch.matchcabinExcept(mlOtaPolicyPrice.getCabinExcept(), routing)) {
            return false;
        }
        //排除航线
        if (!PolicyMatch.matchAirRouteExcept(otaRequest.getFromAirport(), otaRequest.getToAirport(), mlOtaPolicyPrice.getAirRouteExcept())) {
            return false;
        }
        return true;
    }


    public OtaRule getRulePolicyType(SiteSearchRequest siteSearchRequest, SourceData mlSourceData) {
        //根据站点和规则类型取出
        AtomicReference<OtaRule> rulePolicyType = new AtomicReference();
        List<String> airportPrioritys = siteSearchRequest.getAirportPrioritys();
        for(String airStr : airportPrioritys){
            String[] airArray  = StringUtils.split(airStr,"/");
            siteSearchRequest.getOtaPolicyTypes().stream()
                    .filter(Objects::nonNull).map(m -> {
                if (StringUtils.isEmpty(m.getDepAirport())) {
                    m.setDepAirport(DirectConstants.AIRPORT_ALL);
                }
                if (StringUtils.isEmpty(m.getArrAirport())) {
                    m.setArrAirport(DirectConstants.AIRPORT_ALL);
                }
                return m;
            }).forEach(f -> {
                if ((f.getDepAirport().contains(airArray[0])) &&
                        (f.getArrAirport().contains(airArray[1])) &&
                        (StringUtils.isEmpty(f.getSourceType()) || f.getSourceType().contains(mlSourceData.getSourceType())) &&
                        (StringUtils.isEmpty(f.getChannel()) || f.getChannel().contains(mlSourceData.getChannel())) &&
                        (StringUtils.isEmpty(f.getAirline()) || f.getAirline().contains(mlSourceData.getAirline()))) {

                    if (rulePolicyType.get() == null) {
                        rulePolicyType.set(f);
                        if(!StringUtils.isEmpty(f.getAirline())){
                            return ;
                        }
                    }
                    if(rulePolicyType.get() != null && !StringUtils.isEmpty(f.getAirline())){
                        rulePolicyType.set(f);
                        return ;
                    }

                }
            });
        }
        return rulePolicyType.get();
    }

    public OtaRule getOtaPublishPrice(SiteSearchRequest siteSearchRequest, SourceData mlSourceData) {
        OtaRule otaPolicyRule = null;
        Optional<OtaRule> optional = siteSearchRequest.getOtaPublishPrices().stream()
                .filter(f -> mlSourceData.getSourceType().equals(f.getSourceType())).findFirst();
        if (optional.isPresent()) {
            otaPolicyRule = optional.get();
        }
        return otaPolicyRule;
    }


    /**
     * 同程需要根据运价规则进行处理
     * @param otaPolicys
     * @param siteSearchRequest
     * @param otaRequest
     * @return
     */
    public List<OtaSyncPolicy> dealWithPriceRuleByLy(List<OtaSyncPolicy> otaPolicys,SiteSearchRequest siteSearchRequest,OtaRequest otaRequest){
        List<OtaSyncPolicy> mlOtaPolicies = new ArrayList<>();
        if(otaRequest.getOtaSiteCode().contains(DirectConstants.OTA_SITE_CODE_LY)){
            List<OtaRule> otaPriceRules = siteSearchRequest.getOtaPriceRules();
            if(!CollectionUtils.isEmpty(otaPriceRules)){
                Map<String,String> priceRuleMap = otaPriceRules.stream().filter(Objects::nonNull).collect(Collectors.toMap(OtaRule::getAirline,OtaRule::getParameter2));
                List<OtaSyncPolicy> finalMlOtaPolicies = mlOtaPolicies;
                otaPolicys.stream().filter(Objects::nonNull).forEach(e ->{
                    String value=  priceRuleMap.get(e.getAirline());
                    if(!StringUtils.isEmpty(value)){
                        String[] priceValues = value.split("/");
                        OtaSyncPolicy e1 = JSON.parseObject(JSON.toJSONString(e),OtaSyncPolicy.class);
                        e1.setOtaPushPolicyId(e.getOtaPushPolicyId()+0);
                        finalMlOtaPolicies.add(e1);
                        IntStream.range(1,priceValues.length+1).forEach(i ->{
                            OtaSyncPolicy o = JSON.parseObject(JSON.toJSONString(e),OtaSyncPolicy.class);
                            PushPolicyType pushPolicyType = JSON.parseObject(o.getOtaExtendFileds(),PushPolicyType.class);
                            pushPolicyType.setPriceRule(priceValues[i-1]);
                            o.setUniqueKey(e.getUniqueKey()+priceValues[i-1]);
                            o.setOtaPushPolicyId(e.getOtaPushPolicyId()+i);
                            o.setOtaExtendFileds(JSON.toJSONString(pushPolicyType));
                            finalMlOtaPolicies.add(o);
                        });
                    }
                });
            }
        }
        if(!CollectionUtils.isEmpty(mlOtaPolicies)){
           return mlOtaPolicies;
        }
        return otaPolicys;
    }

    /**
     * 舱位替换
     */
    public void realCabinRule(Map<String,List<OtaRule>> realCabinMap,SourceData mlSourceData){
        if(!CollectionUtils.isEmpty(realCabinMap) && realCabinMap.size() >0){
            mlSourceData.getSourceDataSegments().stream().filter(Objects::nonNull).forEach(e ->{
                List<OtaRule> values = realCabinMap.get(e.getAirline());
                if(!CollectionUtils.isEmpty(values)){
                    Optional<OtaRule> mlOtaPolicyRule = values.stream().filter(Objects::nonNull).filter(f -> ("/"+f.getParameter3()+"/").contains(("/"+e.getCabin()+"/"))).findFirst();
                    if(mlOtaPolicyRule.isPresent()) {
                        String c = e.getCabin();
                        e.setCabin(mlOtaPolicyRule.get().getParameter4());
                        e.setRealCabin(c);
                    }
                }
            });
        }
    }


    @Async("asyncExecutor1")
    public void searchAsync1(OtaRequest otaRequest) throws Exception {
        tansformSearch(otaRequest);
    }
    @Async("asyncExecutor2")
    public void searchAsync2(OtaRequest otaRequest) throws Exception {
        tansformSearch(otaRequest);
    }
    @Async("asyncExecutor3")
    public void searchAsync3(OtaRequest otaRequest) throws Exception {
        tansformSearch(otaRequest);
    }
    @Async("asyncExecutor4")
    public void searchAsync4(OtaRequest otaRequest) throws Exception {
        tansformSearch(otaRequest);
    }
    @Async("asyncExecutor5")
    public void searchAsync5(OtaRequest otaRequest) throws Exception {
        tansformSearch(otaRequest);
    }

    @Async("asyncExecutor6")
    public void searchAsync6(OtaRequest otaRequest) throws Exception {
        tansformSearch(otaRequest);
    }

    @Async("asyncExecutor7")
    public void searchAsync7(OtaRequest otaRequest) throws Exception {
        tansformSearch(otaRequest);
    }

    @Async("asyncExecutor8")
    public void searchAsync8(OtaRequest otaRequest) throws Exception {
        tansformSearch(otaRequest);
    }

    @Async("asyncExecutor9")
    public void searchAsync9(OtaRequest otaRequest) throws Exception {
        tansformSearch(otaRequest);
    }

}
