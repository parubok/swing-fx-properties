package org.swingfx.beans;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import swingfx.SwingFX;
import swingfx.beans.binding.Bindings;
import swingfx.beans.binding.BooleanBinding;
import swingfx.beans.binding.StringBinding;
import swingfx.beans.property.BooleanProperty;
import swingfx.beans.property.Property;
import swingfx.beans.property.adapter.JavaBeanObjectPropertyBuilder;

import javax.swing.JLabel;
import javax.swing.SwingUtilities;

public class JLabel_enabled {

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
    void swingfx_prop_1() throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            JLabel label = new TestLabel();
            BooleanProperty enabledProp = SwingFX.enabledProperty(label);

            Assertions.assertTrue(enabledProp.get());
            label.setEnabled(false);
            Assertions.assertFalse(enabledProp.get());
            label.setEnabled(true);
            Assertions.assertTrue(enabledProp.get());

            enabledProp.set(false);
            Assertions.assertFalse(label.isEnabled());
            enabledProp.set(true);
            Assertions.assertTrue(label.isEnabled());
        });
    }

    @Test
    void swingfx_prop_2() throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            JLabel label1 = new TestLabel();
            BooleanProperty enabledProp1 = SwingFX.enabledProperty(label1);
            JLabel label2 = new TestLabel();
            BooleanProperty enabledProp2 = SwingFX.enabledProperty(label2);
            BooleanBinding binding = Bindings.and(enabledProp1, enabledProp2);
            Assertions.assertTrue(binding.get());
            label2.setEnabled(false);
            Assertions.assertFalse(binding.get());
            label2.setEnabled(true);
            Assertions.assertTrue(binding.get());
            label1.setEnabled(false);
            Assertions.assertFalse(binding.get());
            label1.setEnabled(true);
            Assertions.assertTrue(binding.get());

            label2.setEnabled(false);
            label1.setEnabled(false);
            Assertions.assertFalse(binding.get());
            label1.setEnabled(true);
            Assertions.assertFalse(binding.get());
            label2.setEnabled(true);
            Assertions.assertTrue(binding.get());
        });
    }

    @Test
    void swingfx_prop_3() throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            JLabel label = new TestLabel();
            BooleanProperty enabledProp1 = SwingFX.enabledProperty(label);
            StringBinding str = Bindings.createStringBinding(() -> Boolean.toString(enabledProp1.get()), enabledProp1);
            Assertions.assertEquals("true", str.get());
            label.setEnabled(false);
            Assertions.assertEquals("false", str.get());
        });
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
