package packet;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class program {

    public static ArrayList<Book> library = new ArrayList<>();

    public static ArrayList<Sportsman> sportsmans = new ArrayList<>();

    public static void main(String[] args) throws XMLStreamException, IOException {

        // This is about books
        try (StaxStreamProcessor processor = new StaxStreamProcessor(Files.newInputStream(Paths.get("Books.xml")))){
            XMLStreamReader reader = processor.getReader();
            String bookName = "", autorName = "";
            int pages = 0;
            int event = 0;
            while(reader.hasNext()){
                
                if (processor.elementExists(event, reader, "autor")){
                    autorName = reader.getAttributeValue(null, "name");
                    
                    while (reader.hasNext()){
                        event = reader.next();
                        
                        if (processor.elementExists(event, reader, "autor")) break;
                        
                        if (processor.elementExists(event, reader, "name")){
                            bookName = reader.getElementText();
                        }
                        if (processor.elementExists(event, reader, "pages")){
                            pages = Integer.parseInt(reader.getElementText());
                        }
                        if (autorName != "" && bookName != "" && pages != 0){
                            library.add(new Book(bookName, autorName, pages));
                            bookName = ""; pages = 0;
                        }
                    }
                }
                else event = reader.next();
            }
            for (int i = 0; i < library.size(); i++){
                library.get(i).print_book();
            }
        } 


        // Here is task about sportsmen

        try{
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser parser = factory.newSAXParser();

            XMLHandler handler = new XMLHandler();
            parser.parse(new File("Sportsmen.xml"), handler);

            for (Sportsman sp : sportsmans) {
                System.out.println(sp.getName() + ": " + sp.getEventsCount());
            }

        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    private static class XMLHandler extends DefaultHandler {

        private String lastElementName;
        String place = "", year = "", award = "";
        int result = 0;

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            if (qName.equals("sportsman")) {

                String name = attributes.getValue("name");
                String birthday = attributes.getValue("birthday");
                char gender = attributes.getValue("s").charAt(0);

                sportsmans.add(new Sportsman(name, birthday, gender));               
            }

            lastElementName = qName;
            System.out.println(lastElementName);
        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            if ( (place != null && !place.isEmpty()) && (year != null && !year.isEmpty()) &&
                 (award != null && !award.isEmpty()) && (result != 0) ) {
                sportsmans.get(sportsmans.size()-1).addEvent(place, year, result, award);
                place = null;
                year = null;
                award = null;
                result = 0;
            }

            // наладить, не сделано!
        }

        @Override
        public void characters(char[] ch, int start, int length) throws SAXException {
            String information = new String(ch, start, length);

            information = information.replace("\n", "").trim();

            if (!information.isEmpty()) {
                if (lastElementName.equals("place"))
                    place = information;
                    //System.out.println(place);
                if (lastElementName.equals("year"))
                    year = information;
                if (lastElementName.equals("result"))
                    result = Integer.parseInt(information);
                if (lastElementName.equals("award"))
                    award = information;
            }
        }
    }
}