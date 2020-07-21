package org.swingfx;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import swingfx.beans.property.ObjectProperty;
import swingfx.beans.property.ReadOnlyBooleanProperty;
import swingfx.beans.property.ReadOnlyIntegerProperty;
import swingfx.beans.value.ChangeListener;
import swingfx.beans.value.ObservableValue;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
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
import java.util.List;

public class SwingPropertySupportTest {

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
