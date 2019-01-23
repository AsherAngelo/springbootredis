package com.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by 赵梦杰 on 2018/7/25.
 */
@Controller
public class redisTest {

    @Autowired
    RedisTemplate<String,String> redisTemplate;

    @RequestMapping("/hello")
    public void hello(HttpServletResponse response, HttpServletRequest httpServletRequest)throws Exception{
        httpServletRequest.getSession().setAttribute("name","123");
        redisTemplate.opsForSet().add("name","zhaomengjie");
        redisTemplate.opsForValue().set("test","zhaomengjie");
        String data = "成功发送消息了";
        OutputStream outputStream = response.getOutputStream();
        response.setHeader("content-type", "text/html;charset=UTF-8");
        byte[] dataByteArr = data.getBytes("UTF-8");
        outputStream.write(dataByteArr);
    }

    @RequestMapping("/viewOnline")
    public void viewOnline(HttpServletResponse response)throws Exception {

        Set<Object> onLineSet = new HashSet<Object>();
        String prefixKey = "spring:session:sessions:";
        Set<String> sessionKeys = redisTemplate.keys(prefixKey + "*");
        for (String sessionKey : sessionKeys) {
            if (sessionKey.contains("expires")) {
                continue;
            }
            onLineSet.add(sessionKey);
        }
        String data = String.valueOf(onLineSet.size());
        OutputStream outputStream = response.getOutputStream();
        response.setHeader("content-type", "text/html;charset=UTF-8");
        byte[] dataByteArr = data.getBytes("UTF-8");
        outputStream.write(dataByteArr);
    }


}
