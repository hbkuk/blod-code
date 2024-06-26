package com.shop.core.admin.auth.application;

import com.shop.common.exception.ErrorType;
import com.shop.core.admin.auth.application.dto.AdminGithubProfileResponse;
import com.shop.core.admin.auth.domain.Admin;
import com.shop.core.admin.auth.domain.AdminRepository;
import com.shop.core.admin.auth.domain.AdminSignupChannel;
import com.shop.core.admin.auth.exception.NonMatchingSignupChannelException;
import com.shop.core.admin.auth.exception.NotFoundAdminException;
import com.shop.core.admin.auth.presentation.dto.AdminTokenResponse;
import com.shop.core.auth.application.JwtTokenProvider;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class AdminAuthService {

    private final AdminRepository adminRepository;
    private final GithubClient githubClient;
    private final JwtTokenProvider jwtTokenProvider;

    public AdminTokenResponse createTokenByGithub(String githubCode) {
        AdminGithubProfileResponse profileResponse = requestGithubProfile(githubCode);

        Admin admin = findMatchingAdmin(profileResponse);
        return AdminTokenResponse.of(jwtTokenProvider.createToken(admin.getEmail()));
    }

    public Admin findAdminByEmail(String email) {
        return adminRepository.findByEmail(email).orElseThrow(() -> new NotFoundAdminException(ErrorType.NOT_FOUND_ADMIN));
    }

    private Admin findMatchingAdmin(AdminGithubProfileResponse profileResponse) {
        Admin admin = findAdminByEmail(profileResponse.getEmail());
        if (!admin.isSameSignupChannel(AdminSignupChannel.GITHUB)) {
            throw new NonMatchingSignupChannelException(ErrorType.NON_MATCHING_SIGNUP_CHANNEL);
        }

        return admin;
    }

    private AdminGithubProfileResponse requestGithubProfile(String code) {
        String githubToken = githubClient.requestGithubToken(code);

        return githubClient.requestGithubProfile(githubToken);
    }
}
