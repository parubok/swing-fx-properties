package io.github.parubok.fxprop;

import io.github.parubok.swingfx.beans.property.ObjectProperty;
import io.github.parubok.swingfx.beans.property.SimpleObjectProperty;
import io.github.parubok.swingfx.beans.value.ChangeListener;

import javax.swing.JComponent;
import java.awt.Color;
import java.beans.PropertyChangeListener;
import java.util.Objects;

import static io.github.parubok.fxprop.ClientProps.PROP_BACKGROUND;

final class BackgroundPropertyImpl {
    private static final PropertyChangeListener SWING_PROP_LISTENER = e -> {
        JComponent component = (JComponent) e.getSource();
        ObjectProperty<Color> p = (ObjectProperty<Color>) component.getClientProperty(PROP_BACKGROUND);
        Color newValue = (Color) e.getNewValue();
        if (!Objects.equals(newValue, p.get())) {
            p.set(newValue);
        }
    };

    private static final ChangeListener<Color> FX_PROP_LISTENER = (observable, oldValue, newValue) -> {
        ObjectProperty<Color> p = (ObjectProperty) observable;
        JComponent component = (JComponent) p.getBean();
        if (!Objects.equals(newValue, component.getBackground())) {
            component.setBackground(newValue);
        }
    };

    static ObjectProperty<Color> getProperty(JComponent component) {
        Objects.requireNonNull(component, "component");
        ObjectProperty<Color> p = (ObjectProperty) component.getClientProperty(ClientProps.PROP_BACKGROUND);
        if (p == null) {
            p = new SimpleObjectProperty<>(component, "background", component.getBackground());
            component.putClientProperty(PROP_BACKGROUND, p);
            component.addPropertyChangeListener("background", SWING_PROP_LISTENER);
            p.addListener(FX_PROP_LISTENER);
        }
        return p;
    }
}
