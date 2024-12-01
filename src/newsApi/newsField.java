package newsApi;
import java.awt.*;

public class newsField {

    private String title;
    private String url_str;
    private Image image;

    public newsField(String title, String url_str, Image image) {
        this.title = title;
        this.url_str = url_str;
        this.image = image;
    }
}
