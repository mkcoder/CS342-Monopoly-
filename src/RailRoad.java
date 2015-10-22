// Programmer:  Maciej Szpakowski, Muhammad Habib Khan, Muhammad Khalil Khan
// Assignment:  Project 2, Monopoly
// Date:        Oct 20th, 2015
// Description: Applet class that models applet which contains entire front end (UI)
//              for the project
package src;

public class RailRoad extends Property
{
	private RailRoad[] others;							// the other railroads on the board
	
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
		int payment;
		
		payment = 25;
		for(RailRoad r : others)					// for all the other Railroad
		{
			if(r.getOwner() == this.owner) 			// is the current owner the same as the r owner
			{
				payment *= 2;
			}
		}
		
		player.transferMoneyTo(owner, payment);
	}

	@Override
	public String[] getPossibleActions(Player player)
	// PRE: player is initialized and
	// POST: FCTNVAL: return a string array of possible actions the player can do
	{
		return null;
	}
	
	
}
