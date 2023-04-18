package com.example.snsfood.admin.mapper;


import com.example.snsfood.board.model.InquiryDto;
import com.example.snsfood.board.model.InquiryParam;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface InquiryMapper {

    long selectListCount(InquiryParam parameter);

    List<InquiryDto> selectList(InquiryParam parameter);

    long selectListNoCount(InquiryParam parameter);

    long selectListCount2(InquiryParam parameter, String solved);

    List<InquiryDto> selectList2(InquiryParam parameter, String solved);
}
