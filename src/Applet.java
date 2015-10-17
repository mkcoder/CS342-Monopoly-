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
		
		if(getWidth() < getHeight())
			imgScale = getWidth() - 20;
		else
			imgScale = getHeight() - 20;
		
		g.drawImage(boardImage, OFFSET_X, OFFSET_Y, imgScale, imgScale, null);
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
