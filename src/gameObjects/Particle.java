package gameObjects;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.HashMap;

import main.GameObject;
import main.MainLoop;

public class Particle extends GameObject {
	
	public double FADE_START_POINT = .4;
	
	private static ArrayList<Particle> particles = new ArrayList<Particle> ();
	private static HashMap<Integer, Color> colors = new HashMap<Integer, Color> ();
	
	public Color color;
	public int size;
	public double durability;
	public double initialDurability;
	public double direction;
	public double vx;
	public double vy;
	public double speed;
	
	public static Particle makeParticle (double x, double y, Color color, int size, double durability) {
		Particle p;
		if (particles.size () == 0) {
			p = new Particle ();
			p.declare (x, y);
		} else {
			p = particles.remove (0);
			p.setPosition (x, y);
		}
		p.color = color;
		p.size = size;
		p.durability = durability;
		p.initialDurability = durability;
		p.direction = 0;
		p.speed = 0;
		p.computeVectors ();
		p.setHidden (false);
		return p;
	}
	public static Particle makeParticle (double x, double y, Color color, int size, double durability, double direction, double speed) {
		Particle p;
		if (particles.size () == 0) {
			p = new Particle ();
			p.declare (x, y);
		} else {
			p = particles.remove (0);
			p.setPosition (x, y);
		}
		p.color = color;
		p.size = size;
		p.durability = durability;
		p.initialDurability = durability;
		p.direction = direction;
		p.speed = speed;
		p.computeVectors ();
		p.setHidden (false);
		return p;
	}
	public static Particle makeParticle (double x, double y, Color color, int size, double durability, double direction, double speed, double randomDecayProbability) {
		Particle p;
		if (particles.size () == 0) {
			p = new Particle ();
			p.declare (x, y);
		} else {
			p = particles.remove (0);
			p.setPosition (x, y);
		}
		p.color = color;
		p.size = size;
		p.durability = durability - Math.random () * durability * randomDecayProbability;
		p.initialDurability = p.durability;
		p.direction = direction;
		p.speed = speed;
		p.computeVectors ();
		p.setHidden (false);
		return p;
	}
	@Override
	public void forget () {
		particles.add (this);
		setHidden (true);
	}
	@Override
	public void frameEvent () {
		if (!isHidden ()) {
			if (durability <= 0) {
				this.forget ();
			}
			durability -= 1;
			if (speed != 0) {
				this.setX (this.getX () + vx);
				this.setY (this.getY () - vy);
			}
		}
	}
	@Override
	public void draw () {
		
		int fadePoint = (int)((double)(FADE_START_POINT) * initialDurability);
		Color c = color;
		if (durability < fadePoint) {
			double percentFade = ((double)durability / fadePoint);
			int colorInt = (color.getAlpha () << 24) + (color.getRed () << 16) + (color.getGreen () << 8) + color.getBlue ();
			if (colors.containsKey (colorInt)) {
				c = colors.get (colorInt);
			} else {
				c = new Color (color.getRed (), color.getGreen (), color.getBlue (), (int)(color.getAlpha () * percentFade));
				colors.put (colorInt, c);
			}
		}
		MainLoop.getWindow ().getBufferGraphics ().setColor (c);
		MainLoop.getWindow ().getBufferGraphics ().fillRect ((int)this.getX () - getRoom ().getViewX (), (int)this.getY () - getRoom ().getViewY (), size, size);
	}
	public void computeVectors () {
		vx = speed * Math.cos (direction);
		vy = speed * Math.sin (direction);
	}
}
