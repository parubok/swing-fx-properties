package swingfx;

import swingfx.beans.property.BooleanProperty;
import swingfx.beans.property.SimpleBooleanProperty;
import swingfx.beans.value.ChangeListener;

import javax.swing.JComponent;
import java.beans.PropertyChangeListener;
import java.util.Objects;

public class SwingFX {

    private static final String ENABLED_PROP = "swingfx-property-enabled";

    private static final PropertyChangeListener ENABLED_SWING_PROP_LISTENER = e -> {
        JComponent component = (JComponent) e.getSource();
        BooleanProperty p = (BooleanProperty) component.getClientProperty(ENABLED_PROP);
        Boolean newEnabled = (Boolean) e.getNewValue();
        if (newEnabled.booleanValue() != p.get()) {
            p.set(newEnabled.booleanValue());
        }
    };

    private static final ChangeListener<Boolean> ENABLED_FX_PROP_LISTENER = (observable, oldValue, newValue) -> {
        BooleanProperty p = (BooleanProperty) observable;
        JComponent component = (JComponent) p.getBean();
        if (newValue.booleanValue() != component.isEnabled()) {
            component.setEnabled(newValue.booleanValue());
        }
    };

    public static BooleanProperty enabledProperty(JComponent component) {
        Objects.requireNonNull(component, "component");
        BooleanProperty p = (BooleanProperty) component.getClientProperty(ENABLED_PROP);
        if (p == null) {
            p = new SimpleBooleanProperty(component, "enabled", component.isEnabled());
            component.putClientProperty(ENABLED_PROP, p);
            component.addPropertyChangeListener("enabled", ENABLED_SWING_PROP_LISTENER);
            p.addListener(ENABLED_FX_PROP_LISTENER);
        }
        return p;
    }
}
