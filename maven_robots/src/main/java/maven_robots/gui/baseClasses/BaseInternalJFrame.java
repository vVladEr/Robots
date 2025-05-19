package maven_robots.gui.baseClasses;

import javax.swing.JInternalFrame;

import maven_robots.gui.mainFrame.ClosingListeners;
import maven_robots.localization.ILocalizable;
import maven_robots.localization.LocalizationManager;

public abstract class BaseInternalJFrame extends JInternalFrame implements ILocalizable {

    protected final String titleBandleName;

    public BaseInternalJFrame(final String titleBandleName, final boolean resizable,
            final boolean closable,
            final boolean maximizable, final boolean iconable) {
            super(LocalizationManager.getStringByName(titleBandleName), resizable, closable, maximizable, iconable);
            this.titleBandleName = titleBandleName;
            setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
            addInternalFrameListener(ClosingListeners.getFrameClosingListener());
    }
    
    @Override
    public void changeLanguage()
    {
        this.setTitle(LocalizationManager.getStringByName(titleBandleName));
        this.invalidate();
    };
}
