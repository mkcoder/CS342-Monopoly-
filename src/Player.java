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
            addMoney(-1 * property.getCost());
            property.setOwner(this);
            return true;
        }
        return false;
    }

    public void sellLocation()
    {                         
    	// TODO: not required!    
    }
    
    public String move(int n)
    {
        String s;
        String result;        
        int money; 
        result = "";
        money = this.getMoney();
                
        for (int i = 0; i < n; i++)
        {
            location = location.getNext();
            if (location.getName().equals("Go"))
            {
                addMoney(200);
                result += "I just went passed GO.\n";
            }
        }

        if ( location instanceof Property &&
             ((Property)(location)).isOwned())
        {            
            ((Property)(location)).collectRent(this);
            
            result += "I just paid " + (money-getMoney()) + " in rent to ."+((Property)location).getOwner().getToken()+"\n";
        }
        else if (location instanceof Property)
        {
            result += "This property is not owned. You can purchase it!\n";
        }
        else if(location instanceof CardSquare)
        {
        	((CardSquare)(location)).reward(this);        	
        	s = String.format("You were %s $%d.\n", 
        	            (money-getMoney() < 0) ? "penalized" : "rewarded",
        	            Math.abs(money-getMoney()));        	
        	result += s;
        }
        else if (location instanceof TaxSquare)
        {
            result += "You have been taxed $"+((TaxSquare)location).payTax(this)+"!\n";
        }
        else
        {
            result += "You are on a corner.\n";
        }
        return result;
    }

    public List<Property> getProperties()
    {
        return properties;
    }

    public void addMoney(int money)
    {
        setMoney(this.money+money);
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
        this.money = money;
        if (this.money<0)
        {
            declareBankruptcy();
        }

        
    }

    private void declareBankruptcy()
    {
        for(Property p: properties)
        {
            if(p instanceof Lot &&  ((Lot) p).getRentIndex() > 0)
            {
                while(((Lot) p).getRentIndex() > 0 && this.money < 0)
                {
                    ((Lot) p).diminish();
                }
            }
        }
        
        if(this.money < 0)
        {
            this.bankrupt = true;
            for(Property p: properties)
            {
                p.setOwner(null);
            }
        }
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

	public static int getDice()
	{
		return dice;
	}
	
	public static void setDice(int dice) {
        Player.dice = dice;
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