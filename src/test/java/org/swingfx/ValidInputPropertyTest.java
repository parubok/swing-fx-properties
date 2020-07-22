package org.swingfx;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import swingfx.beans.property.ReadOnlyBooleanProperty;

import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;

class ValidInputPropertyTest {
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
}
