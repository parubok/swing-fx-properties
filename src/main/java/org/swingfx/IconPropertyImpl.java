package org.swingfx;

import swingfx.beans.property.ObjectProperty;
import swingfx.beans.property.SimpleObjectProperty;
import swingfx.beans.value.ChangeListener;

import javax.swing.Icon;
import javax.swing.JLabel;
import java.beans.PropertyChangeListener;
import java.util.Objects;

import static org.swingfx.ClientProps.PROP_ICON;

final class IconPropertyImpl {
    private static final PropertyChangeListener SWING_PROP_LISTENER = e -> {
        JLabel label = (JLabel) e.getSource();
        ObjectProperty<Icon> p = (ObjectProperty) label.getClientProperty(PROP_ICON);
        Icon newValue = (Icon) e.getNewValue();
        if (!Objects.equals(newValue, p.get())) {
            p.set(newValue);
        }
    };

    private static final ChangeListener<Icon> FX_PROP_LISTENER = (observable, oldValue, newValue) -> {
        ObjectProperty<Icon> p = (ObjectProperty) observable;
        JLabel label = (JLabel) p.getBean();
        if (!Objects.equals(newValue, label.getIcon())) {
            label.setIcon(newValue);
        }
    };

    static ObjectProperty<Icon> getProperty(JLabel label) {
        Objects.requireNonNull(label, "label");
        ObjectProperty<Icon> p = (ObjectProperty) label.getClientProperty(PROP_ICON);
        if (p == null) {
            p = new SimpleObjectProperty<>(label, "icon", label.getIcon());
            label.putClientProperty(PROP_ICON, p);
            label.addPropertyChangeListener("icon", SWING_PROP_LISTENER);
            p.addListener(FX_PROP_LISTENER);
        }
        return p;
    }
}
