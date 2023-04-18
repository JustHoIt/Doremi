package com.example.snsfood.admin.service.impl;


import com.example.snsfood.admin.dto.CategoryDto;
import com.example.snsfood.admin.dto.DonationDto;
import com.example.snsfood.admin.mailTemplate.entity.Category;
import com.example.snsfood.admin.mailTemplate.entity.MailTemplate;
import com.example.snsfood.admin.mailTemplate.repository.MailTemplateRepository;
import com.example.snsfood.admin.mapper.CategoryMapper;
import com.example.snsfood.admin.mapper.InquiryMapper;
import com.example.snsfood.admin.model.AnswerParam;
import com.example.snsfood.admin.model.CategoryInput;
import com.example.snsfood.admin.repository.CategoryRepository;
import com.example.snsfood.admin.service.CategoryService;
import com.example.snsfood.admin.service.InquiryService;
import com.example.snsfood.board.entity.Inquiry;
import com.example.snsfood.board.model.InquiryDto;
import com.example.snsfood.board.model.InquiryParam;
import com.example.snsfood.board.repository.InquiryRepository;
import com.example.snsfood.compnets.MailComponents;
import com.example.snsfood.member.entity.Member;
import com.example.snsfood.member.model.ServiceResult;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class InquiryServiceImpl implements InquiryService {
    private final InquiryMapper inquiryMapper;

    private final MailTemplateRepository mailTemplateRepository;

    private final MailComponents mailComponents;

    private final InquiryRepository inquiryRepository;

    @Override
    public InquiryDto detail(long id) {
        Optional<Inquiry> optionalInquiry = inquiryRepository.findById(id);

        if (optionalInquiry.isPresent()) {
            return InquiryDto.of(optionalInquiry.get());
        }

        return null;
    }

    @Override
    public List<InquiryDto> adminList(InquiryParam parameter, String solved) {
        List<InquiryDto> list = null;
        long totalCount = 0;
        if (solved == null) {
            totalCount = inquiryMapper.selectListCount(parameter);
            list = inquiryMapper.selectList(parameter);
        } else {
            totalCount = inquiryMapper.selectListCount2(parameter, solved);
            list = inquiryMapper.selectList2(parameter, solved);
        }
        if (!CollectionUtils.isEmpty(list)) {
            int i = 0;
            for (InquiryDto x : list) {
                x.setTotalCount(totalCount);
                x.setSeq(totalCount - parameter.getPageStart() - i);
                i++;
            }
        }
        return list;
    }

    @Override
    public ServiceResult answer(AnswerParam parameter) {
        Optional<Inquiry> optionalInquiry = inquiryRepository.findById(parameter.getId());
        if (!optionalInquiry.isPresent()) {
            return new ServiceResult(false, "문의 내용이 존재하지 않습니다.");

        }
        Inquiry inquiry = optionalInquiry.get();

        inquiry.setDoYn(true);
        inquiryRepository.save(inquiry);

        mailTemplateRepository.findByMailTemplateKey(MailTemplate.MAIL_TEMPLATE_KEY_INQUIRY).ifPresent(e -> {
            String email = parameter.getEmail();
            String subject = e.getTitle();
            String text = e.getContents().replace("####답변내용####", parameter.getAnswer());

            mailComponents.sendMail(email, subject, text);
        });


        return new ServiceResult();
    }

    @Override
    public List<InquiryDto> mainList(InquiryParam parameter) {
        long totalCount = inquiryMapper.selectListNoCount(parameter);
        List<InquiryDto> list = inquiryMapper.selectList(parameter);
        if (!CollectionUtils.isEmpty(list)) {
            int i = 0;
            for (InquiryDto x : list) {
                x.setTotalCount(totalCount);
                x.setSeq(totalCount - parameter.getPageStart() - i);
                i++;
            }
        }
        return list;
    }
}
