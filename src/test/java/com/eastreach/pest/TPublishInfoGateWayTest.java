package com.eastreach.pest;

import com.eastreach.pest.util.HttpUtil;
import com.google.common.collect.Maps;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Map;

/**
 *
 **/
public class TPublishInfoGateWayTest extends RootTest {


    @Test
    public void add() throws IOException {
        Map<String,String> map = Maps.newConcurrentMap();
        map.put("account","root");
        map.put("password","root");
        map.put("code","test");
        map.put("name","test");
        map.put("content","test");

        String url = "/publishInfo/add";

        String response = HttpUtil.get(hostUrl+url,map);
        logger.info(url);
        logger.info(response);

    }

    @Test
    public void test(){
        logger.info(Double.parseDouble("1")+"");
    }
}
