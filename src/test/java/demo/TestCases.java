package demo;

import java.io.IOException;
import java.time.Duration;

import org.bouncycastle.jcajce.provider.symmetric.AES.Wrap;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class TestCases extends Wrapper{

    SoftAssert s= new SoftAssert();
    public TestCases()
    {
        super();
    }

    @AfterSuite
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

    @Test(priority = 1, description = "Go to the 'Films' tab and in the 'Top Selling' section, scroll to the extreme right. Apply a Soft Assert on whether the movie is marked 'A' for Mature or not. Apply a Soft assert on whether the movie is either 'Comedy' or 'Animation'.")
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

    @Test(priority = 3, description = "Go to the 'Music' tab and in the 1st section, scroll to the extreme right. Print the name of the playlist. Soft Assert on whether the number of tracks listed is less than or equal to 50.")
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

    @Test(priority = 4, description = "Go to 'News' tab and print the title and body of the 1st 3 'Latest News Posts' along with the sum of number of likes on all 3 of them. No likes given means 0.")
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

    @Test(dataProvider = "excelData" , priority=5, description ="scrolling till the sum of each videos views reach 10 Cr.")
    public void testCase05(String toBeSearched) throws InterruptedException
    {
        System.out.println("Start Test case: testCase05");
        Wrapper.goTo();
        Wrapper.countSumofViews(toBeSearched);
    }

    @DataProvider(name = "excelData")
    public String[][] excelDataProvider() throws IOException {
        return ReadFroExcel.getData();
    }

    

    


}
