package io.github.parubok.fxprop;

import io.github.parubok.swingfx.beans.property.BooleanProperty;
import io.github.parubok.swingfx.beans.property.SimpleBooleanProperty;
import io.github.parubok.swingfx.beans.value.ChangeListener;

import javax.swing.JComponent;
import java.beans.PropertyChangeListener;
import java.util.Objects;

import static io.github.parubok.fxprop.ClientProps.PROP_ENABLED;

final class ComponentEnabledPropertyImpl {
    private static final PropertyChangeListener SWING_PROP_LISTENER = e -> {
        JComponent component = (JComponent) e.getSource();
        BooleanProperty p = (BooleanProperty) component.getClientProperty(PROP_ENABLED);
        Boolean newValue = (Boolean) e.getNewValue();
        if (newValue.booleanValue() != p.get()) {
            p.set(newValue.booleanValue());
        }
    };

    private static final ChangeListener<Boolean> FX_PROP_LISTENER = (observable, oldValue, newValue) -> {
        BooleanProperty p = (BooleanProperty) observable;
        JComponent component = (JComponent) p.getBean();
        if (newValue.booleanValue() != component.isEnabled()) {
            component.setEnabled(newValue.booleanValue());
        }
    };

    static BooleanProperty getProperty(JComponent component) {
        Objects.requireNonNull(component, "component");
        BooleanProperty p = (BooleanProperty) component.getClientProperty(PROP_ENABLED);
        if (p == null) {
            p = new SimpleBooleanProperty(component, "enabled", component.isEnabled());
            component.putClientProperty(PROP_ENABLED, p);
            component.addPropertyChangeListener("enabled", SWING_PROP_LISTENER);
            p.addListener(FX_PROP_LISTENER);
        }
        return p;
    }
}
