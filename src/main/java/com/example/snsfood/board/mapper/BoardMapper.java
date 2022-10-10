package com.example.snsfood.board.mapper;

import com.example.snsfood.board.dto.BoardDto;
import com.example.snsfood.board.model.BoardParam;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardMapper {
    List<BoardDto> selectList(BoardParam parameter);
    long selectListCount(BoardParam parameter);

}
