import java.util.*;

public class Player
{
    // DATA DICTIONARY
    private final Token token;               //  token class
    private int money;                      //  how much money you have
    private BoardLocation location;         //  the current player location
    private List<Buyable> properties;      // the properties we own
    private boolean bankrupt;

    public Player(String token)
    {
        this.money = 0;
        this.location = Game.getGo();
        this.token = new Token(token);
        this.properties = new ArrayList<>();
    }

    public boolean buyLocation(Buyable buyable)
    {
        if ( buyable.getCost() <= money &&
             buyable.getOwner() == null )
        {
            properties.add(buyable);
            addMoney(-buyable.getCost());
            buyable.setOwner(this);
            return true;
        }
        return false;
    }

    public void move(int n)
    {
        for (int i = 0; i < n; i++)
        {
            location = location.getNext();
            if (location==Game.getGo())
            {
                addMoney(200);
            }
        }

        // location is someone else property?
        if ( location instanceof Buyable &&
             ((Buyable)(location)).isOwned())
        {
            ((Buyable)(location)).collectRent(this);
        }
    }

    public List<Buyable> getProperties()
    {
        return properties;
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
        if (this.money+money<0)
        {
            declareBankruptcy();
        }

        this.money = money;
    }

    private void declareBankruptcy()
    {
        this.bankrupt = true;
    }

    public BoardLocation getLocation()
    {
        return location;
    }

    public void setLocation(BoardLocation location)
    {
        this.location = location;
    }

    public boolean isBankrupt()
    {
        return bankrupt;
    }
}