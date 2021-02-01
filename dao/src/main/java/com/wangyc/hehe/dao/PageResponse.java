package com.wangyc.hehe.dao;

import lombok.Data;

import java.util.List;

/**
 * Describe: PageResponse
 * Date: 2021/2/1
 * Time: 3:47 下午
 * Author: wangyc
 */
@Data
public class PageResponse<T>  {
    private List<T> data;
    private int totalDataNum;
    private int totalPage;
    private int pageSize;
    private int pageNum;

    public PageResponse(List<T> data, int totalDataNum, int pageSize, int pageNum) {
        this.data = data;
        this.totalDataNum = totalDataNum;
        this.totalPage = (totalDataNum/pageSize)+(totalDataNum%pageSize>0?1:0);
        this.setPageNum(pageNum);
        this.setPageSize(pageSize);
    }
    public PageResponse(){}
}