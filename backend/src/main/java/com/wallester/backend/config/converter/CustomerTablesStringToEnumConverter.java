package com.wallester.backend.config.converter;

import com.wallester.backend.persist.entity.CustomerEntityTables;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class CustomerTablesStringToEnumConverter implements Converter<String, CustomerEntityTables> {

    @Override
    public CustomerEntityTables convert(String source) {
        return CustomerEntityTables.valueOf(source.toUpperCase(Locale.ROOT));
    }
}
