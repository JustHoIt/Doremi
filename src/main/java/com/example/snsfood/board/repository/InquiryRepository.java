package com.example.snsfood.board.repository;

import com.example.snsfood.board.entity.Board;
import com.example.snsfood.board.entity.Inquiry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InquiryRepository extends JpaRepository<Inquiry, Long> {
}
