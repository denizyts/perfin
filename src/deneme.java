import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import java.util.List;

public class deneme {

    public static void main(String[] args) {
        // ChromeDriver'ın yolunu belirt
        System.setProperty("webdriver.chrome.driver", "/Users/denizyetis/Desktop/chromeDriver/chromedriver");

        // Chrome tarayıcısını başlat
        WebDriver driver = new ChromeDriver();
        
        // İlgili URL'yi aç
        driver.get("https://www.isyatirim.com.tr/tr-tr/analiz/hisse/Sayfalar/Tarihsel-Fiyat-Bilgileri.aspx");

        // Sayfanın tam olarak yüklenmesi için bekleme (gerekirse sleep ekleyebilirsin)
        try {
            Thread.sleep(70000); // Sayfa yüklendiğinden emin olmak için artırıldı
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Print all iframes on the page and their index
        List<WebElement> iframes = driver.findElements(By.tagName("iframe"));
        System.out.println("Total iframes: " + iframes.size());
        
        for (int i = 0; i < iframes.size(); i++) {
            System.out.println("Iframe " + i + ": " + iframes.get(i).getAttribute("id"));
        }

        // Switch to the first iframe (assuming there is only one)
        if (iframes.size() > 0) {
            driver.switchTo().frame(iframes.get(0));
            System.out.println("Switched to first iframe.");
        } else {
            System.out.println("No iframe found, continuing without switching.");
        }

        // JavaScript Executor to find the dropdown element
        JavascriptExecutor js = (JavascriptExecutor) driver;
        WebElement hisseSelectElement = (WebElement) js.executeScript(
        "return document.getElementById('ctl00_ctt58_g_0d19e9f2_2afd_4e5a_9a92_57c4ab45c57a_ctl00_ddHisseSec');"
        );

        if (hisseSelectElement == null) {
            System.out.println("Element not found.");
            driver.quit();
            return;
        }

        // Select sınıfını kullanarak dropdown'u kontrol et
        Select hisseSelect = new Select(hisseSelectElement);
        hisseSelect.selectByValue("KCHOL");

        // Switch back to the main content if you're inside an iframe
        driver.switchTo().defaultContent();

        // Tablodaki 'tbody' kısmını id'sine göre seç (id = HisseTekilBody)
        WebElement tbody = driver.findElement(By.id("HisseTekilBody"));

        // Satırları seç (tr etiketleri)
        List<WebElement> rows = tbody.findElements(By.tagName("tr"));

        // Her bir tablo satırını (tr) döngüyle gez
        for (WebElement row : rows) {
            List<WebElement> columns = row.findElements(By.tagName("td"));

            // Sütun sayısı kontrolü
            if (columns.size() > 0) {
                String date = columns.get(0).getText();  // Tarih
                String closingPrice = columns.get(1).getText();  // Kapanış fiyatı (sütun indeksine göre düzenle)

                // Çıktıyı yazdır
                System.out.println("Tarih: " + date + ", Kapanış Fiyatı: " + closingPrice);
            }
        }

        // Tarayıcıyı kapat
        driver.quit();
    }
}

