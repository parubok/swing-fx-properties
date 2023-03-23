package io.github.parubok.fxprop;

import io.github.parubok.swingfx.beans.property.ObjectProperty;
import io.github.parubok.swingfx.beans.property.SimpleObjectProperty;
import io.github.parubok.swingfx.beans.value.ChangeListener;

import javax.swing.JComponent;
import java.awt.Font;
import java.beans.PropertyChangeListener;
import java.util.Objects;

import static io.github.parubok.fxprop.ClientProps.PROP_FONT;

final class FontPropertyImpl {
    private static final PropertyChangeListener SWING_PROP_LISTENER = e -> {
        JComponent component = (JComponent) e.getSource();
        ObjectProperty<Font> p = (ObjectProperty) component.getClientProperty(PROP_FONT);
        Font newValue = (Font) e.getNewValue();
        if (!Objects.equals(newValue, p.get())) {
            p.set(newValue);
        }
    };

    private static final ChangeListener<Font> FX_PROP_LISTENER = (observable, oldValue, newValue) -> {
        ObjectProperty<Font> p = (ObjectProperty) observable;
        JComponent component = (JComponent) p.getBean();
        if (!Objects.equals(newValue, component.getFont())) {
            component.setFont(newValue);
        }
    };

    static ObjectProperty<Font> getProperty(JComponent component) {
        Objects.requireNonNull(component, "component");
        ObjectProperty<Font> p = (ObjectProperty) component.getClientProperty(PROP_FONT);
        if (p == null) {
            p = new SimpleObjectProperty<>(component, "font", component.getFont());
            component.putClientProperty(PROP_FONT, p);
            component.addPropertyChangeListener("font", SWING_PROP_LISTENER);
            p.addListener(FX_PROP_LISTENER);
        }
        return p;
    }
}
