package swingfx;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import swingfx.beans.binding.Bindings;
import swingfx.beans.binding.BooleanBinding;
import swingfx.beans.binding.StringBinding;
import swingfx.beans.property.BooleanProperty;
import swingfx.beans.property.StringProperty;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

public class SwingFXTest {

    @Test
    void enabled_prop_1() throws Exception {
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
    void enabled_prop_2() throws Exception {
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
    void enabled_str_binding_1() throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            JLabel label = new TestLabel();
            BooleanProperty enabledProp = SwingFX.enabledProperty(label);
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
            BooleanProperty enabledProp = SwingFX.enabledProperty(label);
            StringProperty textProp = SwingFX.textProperty(label);
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
            SwingFX.enabledProperty(label).bind(SwingFX.selectedProperty(checkBox));
            Assertions.assertFalse(label.isEnabled());
            checkBox.setSelected(true);
            Assertions.assertTrue(label.isEnabled());
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
    }
}
