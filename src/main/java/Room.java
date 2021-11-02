package worldofzuul;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashMap;
import java.util.Iterator;


public class Room
{
    private String description;
    private HashMap<String, Room> exits;
    ArrayList<Item> items = new ArrayList<>();

    public Room(String description)
    {
        this.description = description;
        exits = new HashMap<String, Room>();
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

    private String getExitString()
    {
        String returnString = "Exits:";
        Set<String> keys = exits.keySet();
        for(String exit : keys) {
            returnString += " " + exit;
        }
        //Udskriv hvad der kan samles i et rum
        returnString += "\nfølgende ting kan samles op:";
        returnString += getRoomItems();
        return returnString;
    }

    public Room getExit(String direction)
    {
        return exits.get(direction);
    }

    //Samle ting op fra rummet
    public Item getItem(int index)
    {
        return items.get(index);
    }
    public Item getItem(String itemName)
    {
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getName().equals(itemName))
            {
                return items.get(i);
            }
        }
        return null;
    }
    public void removeItem(String itemName)
    {
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getName().equals(itemName))
            {
                items.remove(i);
            }
        }
    }
    public void setItem(Item newitem)
    {
        items.add(newitem);
    }

    //En beskrivelse af hvad der er i et rum

    public String getRoomItems()
    {
        String output = "";
        for (int i = 0; i < items.size(); i++) {
            output += items.get(i).getName() + " ";
        }
        return output;
    }
}

