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
 * ケース09
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース09 受講生 レポート登録 入力チェック")
public class Case09 {

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
			if (row.getText().contains("提出済み")) {
				row.findElement(By.cssSelector("input[type='submit'][value='詳細']")).click();
				break;
			}
		}
	}

	/**
	 * 各テスト終了後にスクリーンショットを保存
	 */
	@AfterEach
	void takeScreenshot(TestInfo testInfo) throws Exception {

		//スクショを取る処理
		Thread.sleep(2000);
		File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		String fileName = "evidence/case09/" + testInfo.getTestMethod().get().getName() + ".png";
		Path targetPath = new File(fileName).toPath();
		Files.createDirectories(targetPath.getParent());
		Files.copy(screenshot.toPath(), targetPath);
		//画面倍率を元に戻す。
		js.executeScript("document.body.style.zoom='100%'");
	}

	private void editReport() {
		List<WebElement> rows = driver.findElements(
				By.cssSelector("table.table-hover tbody tr"));

		for (WebElement row : rows) {
			if (row.getText().contains("日報【デモ】")) {
				// その行の「詳細」ボタンをクリック
				WebElement detailBtn = row.findElement(
						By.cssSelector("input[type='submit'][value='修正']"));

				// 通常クリックで問題なければこれでOK
				//detailBtn.click();

				// 万一 ElementClickInterceptedException が出る場合はJSでクリック
				((JavascriptExecutor) driver).executeScript("arguments[0].click();", detailBtn);

				break;
			}
		}
	}

	private void pushSubmitButton() throws Exception {
		// 「提出する」ボタンを探してクリック
		WebElement submitBtn = driver.findElement(
				By.cssSelector("input[type='submit'][value$='を確認する']"));
		submitBtn.click();

	}

	private void pushSubmitTextButton() {

		WebElement keywordInput = driver.findElement(By.id("content_0"));
		keywordInput.sendKeys("博多ラーメン");

		WebElement submitBtn = driver.findElement(
				By.cssSelector("button.btn.btn-primary"));
		submitBtn.click();
	}

	private void moveMypage() {
		WebElement userDetailLink = driver.findElement(By.cssSelector("a[href='/lms/user/detail']"));
		userDetailLink.click();

		assertEquals("ユーザー詳細", driver.getTitle());
	}

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

	@Test
	@Order(1)
	@DisplayName("テスト01 トップページURLでアクセス")
	void test01() {
		// TODO ここに追加
		moveTestPage();

		assertEquals("ログイン | LMS", driver.getTitle());

	}

	@Test
	@Order(2)
	@DisplayName("テスト02 初回ログイン済みの受講生ユーザーでログイン")
	void test02() throws Exception {
		// TODO ここに追加
		loginAsStudent();
		// 遷移確認
		Thread.sleep(500);
		assertEquals("コース詳細 | LMS", driver.getTitle());

	}

	@Test
	@Order(3)
	@DisplayName("テスト03 上部メニューの「ようこそ○○さん」リンクからユーザー詳細画面に遷移")
	void test03() throws Exception {
		// TODO ここに追加
		loginAsStudent();
		Thread.sleep(500);
		moveMypage();
		Thread.sleep(500);
		assertEquals("ユーザー詳細", driver.getTitle());

	}

	@Test
	@Order(4)
	@DisplayName("テスト04 該当レポートの「修正する」ボタンを押下しレポート登録画面に遷移")
	void test04() throws Exception {
		// TODO ここに追加
		loginAsStudent();
		Thread.sleep(500);
		moveMypage();
		Thread.sleep(500);
		js.executeScript("document.body.style.zoom='70%'");
		// レポート一覧テーブルの行を取得
		Thread.sleep(500);
		editReport();

		assertEquals("レポート詳細 | LMS", driver.getTitle());

	}

	//エラーが出てこずよくわかりませんでした。
	@Test
	@Order(5)
	@DisplayName("テスト05 報告内容を修正して「提出する」ボタンを押下しエラー表示：学習項目が未入力")
	void test05() {
		// TODO ここに追加
	}

	@Test
	@Order(6)
	@DisplayName("テスト06 不適切な内容で修正して「提出する」ボタンを押下しエラー表示：理解度が未入力")
	void test06() {
		// TODO ここに追加
	}

	@Test
	@Order(7)
	@DisplayName("テスト07 不適切な内容で修正して「提出する」ボタンを押下しエラー表示：目標の達成度が数値以外")
	void test07() {
		// TODO ここに追加
	}

	@Test
	@Order(8)
	@DisplayName("テスト08 不適切な内容で修正して「提出する」ボタンを押下しエラー表示：目標の達成度が範囲外")
	void test08() {
		// TODO ここに追加
	}

	@Test
	@Order(9)
	@DisplayName("テスト09 不適切な内容で修正して「提出する」ボタンを押下しエラー表示：目標の達成度・所感が未入力")
	void test09() {
		// TODO ここに追加
	}

	@Test
	@Order(10)
	@DisplayName("テスト10 不適切な内容で修正して「提出する」ボタンを押下しエラー表示：所感・一週間の振り返りが2000文字超")
	void test10() {
		// TODO ここに追加
	}

}
