package packet;

import java.io.InputStream;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.events.XMLEvent;

public class StaxStreamProcessor implements AutoCloseable{
    private static final XMLInputFactory FACTORY = XMLInputFactory.newInstance();

    private final XMLStreamReader reader;

    public StaxStreamProcessor(InputStream is) throws XMLStreamException{
        reader = FACTORY.createXMLStreamReader(is);
    }

    public XMLStreamReader getReader(){
        return reader;
    }

    public boolean elementExists(int event, XMLStreamReader reader, String name){
        if (event == XMLEvent.START_ELEMENT && name.equals(reader.getLocalName())) return true;
        return false;
    }

    @Override
    public void close(){
        if (reader != null){
            try{
                reader.close();
            } catch (XMLStreamException e){}
        }
    }
}