package com.gn.web.common.redis;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.gn.web.common.exception.CustomException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * spring redis 工具类
 * @author ruoyi
 **/
@Component
public class RedisCache {
    public final RedisTemplate redisTemplate;
    private Logger logger = LoggerFactory.getLogger(RedisCache.class);

    public RedisCache(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * String类型存贮
     *
     * @param key
     * @param value
     * @param timeout
     * @param timeUnit
     */
    public void saveString(String key, Object value, Integer timeout, TimeUnit timeUnit) {
        redisTemplate.opsForValue().set(key, value, timeout, timeUnit);
    }

    public Object getString(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public void deleteKey(String key){
        redisTemplate.delete(key);
    }

    public Long removeHashKey(String key,Object... keyHashs) throws CustomException {

        return redisTemplate.opsForHash().delete(key,keyHashs);
    }

    public List getHashByValues(String key){
            return  redisTemplate.opsForHash().values(key);
    }

    public boolean hasKey(String key, String keyHash){
        return redisTemplate.opsForHash().hasKey(key, keyHash);
    }

    public void addHashMap(String key,String keyHash,Object value) throws CustomException {
        redisTemplate.opsForHash().put(key,keyHash,value);
    }

    public Object getHashMap(String key,String keyHash) throws CustomException {
            return redisTemplate.opsForHash().get(key,keyHash);
    }

    public Map<Object, Object> getHashMap(String key) throws CustomException {
        return  redisTemplate.opsForHash().entries(key);
    }

    public List<?> getHashList(String key, Set<Object> keys){
        return  (List<?>)redisTemplate.opsForHash().multiGet(key, keys);
    }

    public Set<String> getSet(String key){
        return redisTemplate.opsForSet().members(key);
    }

    public void addSet(String key,Object value){
        redisTemplate.opsForSet().add(key,value);
    }

    public void removeSetKey(String key,Object value){
        redisTemplate.opsForSet().remove(key,value);
    }

    /**
     * 批量获取政策
     * @param keyType
     * @param allKey
     * @return
     * @throws CustomException
     */
    public List<Object> batchHash(String keyType, Set<String> allKey) throws CustomException {
        List<Object> objects= new ArrayList<>();
        try {
            if (CollectionUtils.isNotEmpty(allKey)) {
                Set<Object> keyIds = new HashSet<>();
                List<Object> result = redisTemplate.executePipelined(new SessionCallback() {
                    //执行流水线
                    @Override
                    public Object execute(RedisOperations operations) throws DataAccessException {
                        //批量处理的内容
                        for(String key:allKey){
                            operations.opsForSet().members(key);
                        }
                        //注意这里一定要返回null，最终pipeline的执行结果，才会返回给最外层
                        return null;
                    }
                });

                for(Object o:result){
                    if(CollectionUtils.isNotEmpty((Collection) o)){
                        keyIds.addAll((Collection<?>) o);
                    }
                }

                if(CollectionUtils.isNotEmpty(keyIds)) {
                    List<Object> results =redisTemplate.opsForHash().multiGet(keyType, keyIds);
                    objects.addAll(results);
                }
            }
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
        return objects;
    }

    /**
     * 批量获取全平台调价政策
     * @param keyType
     * @param allKey
     * @return
     * @throws CustomException
     */
    public List getBatchHash(String keyType,List<String> allKey) throws CustomException {
        List<Object> objects = new ArrayList<>();
        try {
            if(CollectionUtils.isNotEmpty(allKey)) {
                allKey.stream().filter(Objects::nonNull).forEach(key -> {
                    String hashKey = keyType + "_" + key;
                    List values = redisTemplate.opsForHash().values(hashKey);
                    if (CollectionUtils.isNotEmpty(values)) {
                        objects.addAll(values);
                    }
                });
            }
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
        return objects;
    }


}
