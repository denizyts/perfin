package newsApi;
import java.awt.*;

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
