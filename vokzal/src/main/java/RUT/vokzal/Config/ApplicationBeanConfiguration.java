package RUT.vokzal.Config;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.modelmapper.AbstractConverter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Configuration
public class ApplicationBeanConfiguration {

    @Bean
    public Validator validator() {
        return Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        modelMapper.addConverter(new AbstractConverter<String, LocalDate>() {
            @Override
            protected LocalDate convert(String source) {
                return source != null ? LocalDate.parse(source, DateTimeFormatter.ofPattern("yyyy-MM-dd")) : null;
            }
        });

        modelMapper.addConverter(new AbstractConverter<String, LocalTime>() {
            @Override
            protected LocalTime convert(String source) {
                return source != null ? LocalTime.parse(source, DateTimeFormatter.ofPattern("HH:mm")) : null;
            }
        });

        return modelMapper;
    }
}