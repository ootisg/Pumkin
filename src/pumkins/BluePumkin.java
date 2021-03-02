package pumkins;

import java.awt.Color;

public class BluePumkin extends Pumkin {

	public BluePumkin () {
		super ();
		getParticleMaker ().setColor (new Color (0x2C, 0x57, 0xCC, 0x80));
		setSpeed (1);
	}
	
}
