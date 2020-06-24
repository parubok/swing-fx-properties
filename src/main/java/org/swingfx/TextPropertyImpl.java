package org.swingfx;

import swingfx.beans.property.SimpleStringProperty;
import swingfx.beans.property.StringProperty;
import swingfx.beans.value.ChangeListener;

import javax.swing.JLabel;
import java.beans.PropertyChangeListener;
import java.util.Objects;

final class TextPropertyImpl {
    private static final PropertyChangeListener SWING_PROP_LISTENER_TEXT = e -> {
        JLabel label = (JLabel) e.getSource();
        StringProperty p = (StringProperty) label.getClientProperty(ClientProps.PROP_TEXT);
        String newValue = (String) e.getNewValue();
        if (!Objects.equals(newValue, p.get())) {
            p.set(newValue);
        }
    };

    private static final ChangeListener<String> FX_PROP_LISTENER_TEXT = (observable, oldValue, newValue) -> {
        StringProperty p = (StringProperty) observable;
        JLabel label = (JLabel) p.getBean();
        if (!Objects.equals(newValue, label.getText())) {
            label.setText(newValue);
        }
    };

    static StringProperty getProperty(JLabel label) {
        Objects.requireNonNull(label, "label");
        StringProperty p = (StringProperty) label.getClientProperty(ClientProps.PROP_TEXT);
        if (p == null) {
            p = new SimpleStringProperty(label, "text", label.getText());
            label.putClientProperty(ClientProps.PROP_TEXT, p);
            label.addPropertyChangeListener("text", SWING_PROP_LISTENER_TEXT);
            p.addListener(FX_PROP_LISTENER_TEXT);
        }
        return p;
    }
}
