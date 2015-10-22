// Programmer:  Maciej Szpakowski, Muhammad Habib Khan, Muhammad Khalil Khan
// Assignment:  Project 2, Monopoly
// Date:        Oct 20th, 2015
// Description: CardSquare class that models a CardSquare object which includes Community Chest
//              and Chance.  This BoardLocation has the possibility of rewarding, or penalizing any
//              Player that lands here.
package src;

public class CardSquare extends BoardLocation
{
	public CardSquare(String name, int address)
	// PRE: name is an initialized string and  0 <= address <= 40
	// POST: a CardSquare object is initialized with the specified name and address
	{
		super(name, address);
	}
	
	public int reward(Player player)
	// PRE: player is an initialized Player object
	// POST: FCTVAL = a random amount between -200 and 200 is added 
	//                to the player's balance and returned
	{
		int amount;       // amount to be added
		
		amount = (int)(Math.random()*401 - 200);   // get a random number between -200 and 200
		player.addMoney(amount);
		
		return amount;
	}

	@Override
	public String[] getPossibleActions(Player player)
	// POST: FCTVAL = an string array describing the actions that happening at this location
	{
	    String []actions = {"be rewarded"};
		return actions;
	}
	
}
