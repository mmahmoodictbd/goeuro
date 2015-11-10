package com.chumbok.goeuro;

import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class App {

	private static final Logger logger = LoggerFactory.getLogger(App.class);

	private final static String BASE_ENDPOINT = "http://api.goeuro.com/api/v2/position/suggest/en/";

	public static void main(String[] args) {
		App app = new App();
		app.run(args);
	}

	private void run(String[] args) {

		URL endpoint = null;
		try {
			endpoint = new URL(BASE_ENDPOINT + args[0]);
		} catch (MalformedURLException e) {
			logger.error("Malformed URL.", e);
		}

		String jsonData = Utils.readUrl(endpoint);

		if (jsonData == null) {
			throw new IllegalStateException(
					"Could not fetch gata from endpoint.");
		}

		JSONArray locArr = new JSONArray(jsonData);

		StringBuilder csv = new StringBuilder();
		csv.append(buildCsvHeader());

		for (int i = 0; i < locArr.length(); i++) {
			csv.append(jsonObjToCsvLine((JSONObject) locArr.get(i)));
		}

		Utils.writeCsvFile("loc.csv", csv.toString());

	}

	private String buildCsvHeader() {

		StringBuilder header = new StringBuilder();
		header.append("_id");
		header.append(",");
		header.append("name");
		header.append(",");
		header.append("type");
		header.append(",");
		header.append("latitude");
		header.append(",");
		header.append("longitude");
		header.append("\n");

		return header.toString();
	}

	private String jsonObjToCsvLine(JSONObject obj) {

		StringBuilder line = new StringBuilder();

		line.append(obj.get("_id"));
		line.append(",");
		line.append(obj.get("name"));
		line.append(",");
		line.append(obj.get("type"));
		line.append(",");
		line.append(obj.get("geo_position") != null ? ((JSONObject) obj
				.get("geo_position")).get("latitude") : "");
		line.append(",");
		line.append(obj.get("geo_position") != null ? ((JSONObject) obj
				.get("geo_position")).get("longitude") : "");
		line.append("\n");

		return line.toString();
	}

}
