package com.matias.taskly.security.config;

import com.matias.taskly.security.CookieUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.stereotype.Component;

@Component
public class HttpCookieOAuth2AuthorizationRequestRepository
        implements AuthorizationRequestRepository<OAuth2AuthorizationRequest> {

    // Nombre de la cookie donde se guarda el state
    public static final String COOKIE_NAME = "oauth2_auth_request";

    // 3 minutos — tiempo máximo que el usuario tiene para completar el login en Google
    private static final int COOKIE_EXPIRE_SECONDS = 180;

    // Spring llama a este método en el PASO 3
    // Cuando el usuario sale hacia Google, guarda el authorizationRequest en una cookie
    // El authorizationRequest contiene el state, el redirect_uri, los scopes, etc.
    @Override
    public void saveAuthorizationRequest(OAuth2AuthorizationRequest authorizationRequest,
                                         HttpServletRequest request,
                                         HttpServletResponse response) {
        if (authorizationRequest == null) {
            // Si es null, significa que el flujo fue cancelado — borra la cookie
            CookieUtils.deleteCookie(request, response, COOKIE_NAME);
            return;
        }
        // Serializa el objeto a String y lo guarda en cookie
        CookieUtils.addCookie(response, COOKIE_NAME,
                CookieUtils.serialize(authorizationRequest), COOKIE_EXPIRE_SECONDS);
    }

    // Spring llama a este método en el PASO 5
    // Cuando Google redirige de vuelta, lee la cookie y recupera el authorizationRequest
    // Spring lo usa para verificar que el state del callback coincide con el guardado
    @Override
    public OAuth2AuthorizationRequest loadAuthorizationRequest(HttpServletRequest request) {
        return CookieUtils.getCookie(request, COOKIE_NAME)
                .map(cookie -> CookieUtils.deserialize(cookie, OAuth2AuthorizationRequest.class))
                .orElse(null); // null le dice a Spring que no hay request guardado
    }

    // Spring llama a este después de verificar el state exitosamente
    // Limpia la cookie — ya cumplió su propósito
    @Override
    public OAuth2AuthorizationRequest removeAuthorizationRequest(HttpServletRequest request,
                                                                 HttpServletResponse response) {
        OAuth2AuthorizationRequest request0 = this.loadAuthorizationRequest(request);
        CookieUtils.deleteCookie(request, response, COOKIE_NAME);
        return request0;
    }
}