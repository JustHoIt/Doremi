package com.example.snsfood.admin.mailTemplate.service.impl;

import com.example.snsfood.admin.mailTemplate.dto.MailTemplateDto;
import com.example.snsfood.admin.mailTemplate.entity.MailTemplate;
import com.example.snsfood.admin.mailTemplate.mapper.MailTemplateMapper;
import com.example.snsfood.admin.mailTemplate.model.MailTemplateInput;
import com.example.snsfood.admin.mailTemplate.model.MailTemplateParam;
import com.example.snsfood.admin.mailTemplate.repository.MailTemplateRepository;
import com.example.snsfood.admin.mailTemplate.service.MailTemplateService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MailTemplateServiceImpl implements MailTemplateService {
    
    private final MailTemplateMapper mailTemplateMapper;
    private final MailTemplateRepository mailTemplateRepository;
    
    private Sort getSortBySortValueDesc() {
        return Sort.by(Sort.Direction.DESC, "sortValue");
    }
    
    @Override
    public boolean add(MailTemplateInput parameter) {
        
        //TODO:메일템플릿키가 존재하는지 확인
        
        MailTemplate mailtemplate = MailTemplate.builder()
                .mailTemplateKey(parameter.getMailTemplateKey())
                .title(parameter.getTitle())
                .contents(parameter.getContents())
                .regDt(LocalDateTime.now())
                .build();
        mailTemplateRepository.save(mailtemplate);
        
        return true;
    }
    
    @Override
    public boolean set(MailTemplateInput parameter) {
        
        Optional<MailTemplate> optionalMailTemplate = mailTemplateRepository.findById(parameter.getId());
        if (optionalMailTemplate.isPresent()) {
            MailTemplate mailtemplate = optionalMailTemplate.get();
            mailtemplate.setTitle(parameter.getTitle());
            mailtemplate.setContents(parameter.getContents());
            mailTemplateRepository.save(mailtemplate);
        }
        
        return true;
    }
    
    @Override
    public MailTemplateDto getById(long id) {
        return mailTemplateRepository.findById(id).map(MailTemplateDto::of).orElse(null);
    }
    
    @Override
    public List<MailTemplateDto> list(MailTemplateParam parameter) {
       
        long totalCount = mailTemplateMapper.selectListCount(parameter);
        List<MailTemplateDto> list = mailTemplateMapper.selectList(parameter);
        if (!CollectionUtils.isEmpty(list)) {
            int i = 0;
            for(MailTemplateDto x : list) {
                x.setTotalCount(totalCount);
                x.setSeq(totalCount - parameter.getPageStart() - i);
                i++;
            }
        }
        
        return list;
    }
    
    
    
}
