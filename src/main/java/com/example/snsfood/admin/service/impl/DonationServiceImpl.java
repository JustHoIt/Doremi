package com.example.snsfood.admin.service.impl;


import com.example.snsfood.admin.dto.DonationDto;
import com.example.snsfood.admin.entity.Donation;
import com.example.snsfood.admin.mapper.DonationMapper;
import com.example.snsfood.admin.model.DonationInput;
import com.example.snsfood.admin.model.DonationParam;
import com.example.snsfood.admin.repository.DonationRepository;
import com.example.snsfood.admin.service.DonationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class DonationServiceImpl implements DonationService {

    private final DonationMapper donationMapper;
    private final DonationRepository donationRepository;


    @Override
    public List<DonationDto> list(DonationParam parameter) {
        long totalCount = donationMapper.selectListCount(parameter);
        List<DonationDto> list = donationMapper.selectList(parameter);

        if (!CollectionUtils.isEmpty(list)) {
            int i = 0;
            for (DonationDto x : list) {
                x.setTotalCount(totalCount);
                x.setSeq(totalCount - parameter.getPageStart() - i);
                i++;
            }
        }
        return list;
    }

    @Override
    public DonationDto getById(long id) {
        return donationRepository.findById(id).map(DonationDto::of).orElse(null);
    }

    @Override
    public DonationDto donationDetail(long id) {
        Optional<Donation> optionalDonation = donationRepository.findById(id);

        if(optionalDonation.isPresent()) {
            return DonationDto.of(optionalDonation.get());
        }
        return null;
    }


    @Override
    public boolean set(DonationInput parameter) {
        Optional<Donation> optionalDonation = donationRepository.findById(parameter.getId());
        if(!optionalDonation.isPresent()) {
            return false;
        }


        Donation donation = optionalDonation.get();
        donation.setTitle(parameter.getTitle());
        donation.setContents(parameter.getContents());
        donation.setFileName(parameter.getFileName());
        donation.setPoint(parameter.getPoint());
        donation.setUrlFileName(parameter.getUrlFileName());
        donation.setExpDt(parameter.getExpDt());
        donation.setUdtDt(LocalDateTime.now());


        donationRepository.save(donation);

        return true;
    }

    @Override
    public boolean add(DonationInput parameter) {
        Donation donation = Donation.builder()
                .title(parameter.getTitle())
                .contents(parameter.getContents())
                .expDt(parameter.getExpDt())
                .fileName(parameter.getFileName())
                .urlFileName(parameter.getUrlFileName())
                .regDt(LocalDateTime.now())
                .expDt(parameter.getExpDt())
                .build();
        donationRepository.save(donation);

        return true;
    }

}
