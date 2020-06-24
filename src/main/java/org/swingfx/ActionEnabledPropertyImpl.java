package org.swingfx;

import swingfx.beans.property.BooleanProperty;
import swingfx.beans.property.SimpleBooleanProperty;
import swingfx.beans.value.ChangeListener;

import javax.swing.Action;
import java.beans.PropertyChangeListener;
import java.util.Objects;

import static org.swingfx.ClientProps.PROP_ENABLED;

final class ActionEnabledPropertyImpl {
    private static final PropertyChangeListener SWING_PROP_LISTENER_ENABLED = e -> {
        if ("enabled".equals(e.getPropertyName())) {
            Action action = (Action) e.getSource();
            BooleanProperty p = (BooleanProperty) action.getValue(PROP_ENABLED);
            Boolean newValue = (Boolean) e.getNewValue();
            if (newValue.booleanValue() != p.get()) {
                p.set(newValue.booleanValue());
            }
        }
    };

    private static final ChangeListener<Boolean> FX_PROP_LISTENER_ENABLED = (observable, oldValue, newValue) -> {
        BooleanProperty p = (BooleanProperty) observable;
        Action action = (Action) p.getBean();
        if (newValue.booleanValue() != action.isEnabled()) {
            action.setEnabled(newValue.booleanValue());
        }
    };

    static BooleanProperty getProperty(Action action) {
        Objects.requireNonNull(action, "action");
        BooleanProperty p = (BooleanProperty) action.getValue(PROP_ENABLED);
        if (p == null) {
            p = new SimpleBooleanProperty(action, "enabled", action.isEnabled());
            action.putValue(PROP_ENABLED, p);
            action.addPropertyChangeListener(SWING_PROP_LISTENER_ENABLED);
            p.addListener(FX_PROP_LISTENER_ENABLED);
        }
        return p;
    }
}
