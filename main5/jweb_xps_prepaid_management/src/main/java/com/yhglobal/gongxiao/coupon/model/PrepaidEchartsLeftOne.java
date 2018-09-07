package com.yhglobal.gongxiao.coupon.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 王帅
 */
public class PrepaidEchartsLeftOne {

    Map<String, List<Map<String, String>>> map = new LinkedHashMap<>();

    Map<String, List> map1 = new LinkedHashMap<>();

    Map<String, List> map2 = new LinkedHashMap<>();

    public Map<String, List> getMap2() {
        return map2;
    }

    public void setMap2(Map<String, List> map2) {
        this.map2 = map2;
    }

    public Map<String, List<Map<String, String>>> getMap() {
        return map;
    }

    public void setMap(Map<String, List<Map<String, String>>> map) {
        this.map = map;
    }

    public Map<String, List> getMap1() {
        return map1;
    }

    public void setMap1(Map<String, List> map1) {
        this.map1 = map1;
    }
}
