package worldofzuul;
public enum CommandWord
{
    GÅ("gå"), QUIT("quit"), HJÆLP("hjælp"), PLACE("place"), UKENDT("?"),TAG("tag"),INVENTAR("inventar");
    
    private String commandString;
    
    CommandWord(String commandString)
    {
        this.commandString = commandString;
    }
    
    public String toString()
    {
        return commandString;
    }
}
