//habib
package src;

public class Lot extends Property
{
	private final String color;					// color of the board piece
	private final int[] rent;                   // array with different possible rents
	private int rentIndex;                      // The rent we should be charging
	private final int improveCost;              // Cost of improving this lot
	
	

    public Lot(String name, int address, int cost, 
			   String color, int improve, int[] rent)
    // PRE: name and color are intialized strings,
	//      0 < address <= 40, improve and rent are both intialized		   
    //POST: a lot object with the given values is intialized
	//		   
	{
		super(name, address, cost);
		this.color = color;
		this.rent = rent;
		this.rentIndex = 0;
		this.improveCost = improve;
	}

	@Override
	public void collectRent(Player player) 
    //PRE: player is an initialized object, money is an integer value.
	//POST: The value speciifed at rent[rentIndex] is removed from player.
	//      The amount of rent is added to owner's balance
	{
		int payment;       //The amount of money being removed/added
		
		payment = rent[rentIndex];
		
		player.transferMoneyTo(owner, payment);
	}
	
	public boolean improve()
	// PRE: It is assumed that the player has the necessary requirement in order to build
	//      the houses and hotels. 
	// POST:   FCTVAL = a boolean that is true if owner can improve lot, false o.w
	{
		if(owner != null && owner.getMoney() >= improveCost &&     // If the owner has enough money and  
		   rentIndex < rent.length - 1)                            // we still can improve, then improve the lot
		{
			rentIndex++;                     // increment the rent counter
			owner.addMoney(-1*improveCost);  // remove the improve cost from the owner's balance 
			return true;
		}
		return false;
	}
	
	public int getRentIndex()
	// FCVTVAL = the rentIndex, i.e how many house/hotels should be here
	{
        return rentIndex;
    }

    public boolean diminish()
    // PRE: The owner has at least one house
    // POST: Half the improveCost will be added to the player's balance
	{
		if(owner != null && rentIndex > 0) // The property is owned and has at least 1 house
		{
			rentIndex--;
			owner.addMoney(improveCost/2);
			return true;
		}
		return false;
	}
    
    public int getImproveCost() 
    // FCTVAL = the int improveCost
    {
        return improveCost;
    }

	@Override
	public String[] getPossibleActions(Player player)
	// FCTVAL = an string array describing the actions that happening at this location
	{
	    String []actions = {"Improve property, Diminish Property, Buy Property"};
		return actions;
	}

	@Override
	public String toString()
	// FCTVAL = A string the describes properties name, address, cost.         
	{
		return super.toString() + " Color: " + color + ".";
	}
}
