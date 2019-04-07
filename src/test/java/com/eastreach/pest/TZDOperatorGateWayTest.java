package com.eastreach.pest;

import com.eastreach.pest.util.HttpUtil;
import com.google.common.collect.Maps;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Map;

/**
 *
 **/
public class TZDOperatorGateWayTest extends RootTest{




    @Test
    public void register() throws IOException {
        Map<String,String> map = Maps.newConcurrentMap();
        map.put("account","test");
        map.put("password","test");
        map.put("name","test");

        String url = "/operator/register";

        String response = HttpUtil.get(hostUrl+url,map);
        logger.info(url);
        logger.info(response);

    }


    @Test
    public void login() throws IOException {
        Map<String,String> map = Maps.newConcurrentMap();
        map.put("account","root");
        map.put("password","root");

        String url = "/operator/login";

        String response = HttpUtil.get(hostUrl+url,map);
        logger.info(url);
        logger.info(response);

    }
}
