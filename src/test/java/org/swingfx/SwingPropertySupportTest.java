package org.swingfx;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import swingfx.beans.property.ObjectProperty;
import swingfx.beans.property.ReadOnlyBooleanProperty;

import javax.swing.Icon;
import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;
import java.awt.Component;
import java.awt.Graphics;

public class SwingPropertySupportTest {
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
