package com.example.snsfood.admin.controller;

import com.example.snsfood.util.PageUtil;

public class BaseController {

    public String getPagerHtml(long totalCount, long pageSize, long pageIndex, String queryString, String searchType, String searchValue){
        PageUtil pageUtil = new PageUtil(totalCount, pageSize, pageIndex, queryString, searchType, searchValue);

        return pageUtil.pager();


    }
}
