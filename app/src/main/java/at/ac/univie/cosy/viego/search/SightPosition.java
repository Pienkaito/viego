package at.ac.univie.cosy.viego.search;

/**
 * Created by Mark Anthony on 12.05.2017.
 */

public class SightPosition {
	private float longitude;
	private float latitude;
	private String name;

	SightPosition() {
	}

	SightPosition(float longitude, float latitude, String name) {
		this.latitude = latitude;
		this.longitude = longitude;
		this.name = name;
	}

	public float getLongitude() {
		return longitude;
	}

	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}

	public float getLatitude() {
		return latitude;
	}

	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
