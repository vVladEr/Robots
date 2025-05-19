import java.beans.PropertyVetoException;

import javax.swing.JTextArea;

import org.junit.Assert;
import org.junit.Test;


import maven_robots.gui.frames.internalFrames.LogWindow;
import maven_robots.data.parameters.Parameters;
import maven_robots.data.profiler.Profiler;
import maven_robots.log.LogWindowSource;

public class ListenersTests {

    @Test
    public void removeListenersWhenWindowClosed() {
        LogWindowSource logSource = new LogWindowSource(5);
        Parameters initialLogWindowParameters = new Parameters(10, 10, 300, 800);
        LogWindow logWindow = new LogWindow(new Profiler(),
             new JTextArea(), logSource, initialLogWindowParameters);
        
        Assert.assertEquals(1, logSource.listenersCount());
        try {
            logWindow.setClosed(true);
        } catch (PropertyVetoException ex) {
            System.err.println("Closing Exception");
        }

        Assert.assertEquals(0, logSource.listenersCount());
    }
}
