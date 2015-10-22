//habib
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
	// POST: FCTVAL = an string array describing the actions that happening at this location
	{
		String []actions = {"There is no action you can do!"};
		return actions;
	}
}
