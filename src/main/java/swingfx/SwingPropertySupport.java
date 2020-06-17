package swingfx;

import swingfx.beans.property.BooleanProperty;
import swingfx.beans.property.SimpleBooleanProperty;
import swingfx.beans.property.SimpleStringProperty;
import swingfx.beans.property.StringProperty;
import swingfx.beans.value.ChangeListener;

import javax.swing.AbstractButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import java.awt.event.ItemListener;
import java.beans.PropertyChangeListener;
import java.util.Objects;

public class SwingPropertySupport {

    private static final String PROP_ENABLED = "swingfx-property-enabled";
    private static final String PROP_TEXT = "swingfx-property-text";
    private static final String PROP_SELECTED = "swingfx-property-selected";

    // enabled:
    private static final PropertyChangeListener SWING_PROP_LISTENER_ENABLED = e -> {
        JComponent component = (JComponent) e.getSource();
        BooleanProperty p = (BooleanProperty) component.getClientProperty(PROP_ENABLED);
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

    // selected:
    private static final ItemListener ITEM_LISTENER_SELECTED = e -> {
        AbstractButton absButton = (AbstractButton) e.getSource();
        BooleanProperty p = (BooleanProperty) absButton.getClientProperty(PROP_SELECTED);
        if (absButton.isSelected() != p.get()) {
            p.set(absButton.isSelected());
        }
    };

    private static final ChangeListener<Boolean> FX_PROP_LISTENER_SELECTED = (observable, oldValue, newValue) -> {
        BooleanProperty p = (BooleanProperty) observable;
        AbstractButton absButton = (AbstractButton) p.getBean();
        if (newValue.booleanValue() != absButton.isSelected()) {
            absButton.setSelected(newValue.booleanValue());
        }
    };

    // text:
    private static final PropertyChangeListener SWING_PROP_LISTENER_TEXT = e -> {
        JLabel label = (JLabel) e.getSource();
        StringProperty p = (StringProperty) label.getClientProperty(PROP_TEXT);
        String newValue = (String) e.getNewValue();
        if (!Objects.equals(newValue, p.get())) {
            p.set(newValue);
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
        BooleanProperty p = (BooleanProperty) component.getClientProperty(PROP_ENABLED);
        if (p == null) {
            p = new SimpleBooleanProperty(component, "enabled", component.isEnabled());
            component.putClientProperty(PROP_ENABLED, p);
            component.addPropertyChangeListener("enabled", SWING_PROP_LISTENER_ENABLED);
            p.addListener(FX_PROP_LISTENER_ENABLED);
        }
        return p;
    }

    public static BooleanProperty selectedProperty(AbstractButton absButton) {
        Objects.requireNonNull(absButton, "absButton");
        BooleanProperty p = (BooleanProperty) absButton.getClientProperty(PROP_SELECTED);
        if (p == null) {
            p = new SimpleBooleanProperty(absButton, "selected", absButton.isSelected());
            absButton.putClientProperty(PROP_SELECTED, p);
            absButton.addItemListener(ITEM_LISTENER_SELECTED);
            p.addListener(FX_PROP_LISTENER_SELECTED);
        }
        return p;
    }

    public static StringProperty textProperty(JLabel label) {
        Objects.requireNonNull(label, "label");
        StringProperty p = (StringProperty) label.getClientProperty(PROP_TEXT);
        if (p == null) {
            p = new SimpleStringProperty(label, "text", label.getText());
            label.putClientProperty(PROP_TEXT, p);
            label.addPropertyChangeListener("text", SWING_PROP_LISTENER_TEXT);
            p.addListener(FX_PROP_LISTENER_TEXT);
        }
        return p;
    }
}
