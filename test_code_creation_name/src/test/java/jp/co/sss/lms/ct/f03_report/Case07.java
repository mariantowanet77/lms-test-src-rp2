package jp.co.sss.lms.ct.f03_report;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

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
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 * 結合テスト レポート機能
 * ケース07
 * 受講生 レポート新規登録(日報) 正常系
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース07 受講生 レポート新規登録(日報) 正常系")
public class Case07 {

	ChromeDriver driver = new ChromeDriver();
	String originalHandle = driver.getWindowHandle();
	JavascriptExecutor js = (JavascriptExecutor) driver;

	private void moveTestPage() {
		driver.get("http://localhost:8080/lms/");

	}

	private void loginAsStudent() {
		driver.get("http://localhost:8080/lms/");
		driver.findElement(By.id("loginId")).sendKeys("StudentAA01");
		driver.findElement(By.id("password")).sendKeys("StudentAA01a");
		driver.findElement(By.cssSelector("input[type='submit']")).click();
	}

	private void goToFirstUnsubmittedDetail() {

		js.executeScript("document.body.style.zoom='70%'");
		List<WebElement> rows = driver.findElements(
				By.cssSelector("table.table-hover.sctionList tbody tr"));

		for (WebElement row : rows) {
			if (row.getText().contains("未提出")) {
				row.findElement(By.cssSelector("input[type='submit'][value='詳細']")).click();
				break;
			}
		}
	}

	private void pushSubmitButton() throws Exception {
		// 「提出する」ボタンを探してクリック
		WebElement submitBtn = driver.findElement(
				By.cssSelector("input[type='submit'][value$='を提出する']"));
		submitBtn.click();

	}

	private void pushSubmitTextButton() {

		WebElement keywordInput = driver.findElement(By.id("content_0"));
		keywordInput.sendKeys("たのしかったあああああああああああ！！！");

		WebElement submitBtn = driver.findElement(
				By.cssSelector("button.btn.btn-primary"));
		submitBtn.click();
	}

	/** 前処理 */
	@BeforeAll
	static void before() throws Exception {
		Path evidenceDir = Path.of("evidence/case07");
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

		//スクショを取る処理
		Thread.sleep(2000);
		File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		String fileName = "evidence/case07/" + testInfo.getTestMethod().get().getName() + ".png";
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
		// TODO: URLは各自の環境に合わせて設定
		moveTestPage();

		// ページタイトルで確認
		assertEquals("ログイン | LMS", driver.getTitle());
	}

	@Test
	@Order(2)
	@DisplayName("テスト02 初回ログイン済みの受講生ユーザーでログイン")
	void test02() throws Exception {
		// ログインフォーム入力
		loginAsStudent();
		// 遷移確認
		Thread.sleep(500);
		assertEquals("コース詳細 | LMS", driver.getTitle());
	}

	@Test
	@Order(3)
	@DisplayName("テスト03 未提出の研修日の「詳細」ボタンを押下しセクション詳細画面に遷移")
	void test03() throws Exception {
		loginAsStudent();
		Thread.sleep(500);
		goToFirstUnsubmittedDetail();
		assertEquals("セクション詳細 | LMS", driver.getTitle());
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 「提出する」ボタンを押下しレポート登録画面に遷移")
	void test04() throws Exception {
		// TODO: 提出ボタンのセレクタは環境依存、修正して使用
		loginAsStudent();
		Thread.sleep(500);
		goToFirstUnsubmittedDetail();
		Thread.sleep(500);
		pushSubmitButton();

		Thread.sleep(500);
		// 遷移先のタイトル確認（環境に合わせて修正）
		assertEquals("レポート登録 | LMS", driver.getTitle());

	}

	@Test
	@Order(5)
	@DisplayName("テスト05 報告内容を入力して「提出する」ボタンを押下し確認ボタン名が更新される")
	void test05() throws Exception {
		loginAsStudent();
		Thread.sleep(500);
		goToFirstUnsubmittedDetail();
		Thread.sleep(500);
		pushSubmitButton();
		Thread.sleep(500);
		pushSubmitTextButton();
		Thread.sleep(500);
		// 遷移先のタイトル確認（環境に合わせて修正）
		assertEquals("セクション詳細 | LMS", driver.getTitle());
	}
}
