package com.msb.serviceprice.service;

import com.msb.internalcommon.constant.CommonStatusEnum;
import com.msb.internalcommon.dto.PriceRule;
import com.msb.internalcommon.dto.ResponseResult;
import com.msb.internalcommon.request.GoogleForecastPriceDTO;
import com.msb.internalcommon.response.DirectionResponse;
import com.msb.internalcommon.response.ForecastPriceResponse;
import com.msb.internalcommon.util.BigDecimalUtils;
import com.msb.serviceprice.mapper.PriceRuleMapper;
import com.msb.serviceprice.remote.ServiceMapClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class ForecastPriceService {
    @Autowired
    private ServiceMapClient serviceMapClient;

    @Autowired
    private PriceRuleMapper priceRuleMapper;

    /**
     * 根据出发地和目的地经纬度 计算预估价格
     *
     * @param depLongitude
     * @param depLatitude
     * @param destLongitude
     * @param destLatitude
     * @return
     */
    public ResponseResult forecastPrice(String startAddress,
                                        String endAddress) {
        log.info("出发地: " + startAddress);
        log.info("目的地: " + endAddress);
        log.info("调用地图服务，查询距离和时长");
        GoogleForecastPriceDTO forecastPriceDTO = new GoogleForecastPriceDTO();
        forecastPriceDTO.setStartAddress(startAddress);
        forecastPriceDTO.setEndAddress(endAddress);
        ResponseResult<DirectionResponse> direction = serviceMapClient.direction(forecastPriceDTO);
        Double distance = direction.getData().getDistance();
        Integer duration = direction.getData().getDuration();
        log.info("距离 (miles): " + distance + " 时长 (minutes): " + duration);

        log.info("读取计价规则");
        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put("city_code", "110000");
        queryMap.put("vehicle_type", "1");
        List<PriceRule> priceRules = priceRuleMapper.selectByMap(queryMap);
        if (priceRules.size() == 0) {
            return ResponseResult.fail(CommonStatusEnum.PRICE_RULE_EMPTY.getCode(), CommonStatusEnum.PRICE_RULE_EMPTY.getValue());
        }
        PriceRule priceRule = priceRules.get(0);
        log.info("根据距离、时长、计价规则，计算价格");

        double price = getPrice(distance, duration, priceRule);

        // TODO: 根据出发地和目的地经纬度 计算预估价格
        ForecastPriceResponse forecastPriceResponse = new ForecastPriceResponse();
        forecastPriceResponse.setPrice(price);
        return ResponseResult.success(forecastPriceResponse);
    }

    /**
     * 根据距离、时长、计价规则，计算价格
     *
     * @param distance
     * @param duration
     * @param priceRule
     * @return
     */
    private static double getPrice(Double distance, Integer duration, PriceRule priceRule) {
        double price = 0;

        // 起步价
        double startPrice = priceRule.getStartFare();
        price = BigDecimalUtils.add(price, startPrice);

        // 里程费
        // 总里程 m
        double distanceMile = BigDecimalUtils.divide(distance, 1000);

        // 起步里程
        double startMile = (double) priceRule.getStartMile();
        double distanceSubtract = BigDecimalUtils.subtract(distanceMile, startMile);

        // 最终收费的里程数
        double mile = distanceSubtract < 0 ? 0 : distanceSubtract;
        // 计程单价，元/公里
        double unitPricePerMile = priceRule.getUnitPricePerMile();

        // 里程价格
        double mileFare = BigDecimalUtils.multiply(mile, unitPricePerMile);
        price = BigDecimalUtils.add(price, mileFare);

        // 时长
        // 分钟数
        double timeMinute = BigDecimalUtils.divide(duration, 60);
        // 时长单价
        double unitPricePerMinute = priceRule.getUnitPricePerMinute();

        // 时长费用
        double timeFare = BigDecimalUtils.multiply(timeMinute, unitPricePerMinute);
        price = BigDecimalUtils.add(price, timeFare);

        BigDecimal priceBigdecimal = BigDecimal.valueOf(price);
        priceBigdecimal = BigDecimal.valueOf(priceBigdecimal.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
        return priceBigdecimal.doubleValue();
    }

//    public static void main(String[] args) {
//        PriceRule priceRule = new PriceRule();
//        priceRule.setUnitPricePerMile(1.8);
//        priceRule.setUnitPricePerMinute(0.5);
//        priceRule.setStartFare(10.0);
//        priceRule.setStartMile(3);
//        System.out.println(getPrice(6500.0, 1800, priceRule));
//    }
}
