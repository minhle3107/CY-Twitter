package com.global.project.restController;

import com.global.project.dto.ApiResponse;
import com.global.project.dto.LikeResponse;
import com.global.project.modal.LikeRequest;
import com.global.project.services.ILikeService;
import com.global.project.utils.Const;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "03. LIKE")
@RestController
@RequestMapping(value = Const.PREFIX_VERSION)
public class RestLikeController {

    private final ILikeService likeService;

    public RestLikeController(ILikeService likeService) {
        this.likeService = likeService;
    }

    @Operation(summary = "Like or unlike", description = "Like or unlike", tags = {"03. LIKE"})
    @PostMapping("/like-or-unlike")
    public ResponseEntity<ApiResponse<LikeResponse>> likeOrUnlike(@RequestBody LikeRequest likeRequest) {
        return likeService.likeOrUnlike(likeRequest);
    }

}
