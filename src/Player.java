package src;

import java.util.*;

public class Player
{
    // DATA DICTIONARY
	private static final int INITIAL_MONEY = 1500;
	private static int dice = 0;			// dice value
    private final String token;				// token
    private int money;						// how much money you have
    private BoardLocation location;			// the current player location
    private List<Property> properties;		// the properties we own
    private boolean bankrupt;				// is the person bankrupt
    
    public Player(String token, BoardLocation go)
    {
        this.money = INITIAL_MONEY;
        this.location = go;
        this.token = token;
        this.properties = new ArrayList<>();
    }
    
    public boolean buyLocation(Property property)
    {
        if ( property.getCost() <= money &&
             property.getOwner() == null )
        {
            properties.add(property);
            addMoney(-property.getCost());
            property.setOwner(this);
            return true;
        }
        return false;
    }

    public void sellLocation()
    {                         
    	// TODO: not required!    
    }
    
    public void move(int n)
    {
        for (int i = 0; i < n; i++)
        {
            location = location.getNext();
            if (location.getName().equals("Go"))
            {
                addMoney(200);
            }
        }

        if ( location instanceof Property &&
             ((Property)(location)).isOwned())
        {
            ((Property)(location)).collectRent(this);
        }
    }

    /*public List<Property> getProperties()
    {
        return properties;
    }*/

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

	public int getDice()
	{
		return dice;
	}
	
	public String getToken()
	{
		return token;
	}
	
	@Override
	public String toString()
	{
		if(!bankrupt)
			return "Player: " + token + " has $" + money;
		else
			return "Player: " + token + " is broke :(";
	}
}