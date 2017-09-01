package be.mira.jongeren.mailinglist.selenium.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class PageObject {

    private WebDriver webDriver;

    public PageObject(WebDriver webDriver) {
        this.webDriver = webDriver;
        this.initElements();
    }

    public PageObject(WebDriver webDriver, String baseUrl, String page){
        this.webDriver = webDriver;
        this.webDriver.navigate().to(baseUrl + page);
        this.initElements();
    }

    protected void initElements(){
        PageFactory.initElements(this.webDriver, this);
    }

    protected WebDriver driver(){
        return this.webDriver;
    }

    public boolean hasClass(String className){
        try{
            this.webDriver.findElement(By.className(className));
            return true;
        }
        catch(NoSuchElementException e){
            return false;
        }
    }
}
