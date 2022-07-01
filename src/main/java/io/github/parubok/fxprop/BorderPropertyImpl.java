package io.github.parubok.fxprop;

import io.github.parubok.swingfx.beans.property.ObjectProperty;
import io.github.parubok.swingfx.beans.property.SimpleObjectProperty;
import io.github.parubok.swingfx.beans.value.ChangeListener;

import javax.swing.JComponent;
import javax.swing.border.Border;
import java.beans.PropertyChangeListener;
import java.util.Objects;

import static io.github.parubok.fxprop.ClientProps.PROP_BORDER;

final class BorderPropertyImpl {
    private static final PropertyChangeListener SWING_PROP_LISTENER = e -> {
        JComponent component = (JComponent) e.getSource();
        ObjectProperty<Border> p = (ObjectProperty) component.getClientProperty(PROP_BORDER);
        Border newValue = (Border) e.getNewValue();
        if (!Objects.equals(newValue, p.get())) {
            p.set(newValue);
        }
    };

    private static final ChangeListener<Border> FX_PROP_LISTENER = (observable, oldValue, newValue) -> {
        ObjectProperty<Border> p = (ObjectProperty) observable;
        JComponent component = (JComponent) p.getBean();
        if (!Objects.equals(newValue, component.getBorder())) {
            component.setBorder(newValue);
        }
    };

    static ObjectProperty<Border> getProperty(JComponent component) {
        Objects.requireNonNull(component, "component");
        ObjectProperty<Border> p = (ObjectProperty) component.getClientProperty(PROP_BORDER);
        if (p == null) {
            p = new SimpleObjectProperty<>(component, "border", component.getBorder());
            component.putClientProperty(PROP_BORDER, p);
            component.addPropertyChangeListener("border", SWING_PROP_LISTENER);
            p.addListener(FX_PROP_LISTENER);
        }
        return p;
    }
}
