import org.junit.Test;

import org.junit.Assert;
import maven_robots.gui.LogWindow;
import maven_robots.localization.Languages;
import maven_robots.localization.LocalizationManager;
import maven_robots.log.LogWindowSource;

public class LocalizationTests {

    @Test
    public void ChangeTextOnChangeLocale()
    {
        LocalizationManager.setLocale(Languages.RU);
        LogWindowSource logSource = new LogWindowSource(5);
        LogWindow logWindow = new LogWindow(logSource);
        String expectedRuTile = LocalizationManager.getStringByName("log.title");
        Assert.assertEquals(expectedRuTile, logWindow.getTitle());
        LocalizationManager.setLocale(Languages.EN);
        logWindow.changeLanguage();
        String enTitle = logWindow.getTitle();
        Assert.assertNotEquals(expectedRuTile, enTitle);
        Assert.assertEquals(LocalizationManager.getStringByName("log.title"), enTitle);
    }
}
