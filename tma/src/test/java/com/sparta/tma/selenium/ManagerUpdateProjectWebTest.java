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
//public class ManagerUpdateProjectWebTest {
//    private  static final String DRIVER_PATH = "src/test/resources/chromedriver.exe";
//    private  static ChromeDriverService service;
//    private WebDriver driver;
//    private Map<String, Object> vars;
//    JavascriptExecutor js;
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
//    public void managerupdateproject() {
//        driver.get("http://localhost:8080/login");
//        driver.manage().window().setSize(new Dimension(1192, 725));
//        driver.findElement(By.id("username")).click();
//        driver.findElement(By.id("username")).sendKeys("manager");
//        driver.findElement(By.id("password")).click();
//        driver.findElement(By.id("password")).sendKeys("manager");
//        driver.findElement(By.cssSelector(".btn")).click();
//        driver.findElement(By.linkText("View Employees")).click();
//        driver.findElement(By.cssSelector("section:nth-child(3) p:nth-child(2)")).click();
//        driver.findElement(By.cssSelector(".fa-solid")).click();
//        driver.findElement(By.id("project")).click();
//        driver.findElement(By.id("project")).click();
//
//        WebElement dropdown = driver.findElement(By.id("project"));
//        String value = dropdown.findElement(By.xpath("//option[. = 'New Starters']")).getText();
//        dropdown.findElement(By.xpath("//option[. = 'New Starters']")).click();
//
//        System.out.println(value);
//
//        driver.findElement(By.cssSelector(".btn:nth-child(3)")).click();
//        assertThat(driver.findElement(By.cssSelector("h1:nth-child(3)")).getText(), is("New Starters"));
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
