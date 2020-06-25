package org.swingfx;

import swingfx.beans.property.BooleanProperty;
import swingfx.beans.property.SimpleBooleanProperty;
import swingfx.beans.value.ChangeListener;

import javax.swing.Action;
import java.beans.PropertyChangeListener;
import java.util.Objects;

final class ActionEnabledPropertyImpl {

    private static final String ACTION_PROPERTY = "swingfx-property-enabled";

    private static final PropertyChangeListener SWING_PROP_LISTENER = e -> {
        if ("enabled".equals(e.getPropertyName())) {
            Action action = (Action) e.getSource();
            BooleanProperty p = (BooleanProperty) action.getValue(ACTION_PROPERTY);
            Boolean newValue = (Boolean) e.getNewValue();
            if (newValue.booleanValue() != p.get()) {
                p.set(newValue.booleanValue());
            }
        }
    };

    private static final ChangeListener<Boolean> FX_PROP_LISTENER = (observable, oldValue, newValue) -> {
        BooleanProperty p = (BooleanProperty) observable;
        Action action = (Action) p.getBean();
        if (newValue.booleanValue() != action.isEnabled()) {
            action.setEnabled(newValue.booleanValue());
        }
    };

    static BooleanProperty getProperty(Action action) {
        Objects.requireNonNull(action, "action");
        BooleanProperty p = (BooleanProperty) action.getValue(ACTION_PROPERTY);
        if (p == null) {
            p = new SimpleBooleanProperty(action, "enabled", action.isEnabled());
            action.putValue(ACTION_PROPERTY, p);
            action.addPropertyChangeListener(SWING_PROP_LISTENER);
            p.addListener(FX_PROP_LISTENER);
        }
        return p;
    }
}
