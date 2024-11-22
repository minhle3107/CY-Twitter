package com.global.project.modal;

import lombok.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.util.List;

@Getter
@Service
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
public class TweetRequest {
    String username;
    int type;
    int audience;
    String content;
    Long parentId;
    List<String> hastags;
    List<String> usernames;
    List<MultipartFile> files;
}
