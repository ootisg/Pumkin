package pumkins;

import java.awt.Color;

public class RedPumkin extends Pumkin {
	
	public RedPumkin () {
		super ();
		getParticleMaker ().setColor (new Color (0xFF, 0x00, 0x00, 0x80));
	}

}
