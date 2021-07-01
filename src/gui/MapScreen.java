package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.util.ArrayList;
import java.util.LinkedList;

import gameObjects.Mappable;
import main.GameObject;
import main.MainLoop;
import resources.Sprite;

public class MapScreen extends GuiComponent {

	private Point mousePrev = null;
	
	private int scrollX = 0;
	private int scrollY = 0;
	
	private static int CELL_SIZE = 32;
	private static int SCROLL_X_MIN = 0;
	private static int SCROLL_Y_MIN = 0;
	private static int SCROLL_X_MAX = 1024;
	private static int SCROLL_Y_MAX = 1024;
	
	private static double MAP_SCALE = .125;
	
	private BufferedImage terrain;
	
	public MapScreen () {
		
		//Make the terrain image
		terrain = new BufferedImage (SCROLL_X_MAX, SCROLL_Y_MAX, BufferedImage.TYPE_INT_ARGB);
		genTerrainImg ();
		
	}
	
	public void genTerrainImg () {
		
		//Notably assumes an image type of TYPE_INT_ARGB
		Graphics g = terrain.getGraphics ();
		WritableRaster r = terrain.getRaster ();
		for (int wx = 0; wx < SCROLL_X_MAX; wx++) {
			for (int wy = 0; wy < SCROLL_Y_MAX; wy++) {
				r.setDataElements (wx, wy, new int[] {((240) << 24) | ((wx % 256) << 16) | ((wy % 256) << 8) | ((wx + wy) % 256)});
			}
		}
		
	}
	
	@Override
	public void frameEvent () {
		
		//Handle dragging the screen
		if (keyCheck (KeyEvent.VK_SHIFT) && mouseButtonDown (1)) {
			if (mousePrev == null) {
				//If dragging just started
				mousePrev = new Point (getMouseX (), getMouseY ());
			} else {
				//Dragging has occured
				//Set the new scroll position accordingly
				double diffX = mousePrev.getX () - getMouseX ();
				double diffY = mousePrev.getY () - getMouseY ();
				scrollX += diffX;
				scrollY += diffY;
				//Bound the scroll position
				if (scrollX < SCROLL_X_MIN) {
					scrollX = SCROLL_X_MIN;
				}
				if (scrollX + MainLoop.getWindow ().getResolution ()[0] > SCROLL_X_MAX) {
					scrollX = SCROLL_X_MAX - MainLoop.getWindow ().getResolution ()[0];
				}
				if (scrollY < SCROLL_Y_MIN) {
					scrollY = SCROLL_Y_MIN;
				}
				if (scrollY + MainLoop.getWindow ().getResolution ()[1] > SCROLL_Y_MAX) {
					scrollY = SCROLL_Y_MAX - MainLoop.getWindow ().getResolution ()[1];
				}
				//Update mouse coords
				mousePrev = new Point (getMouseX (), getMouseY ());
			}
		} else {
			mousePrev = null;
		}
		
		if (mouseClicked ()) {
			int cellX = (getMouseX () + scrollX) / CELL_SIZE;
			int cellY = (getMouseY () + scrollY) / CELL_SIZE;
		}
		
	}
	
	@Override
	public void draw () {
		
		//Get the target resolution
		int imgWidth = MainLoop.getWindow ().getResolution ()[0];
		int imgHeight = MainLoop.getWindow ().getResolution ()[1];
		
		//Get the image to render to and its graphics
		BufferedImage renderImg = new BufferedImage (imgWidth, imgHeight, BufferedImage.TYPE_4BYTE_ABGR);
		Graphics g = renderImg.getGraphics ();
		
		//Draw the cells
		g.drawImage (terrain, 0, 0, imgWidth, imgHeight, scrollX, scrollY, scrollX + imgWidth, scrollY + imgHeight, null);
		
		//Draw the map displayable objects
		ArrayList<GameObject> mapObjs = MainLoop.getObjectMatrix ().getAll (Mappable.class);
		for (int i = 0; i < mapObjs.size (); i++) {
			GameObject curr = mapObjs.get (i);
			if (((Mappable)curr).isHiddenOnMap ()) {
				Sprite icon = ((Mappable)curr).getMapIcon ();
				icon.draw ((int)(curr.getX () * MAP_SCALE) - scrollX, (int)(curr.getY () * MAP_SCALE) - scrollY);
			}
		}
		
		//Draw the grid overlay
		g.setColor (new Color (0x000000));
		for (int wx = -scrollX % CELL_SIZE; wx < MainLoop.getWindow ().getResolution ()[0]; wx += CELL_SIZE) {
			g.drawLine (wx, 0, wx, imgHeight);
		}
		for (int wy = -scrollY % CELL_SIZE; wy < MainLoop.getWindow ().getResolution ()[1]; wy += CELL_SIZE) {
			g.drawLine (0, wy, imgWidth, wy);
		}
		
		//Draw the image to the screen
		Graphics wg = MainLoop.getWindow ().getBufferGraphics ();
		wg.drawImage (renderImg, 0, 0, null);
		
	}

	@Override
	public String getComponentId () {
		return "map_screen";
	}
	
}
