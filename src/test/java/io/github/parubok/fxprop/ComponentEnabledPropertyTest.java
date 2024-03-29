package io.github.parubok.fxprop;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import io.github.parubok.swingfx.beans.binding.Bindings;
import io.github.parubok.swingfx.beans.binding.BooleanBinding;
import io.github.parubok.swingfx.beans.binding.StringBinding;
import io.github.parubok.swingfx.beans.property.BooleanProperty;
import io.github.parubok.swingfx.beans.property.StringProperty;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

class ComponentEnabledPropertyTest {
    @Test
    void test_1() throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            JLabel label = new TestLabel();
            BooleanProperty enabledProp = SwingPropertySupport.enabledProperty(label);

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
    void test_2() throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            JLabel label1 = new TestLabel();
            BooleanProperty enabledProp1 = SwingPropertySupport.enabledProperty(label1);
            JLabel label2 = new TestLabel();
            BooleanProperty enabledProp2 = SwingPropertySupport.enabledProperty(label2);
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
    void enabled_str_binding_1() throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            JLabel label = new TestLabel();
            BooleanProperty enabledProp = SwingPropertySupport.enabledProperty(label);
            StringBinding str = Bindings.createStringBinding(() -> Boolean.toString(enabledProp.get()), enabledProp);
            Assertions.assertEquals("true", str.get());
            label.setEnabled(false);
            Assertions.assertEquals("false", str.get());
        });
    }

    @Test
    void enabled_text_binding_1() throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            JLabel label = new TestLabel();
            BooleanProperty enabledProp = SwingPropertySupport.enabledProperty(label);
            StringProperty textProp = SwingPropertySupport.textProperty(label);
            textProp.bind(Bindings.createStringBinding(() -> Boolean.toString(enabledProp.get()), enabledProp));
            Assertions.assertEquals("true", label.getText());
            label.setEnabled(false);
            Assertions.assertEquals("false", label.getText());
            label.setEnabled(true);
            Assertions.assertEquals("true", label.getText());
        });
    }

    @Test
    void selected_enabled_binding_1() throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            JCheckBox checkBox = new JCheckBox();
            Assertions.assertFalse(checkBox.isSelected());
            JLabel label = new TestLabel();
            Assertions.assertTrue(label.isEnabled());
            SwingPropertySupport.enabledProperty(label).bind(SwingPropertySupport.selectedProperty(checkBox));
            Assertions.assertFalse(label.isEnabled());
            checkBox.setSelected(true);
            Assertions.assertTrue(label.isEnabled());
        });
    }
}
