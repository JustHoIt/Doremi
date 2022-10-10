package com.example.snsfood.member.repository;

import com.example.snsfood.member.entity.MemberLoginHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberLoginHistoryRepository extends JpaRepository<MemberLoginHistory, Long> {

	Optional<List<MemberLoginHistory>> findByUserId(String userId);
}
