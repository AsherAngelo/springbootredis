package com.redis;

import org.apache.commons.lang.StringUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.exceptions.JedisException;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by 赵梦杰 on 2018/8/28.
 */
public class JedisUtil {
    private static JedisPool jedisPool = SpringContextHolder.getBean(JedisPool.class);
    public static String get(String key) {
        String value = null;
        Jedis jedis = null;
        try {
            jedis = getResource();
            if (jedis.exists(key)) {
                value = jedis.get(key);
                value = StringUtils.isNotBlank(value) && !"nil".equalsIgnoreCase(value) ? value : null;
            }
        } catch (Exception e) {
        } finally {
            returnResource(jedis);
        }
        return value;
    }

    public static String set(String key, String value, int cacheSeconds) {
        String result = null;
        Jedis jedis = null;
        try {
            jedis = getResource();
            result = jedis.set(key, value);
            if (cacheSeconds != 0) {
                jedis.expire(key, cacheSeconds);
            }
        } catch (Exception e) {
        } finally {
            returnResource(jedis);
        }
        return result;
    }

    public static Jedis getResource() throws JedisException {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
        } catch (JedisException e) {
            throw e;
        }
        return jedis;
    }

    public static void returnResource(Jedis jedis) {
        if (jedis != null) {
//			jedisPool.returnResource(jedis);
            jedis.close();
        }
    }

    public static void main(String[] args) {
        System.out.println(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date(1535637000000L)));
    }
}
