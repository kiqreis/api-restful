package br.com.kiqreis.apirestfulsb.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class JacksonConfig {

    @Autowired
    private void registerSerializersDeserializers(List<ObjectMapper> objectMappers) {
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        objectMappers.forEach(objectMapper -> objectMapper.registerModule(javaTimeModule));
    }
}
