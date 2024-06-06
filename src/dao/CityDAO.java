package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.City;
import model.Country;

public class CityDAO {
	private static Connection con;

	public CityDAO(Connection con) {
		this.con = con;
	}

	public List<City> getAllCities() {
		List<City> cities = new ArrayList<>();
		try {
			String query = "SELECT * FROM cities";
			PreparedStatement pst = con.prepareStatement(query);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				City city = new City();
				city.setCityId(rs.getInt("city_id"));
				city.setCityName(rs.getString("city_name"));
				city.setImages(rs.getString("images"));

				int countryId = rs.getInt("country_id");
				Country country = CountryDAO.getCountryById(con, countryId);
				city.setCountry(country);
				cities.add(city);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cities;
	}

	public City getCityByName(String cityName) {
		City city = null;
		try {
			String query = "SELECT * FROM cities WHERE city_name = ?";
			PreparedStatement pst = con.prepareStatement(query);
			pst.setString(1, cityName);
			ResultSet rs = pst.executeQuery();
			if (rs.next()) {
				city = new City();
				city.setCityId(rs.getInt("city_id"));
				city.setCityName(rs.getString("city_name"));
				city.setImages(rs.getString("images"));

				int countryId = rs.getInt("country_id");
				Country country = CountryDAO.getCountryById(con, countryId);
				city.setCountry(country);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return city;
	}

	public void updateCity(City city) {
		try {
			String query = "UPDATE cities SET city_name = ?, images = ?, country_id = ? WHERE city_id = ?";
			PreparedStatement pst = con.prepareStatement(query);
			pst.setString(1, city.getCityName());
			pst.setString(2, city.getImages());
			pst.setInt(3, city.getCountry().getCountryId());
			pst.setInt(4, city.getCityId());
			pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public int getViolationCount(String cityName, String violationType) {
		int violationCount = 0;
		try {
			String query = "SELECT COUNT(*) AS violation_count FROM report WHERE city = ? AND violation_type = ?";
			PreparedStatement pst = con.prepareStatement(query);
			pst.setString(1, cityName);
			pst.setString(2, violationType);
			ResultSet rs = pst.executeQuery();
			if (rs.next()) {
				violationCount = rs.getInt("violation_count");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return violationCount;
	}

	public static void updateCityStatus(String cityName, boolean isSafe, boolean isDangerous) {
		try {
			String query = "UPDATE cities SET is_safe = ?, is_dangerous = ? WHERE city_name = ?";
			PreparedStatement pst = con.prepareStatement(query);
			pst.setBoolean(1, isSafe);
			pst.setBoolean(2, isDangerous);
			pst.setString(3, cityName);
			pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void updateCitySanityStatus(String cityName, boolean isSane, boolean isInsane) {
		try {
			String query = "UPDATE cities SET is_sane = ?, is_insane = ? WHERE city_name = ?";
			PreparedStatement pst = con.prepareStatement(query);
			pst.setBoolean(1, isSane);
			pst.setBoolean(2, isInsane);
			pst.setString(3, cityName);
			pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void updateCityCleanlinessStatus(String cityName, boolean isClean, boolean isDirty) {
		try {
			String query = "UPDATE cities SET is_clean = ?, is_dirty = ? WHERE city_name = ?";
			PreparedStatement pst = con.prepareStatement(query);
			pst.setBoolean(1, isClean);
			pst.setBoolean(2, isDirty);
			pst.setString(3, cityName);
			pst.executeUpdate();

			int cleanlinessStatus = isClean ? 1 : 0; // Convert boolean to int (1 for true, 0 for false)
			int dirtinessStatus = isDirty ? 1 : 0; // Convert boolean to int (1 for true, 0 for false)

			String violationQuery = "UPDATE cities SET violation_count = ? WHERE city_name = ?";
			PreparedStatement violationPst = con.prepareStatement(violationQuery);
			violationPst.setInt(1, cleanlinessStatus + dirtinessStatus); // Add cleanliness and dirtiness statuses
			violationPst.setString(2, cityName);
			violationPst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
