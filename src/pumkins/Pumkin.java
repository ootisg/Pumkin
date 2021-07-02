package pumkins;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Vector;

import main.GameObject;
import main.MainLoop;
import util.Vector2D;
import visualEffects.ParticleMaker;

public class Pumkin extends GameObject {
	
	public static final int PUMKIN_AI_IDLE = 0;
	public static final int PUMKIN_AI_FOLLOW_PLAYER = 1;
	public static final int PUMKIN_AI_FOLLOW_POINT = 2;
	public static final int PUMKIN_AI_RUN_BETWEEN = 3;
	public static final int PUMKIN_AI_THROW_TO = 4;
	
	public static final double PUMKIN_DEFAULT_SPEED = 1;
	public static final double PUMKIN_THROW_SPEED = 10;
	public static final double PUMKIN_GROUP_STRENGTH = .05;
	public static final double PUMKIN_REPULSION_STRENGTH = 1;
	public static final double PUMKIN_PERSONAL_SPACE = 8;
	public static final double PUMKIN_GROUPING_DISTANCE = 50;
	public static final double PUMKIN_STOP_DISTANCE = 25;
	public static final double PUMKIN_DEST_DISTANCE = 16;
	
	private double speed;
	
	private ParticleMaker bodyParticle;
	
	private int currentAi;
	private double destX;
	private double destY;
	private double fromX;
	private double fromY;
	private int throwFrames;
	private boolean towardsDest;
	
	Vector2D scratch = new Vector2D (0, 0);
	
	public Pumkin () {
		setParticleMaker (getDefaultPumkinParticleMaker ());
		speed = PUMKIN_DEFAULT_SPEED;
		currentAi = PUMKIN_AI_FOLLOW_PLAYER;
		createHitbox (-2, -2, 4, 4);
	}
	
	public ParticleMaker getDefaultPumkinParticleMaker () {
		ParticleMaker pm = new ParticleMaker ();
		pm.setColor (new Color (0, 0, 0, 127));
		pm.setMinAng ((Math.PI / 180) * 85);
		pm.setMaxAng ((Math.PI / 180) * 95);
		pm.setMinLifespan (8);
		pm.setMaxLifespan (20);
		pm.setSpeed (.25);
		pm.setMinSize (1);
		pm.setMaxSize (2);
		return pm;
	}
	
	public ParticleMaker getParticleMaker () {
		return bodyParticle;
	}
	
	public double getSpeed () {
		return speed;
	}
	
	public void setParticleMaker (ParticleMaker pm) {
		bodyParticle = pm;
	}
	
	public void setSpeed (double speed) {
		this.speed = speed;
	}

	@Override
	public void frameEvent () {
		doAIStep ();
		if (isColliding (getPlayer ()) && currentAi == PUMKIN_AI_IDLE) {
			followPlayer ();
		}
	}
	
	public void doAIStep () {
		switch (currentAi) {
			case PUMKIN_AI_IDLE:
				//Do nothing
				break;
			case PUMKIN_AI_FOLLOW_PLAYER:
				followAIStep (getPlayer ().getCenterX (), getPlayer ().getCenterY ());
				break;
			case PUMKIN_AI_FOLLOW_POINT:
				followAIStep (destX, destY);
				break;
			case PUMKIN_AI_RUN_BETWEEN:
				if (towardsDest) {
					followAIStep (destX, destY);
				} else {
					followAIStep (fromX, fromY);
				}
				double x1 = getX ();
				double y1 = getY ();
				double x2 = towardsDest ? destX : fromX;
				double y2 = towardsDest ? destY : fromY;
				double distx = x2 - x1;
				double disty = y2 - y1;
				double dist = Math.sqrt (distx * distx + disty * disty);
				if (dist < PUMKIN_DEST_DISTANCE) {
					towardsDest = !towardsDest;
				}
				break;
			case PUMKIN_AI_THROW_TO:
				Vector2D throwVector = new Vector2D (destX - fromX, destY - fromY);
				Vector2D currVector = new Vector2D (throwVector);
				currVector.normalize ();
				currVector.scale (throwFrames * PUMKIN_THROW_SPEED);
				if (currVector.getLength () >= throwVector.getLength ()) {
					setX (destX);
					setY (destY);
					throwFrames = 0;
					idle ();
				} else {
					setX (fromX + currVector.x);
					setY (fromY + currVector.y);
					throwFrames++;
				}
			default:
				break;
		}
	}
	
	public void followAIStep (double x, double y) {
		scratch.x = x - getX ();
		scratch.y = y - getY ();
		if (scratch.getLength () > PUMKIN_STOP_DISTANCE) {
			scratch.normalize ();
			scratch.scale (speed);
			setX (getX () + scratch.x);
			setY (getY () + scratch.y);
		}
		ArrayList<GameObject> pumks = MainLoop.getObjectMatrix ().getAll (Pumkin.class);
		for (int i = 0; i < pumks.size (); i++) {
			Pumkin currPumk = (Pumkin)pumks.get (i);
			//Adjust for 0 distance case
			while (currPumk != this && currPumk.getX () == this.getX () && currPumk.getY () == this.getY ()) {
				Vector2D vRand = new Vector2D (Math.random () * 2 - 2, Math.random () * 2 - 2);
				vRand.normalize();
				setX (getX () + vRand.x);
				setY (getY () + vRand.y);
			}
			//Do pumkin things
			Vector2D offs = new Vector2D (currPumk.getX () - getX (), currPumk.getY () - getY ());
			double offsLength = offs.getLength ();
			offs.normalize ();
			if (currPumk.getClass ().equals (this.getClass ()) && currPumk != this && offsLength < PUMKIN_GROUPING_DISTANCE) {
				if (currPumk.getClass ().equals (this.getClass ())) {
					offs.scale (-(PUMKIN_GROUP_STRENGTH * speed));
				}
				setX (getX () - offs.x);
				setY (getY () - offs.y);
				offs.scale (-(1/(PUMKIN_GROUP_STRENGTH * speed)));
			}
			if (currPumk != this && offsLength < PUMKIN_PERSONAL_SPACE) {
				offs.scale (PUMKIN_REPULSION_STRENGTH * speed);
				setX (getX () - offs.x);
				setY (getY () - offs.y);
			}
		}
	}
	
	public void idle () {
		currentAi = PUMKIN_AI_IDLE;
	}
	
	public void followPlayer () {
		currentAi = PUMKIN_AI_FOLLOW_PLAYER;
	}
	
	public void gotoPoint (double x, double y) {
		currentAi = PUMKIN_AI_FOLLOW_POINT;
		destX = x;
		destY = y;
	}
	
	public void goBetween (double xfrom, double yfrom, double xto, double yto) {
		Thread.dumpStack();
		currentAi = PUMKIN_AI_RUN_BETWEEN;
		destX = xto;
		destY = yto;
		fromX = xfrom;
		fromY = yfrom;
		towardsDest = true;
	}
	
	public void throwTo (double xfrom, double yfrom, double xto, double yto) {
		currentAi = PUMKIN_AI_THROW_TO;
		destX = xto;
		destY = yto;
		fromX = xfrom;
		fromY = yfrom;
	}
	
	public void reverse () {
		towardsDest = !towardsDest;
	}
	
	public int getCurrentAi () {
		return currentAi;
	}
	
	@Override
	public void draw () {
		bodyParticle.makeParticle ((int)getX (), (int)getY ());
		/*Graphics g = MainLoop.getWindow ().getBufferGraphics ();
		g.setColor (Color.BLACK);
		g.fillRect ((int)getX (), (int)getY (), 2, 2);*/
	}
	
}
