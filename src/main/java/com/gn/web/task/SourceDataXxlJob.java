package com.gn.web.task;

import com.gn.web.source.service.SourceDataService;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.XxlJob;
import com.xxl.job.core.log.XxlJobLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class SourceDataXxlJob {

    Logger logger= LoggerFactory.getLogger(SourceDataXxlJob.class);

    @Autowired
    private SourceDataService sourceDataService;

    @XxlJob("sourceDataJobHandler")
    public ReturnT<String> sourceDataJobHandler(String param) throws Exception {
        XxlJobLogger.log("开始将数据存入缓存");

        logger.info("开始执行----->");
        sourceDataService.sourceDataCache();
        logger.info("执行完成，，，，，");
        return ReturnT.SUCCESS;
    }

}
