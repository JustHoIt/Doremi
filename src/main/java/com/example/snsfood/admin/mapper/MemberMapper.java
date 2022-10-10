package com.example.snsfood.admin.mapper;


import com.example.snsfood.admin.dto.MemberDto;
import com.example.snsfood.admin.model.MemberParam;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MemberMapper {

    List<MemberDto> selectList(MemberParam parameter);
    long selectListCount(MemberParam parameter);

    void givePoint(String userId);
}
