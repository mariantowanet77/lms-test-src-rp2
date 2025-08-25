package jp.co.sss.lms.ct.f02_faq;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 * 結合テスト よくある質問機能
 * ケース05
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース05 キーワード検索 正常系")
public class Case05 {

	ChromeDriver driver = new ChromeDriver();
	String originalHandle = driver.getWindowHandle();

	/** 前処理 */
	@BeforeAll
	static void before() {
		createDriver();
	}

	/** 後処理 */
	@AfterAll
	static void after() {
		closeDriver();
	}

	/**
	 * 各テスト終了後にスクリーンショットを保存
	 */
	@AfterEach
	void takeScreenshot(TestInfo testInfo) throws Exception {
		// 画面倍率の変更(80%)
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("document.body.style.zoom='80%'");
		//スクショを取る処理
		Thread.sleep(2000);
		File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		String fileName = "evidence/case05/" + testInfo.getTestMethod().get().getName() + ".png";
		Path targetPath = new File(fileName).toPath();
		Files.createDirectories(targetPath.getParent());
		Files.copy(screenshot.toPath(), targetPath);
		//画面倍率を元に戻す。
		js.executeScript("document.body.style.zoom='100%'");
	}

	@Test
	@Order(1)
	@DisplayName("テスト01 トップページURLでアクセス")
	void test01() {
		driver.get("http://localhost:8080/lms/");
	}

	@Test
	@Order(2)
	@DisplayName("テスト02 初回ログイン済みの受講生ユーザーでログイン")
	void test02() {
		driver.get("http://localhost:8080/lms/");
		driver.findElement(By.id("loginId")).sendKeys("StudentAA01");
		driver.findElement(By.id("password")).sendKeys("StudentAA01a");
		driver.findElement(By.cssSelector("input[type='submit']")).click();
	}

	@Test
	@Order(3)
	@DisplayName("テスト03 上部メニューの「ヘルプ」リンクからヘルプ画面に遷移")
	void test03() {
		driver.get("http://localhost:8080/lms/");
		driver.findElement(By.id("loginId")).sendKeys("StudentAA01");
		driver.findElement(By.id("password")).sendKeys("StudentAA01a");
		driver.findElement(By.cssSelector("input[type='submit']")).click();
		driver.findElement(By.linkText("機能")).click();
		driver.findElement(By.linkText("ヘルプ")).click();
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 「よくある質問」リンクからよくある質問画面を別タブに開く")
	void test04() {
		driver.get("http://localhost:8080/lms/");
		driver.findElement(By.id("loginId")).sendKeys("StudentAA01");
		driver.findElement(By.id("password")).sendKeys("StudentAA01a");
		driver.findElement(By.cssSelector("input[type='submit']")).click();

		driver.findElement(By.linkText("機能")).click();
		driver.findElement(By.linkText("ヘルプ")).click();
		WebElement faqLink = driver.findElement(By.linkText("よくある質問"));
		faqLink.sendKeys(Keys.chord(Keys.CONTROL, Keys.RETURN));
		Set<String> handles = driver.getWindowHandles();
		for (String handle : handles) {
			if (!handle.equals(originalHandle)) {
				driver.switchTo().window(handle); // 新しいタブに切り替え
				break;
			}
		}
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 キーワード検索で該当キーワードを含む検索結果だけ表示")
	void test05() {
		String originalHandle = driver.getWindowHandle();
		driver.get("http://localhost:8080/lms/");
		driver.findElement(By.id("loginId")).sendKeys("StudentAA01");
		driver.findElement(By.id("password")).sendKeys("StudentAA01a");
		driver.findElement(By.cssSelector("input[type='submit']")).click();

		driver.findElement(By.linkText("機能")).click();
		driver.findElement(By.linkText("ヘルプ")).click();
		WebElement faqLink = driver.findElement(By.linkText("よくある質問"));
		faqLink.sendKeys(Keys.chord(Keys.CONTROL, Keys.RETURN));

		Set<String> handles = driver.getWindowHandles();
		for (String handle : handles) {
			if (!handle.equals(originalHandle)) {
				driver.switchTo().window(handle); // 新しいタブに切り替え
				break;
			}
		}
		WebElement keywordInput = driver.findElement(By.id("form"));
		keywordInput.sendKeys("あ");
		driver.findElement(By.cssSelector("input[type='submit'][value='検索']")).click();
	}

	@Test
	@Order(6)
	@DisplayName("テスト06 「クリア」ボタン押下で入力したキーワードを消去")
	void test06() {
		driver.get("http://localhost:8080/lms/faq");
		WebElement keywordInput = driver.findElement(By.id("form"));
		keywordInput.sendKeys("テスト");
		driver.findElement(By.cssSelector("input[type='button'][value='クリア']")).click();
	}
}
