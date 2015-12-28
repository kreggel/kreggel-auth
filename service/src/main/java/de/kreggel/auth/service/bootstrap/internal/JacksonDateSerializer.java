package de.kreggel.auth.service.bootstrap.internal;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

/**
 * Custom {@link Date} serializer to not rely on jackson default date serialization which might change in future.
 * This serializer produces a date string of the format: 2015-12-28T16:12:00Z.
 */
public class JacksonDateSerializer extends StdSerializer<Date> {

    private static final String ISO_8601_DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZ";

    public JacksonDateSerializer() {
        super(Date.class);
    }

    @Override
    public void serialize(Date value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonGenerationException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(ISO_8601_DATE_TIME_FORMAT, Locale.ENGLISH);
        String dateTimeString = simpleDateFormat.format(value);
        jgen.writeString(dateTimeString);
    }
}