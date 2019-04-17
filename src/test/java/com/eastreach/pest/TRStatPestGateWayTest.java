package com.eastreach.pest;

import com.eastreach.pest.util.HttpUtil;
import com.google.common.collect.Maps;
import org.dom4j.DocumentException;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Map;

/**
 *
 **/
public class TRStatPestGateWayTest extends RootTest {

    @Test
    public void add() throws IOException, DocumentException {
        Map<String,String> map = Maps.newConcurrentMap();
        map.put("account",account_root);
        map.put("password",password_root);
        map.put("year","2018");
        map.put("month","1");
        map.put("areaCode","qzgy");
        map.put("grainCode","冬小麦");
        map.put("pestCode","麦蛾");
        map.put("pestValue","10");

        String url = "/statPest/add";

        String response = HttpUtil.postKV(hostUrl+url,map);
        logger.info(url);
        logger.info(response);

    }
}
