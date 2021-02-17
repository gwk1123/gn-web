package com.gn.web.task;

import com.gn.web.manual.service.OtaSyncPolicyService;
import com.gn.web.source.service.SourceDataService;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.XxlJob;
import com.xxl.job.core.log.XxlJobLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OtaSyncPolicyXxlJob {

    Logger logger= LoggerFactory.getLogger(SourceDataXxlJob.class);

    @Autowired
    private OtaSyncPolicyService otaSyncPolicyService;

    @XxlJob("otaSyncPolicyJobHandler")
    public ReturnT<String> otaSyncPolicyJobHandler(String param) throws Exception {
        XxlJobLogger.log("开始生成同步政策...");

        logger.info("开始生成同步政策执行...");
        otaSyncPolicyService.generateOtaSyncPolicy();
        logger.info("执行同步政策完成，，，，，");
        return ReturnT.SUCCESS;
    }
}
