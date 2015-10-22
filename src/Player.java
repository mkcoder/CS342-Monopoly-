// Programmer:  Maciej Szpakowski, Muhammad Habib Khan, Muhammad Khalil Khan
// Assignment:  Project 2, Monopoly
// Date:        Oct 20th, 2015
// Description: Player class that models a Player object. This class holds the data
//              for which properites this player owns and what their balance is at the 
//              moment.
//            
package src;

import java.util.*;

public class Player
{
	private static final int INITIAL_MONEY = 1500; // amount of money each player get on start
	private static int dice = 0;			// current dice value
    private final String token;				// token name
    private int money;						// amount of money you have
    private BoardLocation location;			// the current player location
    private List<Property> properties;		// the properties player owns
    private boolean bankrupt;				// flag indicating is the player bankrupt
    
    public Player(String token, BoardLocation go)
    //PRE: token is an initialized string and that Boardlocation
    //     is the the Boardlocation object for "go"
    //POST: A player objects is initialized at go with initial money INITIAL_MONEY, the 
    //      specified token and an empty list of properties
    {
        this.money = INITIAL_MONEY;
        this.location = go;
        this.token = token;
        this.properties = new ArrayList<>();
    }
    
    public boolean buyLocation(Property property)
    // PRE: property is an initalized Property object
    // POST: FCTVAL = TRUE if player can buy, FALSE o.w
    //       The given property will be added to the player's 
    //       property list. The property will be marked as 
    //       owned, and the cost of the propert will be
    //       subtracted from the player's money.
    {
        if ( property.getCost() <= money &&     //The location is not owned and 
             property.getOwner() == null )      //the player has enough money to buy it
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
    	// not required!
    }
    
    public String move(int n)
    // PRE: n is initialized
    // POST: FCTVAL = String with information about any actions the user took 
    //      Player will be moved n spaces, and any rent/taxes will
    //       be collected;
    {
        String result;          //The string that describes the player's actions
        int money;              // A local copy to store the player's money
        
        
        result = "";
        money = this.getMoney();
                
        for (int i = 0; i < n; i++)      //Move the player n spaces
        {
            location = location.getNext();
            if (location.getName().equals("Go")) //Check to see if GO is passed
            {
                addMoney(200);                   //Add the salary
                result += "You just went passed GO and got $200.\n";
            }
        }

        if (location instanceof Property &&    //If the new location is a property and 
           ((Property)location).isOwned())     // it's owned, pay any fines. Append to the result
                                               //string
        {            
            ((Property)(location)).collectRent(this);
            
            result += String.format("I just paid %d in rent to %s.\n", 
            		(money - getMoney()), ((Property)location).getOwner().getToken());
        }
        else if (location instanceof Property) //If the location is a buyable property,
                                               //indicate to the player they can buy it.
        {
            result += location.getName() + "\n(Cost $" + ((Property) location).getCost() + ") "
                    + "is not owned.\nYou can purchase it.\n";
        }
        else if(location instanceof CardSquare) //If the location is a CardSquare,
                                                //then apply whatever penalty is needed
        {
        	((CardSquare)(location)).reward(this);      
        	                                             //If your money is less, then you were penalized, else rewarded
        	result += String.format("You were %s $%d.\n", 
    	            (money - getMoney() < 0) ? "penalized" : "rewarded", 
    	            Math.abs(money - getMoney()));
        }
        else if (location instanceof TaxSquare)
            
        {
            result += "You have been taxed $" + ((TaxSquare)location).payTax(this) + ".\n";
        }
        else
        {
            result += "You are on a corner.\n";
        }
        
        return result;
    }

    public List<Property> getProperties()
    // POST:FCTVAL = the properties owned by the player
    {
        return properties;
    }

    public void addMoney(int money)
    // PRE: money is some integer value
    // POST: the specified value will be added to player's balance
    //       or subtracted if value is negative
    {
        setMoney(this.money+money);
    }

    public void transferMoneyTo(Player recipient, int money)
    //PRE: player and money are initialized
    //POST:  the specifed value will be taken from this player and added to 
    //       recipient's money
    {
        this.addMoney(-1 * money);
        recipient.addMoney(money);
    }

    public int getMoney()
    // POST:FCTVAL = the balance of the player as an integer
    {
        return money;
    }

    public void setMoney(int money)
    // PRE: money is initialized
    // POST: the specifed money will be added to the player's balance
    //       This method also checks to see if the player has a negative balance
    {
        this.money = money;
        if (this.money<0)
        {
            declareBankruptcy();
        }        
    }

    private void declareBankruptcy()
    // POST: Keep selling houses/hotels until this.money >= 0. If the player
    //       is still broke after the initial downgrading, then all their
    //       Properties are returned to the bank.
    {
        for(Property p: properties)
        {
            if(p instanceof Lot &&  ((Lot) p).getRentIndex() > 0) // property that has houses/hotels
            {
                while(((Lot) p).getRentIndex() > 0 && this.money < 0) // keep selling/houses hotels
                	                                                  // until this.money >= 0
                {
                    ((Lot) p).diminish();
                }
            }
        }
        
        if(this.money < 0)     //the player is still, broke
        {
            this.bankrupt = true;
            for(Property p: properties)  //Set each of the player's properties to null
            {
                p.setOwner(null);
            }
        }
    }

    public BoardLocation getLocation()
    //  POST:FCTVAL = the Boardlocation of this player
    {
        return location;
    }

    public void setLocation(BoardLocation location)
    // PRE: location is an intialized Boardlocation
    // POST: the player's location is updated.
    {
        this.location = location;
    }

    public boolean isBankrupt()
    //  POST:FCTVAL = a boolean if the player is bankrupt or not
    {
        return bankrupt;
    }

	public static int getDice()
	//  POST:FCTVAL = the value of dice is returned as an int
	{
		return dice;
	}
	
	public static void setDice(int dice)
	//PRE:  1*numOfDice < dice <= 6*numOfDice
	//POST: The value of dice is updated
	{	  
        Player.dice = dice;
    }

    public String getToken()
    //  POST:FCTVAL = a String with the player's token
	{
		return token;
	}
	
	@Override
	public String toString()
	//  POST:FCTVAL = A string the describes the player's token and money.
	//         If the player is broke, then that is indicated
	{
		if(!bankrupt)
			return "Player: " + token + " has $" + money;
		else
			return "Player: " + token + " is broke :(";
	}
}