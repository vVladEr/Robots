import java.beans.PropertyVetoException;

import org.junit.Assert;
import org.junit.Test;

import Helpers.TestWindows.TestLogWindow;
import maven_robots.log.LogWindowSource;

public class ListenersTests {

    @Test
    public void removeListenersWhenWindowClosed() {
        LogWindowSource logSource = new LogWindowSource(5);
        TestLogWindow logWindow = new TestLogWindow(logSource);
        
        Assert.assertEquals(1, logSource.listenersCount());
        try {
            logWindow.setClosed(true);
        } catch (PropertyVetoException ex) {
            System.err.println("Closing Exception");
        }

        Assert.assertEquals(0, logSource.listenersCount());
    }
}
