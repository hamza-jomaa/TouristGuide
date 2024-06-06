package model;

public class City {
	private int cityId;
	private String cityName;
	private Country country;
	private String images;
	private boolean isClean;
	private boolean isSafe;
	private boolean isSane;

	public City() {
	}

	public City(int cityId, String cityName, Country country, String images, boolean isClean, boolean isSafe,
			boolean isSane) {
		this.cityId = cityId;
		this.cityName = cityName;
		this.country = country;
		this.images = images;
		this.isClean = isClean;
		this.isSafe = isSafe;
		this.isSane = isSane;
	}

	public int getCityId() {
		return cityId;
	}

	public boolean isSane() {
		return isSane;
	}

	public void setSane(boolean sane) {
		isSane = sane;
	}

	public void setCityId(int cityId) {
		this.cityId = cityId;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public String getImages() {
		return images;
	}

	public void setImages(String images) {
		this.images = images;
	}

	public boolean isClean() {
		return isClean;
	}

	public void setClean(boolean clean) {
		isClean = clean;
	}

	public boolean isSafe() {
		return isSafe;
	}

	public void setSafe(boolean safe) {
		isSafe = safe;
	}

	@Override
	public String toString() {
		return "City{" + "cityId=" + cityId + ", cityName='" + cityName + '\'' + ", country=" + country + ", images='"
				+ images + '\'' + ", isClean=" + isClean + ", isSafe=" + isSafe + '}';
	}
}
