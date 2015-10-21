//habib
package src;

public class CardSquare extends BoardLocation
{
	public CardSquare(String name, int address)
	// PRE: name is an initialized string and  0 <= address <= 40
	// POST: a CardSquare object is initialized with the specifed name and address
	{
		super(name, address);
	}
	
	public int reward(Player player)
	// PRE: player is an initialized Player object
	// POST: FCTVAL = a random amount between 0 and 200 is added 
	//                to the player's balance
	{
		int amount;       // amount to be added
		
		amount = (int)(Math.random()*401 - 200);   // get a random number between 0 and 200
		player.addMoney(amount);
		
		return amount;
	}

	@Override
	public String[] getPossibleActions(Player player)
	// FCTVAL = an string array describing the actions that happening at this location
	{
	    String []actions = {"be rewarded"};
		return actions;
	}
	
}
