package com.example.snsfood.admin.mailTemplate.model;

import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class MailTemplateInput {
    
    long id;
    String mailTemplateKey;
    String title;
    String contents;

}
