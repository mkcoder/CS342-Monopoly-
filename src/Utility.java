// Programmer:  Maciej Szpakowski, Muhammad Habib Khan, Muhammad Khalil Khan
// Assignment:  Project 2, Monopoly
// Date:        Oct 20th, 2015
// Description: A utility class that models an utility. This class lets you collect rent from the other person.

package src;

public class Utility extends Property
{
	private Utility other;           // the other utility the person can also purchase
	
	public Utility(String name, int address, int cost)
	// PRE: name, address, and cost is initialized. 0 <= address <= 39, cost >= 0
    // POST: create a new utility object that has name set to name, address set to address and cost set to cost
	{
		super(name, address, cost);
	}

	@Override
	public String collectRent(Player player)
    // PRE:  player is initialized and is of type player
    // POST: subtract amount of rent from the player current earnings ($)
	{		
		lastRent = other.owner == this.owner ? Player.getDice() * 10 : Player.getDice() * 4;
		player.transferMoneyTo(owner, lastRent);
		
		return "You paid $" + lastRent + " rent to " + owner.getToken();
	}
	
	public static void setOther(Utility water, Utility electric)
    // PRE: wave and electric is initialized
    // POST: does a basic swap by setting each other to each other
	{
		water.other = electric;
		electric.other = water;
	}
	
	@Override
	public String toString()
    // POST: returns a string object
    // fctnval == return a formatted string with location + " COST $" + cost + " Owned by " + player " + ".";
	{
		return super.toString();
	}
	
}
