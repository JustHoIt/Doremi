package com.example.snsfood.Reply.service;


import com.example.snsfood.Reply.dto.ReplyDto;
import com.example.snsfood.Reply.model.ReplyInput;
import com.example.snsfood.board.dto.BoardDto;
import com.example.snsfood.board.model.BoardInput;
import com.example.snsfood.board.model.BoardParam;

import java.util.List;

public interface ReplyService {

    boolean add(ReplyInput parameter);


}
