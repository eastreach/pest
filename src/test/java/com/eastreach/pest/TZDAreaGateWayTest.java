package com.eastreach.pest;

import com.alibaba.fastjson.JSON;
import com.beust.jcommander.internal.Lists;
import com.eastreach.pest.model.TZDArea;
import com.eastreach.pest.util.HttpUtil;
import com.google.common.collect.Maps;
import net.sf.json.JSONObject;
import org.dom4j.DocumentException;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 *
 **/
public class TZDAreaGateWayTest extends RootTest {


    @Test
    public void add() throws IOException, DocumentException {
        Map<String, String> map = Maps.newConcurrentMap();
        map.put("account", account_root);
        map.put("password", password_root);
        map.put("code", "qzgy");
        map.put("name", "青藏高原储粮区");
        map.put("memo", "");
        map.put("areaDesc", "西藏,青海,四川部分地区");
        map.put("featureDesc", "高寒干燥");
        map.put("grainDesc", "青稞,春小麦,冬小麦");

        String url = "/area/add";

        String response = HttpUtil.postKV(hostUrl + url, map);
        logger.info(url);
        logger.info(response);

    }

    @Test
    public void addBatch() throws IOException, DocumentException {

        JSONObject requestJson = new JSONObject();
        requestJson.put("account", account_root);
        requestJson.put("password", password_root);

        TZDArea tzdArea = new TZDArea();
        tzdArea.setCode("01");
        tzdArea.setName("01");
        List<TZDArea> tzdAreaList = Lists.newArrayList();
        tzdAreaList.add(tzdArea);
        requestJson.put("tzdAreaList", JSON.toJSONString(tzdAreaList));

        String url = "/area/addBatch";
        logger.info(url);
        logger.info(requestJson.toString());
        JSONObject responseJson = HttpUtil.postJSON(hostUrl + url, requestJson);

        logger.info(responseJson.toString());

    }


    @Test
    public void select() throws IOException, DocumentException {
        Map<String, String> map = Maps.newConcurrentMap();
        map.put("account", account_root);
        map.put("password", password_root);
        String url = "/area/select";

        String response = HttpUtil.postKV(hostUrl + url, map);
        logger.info(url);
        logger.info(response);

    }
}
