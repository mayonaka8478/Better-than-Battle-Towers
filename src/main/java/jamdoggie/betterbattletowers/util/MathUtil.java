package jamdoggie.betterbattletowers.util;

import java.util.Random;

public class MathUtil {

	private MathUtil(){}

	/**
	 * @return value between 0 and 1 with a normal distribution
	 * */
	public static double posGausssian(Random random){
		return (random.nextGaussian() + 2.0D)/ 2.0D;
	}

	/**
	 * @return value between 0 and 2 * mean with a normal distribution and expected value of mean
	 * */
	public static double posGausssian(Random random, int mean){
		return MathUtil.posGausssian(random) * 2 * mean;
	}


	public static int posGausssianInt(Random random, int mean){
		return (int) Math.round(posGausssian(random, mean));
	}
}
