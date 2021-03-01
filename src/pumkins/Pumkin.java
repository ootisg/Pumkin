package pumkins;

import java.awt.Color;

import main.GameObject;
import visualEffects.ParticleMaker;

public class Pumkin extends GameObject {
	
	private ParticleMaker bodyParticle;
	
	public Pumkin () {
		setParticleMaker (getDefaultPumkinParticleMaker ());
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
	
	public void setParticleMaker (ParticleMaker pm) {
		bodyParticle = pm;
	}

	@Override
	public void frameEvent () {
		
	}
	
	@Override
	public void draw () {
		bodyParticle.makeParticle ((int)getX (), (int)getY ());
	}
	
}
