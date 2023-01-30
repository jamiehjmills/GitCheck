package com.eternalcode.gitcheck.shared;

import com.eternalcode.gitcheck.git.GitException;
import com.eternalcode.gitcheck.git.GitRepository;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.stream.Collectors;

public class Http {

    GitRepository gitRepository;

    private JSONObject requestLastRelease(GitRepository repository, String apiURL, String userAgent) {
        String getUrl = String.format(apiURL, repository.getFullName());

        try {
            URL url = new URL(getUrl);
            URLConnection urlConnection = url.openConnection();

            if (!(urlConnection instanceof HttpURLConnection)) {
                throw new GitException("The URL is not an HTTP URL");
            }

            HttpURLConnection httpURLConnection = (HttpURLConnection) urlConnection;

            httpURLConnection.setRequestProperty("User-Agent", userAgent);
            httpURLConnection.connect();

            if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_NOT_FOUND) {
                throw new GitException("The release of the repository " + repository.getFullName() + " was not found");
            }

            if (httpURLConnection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new GitException("The response code is not 200");
            }

            String response = this.readResponse(httpURLConnection);

            JSONParser parser = new JSONParser();
            Object parsed = parser.parse(response);

            if (!(parsed instanceof JSONObject)) {
                throw new GitException("The response is not a JSON object");
            }

            return (JSONObject) parsed;
        } catch (IOException exception) {
            throw new GitException("Invalid URL", exception);
        } catch (ParseException exception) {
            throw new GitException("Invalid JSON response", exception);
        }
    }

    private String readResponse(HttpURLConnection connection) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            return reader.lines()
                    .collect(Collectors.joining());
        }
    }


}
