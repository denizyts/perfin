package silme;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

import java.util.List;

public class StockScraperSelenium {

    public static void main(String[] args) {
        // ChromeDriver'ın yolunu belirt
        System.setProperty("webdriver.chrome.driver", "/Users/denizyetis/Desktop/chromeDriver/chromedriver");

        // Chrome tarayıcısını başlat
        WebDriver driver = new ChromeDriver();
        
        // İlgili URL'yi aç
        driver.get("https://www.isyatirim.com.tr/tr-tr/analiz/hisse/Sayfalar/Tarihsel-Fiyat-Bilgileri.aspx");

        try {
            Thread.sleep(30000); // 50 saniye bekleme (gerekirse artırılabilir)
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

        WebElement hisseSelectElement = wait.until(
            ExpectedConditions.visibilityOfElementLocated(By.id("ctl00_ctt58_g_0d19e9f2_2afd_4e5a_9a92_57c4ab45c57a_ctl00_ddHisseSec"))
        );

       //WebElement hisseSelectElement = driver.findElement(By.id("ctl00_ctt58_g_0d19e9f2_2afd_4e5a_9a92_57c4ab45c57a_ctl00_ddHisseSec"));
        
        // Select sınıfını kullanarak dropdown'u kontrol et
        Select hisseSelect = new Select(hisseSelectElement);
        hisseSelect.selectByValue("KCHOL");

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
