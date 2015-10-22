// Programmer:  Maciej Szpakowski, Muhammad Habib Khan, Muhammad Khalil Khan
// Assignment:  Project 2, Monopoly
// Date:        Oct 20th, 2015
// Description: Lot class that models a Lot object which includes properties
//              that allow a Player to improve and diminish this BoardLocation
package src;

public class Lot extends Property
{
	private final String color;					// color of the board piece
	private final int[] rent;                   // array with different possible rents	
	private final int improveCost;              // Cost of improving this lot	
	private int rentIndex;                      // The rent we should be charging
	                                            // current housing level of the lot

    public Lot(String name, int address, int cost, 
			   String color, int improve, int[] rent)
    // PRE: name and color are intialized strings,
	//      0 <= address <= 40, improve and rent are both intialized		   
    //POST: a lot object with the given values is intialized
	{
		super(name, address, cost);
		this.color = color;
		this.rent = rent;
		this.rentIndex = 0;
		this.improveCost = improve;
	}

	@Override
	public String collectRent(Player player) 
    //PRE: player is an initialized object
	//POST: The value specified at rent[rentIndex] is removed from player.
	//      The amount of rent is added to owner's balance
	{
		lastRent = rent[rentIndex];
		player.transferMoneyTo(owner, rent[rentIndex]);
		
		return "You paid $" + lastRent + " rent to " + owner.getToken();
	}
	
	@Override
	public int getRent()
	// POST: FCTVAL: returns current rent for this property
	{
		return rent[rentIndex];
	}
	
	public boolean improve()
	// PRE: It is assumed that the player has met the necessary requirement in order to build
	//      the houses and hotels
    //      The player is improving their properties evenly meaning they improve their lots one house at a time 
    //      and has bought the all the properties in their color group
	// POST:   FCTVAL = a boolean that is true if lot has been improved, false o.w
	{
		if(owner != null && owner.getMoney() >= improveCost && // the owner has enough money and  
		   rentIndex < rent.length - 1)                        // housing level is less than hotel
		{
			rentIndex++;                     // increment the rent counter
			owner.addMoney(-1*improveCost);  // remove the improve cost from the owner's balance 
			return true;
		}
		return false;
	}
	
	public int getRentIndex()
	// POST: FCVTVAL = the rentIndex, i.e how many house/hotels should be here
	{
        return rentIndex;
    }

    public boolean diminish()
    // PRE: The owner has at least one house
    //      The player can only diminish their properties evenly
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
    // POST: FCTVAL = the int improveCost
    {
        return improveCost;
    }
    
    public String getHousingLevel()
    // POST: FCTVAL: returns a string of how many houses/hotels are there 
    {
    	if(rentIndex == 0) // no houses
    	{
    		return "No houses";
    	}
    	if(rentIndex < 5) // 1-4 houses
    	{
    		return rentIndex + " houses";
    	}
    	else // 1 hotel
    	{
    		return "1 hotel";
    	}
    }

	@Override
	public String toString()
	// POST: FCTVAL = A string the describes property's name, address, cost.
	{
		return super.toString() + " Color: " + color + ".";
	}
}
