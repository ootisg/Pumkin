package gui;

import main.GameObject;
import music.MusicPlayer;
import resources.Sprite;

public class TitleScreen extends GameObject {
	
	public static Sprite bg = new Sprite ("resources/backgrounds/title_screen_bg.png");
	public static Sprite text = new Sprite ("resources/backgrounds/title_screen_text.png");
	public static Sprite gameplay = new Sprite ("resources/backgrounds/gameplay.png");
	
	public TitleScreen () {
		setSprite (bg);
	}
	
	@Override
	public void frameEvent () {
		if (mouseClicked () && getSprite () == bg) {
			setSprite (gameplay);
			MusicPlayer.playSong ("resources/sounds/gameplay.wav");
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
