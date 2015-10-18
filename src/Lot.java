package src;

public class Lot extends Property
{
	private final String color;					// color of the board piece
	private final int[] rent;
	private int rentIndex;
	private final int improveCost;
	
	public Lot(String name, int address, int cost, 
			   String color, int improve, int[] rent)
	{
		super(name, address, cost);
		this.color = color;
		this.rent = rent;
		this.rentIndex = 0;
		this.improveCost = improve;
	}

	@Override
	public void collectRent(Player player) 
	{
		int payment;
		
		payment = rent[rentIndex];
		
		player.transferMoneyTo(owner, payment);
	}
	
	public boolean improve()
	{
		if(owner != null && owner.getMoney() >= improveCost && rentIndex < rent.length - 1)
		{
			rentIndex++;
			owner.addMoney(-1*improveCost);
			return true;
		}
		return false;
	}
	
	public int getRentIndex()
	{
        return rentIndex;
    }

    public boolean diminish()
	{
		if(owner != null && rentIndex > 0)
		{
			rentIndex--;
			owner.addMoney(improveCost/2);
			return true;
		}
		return false;
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
