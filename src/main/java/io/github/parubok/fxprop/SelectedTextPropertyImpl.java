package io.github.parubok.fxprop;

import io.github.parubok.swingfx.beans.property.ReadOnlyStringProperty;
import io.github.parubok.swingfx.beans.property.ReadOnlyStringWrapper;

import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.text.JTextComponent;
import java.util.Objects;

import static io.github.parubok.fxprop.ClientProps.PROP_SELECTED_TEXT;

final class SelectedTextPropertyImpl {

    private static String getSelectedText(JTextComponent textComponent) {
        String selText = textComponent.getSelectedText();
        return selText != null ? selText : "";
    }

    static void updateProp(JTextComponent textComponent) {
        SelectedTextProperty p = (SelectedTextProperty) textComponent.getClientProperty(PROP_SELECTED_TEXT);
        String selText = getSelectedText(textComponent);
        if (!Objects.equals(selText, p.get())) {
            p.set(selText);
        }
    }

    private static class SelectedTextProperty extends ReadOnlyStringWrapper implements CaretListener {

        SelectedTextProperty(JTextComponent textComponent) {
            super(textComponent, "selectedText", getSelectedText(textComponent));
        }

        @Override
        public void caretUpdate(CaretEvent e) {
            updateProp((JTextComponent) e.getSource());
        }
    }

    static ReadOnlyStringProperty getProperty(JTextComponent textComponent) {
        Objects.requireNonNull(textComponent, "textComponent");
        SelectedTextProperty p = (SelectedTextProperty) textComponent.getClientProperty(PROP_SELECTED_TEXT);
        if (p == null) {
            p = new SelectedTextProperty(textComponent);
            textComponent.putClientProperty(PROP_SELECTED_TEXT, p);
            // listeners:
            textComponent.addCaretListener(p);
        }
        return p.getReadOnlyProperty();
    }
}
