package com.wallester.backend.config;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.wallester.backend.exception.ApiException;

import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeParseException;
import java.util.regex.Pattern;

/**
 * Deserializer of a birthDate to dd.MM.yyyy format with right date regex.
 */
public class DateDeSerializer extends StdDeserializer<Date> {
    public static final String DATE_REGEX = "(?:(?:31(\\/|-|\\.)(?:0?[13578]|1[02]))\\1|(?:(?:29|30)(\\/|-|\\.)(?:0?[13-9]|1[0-2])\\2))(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$|^(?:29(\\/|-|\\.)0?2\\3(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))$|^(?:0?[1-9]|1\\d|2[0-8])(\\/|-|\\.)(?:(?:0?[1-9])|(?:1[0-2]))\\4(?:(?:1[6-9]|[2-9]\\d)?\\d{2})";
    private final Pattern pattern = Pattern.compile(DATE_REGEX);

    public DateDeSerializer() {
        super(Date.class);
    }

    @Override
    public Date deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        String value = p.readValueAs(String.class);
        if (!pattern.matcher(value).find()) {
            throw ApiException.badRequestError("Date validation error");
        }
        try {
            java.util.Date date = new SimpleDateFormat("dd.MM.yyyy").parse(value);
            return new Date(date.getTime());
        } catch (DateTimeParseException | ParseException e) {
            throw ApiException.badRequestError("Date validation error", e);
        }
    }

}