package org.footballclub.footballclubmanagement.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PaginatedResponse <T> {
    private List<T> content;
    private int currentPage;
    private int totalPages;
    private long totalElements;
    private int pageSize;

    public PaginatedResponse(List<T> content,
                             int currentPage,
                             int totalPages,
                             long totalElements,
                             int pageSize){
        this.content=content;
        this.currentPage=currentPage;
        this.totalPages=totalPages;
        this.totalElements=totalElements;
        this.pageSize=pageSize;
    }
}
