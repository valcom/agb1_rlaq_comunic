package it.inps.entrate.rlaq.batch.ws;

import java.io.IOException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.transform.TransformerException;

import org.springframework.ws.WebServiceMessage;
import org.springframework.ws.client.core.WebServiceMessageCallback;
import org.springframework.ws.soap.SoapHeader;
import org.springframework.ws.soap.SoapMessage;

import it.inps.Identity;

public class IdentityHeader implements WebServiceMessageCallback {

    private Identity identity;

    public IdentityHeader(Identity identity) {
        this.identity = identity;
    }

    @Override
    public void doWithMessage(WebServiceMessage message) throws IOException, TransformerException {
        SoapHeader soapHeader = ((SoapMessage)message).getSoapHeader();

        try {
            JAXBContext context = JAXBContext.newInstance(Identity.class);

            Marshaller marshaller = context.createMarshaller();
            marshaller.marshal(identity, soapHeader.getResult());

        } catch (JAXBException e) {
            throw new IOException("error while marshalling identity.",e);
        }
    }
    
}