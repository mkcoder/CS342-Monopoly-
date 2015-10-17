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
	public static final String CAR = "./src/car.png";
	public static final int OFFSET_X = 10;
	public static final int OFFSET_Y = 10;
	private Point  [] coordArray;
	
	BufferedImage boardImage;
	BufferedImage carImage;
	
	
	public void init()
	{
		boardImage = getImage(BOARD_FILE);
		carImage = getImage(CAR);
		
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
		//g.drawImage(carImage, OFFSET_X, OFFSET_Y, null);
		
		for(Point p: coordArray)
		{
		    g.drawImage(carImage, p.x, p.y,imgScale/20,imgScale/20 , null);
		}
		
	}
	
	private Point[] getPoints(int imageScale)
	{
	    Point [] tempCoord;
	    double divFactor;
	 
	    
	    tempCoord = new Point[40];
	    divFactor = (imageScale/10) * (0.825);
	    
	    
	    for(int i = 0; i < 11 ;i++)
	    {
	        tempCoord[i] = new Point( (int) (imageScale-divFactor*i -divFactor*1.5),(int) (imageScale-divFactor));
	    }
	    
	    for (int i = 11; i < 21; i++)
	    {
	        tempCoord[i] = new Point((int) divFactor/2,(int) (imageScale-divFactor*(i-10)-divFactor*1.5));
        }
	    
	    for (int i = 21; i < 31; i++)
        {
            tempCoord[i] = new Point((int) (divFactor*(i-20) +divFactor*1),(int) (divFactor/2));
        }
	    
	    for (int i = 31; i < 40; i++)
        {
            tempCoord[i] = new Point((int) (imageScale-divFactor),(int) (divFactor*(i-30)+divFactor*.9));
        }
	    
	    System.out.println("****************"+imageScale+"*******************************");
	    for(Point p: tempCoord)
	    {
	        p.x = p.x + OFFSET_X;
	        p.y = p.y + OFFSET_Y;
	        
	         
	        System.out.println((p.x) + " " + p.y);
	    }
	    System.out.println();
	    
	    
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
