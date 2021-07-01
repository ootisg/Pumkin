package gameObjects;

import main.GameObject;
import resources.Sprite;
import resources.Spritesheet;

public class Player extends GameObject {

	public static Spritesheet playerSheet = new Spritesheet ("resources/sprites/player.png");
	public static Sprite playerSprites = new Sprite (playerSheet, 8, 8);
	
	public Player () {
		setSprite (playerSprites);
		getAnimationHandler ().setAnimationSpeed (0);
	}
	
	@Override
	public void frameEvent () {
		//Movement checks
		if (keyCheck ('W')) {
			setY (getY () - 2);
			getAnimationHandler ().setFrame (0);
		}
		if (keyCheck ('A')) {
			setX (getX () - 2);
			getAnimationHandler ().setFrame (1);
		}
		if (keyCheck ('S')) {
			setY (getY () + 2);
			getAnimationHandler ().setFrame (2);
		}
		if (keyCheck ('D')) {
			setX (getX () + 2);
			getAnimationHandler ().setFrame (3);
		}
	}
	
}
