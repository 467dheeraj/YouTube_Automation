package demo;

import java.io.IOException;
import java.time.Duration;

import org.bouncycastle.jcajce.provider.symmetric.AES.Wrap;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class TestCases extends Wrapper{

    SoftAssert s= new SoftAssert();
    public TestCases()
    {
        super();
    }

    @AfterTest
    public void endTest()
    {
        System.out.println("End Test: TestCases");
        driver.close();
        driver.quit();

    }

    @Test(priority = 0, description = "Go to YouTube.com and Assert you are on the correct URL. Click on 'About' at the bottom of the sidebar, and print the message on the screen.")
    public void testCase01() throws InterruptedException{
        System.out.println("Start Test case: testCase01");
        Wrapper.goTo();
        WebElement aboutElement=driver.findElement(By.xpath("//a[text()='About']"));
        try 
        {
            Wrapper.scroll(aboutElement);
        } 
        catch (InterruptedException e) 
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Wrapper.click(aboutElement);
        WebElement textele=driver.findElement(By.xpath("//p[contains(@class,'lb-font-display-3')]"));
        Wrapper.getText(textele);
        System.out.println("end Test case: testCase01");
    }

    @Test
    public void testCase02() throws InterruptedException
    {
        System.out.println("Start Test case: testCase02");
        Wrapper.goTo();
        WebElement moviesTab=driver.findElement(By.xpath("//*[text()='Movies']"));
        Wrapper.gotoMoviesTabs(moviesTab);
        WebDriverWait w= new WebDriverWait(driver, Duration.ofSeconds(10));
        w.until((ExpectedConditions.elementToBeClickable(By.xpath("//yt-button-shape/button[@aria-label='Next']/yt-touch-feedback-shape/div"))));
        WebElement scrollToRightEle=driver.findElement(By.xpath("//yt-button-shape/button[@aria-label='Next']/yt-touch-feedback-shape/div"));
        Wrapper.scrllTotheRight(scrollToRightEle);

        WebElement movieNameEle=driver.findElement(By.xpath("//span[@title='The Wolf of Wall Street']"));
        String movieName=movieNameEle.getText();
        if(movieName.contains("The Wolf of Wall Street"))
        {
            String categoryText=Wrapper.asseerttCategory(movieNameEle);
            //  SoftAssert s= new SoftAssert();
            s.assertEquals("A",categoryText, "Movie category is not A and is: "+categoryText+ " which is not expected");
            s.assertAll();
            String movieZone=Wrapper.asseerttZone(movieNameEle);
            s.assertTrue(movieZone.equals("Comedy") || movieZone.equals("Animation") , "Movie is not in expected Zone");
            s.assertAll();
        }
        System.out.println("end Test case: testCase02");
    }

    @Test
    public void testCase03() throws InterruptedException
    {
        System.out.println("Start Test case: testCase03");
        Wrapper.goTo();
        WebElement musicTab=driver.findElement(By.xpath("//*[text()='Music']"));
        Wrapper.gotoMusicTabs(musicTab);
        
        WebElement biggestHits=driver.findElement(By.xpath("//span[contains(text(),'Biggest Hits')]"));
        Wrapper.scroll(biggestHits);

        WebDriverWait w= new WebDriverWait(driver, Duration.ofSeconds(10));
        w.until((ExpectedConditions.elementToBeClickable(By.xpath("(//yt-button-shape/button[@aria-label='Next']/yt-touch-feedback-shape/div)[2]"))));
        WebElement scrollToRightEle=driver.findElement(By.xpath("(//yt-button-shape/button[@aria-label='Next']/yt-touch-feedback-shape/div)[1]"));
        Wrapper.scrllTotheRight(scrollToRightEle);

        WebElement playlistEle=driver.findElement(By.xpath("//h3[contains(text(),'Bollywood Dance Hitlist')]"));
        String playlistName=playlistEle.getText();
        if(playlistName.contains("Bollywood Dance"))
        {
            System.out.println("Playlist Name is: "+playlistName);
            int NumberOfTracks=Wrapper.asseerttPlaylist(playlistEle);
            //SoftAssert s= new SoftAssert();
            s.assertTrue(NumberOfTracks<=50, "Number of tracks are not less than or equal to 50 and are: "+NumberOfTracks);
            s.assertAll();
        }
        System.out.println("end Test case: testCase03");
    }

    @Test
    public void testCase04() throws InterruptedException
    {
        System.out.println("Start Test case: testCase04");
        Wrapper.goTo();
        WebElement newsTab=driver.findElement(By.xpath("//*[text()='News']"));
        Wrapper.gotoNewsTabs(newsTab);
        
        WebElement latestNews=driver.findElement(By.xpath("//span[text()='Latest news posts']"));
        Wrapper.scroll(latestNews);

        Wrapper.latestNews();

        System.out.println("end Test case: testCase04");
    }


}
