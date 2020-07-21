package org.swingfx;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import swingfx.beans.property.ObjectProperty;
import swingfx.beans.value.ChangeListener;
import swingfx.beans.value.ObservableValue;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class BorderPropertyTest {
    @Test
    void test_1() throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            JPanel panel = new JPanel();
            ObjectProperty<Border> p = SwingPropertySupport.borderProperty(panel);
            Assertions.assertNull(p.get());
            p.set(BorderFactory.createEmptyBorder());
            Assertions.assertEquals(BorderFactory.createEmptyBorder(), panel.getBorder());

            List<Border> values = new ArrayList<>();
            p.addListener(new ChangeListener<Border>() {
                @Override
                public void changed(ObservableValue<? extends Border> observable, Border oldValue, Border newValue) {
                    values.add(oldValue);
                    values.add(newValue);
                }
            });
            Border lineBorder = BorderFactory.createLineBorder(Color.RED, 10);
            panel.setBorder(lineBorder);
            Assertions.assertEquals(lineBorder, p.get());
            Assertions.assertIterableEquals(Arrays.asList(BorderFactory.createEmptyBorder(), lineBorder), values);

            panel.setBorder(null);
            Assertions.assertNull(p.get());
            Assertions.assertNull(panel.getBorder());
            Assertions.assertIterableEquals(Arrays.asList(BorderFactory.createEmptyBorder(), lineBorder,
                    lineBorder, null), values);
        });
    }
}
