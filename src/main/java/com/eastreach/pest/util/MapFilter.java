package com.eastreach.pest.util;


import com.eastreach.pest.annotation.MapFilterIgnore;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 动态sql过滤对象
 * 领域对象过滤元数据
 * 扁平化过滤,不包含嵌套方式
 **/
public class MapFilter {

    private static final Logger logger = LoggerFactory.getLogger(MapFilter.class);

    public static final String mapEqual = "mapEqual";           //==
    public static final String mapIn = "mapIn";                 //in
    public static final String mapLike = "mapLike";             //like
    public static final String mapGreat = "mapGreat";           //>
    public static final String mapGreatEqual = "mapGreatEqual"; //>=
    public static final String mapLess = "mapLess";             //<
    public static final String mapLessEqual = "mapLessEqual";   //<=

    //过滤算子字段后缀
    public static final String suffixEqual = "Equal";           //==
    public static final String suffixIn = "In";                 //in
    public static final String suffixLike = "Like";             //like
    public static final String suffixGreat = "Great";           //>
    public static final String suffixGreatEqual = "GreatEqual"; //>=
    public static final String suffixLess = "Less";             //<
    public static final String suffixLessEqual = "LessEqual";   //<=

    /**
     * MapFilter对象
     */
    private final Map<String, Map<String, String>> mapMap = Maps.newConcurrentMap();

