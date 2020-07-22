package org.swingfx;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import swingfx.beans.property.BooleanProperty;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.SwingUtilities;
import java.awt.event.ActionEvent;

class ActionEnabledPropertyTest {
    @Test
    void test_1() throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            Action action = new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {

                }
            };
            BooleanProperty p = SwingPropertySupport.enabledProperty(action);
            Assertions.assertTrue(p.get());

            action.setEnabled(false);
            Assertions.assertFalse(p.get());

            p.set(true);
            Assertions.assertTrue(action.isEnabled());
        });
    }

    @Test
    void test_2() throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            Action action = new AbstractAction() {
                {
                    setEnabled(false);
                }

                @Override
                public void actionPerformed(ActionEvent e) {

                }
            };
            BooleanProperty p = SwingPropertySupport.enabledProperty(action);
            Assertions.assertFalse(p.get());
        });
    }
}
