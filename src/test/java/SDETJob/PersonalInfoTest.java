package SDETJob;

import static org.testng.Assert.assertEquals;
//Homework:
//	  1) Finish all step and click on Apply
//	  2) Validate each value
//	    IP address: goto google and search for what is my ip
//	  3) Goto your email and find the email and click on it
//	  by id. SDET Application #id
import static org.testng.Assert.fail;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import io.github.bonigarcia.wdm.WebDriverManager;

public class PersonalInfoTest {
	WebDriver driver;
	String firstName;
	String lastName;
	int gender;
	String dateOfBirth;
	String email;
	String phoneNumber;
	String city;
	String State;
	String country;
	int annualSalary;
	List<String> technologies;
	int yearsOfExperience;
	String education;
	String github;
	List<String> certifications;
	String additionalsSkills;
	Faker data = new Faker();
	Random random = new Random();
	String Appid;
	String idStr;

	@BeforeClass /// runs once for all test
	public void setUp() {
		System.out.println("Setting up WebDriver in beforeClass...");
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}

	@BeforeMethod // runs before each test
	public void navigateToHomePage() {
		System.out.println("navigating to homepage in @BeforeMethod....");
		driver.get(
				"https://forms.zohopublic.com/murodil/form/JobApplicationForm/formperma/kOqgtfkv1dMJ4Df6k4_mekBNfNLIconAHvfdIk3CJSQ");
		firstName = data.name().firstName();
		lastName = data.name().lastName();
		gender = random.nextInt(2) + 1;
		dateOfBirth = data.date().birthday().toString();
		email = "kimyanursa@gmail.com";
		phoneNumber = data.phoneNumber().cellPhone().replace(".", "");
		city = data.address().cityName();
		State = data.address().stateAbbr();
		country = data.address().country();
		annualSalary = data.number().numberBetween(60000, 150000);
		technologies = new ArrayList<>();
		technologies.add("Java-" + data.number().numberBetween(1, 4));
		technologies.add("HTML-" + data.number().numberBetween(1, 4));
		technologies.add("Selenium WebDriver-" + data.number().numberBetween(1, 4));
		technologies.add("Maven-" + data.number().numberBetween(1, 4));
		technologies.add("Git-" + data.number().numberBetween(1, 4));
		technologies.add("TestNG-" + data.number().numberBetween(1, 4));
		technologies.add("Cucumber-" + data.number().numberBetween(1, 4));
		technologies.add("API Automation-" + data.number().numberBetween(1, 4));
		technologies.add("JDBC-" + data.number().numberBetween(1, 4));
		technologies.add("SQL-" + data.number().numberBetween(1, 4));
		System.out.println("=====" + technologies.add("SQL-" + data.number().numberBetween(1, 4)));
		yearsOfExperience = data.number().numberBetween(0, 11);
		education = "" + data.number().numberBetween(1, 4);
		github = "https://github.com/CybertekSchool/selenium-maven-automation.git";
		certifications = new ArrayList<>();
		certifications.add("Java OCA");
		certifications.add("AWS");
		certifications.add("Scrum Master");
		additionalsSkills = data.job().keySkills();
	}

