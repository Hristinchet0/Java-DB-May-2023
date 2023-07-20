package com.example.objectmapping.serivce.util;

import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;

@Component("xml_format_converter")
public class XMLFormatConverter implements FormatConverter{

    @Override
    public String serialize(Object obj) {

        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(obj.getClass());
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            StringWriter sw = new StringWriter();

            marshaller.marshal(obj, sw);

            return sw.toString();
        } catch (JAXBException e) {
            e.printStackTrace();
        }

        return null;

    }

    @Override
    public void serialize(Object obj, String fileName) {

        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(obj.getClass());
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            FileWriter fw = new FileWriter(fileName);

            marshaller.marshal(obj, fw);

        } catch (JAXBException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public <T> T deserialize(String input, Class<T> toType) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(toType);

            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

            ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());

            T unmarshal = (T) unmarshaller.unmarshal(inputStream);

            return unmarshal;

        } catch (JAXBException e) {
            e.printStackTrace();
        }

        return null;

    }

    @Override
    public <T> T deserializeFromFile(String fileName, Class<T> toType) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(toType);

            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

            T unmarshal = (T) unmarshaller.unmarshal(new FileInputStream(fileName));

            return unmarshal;

        } catch (JAXBException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        return null;
    }
}
