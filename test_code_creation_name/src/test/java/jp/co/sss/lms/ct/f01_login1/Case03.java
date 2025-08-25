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
 * ケース03
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース03 受講生 ログイン 正常系")
public class Case03 {
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
		Thread.sleep(2000); // ページロード待ち(任意)
		File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

		// 保存先パス
		Path targetPath = new File("evidence/case03/test01.png").toPath();
		Files.createDirectories(targetPath.getParent()); // フォルダ作成
		Files.copy(screenshot.toPath(), targetPath);
	}

	@Test
	@Order(2)
	@DisplayName("テスト02 初回ログイン済みの受講生ユーザーでログイン")
	void test02() throws Exception {
		// TODO ここに追加
		driver.get("http://localhost:8080/lms/");

		driver.findElement(By.id("loginId")).sendKeys("StudentAA01");

		// パスワード入力
		driver.findElement(By.id("password")).sendKeys("StudentAA01a");

		// ログインボタンをクリック
		driver.findElement(By.cssSelector("input[type='submit']")).click();
		// --- スクショ処理 ---
		Thread.sleep(2000); // ページロード待ち(任意)
		File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

		// 保存先パス
		Path targetPath = new File("evidence/case03/test02.png").toPath();
		Files.createDirectories(targetPath.getParent()); // フォルダ作成
		Files.copy(screenshot.toPath(), targetPath);
	}

}
