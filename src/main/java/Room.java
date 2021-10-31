package worldofzuul;
import java.util.Set;
import java.util.HashMap;
import java.util.Iterator;


public class Room 
{
    private String description;
    private String name;
    private String dropOff;
    private HashMap<String, Room> exits;

    public Room(String description) 
    {
        this.description = description;
        exits = new HashMap<String, Room>();
    }

    public Room(String description,String dropOff,String name)
    {
        this.description = description;
        this.dropOff = dropOff;
        exits = new HashMap<String, Room>();
        this.name = name;
    }

    public void setExit(String direction, Room neighbor) 
    {
        exits.put(direction, neighbor);
    }

    public String getShortDescription()
    {
        return description;
    }

    public String getLongDescription()
    {
        return "Du er " + description + ".\n" + getExitString();
    }

    public String getName() { return name; }

    private String getExitString()
    {
        String returnString = "Exits:";
        Set<String> keys = exits.keySet();
        for(String exit : keys) {
            returnString += " " + exit;
        }
        return returnString;
    }

    public Room getExit(String direction) 
    {
        return exits.get(direction);
    }

    public String getDropoff() {
        return this.dropOff;
    }
}

