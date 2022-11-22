package com.example.miproyectowebspringboot;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

public class ServletInitializer extends SpringBootServletInitializer{

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        // Esta clase java es necesaria para ejecutarlo en TomCat Externo
        return builder.sources(MiproyectowebSpringbootApplication.class);
    }
    
}
