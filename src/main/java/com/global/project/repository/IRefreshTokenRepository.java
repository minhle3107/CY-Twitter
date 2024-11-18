package com.global.project.repository;

import com.global.project.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    RefreshToken findByAccountIdAndDeviceInfo(Long accountId, String deviceInfo);
}
