package org.swingfx;

import org.junit.jupiter.api.Assertions;

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
