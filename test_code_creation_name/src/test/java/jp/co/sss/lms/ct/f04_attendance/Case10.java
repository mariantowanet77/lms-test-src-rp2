package jp.co.sss.lms.ct.f04_attendance;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * 結合テスト 勤怠管理機能
 * ケース10
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース10 受講生 勤怠登録 正常系")
public class Case10 {

	ChromeDriver driver = new ChromeDriver();
	String originalHandle = driver.getWindowHandle();
	JavascriptExecutor js = (JavascriptExecutor) driver;

	private void moveTestPage() {
		driver.get("http://localhost:8080/lms/");

	}

	private void moveAttendancePage() throws Exception {
		loginAsStudent();
		//機能→ヘルプをクリック
		Thread.sleep(500);
		driver.findElement(By.linkText("勤怠")).click();
	}

	private void loginAsStudent() {
		driver.get("http://localhost:8080/lms/");
		driver.findElement(By.id("loginId")).sendKeys("StudentAA01");
		driver.findElement(By.id("password")).sendKeys("StudentAA01a");
		driver.findElement(By.cssSelector("input[type='submit']")).click();
	}

	/** 前処理 */
	@BeforeAll
	static void before() throws Exception {
		Path evidenceDir = Path.of("evidence/case10");
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
		String fileName = "evidence/case10/" + testInfo.getTestMethod().get().getName() + ".png";
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
		moveTestPage();
	}

	@Test
	@Order(2)
	@DisplayName("テスト02 初回ログイン済みの受講生ユーザーでログイン")
	void test02() {
		// TODO ここに追加
		loginAsStudent();
	}

	@Test
	@Order(3)
	@DisplayName("テスト03 上部メニューの「勤怠」リンクから勤怠管理画面に遷移")
	void test03() throws Exception {
		// TODO ここに追加
		moveAttendancePage();
		Thread.sleep(500);
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
			Alert alert = wait.until(ExpectedConditions.alertIsPresent());
			System.out.println("過去日の勤怠に未入力があります。" + alert.getText());
			alert.accept(); // OK押下
		} catch (TimeoutException e) {
			System.out.println("error");
		}
		Thread.sleep(500);
		assertEquals("勤怠情報変更｜LMS", driver.getTitle());
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 「出勤」ボタンを押下し出勤時間を登録")
	void test04() throws Exception {
		// TODO ここに追加
		moveAttendancePage();

		Thread.sleep(500);

		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
			Alert alert = wait.until(ExpectedConditions.alertIsPresent());
			System.out.println("過去日の勤怠に未入力があります。" + alert.getText());
			alert.accept(); // OK押下
		} catch (TimeoutException e) {
			System.out.println("error");
		}
		Thread.sleep(500);
		// 出勤ボタンを探してクリック
		WebElement punchInBtn = driver.findElement(
				By.cssSelector("input[type='submit'][value='出勤']"));

		Thread.sleep(500);
		// 通常クリック
		// punchInBtn.click();

		// 万一クリックできない場合は JS クリックに切り替え
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", punchInBtn);

		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
			Alert alert = wait.until(ExpectedConditions.alertIsPresent());
			System.out.println("打刻します。よろしいですか？" + alert.getText());
			alert.accept(); // OK押下
		} catch (TimeoutException e) {
			System.out.println("error2");
		}
		Thread.sleep(500);
		assertEquals("勤怠情報変更｜LMS", driver.getTitle());
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 「退勤」ボタンを押下し退勤時間を登録")
	void test05() throws Exception {
		// TODO ここに追加
		moveAttendancePage();

		Thread.sleep(500);

		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
			Alert alert = wait.until(ExpectedConditions.alertIsPresent());
			System.out.println("過去日の勤怠に未入力があります。" + alert.getText());
			alert.accept(); // OK押下
		} catch (TimeoutException e) {
			System.out.println("error3");
		}
		Thread.sleep(500);
		// 出勤ボタンを探してクリック
		WebElement punchInBtn = driver.findElement(
				By.cssSelector("input[type='submit'][value='退勤']"));

		Thread.sleep(500);
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", punchInBtn);

		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
			Alert alert = wait.until(ExpectedConditions.alertIsPresent());
			System.out.println("打刻します。よろしいですか？" + alert.getText());
			alert.accept(); // OK押下
		} catch (TimeoutException e) {
			System.out.println("error4");
		}
		Thread.sleep(500);
		assertEquals("勤怠情報変更｜LMS", driver.getTitle());
	}

}
