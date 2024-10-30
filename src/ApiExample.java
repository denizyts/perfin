import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ApiExample {
    @SuppressWarnings("deprecation") //JAVA 20den sonra url obj oluştururken problemler olmuş.
    public static void main(String[] args) {
        try {
            // API URL'si
            URL url = new URL("https://bigpara.hurriyet.com.tr/api/v1/hisse/list?");
            //URL url2 = new URL("https://bigpara.hurriyet.com.tr/api/v1/borsa/hisseyuzeysel/ISCTR");
            //URL url3 = new URL("https://bigpara.hurriyet.com.tr/api/v1/borsa/hisseyuzeysel/TUPRS");
            //URL url4 = new URL("https://api.hurriyet.com.tr/v1/articles?$select=Title");

            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            
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
