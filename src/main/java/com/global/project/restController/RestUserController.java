package com.global.project.restController;

import com.global.project.dto.ApiResponse;
import com.global.project.dto.UserResponse;
import com.global.project.services.IUserService;
import com.global.project.utils.Const;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Tag(name = "06. User")
@RequestMapping(value = Const.PREFIX_VERSION + "/user" )
public class RestUserController {
    private final IUserService _service;
    public RestUserController(IUserService service){
        _service=service;
    }
    @GetMapping("/getallnotme")
    public ResponseEntity<ApiResponse<List<UserResponse>>> getAll() {
        return _service.findAllExceptCurrentUser();
    }
}
