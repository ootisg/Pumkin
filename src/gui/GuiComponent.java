package gui;

import java.util.ArrayList;

import main.GameObject;
import main.MainLoop;
import main.TextInterface;
import resources.Sprite;

public abstract class GuiComponent extends GameObject {
	private boolean focus;
	private TextInterface textInterface;
	protected GuiComponent () {
		this (null);
		setPriority (-3);
	}
	protected GuiComponent (Sprite background) {
		if (background != null) {
			this.setSprite (background);
		}
		focus = false;
		this.setPersistent (true);
	}
	public void focus () {
		this.focus = true;
	}
	public void unfocus () {
		this.focus = false;
	}
	public void keyEvent (char c) {
		
	}
	public void clickEvent (int clickX, int clickY) {
		
	}
	public void guiFrame () {
		
	}
	public void renderBackground () {
		drawSprite ();
	}
	public void renderElements () {
		
	}
	public boolean hasFocus () {
		return focus;
	}
	/*@Override
	public void pauseEvent () {
		if (!isHidden () && getGui ().guiOpen ()) {
			if (focus) {
				boolean[] charsOnFrame = MainLoop.getWindow ().keysPressedOnFrame;
				for (int i = 0; i < charsOnFrame.length; i ++) {
					if (charsOnFrame [i]) {
						keyEvent ((char)i);
					}
				}
				if (mouseClicked ()) {
					clickEvent (getMouseX (), getMouseY ());
				}
			}
			guiFrame ();
		}
	}
	@Override
	public void draw () {
		if (getGui ().guiOpen ()) {
			renderBackground ();
			renderElements ();
		}
	}*/
	protected void drawText (String text, int x, int y) {
		for (int i = 0; i < text.length (); i ++) {
			textInterface.drawChar (text.charAt (i), (int)this.getX () + x + i * 8,(int)this.getY () + y);
		}
	}
}