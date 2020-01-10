package APITest.APITest;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Set;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import java.util.List;

import org.json.simple.parser.*;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Cookies;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import static io.restassured.RestAssured.given;

import org.openqa.selenium.Cookie;
import org.openqa.selenium.JavascriptExecutor;

public class Unittest {
  @Test
  public void f() {
	  String exePath = "C:\\Users\\mahesh.x.kuma\\Documents\\Automation\\chromedriver_win32\\chromedriver.exe";
	  System.setProperty("webdriver.chrome.driver", exePath);
	  
	  
	  
	 // code for network console
	  ChromeOptions options = new ChromeOptions();
	    // if you like to specify another profile
	    options.addArguments("user-data-dir=/root/Downloads/aaa"); 
	    options.addArguments("start-maximized");
	    DesiredCapabilities capabilities = DesiredCapabilities.chrome();
	    capabilities.setCapability(ChromeOptions.CAPABILITY, options);
	    WebDriver driver = new ChromeDriver(capabilities);
	  
	  //WebDriver driver = new ChromeDriver();
	  driver.get("https://pgo-qa.accentureanalytics.com/");
	  WebDriverWait wait=new WebDriverWait(driver, 50);
	  try {
		Thread.sleep(50000);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	  
	  driver.findElement(By.id("email")).sendKeys("mahesh.kumar@pressganey.com");
	 boolean loginclick= driver.findElement(By.xpath("//*[@class='loginButton']//*[text()='Sign In']")).isEnabled();
	 
	 if (loginclick)
	 {
		 driver.findElement(By.xpath("//*[@class='loginButton']//*[text()='Sign In']")).click();
	 }
	 else
	 {
		 try {
			Thread.sleep(50000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 driver.findElement(By.xpath("//*[@class='loginButton']//*[text()='Sign In']")).click();
		 
	 }
	 WebElement h;
	 h=wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("password")));
	 h.sendKeys("Newwashing@59");
	 h=wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("next")));
	 h.click();
	 
	 // enter top client  //*[@id='cda-srch']//following-sibling::input
	 h=wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='cda-srch']//following-sibling::input")));
	 h.sendKeys("50000");
	 // checking if 50000 appears in drop down //*[@class='ui-listbox-list']/li/span
	 h=wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@class='ui-listbox-list']/li/span")));
	 h.click();
	 // waiting for page to load
	 try {
		Thread.sleep(3000);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	 
	 // storing cookies
	// create file named Cookies to store Login Information		
     File file = new File("Cookies.data");							
     try		
     {	  
         // Delete old file if exists
			file.delete();		
         file.createNewFile();			
         FileWriter fileWrite = new FileWriter(file);							
         BufferedWriter Bwrite = new BufferedWriter(fileWrite);							
         // loop for getting the cookie information 		
         	
         // loop for getting the cookie information 		
         for(Cookie ck : driver.manage().getCookies())							
         {			
             Bwrite.write((ck.getName()+";"+ck.getValue()+";"+ck.getDomain()+";"+ck.getPath()+";"+ck.getExpiry()+";"+ck.isSecure()));																									
             Bwrite.newLine();             
         }			
         Bwrite.close();			
         fileWrite.close();	
         
     }
     catch(Exception ex)					
     {		
         ex.printStackTrace();			
     }
     

     
     Set<Cookie> allcookies = driver.manage().getCookies();
     System.out.println(allcookies);
     
     // to print one cookie
     System.out.println("+++++++++++++++identity+++++++++++");
     System.out.println(driver.manage().getCookieNamed("Identity").getValue());
     
     String s= ((ChromeDriver) driver).getLocalStorage().getItem("x-xsrf-token");
     
 	System.out.println("--------"+s);
 	
// 	String a[]=s.split(":");
// 	System.out.println("key"+a[0]);
// 	System.out.println("value"+a[1]);
// 	Sytem.out.pri
    
 	JSONParser parser = new JSONParser();
 	JSONObject json=null;
	try {
		json = (JSONObject) parser.parse(s);
	} catch (ParseException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
 	System.out.println(json.get("token"));
 	
 	
 	
 	
// to read request
    JSONParser jsonParser = new JSONParser();
    JSONArray usersearRequest=null;
    try (FileReader reader = new FileReader("UserSearch.json"))
    {
        //Read JSON file
        Object obj = jsonParser.parse(reader);

         usersearRequest = new JSONArray();
       
        usersearRequest.add(obj);
        System.out.println("---------Jason request-------------");
        System.out.println(usersearRequest);
        
    }
    
   catch (FileNotFoundException e) {
      e.printStackTrace();
  } catch (IOException e) {
      e.printStackTrace();
  } catch (ParseException e) {
      e.printStackTrace();
  }
 	// API code
 	
 	String url="https://pgo-qa.accentureanalytics.com/api/uam/users/search";
 	RestAssured.baseURI="https://pgo-qa.accentureanalytics.com";
 	RestAssured.useRelaxedHTTPSValidation();
 	
 	JSONObject requestParams = new JSONObject();
 	
 	// cookie
 	List restAssuredCookies = new ArrayList();
 	for (org.openqa.selenium.Cookie cookie : allcookies) {
        restAssuredCookies.add(new io.restassured.http.Cookie.Builder(cookie.getName(), cookie.getValue()).build());
        System.out.println(cookie.getName());
        System.out.println(cookie.getValue());
    }
 	RequestSpecification request = RestAssured.given().cookies(new Cookies (restAssuredCookies));
 	
 	String getresponse=RestAssured.given().cookie("Identity",driver.manage().getCookieNamed("Identity").getValue()).headers("x-xsrf-token", json.get("token"))
 			.contentType(ContentType.JSON).body(usersearRequest).when().post("/api/uam/users/search").print();
 		
	System.out.println("+++++++++++++Response+++++++++");
	System.out.println(getresponse);
  }
}
