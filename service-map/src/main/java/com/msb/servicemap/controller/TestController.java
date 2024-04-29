package com.msb.servicemap.controller;

import com.msb.internalcommon.dto.DicDistrict;
import com.msb.servicemap.mapper.DicDistrictMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class TestController {
    @GetMapping("/test")
    public String test() {
        return "service-map is running";
    }

    @Autowired
    DicDistrictMapper dicDistrictMapper;

    @GetMapping("/test-map")
    public String testMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("address_code", "110000");
        List<DicDistrict> dicDistricts = dicDistrictMapper.selectByMap(map);
        System.out.println(dicDistricts);
        return "success";
    }
}
