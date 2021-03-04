package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import main.GameObject;
import main.MainLoop;

public class MapScreen extends GameObject {

	private Point mousePrev = null;
	
	private double scrollX = 0;
	private double scrollY = 0;
	
	@Override
	public void frameEvent () {
		
	}
	
	@Override
	public void draw () {
		
		//Get the target resolution
		int imgWidth = MainLoop.getWindow ().getResolution ()[0];
		int imgHeight = MainLoop.getWindow ().getResolution ()[1];
		
		//Render the thing
		BufferedImage renderImg = new BufferedImage (imgWidth, imgHeight, BufferedImage.TYPE_4BYTE_ABGR);
		Graphics g = renderImg.getGraphics ();
		g.setColor (new Color (0xFFFFFF));
		g.fillRect (0, 0, imgWidth, imgHeight);
		g.setColor (new Color (0x000000));
		for (int wx = 0; wx < MainLoop.getWindow ().getResolution ()[0]; wx += 24) {
			g.drawLine (wx, 0, wx, imgHeight);
		}
		for (int wy = 0; wy < MainLoop.getWindow ().getResolution ()[1]; wy += 24) {
			g.drawLine (0, wy, imgWidth, wy);
		}
		
		//Draw the image to the screen
		Graphics wg = MainLoop.getWindow ().getBufferGraphics ();
		wg.drawImage (renderImg, 0, 0, null);
		
	}
	
}
