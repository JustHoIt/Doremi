package com.example.snsfood.admin.mailTemplate.dto;

import com.example.snsfood.admin.mailTemplate.entity.MailTemplate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class MailTemplateDto {
    
    long id;
    String mailTemplateKey;
    String title;
    String contents;
    
    LocalDateTime regDt;//등록일(추가날짜)
    LocalDateTime udtDt;//수정일(수정날짜)
    
    //추가컬럼
    long totalCount;
    long seq;
    
    public static MailTemplateDto of(MailTemplate mailTemplate) {
        
        return MailTemplateDto.builder()
                .id(mailTemplate.getId())
                .mailTemplateKey(mailTemplate.getMailTemplateKey())
                .title(mailTemplate.getTitle())
                .contents(mailTemplate.getContents())
                .regDt(mailTemplate.getRegDt())
                .udtDt(mailTemplate.getUdtDt())
                .build();
    }
    
    
    public String getRegDtText() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss");
        return regDt != null ? regDt.format(formatter) : "";
    }
    
    public String getUdtDtText() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss");
        return udtDt != null ? udtDt.format(formatter) : "";
        
    }
    
}
