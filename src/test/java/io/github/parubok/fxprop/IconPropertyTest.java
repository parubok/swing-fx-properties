package io.github.parubok.fxprop;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import io.github.parubok.swingfx.beans.property.ObjectProperty;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import java.awt.Component;
import java.awt.Graphics;

class IconPropertyTest {

    private final Icon icon = new Icon() {
        @Override
        public void paintIcon(Component c, Graphics g, int x, int y) {

        }

        @Override
        public int getIconWidth() {
            return 10;
        }

        @Override
        public int getIconHeight() {
            return 10;
        }
    };

    @Test
    void forTextLabel() throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            JLabel label = new TestLabel();
            ObjectProperty<Icon> p = SwingPropertySupport.iconProperty(label);
            Assertions.assertNull(p.get());
            label.setIcon(icon);
            Assertions.assertEquals(icon, label.getIcon());
            Assertions.assertEquals(icon, p.get());

            p.set(null);
            Assertions.assertNull(label.getIcon());
            Assertions.assertNull(p.get());

            p.set(icon);
            Assertions.assertEquals(icon, label.getIcon());
            Assertions.assertEquals(icon, p.get());
        });
    }

    @Test
    void forJButton() throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            JButton button = new JButton();
            ObjectProperty<Icon> p = SwingPropertySupport.iconProperty(button);
            Assertions.assertNull(p.get());
            button.setIcon(icon);
            Assertions.assertEquals(icon, button.getIcon());
            Assertions.assertEquals(icon, p.get());

            p.set(null);
            Assertions.assertNull(button.getIcon());
            Assertions.assertNull(p.get());

            p.set(icon);
            Assertions.assertEquals(icon, button.getIcon());
            Assertions.assertEquals(icon, p.get());
        });
    }
}
