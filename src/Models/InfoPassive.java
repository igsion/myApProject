package Models;

public class InfoPassive {

    private String name, description;
    private int id;

    public InfoPassive(String name, String description, int id) {
        this.name = name;
        this.description = description;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getId(){
        return id;
    }
}
