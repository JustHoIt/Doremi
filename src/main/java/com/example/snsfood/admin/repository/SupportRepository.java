package com.example.snsfood.admin.repository;

import com.example.snsfood.admin.mailTemplate.entity.Donation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupportRepository extends JpaRepository<Donation, Long> {
}
