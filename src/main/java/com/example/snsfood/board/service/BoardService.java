package com.example.snsfood.board.service;


import com.example.snsfood.Reply.dto.ReplyDto;
import com.example.snsfood.Reply.model.ReplyParam;
import com.example.snsfood.board.dto.BoardDto;
import com.example.snsfood.board.model.BoardInput;
import com.example.snsfood.board.model.BoardParam;

import java.util.List;

public interface BoardService {
    BoardDto getById(long id);


    //게시글 수정
    boolean set(BoardInput parameter);

    //게시글 작성
    boolean add(BoardInput parameter);

    List<BoardDto> list(BoardParam parameter);

    BoardDto boardDetail(long id);

    boolean del(long id);


    List<ReplyDto> replylist(ReplyParam replyParam);
}
