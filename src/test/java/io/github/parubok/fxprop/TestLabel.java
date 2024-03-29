package io.github.parubok.fxprop;

import org.junit.jupiter.api.Assertions;
import io.github.parubok.swingfx.beans.property.StringProperty;

import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

public class TestLabel extends JLabel {
    @Override
    public boolean isEnabled() {
        Assertions.assertTrue(SwingUtilities.isEventDispatchThread());
        return super.isEnabled();
    }

    @Override
    public void setEnabled(boolean enabled) {
        Assertions.assertTrue(SwingUtilities.isEventDispatchThread());
        super.setEnabled(enabled);
    }

    @Override
    public String getText() {
        Assertions.assertTrue(SwingUtilities.isEventDispatchThread());
        return super.getText();
    }

    @Override
    public void setText(String text) {
        Assertions.assertTrue(SwingUtilities.isEventDispatchThread());
        super.setText(text);
    }

    public StringProperty textProperty() {
        return SwingPropertySupport.textProperty(this);
    }

    @Override
    public Icon getIcon() {
        Assertions.assertTrue(SwingUtilities.isEventDispatchThread());
        return super.getIcon();
    }

    @Override
    public void setIcon(Icon icon) {
        Assertions.assertTrue(SwingUtilities.isEventDispatchThread());
        super.setIcon(icon);
    }
}
