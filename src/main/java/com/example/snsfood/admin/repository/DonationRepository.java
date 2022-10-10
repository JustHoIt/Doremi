package com.example.snsfood.admin.repository;

import com.example.snsfood.admin.entity.Donation;
import com.example.snsfood.board.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DonationRepository extends JpaRepository<Donation, Long> {
}
