// Programmer:  Maciej Szpakowski, Muhammad Habib Khan, Muhammad Khalil Khan
// Assignment:  Project 2, Monopoly
// Date:        Oct 20th, 2015
// Description: CornerSquare class that models a CornerSquare object which includes Go to Jail,
//              free parking, etc. This BoardLocation doesn't really do anything.
package src;

public class CornerSquare extends BoardLocation
{	
	public CornerSquare(String name, int address)
	// PRE: name is an initialized string and  0 <= address <= 40
    // POST: a CornerSquare object is initialized with the specifed name and address
	{
		super(name, address);
	}

	@Override
	public String[] getPossibleActions(Player player)
	// POST: FCTVAL = an string array describing the actions for corner
	{
		return new String[]{CORNER};
	}
}
