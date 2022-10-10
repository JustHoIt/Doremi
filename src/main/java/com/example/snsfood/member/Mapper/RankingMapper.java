package com.example.snsfood.member.Mapper;


import com.example.snsfood.admin.dto.MemberDto;
import com.example.snsfood.admin.model.MemberParam;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RankingMapper {

    List<MemberDto> selectList(MemberParam parameter);
}
