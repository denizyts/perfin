import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;


class newsFetcher {

    @SuppressWarnings("deprecation") //JAVA 20den sonra url obj oluştururken problemler olmuş.
    public ArrayList<newsField> fetchNews(String searchQuery) {
        ArrayList<newsField> arrList = new ArrayList<newsField>();
        final String api_key = "bfaf4fd25b1f42ccb8751d72e41949da";
        
        LocalDate today = LocalDate.now();
        LocalDate twentyDaysBefore = today.minusDays(20);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate = twentyDaysBefore.format(formatter);
        System.out.println("Formatted Date: " + formattedDate);
        try {
            // API URL
            String urlString = "https://newsapi.org/v2/everything?q=" + searchQuery + "&from=" + formattedDate + "&sortBy=publishedAt&apiKey=" + api_key;
            //urlString = "https://newsapi.org/v2/everything?q=" + searchQuery + "&from=2024-11-05&sortBy=publishedAt&apiKey=bfaf4fd25b1f42ccb8751d72e41949da";
            //urlString = "https://newsapi.org/v2/everything?q=tesla&from=2024-11-17&sortBy=publishedAt&apiKey=bfaf4fd25b1f42ccb8751d72e41949da";
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
 

}
public class newsField {

    private String title;
    private String url_str;
    

    public newsField(String title, String url_str) {
        this.title = title;
        this.url_str = url_str;
    }

    public String getTitle() {
        return title;
    }
    public String getUrl_str() {
        return url_str;
    }
    
}
