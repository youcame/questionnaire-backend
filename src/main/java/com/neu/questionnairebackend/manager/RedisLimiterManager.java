package com.neu.questionnairebackend.manager;

import com.neu.questionnairebackend.common.ErrorCode;
import com.neu.questionnairebackend.exception.BusinessException;
import org.redisson.api.RRateLimiter;
import org.redisson.api.RateIntervalUnit;
import org.redisson.api.RateType;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * redission 进行限流
 */
@Service
public class RedisLimiterManager {

    @Resource
    RedissonClient redissonClient;

    public void doLimit(String key){
        RRateLimiter rateLimiter = redissonClient.getRateLimiter(key);
        rateLimiter.trySetRate(RateType.OVERALL,2,1, RateIntervalUnit.SECONDS);
        boolean b = rateLimiter.tryAcquire(2);
        if(!b){
            throw new BusinessException(ErrorCode.TO_MANY_REQUEST);
        }
    }
}
