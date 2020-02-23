package com.api.format;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.text.SimpleDateFormat;

/**
 * 自定义日期格式
 * TODO : 需要完善
 *
 * @author 李星源
 * @version 1.0
 */
public class CustomDateSerializer extends JsonSerializer {

    /**
     * 实现日期格式
     * TODO : 需要完善
     *
     * @param value
     * @param jgen
     * @param provider
     */
    @Override
    public void serialize(Object value, JsonGenerator jgen, SerializerProvider provider){
        try {
            SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
            String foe=format.format(value);
            jgen.writeString(foe);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

}