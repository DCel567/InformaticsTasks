package packet;

import java.io.FileWriter;
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

        System.out.println();

        // Here is task about sportsmen
        try{
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser parser = factory.newSAXParser();

            XMLHandler handler = new XMLHandler();
            parser.parse(new File("Sportsmen.xml"), handler);

            for (Sportsman sp : sportsmans) {
                System.out.println(sp.toString() + ": ");
                for (int i = 0; i < sp.getEventsCount(); i++)
                    System.out.println(i+1 + ". " + sp.returnEvent(i));
            }

        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }

        writeToJson();
    }

    private static class XMLHandler extends DefaultHandler {

        String place = null, year = null, award = null;
        int result = 0;
        boolean bAward = false, bResult = false;
        StringBuffer data = null;

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            if (qName.equals("sportsman")) {

                String name = attributes.getValue("name");
                String birthday = attributes.getValue("birthday");
                char gender = attributes.getValue("s").charAt(0);

                sportsmans.add(new Sportsman(name, birthday, gender));  

            } else if (qName.equals("event")) {

                place = attributes.getValue("place");
                year = attributes.getValue("year");

            } else if (qName.equals("result")) {
                bResult = true;
            } else if (qName.equals("award")) {
                bAward = true;
            }            

            data = new StringBuffer();
        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            if (bResult) {
                result = Integer.parseInt(data.toString());
                bResult = false;
            } else if (bAward){
                award = data.toString();
                bAward = false;
            } else if (qName.equals("event")){
                sportsmans.get(sportsmans.size()-1).addEvent(place, year, result, award);
            }
        }

        @Override
	    public void characters(char ch[], int start, int length) throws SAXException {
		    data.append(new String(ch, start, length));
	    }
    }

    static public void writeToJson() throws IOException {
        FileWriter writer = new FileWriter("Sportsmen.json");
        writer.append("{\n");
        writer.append("\t\"team\": [\n");

        for(int i = 0; i < sportsmans.size(); i++){
            writer.append("\t\t{\n");
            {
                writer.append("\t\t\t\"name\": \"" + sportsmans.get(i).getName() + "\",\n");
                writer.append("\t\t\t\"birthday\": \"" + sportsmans.get(i).getBirthday() + "\",\n");
                writer.append("\t\t\t\"gender\": \"" + sportsmans.get(i).getGender() + "\",\n");
                writer.append("\t\t\t\"events\": [\n");

                for(int j = 0; j < sportsmans.get(i).getEventsCount(); j++){
                    writer.append("\t\t\t\t{\n");
                    writer.append("\t\t\t\t\t\"place\": \"" + sportsmans.get(i).returnEventPlace(j) + "\",\n");
                    writer.append("\t\t\t\t\t\"year\": \"" + sportsmans.get(i).returnEventYear(j) + "\",\n");
                    writer.append("\t\t\t\t\t\"result\": \"" + sportsmans.get(i).returnEventResult(j) + "\",\n");
                    writer.append("\t\t\t\t\t\"award\": \"" + sportsmans.get(i).returnEventAward(j) + "\"\n");
                    if (j == sportsmans.get(i).getEventsCount()-1){
                        writer.append("\t\t\t\t}\n");
                    } else {
                        writer.append("\t\t\t\t},\n");
                    }
                }

                writer.append("\t\t\t]\n");
            }
            if (i == sportsmans.size()-1){
                writer.append("\t\t}\n");
            } else {
                writer.append("\t\t},\n");
            }
        }

        writer.append("\t]\n");
        writer.append("}");
        writer.close();
    }
}