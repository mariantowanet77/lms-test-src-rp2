package jp.co.sss.lms.ct.f02_faq;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
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
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * 結合テスト よくある質問機能
 * ケース06
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース06 カテゴリ検索 正常系")
public class Case06 {

	ChromeDriver driver = new ChromeDriver();
	String originalHandle = driver.getWindowHandle();

	/** 前処理 */
	@BeforeAll
	static void before() throws Exception {
		Path evidenceDir = Path.of("evidence/case06");
		if (Files.exists(evidenceDir)) {
			Files.walk(evidenceDir)
					.map(Path::toFile)
					.sorted((a, b) -> -a.compareTo(b)) // 子 → 親の順で削除
					.forEach(File::delete);
		}
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
		js.executeScript("document.body.style.zoom='70%'");
		//スクショを取る処理
		Thread.sleep(2000);
		File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		String fileName = "evidence/case06/" + testInfo.getTestMethod().get().getName() + ".png";
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
		// TODO ここに追加
		driver.get("http://localhost:8080/lms/");
		assertEquals("ログイン | LMS", driver.getTitle());
	}

	@Test
	@Order(2)
	@DisplayName("テスト02 初回ログイン済みの受講生ユーザーでログイン")
	void test02() throws Exception {
		// TODO ここに追加
		//ログイン
		driver.get("http://localhost:8080/lms/");
		driver.findElement(By.id("loginId")).sendKeys("StudentAA01");
		driver.findElement(By.id("password")).sendKeys("StudentAA01a");
		driver.findElement(By.cssSelector("input[type='submit']")).click();

		Thread.sleep(500);
		assertEquals("コース詳細 | LMS", driver.getTitle());
	}

	@Test
	@Order(3)
	@DisplayName("テスト03 上部メニューの「ヘルプ」リンクからヘルプ画面に遷移")
	void test03() throws Exception {
		// TODO ここに追加
		driver.get("http://localhost:8080/lms/");
		//ログイン
		driver.findElement(By.id("loginId")).sendKeys("StudentAA01");
		driver.findElement(By.id("password")).sendKeys("StudentAA01a");
		driver.findElement(By.cssSelector("input[type='submit']")).click();

		//機能→ヘルプをクリック
		Thread.sleep(500);
		driver.findElement(By.linkText("機能")).click();
		driver.findElement(By.linkText("ヘルプ")).click();
		assertEquals("ヘルプ | LMS", driver.getTitle());

	}

