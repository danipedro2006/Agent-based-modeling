package ABM;

import java.awt.Color;
import java.util.Random;

public class Helper {
	private static Random rand = null;

	private static Random getRand() {
		if (rand == null) {
			rand = new Random();
		}
		return rand;
	}

	public static void setSeed(long seed) {
		rand = new Random(seed);
	}

	public static int nextInt(int max) {
		return getRand().nextInt(max);
	}

	public static double nextDouble() {
		return getRand().nextDouble();
	}

}
