package com.msb.servicemap.service;

import com.msb.internalcommon.constant.AmapConfigConstants;
import com.msb.internalcommon.constant.CommonStatusEnum;
import com.msb.internalcommon.dto.DicDistrict;
import com.msb.internalcommon.dto.ResponseResult;
import com.msb.servicemap.mapper.DicDistrictMapper;
import com.msb.servicemap.remote.MapDicDistrictClient;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DicDistrictService {
    @Autowired
    private MapDicDistrictClient mapDicDistrictClient;

    @Autowired
    private DicDistrictMapper dicDistrictMapper;

    public ResponseResult initDicDistrict() {
        // 请求地图
        String str = mapDicDistrictClient.initDicDistrict();
        //System.out.println(str);
        // 解析结果
        JSONObject dicDistrictJsonObject = JSONObject.fromObject(str);
        int status = dicDistrictJsonObject != null ? 1 : -1;
        if (status != 1) {
            return ResponseResult.fail(CommonStatusEnum.MAP_DISTRICT_ERROR.getCode(), CommonStatusEnum.MAP_DISTRICT_ERROR.getValue());
        }
        JSONArray regionsJsonArray = dicDistrictJsonObject.getJSONArray(AmapConfigConstants.REGIONS);
        for (int i = 0; i < regionsJsonArray.size(); i++) {
            JSONObject regionJsonObject = regionsJsonArray.getJSONObject(i);
            String addressCode = regionJsonObject.getString(AmapConfigConstants.ADCODE);
            String addressName = regionJsonObject.getString(AmapConfigConstants.NAME);
            String level = regionJsonObject.getString(AmapConfigConstants.LEVEL);
            String parentAddressCode = "0";
            if (regionJsonObject.containsKey(AmapConfigConstants.PARENT)) {
                parentAddressCode = regionJsonObject.getString(AmapConfigConstants.PARENT);
            }
            insertDicDistrict(addressCode, addressName, level, parentAddressCode);
        }
        return ResponseResult.success("");
    }

    public void insertDicDistrict(String addressCode, String addressName, String level, String parentAddressCode) {
        // 数据库对象
        DicDistrict dicDistrict = new DicDistrict();
        dicDistrict.setAddressCode(addressCode);
        dicDistrict.setAddressName(addressName);
        dicDistrict.setLevel(Integer.valueOf(level));
        dicDistrict.setParentAddressCode(parentAddressCode);
        // 插入数据库
        dicDistrictMapper.insert(dicDistrict);
    }

    /**
     * 根据级别生成地址码
     * @param level
     * @return
     */
    public int generateLevel(String level) {
        int levelInt = 0;
        if (level.trim().equals(AmapConfigConstants.COUNTRY)) {
            levelInt = 0;
        } else if (level.trim().equals(AmapConfigConstants.PROVINCE)) {
            levelInt = 1;
        } else if (level.trim().equals(AmapConfigConstants.CITY)) {
            levelInt = 2;
        } else if (level.trim().equals(AmapConfigConstants.DISTRICT)) {
            levelInt = 3;
        }
        return levelInt;
    }
}
