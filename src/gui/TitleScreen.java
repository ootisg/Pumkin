package gui;

import java.awt.event.KeyEvent;

import gameObjects.Player;
import main.GameObject;
import music.MusicPlayer;
import pumkins.*;
import resources.Sprite;

public class TitleScreen extends GameObject {
	
	public static Sprite bg = new Sprite ("resources/backgrounds/title_screen_bg.png");
	public static Sprite text = new Sprite ("resources/backgrounds/title_screen_text.png");
	public static Sprite gameplay = new Sprite ("resources/backgrounds/gameplay.png");
	
	public static MapScreen mps = new MapScreen ();
	
	public TitleScreen () {
		setSprite (bg);
	}
	
	@Override
	public void frameEvent () {
		if (mouseClicked () && getSprite () == bg) {
			setSprite (gameplay);
			this.setHidden (true);
			mps.declare (0, 0);
			for (int i = 0; i < 6; i++) {
				new RedPumkin ().declare (16, 32 + i * 8);
				new OrangePumkin ().declare (24, 32 + i * 8);
				new YellowPumkin ().declare (32, 32 + i * 8);
				new GreenPumkin ().declare (40, 32 + i * 8);
				new BluePumkin ().declare (48, 32 + i * 8);
				new PurplePumkin ().declare (56, 32 + i * 8);
			}
			new Player ().declare (20, 20);
		}
	}
	
	@Override
	public void draw () {
		super.draw ();
		if (getSprite () == bg) {
			text.draw (80, 60);
		}
	}

}
