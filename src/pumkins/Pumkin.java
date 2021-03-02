package pumkins;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Vector;

import main.GameObject;
import main.MainLoop;
import util.Vector2D;
import visualEffects.ParticleMaker;

public class Pumkin extends GameObject {
	
	public static final double PUMKIN_DEFAULT_SPEED = 1;
	public static final double PUMKIN_GROUP_STRENGTH = .05;
	public static final double PUMKIN_REPULSION_STRENGTH = 1;
	public static final double PUMKIN_PERSONAL_SPACE = 8;
	public static final double PUMKIN_GROUPING_DISTANCE = 50;
	public static final double PUMKIN_STOP_DISTANCE = 25;
	
	private double speed;
	
	private ParticleMaker bodyParticle;
	
	public Pumkin () {
		setParticleMaker (getDefaultPumkinParticleMaker ());
		speed = PUMKIN_DEFAULT_SPEED;
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
		double mouseX = getMouseX ();
		double mouseY = getMouseY ();
		Vector2D disp = new Vector2D (mouseX - getX (), mouseY - getY ());
		if (disp.getLength () > PUMKIN_STOP_DISTANCE) {
			disp.normalize ();
			disp.scale (speed);
			setX (getX () + disp.x);
			setY (getY () + disp.y);
		}
		ArrayList<GameObject> pumks = MainLoop.getObjectMatrix ().getAll (Pumkin.class);
		for (int i = 0; i < pumks.size (); i++) {
			Pumkin currPumk = (Pumkin)pumks.get (i);
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
	
	@Override
	public void draw () {
		bodyParticle.makeParticle ((int)getX (), (int)getY ());
	}
	
}
