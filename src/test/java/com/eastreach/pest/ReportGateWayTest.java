package com.eastreach.pest;

import com.eastreach.pest.metadata.APIDef;
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
public class ReportGateWayTest extends RootTest {

    @Test
    public void sp_rep_stat_pest_by_year() throws IOException, DocumentException {
        Map<String, String> map = Maps.newConcurrentMap();
        map.put("account", account_root);
        map.put("password", password_root);
        map.put(APIDef.startDtKey, "2019-01-01");
        map.put(APIDef.endDtKey, "2020-01-01");

        String url = "/report/sp_rep_stat_pest_by_year";

        String response = HttpUtil.postKV(hostUrl + url, map);
        logger.info(url);
        logger.info(response);

    }
}
