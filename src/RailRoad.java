// Programmer:  Maciej Szpakowski, Muhammad Habib Khan, Muhammad Khalil Khan
// Assignment:  Project 2, Monopoly
// Date:        Oct 20th, 2015
// Description: A class that models the RailRoads.
// This class allows you set the other rails roads. Collect rent from other player.

package src;

public class RailRoad extends Property
{
	private RailRoad[] others;        // the other railroads on the board
	private static int lastRent = 0;  // last rent any player paid for RR
	
	public RailRoad(String name, int address, int cost)
	// PRE: name, address, and cost is initialized, 0 <= address <= 39, and cost >= 0
	// POST: FCTNVAL: creates a new Railroad object with name set to name,
	// address set to address and cost set to cost and other set new a array of RailRoads and size 3
	{
		super(name, address, cost);
		others = new RailRoad[3];
	}	
	
	public static void setOthers(RailRoad rr1, RailRoad rr2, RailRoad rr3, RailRoad rr4)
	// PRE: rr1, rr2, rr3, rr4 are initialized and correspond to different railroads on the board
	// POST: sets the other to railroads
	{
		rr1.others[0] = rr2;
		rr1.others[1] = rr3;
		rr1.others[2] = rr4;
		
		rr2.others[0] = rr1;
		rr2.others[1] = rr3;
		rr2.others[2] = rr4;
		
		rr3.others[0] = rr1;
		rr3.others[1] = rr2;
		rr3.others[2] = rr4;
		
		rr4.others[0] = rr1;
		rr4.others[1] = rr2;
		rr4.others[2] = rr3;
	}

	@Override
	public void collectRent(Player player)
	// PRE: player is initialized, player is another person we are collecting rent from
	// POST: collects the rent from the player, by building up the payment using others
	// and if the other player is equal this owner we multiple the payment by 2
	{		
		lastRent = 25;
		for(RailRoad r : others)					// for all the other Railroad
		{
			if(r.getOwner() == this.owner) 			// is the current owner the same as the r owner
			{
				lastRent *= 2;
			}
		}
		
		player.transferMoneyTo(owner, lastRent);
	}

	@Override
	public String[] getPossibleActions(Player player)
	// PRE: player must be initialized
    // POST: FCTVAL: return array of all possible actions player can perform for that RR
	//               or reports what rent this player paid for that RR
    {    	
    	if(owner == null) // not owned
    	{
    		return new String[]{"Can be purchased."};
    	}
    	else if(owner == player) // owned and player is the owner
    	{
    		return new String[]{"You own this."};
    	}
    	else // owned and player is not the owner
    	{
    		return new String[]{String.format("I just paid %d in rent to %s.", 
    				lastRent, owner)};
    	}
    }
}
