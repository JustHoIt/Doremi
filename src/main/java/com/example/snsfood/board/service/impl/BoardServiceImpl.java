package com.example.snsfood.board.service.impl;


import com.example.snsfood.Reply.dto.ReplyDto;
import com.example.snsfood.Reply.entity.Reply;
import com.example.snsfood.Reply.mapper.ReplyMapper;
import com.example.snsfood.Reply.model.ReplyParam;
import com.example.snsfood.board.dto.BoardDto;
import com.example.snsfood.board.entity.Board;
import com.example.snsfood.board.mapper.BoardMapper;
import com.example.snsfood.board.model.BoardInput;
import com.example.snsfood.board.model.BoardParam;
import com.example.snsfood.board.repository.BoardRepository;
import com.example.snsfood.board.service.BoardService;
import com.example.snsfood.food.dto.FoodDto;
import com.example.snsfood.food.entity.Food;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class BoardServiceImpl implements BoardService {
    private final BoardRepository boardRepository;
    private final BoardMapper boardMapper;

    private final ReplyMapper replyMapper;


    //조히
    @Override
    public BoardDto getById(long id) {
        return boardRepository.findById(id).map(BoardDto::of).orElse(null);
    }

    //게시글 수정
    @Override
    public boolean set(BoardInput parameter) {
        Optional<Board> optionalBoard = boardRepository.findById(parameter.getId());
        if (!optionalBoard.isPresent()) {
            return false;
        }

        Board board = optionalBoard.get();
        board.setId(parameter.getId());
        board.setUserId(parameter.getUserId());
        board.setTitle(parameter.getTitle());
        board.setCategoryCode(parameter.getCategoryCode());
        board.setContents(parameter.getContents());
        board.setUdtDt(LocalDateTime.now());
        board.setFileName(parameter.getFileName());
        board.setUrlFileName(parameter.getUrlFileName());


        boardRepository.save(board);

        return true;
    }
    //게시글 작성
    @Override
    public boolean add(BoardInput parameter) {
        Board board = Board.builder()
                .id(parameter.getId())
                .userId(parameter.getUserId())
                .title(parameter.getTitle())
                .categoryCode(parameter.getCategoryCode())
                .contents(parameter.getContents())
                .regDt(LocalDateTime.now())
                .fileName(parameter.getFileName())
                .urlFileName(parameter.getUrlFileName())
                .build();

        boardRepository.save(board);
        return true;
    }

    @Override
    public List<BoardDto> list(BoardParam parameter) {
        long totalCount = boardMapper.selectListCount(parameter);
        List<BoardDto> list = boardMapper.selectList(parameter);

        if (!CollectionUtils.isEmpty(list)) {
            int i = 0;
            for (BoardDto x : list) {
                x.setTotalCount(totalCount);
                x.setSeq(totalCount - parameter.getPageStart() - i);
                i++;
            }
        }
        return list;
    }

    @Override
    public List<ReplyDto> replylist(ReplyParam replyParam) {
        long totalCount= replyMapper.selectListCount(replyParam);
        List<ReplyDto> replylist = replyMapper.selectList(replyParam);

        if (!CollectionUtils.isEmpty(replylist)) {
            int i = 0;
            for (ReplyDto x : replylist) {
                x.setTotalCount(totalCount);
                i++;
            }
        }
        return replylist;
    }



    @Override
    public BoardDto boardDetail(long id) {
        Optional<Board> optionalBoard = boardRepository.findById(id);
        if(optionalBoard.isPresent()) {
            return  BoardDto.of(optionalBoard.get());
        }
        return null;
    }




    @Override
    public boolean del(long id) {
        Board board = boardRepository.findById(id)
                .orElseThrow(()-> new IllegalStateException("해당 게시글이 없습니다."));

        boardRepository.delete(board);

        return true;
    }



}
