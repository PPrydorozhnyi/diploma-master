package com.diploma.master.configs;

import com.diploma.master.models.converters.EnumFromStringConverterFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class WebConfig implements WebMvcConfigurer {

    private final EnumFromStringConverterFactory enumFromStringConverterFactory;
    @Value("${date.pattern}")
    private String datePattern;

    @Override
    public void addFormatters(FormatterRegistry registry) {
        DateFormatter dateFormatter = new DateFormatter();
        dateFormatter.setPattern(datePattern);
        registry.addFormatter(dateFormatter);
        registry.addConverterFactory(enumFromStringConverterFactory);
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedMethods("GET", "POST", "PUT", "DELETE");
    }

}
