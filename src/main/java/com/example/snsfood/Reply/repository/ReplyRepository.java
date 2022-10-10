package com.example.snsfood.Reply.repository;

import com.example.snsfood.Reply.entity.Reply;
import com.example.snsfood.board.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
}
