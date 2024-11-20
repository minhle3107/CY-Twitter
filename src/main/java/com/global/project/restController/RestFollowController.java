package com.global.project.restController;

import com.global.project.dto.ApiResponse;
import com.global.project.modal.FollowRequest;
import com.global.project.services.impl.FollowServiceImpl;
import com.global.project.utils.Const;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "04. Follow")
@RequestMapping(value = Const.PREFIX_VERSION )
    public class RestFollowController {
    @Autowired
    private FollowServiceImpl service;
    @PostMapping("/follow")
    public ResponseEntity<?> follow(@RequestBody FollowRequest followRequest) {
       return service.follow(followRequest);
    }
}
