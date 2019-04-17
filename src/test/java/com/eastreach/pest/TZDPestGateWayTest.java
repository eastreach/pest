package com.eastreach.pest;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.beust.jcommander.internal.Lists;
import com.eastreach.pest.model.TZDPest;
import com.eastreach.pest.util.FileUtil;
import com.eastreach.pest.util.HttpUtil;
import com.google.common.collect.Maps;
import org.dom4j.DocumentException;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 *
 **/
public class TZDPestGateWayTest extends RootTest {

    @Test
    public void add() throws IOException {
        Map<String, String> map = Maps.newConcurrentMap();
        map.put("account", "root");
        map.put("password", "root");
        map.put("code", "code");
        map.put("name", "name");
        map.put("memo", "memo");

        String url = "/pest/add";

        String response = HttpUtil.get(hostUrl + url, map);
        logger.info(url);
        logger.info(response);

    }

    @Test
    public void select() throws IOException {
        Map<String, String> map = Maps.newConcurrentMap();
        map.put("account", "root");
        map.put("password", "root");
//        map.put("code","code");
//        map.put("name","am");

        String url = "/pest/select";

        String response = HttpUtil.get(hostUrl + url, map);
        logger.info(url);
        logger.info(response);

    }


    @Test
    public void selectPage() throws IOException {
        Map<String, String> map = Maps.newConcurrentMap();
        map.put("account", "root");
        map.put("password", "root");
        map.put("pageSize", "10");
//        map.put("currentPage","0");
        map.put("name", "am");

        String url = "/pest/selectPage";

        String response = HttpUtil.get(hostUrl + url, map);
        logger.info(url);
        logger.info(response);

    }

    @Test
    public void parseJSON() {
        String json = FileUtil.ReadFile("./public/t_zd_pest.json");
        System.out.println(json);
    }

    @Test
    public void init() throws IOException, DocumentException {
        JSONObject jsonObject = JSONObject.parseObject(FileUtil.ReadFile("./public/t_zd_pest.json"));
        List<TZDPest> tzdPestList = Lists.newArrayList();
        for (Object o : jsonObject.getJSONArray("RECORDS")) {
            JSONObject jsonObject1 = (JSONObject) o;
            TZDPest tzdPest = new TZDPest();
            tzdPest.setCode(jsonObject1.getString("NAME"));
            tzdPest.setName(jsonObject1.getString("NAME"));
            tzdPest.setDanger(jsonObject1.getString("DANGER"));
            tzdPest.setFamily(jsonObject1.getString("FAMILY"));
            tzdPest.setCategory(jsonObject1.getString("CATEGORY"));
            tzdPest.setFeature(jsonObject1.getString("FEATURE"));
            tzdPest.setHabit(jsonObject1.getString("HABIT"));
            tzdPest.setDamage(jsonObject1.getString("DAMAGE"));
            tzdPest.setDistribution(jsonObject1.getString("DISTRIBUTION"));
            tzdPest.setPrevention(jsonObject1.getString("PREVENTION"));
            tzdPest.setPic(hostUrl + "/" + jsonObject1.getString("PICTUREURL"));
            tzdPestList.add(tzdPest);
        }
        Map<String, String> map = Maps.newConcurrentMap();
        map.put("account", "root");
        map.put("password", "root");
        map.put("tzdPestList", JSONArray.toJSONString(tzdPestList));
        String url = "/pest/addBatch";


        net.sf.json.JSONObject response = HttpUtil.postJSON(hostUrl + url, net.sf.json.JSONObject.fromObject(map));
        logger.info(url);
        logger.info(response.toString());
    }
}