	@Test
	public void submitFullApplication() throws InterruptedException {
		driver.findElement(By.xpath("//input[@name='Name_First']")).sendKeys(firstName);
		driver.findElement(By.xpath("//input[@name='Name_Last']")).sendKeys(lastName);
		setGender(gender);
		setDateOfBirth(dateOfBirth);
		driver.findElement(By.xpath("//input[@name='Email']")).sendKeys(email);
		driver.findElement(By.xpath("//input[@name='countrycode']")).sendKeys(phoneNumber.replaceAll(".,", ""));
		driver.findElement(By.xpath("//input[@name='Address_City']")).sendKeys(city);
		driver.findElement(By.xpath("//input[@name='Address_Region']")).sendKeys(State);
		Select countryElement = new Select(driver.findElement(By.xpath("//select[@id='Address_Country']")));
		countryElement.selectByVisibleText(country);
		// countryElement.selectByIndex(data.number().numberBetween(1,
		// countryElem.getOptions().size()));
		driver.findElement(By.xpath("//input[@name='Number']")).sendKeys(String.valueOf(annualSalary) + Keys.TAB);
		verifySalarycalculations(annualSalary);
		driver.findElement(By.xpath("//em[.=' Next ']")).click();

		// SECOND PAGE ACTIONS
		setSkillset(technologies);
		if (yearsOfExperience > 0) {
			driver.findElement(By.xpath("//a[@rating_value='" + yearsOfExperience + "']")).click();
		}
		Select educationList = new Select(driver.findElement(By.xpath("//select[@name='Dropdown']")));
		educationList.selectByIndex(data.number().numberBetween(1, educationList.getOptions().size()));

		driver.findElement(By.xpath("(//input[ @type='text']) [ 12 ]")).sendKeys(github);

		WebElement java = driver.findElement(By.xpath("//input[@id='Checkbox_1']"));
		WebElement aws = driver.findElement(By.xpath("//input[@id='Checkbox_2']"));
		WebElement scrum = driver.findElement(By.xpath("//input[@id='Checkbox_3']"));
		
		if(random.nextInt(2)==1)  java.click();
		if(random.nextInt(2)==1)  aws.click();
		if(random.nextInt(2)==1)  scrum.click();
		
		
		driver.findElement(By.xpath("//em[.='Apply']")).click();
		
	
		String actual=driver.findElement(By.xpath("//*[@id=\"richTxtMsgSpan\"]/label/div[6]")).getText();
		//String actual=driver.findElement(By.xpath("//div[.='IP address: 184.185.44.234']")).getText();    //handle xpath
		Assert.assertTrue(actual.contains("184.185.44.234"));

		
	
		Appid=driver.findElement(By.xpath("//*[@id=\"richTxtMsgSpan\"]/label/div[8]")).getText();
		System.out.println(Appid);
		
		
		
		driver.get("https://mail.google.com/mail/u/0/#inbox");
		driver.findElement(By.xpath("//*[@id=\"identifierId\"]")).sendKeys("kimyanursa@gmail.com");
		driver.findElement(By.xpath("//*[@id=\"identifierNext\"]/content/span")).click();
		driver.findElement(By.xpath("//*[@id=\"password\"]/div[1]/div/div[1]/input")).sendKeys(        "pasword");
		Thread.sleep(2000);
		driver.findElement(By.xpath("(//span[@class='RveJvd snByac'])[1]")).click();   //handle click next
		//driver.findElement(By.xpath("//*[@id=\"passwordNext\"]/content/span")).click();
		driver.findElement(By.xpath("//*[@id=\":3c\"]/span")).click();
		
		//idStr=driver.findElement(By.xpath("//*[@id=\":lh\"]/table/tbody/tr[2]/td[3]")).getText();
		idStr=driver.findElement(By.xpath("(//td[@valign='top'])[3]")).getText();
		//     (//td[@valign='top'])[3]
		System.out.println(idStr);
		Assert.assertTrue(Appid.contains(idStr));
	}
	
	
//	@Test
//	public void idEmail() {
//		driver.get("https://mail.google.com/mail/u/0/#inbox");
//	}


	
	
	
//	public void setSkillset(List<String> tech) {
//
//		for (String skill : tech) {
//			String technology = skill.substring(0, skill.length() - 2);
//			int rate = Integer.parseInt(skill.substring(skill.length() - 1));
//
//			String level = "";
//
//			switch (rate) {
//			case 1:
//				level = "Expert";
//				break;
//			case 2:
//				level = "Proficient";
//				break;
//			case 3:
//				level = "Beginner";
//				break;
//			default:
//				fail(rate + " is not a valid level");
//			}
//
//			String xpath = "//input[@rowvalue='" + technology + "' and @columnvalue='" + level + "']";
//			driver.findElement(By.xpath(xpath)).click();
//
//		}
//
//	}
	
	
	
	
	
public void setSkillset(List<String> tech) {
		
		for (String skill : tech) {
			String technology = skill.substring(0, skill.length()-2);
			int rate = Integer.parseInt(skill.substring(skill.length()-1));
			
			String level = "";
			
			switch(rate) {
				case 1:
					level = "Expert";
					break;
				case 2:
					level = "Proficient";
					break;
				case 3:
					level = "Beginner";
					break;
				default:
					fail(rate + " is not a valid level");
			}
			
			String xpath = "//input[@rowvalue='"+ technology +"' and @columnvalue='"+ level +"']";
			driver.findElement(By.xpath(xpath)).click();
			
		}
		
		
	}
	
	
	

	public void verifySalarycalculations(int annual) {
		String monthly = driver.findElement(By.xpath("//input[@name='Formula']")).getAttribute("value");
		String weekly = driver.findElement(By.xpath("//input[@name='Formula1']")).getAttribute("value");
		String hourly = driver.findElement(By.xpath("//input[@name='Formula2']")).getAttribute("value");

		System.out.println(monthly);
		System.out.println(weekly);
		System.out.println(hourly);

		DecimalFormat formatter = new DecimalFormat("#.##");

		assertEquals(Double.parseDouble(monthly), Double.parseDouble(formatter.format((double) annual / 12.0)));
		assertEquals(Double.parseDouble(weekly), Double.parseDouble(formatter.format((double) annual / 52.0)));
		assertEquals(Double.parseDouble(hourly), Double.parseDouble(formatter.format((double) annual / 52.0 / 40.0)));

	}

	public void setDateOfBirth(String bday) {
		String[] pieces = bday.split(" ");
		String birthDay = pieces[2] + "-" + pieces[1] + "-" + pieces[5];
		driver.findElement(By.xpath("//input[@id='Date-date']")).sendKeys(birthDay);
	}

	public void setGender(int n) {
		if (n == 1) {
			driver.findElement(By.xpath("//input[@value='Male']")).click();
		} else {
			driver.findElement(By.xpath("//input[@value='Female']")).click();
		}
	}

	@Test
	public void fullNameEmtyTest() {
		assertEquals(driver.getTitle(), "SDET Job Application");
		// driver.findElement(By.name("Name_First")).clear();
		driver.findElement(By.xpath("//input[@name='Name_First']")).clear();
		driver.findElement(By.name("Name_Last")).clear();

		driver.findElement(By.xpath("//em[.=' Next ']")).click();

		String nameError = driver.findElement(By.xpath("//p[@id='error-Name']")).getText();
		assertEquals(nameError, "Enter a value for this field.");
		// assertEquals(driver.findElement(By.xpath("//p[@id='error-Name']")).getText(),
		// "Enter a value for this field.");

	}

}
