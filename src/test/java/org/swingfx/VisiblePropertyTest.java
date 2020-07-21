package org.swingfx;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import swingfx.beans.property.BooleanProperty;

import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import java.util.concurrent.atomic.AtomicReference;

class VisiblePropertyTest {
    @Test
    void visible_prop_1() throws Exception {
        final AtomicReference<JLabel> ref = new AtomicReference<>();
        SwingUtilities.invokeAndWait(() -> {
            JLabel label = new TestLabel();
            ref.set(label);
            BooleanProperty visibleProp = SwingPropertySupport.visibleProperty(label);
            Assertions.assertTrue(visibleProp.get());
            label.setVisible(false);
        });
        SwingUtilities.invokeAndWait(() -> {
            JLabel label = ref.get();
            Assertions.assertFalse(label.isVisible());
            BooleanProperty visibleProp = SwingPropertySupport.visibleProperty(label);
            Assertions.assertFalse(visibleProp.get());
            visibleProp.set(true);
            Assertions.assertTrue(label.isVisible());
            visibleProp.set(false);
            Assertions.assertFalse(label.isVisible());
        });
    }
}
