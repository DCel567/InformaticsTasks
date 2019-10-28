package packet;


public class Book {
    private String name;
    private String autorName;
    private int pages;

    public String getName(){ return name; }
    public void setName(String n){ name = n; }
    public String getAutorName(){ return autorName; }
    public int getPages(){ return pages; }
    public void setPages(int p){ pages = p; }

    public Book(){
        name = "";
        autorName = "";
        pages = 0;
    }

    public Book(String n, String aN, int p){
        name = n;
        autorName = aN;
        pages = p;
    }

    public void print_book(){
        System.out.println(this.getName() + " Autor: " + this.getAutorName());
    }
}