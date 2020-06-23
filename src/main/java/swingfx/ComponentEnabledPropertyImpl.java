package swingfx;

import swingfx.beans.property.BooleanProperty;
import swingfx.beans.property.SimpleBooleanProperty;
import swingfx.beans.value.ChangeListener;

import javax.swing.JComponent;
import java.beans.PropertyChangeListener;
import java.util.Objects;

final class ComponentEnabledPropertyImpl {
    private static final PropertyChangeListener SWING_PROP_LISTENER_ENABLED = e -> {
        JComponent component = (JComponent) e.getSource();
        BooleanProperty p = (BooleanProperty) component.getClientProperty(ClientProps.PROP_ENABLED);
        Boolean newValue = (Boolean) e.getNewValue();
        if (newValue.booleanValue() != p.get()) {
            p.set(newValue.booleanValue());
        }
    };

    private static final ChangeListener<Boolean> FX_PROP_LISTENER_ENABLED = (observable, oldValue, newValue) -> {
        BooleanProperty p = (BooleanProperty) observable;
        JComponent component = (JComponent) p.getBean();
        if (newValue.booleanValue() != component.isEnabled()) {
            component.setEnabled(newValue.booleanValue());
        }
    };

    static BooleanProperty getProperty(JComponent component) {
        Objects.requireNonNull(component, "component");
        BooleanProperty p = (BooleanProperty) component.getClientProperty(ClientProps.PROP_ENABLED);
        if (p == null) {
            p = new SimpleBooleanProperty(component, "enabled", component.isEnabled());
            component.putClientProperty(ClientProps.PROP_ENABLED, p);
            component.addPropertyChangeListener("enabled", SWING_PROP_LISTENER_ENABLED);
            p.addListener(FX_PROP_LISTENER_ENABLED);
        }
        return p;
    }
}
