package com.example.snsfood.admin.model;

import lombok.Data;

@Data
public class DonationParam extends CommonParam {
    long id;
    String searchType;
    String searchValue;

    int totalCount;
}
