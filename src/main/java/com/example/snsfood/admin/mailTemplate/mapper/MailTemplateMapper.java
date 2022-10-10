package com.example.snsfood.admin.mailTemplate.mapper;


import com.example.snsfood.admin.mailTemplate.dto.MailTemplateDto;
import com.example.snsfood.admin.mailTemplate.model.MailTemplateParam;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MailTemplateMapper {

    long selectListCount(MailTemplateParam parameter);
    List<MailTemplateDto> selectList(MailTemplateParam parameter);

}
