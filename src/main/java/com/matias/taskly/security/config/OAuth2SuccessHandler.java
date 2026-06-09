package com.matias.taskly.security.config;

import com.matias.taskly.model.User;
import com.matias.taskly.repository.UserRepository;
import com.matias.taskly.security.JwtService;
import com.matias.taskly.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtService jwtService;
    private final UserService userService;  // cambiás repository por service

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {

        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String email    = oAuth2User.getAttribute("email");
        String nickname = oAuth2User.getAttribute("name");

        userService.findOrCreateOAuthUser(email, nickname);

        String token = jwtService.generateToken(email);
        String redirectUrl = "http://localhost:5173/oauth2/callback?token=" + token;
        getRedirectStrategy().sendRedirect(request, response, redirectUrl);
    }
}