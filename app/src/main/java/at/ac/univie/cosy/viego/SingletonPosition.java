package at.ac.univie.cosy.viego;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Created by Mark Anthony on 13.05.2017.
 */

public class SingletonPosition {
	private double minlongitude = 16.348924;
	private double maxlongitude = 16.392974;
	private double minlatitude = 48.193283;
	private double maxlatitude = 48.221780;

	private double currentlong = 0;
	private double currentlat = 0;

	private static final SingletonPosition ourInstance = new SingletonPosition();

	public static SingletonPosition getInstance() {
		return ourInstance;
	}

	private SingletonPosition() {

		currentlong = Math.random() * (maxlongitude - minlongitude) + minlongitude;
		currentlat = Math.random() * (maxlatitude - minlatitude) + minlatitude;

		BigDecimal bd = new BigDecimal(currentlong);
		currentlong = bd.setScale(6, RoundingMode.HALF_UP).doubleValue();
		bd = new BigDecimal(currentlat);
		currentlat = bd.setScale(6, RoundingMode.HALF_UP).doubleValue();
	}

	public double getCurrentlong() {
		return currentlong;
	}

	public double getCurrentlat() {
		return currentlat;
	}
}
