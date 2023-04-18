package com.example.snsfood.Reply.mapper;

import com.example.snsfood.Reply.dto.ReplyDto;
import com.example.snsfood.Reply.model.ReplyParam;
import com.example.snsfood.board.dto.BoardDto;
import com.example.snsfood.board.model.BoardParam;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ReplyMapper {
    List<ReplyDto> selectList(ReplyParam replyParam);
    long selectListCount(ReplyParam replyParam);

}