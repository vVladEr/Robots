import java.beans.PropertyVetoException;

import org.junit.Assert;
import org.junit.Test;


import maven_robots.gui.LogWindow;
import maven_robots.log.LogWindowSource;

public class ListenersTests {

    @Test
    public void RemoveListenersAfterWindowClosing(){
        LogWindowSource logSource = new LogWindowSource(5);
        LogWindow logWindow = new LogWindow(logSource);
        Assert.assertEquals(1, logSource.listenersCount());
        try {
        logWindow.setClosed(true);
        } catch (PropertyVetoException ex) {
            System.err.println("Closing Exception");
        }
        Assert.assertEquals(0, logSource.listenersCount());
    }
}
