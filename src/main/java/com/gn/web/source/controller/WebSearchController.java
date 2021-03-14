package com.gn.web.source.controller;

import cn.hutool.core.date.SystemClock;
import com.alibaba.fastjson.JSON;
import com.gn.web.common.constant.DirectConstants;
import com.gn.web.common.redis.RedisCache;
import com.gn.web.manual.entity.SiteConfig;
import com.gn.web.manual.service.OtaSyncPolicyService;
import com.gn.web.source.entity.OtaRequest;
import com.gn.web.source.service.SourceDataService;
import com.gn.web.source.service.WebSearchService;
import com.gn.web.source.service.impl.WebSearchServiceImpl;
import feign.Param;
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
import org.springframework.web.bind.annotation.*;

import javax.servlet.annotation.WebListener;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

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
        redisCache.deleteKey(DirectConstants.DEDUPLICATION);
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
                    redisCache.addHashMap(DirectConstants.DEDUPLICATION, key, key);
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



    @Autowired
    private SourceDataService sourceDataService;
    @Autowired
    private OtaSyncPolicyService otaSyncPolicyService;

    @ResponseBody
    @RequestMapping(value = "/test")
    public void test(@RequestBody OtaRequest otaRequest) throws Exception {
        otaSyncPolicyService.generateOtaSyncPolicy();
//        sourceDataService.sourceDataCache();
//        webSearchService.tansformSearch(otaRequest);
    }


    /**
     * 对锁测试
     */
    private Lock lock = new ReentrantLock();

    @ResponseBody
    @RequestMapping(value = "/test1")
    public void test1() throws Exception {

        Thread thread = Thread.currentThread();
        LocalDateTime t1 = LocalDateTime.now();
        logger.info("线程:{},时间:{}",thread.getId(),t1.toString());
        lock.lock();
        try {
            Thread.sleep(10 * 1000);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
        LocalDateTime t2 = LocalDateTime.now();
        logger.info("线程:{},时间:{}",thread.getId(),t2.toString());
    }


    /**
     * 多线程对多个结果集进行合并
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {

        LocalDateTime t1 = LocalDateTime.now();
        System.out.println("t1:"+t1);
        ExecutorService executorService= Executors.newFixedThreadPool(10,r ->{
            Thread t =new Thread(r);
            t.setDaemon(true);
            return t;
        });
        List<Integer> numList = Arrays.asList(1,2,3,4,5,6,7,8);

       List<Double> doubles = numList.parallelStream()
               .map(i -> CompletableFuture.supplyAsync(() -> random(i),executorService))
               .map(futrue -> futrue.thenApply(WebSearchController::multiply))
               .map(CompletableFuture::join).collect(toList());
        System.out.println(JSON.toJSONString(doubles));
        LocalDateTime t2 = LocalDateTime.now();
        System.out.println("t2:"+t2);

    }

    public static Double multiply(Double i){
        try {
            Thread.sleep(2*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return 10L * i;
    }


    public static Double random(Integer i){
        Double d= Double.valueOf(new Random().nextInt(10) * i);
        System.out.println("i:"+i+";d:"+d);
        return d;
    }



}
