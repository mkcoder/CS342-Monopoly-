import java.util.*;

public class Player
{
    // DATA DICTIONARY
    private final Token token;               //  token class
    private int money;                      //  how much money you have
    private BoardLocation location;         //  the current player location
    private List<Property> properties;      // the properties we own

    public Player(String token)
    {
        this.money = 0;
        this.location = BoardLocation.getGo();
        this.token = new Token(token);
        this.properties = new ArrayList<>();
    }

    public void addMoney(int money)
    {
        setMoney(getMoney()+money);
    }

    public void transferMoneyTo(Player player, int money)
    {
        this.addMoney(-money);
        player.addMoney(money);
    }

    public int getMoney()
    {
        return money;
    }

    public void setMoney(int money)
    {
        if ( money <= 0 ) return;
        this.money = money;
    }

    public List<Property> getProperties()
    {
        return properties;
    }

    public void setProperties(List<Property> properties)
    {
        this.properties = properties;
    }

    public BoardLocation getLocation()
    {
        return location;
    }

    public void setLocation(BoardLocation location)
    {
        this.location = location;
    }
}