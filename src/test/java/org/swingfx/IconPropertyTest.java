package org.swingfx;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import swingfx.beans.property.ObjectProperty;

import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import java.awt.Component;
import java.awt.Graphics;

class IconPropertyTest {
    @Test
    void test_1() throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            JLabel label = new TestLabel();
            ObjectProperty<Icon> p = SwingPropertySupport.iconProperty(label);
            Assertions.assertNull(p.get());
            Icon icon = new Icon() {
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
            label.setIcon(icon);
            Assertions.assertEquals(icon, label.getIcon());
            Assertions.assertEquals(icon, p.get());

            p.set(null);
            Assertions.assertNull(label.getIcon());
        });
    }
}
