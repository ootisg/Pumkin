package pumkins;

import java.awt.Color;

public class PurplePumkin extends Pumkin {
	
	public PurplePumkin () {
		super ();
		getParticleMaker ().setColor (new Color (0xAA, 0x41, 0xCC, 0x80));
		setSpeed (.6);
	}

}
