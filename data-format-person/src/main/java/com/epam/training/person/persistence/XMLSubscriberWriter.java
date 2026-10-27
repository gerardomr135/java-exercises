package com.epam.training.person.persistence;

import com.epam.training.person.domain.Phonebook;
import com.epam.training.person.domain.Subscriber;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.PropertyException;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.List;

public class XMLSubscriberWriter implements DataWriter<List<Subscriber>> {
    private final OutputStreamWriter outputWriter;

    public XMLSubscriberWriter(OutputStream out) {
        this.outputWriter = new OutputStreamWriter(out);
    }

    @Override
    public void write(List<Subscriber> value) {
        Phonebook phonebook = new Phonebook(value);

        Marshaller marshaller = null;
        try {
            JAXBContext  jaxbContext = JAXBContext.newInstance(Phonebook.class);
            marshaller = jaxbContext.createMarshaller();
        }catch (JAXBException e) {
            e.printStackTrace();
            return;
        }

        try {
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        } catch (PropertyException e) {
            e.printStackTrace();
            return;
        }

        try {
            marshaller.marshal(phonebook, outputWriter);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close() throws Exception {
        outputWriter.close();
    }
}
