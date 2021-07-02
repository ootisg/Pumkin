package gameObjects;

import java.awt.Point;
import java.util.ArrayList;

import main.GameObject;
import main.MainLoop;
import pumkins.Pumkin;
import resources.Sprite;
import resources.Spritesheet;
import util.Vector2D;

public class Player extends GameObject {

	public static Spritesheet playerSheet = new Spritesheet ("resources/sprites/player.png");
	public static Sprite playerSprites = new Sprite (playerSheet, 8, 8);
	
	public static final int PLAYER_SPEED = 1;
	
	public static final int PUMKIN_MAX_THROW_DISTANCE = 70;
	public static final int PUMKIN_MAX_GRAB_DISTANCE = 30;
	public static final int WISTLE_RADIUS = 20;
	
	public Player () {
		setSprite (playerSprites);
		getAnimationHandler ().setAnimationSpeed (0);
		createHitbox (0, 0, 8, 8);
	}
	
	@Override
	public void frameEvent () {
		
		//Movement checks
		if (keyCheck ('W')) {
			setY (getY () - PLAYER_SPEED);
			getAnimationHandler ().setFrame (0);
		}
		if (keyCheck ('A')) {
			setX (getX () - PLAYER_SPEED);
			getAnimationHandler ().setFrame (1);
		}
		if (keyCheck ('S')) {
			setY (getY () + PLAYER_SPEED);
			getAnimationHandler ().setFrame (2);
		}
		if (keyCheck ('D')) {
			setX (getX () + PLAYER_SPEED);
			getAnimationHandler ().setFrame (3);
		}
		
		//Throw logic
		if (this.mouseButtonReleased (1)) {
			
			//Find the closest pumkin
			ArrayList<GameObject> pumkins = MainLoop.getObjectMatrix ().getAll (Pumkin.class);
			Pumkin shortest = null;
			double shortestDist = Double.POSITIVE_INFINITY;
			for (int i = 0; i < pumkins.size (); i++) {
				Pumkin curr = (Pumkin)pumkins.get (i);
				double dist = curr.getDistance (this);
				if (
					dist < shortestDist && 
					curr.getCurrentAi () == Pumkin.PUMKIN_AI_FOLLOW_PLAYER &&
					dist <= PUMKIN_MAX_GRAB_DISTANCE )
				{
					//Pumkin can be thrown
					shortest = (Pumkin)pumkins.get (i);
					shortestDist = dist;
				}
			}
			
			//Throw the pumkin
			if (shortest != null) {
				Point pt = getThrowingPoint ();
				shortest.throwTo (getCenterX (), getCenterY (), pt.x, pt.y);
			}
			
		}
		
		//Wistle logic
		if (mouseButtonDown (3) || keyCheck ('E')) {
			
			//Check for nearby pumkins and 
			ArrayList<GameObject> pumkins = MainLoop.getObjectMatrix ().getAll (Pumkin.class);
			for (int i = 0; i < pumkins.size (); i++) {
				Pumkin curr = (Pumkin) pumkins.get(i);
				setPosition (getMouseX () - getHitbox ().width / 2, getMouseY () - getHitbox ().height / 2); //Hacky af, change later pls
				if (curr.getDistance (this) <= WISTLE_RADIUS && curr.getCurrentAi () == Pumkin.PUMKIN_AI_IDLE) {
					curr.followPlayer ();
				}
				backstep ();
			}
			
		}
		
	}
	
	public Point getThrowingPoint () {
		Vector2D v = new Vector2D (getMouseX () - getCenterX (), getMouseY () - getCenterY ());
		if (v.getLength() > PUMKIN_MAX_THROW_DISTANCE) {
			v.normalize ();
			v.scale (PUMKIN_MAX_THROW_DISTANCE);
		}
		return new Point ((int)(getCenterX () + v.x), (int)(getCenterY () + v.y));
	}
	
}
