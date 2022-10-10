package com.example.snsfood.admin.mailTemplate.service;

import com.example.snsfood.admin.mailTemplate.dto.MailTemplateDto;
import com.example.snsfood.admin.mailTemplate.model.MailTemplateInput;
import com.example.snsfood.admin.mailTemplate.model.MailTemplateParam;

import java.util.List;

public interface MailTemplateService {
    
    /**
     * 템플릿 등록
     */
    boolean add(MailTemplateInput parameter);
    
    /**
     * 템플릿 수정
     */
    boolean set(MailTemplateInput parameter);
    
    /**
     * 템플릿 상세정보
     */
    MailTemplateDto getById(long id);
    
    List<MailTemplateDto> list(MailTemplateParam parameter);
    
}
