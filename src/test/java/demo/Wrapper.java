package demo;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class Wrapper {

    static WebDriver driver;
    static String URL="https://www.youtube.com/";
    
    public Wrapper()
    {
        System.out.println("Constructor: TestCases");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        Wrapper.driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        PageFactory.initElements(driver, this); 
    }

    //Navigate to Youtube website
    public static void goTo()
    {
        if(!driver.getCurrentUrl().equals(URL))
        {
            driver.get(URL);
            String ExpectedURL=driver.getCurrentUrl();
            Assert.assertEquals(URL, ExpectedURL);
        }
    }

    //Scroll to the required element
    public static void scroll(WebElement e) throws InterruptedException
    {
        JavascriptExecutor js = (JavascriptExecutor)driver;
        js.executeScript("arguments[0].scrollIntoView(true);", e);
    }

    //Click on the required element
    public static void click(WebElement e) throws InterruptedException
    {
        e.click();
        WebDriverWait w= new WebDriverWait(driver, Duration.ofSeconds(10));
        w.until(ExpectedConditions.urlToBe("https://about.youtube/"));

    }

    //get Text of required element
    public static void getText(WebElement e)
    {
        String text=e.getText();
        System.out.println("Text present is: "+text);
    }

    //Go to movies tab
    public static void gotoMoviesTabs(WebElement e)
    {
        e.click();
        WebDriverWait w= new WebDriverWait(driver, Duration.ofSeconds(10));
        w.until(ExpectedConditions.urlContains("https://www.youtube.com/feed/"));
    }

    //Go to Music tab
    public static void gotoMusicTabs(WebElement e)
    {
        e.click();
        WebDriverWait w= new WebDriverWait(driver, Duration.ofSeconds(10));
        w.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'Biggest Hits')]")));
    }

    //Go to News tab
    public static void gotoNewsTabs(WebElement e)
    {
        e.click();
        WebDriverWait w= new WebDriverWait(driver, Duration.ofSeconds(10));
        w.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Latest news posts']")));
    }


    //Scroll to the most right movie in the Top selling list
    public static void scrllTotheRight(WebElement e) throws InterruptedException
    {

        while(e.isDisplayed())
        {
            e.click();
            Thread.sleep(2000);
        }
       
        // for(int i=0;i<3;i++)
        // {
        //     e.click();
        //     Thread.sleep(2000);
        // }
    }

    //check whether the movie is marked 'A' for Mature or not
    public static String asseerttCategory(WebElement e)
    {
        WebElement category=e.findElement(By.xpath("./../../following-sibling::ytd-badge-supported-renderer/div[2]"));
        String categoryText=category.getText();
        return categoryText;
        //System.out.println(categoryText);
        // SoftAssert s= new SoftAssert();
        // s.assertEquals("A",categoryText, "Movie category is not A and is: "+categoryText+ " which is not expected");
        // s.assertAll();
    }

    //check whether the movie is either 'Comedy' or 'Animation'.
    public static String asseerttZone(WebElement e)
    {
        WebElement zone=e.findElement(By.xpath("./../following-sibling::span"));
        String zoneText=zone.getText();
        String replacedString=zoneText.replaceAll("[^a-zA-Z]", "");
        return replacedString;
        //System.out.println(replacedString);
        // SoftAssert s= new SoftAssert();
        // s.assertTrue(replacedString.equals("Comedy") || replacedString.equals("Animation") , "Movie is not in expected Zone");
        // s.assertAll();
    }

    //Soft Assert on whether the number of tracks listed is less than or equal to 50.
    public static int asseerttPlaylist(WebElement e)
    {
        WebElement NoOfTracksEle=e.findElement(By.xpath("./../p"));
        String NoOfTracks=NoOfTracksEle.getText();
        String replacedString=NoOfTracks.replaceAll("[^0-9]", "");
        int NoOfTracks1=Integer.valueOf(replacedString);
        //System.out.println(NoOfTracks1);
        return NoOfTracks1;
        // SoftAssert s= new SoftAssert();
        // s.assertTrue(NoOfTracks1<=50, "Number of tracks are not less than or equal to 50 and are: "+NoOfTracks1);
        // s.assertAll();
    }

    public static void latestNews() throws InterruptedException
    {
        int totalSum=0;
        List<WebElement> newsPostElements= driver.findElements(By.xpath("//*[@id='content']/ytd-post-renderer"));
        for(int i=0;i<3;i++)
        {
            WebElement titleElement=newsPostElements.get(i).findElement(By.xpath("./div/div/div[2]"));
            String title=titleElement.getText();
            WebElement bodyElement= newsPostElements.get(i).findElement(By.xpath("./div/div[2]/div/yt-formatted-string"));
            String body=bodyElement.getText();

            System.out.println("Title of the news is :"+title);
            System.out.println("Body of the news is: "+body);

            WebElement sumElement= newsPostElements.get(i).findElement(By.xpath("./div/div[3]/ytd-comment-action-buttons-renderer/div/span[2]"));
            String Stringsum=sumElement.getText();
            String likesNumber = Stringsum.replaceAll("^[0-9]", "");
           System.out.println(likesNumber);
            if(likesNumber!=" ")
            {
                totalSum=totalSum+Integer.valueOf(Stringsum);
            } 
        }
        System.out.println("sum of number of likes are: "+totalSum);
    }

    public static void countSumofViews(String value)
    {
        Double totalCount=(double) 0;
        long viewCount= 0;
       WebElement SearchEle= driver.findElement(By.xpath("//input[@name='search_query']"));
       SearchEle.sendKeys(value);
       SearchEle.sendKeys(Keys.ENTER);

       WebDriverWait w= new WebDriverWait(driver, Duration.ofSeconds(10));
       w.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='metadata-line']/span[1]")));
        
        List<WebElement> rawCountList=driver.findElements(By.xpath("//div[@id='metadata-line']/span[1]"));
        
            for(WebElement e: rawCountList)
            {
                String rawCount=e.getText();
                //String [] str= rawCount.split(" ");
                String views=rawCount.replaceAll(" views", "");
                views=views.replaceAll(",", "");
                views.trim();
                if(views.endsWith("K"))
                {
                    views = views.replace("K", "");
                    viewCount= (long) (Double.parseDouble(views)*1000);
                }
                else if(views.endsWith("M"))
                {
                    views= views.replace("M", "");
                    viewCount= (long) (Double.parseDouble(views.replace("M", ""))*100000);
                }
                totalCount= totalCount+viewCount;
                
                if(totalCount>10000000)
                {
                    System.out.println("Views has reached 1 CR.");
                    System.out.println(totalCount); 
                    break;
                }
            }
               
    }




    
    
}
