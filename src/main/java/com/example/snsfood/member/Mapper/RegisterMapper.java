package com.example.snsfood.member.Mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RegisterMapper {
   int idCheck(String id);

   int nicknameCheck(String nickname);
}
