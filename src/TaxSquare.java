// Programmer:  Maciej Szpakowski, Muhammad Habib Khan, Muhammad Khalil Khan
// Assignment:  Project 2, Monopoly
// Date:        Oct 20th, 2015
// Description: A taxsquare that models the ability to collect tax from the other player.

package src;

public class TaxSquare extends BoardLocation
{
	private final int tax;			// the amount of tax to be collected from the player ($)
	
	public TaxSquare(String name, int address, int tax)
	// PRE: name, address, and tax is initialized tax >= 0, 0 <= address <= 15
	// POST: Create a new TaxSquare object with name set to name, address set to address and tax set to tax
	{
		super(name, address);
		this.tax = tax;
	}

	public int payTax(Player player)
    // PRE: player is initialized and is of type player
    // POST: subtracts the tax amount from the player and returns the tax amount collected
	{	    
	    player.addMoney(-1*tax);
	    return tax;
	}
	
	@Override
	public String[] getPossibleActions(Player player)
    // PRE: player must be initialized
    // POST: FCTVAL: return that player has been taxed
    {
		return new String[]{"You have been taxed $" + tax};
    }
}
