package it.inps.entrate.rlaq.batch.ws;

import java.io.IOException;

import javax.xml.transform.TransformerException;

import org.springframework.ws.WebServiceMessage;
import org.springframework.ws.client.core.WebServiceMessageCallback;
import org.springframework.ws.soap.SoapHeader;
import org.springframework.ws.soap.SoapMessage;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;

public class HeaderAdder<T> implements WebServiceMessageCallback {

	private T header;

	public HeaderAdder(T header) {
		this.header = header;
	}

	@Override
	public void doWithMessage(WebServiceMessage message) throws IOException, TransformerException {
		SoapHeader soapHeader = ((SoapMessage) message).getSoapHeader();

		try {
			JAXBContext context = JAXBContext.newInstance(header.getClass());

			Marshaller marshaller = context.createMarshaller();
			marshaller.marshal(header, soapHeader.getResult());

		} catch (JAXBException e) {
			throw new IOException("error while marshalling header.", e);
		}
	}

}