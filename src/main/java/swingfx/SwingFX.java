package swingfx;

import swingfx.beans.property.BooleanProperty;
import swingfx.beans.property.SimpleBooleanProperty;
import swingfx.beans.property.SimpleStringProperty;
import swingfx.beans.property.StringProperty;
import swingfx.beans.value.ChangeListener;

import javax.swing.JComponent;
import javax.swing.JLabel;
import java.beans.PropertyChangeListener;
import java.util.Objects;

public class SwingFX {

    private static final String ENABLED_PROP = "swingfx-property-enabled";
    private static final String TEXT_PROP = "swingfx-property-text";

    private static final PropertyChangeListener SWING_PROP_LISTENER_ENABLED = e -> {
        JComponent component = (JComponent) e.getSource();
        BooleanProperty p = (BooleanProperty) component.getClientProperty(ENABLED_PROP);
        Boolean newEnabled = (Boolean) e.getNewValue();
        if (newEnabled.booleanValue() != p.get()) {
            p.set(newEnabled.booleanValue());
        }
    };

    private static final ChangeListener<Boolean> FX_PROP_LISTENER_ENABLED = (observable, oldValue, newValue) -> {
        BooleanProperty p = (BooleanProperty) observable;
        JComponent component = (JComponent) p.getBean();
        if (newValue.booleanValue() != component.isEnabled()) {
            component.setEnabled(newValue.booleanValue());
        }
    };

    private static final PropertyChangeListener SWING_PROP_LISTENER_TEXT = e -> {
        JLabel label = (JLabel) e.getSource();
        StringProperty p = (StringProperty) label.getClientProperty(TEXT_PROP);
        String newText = (String) e.getNewValue();
        if (!Objects.equals(newText, p.get())) {
            p.set(newText);
        }
    };

    private static final ChangeListener<String> FX_PROP_LISTENER_TEXT = (observable, oldValue, newValue) -> {
        StringProperty p = (StringProperty) observable;
        JLabel label = (JLabel) p.getBean();
        if (!Objects.equals(newValue, label.getText())) {
            label.setText(newValue);
        }
    };

    public static BooleanProperty enabledProperty(JComponent component) {
        Objects.requireNonNull(component, "component");
        BooleanProperty p = (BooleanProperty) component.getClientProperty(ENABLED_PROP);
        if (p == null) {
            p = new SimpleBooleanProperty(component, "enabled", component.isEnabled());
            component.putClientProperty(ENABLED_PROP, p);
            component.addPropertyChangeListener("enabled", SWING_PROP_LISTENER_ENABLED);
            p.addListener(FX_PROP_LISTENER_ENABLED);
        }
        return p;
    }

    public static StringProperty textProperty(JLabel label) {
        Objects.requireNonNull(label, "label");
        StringProperty p = (StringProperty) label.getClientProperty(TEXT_PROP);
        if (p == null) {
            p = new SimpleStringProperty(label, "text", label.getText());
            label.putClientProperty(TEXT_PROP, p);
            label.addPropertyChangeListener("text", SWING_PROP_LISTENER_TEXT);
            p.addListener(FX_PROP_LISTENER_TEXT);
        }
        return p;
    }
}
