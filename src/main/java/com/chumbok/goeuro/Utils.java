package com.chumbok.goeuro;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Utils {

	private static final Logger logger = LoggerFactory.getLogger(Utils.class);

	// Preventing class init
	private Utils() {
		throw new IllegalStateException(
				"All method are static. Do not init the class.");
	}

	public static String readUrl(URL url) {

		try {
			return IOUtils.toString(url, Charset.forName("UTF-8"));
		} catch (IOException e) {
			logger.error("Could not read from URL.", e);
		}

		return null;
	}

	public static void writeCsvFile(String outFileName, String data) {
		try {
			FileWriter writer = new FileWriter(outFileName);
			writer.append(data);
			writer.flush();
			writer.close();
		} catch (IOException e) {
			logger.error("Could not write data.", e);
		}
	}
}
