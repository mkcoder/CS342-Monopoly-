package src;

public class TaxSquare extends BoardLocation
{
	private final int tax;
	
	public TaxSquare(String name, int address, int tax)
	{
		super(name, address);
		this.tax = tax;
	}

	@Override
	public String[] getPossibleActions(Player player)
	{
		// TODO Auto-generated method stub
		return null;
	}
	
}