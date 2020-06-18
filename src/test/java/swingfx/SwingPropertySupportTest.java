package swingfx;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import swingfx.beans.binding.Bindings;
import swingfx.beans.binding.BooleanBinding;
import swingfx.beans.binding.StringBinding;
import swingfx.beans.property.BooleanProperty;
import swingfx.beans.property.ReadOnlyIntegerProperty;
import swingfx.beans.property.StringProperty;

import javax.swing.DefaultListSelectionModel;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.util.concurrent.atomic.AtomicReference;

public class SwingPropertySupportTest {

    @Test
    void enabled_prop_1() throws Exception {
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
    void enabled_prop_2() throws Exception {
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

    @Test
    void selectedRowCountProperty_1() throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            TableModel model = new DefaultTableModel(10, 3);
            JTable table = new JTable();
            table.setModel(model);
            table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            ReadOnlyIntegerProperty selRowCountProp = SwingPropertySupport.selectedRowCountProperty(table);
            Assertions.assertEquals(0, selRowCountProp.get());
            table.getSelectionModel().setSelectionInterval(0, 0);
            Assertions.assertEquals(1, selRowCountProp.get());

            // test that the property continues to work after the selection model is replaced:
            table.setSelectionModel(new DefaultListSelectionModel());
            Assertions.assertEquals(0, selRowCountProp.get());
            table.getSelectionModel().setSelectionInterval(0, 0);
            Assertions.assertEquals(1, selRowCountProp.get());

            table.getSelectionModel().setSelectionInterval(0, 2);
            Assertions.assertEquals(3, selRowCountProp.get());
            table.clearSelection();
            Assertions.assertEquals(0, selRowCountProp.get());
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
