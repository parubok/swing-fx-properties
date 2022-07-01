package io.github.parubok.fxprop;

import io.github.parubok.swingfx.beans.property.BooleanProperty;
import io.github.parubok.swingfx.beans.property.SimpleBooleanProperty;
import io.github.parubok.swingfx.beans.value.ChangeListener;

import javax.swing.AbstractButton;
import java.awt.event.ItemListener;
import java.util.Objects;

import static io.github.parubok.fxprop.ClientProps.PROP_SELECTED;

final class SelectedPropertyImpl {
    private static final ItemListener ITEM_LISTENER = e -> {
        AbstractButton absButton = (AbstractButton) e.getSource();
        BooleanProperty p = (BooleanProperty) absButton.getClientProperty(PROP_SELECTED);
        if (absButton.isSelected() != p.get()) {
            p.set(absButton.isSelected());
        }
    };

    private static final ChangeListener<Boolean> FX_PROP_LISTENER = (observable, oldValue, newValue) -> {
        BooleanProperty p = (BooleanProperty) observable;
        AbstractButton absButton = (AbstractButton) p.getBean();
        if (newValue.booleanValue() != absButton.isSelected()) {
            absButton.setSelected(newValue.booleanValue());
        }
    };

    static BooleanProperty getProperty(AbstractButton absButton) {
        Objects.requireNonNull(absButton, "absButton");
        BooleanProperty p = (BooleanProperty) absButton.getClientProperty(PROP_SELECTED);
        if (p == null) {
            p = new SimpleBooleanProperty(absButton, "selected", absButton.isSelected());
            absButton.putClientProperty(PROP_SELECTED, p);
            absButton.addItemListener(ITEM_LISTENER);
            p.addListener(FX_PROP_LISTENER);
        }
        return p;
    }
}
