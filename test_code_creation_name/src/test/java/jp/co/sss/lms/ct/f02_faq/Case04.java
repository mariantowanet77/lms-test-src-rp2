package jp.co.sss.lms.ct.f02_faq;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 * 結合テスト よくある質問機能
 * ケース04
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース04 よくある質問画面への遷移")
public class Case04 {

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
	void test01() {
		// TODO ここに追加
		driver.get("http://localhost:8080/lms/");
	}

	@Test
	@Order(2)
	@DisplayName("テスト02 初回ログイン済みの受講生ユーザーでログイン")
	void test02() {
		// TODO ここに追加
		driver.get("http://localhost:8080/lms/");

		driver.findElement(By.id("loginId")).sendKeys("StudentAA01");

		// パスワード入力
		driver.findElement(By.id("password")).sendKeys("StudentAA01a");

		// ログインボタンをクリック
		driver.findElement(By.cssSelector("input[type='submit']")).click();
	}

	@Test
	@Order(3)
	@DisplayName("テスト03 上部メニューの「ヘルプ」リンクからヘルプ画面に遷移")
	void test03() {
		// TODO ここに追加
		driver.get("http://localhost:8080/lms/");

		driver.findElement(By.id("loginId")).sendKeys("StudentAA01");

		// パスワード入力
		driver.findElement(By.id("password")).sendKeys("StudentAA01a");

		// ログインボタンをクリック
		driver.findElement(By.cssSelector("input[type='submit']")).click();

		driver.findElement(By.linkText("機能")).click();
		driver.findElement(By.linkText("ヘルプ")).click();
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 「よくある質問」リンクからよくある質問画面を別タブに開く")
	void test04() {
		// TODO ここに追加
		driver.get("http://localhost:8080/lms/");

		driver.findElement(By.id("loginId")).sendKeys("StudentAA01");

		// パスワード入力
		driver.findElement(By.id("password")).sendKeys("StudentAA01a");

		// ログインボタンをクリック
		driver.findElement(By.cssSelector("input[type='submit']")).click();

		driver.findElement(By.linkText("機能")).click();
		driver.findElement(By.linkText("ヘルプ")).click();
		WebElement faqLink = driver.findElement(By.linkText("よくある質問"));
		faqLink.sendKeys(Keys.chord(Keys.CONTROL, Keys.RETURN));

	}

}
