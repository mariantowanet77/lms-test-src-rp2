package jp.co.sss.lms.ct.f01_login1;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 * 結合テスト ログイン機能①
 * ケース02
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース02 受講生 ログイン 認証失敗")
public class Case02 {
	ChromeDriver driver = new ChromeDriver();

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
	void test01() throws Exception {
		// TODO ここに追加

		driver.get("http://localhost:8080/lms/");

		// --- スクショ処理 ---
		Thread.sleep(2000);
		File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

		// 保存先パス
		Path targetPath = new File("evidence/case02/test01.png").toPath();
		Files.createDirectories(targetPath.getParent()); // フォルダ作成
		Files.copy(screenshot.toPath(), targetPath);
	}

	@Test
	@Order(2)
	@DisplayName("テスト02 DBに登録されていないユーザーでログイン")
	void test02() throws Exception {
		// TODO ここに追加
		// ログインID入力
		driver.get("http://localhost:8080/lms/");

		driver.findElement(By.id("loginId")).sendKeys("korehatestdesu11");

		// パスワード入力
		driver.findElement(By.id("password")).sendKeys("o");

		// ログインボタンをクリック
		driver.findElement(By.cssSelector("input[type='submit']")).click();

		// --- スクショ処理 ---
		Thread.sleep(2000); // ページロード待ち(任意)
		File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

		// 保存先パス
		Path targetPath = new File("evidence/case02/test02.png").toPath();
		Files.createDirectories(targetPath.getParent()); // フォルダ作成
		Files.copy(screenshot.toPath(), targetPath);

	}

	@Test
	@Order(3)
	@DisplayName("テスト02 全部空白でログインボタンを押す")
	void test03() throws Exception {
		// TODO ここに追加
		// ログインID入力
		driver.get("http://localhost:8080/lms/");

		driver.findElement(By.id("loginId")).sendKeys("");

		// パスワード入力
		driver.findElement(By.id("password")).sendKeys("");

		// ログインボタンをクリック
		driver.findElement(By.cssSelector("input[type='submit']")).click();

		// --- スクショ処理 ---
		Thread.sleep(2000); // ページロード待ち(任意)
		File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

		// 保存先パス
		Path targetPath = new File("evidence/case02/test03.png").toPath();
		Files.createDirectories(targetPath.getParent()); // フォルダ作成
		Files.copy(screenshot.toPath(), targetPath);
	}

	@Test
	@Order(4)
	@DisplayName("テスト02 登録されてないユーザーID、登録されたパスワードでログイン")
	void test04() throws Exception {
		// TODO ここに追加
		// ログインID入力
		driver.get("http://localhost:8080/lms/");

		driver.findElement(By.id("loginId")).sendKeys("StudentAA01");

		// パスワード入力
		driver.findElement(By.id("password")).sendKeys("Student000000");

		// ログインボタンをクリック
		driver.findElement(By.cssSelector("input[type='submit']")).click();

		//スクショを保存
		File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

		// 保存先パス
		Path targetPath = new File("evidence/case02/test04.png").toPath();
		Files.createDirectories(targetPath.getParent()); // フォルダ作成
		Files.copy(screenshot.toPath(), targetPath);
	}

}
