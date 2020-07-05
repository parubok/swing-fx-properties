package org.swingfx;

import com.sun.swingfx.collections.ObservableListWrapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import swingfx.beans.binding.Bindings;
import swingfx.beans.binding.BooleanBinding;
import swingfx.beans.binding.StringBinding;
import swingfx.beans.property.BooleanProperty;
import swingfx.beans.property.ListProperty;
import swingfx.beans.property.ObjectProperty;
import swingfx.beans.property.ReadOnlyBooleanProperty;
import swingfx.beans.property.ReadOnlyIntegerProperty;
import swingfx.beans.property.StringProperty;
import swingfx.beans.value.ChangeListener;
import swingfx.beans.value.ObservableValue;

import javax.swing.BorderFactory;
import javax.swing.DefaultListSelectionModel;
import javax.swing.Icon;
import javax.swing.InputVerifier;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
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

            ListSelectionModel oldSelectionModel = table.getSelectionModel();

            // test that the property continues to work after the selection model is replaced:
            table.setSelectionModel(new DefaultListSelectionModel());
            Assertions.assertEquals(0, selRowCountProp.get());
            table.getSelectionModel().setSelectionInterval(0, 0);
            Assertions.assertEquals(1, selRowCountProp.get());

            table.getSelectionModel().setSelectionInterval(0, 2);
            Assertions.assertEquals(3, selRowCountProp.get());
            table.clearSelection();
            Assertions.assertEquals(0, selRowCountProp.get());

            oldSelectionModel.setSelectionInterval(0, 1); // should not affect the property
            Assertions.assertEquals(0, selRowCountProp.get());
        });
    }

    @Test
    void selectedRowCountProperty_2() throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            TableModel model = new DefaultTableModel(10, 3);
            JTable table = new JTable();
            table.setModel(model);
            table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            ReadOnlyIntegerProperty selRowCountProp = SwingPropertySupport.selectedRowCountProperty(table);
            List<Number> values = new ArrayList<>();
            selRowCountProp.addListener(new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                    Assertions.assertTrue(SwingUtilities.isEventDispatchThread());
                    values.add(newValue);
                }
            });
            table.getSelectionModel().setSelectionInterval(0, 1);
            Assertions.assertEquals(2, selRowCountProp.get());
            Assertions.assertIterableEquals(Collections.singletonList(2), values);
            table.clearSelection();
            Assertions.assertIterableEquals(Arrays.asList(2, 0), values);
            table.clearSelection(); // should not fire event
            Assertions.assertIterableEquals(Arrays.asList(2, 0), values);
        });
    }

    @Test
    void selectedRowsProperty_1() throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            TableModel model = new DefaultTableModel(10, 3);
            JTable table1 = new JTable();
            table1.setModel(model);
            table1.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            ListProperty<Integer> selRowsProp1 = SwingPropertySupport.selectedRowsProperty(table1);
            JTable table2 = new JTable();
            table2.setModel(model);
            table2.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            ListProperty<Integer> selRowsProp2 = SwingPropertySupport.selectedRowsProperty(table2);
            selRowsProp1.bindBidirectional(selRowsProp2);

            table1.getSelectionModel().setSelectionInterval(0, 0);
            Assertions.assertArrayEquals(new int[]{0}, table2.getSelectedRows());

            table1.getSelectionModel().addSelectionInterval(1, 1);
            Assertions.assertArrayEquals(new int[]{0, 1}, table2.getSelectedRows());

            table1.clearSelection();
            Assertions.assertArrayEquals(new int[0], table2.getSelectedRows());

            table2.getSelectionModel().addSelectionInterval(1, 1);
            Assertions.assertArrayEquals(new int[]{1}, table1.getSelectedRows());
        });
    }

    @Test
    void selectedRowsProperty_2() throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            TableModel model = new DefaultTableModel(10, 3);
            JTable table = new JTable();
            table.setModel(model);
            table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            ListProperty<Integer> p = SwingPropertySupport.selectedRowsProperty(table);
            p.get().add(3);
            Assertions.assertArrayEquals(new int[] {3}, table.getSelectedRows());
            p.set(new ObservableListWrapper<>(new ArrayList<>(Arrays.asList(4, 6))));
            Assertions.assertArrayEquals(new int[] {4,6}, table.getSelectedRows());

            p.get().clear();
            Assertions.assertArrayEquals(new int[0], table.getSelectedRows());

            table.getSelectionModel().setSelectionInterval(2, 5);
            Assertions.assertEquals(Arrays.asList(2, 3, 4, 5), p.get());
        });
    }

    @Test
    void selectedRowsProperty_3() throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            TableModel model = new DefaultTableModel(20, 3);
            JTable table = new JTable();
            table.setModel(model);
            table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            ListProperty<Integer> p = SwingPropertySupport.selectedRowsProperty(table);
            Assertions.assertTrue(p.get().isEmpty());
            Assertions.assertEquals(0, table.getSelectedRowCount());
            p.get().setAll(9, 7, 8); // unsorted
            Assertions.assertEquals(Arrays.asList(9, 7, 8), p.get());
            Assertions.assertArrayEquals(new int[]{7, 8, 9}, table.getSelectedRows());

            table.getSelectionModel().addSelectionInterval(11, 12);
            Assertions.assertEquals(Arrays.asList(7, 8, 9, 11, 12), p.get());

            table.getSelectionModel().addSelectionInterval(17, 15);
            Assertions.assertEquals(Arrays.asList(7, 8, 9, 11, 12, 15, 16, 17), p.get());

            table.clearSelection();
            Assertions.assertEquals(Collections.emptyList(), p.get());
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

        @Override
        public Icon getIcon() {
            Assertions.assertTrue(SwingUtilities.isEventDispatchThread());
            return super.getIcon();
        }

        @Override
        public void setIcon(Icon icon) {
            Assertions.assertTrue(SwingUtilities.isEventDispatchThread());
            super.setIcon(icon);
        }
    }

    @Test
    void modelRowCountProperty_1() throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            DefaultTableModel model = new DefaultTableModel(3, 2);
            JTable table = new JTable(model);
            ReadOnlyIntegerProperty rowCountProperty = SwingPropertySupport.modelRowCountProperty(table);

            List<Number> values = new ArrayList<>();
            rowCountProperty.addListener(new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                    Assertions.assertTrue(SwingUtilities.isEventDispatchThread());
                    Assertions.assertEquals(rowCountProperty, observable);
                    values.add(oldValue);
                    values.add(newValue);
                }
            });

            Assertions.assertEquals(3, rowCountProperty.get());
            model.removeRow(0);
            Assertions.assertEquals(2, rowCountProperty.get());
            Assertions.assertIterableEquals(Arrays.asList(3, 2), values);

            table.setModel(new DefaultTableModel(10, 2));
            Assertions.assertEquals(10, rowCountProperty.get());
            Assertions.assertIterableEquals(Arrays.asList(3, 2, 2, 10), values);

            model.removeRow(0); // old model - no effect
            Assertions.assertIterableEquals(Arrays.asList(3, 2, 2, 10), values);

            ((DefaultTableModel) table.getModel()).removeRow(0);
            Assertions.assertIterableEquals(Arrays.asList(3, 2, 2, 10, 10, 9), values);
        });
    }

    @Test
    void borderProperty_1() throws Exception {
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

    @Test
    void tree_selectionCount_1() throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            DefaultMutableTreeNode root = new DefaultMutableTreeNode();
            DefaultMutableTreeNode child_1 = new DefaultMutableTreeNode();
            DefaultMutableTreeNode child_2 = new DefaultMutableTreeNode();
            root.add(child_1);
            root.add(child_2);
            TreeModel model = new DefaultTreeModel(root);
            JTree tree = new JTree(model);
            ReadOnlyIntegerProperty selCountProp = SwingPropertySupport.selectionCountProperty(tree);

            List<Number> values = new ArrayList<>();
            selCountProp.addListener(new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                    Assertions.assertTrue(SwingUtilities.isEventDispatchThread());
                    Assertions.assertEquals(selCountProp, observable);
                    values.add(oldValue);
                    values.add(newValue);
                }
            });

            Assertions.assertEquals(0, selCountProp.get());
            tree.setSelectionPath(new TreePath(new Object[] { root, child_1 }));
            tree.addSelectionPath(new TreePath(new Object[] { root, child_2 }));
            Assertions.assertEquals(2, selCountProp.get());
            Assertions.assertIterableEquals(Arrays.asList(0, 1, 1, 2), values);
            tree.clearSelection();
            Assertions.assertEquals(0, selCountProp.get());
            Assertions.assertIterableEquals(Arrays.asList(0, 1, 1, 2, 2, 0), values);
        });
    }

    @Test
    void validInput_1() throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            JTextField textField = new JTextField();
            InputVerifier inputVerifier = new InputVerifier() {
                @Override
                public boolean verify(JComponent input) {
                    return ((JTextField) input).getText().length() > 3;
                }
            };
            ReadOnlyBooleanProperty p = SwingPropertySupport.validInputProperty(textField);
            Assertions.assertTrue(p.get());
            textField.setInputVerifier(inputVerifier);
            Assertions.assertFalse(p.get());
            textField.setText("ABCD");
            Assertions.assertTrue(p.get());
            textField.setText("ABC");
            Assertions.assertFalse(p.get());

            Document newDoc = new PlainDocument();
            try {
                newDoc.insertString(0, "1234", null);
            } catch (BadLocationException e) {
                Assertions.fail(e);
            }
            textField.setDocument(newDoc);
            Assertions.assertTrue(p.get());

            textField.setInputVerifier(new InputVerifier() {
                @Override
                public boolean verify(JComponent input) {
                    return false;
                }
            });
            Assertions.assertFalse(p.get());
        });
    }

    @Test
    void icon_1() throws Exception {
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
