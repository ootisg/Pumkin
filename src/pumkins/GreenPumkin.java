package pumkins;

import java.awt.Color;

public class GreenPumkin extends Pumkin {

	public GreenPumkin () {
		super ();
		getParticleMaker ().setColor (new Color (0x3E, 0xCC, 0x2E, 0x80));
		setSpeed (1.4);
	}
	
}
