package model;

import java.util.HashMap;
import java.util.Map;

public class ViolationSystem {
	private static Map<String, Integer> trafficLightViolations;
	private static Map<String, Integer> stopSignViolations;
	private static Map<String, Integer> jaywalkingViolations;
	private static Map<String, Integer> litteringViolations;

	static {
		trafficLightViolations = new HashMap<>();
		stopSignViolations = new HashMap<>();
		jaywalkingViolations = new HashMap<>();
		litteringViolations = new HashMap<>();
	}

	public static boolean reportViolation(String city, String violationType) {
		switch (violationType) {
		case "traffic_light":
			return incrementViolationCount(city, trafficLightViolations);
		case "stop_sign":
			return incrementViolationCount(city, stopSignViolations);
		case "jaywalking":
			return incrementViolationCount(city, jaywalkingViolations);
		case "littering":
			return incrementViolationCount(city, litteringViolations);
		default:
			return false;
		}
	}

	private static boolean incrementViolationCount(String city, Map<String, Integer> violationCounts) {
		violationCounts.put(city, violationCounts.getOrDefault(city, 0) + 1);
		int count = violationCounts.get(city);

		if (count == 10) {
			return true; // City reached the threshold for being considered dangerous
		}

		return false;
	}

	public static int getViolationCount(String city, String violationType) {
		Map<String, Integer> violationCounts = getViolationMapByType(violationType);
		return violationCounts.getOrDefault(city, 0);
	}

	private static Map<String, Integer> getViolationMapByType(String violationType) {
		switch (violationType) {
		case "traffic_light":
			return trafficLightViolations;
		case "stop_sign":
			return stopSignViolations;
		case "jaywalking":
			return jaywalkingViolations;
		case "littering":
			return litteringViolations;
		default:
			return new HashMap<>(); // Return an empty map if the violation type is unknown
		}
	}
}
