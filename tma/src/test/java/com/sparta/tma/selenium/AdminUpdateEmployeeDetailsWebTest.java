//package com.sparta.tma.selenium;
//
//import org.junit.jupiter.api.*;
//import org.openqa.selenium.*;
//import org.openqa.selenium.chrome.ChromeDriverService;
//import org.openqa.selenium.chrome.ChromeOptions;
//import org.openqa.selenium.remote.RemoteWebDriver;
//
//import java.io.File;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//
//import static org.hamcrest.CoreMatchers.is;
//import static org.hamcrest.MatcherAssert.assertThat;
//
//public class AdminUpdateEmployeeDetailsWebTest {
//    private  static final String DRIVER_PATH = "src/test/resources/chromedriver.exe";
//    private  static ChromeDriverService service;
//    private WebDriver driver;
////    private Map<String, Object> vars;
////    JavascriptExecutor js;
//
//    public static ChromeOptions getChromeOptions() {
//        List<String> args = new ArrayList<>();
////        args.add("headless");
//        args.add("--start-maximized");
//        args.add("--remote-allow-origins=*");
//
//        ChromeOptions chromeOptions = new ChromeOptions();
//        chromeOptions.addArguments(args);
//        return chromeOptions;
//    }
//
//    @BeforeAll
//    public static void beforeAll() throws IOException {
//        service = new ChromeDriverService.Builder()
//                .usingDriverExecutable(new File(DRIVER_PATH))
//                .usingAnyFreePort()
//                .build();
//        service.start();
//    }
//
//    @BeforeEach
//    public void setup() {
//        driver = new RemoteWebDriver(service.getUrl(), getChromeOptions());
//    }
//
//    @Test
//    public void adminUpdateEmployeeDetails() {
//        driver.get("http://localhost:8080/login");
//        driver.manage().window().setSize(new Dimension(1192, 725));
//        driver.findElement(By.id("username")).click();
//        driver.findElement(By.id("username")).sendKeys("admin");
//        driver.findElement(By.id("password")).click();
//        driver.findElement(By.id("password")).sendKeys("admin");
//        driver.findElement(By.cssSelector(".btn")).click();
//        driver.findElement(By.linkText("View Employees")).click();
//        driver.findElement(By.cssSelector("section:nth-child(19)")).click();
//        driver.findElement(By.cssSelector("a")).click();
//        driver.findElement(By.cssSelector("h1:nth-child(5)")).click();
//        assertThat(driver.findElement(By.cssSelector("h1:nth-child(5)")).getText(), is("Unassigned"));
//
//    }
//
//    @AfterEach
//    public void afterEach() {
//        driver.quit();
//    }
//
//    @AfterAll
//    public static void afterAll() {
//        service.stop();
//    }
//}
