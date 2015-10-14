// 

package src;

import javax.swing.*;
import java.awt.*;

public class Game extends JApplet
{
    private BoardLocation head;
    
    public void init()
    {
    	/*board = new BoardLocation[]
		{
			new CornerSquare("Go", 0),
			new Lot("Mediterrenean Ave.", 1, 60, "Dark Purple", 
					50, new int[]{2,10,30,90,160,230}),
			new CardSquare("Community chest", 2),
			new Lot("Baltic Ave.", 3, 60, "Dark Purple", 50,
					new int[]{4,20,60,180,320,450}),
			new TaxSquare("Income tax", 4, 200),
			
		};*/
    }
    
    @Override
    public void paint(Graphics g)
    {
    	super.paint(g);
    }
}
