//khalil
package src;

public class RailRoad extends Property
{
    private RailRoad[] others;

    public RailRoad(String name, int address, int cost)
    {
        super(name, address, cost);
        others = new RailRoad[3];
    }

    public static void setOthers(RailRoad rr1, RailRoad rr2, RailRoad rr3, RailRoad rr4)
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
    {
        int payment;

        payment = 25;
        for(RailRoad r : others)
        {
            if(r.getOwner() == this.owner)
            {
                payment *= 2;
            }
        }

        player.transferMoneyTo(owner, payment);
    }

    @Override
    public String[] getPossibleActions(Player player)
    {
        return null;
    }
}