	@Test
	@Order(4)
	@DisplayName("テスト04 「よくある質問」リンクからよくある質問画面を別タブに開く")
	void test04() throws Exception {
		// TODO ここに追加
		driver.get("http://localhost:8080/lms/");
		//ログイン
		driver.findElement(By.id("loginId")).sendKeys("StudentAA01");
		driver.findElement(By.id("password")).sendKeys("StudentAA01a");
		driver.findElement(By.cssSelector("input[type='submit']")).click();
		Thread.sleep(500);
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
		assertEquals("よくある質問 | LMS", driver.getTitle());
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 カテゴリ検索で該当カテゴリの検索結果だけ表示")
	void test05() throws Exception {
		String originalHandle = driver.getWindowHandle();

		// ログイン処理
		driver.get("http://localhost:8080/lms/");
		driver.findElement(By.id("loginId")).sendKeys("StudentAA01");
		driver.findElement(By.id("password")).sendKeys("StudentAA01a");
		driver.findElement(By.cssSelector("input[type='submit']")).click();

		// ヘルプ → よくある質問を新しいタブで開く
		Thread.sleep(500);
		driver.findElement(By.linkText("機能")).click();
		driver.findElement(By.linkText("ヘルプ")).click();
		WebElement faqLink = driver.findElement(By.linkText("よくある質問"));
		faqLink.sendKeys(Keys.chord(Keys.CONTROL, Keys.RETURN));

		// 新しいタブに切り替え
		Set<String> handles = driver.getWindowHandles();
		for (String handle : handles) {
			if (!handle.equals(originalHandle)) {
				driver.switchTo().window(handle);
				break;
			}
		}

		// --- カテゴリ検索（研修関係）
		WebElement faqCategory = driver.findElement(By.linkText("【研修関係】"));
		faqCategory.sendKeys(Keys.chord(Keys.CONTROL, Keys.RETURN));
		// タブ一覧をリストに変換
		List<String> tabs = new ArrayList<>(driver.getWindowHandles());

		// 一番右（最後に開かれたタブ）に切り替える
		driver.switchTo().window(tabs.get(tabs.size() - 1));
	}

	@Test
	@Order(6)
	@DisplayName("テスト05 カテゴリ検索で該当カテゴリの検索結果だけ表示")
	void test06() throws Exception {
		String originalHandle = driver.getWindowHandle();

		// ログイン処理
		driver.get("http://localhost:8080/lms/");
		driver.findElement(By.id("loginId")).sendKeys("StudentAA01");
		driver.findElement(By.id("password")).sendKeys("StudentAA01a");
		driver.findElement(By.cssSelector("input[type='submit']")).click();

		// ヘルプ → よくある質問を新しいタブで開く
		Thread.sleep(500);
		driver.findElement(By.linkText("機能")).click();
		driver.findElement(By.linkText("ヘルプ")).click();
		WebElement faqLink = driver.findElement(By.linkText("よくある質問"));
		faqLink.sendKeys(Keys.chord(Keys.CONTROL, Keys.RETURN));

		// 新しいタブに切り替え
		Set<String> handles = driver.getWindowHandles();
		for (String handle : handles) {
			if (!handle.equals(originalHandle)) {
				driver.switchTo().window(handle);
				break;
			}
		}

		// --- カテゴリ検索（研修関係）
		WebElement faqCategory = driver.findElement(By.linkText("【人材開発支援助成金】"));
		faqCategory.sendKeys(Keys.chord(Keys.CONTROL, Keys.RETURN));
		// タブ一覧をリストに変換
		List<String> tabs = new ArrayList<>(driver.getWindowHandles());

		// 一番右（最後に開かれたタブ）に切り替える
		driver.switchTo().window(tabs.get(tabs.size() - 1));
	}

	@Test
	@Order(7)
	@DisplayName("テスト05 カテゴリ検索で該当カテゴリの検索結果だけ表示")
	void test07() throws Exception {
		String originalHandle = driver.getWindowHandle();

		// ログイン処理
		driver.get("http://localhost:8080/lms/");
		driver.findElement(By.id("loginId")).sendKeys("StudentAA01");
		driver.findElement(By.id("password")).sendKeys("StudentAA01a");
		driver.findElement(By.cssSelector("input[type='submit']")).click();

		// ヘルプ → よくある質問を新しいタブで開く
		Thread.sleep(500);
		driver.findElement(By.linkText("機能")).click();
		driver.findElement(By.linkText("ヘルプ")).click();
		WebElement faqLink = driver.findElement(By.linkText("よくある質問"));
		faqLink.sendKeys(Keys.chord(Keys.CONTROL, Keys.RETURN));

		// 新しいタブに切り替え
		Set<String> handles = driver.getWindowHandles();
		for (String handle : handles) {
			if (!handle.equals(originalHandle)) {
				driver.switchTo().window(handle);
				break;
			}
		}

		// --- カテゴリ検索（研修関係）
		WebElement faqCategory = driver.findElement(By.linkText("【遠隔研修】"));
		faqCategory.sendKeys(Keys.chord(Keys.CONTROL, Keys.RETURN));
		// タブ一覧をリストに変換
		List<String> tabs = new ArrayList<>(driver.getWindowHandles());

		// 一番右（最後に開かれたタブ）に切り替える
		driver.switchTo().window(tabs.get(tabs.size() - 1));
	}

	@Test
	@Order(8)
	@DisplayName("テスト06 検索結果の質問をクリックしその回答を表示")
	void test08() throws Exception {

		// ログイン処理
		driver.get("http://localhost:8080/lms/");
		driver.findElement(By.id("loginId")).sendKeys("StudentAA01");
		driver.findElement(By.id("password")).sendKeys("StudentAA01a");
		driver.findElement(By.cssSelector("input[type='submit']")).click();

		// ヘルプ → よくある質問を新しいタブで開く
		Thread.sleep(500);
		driver.findElement(By.linkText("機能")).click();
		driver.findElement(By.linkText("ヘルプ")).click();
		WebElement faqLink = driver.findElement(By.linkText("よくある質問"));
		faqLink.sendKeys(Keys.chord(Keys.CONTROL, Keys.RETURN));

		// タブの切り替え
		List<String> tabs = new ArrayList<>(driver.getWindowHandles());
		driver.switchTo().window(tabs.get(tabs.size() - 1));

		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("document.body.style.zoom='70%'");

		// --- 1件目の質問 (dt) をクリック
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		WebElement firstQuestion = wait.until(
				ExpectedConditions.elementToBeClickable(
						By.xpath("//*[@id='DataTables_Table_0']/tbody/tr[1]/td/dl/dt")));
		firstQuestion.click();

		// --- 1件目の回答 (dd) を待って表示確認
		WebElement answer = wait.until(
				ExpectedConditions.visibilityOfElementLocated(
						By.xpath("//*[@id='DataTables_Table_0']/tbody/tr[1]/td/dl/dd")));
		// 表示されていることを検証
		assertTrue(answer.isDisplayed());
	}

}