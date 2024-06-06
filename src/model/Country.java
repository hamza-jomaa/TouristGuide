package model;

import java.util.List;

public class Country {
	private int countryId;
	private String countryName;
	private List<City> cities;

	public Country() {

	}

	public Country(int countryId, String countryName, List<City> cities) {
		this.countryId = countryId;
		this.countryName = countryName;
		this.cities = cities;
	}

	public int getCountryId() {
		return countryId;
	}

	public void setCountryId(int countryId) {
		this.countryId = countryId;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public List<City> getCities() {
		return cities;
	}

	public void setCities(List<City> cities) {
		this.cities = cities;
	}

	@Override
	public String toString() {
		return "Country{" + "countryId=" + countryId + ", countryName='" + countryName + '\'' + ", cities=" + cities
				+ '}';
	}
}
