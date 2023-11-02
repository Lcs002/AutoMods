package lvum.com.ui;

import javax.swing.*;

public class ReadOnlyToggleButtonModel extends JToggleButton.ToggleButtonModel
{
    public ReadOnlyToggleButtonModel()
    {
        super();
    }

    public ReadOnlyToggleButtonModel(boolean selected)
    {
        super();
        super.setSelected(selected);
    }

    @Override
    public void setSelected(boolean b)
    {
        // intentionally do nothing
    }
}
