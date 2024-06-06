package model;

import java.time.LocalDateTime;
import java.util.HashMap;

public class Report {
	private int id;
	private String phoneNumber;
	private LocalDateTime date;
	private String country;
	private String city;
	private String media;
	private String violationType;
	private String status;

	// Constructor
	public Report() {
	}

	// Getters and Setters
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getMedia() {
		return media;
	}

	public void setMedia(String media) {
		this.media = media;
	}

	public String getViolationType() {
		return violationType;
	}

	public void setViolationType(String violationType) {
		this.violationType = violationType;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	// Static variables to track violation counts, sanity status, and cleanliness
	// status
	private static HashMap<String, Integer> violationCounts = new HashMap<>();
	private static HashMap<String, Boolean> sanityStatus = new HashMap<>();
	private static HashMap<String, Boolean> cleanlinessStatus = new HashMap<>();

	// Constants for violation thresholds
	private static final int TRAFFIC_LIGHT_THRESHOLD = 10;
	private static final int STOP_SIGN_THRESHOLD = 10;
	private static final int JAYWALKING_THRESHOLD = 10;
	private static final int LITTERING_THRESHOLD = 10;

	// Increment the violation count for a specific city and violation type
	public static void incrementViolationCount(String city, String violationType) {
		String key = city + "_" + violationType;
		int count = violationCounts.getOrDefault(key, 0);
		violationCounts.put(key, count + 1);
		updateCityStatus(city, violationType, count + 1);
	}

	// Update the city status based on the violation count
	private static void updateCityStatus(String city, String violationType, int count) {
		if (violationType.equals("Traffic Light") && count >= TRAFFIC_LIGHT_THRESHOLD) {
			setCityDangerous(city);
		} else if (violationType.equals("Stop Sign") && count >= STOP_SIGN_THRESHOLD) {
			setCityDangerous(city);
		} else if (violationType.equals("Jaywalking") && count >= JAYWALKING_THRESHOLD) {
			setResidentsInsane(city);
		} else if (violationType.equals("Littering") && count >= LITTERING_THRESHOLD) {
			setCityDirty(city);
		}
	}

	// Set the city as dangerous
	private static void setCityDangerous(String city) {
		// Mark the city as dangerous
		// Reset the cleanliness status for the city
		cleanlinessStatus.remove(city);
	}

	// Set the residents of the city as insane
	private static void setResidentsInsane(String city) {
		// Mark the residents of the city as insane
		sanityStatus.put(city, false);
	}

	// Set the city as dirty
	private static void setCityDirty(String city) {
		// Mark the city as dirty
		// Reset the cleanliness status for the city
		cleanlinessStatus.put(city, false);
	}

	// Check if the city is dangerous
	public static boolean isCityDangerous(String city) {
		return cleanlinessStatus.containsKey(city) && !cleanlinessStatus.get(city);
	}

	// Check if the residents of the city are insane
	public static boolean areResidentsInsane(String city) {
		return sanityStatus.containsKey(city) && !sanityStatus.get(city);
	}

	// Check if the city is clean
	public static boolean isCityClean(String city) {
		return !cleanlinessStatus.containsKey(city) || cleanlinessStatus.get(city);
	}

	// Initialize the cleanliness status for a city
	public static void initializeCleanlinessStatus(String city) {
		cleanlinessStatus.put(city, true);
	}
}
