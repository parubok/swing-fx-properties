package io.github.parubok.fxprop;

import io.github.parubok.swingfx.beans.property.ReadOnlyBooleanProperty;
import io.github.parubok.swingfx.beans.property.ReadOnlyBooleanWrapper;

import javax.swing.InputVerifier;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;
import javax.swing.text.JTextComponent;
import java.beans.PropertyChangeListener;
import java.util.Objects;

import static io.github.parubok.fxprop.ClientProps.PROP_VALID_INPUT;

final class ValidInputPropertyImpl {

    private static boolean isValidInput(JTextComponent textComponent) {
        InputVerifier inputVerifier = textComponent.getInputVerifier();
        return inputVerifier == null || inputVerifier.verify(textComponent);
    }

    static void updateProp(JTextComponent textComponent) {
        ValidInputProperty p = (ValidInputProperty) textComponent.getClientProperty(PROP_VALID_INPUT);
        boolean validInput = isValidInput(textComponent);
        if (validInput != p.get()) {
            p.set(validInput);
        }
    }

    private static class ValidInputProperty extends ReadOnlyBooleanWrapper implements DocumentListener {

        ValidInputProperty(JTextComponent textComponent) {
            super(textComponent, "validInput", isValidInput(textComponent));
        }

        void onDocChange() {
            updateProp((JTextComponent) getBean());
        }

        @Override
        public void insertUpdate(DocumentEvent e) {
            onDocChange();
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            onDocChange();
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            onDocChange();
        }
    }

    private static final PropertyChangeListener DOC_LISTENER = e -> {
        JTextComponent textComponent = (JTextComponent) e.getSource();
        ValidInputProperty p = (ValidInputProperty) textComponent.getClientProperty(PROP_VALID_INPUT);
        ((Document) e.getOldValue()).removeDocumentListener(p);
        ((Document) e.getNewValue()).addDocumentListener(p);
        updateProp(textComponent);
    };

    private static final PropertyChangeListener INPUT_VERIFIER_LISTENER = e -> {
        updateProp((JTextComponent) e.getSource());
    };

    static ReadOnlyBooleanProperty getProperty(JTextComponent textComponent) {
        Objects.requireNonNull(textComponent, "textComponent");
        ValidInputProperty p = (ValidInputProperty) textComponent.getClientProperty(PROP_VALID_INPUT);
        if (p == null) {
            p = new ValidInputProperty(textComponent);
            textComponent.putClientProperty(PROP_VALID_INPUT, p);
            // listeners:
            textComponent.getDocument().addDocumentListener(p);
            textComponent.addPropertyChangeListener("document", DOC_LISTENER);
            textComponent.addPropertyChangeListener("inputVerifier", INPUT_VERIFIER_LISTENER);
        }
        return p.getReadOnlyProperty();
    }
}
