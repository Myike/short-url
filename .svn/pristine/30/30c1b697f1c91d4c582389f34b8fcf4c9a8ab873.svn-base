package cn.com.yunyoutianxia.tour.service.impl;

import cn.com.yunyoutianxia.tour.service.MainService;
import cn.com.yunyoutianxia.tour.utils.ShortUrlUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;
import org.springframework.stereotype.Service;


/**
 * @Author: zhiwutu
 * @Date: 2019/5/7 15:50
 * @Description:
 */
@Service("cn.com.yunyoutianxia.tour.service.MainService")
public class MainServiceImpl implements MainService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public String getShortCode(String originUrl) {
//        ValueOperations operations = redisTemplate.opsForValue();
//        String shortCode = ShortUrlUtil.digestAlgorithm(originUrl);
//        if(!redisTemplate.hasKey("tour:short:url:code:" + shortCode)) {
//            operations.set("tour:short:url:code:" + shortCode, originUrl);
//        }
        String shortCode = hashUrl(originUrl);
        return shortCode;
    }

    @Override
    public String getOriginUrl(String code) {
        ValueOperations operations = redisTemplate.opsForValue();
        Object originUrl = operations.get("tour:short:url:code:" + code);
        return null != originUrl ? originUrl.toString() : "";
    }


    /**
     * @Description:  生成短码
     *
     * @MethodName   hashUrl
     * @author      zhiwutu
     * @param originUrl
     * @return      java.lang.String
     * @date        2019/5/9 16:23
     */
    private String hashUrl(String originUrl) {
        ValueOperations operations = redisTemplate.opsForValue();
        String shortCode = "";
        //使用摘要算法-有重复的几率
//        String shortCode = ShortUrlUtil.digestAlgorithm(originUrl);
//        if(!redisTemplate.hasKey("tour:short:url:code:" + shortCode)) {
//            operations.set("tour:short:url:code:" + shortCode, originUrl);
//        }

        //自增序列法-永不重复
        //redis自增, 一个id对应一个62进制字符串
        if(!redisTemplate.hasKey("tour:short:url:origin:"+originUrl)) {
            RedisAtomicLong urlIdCounter = new RedisAtomicLong("tour:short:url:id", redisTemplate);
            long counter = urlIdCounter.incrementAndGet();
            System.out.println("自增id:" + counter);
            if(counter < ShortUrlUtil.LENGTH_4_NUM) {
                urlIdCounter.set(ShortUrlUtil.LENGTH_4_NUM);
                counter = ShortUrlUtil.LENGTH_4_NUM;
            }
            shortCode = ShortUrlUtil.encode62(counter);
            operations.set("tour:short:url:origin:"+originUrl, counter);
            operations.set("tour:short:url:code:" + shortCode, originUrl);
        } else {
            String counter = operations.get("tour:short:url:origin:" + originUrl).toString();
            shortCode = ShortUrlUtil.encode62(Long.parseLong(counter));
        }
        return shortCode;
    }



}
