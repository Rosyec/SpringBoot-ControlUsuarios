package com.example.miproyectowebspringboot;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.support.SessionFlashMapManager;

@Component
public class LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    Logger log = LoggerFactory.getLogger(LoginSuccessHandler.class);

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private LocaleResolver localeResolver;


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
                Locale locale = localeResolver.resolveLocale(request);
        SessionFlashMapManager flashMapManager = new SessionFlashMapManager();
        FlashMap flashMap = new FlashMap();
        flashMap.put("success", messageSource.getMessage("text.login.mensajeBienvenida", null, locale)
        .concat(" ")
        .concat(authentication.getName())
        .concat(" ")
        .concat(messageSource.getMessage("text.login.mensajeBienvenida.2", null, locale)));
        flashMapManager.saveOutputFlashMap(flashMap, request, response);
        if (authentication != null) {
            log.info("El usuario ".concat(authentication.getName()).concat(" ha iniciado sesión."));
        }
        response.sendRedirect("/app/listar");
        
        //super.onAuthenticationSuccess(request, response, authentication);
    }

}
