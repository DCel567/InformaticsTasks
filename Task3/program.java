package packet;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class program {
    public static ArrayList<Book> library = new ArrayList<>();

    public static void main(String[] args) {
        String filepath = "Books.xml";
        File xmlFile = new File(filepath);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;

        try {
            builder = factory.newDocumentBuilder();
            Document document = builder.parse(xmlFile);
            document.getDocumentElement().normalize();
            //----------------------------------------//

            NodeList autors = document.getElementsByTagName("autor");
            for(int j = 0; j < autors.getLength(); j++){
                NodeList books = autors.item(j).getChildNodes();
    
                for(int i = 0; i < books.getLength(); i++){
                    Node book = books.item(i);
                    if (book.getNodeType() == Node.ELEMENT_NODE){
                        String name = getTagValue("name", book);
                        int pages = Integer.parseInt(getTagValue("pages", book));
                        String aName = book.getParentNode().getAttributes().getNamedItem("name").getNodeValue();
                        library.add(new Book(name, aName, pages));
                    }
                }
            }
            for(int i = 0; i < library.size(); i++) library.get(i).print_book();
        }    
        catch(Exception ex){
            System.out.println(ex.getMessage());
        }
        System.out.println();
        //----------------------------------------------------//

        ArrayList<Sportsman> sportsmen = new ArrayList<>();

        try{
            builder = factory.newDocumentBuilder();
            filepath = "Sportsmen.xml";
            File anotherFile = new File(filepath);
            Document document = builder.parse(anotherFile);
            document.getDocumentElement().normalize();

            NodeList results = document.getElementsByTagName("sportsman");

            for(int i = 0; i < results.getLength(); i++){
                Node oneSportman = results.item(i);
                String sportsmanName = oneSportman.getAttributes().getNamedItem("name").getNodeValue();
                String sportsmanBirthday = oneSportman.getAttributes().getNamedItem("birthday").getNodeValue();
                char s = oneSportman.getAttributes().getNamedItem("s").getNodeValue().charAt(0);

                sportsmen.add(new Sportsman(sportsmanName, sportsmanBirthday, s));
                
                NodeList currEvents = oneSportman.getChildNodes();
                for(int j = 0; j < currEvents.getLength(); j++){
                    if (currEvents.item(j).getNodeType() == Node.ELEMENT_NODE){
                        int result = Integer.parseInt(getTagValue("result", currEvents.item(j)));
                        String award = getTagValue("award", currEvents.item(j));
                        String place = currEvents.item(j).getAttributes().getNamedItem("place").getNodeValue();
                        String year = currEvents.item(j).getAttributes().getNamedItem("year").getNodeValue();
                        sportsmen.get(i).addEvent(place, year, result, award);
                    }
                }
            }
        }
        catch(Exception ex){
            System.out.println(ex.getMessage());
        }
        filterA(sportsmen);
        System.out.println();
        filterB(sportsmen);
        System.out.println();
        filterC(sportsmen);
        System.out.println();

        make_new_sportsman(sportsmen);

        try{
            Document newDoc = create_new_xml(sportsmen);
        } catch(Exception e){
            System.out.println(e.getMessage());
        }

    }

    public static String getTagValue(String tag, Node n) {
        Element el = (Element)n;
        NodeList nodeList = el.getElementsByTagName(tag).item(0).getChildNodes();
        Node node = (Node) nodeList.item(0);
        return node.getNodeValue();
    }

    public static void filterA(ArrayList<Sportsman> sp){
        System.out.println("Names and birth dates of all men:");
        for(int i = 0; i < sp.size(); i++){
            if (sp.get(i).getGender() == 'м'){
                System.out.println(sp.get(i).getName() + ", " + sp.get(i).getBirthday());
            }
        }
    }

    public static void filterB(ArrayList<Sportsman> sp){
        System.out.println("Names, births and medals of women:");
        for(int i = 0; i < sp.size(); i++){
            if (sp.get(i).getGender() == 'ж' && Integer.parseInt(sp.get(i).getBirthday().substring(0, 4)) < 1985){
            System.out.println(sp.get(i).getName() + ", " + sp.get(i).getBirthday() + ": ");
                for(int j = 0; j < sp.get(i).getEventsCount(); j++) {
                    System.out.print(sp.get(i).getEvents().get(j).getAward() + " ");
                }
            }
        }
        System.out.println();
    }

    public static void filterC(ArrayList<Sportsman> sp){
        System.out.println("Names and results of Moscow 2002 sportsmen:");
        for(int i = 0; i < sp.size(); i++){
            for(int j = 0; j < sp.get(i).getEventsCount(); j++){
                if (sp.get(i).getEvents().get(j).getPlace().equals("москва") && sp.get(i).getEvents().get(j).getYear() == 2002) {
                    System.out.println(sp.get(i).getName() + ", score: " + sp.get(i).getEvents().get(j).getResult());
                }
            }
        }
    }

    public static void make_new_sportsman(ArrayList<Sportsman> sp){
        String name = "", birthday = "";
        char gender;
        Scanner in = new Scanner(System.in);
        int eventsAmount;

        System.out.println("Adding new sportsman: ");
        System.out.print("The name is: ");
        name = in.nextLine();
        System.out.print("Date of birth: ");
        birthday = in.nextLine();
        System.out.print("The gender (m or f): ");
        gender = in.next().charAt(0);
        if (gender == 'm'){
            sp.add(new Sportsman(name, birthday, 'м'));
        } else {
            sp.add(new Sportsman(name, birthday, 'ж'));
        }

        System.out.print("How much events had this sportsman? ");
        eventsAmount = in.nextInt();
        for(int i = 0; i < eventsAmount; i++){
            String place, year, award;
            int result;
            System.out.println("Input place, year, result and award: ");
            place = in.next(); year = in.next();
            result = in.nextInt(); award = in.next();
            sp.get(sp.size()-1).addEvent(place, year, result, award);
        }
    }

    public static Document create_new_xml(ArrayList<Sportsman> sp) throws ParserConfigurationException {
        Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
        try {
            Element root = doc.createElement("team");
            doc.appendChild(root);

            for(int i = 0; i < sp.size(); i++){
                Element sportsman = doc.createElement("sportsman");
                sportsman.setAttribute("birthday", sp.get(i).getBirthday());
                sportsman.setAttribute("name", sp.get(i).getName());
                sportsman.setAttribute("s", String.valueOf(sp.get(i).getGender()));
                root.appendChild(sportsman);

                for (int j = 0; j < sp.get(i).getEventsCount(); j++){
                    Element event = doc.createElement("event");
                    sportsman.appendChild(event);
                    event.setAttribute("place", sp.get(i).getEvents().get(j).getPlace());
                    event.setAttribute("year", String.valueOf(sp.get(i).getEvents().get(j).getYear()));
                    Element result = doc.createElement("result");
                    result.setTextContent(String.valueOf(sp.get(i).getEvents().get(j).getResult()));
                    Element award = doc.createElement("award");
                    award.setTextContent(String.valueOf(sp.get(i).getEvents().get(j).getAward()));
                    event.appendChild(result);
                    event.appendChild(award);
                }
            }


            //saving to file
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("output.xml"));

            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.transform(source, result);
            System.out.println("Document saved");
            return doc;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return doc;
    }

}