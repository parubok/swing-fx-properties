package io.github.parubok.fxprop;

import io.github.parubok.swingfx.beans.property.SimpleStringProperty;
import io.github.parubok.swingfx.beans.property.StringProperty;
import io.github.parubok.swingfx.beans.value.ChangeListener;

import javax.swing.JLabel;
import java.beans.PropertyChangeListener;
import java.util.Objects;

import static io.github.parubok.fxprop.ClientProps.PROP_TEXT;

final class LabelTextPropertyImpl {
    private static final PropertyChangeListener SWING_PROP_LISTENER = e -> {
        JLabel label = (JLabel) e.getSource();
        StringProperty p = (StringProperty) label.getClientProperty(PROP_TEXT);
        String newValue = (String) e.getNewValue();
        if (!Objects.equals(newValue, p.get())) {
            p.set(newValue);
        }
    };

    private static final ChangeListener<String> FX_PROP_LISTENER = (observable, oldValue, newValue) -> {
        StringProperty p = (StringProperty) observable;
        JLabel label = (JLabel) p.getBean();
        if (!Objects.equals(newValue, label.getText())) {
            label.setText(newValue);
        }
    };

    static StringProperty getProperty(JLabel label) {
        Objects.requireNonNull(label, "label");
        StringProperty p = (StringProperty) label.getClientProperty(PROP_TEXT);
        if (p == null) {
            p = new SimpleStringProperty(label, "text", label.getText());
            label.putClientProperty(PROP_TEXT, p);
            label.addPropertyChangeListener("text", SWING_PROP_LISTENER);
            p.addListener(FX_PROP_LISTENER);
        }
        return p;
    }
}
