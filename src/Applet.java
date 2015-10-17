package src;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

public class Applet extends JApplet
{
	public static final String BOARD_FILE = "./src/mon.jpg";
	public static final int OFFSET_X = 10;
	public static final int OFFSET_Y = 10;
	private Point  [] coordArray;
	
	BufferedImage boardImage;
	
	
	
	public void init()
	{
		boardImage = getImage(BOARD_FILE);		
	}
	
	@Override
	public void paint(Graphics g)
	{
	    
		int imgScale;
		
		super.paint(g);
		
		 
		
		if((getWidth() - 130) < getHeight())
			imgScale = getWidth() - 150;
		else
			imgScale = getHeight() - 20;
		
		coordArray = getPoints(imgScale);
		
		g.drawImage(boardImage, OFFSET_X, OFFSET_Y, imgScale, imgScale, null);
		
		
	}
	
	private Point[] getPoints(int imageScale)
	{
	    Point [] tempCoord;
	    int divFactor;
	 
	    
	    tempCoord = new Point[40];
	    divFactor = imageScale/9;
	    
	    for(int i = 0; i < 11 ;i++)
	    {
	        tempCoord[i] = new Point(imageScale-divFactor*i,imageScale);
	    }
	    
	    for (int i = 11; i < 21; i++)
	    {
	        tempCoord[i] = new Point(0,imageScale-divFactor*(i-11));
        }
	    
	    for (int i = 21; i < 31; i++)
        {
            tempCoord[i] = new Point(divFactor*(i-21),0);
        }
	    
	    for (int i = 31; i < 40; i++)
        {
            tempCoord[i] = new Point(imageScale,divFactor*(i-31));
        }
	    
	    
	    
        return tempCoord;
	}
	
	public BufferedImage getImage(String fileName)
	{
		BufferedImage img;
		
		img = null;
		try 
		{
			img = ImageIO.read(new File(fileName));
		} 
		catch (IOException e)
		{
		}
		
		return img;
	}
}
