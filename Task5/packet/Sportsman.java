package packet;

import java.util.ArrayList;

public class Sportsman {
    private String name;
    private String birthday;
    private char gender;
    private ArrayList<Event> events = new ArrayList<Event>();
    
    public String getName() { return name; }
    public String getBirthday() { return birthday; }
    public char getGender() { return gender; }
    public ArrayList<Event> getEvents() { return events; }
    public int getEventsCount() { return events.size(); }
    
    public void setName(String name) { this.name = name; }
    public void setBirthday(String birthday) { this.birthday = birthday; }
    public void setGender(char gender) { this.gender = gender; }
    
    
    public void addEvent(String p, String y, int r, String a){
        Event newEvent = new Event(p, y, r, a);
        events.add(newEvent);
    }

    public Sportsman(){
        name = ""; birthday = ""; gender = 'м';
    }

    public Sportsman(String n, String b, char g){
        this.name = n; this.birthday = b; this.gender = g;
    }

    public String returnEventAward(int i){
        return events.get(i).getAward();
    }

    public int returnEventYear(int i){
        return events.get(i).getYear();
    }

    public String returnEventPlace(int i){
        return events.get(i).getPlace();
    }

    public int returnEventResult(int i){
        return events.get(i).getResult();
    }

    public String returnEvent(int i){
        return new String(events.get(i).getPlace() + " " + events.get(i).getYear() + " " + events.get(i).getResult() + " " + events.get(i).getAward());
    }
}