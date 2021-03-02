package pumkins;

import java.awt.Color;

public class OrangePumkin extends Pumkin {

	public OrangePumkin () {
		super ();
		getParticleMaker ().setColor (new Color (0xFF, 0x8E, 0x00, 0x80));
		setSpeed (.9);
	}
	
}
