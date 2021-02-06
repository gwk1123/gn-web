package com.gn.web.source.controller;

import com.gn.web.common.constant.DirectConstants;
import com.gn.web.common.redis.RedisCache;
import com.gn.web.manual.entity.SiteConfig;
import com.gn.web.source.entity.OtaRequest;
import com.gn.web.source.service.WebSearchService;
import com.gn.web.source.service.impl.WebSearchServiceImpl;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.annotation.WebListener;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@WebListener
@Controller
@RequestMapping("/manual_policy")
public class WebSearchController implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private WebSearchService webSearchService;

    @Autowired
    private RedisCache redisCache;

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

    private static BlockingQueue<OtaRequest> blockingQueue = new LinkedBlockingQueue<>(10000000);

    private Logger logger = LoggerFactory.getLogger(WebSearchController.class);




    @ResponseBody
    @RequestMapping(value = "/queue")
    public String  getQueue(){

        int n1 = asyncExecutor1.getThreadPoolExecutor().getQueue().size();
        int n2 = asyncExecutor2.getThreadPoolExecutor().getQueue().size();
        int n3 = asyncExecutor3.getThreadPoolExecutor().getQueue().size();
        int n4 = asyncExecutor4.getThreadPoolExecutor().getQueue().size();
        int n5 = asyncExecutor5.getThreadPoolExecutor().getQueue().size();
        int n6 = asyncExecutor6.getThreadPoolExecutor().getQueue().size();
        int n7 = asyncExecutor7.getThreadPoolExecutor().getQueue().size();
        int n8 = asyncExecutor8.getThreadPoolExecutor().getQueue().size();
        int n9 = asyncExecutor9.getThreadPoolExecutor().getQueue().size();
        int queue = blockingQueue.size();
        return  "总:"+queue+";"+
                "线程池a:"+n1+";"+
                "线程池b:"+n2+";"+
                "线程池c:"+n3+";"+
                "线程池d:"+n4+";"+
                "线程池e:"+n5+";"+
                "线程池f:"+n6+";"+
                "线程池g:"+n7+";"+
                "线程池h:"+n8+";"+
                "线程池i:"+n9+";";
    }

    @SneakyThrows
    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        redisCache.removeKey(DirectConstants.DEDUPLICATION);
        new Thread(() -> {
            logger.info("开始监听队列");
            while(true){
                try {
                    if(!blockingQueue.isEmpty() || blockingQueue.size() !=0) {
                        addElement();
                    }else {
                        Thread.sleep(5000);
                    }
                }catch (Exception e) {
                    e.printStackTrace();
                    logger.error("监听队列线程沉睡异常:{}",e);
                }
            }
        }).start();
    }


    @ResponseBody
    @RequestMapping(value = "/search")
    public void tansformBatchSearch(@RequestBody List<OtaRequest> otaRequestList){

        List<SiteConfig> sourceDataSwtichList = redisCache.getHashByValues(DirectConstants.SITE_CONFIG);
        otaRequestList.stream().filter(Objects::nonNull).forEach(i ->{
            String key = WebSearchServiceImpl.deduplicationKey(i);
            Boolean hasKey =false;
            try {
                hasKey = redisCache.hasKey(DirectConstants.DEDUPLICATION,key);
            } catch (Exception e1) {
                e1.printStackTrace();
                logger.error("获取去重key异常:{}",e1);
            }
            if(!hasKey) {
                try {
                    redisCache.addHash(DirectConstants.DEDUPLICATION, key, key);
                } catch (Exception customException) {
                    customException.printStackTrace();
                    logger.error("放入去重的key异常:{}", customException);
                }

                if (!CollectionUtils.isEmpty(sourceDataSwtichList)) {
                    sourceDataSwtichList.stream().forEach(e -> {

                        OtaRequest otaRequest = new OtaRequest();
                        otaRequest.setOtaSiteCode(e.getOtaSiteCode());
                        otaRequest.setFromAirport(i.getFromAirport());
                        otaRequest.setFromDate(i.getFromDate());
                        otaRequest.setToAirport(i.getToAirport());
                        otaRequest.setRetDate(i.getRetDate());
                        otaRequest.setTripType(i.getTripType());
                        blockingQueue.add(otaRequest);

                    });
                }

            }
        });
    }



    public void addElement() throws Exception {

        int n1 = asyncExecutor1.getThreadPoolExecutor().getQueue().size();
        int n2 = asyncExecutor2.getThreadPoolExecutor().getQueue().size();
        int n3 = asyncExecutor3.getThreadPoolExecutor().getQueue().size();
        int n4 = asyncExecutor4.getThreadPoolExecutor().getQueue().size();
        int n5 = asyncExecutor5.getThreadPoolExecutor().getQueue().size();
        int n6 = asyncExecutor6.getThreadPoolExecutor().getQueue().size();
        int n7 = asyncExecutor7.getThreadPoolExecutor().getQueue().size();
        int n8 = asyncExecutor8.getThreadPoolExecutor().getQueue().size();
        int n9 = asyncExecutor9.getThreadPoolExecutor().getQueue().size();

        int corePool =9999;
        if(n1 <=corePool){
            if(blockingQueue.size() !=0 || !blockingQueue.isEmpty()) {
                webSearchService.searchAsync1(blockingQueue.take());
            }
        }

        if(n2 <=corePool){
            if(blockingQueue.size() !=0 || !blockingQueue.isEmpty()) {
                webSearchService.searchAsync2(blockingQueue.take());
            }
        }

        if(n3 <=corePool){
            if(blockingQueue.size() !=0 || !blockingQueue.isEmpty()) {
                webSearchService.searchAsync3(blockingQueue.take());
            }
        }

        if(n4 <=corePool){
            if(blockingQueue.size() !=0 || !blockingQueue.isEmpty()) {
                webSearchService.searchAsync4(blockingQueue.take());
            }
        }

        if(n5 <=corePool){
            if(blockingQueue.size() !=0 || !blockingQueue.isEmpty()) {
                webSearchService.searchAsync5(blockingQueue.take());
            }
        }

        if(n6 <=corePool){
            if(blockingQueue.size() !=0 || !blockingQueue.isEmpty()) {
                webSearchService.searchAsync6(blockingQueue.take());
            }
        }

        if(n7 <=corePool){
            if(blockingQueue.size() !=0 || !blockingQueue.isEmpty()) {
                webSearchService.searchAsync7(blockingQueue.take());
            }
        }

        if(n8 <=corePool){
            if(blockingQueue.size() !=0 || !blockingQueue.isEmpty()) {
                webSearchService.searchAsync8(blockingQueue.take());
            }
        }

        if(n9 <=corePool){
            if(blockingQueue.size() !=0 || !blockingQueue.isEmpty()) {
                webSearchService.searchAsync9(blockingQueue.take());
            }
        }

        if(n1 >corePool && n2 >corePool && n3 >corePool && n4 >corePool && n5 >corePool && n6 >corePool && n7 >corePool && n8 >corePool && n9 >corePool){
           Thread.sleep(2000);
        }
    }


//    @ResponseBody
//    @RequestMapping(value = "/delete_status")
//    public String updateOtaDeleteStatus() throws Exception {
//        String msg = "OK";
//        try {
//            webSearchService.updateOtaDeleteStatus();
//        }catch (Exception e){
//            e.printStackTrace();
//            msg = "error";
//        }
//        return msg;
//    }



    @ResponseBody
    @RequestMapping(value = "/test")
    public void test(@RequestBody OtaRequest otaRequest) throws Exception {
        webSearchService.tansformSearch(otaRequest);
    }


}
