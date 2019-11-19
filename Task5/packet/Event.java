package packet;

public class Event{
    private String place;
    private String year;
    private int result;
    private String award;

    public String getPlace() { return place; }
    public int getYear() { return Integer.parseInt(year); }
    public int getResult() { return result; }
    public String getAward() { return award; }

    public Event(){
        place = ""; year = ""; result = 0; award = "";
    }

    public Event(String p, String y, int r, String a){
        place = p; year = y; result = r; award = a;
    }
}