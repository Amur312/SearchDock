package com.parser;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class GoogleSearch {
    private String apiKey;
    private String searchEngineId;

    public GoogleSearch(String apiKey, String searchEngineId) {
        this.apiKey = apiKey;
        this.searchEngineId = searchEngineId;
    }

    public List<String> search(String query) throws IOException {
        List<String> urls = new ArrayList<>();

        HttpClient httpClient = HttpClients.createDefault();
        String url = "https://www.googleapis.com/customsearch/v1?key=" + apiKey + "&cx=" + searchEngineId + "&q=" + query;

        HttpGet httpGet = new HttpGet(url);
        HttpResponse httpResponse = httpClient.execute(httpGet);
        HttpEntity httpEntity = httpResponse.getEntity();

        if (httpEntity != null) {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(httpEntity.getContent()))) {
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                String jsonResponse = stringBuilder.toString();

                // Парсинг JSON ответа
                Gson gson = new Gson();
                JsonObject jsonObject = JsonParser.parseString(jsonResponse).getAsJsonObject();
                JsonArray items = jsonObject.getAsJsonArray("items");
                for (JsonElement item : items) {
                    JsonObject itemObject = item.getAsJsonObject();
                    String urlValue = itemObject.get("link").getAsString();
                    urls.add(urlValue);
                }
            }
        }

        return urls;
    }
}
