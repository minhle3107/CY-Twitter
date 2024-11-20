package com.global.project.services.impl;

import com.global.project.entity.Hastag;
import com.global.project.repository.HastagRepository;
import com.global.project.services.IHastagService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class HastagService implements IHastagService {
    HastagRepository hastagRepository;

    @Override
    public int insertHasTags(List<String> hasTagNames) {
        try {
            List<Hastag> newHasTags = hasTagNames.stream()
                    .filter(name -> !hastagRepository.existsByName(name))
                    .map(name -> Hastag.builder()
                            .name(name)
                            .createdAt(LocalDateTime.now())
                            .build())
                    .toList();
            
            hastagRepository.saveAll(newHasTags);
            return 1;
        } catch (Exception e) {
            return 0;
        }

    }
}
