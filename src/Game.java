package src;

import javax.swing.*;
import java.awt.*;

public class Game extends JApplet
{
    private BoardLocation board;
    
    public void init()
    {
    	board = new Utility(100, "Water", 100);    	
    }
    
    @Override
    public void paint(Graphics g)
    {
    	super.paint(g);
    	
    	g.drawString(board.toString(), 20, 20);
    }
}
