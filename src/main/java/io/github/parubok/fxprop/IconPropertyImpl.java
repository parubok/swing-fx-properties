package io.github.parubok.fxprop;

import io.github.parubok.swingfx.beans.property.ObjectProperty;
import io.github.parubok.swingfx.beans.property.SimpleObjectProperty;
import io.github.parubok.swingfx.beans.value.ChangeListener;

import javax.swing.Icon;
import javax.swing.JComponent;
import java.beans.PropertyChangeListener;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static io.github.parubok.fxprop.ClientProps.PROP_ICON;
import static io.github.parubok.fxprop.ClientProps.PROP_ICON_GETTER;
import static io.github.parubok.fxprop.ClientProps.PROP_ICON_SETTER;

final class IconPropertyImpl {
    private static final PropertyChangeListener SWING_PROP_LISTENER = e -> {
        JComponent com = (JComponent) e.getSource();
        ObjectProperty<Icon> p = (ObjectProperty) com.getClientProperty(PROP_ICON);
        Icon newValue = (Icon) e.getNewValue();
        if (!Objects.equals(newValue, p.get())) {
            p.set(newValue);
        }
    };

    private static final ChangeListener<Icon> FX_PROP_LISTENER = (observable, oldValue, newValue) -> {
        ObjectProperty<Icon> p = (ObjectProperty) observable;
        JComponent com = (JComponent) p.getBean();
        Supplier<Icon> iconGetter = (Supplier) com.getClientProperty(PROP_ICON_GETTER);
        Consumer<Icon> iconSetter = (Consumer) com.getClientProperty(PROP_ICON_SETTER);
        if (!Objects.equals(newValue, iconGetter.get())) {
            iconSetter.accept(newValue);
        }
    };

    static ObjectProperty<Icon> getProperty(JComponent com, Supplier<Icon> iconGetter, Consumer<Icon> iconSetter) {
        Objects.requireNonNull(com);
        ObjectProperty<Icon> p = (ObjectProperty) com.getClientProperty(PROP_ICON);
        if (p == null) {
            p = new SimpleObjectProperty<>(com, "icon", iconGetter.get());
            com.putClientProperty(PROP_ICON, p);
            com.putClientProperty(PROP_ICON_GETTER, iconGetter);
            com.putClientProperty(PROP_ICON_SETTER, iconSetter);
            com.addPropertyChangeListener("icon", SWING_PROP_LISTENER);
            p.addListener(FX_PROP_LISTENER);
        }
        return p;
    }
}
