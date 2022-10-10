package com.example.snsfood.food.service.Impl;


import com.example.snsfood.food.dto.FoodDto;
import com.example.snsfood.food.entity.Food;
import com.example.snsfood.food.mapper.FoodMapper;
import com.example.snsfood.food.model.FoodInput;
import com.example.snsfood.food.model.FoodParam;
import com.example.snsfood.food.repository.FoodRepository;
import com.example.snsfood.food.service.FoodService;
import com.example.snsfood.member.model.ServiceResult;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class FoodServiceImpl implements FoodService {

    private final FoodRepository foodRepository;
    private final FoodMapper foodMapper;


    @Value("${APIKEY}")
    private String apiKey;


    @Override
    public List<FoodDto> list(FoodParam parameter) {
        long totalCount = foodMapper.selectListCount(parameter);
        List<FoodDto> list = foodMapper.selectList(parameter);

        if (!CollectionUtils.isEmpty(list)) {
            int i = 0;
            for (FoodDto x : list) {
                x.setTotalCount(totalCount);
                x.setSeq(totalCount - parameter.getPageStart() - i);
                i++;
            }
        }
        return list;
    }

    @Override
    public List<FoodDto> mainFoodList(FoodParam foodParam) {
        List<FoodDto> list = foodMapper.selectMainFoodList(foodParam);

        return list;
    }


    @Override
    public boolean del(String idList) {
        if (idList != null && idList.length() > 0) {

            String[] ids = idList.split(",");
            for (String x : ids) {
                long id = 0L;
                try {
                    id = Long.parseLong(x);
                } catch (Exception e) {
                }
                if (id > 0) {
                    foodRepository.deleteById(id);
                }
            }
        }
        return true;
    }

    @Override
    public boolean add(FoodInput parameter) {
        Food food = Food.builder()
                .foodNm(parameter.getFoodNm())
                .id(parameter.getId())
                .userId(parameter.getUserId())
                .foodQt(parameter.getFoodQt())
                .foodOt(parameter.getFoodOt())
                .foodOt2(parameter.getFoodOt2())
                .foodTp(parameter.getFoodTp())
                .barCode(parameter.getBarCode())
                .foodStorage(parameter.getFoodStorage())
                .foodMm(parameter.getFoodMm())
                .buyDt(parameter.getBuyDt())
                .expDt(parameter.getExpDt())
                .regDt(LocalDateTime.now())
                .filename(parameter.getFilename())
                .urlFilename(parameter.getUrlFilename())
                .useYn(false)
                .build();
        foodRepository.save(food);

        return true;
    }



    @Override
    public boolean set(FoodInput parameter) {
        Optional<Food> optionalFood = foodRepository.findById(parameter.getId());
        if (!optionalFood.isPresent()) {
            return false;
        }
        Food food = optionalFood.get();
        food.setFoodNm(parameter.getFoodNm());
        food.setId(parameter.getId());
        food.setUserId(parameter.getUserId());
        food.setBarCode(parameter.getBarCode());
        food.setFoodQt(parameter.getFoodQt());
        food.setFoodOt(parameter.getFoodOt());
        food.setFoodStorage(parameter.getFoodStorage());
        food.setFoodOt2(parameter.getFoodOt2());
        food.setFoodTp(parameter.getFoodTp());
        food.setFoodMm(parameter.getFoodMm());
        food.setBuyDt(parameter.getBuyDt());
        food.setExpDt(parameter.getExpDt());
        food.setUdtDt(LocalDateTime.now());
        food.setUrlFilename(parameter.getUrlFilename());
        food.setFilename(parameter.getFilename());
        foodRepository.save(food);

        return true;
    }

    @Override
    public ServiceResult useFood(FoodInput foodInput) {
        Long foodId = foodInput.getId();
        System.out.println(foodId + "@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");

        Optional<Food> optionalFood = foodRepository.findById(foodId);
        System.out.println(optionalFood.toString()+"@@@@@@!!!@@@@");
        if(!optionalFood.isPresent()){
           return new ServiceResult(false,"식품 정보가 존재하지 않습니다.");
        }

        Food food = optionalFood.get();

        if(food.isUseYn() == true){
            return new ServiceResult(false,"잘못된 요청입니다.");
        }
        food.setUseYn(true);
        System.out.println("true 로 변경 @@@@@@2");
        foodRepository.save(food);
        return new ServiceResult();
    }


    @Override
    public FoodDto getById(long id) {
        return foodRepository.findById(id).map(FoodDto::of).orElse(null);
    }

    @Override
    public FoodDto frontDetail(long id) {
        Optional<Food> optionalFood = foodRepository.findById(id);
        if (optionalFood.isPresent()) {
            return FoodDto.of(optionalFood.get());
        }
        return null;
    }

    @Override
    public Map<String, Object> barCodeCheck(String barCode) {
        System.out.println("실행됨");
        String  foodData = getFoodInfo(barCode);

        System.out.println("123실행됨");

        return parseFood(foodData);


    }



//    private Food getFoodInfoFromApi(){
//        String
//    }


    private String getFoodInfo(String barCode){

        String apiUrl = "http://openapi.foodsafetykorea.go.kr/api/" + apiKey + "/C005/json/1/1/BAR_CD=" + barCode;

        System.out.println(apiUrl);
        System.out.println("실행됨(getFoodInfo)");
        try {
            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            int responseCode = connection.getResponseCode();
            BufferedReader br;
            if(responseCode == 200){
                br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            }else {
                br = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
            }
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }
            br.close();


            return response.toString();

        } catch (Exception e) {
            return "failed to get response";
        }

    }



    private Map<String, Object> parseFood(String jsonString){
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject;
        System.out.println("실행됨(parseFood)");
        try {
            jsonObject = (JSONObject)  jsonParser.parse(jsonString);
        }catch (ParseException e){
            throw new RuntimeException(e);
        }

        Map<String, Object> resultMap = new HashMap<>();
        JSONObject mainData = (JSONObject) jsonObject.get("C005");
        JSONArray row = (JSONArray) mainData.get("row");
        JSONObject object= (JSONObject) row.get(0);


        resultMap.put("BAR_CD", object.get("BAR_CD")); //바코드
        resultMap.put("PRDLST_NM",object.get("PRDLST_NM")); //제품명
        resultMap.put("POG_DAYCNT",object.get("POG_DAYCNT")); //유통기한
        resultMap.put("PRDLST_DCNM",object.get("PRDLST_DCNM")); //식품구분



        System.out.println(resultMap.toString());


        return resultMap;
    }




}

