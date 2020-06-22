package swingfx;

import swingfx.beans.property.ObjectProperty;
import swingfx.beans.property.SimpleObjectProperty;
import swingfx.beans.value.ChangeListener;

import javax.swing.JComponent;
import java.awt.Color;
import java.beans.PropertyChangeListener;
import java.util.Objects;

import static swingfx.ClientProps.PROP_FOREGROUND;

final class ForegroundPropertyImpl {
    private static final PropertyChangeListener SWING_PROP_LISTENER = e -> {
        JComponent component = (JComponent) e.getSource();
        ObjectProperty<Color> p = (ObjectProperty<Color>) component.getClientProperty(PROP_FOREGROUND);
        Color newValue = (Color) e.getNewValue();
        if (!Objects.equals(newValue, p.get())) {
            p.set(newValue);
        }
    };

    private static final ChangeListener<Color> FX_PROP_LISTENER = (observable, oldValue, newValue) -> {
        ObjectProperty<Color> p = (ObjectProperty) observable;
        JComponent component = (JComponent) p.getBean();
        if (!Objects.equals(newValue, component.getForeground())) {
            component.setForeground(newValue);
        }
    };

    static ObjectProperty<Color> foregroundProperty(JComponent component) {
        Objects.requireNonNull(component, "component");
        ObjectProperty<Color> p = (ObjectProperty) component.getClientProperty(ClientProps.PROP_FOREGROUND);
        if (p == null) {
            p = new SimpleObjectProperty<>(component, "foreground", component.getForeground());
            component.putClientProperty(PROP_FOREGROUND, p);
            component.addPropertyChangeListener("foreground", SWING_PROP_LISTENER);
            p.addListener(FX_PROP_LISTENER);
        }
        return p;
    }
}
