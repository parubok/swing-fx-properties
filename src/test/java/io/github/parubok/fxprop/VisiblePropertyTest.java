package io.github.parubok.fxprop;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import io.github.parubok.swingfx.beans.property.BooleanProperty;

import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import java.util.concurrent.atomic.AtomicReference;

class VisiblePropertyTest {
    @Test
    void test_1() throws Exception {
        final AtomicReference<JLabel> ref = new AtomicReference<>();
        SwingUtilities.invokeAndWait(() -> {
            JLabel label = new TestLabel();
            ref.set(label);
            Assertions.assertTrue(label.isVisible());
            BooleanProperty visibleProp = SwingPropertySupport.visibleProperty(label);
            Assertions.assertTrue(visibleProp.get());
            label.setVisible(false);
        });
        // has to use invokeAndWait twice since visibility change is signaled via Toolkit.getEventQueue().postEvent()
        SwingUtilities.invokeAndWait(() -> {
            JLabel label = ref.get();
            Assertions.assertFalse(label.isVisible());
            BooleanProperty p = SwingPropertySupport.visibleProperty(label);
            Assertions.assertFalse(p.get());
            p.set(true);
            Assertions.assertTrue(label.isVisible());
            p.set(false);
            Assertions.assertFalse(label.isVisible());
        });
    }
}
