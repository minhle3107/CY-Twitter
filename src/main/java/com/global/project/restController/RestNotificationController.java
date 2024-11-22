package com.global.project.restController;

import com.global.project.dto.ApiResponse;
import com.global.project.dto.NotificationResponse;
import com.global.project.modal.NotificationRequest;
import com.global.project.services.INotificationService;
import com.global.project.utils.Const;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "07. Notification")
@RequestMapping(value = Const.PREFIX_VERSION )
public class RestNotificationController {
    private final INotificationService _service;

    public RestNotificationController(INotificationService service) {
        _service = service;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createNotification(@RequestBody NotificationRequest request) {
        return _service.createNotification(request);
    }

    @GetMapping("/get")
    public ResponseEntity<ApiResponse<List<NotificationResponse>>> getAll() {
        return _service.getNotificationByUserName();
    }

    @GetMapping("/seen")
    public ResponseEntity<ApiResponse<List<NotificationResponse>>> getAllSeen() {
        return _service.getNotificationByStatusSeen();
    }
    @GetMapping("/unseen")
    public ResponseEntity<ApiResponse<List<NotificationResponse>>> getAllUnSeen() {
        return _service.getNotificationByStatusUnSeen();
    }
}
