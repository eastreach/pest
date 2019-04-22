package com.eastreach.pest;

import com.alibaba.fastjson.JSON;
import com.beust.jcommander.internal.Lists;
import com.eastreach.pest.model.TZDArea;
import com.eastreach.pest.model.TZDUrl;
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
public class TZDUrlGateWayTest extends RootTest {

    @Test
    public void addBatch() throws IOException, DocumentException {
        Map<String, String> map = Maps.newConcurrentMap();
        map.put("account", account_root);
        map.put("password", password_root);

        List<TZDUrl> tzdUrlList = Lists.newArrayList();
        tzdUrlList.add(new TZDUrl("/operator/login","登录权限"));
        tzdUrlList.add(new TZDUrl("/pest/add","害虫增加"));
        tzdUrlList.add(new TZDUrl("/pest/addBatch","害虫增加批量"));
        tzdUrlList.add(new TZDUrl("/pest/delete","害虫删除"));
        tzdUrlList.add(new TZDUrl("/pest/deleteBatch","害虫删除批量"));
        tzdUrlList.add(new TZDUrl("/pest/update","修改"));

        map.put("tzdUrlList", JSON.toJSONString(tzdUrlList));

        String url = "/url/addBatch";
        String response = HttpUtil.postKV(hostUrl + url, map);
        logger.info(url);
        logger.info(response);

    }
}
