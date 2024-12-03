package newsApi;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ApiExample {
    @SuppressWarnings("deprecation") //JAVA 20den sonra url obj oluştururken problemler olmuş.
    public static void main(String[] args) {
        final String api_key = "bfaf4fd25b1f42ccb8751d72e41949da";
        try {
            // API URL'si
            URL url = new URL("https://newsapi.org/v2/everything?q=isctr&from=2024-11-01&sortBy=publishedAt&apiKey=" + api_key);
            URL url2 = new URL("https://newsapi.org/v2/everything?q=bist100&from=2024-11-03&sortBy=publishedAt&apiKey=bfaf4fd25b1f42ccb8751d72e41949da");
         
            HttpURLConnection con = (HttpURLConnection) url2.openConnection();
            
            con.setRequestMethod("GET");
            con.setInstanceFollowRedirects(true); // Otomatik yönlendirme

            int responseCode = con.getResponseCode();
            System.out.println("Response Code : " + responseCode);

            if (responseCode == HttpURLConnection.HTTP_OK) { // 200 durum kodu
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuilder content = new StringBuilder();
                
                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine);
                }
                
                // Bağlantıları kapat
                in.close();
                con.disconnect();
                
                // JSON verisini yazdır
                System.out.println(content.toString());
            } else {
                System.out.println("GET isteği başarisiz oldu. Response Code: " + responseCode);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
