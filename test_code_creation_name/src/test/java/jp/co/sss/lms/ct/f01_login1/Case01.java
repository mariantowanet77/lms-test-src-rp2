package jp.co.sss.lms.ct.f01_login1;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;
import static org.junit.jupiter.api.Assertions.*;

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
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 * 結合テスト ログイン機能①
 * ケース01
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース01 ログイン画面への遷移")
public class Case01 {

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

		driver.get("http://localhost:8080/lms/");
		assertEquals("ログイン | LMS", driver.getTitle());
		// --- スクショ処理 ---
		Thread.sleep(2000); // ページロード待ち(任意)
		File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

		// 保存先パス
		Path targetPath = new File("evidence/case01/test01.png").toPath();
		Files.createDirectories(targetPath.getParent()); // フォルダ作成
		Files.copy(screenshot.toPath(), targetPath);
	}
}
