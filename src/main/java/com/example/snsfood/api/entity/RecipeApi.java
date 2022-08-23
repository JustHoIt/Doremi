package com.example.snsfood.api.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class RecipeApi {
    @Id
    private String rcpSeq;  //레시피번호
    private String rcpNm;   //레시피이름
    private String rcpWay2;  //조리 방식
    private String rcpPat2;  //요리종류
    private String infoWgt;  //중량(인분)
    private String infoEng;  //열량
    private String infoCar;  //탄수화물
    private String infoPro;  //단백질
    private String infoFat;  //지방
    private String infoNa;   //나트륨
    private String hashTag;  //해쉬태그
    private String attFileNoMain;  //이미지경로(소)
    private String attFileNoMk; //이미지경로(대)
    private String rcpPartsDtls; //재료정보
    private String manual01;  //만드는법01
    private String manualImg01; //만드는법 이미지
    private String manual02;    //만드는법
    private String manualImg02; //만드는법 이미지
    private String manual03;    //만드는법
    private String manualImg03; //만드는법 이미지
    private String manual04;    //만드는법
    private String manualImg04; //만드는법 이미지
    private String manual05;    //만드는법
    private String manualImg05; //만드는법 이미지
    private String manual06;    //만드는법
    private String manualImg06; //만드는법 이미지
    private String manual07;    //만드는법
    private String manualImg07; //만드는법 이미지
    private String manual08;    //만드는법
    private String manualImg08; //만드는법 이미지
    private String manual09;    //만드는법
    private String manualImg09; //만드는법 이미지
    private String manual10;    //만드는법
    private String manualImg10; //만드는법 이미지
    private String manual11;    //만드는법
    private String manualImg11; //만드는법 이미지
    private String manual12;    //만드는법
    private String manualImg12; //만드는법 이미지
    private String manual13;    //만드는법
    private String manualImg13; //만드는법 이미지
    private String manual14;    //만드는법
    private String manualImg14; //만드는법 이미지
    private String manual15;    //만드는법
    private String manualImg15; //만드는법 이미지
    private String manual16;    //만드는법
    private String manualImg16; //만드는법 이미지
    private String manual17;    //만드는법
    private String manualImg17; //만드는법 이미지
    private String manual18;    //만드는법
    private String manualImg18; //만드는법 이미지
    private String manual19;    //만드는법
    private String manualImg19; //만드는법 이미지
    private String manual20;    //만드는법
    private String manualImg20; //만드는법 이미지
}
