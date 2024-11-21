package com.global.project.services.impl;

import com.global.project.entity.ViaCode;
import com.global.project.repository.ViaCodeRepository;
import com.global.project.services.IViaCodeService;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;

@Service
@Transactional
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ViaCodeService implements IViaCodeService {
    ViaCodeRepository viaRepository;

    @Override
    public boolean validateCode(String code, String email) {
        ViaCode viaCode = viaRepository.findByEmail(email);
        if (viaCode != null) {
            LocalDateTime now = LocalDateTime.now();
            Duration duration = Duration.between(viaCode.getCreatedAt(), now);
            if (duration.toMinutes() > 3) {
                return false;
            }
            String codeCheck = viaCode.getViaCode().toString();
            return code.equals(codeCheck);
        }
        return false;
    }
}
