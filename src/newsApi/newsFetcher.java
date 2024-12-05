package newsApi;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import java.util.ArrayList;


public class newsFetcher {

    @SuppressWarnings("deprecation") //JAVA 20den sonra url obj oluştururken problemler olmuş.
    public ArrayList<newsField> fetchNews(String searchQuery) {
        ArrayList<newsField> arrList = new ArrayList<newsField>();
        final String api_key = "bfaf4fd25b1f42ccb8751d72e41949da";
        try {
            // API URL
            String urlString = "https://newsapi.org/v2/everything?q=" + searchQuery + "&from=2024-11-03&sortBy=publishedAt&apiKey=" + api_key;
            urlString = "https://newsapi.org/v2/everything?q=" + searchQuery + "&from=2024-11-05&sortBy=publishedAt&apiKey=bfaf4fd25b1f42ccb8751d72e41949da";
            URL url = new URL(urlString);

            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setInstanceFollowRedirects(true);

            int responseCode = con.getResponseCode();
            System.out.println("Response Code: " + responseCode);

            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                StringBuilder content = new StringBuilder();
                String inputLine;

                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine);
                }

                in.close();
                con.disconnect();

                // Parse JSON response
                JsonObject jsonResponse = JsonParser.parseString(content.toString()).getAsJsonObject();
                JsonArray articles = jsonResponse.getAsJsonArray("articles");

                for (int i = 0; i < articles.size(); i++) {
                   
                    JsonObject article = articles.get(i).getAsJsonObject();
                    String title = article.get("title").getAsString();
                    String urlLink = article.get("url").getAsString();

                    arrList.add(new newsField(title, urlLink)); 
                }
            } else {
                System.out.println("GET request failed. Response Code: " + responseCode);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return arrList;
    }
 

    public static void main(String[] args) {
        newsFetcher fetcher = new newsFetcher();
        fetcher.fetchNews("technology");
    }
}
