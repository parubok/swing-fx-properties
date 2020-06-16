package org.swingfx.beans;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import swingfx.beans.property.Property;
import swingfx.beans.property.adapter.JavaBeanObjectPropertyBuilder;

import javax.swing.JLabel;
import javax.swing.SwingUtilities;

public class AppTest {

    private static Property createBeanAdapter(Object bean, String propertyName) {
        try {
            return JavaBeanObjectPropertyBuilder.create()
                    .bean(bean)
                    .name(propertyName)
                    .build();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Test
    void enabledProperty_1() throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            JLabel label1 = new TestLabel();
            JLabel label2 = new TestLabel();

            Property enabledProperty1 = createBeanAdapter(label1, "enabled");
            Property enabledProperty2 = createBeanAdapter(label2, "enabled");
            enabledProperty2.bind(enabledProperty1);

            Assertions.assertTrue(label1.isEnabled());
            Assertions.assertTrue(label2.isEnabled());

            label1.setEnabled(false);
            Assertions.assertFalse(label1.isEnabled());
            Assertions.assertFalse(label2.isEnabled());

            label1.setEnabled(true);
            Assertions.assertTrue(label1.isEnabled());
            Assertions.assertTrue(label2.isEnabled());
        });
    }

    public static class TestLabel extends JLabel {
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
    }
}
