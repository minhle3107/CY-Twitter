package com.global.project.restController;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.concurrent.ConcurrentLinkedQueue;

@RestController
public class NotificationController {
    private final ConcurrentLinkedQueue<SseEmitter> emitters = new ConcurrentLinkedQueue<>();
    private final SimpMessagingTemplate messagingTemplate;
    public NotificationController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }


    @GetMapping("/follow-events")
    public SseEmitter handleFollowEvents() {
        SseEmitter emitter = new SseEmitter();
        emitters.add(emitter);

        emitter.onCompletion(() -> emitters.remove(emitter));
        emitter.onTimeout(() -> emitters.remove(emitter));

        return emitter;
    }

    public void notifyFollow(String follower, String followed) {
        for (SseEmitter emitter : emitters) {
            try {
                emitter.send(follower + " is now following " + followed);
            } catch (IOException e) {
                emitters.remove(emitter);
            }
        }
    }
}
