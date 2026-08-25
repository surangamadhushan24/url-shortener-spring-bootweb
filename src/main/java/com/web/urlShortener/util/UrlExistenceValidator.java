package com.web.urlShortener.util;

import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

public class UrlExistenceValidator {
	
	

    public static boolean isUrlExists(String urlString) {
        try {
           
            URL url = new URI(urlString).toURL();
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("HEAD");
            connection.setConnectTimeout(5000); // 5 seconds
            connection.setReadTimeout(5000);

            int responseCode = connection.getResponseCode();
            return (responseCode >= 200 && responseCode < 400); // 2xx and 3xx are valid
        } catch (Exception e) {
           
            return false; // URL is invalid or not reachable
        }
    }

}
