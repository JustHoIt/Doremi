package com.example.snsfood.admin.mailTemplate.repository;

import com.example.snsfood.admin.mailTemplate.entity.MailTemplate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MailTemplateRepository extends JpaRepository<MailTemplate, Long> {

	Optional<MailTemplate> findByMailTemplateKey(String mailTemplateKey);

}
