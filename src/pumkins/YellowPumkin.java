package pumkins;

import java.awt.Color;

public class YellowPumkin extends Pumkin {

	public YellowPumkin () {
		super ();
		getParticleMaker ().setColor (new Color (0xFF, 0xE1, 0x00, 0x80));
	}
	
}
