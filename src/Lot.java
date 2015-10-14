package src;

public class Lot extends Property
{
	private final String color;					// color of the board piece
	private final int[] rent;
	private int rentIndex;
	private final int improve;
	
	public Lot(String name, int address, int cost, 
			   String color, int improve, int[] rent)
	{
		super(name, address, cost);
		this.color = color;
		this.rent = rent;
		this.rentIndex = 0;
		this.improve = improve;
	}

	@Override
	public void collectRent(Player player) 
	{
		int payment;
		
		payment = rent[rentIndex];
		
		player.transferMoneyTo(owner, payment);
	}
	
	public void improve()
	{
	}
	
	public void diminish()
	{
	}

	@Override
	public String[] getPossibleActions(Player player)
	{
		return null;
	}

	@Override
	public String toString()
	{
		return super.toString() + " color: " + color;
	}
}
