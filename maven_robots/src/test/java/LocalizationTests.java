import org.junit.Assert;
import org.junit.Test;

import maven_robots.gui.frames.internalFrames.LogWindow;
import maven_robots.data.parameters.Parameters;
import maven_robots.localization.Language;
import maven_robots.localization.LocalizationManager;
import maven_robots.log.LogWindowSource;

public class LocalizationTests {

    @Test
    public void changeTextOnChangeLocale() {
        LocalizationManager.setLanguage(Language.RU);
        LogWindowSource logSource = new LogWindowSource(5);
        Parameters initialLogWindowParameters = new Parameters(10, 10, 300, 800);
        LogWindow logWindow = new LogWindow(logSource, initialLogWindowParameters);
        String expectedRuTile = LocalizationManager.getStringByName("log.title");
        Assert.assertEquals(expectedRuTile, logWindow.getTitle());

        LocalizationManager.setLanguage(Language.EN);
        logWindow.changeLanguage();
        String enTitle = logWindow.getTitle();

        Assert.assertNotEquals(expectedRuTile, enTitle);
        Assert.assertEquals(LocalizationManager.getStringByName("log.title"), enTitle);
    }
}
