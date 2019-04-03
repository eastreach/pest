package com.eastreach.pest;

import com.eastreach.pest.util.HttpUtil;
import com.google.common.collect.Maps;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Map;

/**
 *
 **/
public class TZDPestGateWayTest extends RootTest {

    @Test
    public void add() throws IOException {
        Map<String,String> map = Maps.newConcurrentMap();
        map.put("account","root");
        map.put("password","root");
        map.put("code","code");
        map.put("name","name");
        map.put("memo","memo");

        String url = "/pest/add";

        String response = HttpUtil.get(hostUrl+url,map);
        logger.info(url);
        logger.info(response);

    }

    @Test
    public void select() throws IOException {
        Map<String,String> map = Maps.newConcurrentMap();
        map.put("account","root");
        map.put("password","root");
//        map.put("code","code");
//        map.put("name","am");

        String url = "/pest/select";

        String response = HttpUtil.get(hostUrl+url,map);
        logger.info(url);
        logger.info(response);

    }


    @Test
    public void selectPage() throws IOException {
        Map<String,String> map = Maps.newConcurrentMap();
        map.put("account","root");
        map.put("password","root");
        map.put("pageSize","10");
//        map.put("currentPage","0");
        map.put("name","am");

        String url = "/pest/selectPage";

        String response = HttpUtil.get(hostUrl+url,map);
        logger.info(url);
        logger.info(response);

    }
}
