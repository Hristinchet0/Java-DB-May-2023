package com.example.objectmapping.serivce.util;


public interface FormatConverter {

    String serialize(Object obj);

    void serialize(Object obj, String fileName);

    <T> T deserialize(String input, Class<T> toType);

    <T> T deserializeFromFile(String fileName, Class<T> toType);

}
