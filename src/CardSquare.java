// Programmer:  Maciej Szpakowski, Muhammad Habib Khan, Muhammad Khalil Khan
// Assignment:  Project 2, Monopoly
// Date:        Oct 20th, 2015
// Description: CardSquare class that models a CardSquare object which includes Community Chest
//              and Chance.  This BoardLocation has the possibility of rewarding, or penalizing any
//              Player that lands here.
package src;

public class CardSquare extends BoardLocation
{
	private static int lastReward = 0; // amount for the last reward any player got
	
	public CardSquare(String name, int address)
	// PRE: name is an initialized string and  0 <= address <= 40
	// POST: a CardSquare object is initialized with the specified name and address
	{
		super(name, address);
	}
	
	public String reward(Player player)
	// PRE: player is an initialized Player object
	// POST: random amount between -200 and 200 is added 
	//       to the player's balance
	{		
		lastReward = (int)(Math.random()*401 - 200);   // get a random number between -200 and 200
		player.addMoney(lastReward);
		
		if(lastReward > 0)							   		// lastReward greater than 0
			return "You have been rewarded $" + Math.abs(lastReward);
		else if(lastReward < 0)								// lastReward less than than 0
			return "You have been penalized $" + Math.abs(lastReward);
		else												// you didn't get any reward
			return "Yoy got nothing";
	}

	@Override
	public String[] getPossibleActions(Player player)
	// POST: FCTVAL = an string array describing the actions that happening at this location
	{
		return new String[]{PICK_CARD};
	}
	
}
