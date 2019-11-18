package com.inautix.utils;

import java.io.IOException;

import javax.sql.rowset.spi.SyncProvider;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class ErrorSerializer extends StdSerializer {
     
    public ErrorSerializer() {
        super(BookError.class);
    }
 
    public ErrorSerializer(Class t) {
        super(t);
    }
 
    public void serialize(Object error, JsonGenerator generator,
      SerializerProvider provider) 
      throws IOException, JsonProcessingException {
        generator.writeStartObject();
        generator.writeFieldName("desc");
        generator.writeString(((Enum<BookError>) error).name());
        generator.writeFieldName("code");
        generator.writeString("TRA"+((BookError) error).getCode());
        generator.writeEndObject();
    }
}