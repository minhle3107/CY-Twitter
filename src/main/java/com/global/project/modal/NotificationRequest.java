package com.global.project.modal;

import com.global.project.enums.NotificationStatus;
import com.global.project.enums.TypeStatus;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationRequest {
    private String recipient;
    private String actor;
    private String content;
    private TypeStatus type;
    private NotificationStatus notificationStatus;
}
