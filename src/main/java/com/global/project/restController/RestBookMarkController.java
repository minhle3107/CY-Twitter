package com.global.project.restController;

import com.global.project.dto.ApiResponse;
import com.global.project.dto.BookMarkResponse;
import com.global.project.modal.BookMarkRequest;
import com.global.project.services.IBookMarkService;
import com.global.project.utils.Const;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "05. BOOKMARK")
@RestController
@RequestMapping(value = Const.PREFIX_VERSION)
public class RestBookMarkController {

    private final IBookMarkService iBookMarkService;

    public RestBookMarkController(IBookMarkService iBookMarkService) {
        this.iBookMarkService = iBookMarkService;
    }

    @Operation(summary = "Bookmark or un bookmark", description = "Bookmark or un bookmark", tags = {"05. BOOKMARK"})
    @PostMapping("/bookmark-or-un-bookmark")
    public ResponseEntity<ApiResponse<BookMarkResponse>> bookmarkOrUnBookmark(@RequestBody BookMarkRequest bookMarkRequest) {
        return iBookMarkService.bookMarkOrUnBookMark(bookMarkRequest);
    }
}
