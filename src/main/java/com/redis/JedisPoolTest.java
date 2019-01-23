package com.redis;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import redis.clients.jedis.Jedis;

import javax.servlet.http.HttpServletRequest;


/**
 * Created by 赵梦杰 on 2018/8/28.
 */
@Controller
public class JedisPoolTest {

    @RequestMapping("/jedisPool123")
    @ResponseBody
    public String jedisSet(HttpServletRequest request,String name, String value){
        System.out.println(request.getParameter("name"));
        Jedis jedis = JedisUtil.getResource();
        jedis.set(name,value);
        return jedis.get(name);
    }
}
