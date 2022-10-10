package com.example.snsfood.board.model;

import com.example.snsfood.admin.model.CommonParam;
import lombok.Data;

@Data
public class BoardParam extends CommonParam {
    long id;
    String searchType;
    String searchValue;
}
