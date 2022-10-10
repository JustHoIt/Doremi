package com.example.snsfood.admin.service;

import com.example.snsfood.admin.dto.CategoryDto;
import com.example.snsfood.admin.dto.DonationDto;
import com.example.snsfood.admin.model.CategoryInput;
import com.example.snsfood.admin.model.DonationInput;
import com.example.snsfood.admin.model.DonationParam;
import com.example.snsfood.board.model.BoardInput;

import java.util.List;

public interface DonationService {

    List<DonationDto> list(DonationParam parameter);

    DonationDto getById(long id);

    DonationDto donationDetail(long id);

    boolean set(DonationInput parameter);

    boolean add(DonationInput parameter);
}
