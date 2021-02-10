package com.gn.web.common.init;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gn.web.common.constant.DirectConstants;
import com.gn.web.common.redis.RedisCache;
import com.gn.web.common.redis.RedisCacheKeyUtils;
import com.gn.web.manual.entity.OtaRule;
import com.gn.web.manual.entity.PolicyGlobal;
import com.gn.web.manual.entity.PolicyInfo;
import com.gn.web.manual.entity.SiteConfig;
import com.gn.web.manual.mapper.OtaRuleMapper;
import com.gn.web.manual.mapper.PolicyGlobalMapper;
import com.gn.web.manual.mapper.PolicyInfoMapper;
import com.gn.web.manual.mapper.SiteConfigMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class InitCacheRunner implements CommandLineRunner {

    @Autowired
    private OtaRuleMapper otaRuleMapper;
    @Autowired
    private PolicyGlobalMapper policyGlobalMapper;
    @Autowired
    private PolicyInfoMapper policyInfoMapper;
    @Autowired
    private SiteConfigMapper siteConfigMapper;
    @Autowired
    private RedisCache redisCache;

    private Logger logger =LoggerFactory.getLogger(InitCacheRunner.class);

    @Override
    public void run(String... args) throws Exception {
        initData();
    }

    public void initData(){
        logger.info("初始化缓存。。。");
        initSiteConfig();
        initOtaRule();
        initPolicyGlobal();
        initPolicyInfo();
        logger.info("缓存完成。。。");
    }


    public void initSiteConfig(){
        redisCache.deleteKey(DirectConstants.SITE_CONFIG);
        QueryWrapper<SiteConfig> queryWrapper=new QueryWrapper<>();
        queryWrapper.lambda().eq(SiteConfig::getStatus, DirectConstants.NORMAL);
        List<SiteConfig> siteConfigs =siteConfigMapper.selectList(queryWrapper);
        if(!CollectionUtils.isEmpty(siteConfigs)){
            siteConfigs.stream().forEach(siteConfig ->{
                redisCache.addHashMap(DirectConstants.SITE_CONFIG,siteConfig.getOtaSiteCode(),siteConfig);            });
        }
    }


    public void initOtaRule(){
        QueryWrapper<OtaRule> queryWrapper=new QueryWrapper<>();
        queryWrapper.lambda().eq(OtaRule::getStatus, DirectConstants.NORMAL);
        List<OtaRule> OtaRules =otaRuleMapper.selectList(queryWrapper);
        if(!CollectionUtils.isEmpty(OtaRules)){
            Map<String,List<OtaRule>> otaRuleMap=OtaRules.stream().collect(Collectors.groupingBy(otaRule -> otaRule.getOtaSiteCode()+"_"+otaRule.getRuleType()));
            otaRuleMap.entrySet().stream().forEach(entry ->{
                redisCache.deleteKey(entry.getKey());
            });
            OtaRules.stream().forEach(otaRule ->{
                redisCache.addHashMap(otaRule.getOtaSiteCode()+"_"+otaRule.getRuleType(),String.valueOf(otaRule.getId()),otaRule);
            });
        }
    }

    public void initPolicyGlobal(){
        QueryWrapper<PolicyGlobal> queryWrapper=new QueryWrapper<>();
        queryWrapper.lambda().eq(PolicyGlobal::getStatus, DirectConstants.NORMAL);
        List<PolicyGlobal> PolicyGlobals =policyGlobalMapper.selectList(queryWrapper);
        if(!CollectionUtils.isEmpty(PolicyGlobals)){
            PolicyGlobals.stream().forEach(policyGlobal ->{
                String setKey = RedisCacheKeyUtils.policyGlobalSetKey(policyGlobal);
                redisCache.addSet(setKey, String.valueOf(policyGlobal.getId()));
                redisCache.addHashMap(DirectConstants.POLICY_GLOBAL, String.valueOf(policyGlobal.getId()), policyGlobal);            });
        }
    }

    public void initPolicyInfo(){
        QueryWrapper<PolicyInfo> queryWrapper=new QueryWrapper<>();
        queryWrapper.lambda().eq(PolicyInfo::getStatus, DirectConstants.NORMAL);
        List<PolicyInfo> PolicyInfos =policyInfoMapper.selectList(queryWrapper);
        if(!CollectionUtils.isEmpty(PolicyInfos)){
            PolicyInfos.stream().forEach(policyInfo ->{
                String setKey = RedisCacheKeyUtils.policyInfoSetKey(policyInfo);
                redisCache.addSet(setKey, String.valueOf(policyInfo.getId()));
                redisCache.addHashMap(DirectConstants.POLICY_INFO, String.valueOf(policyInfo.getId()), policyInfo);
            });
        }
    }

}
