package com.eastreach.pest;

import com.eastreach.pest.util.HttpUtil;
import com.google.common.collect.Maps;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Map;

/**
 *
 **/
public class TZDGrainGateWayTest extends RootTest {

    @Test
    public void select() throws IOException {
        Map<String, String> map = Maps.newConcurrentMap();
        map.put("account", "root");
        map.put("password", "root");
//        map.put("code","code");
//        map.put("name","am");

        String url = "/grain/select";

        String response = HttpUtil.get(hostUrl + url, map);
        logger.info(url);
        logger.info(response);

    }
}
