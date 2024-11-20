package com.global.project.services;

import com.global.project.dto.ApiResponse;
import com.global.project.dto.BookMarkResponse;
import com.global.project.modal.BookMarkRequest;
import org.springframework.http.ResponseEntity;

public interface IBookMarkService {
    ResponseEntity<ApiResponse<BookMarkResponse>> bookMarkOrUnBookMark(BookMarkRequest bookMarkRequest);
}
