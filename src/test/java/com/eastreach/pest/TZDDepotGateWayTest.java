package com.eastreach.pest;

import com.eastreach.pest.util.HttpUtil;
import com.google.common.collect.Maps;
import org.dom4j.DocumentException;
import org.joda.time.DateTime;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Map;

/**
 *
 **/
public class TZDDepotGateWayTest extends RootTest{


    @Test
    public void add() throws IOException, DocumentException {
        Map<String, String> map = Maps.newConcurrentMap();
        map.put("account", account_root);
        map.put("password", password_root);
        map.put("code", "test0124");
        map.put("name", "test01粮仓");
        map.put("mountValue", "10.0");
        map.put("inDt", DateTime.now().toString("yyyy-MM-dd HH:mm:ss"));

        String url = "/depot/add";

        String response = HttpUtil.postKV(hostUrl + url, map);
        logger.info(url);
        logger.info(response);

    }
}
