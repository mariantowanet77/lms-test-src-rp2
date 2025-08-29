package jp.co.sss.lms.ct.f04_attendance;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
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
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * 結合テスト 勤怠管理機能
 * ケース11
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース11 受講生 勤怠直接編集 正常系")
public class Case11 {

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
		Path evidenceDir = Path.of("evidence/case11");
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
		String fileName = "evidence/case11/" + testInfo.getTestMethod().get().getName() + ".png";
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
			System.out.println("errorAttendance2");
		}
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 「勤怠情報を直接編集する」リンクから勤怠情報直接変更画面に遷移")
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
			System.out.println("errorAttendance2");
		}
		Thread.sleep(500);
		WebElement editLink = driver.findElement(By.linkText("勤怠情報を直接編集する"));
		editLink.click();

		Thread.sleep(500);

		// 遷移確認
		assertEquals("勤怠情報変更｜LMS", driver.getTitle());

	}

	@Test
	@Order(5)
	@DisplayName("テスト05 すべての研修日程の勤怠情報を正しく更新し勤怠管理画面に遷移")
	void test05() throws Exception {
		// TODO ここに追加
		test04();//これでいけたんだ...

		// テーブルを取得
		WebElement attendanceTable = driver.findElement(By.cssSelector("table.dataTable"));

		// 各行を取得
		List<WebElement> rows = attendanceTable.findElements(By.cssSelector("tbody tr"));
		//取得後、空欄を探し出し、それぞれ定時を入力(9:00、18:00)
		for (int i = 0; i < rows.size(); i++) {
			WebElement row = rows.get(i);

			// 出勤時間
			WebElement startHour = row.findElement(By.id("startHour" + i));
			WebElement startMinute = row.findElement(By.id("startMinute" + i));

			// 退勤時間
			WebElement endHour = row.findElement(By.id("endHour" + i));
			WebElement endMinute = row.findElement(By.id("endMinute" + i));

			// 空欄の場合のみ設定
			if (startHour.getAttribute("value").isEmpty()) {
				new Select(startHour).selectByValue("9");
			}
			if (startMinute.getAttribute("value").isEmpty()) {
				new Select(startMinute).selectByValue("0");
			}
			if (endHour.getAttribute("value").isEmpty()) {
				new Select(endHour).selectByValue("18");
			}
			if (endMinute.getAttribute("value").isEmpty()) {
				new Select(endMinute).selectByValue("0");
			}
		}
		js.executeScript("document.body.style.zoom='70%'");
		Thread.sleep(500);
		// 更新ボタンを押す
		WebElement updateButton = driver.findElement(By.cssSelector(".update-button"));
		updateButton.click();
		Thread.sleep(500);
		// 確認ダイアログ（confirm）を自動でOK
		Alert alert = driver.switchTo().alert();
		alert.accept(); // OKを押す
		Thread.sleep(500);
		//最後の遷移確認
		assertEquals("勤怠情報変更｜LMS", driver.getTitle());
	}
}
