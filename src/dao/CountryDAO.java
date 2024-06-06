package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.Country;

public class CountryDAO {
	private Connection con;

	public CountryDAO(Connection con) {
		this.con = con;
	}

	public List<Country> getAllCountries() {
		List<Country> countries = new ArrayList<Country>();
		try {
			String query = "SELECT * FROM countries";
			PreparedStatement pst = con.prepareStatement(query);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				Country country = new Country();
				country.setCountryId(rs.getInt("country_id"));
				country.setCountryName(rs.getString("country_name"));
				;
				countries.add(country);
			}
			rs.close();
			pst.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return countries;
	}

	public static Country getCountryById(Connection con, int countryId) {
		Country country = null;
		try {
			String query = "SELECT * FROM  countries WHERE country_id = ?";
			PreparedStatement pst = con.prepareStatement(query);
			pst.setInt(1, countryId);
			ResultSet rs = pst.executeQuery();
			if (rs.next()) {
				country = new Country();
				country.setCountryId(rs.getInt("country_id"));
				country.setCountryName(rs.getString("country_name"));
			}
			rs.close();
			pst.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return country;
	}
}
