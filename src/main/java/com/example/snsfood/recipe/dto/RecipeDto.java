package com.example.snsfood.recipe.dto;


import com.example.snsfood.recipe.entity.Recipe;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class RecipeDto {
    private String rcpSeq;
    private String attFileNoMain;
    private String attFileNoMk;
    private String hashTag;
    private String infoCar;
    private String infoEng;
    private String infoFat;
    private String infoNa;
    private String infoPro;
    private String infoWgt;
    private String rcpNm;
    private String rcpPartsDtls;
    private String rcpPat2;
    private String rcpWay2;
    private String manual01;
    private String manual02;
    private String manual03;
    private String manual04;
    private String manual05;
    private String manual06;
    private String manual07;
    private String manual08;
    private String manual09;
    private String manual10;
    private String manual11;
    private String manual12;
    private String manual13;
    private String manual14;
    private String manual15;
    private String manual16;
    private String manual17;
    private String manual18;
    private String manual19;
    private String manual20;
    private String manualImg01;
    private String manualImg02;
    private String manualImg03;
    private String manualImg04;
    private String manualImg05;
    private String manualImg06;
    private String manualImg07;
    private String manualImg08;
    private String manualImg09;
    private String manualImg10;
    private String manualImg11;
    private String manualImg12;
    private String manualImg13;
    private String manualImg14;
    private String manualImg15;
    private String manualImg16;
    private String manualImg17;
    private String manualImg18;
    private String manualImg19;
    private String manualImg20;

    long totalCount;
    long seq;

    public static RecipeDto of(Recipe recipe) {

        return RecipeDto.builder()
                .rcpSeq(recipe.getRcpSeq())
                .attFileNoMain(recipe.getAttFileNoMain())
                .attFileNoMk(recipe.getAttFileNoMk())
                .hashTag(recipe.getHashTag())
                .infoCar(recipe.getInfoCar())
                .infoEng(recipe.getInfoEng())
                .infoFat(recipe.getInfoFat())
                .infoNa(recipe.getInfoNa())
                .infoPro(recipe.getInfoPro())
                .infoWgt(recipe.getInfoWgt())
                .rcpNm(recipe.getRcpNm())
                .rcpPartsDtls(recipe.getRcpPartsDtls())
                .rcpPat2(recipe.getRcpPat2())
                .rcpWay2(recipe.getRcpWay2())
                .manual01(recipe.getManual01())
                .manual02(recipe.getManual02())
                .manual03(recipe.getManual03())
                .manual04(recipe.getManual04())
                .manual05(recipe.getManual05())
                .manual06(recipe.getManual06())
                .manual07(recipe.getManual07())
                .manual08(recipe.getManual08())
                .manual09(recipe.getManual09())
                .manual10(recipe.getManual10())
                .manual11(recipe.getManual11())
                .manual12(recipe.getManual12())
                .manual13(recipe.getManual13())
                .manual14(recipe.getManual14())
                .manual15(recipe.getManual15())
                .manual16(recipe.getManual16())
                .manual17(recipe.getManual17())
                .manual18(recipe.getManual18())
                .manual19(recipe.getManual19())
                .manual20(recipe.getManual20())
                .manualImg01(recipe.getManualImg01())
                .manualImg02(recipe.getManualImg02())
                .manualImg03(recipe.getManualImg03())
                .manualImg04(recipe.getManualImg04())
                .manualImg05(recipe.getManualImg05())
                .manualImg06(recipe.getManualImg06())
                .manualImg07(recipe.getManualImg07())
                .manualImg08(recipe.getManualImg08())
                .manualImg09(recipe.getManualImg09())
                .manualImg10(recipe.getManualImg10())
                .manualImg11(recipe.getManualImg11())
                .manualImg12(recipe.getManualImg12())
                .manualImg13(recipe.getManualImg13())
                .manualImg14(recipe.getManualImg14())
                .manualImg15(recipe.getManualImg15())
                .manualImg16(recipe.getManualImg16())
                .manualImg17(recipe.getManualImg17())
                .manualImg18(recipe.getManualImg18())
                .manualImg19(recipe.getManualImg19())
                .manualImg20(recipe.getManualImg20())
                .build();
    }


    public static List<RecipeDto> of(List<Recipe> recipes) {

        if (recipes == null) {
            return null;
        }
        List<RecipeDto> recipeDtos = new ArrayList<>();
        for (Recipe x : recipes) {
            recipeDtos.add(RecipeDto.of(x));
        }
        return recipeDtos;

    }
}



