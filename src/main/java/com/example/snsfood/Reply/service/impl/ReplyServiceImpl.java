package com.example.snsfood.Reply.service.impl;


import com.example.snsfood.Reply.entity.Reply;
import com.example.snsfood.Reply.model.ReplyInput;
import com.example.snsfood.Reply.repository.ReplyRepository;
import com.example.snsfood.Reply.service.ReplyService;
import com.example.snsfood.board.dto.BoardDto;
import com.example.snsfood.board.entity.Board;
import com.example.snsfood.board.mapper.BoardMapper;
import com.example.snsfood.board.model.BoardInput;
import com.example.snsfood.board.model.BoardParam;
import com.example.snsfood.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ReplyServiceImpl implements ReplyService {

    private final ReplyRepository replyRepository;

    @Override
    public boolean add(ReplyInput parameter) {
        Reply reply = Reply.builder()
                .id(parameter.getId())
                .bid(parameter.getBid())
                .userId(parameter.getUserId())
                .contents(parameter.getContents())
                .openYn(true)
                .regDt(LocalDateTime.now())
                .build();
        replyRepository.save(reply);
        return true;
    }
}
