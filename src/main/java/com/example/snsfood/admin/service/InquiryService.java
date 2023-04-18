package com.example.snsfood.admin.service;

import com.example.snsfood.admin.model.AnswerParam;
import com.example.snsfood.board.model.InquiryDto;
import com.example.snsfood.board.model.InquiryParam;
import com.example.snsfood.member.model.ServiceResult;

import javax.xml.ws.Service;
import java.util.List;

public interface InquiryService {

    InquiryDto detail(long id);

    List<InquiryDto> adminList(InquiryParam parameter, String solved);

    ServiceResult answer(AnswerParam parameter);

    List<InquiryDto> mainList(InquiryParam parameter);
}
