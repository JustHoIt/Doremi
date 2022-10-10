package com.example.snsfood.admin.mapper;

import com.example.snsfood.admin.dto.CategoryDto;
import com.example.snsfood.admin.dto.DonationDto;
import com.example.snsfood.admin.model.DonationParam;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DonationMapper {

    long selectListCount(DonationParam parameter);

    List<DonationDto> selectList(DonationParam parameter);
}