    /**
     * 获取动态查询模版
     */
    public <T> Specification getWhereClause() {
        return new Specification<T>() {
            @Override
            public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicateList = Lists.newArrayList();
                if (mapMap.get(MapFilter.mapEqual) != null) {
                    for (Map.Entry<String, String> entry : mapMap.get(MapFilter.mapEqual).entrySet()) {
                        predicateList.add(cb.equal(root.get(entry.getKey()), entry.getValue()));
                    }
                }
                if (mapMap.get(MapFilter.mapIn) != null) {
                    for (Map.Entry<String, String> entry : mapMap.get(MapFilter.mapIn).entrySet()) {
                        predicateList.add(cb.in(root.get(entry.getKey())).value(Sets.newConcurrentHashSet(Splitter.on(",").split(entry.getValue()))));
                    }
                }
                //mapLike
                if (mapMap.get(MapFilter.mapLike) != null) {
                    for (Map.Entry<String, String> entry : mapMap.get(MapFilter.mapLike).entrySet()) {
                        predicateList.add(cb.like(root.get(entry.getKey()).as(String.class), "%" + entry.getValue() + "%"));
                    }
                }
                //mapGreat
                if (mapMap.get(MapFilter.mapGreat) != null) {
                    for (Map.Entry<String, String> entry : mapMap.get(MapFilter.mapGreat).entrySet()) {
                        predicateList.add(cb.greaterThan(root.get(entry.getKey()).as(String.class), entry.getValue()));
                    }
                }
                //mapGreatEqual
                if (mapMap.get(MapFilter.mapGreatEqual) != null) {
                    for (Map.Entry<String, String> entry : mapMap.get(MapFilter.mapGreatEqual).entrySet()) {
                        predicateList.add(cb.greaterThanOrEqualTo(root.get(entry.getKey()).as(String.class), entry.getValue()));
                    }
                }
                //mapLess
                if (mapMap.get(MapFilter.mapLess) != null) {
                    for (Map.Entry<String, String> entry : mapMap.get(MapFilter.mapLess).entrySet()) {
                        predicateList.add(cb.lessThan(root.get(entry.getKey()).as(String.class), entry.getValue()));
                    }
                }
                //mapLessEqual
                if (mapMap.get(MapFilter.mapLessEqual) != null) {
                    for (Map.Entry<String, String> entry : mapMap.get(MapFilter.mapLessEqual).entrySet()) {
                        predicateList.add(cb.lessThanOrEqualTo(root.get(entry.getKey()).as(String.class), entry.getValue()));
                    }
                }
                return query.where(predicateList.toArray(new Predicate[predicateList.size()])).getRestriction();
            }
        };
    }


    /**
     * Equal算子
     * 添加相等过滤
     */
    public MapFilter mapEqual(String key, String value) {
        if (StringUtils.isEmpty(key) || StringUtils.isEmpty(value)) {
            return this;
        }
        if (mapMap.get(mapEqual) == null) {
            mapMap.put(mapEqual, Maps.<String, String>newConcurrentMap());
        }
        mapMap.get(mapEqual).put(key, value);
        return this;
    }

    /**
     * In算子
     * 添加包含过滤
     */
    public MapFilter mapIn(String key, String value) {
        if (StringUtils.isEmpty(key) || StringUtils.isEmpty(value)) {
            return this;
        }
        if (mapMap.get(mapIn) == null) {
            mapMap.put(mapIn, Maps.<String, String>newConcurrentMap());
        }
        mapMap.get(mapIn).put(key, value);
        return this;
    }

    /**
     * Like算子
     * 添加相似过滤
     */
    public MapFilter mapLike(String key, String value) {
        if (StringUtils.isEmpty(key) || StringUtils.isEmpty(value)) {
            return this;
        }
        if (mapMap.get(mapLike) == null) {
            mapMap.put(mapLike, Maps.<String, String>newConcurrentMap());
        }
        mapMap.get(mapLike).put(key, value);
        return this;
    }

    /**
     * Great算子
     * 添加大于过滤
     */
    public MapFilter mapGreat(String key, String value) {
        if (StringUtils.isEmpty(key) || StringUtils.isEmpty(value)) {
            return this;
        }
        if (mapMap.get(mapGreat) == null) {
            mapMap.put(mapGreat, Maps.<String, String>newConcurrentMap());
        }
        mapMap.get(mapGreat).put(key, value);
        return this;
    }

    /**
     * GreatEqual算子
     * 添加大于等于过滤
     */
    public MapFilter mapGreatEqual(String key, String value) {
        if (StringUtils.isEmpty(key) || StringUtils.isEmpty(value)) {
            return this;
        }
        if (mapMap.get(mapGreatEqual) == null) {
            mapMap.put(mapGreatEqual, Maps.<String, String>newConcurrentMap());
        }
        mapMap.get(mapGreatEqual).put(key, value);
        return this;
    }

    /**
     * Less算子
     * 添加小于过滤
     */
    public MapFilter mapLess(String key, String value) {
        if (StringUtils.isEmpty(key) || StringUtils.isEmpty(value)) {
            return this;
        }
        if (mapMap.get(mapLess) == null) {
            mapMap.put(mapLess, Maps.<String, String>newConcurrentMap());
        }
        mapMap.get(mapLess).put(key, value);
        return this;
    }

    /**
     * LessEqual算子
     * 添加小于等于过滤
     */
    public MapFilter mapLessEqual(String key, String value) {
        if (StringUtils.isEmpty(key) || StringUtils.isEmpty(value)) {
            return this;
        }
        if (mapMap.get(mapLessEqual) == null) {
            mapMap.put(mapLessEqual, Maps.<String, String>newConcurrentMap());
        }
        mapMap.get(mapLessEqual).put(key, value);
        return this;
    }

    public <T> MapFilter addFilter(JSONObject requestJson, Class<T> clazz, Set<String> ignoreSet) {
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (field.getAnnotation(MapFilterIgnore.class)!=null){
                continue;
            }
            String key = field.getName();
            //==
            if (!ignoreSet.contains(key)) {
                mapEqual(key, requestJson.optString(key));
            }
            //==
            if (!ignoreSet.contains(key + suffixEqual)) {
                mapEqual(key, requestJson.optString(key + suffixEqual));
            }
            //in
            if (!ignoreSet.contains(key + suffixIn)) {
                mapIn(key, requestJson.optString(key + suffixIn));                     //in
            }
            //like
            if (!ignoreSet.contains(key + suffixLike)) {
                mapLike(key, requestJson.optString(key + suffixLike));                 //like
            }
            //>
            if (!ignoreSet.contains(key + suffixGreat)) {
                mapGreat(key, requestJson.optString(key + suffixGreat));               //>
            }
            //>=
            if (!ignoreSet.contains(key + suffixGreatEqual)) {
                mapGreatEqual(key, requestJson.optString(key + suffixGreatEqual));     //>=
            }
            //<
            if (!ignoreSet.contains(key + suffixLess)) {
                mapLess(key, requestJson.optString(key + suffixLess));                 //<
            }
            //<=
            if (!ignoreSet.contains(key + suffixLessEqual)) {
                mapLessEqual(key, requestJson.optString(key + suffixLessEqual));       //<=
            }
        }
        return this;
    }


    /**
     * 返回空的对象过滤模型
     */
    public static MapFilter newInstance() {
        return new MapFilter();
    }

    /**
     * 根据Json对象生成过滤对象
     *
     * @param requestJson 请求json对象封装
     * @param clazz       领域模型元信息.
     * @param ignoreSet   忽略请求中的字段
     * @return
     */
    public static <T> MapFilter newInstance(JSONObject requestJson, Class<T> clazz, Set<String> ignoreSet) {
        //过滤默认关键字
        MapFilter mapFilter = newInstance();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (field.getAnnotation(MapFilterIgnore.class)!=null){
                continue;
            }
            String key = field.getName();
            //==
            if (!ignoreSet.contains(key)) {
                mapFilter.mapEqual(key, requestJson.optString(key));
            }
            //==
            if (!ignoreSet.contains(key + suffixEqual)) {
                mapFilter.mapEqual(key, requestJson.optString(key + suffixEqual));
            }
            //in
            if (!ignoreSet.contains(key + suffixIn)) {
                mapFilter.mapIn(key, requestJson.optString(key + suffixIn));                     //in
            }
            //like
            if (!ignoreSet.contains(key + suffixLike)) {
                mapFilter.mapLike(key, requestJson.optString(key + suffixLike));                 //like
            }
            //>
            if (!ignoreSet.contains(key + suffixGreat)) {
                mapFilter.mapGreat(key, requestJson.optString(key + suffixGreat));               //>
            }
            //>=
            if (!ignoreSet.contains(key + suffixGreatEqual)) {
                mapFilter.mapGreatEqual(key, requestJson.optString(key + suffixGreatEqual));     //>=
            }
            //<
            if (!ignoreSet.contains(key + suffixLess)) {
                mapFilter.mapLess(key, requestJson.optString(key + suffixLess));                 //<
            }
            //<=
            if (!ignoreSet.contains(key + suffixLessEqual)) {
                mapFilter.mapLessEqual(key, requestJson.optString(key + suffixLessEqual));       //<=
            }
        }
        return mapFilter;
    }
}
