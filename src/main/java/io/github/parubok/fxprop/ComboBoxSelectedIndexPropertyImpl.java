package io.github.parubok.fxprop;

import io.github.parubok.swingfx.beans.property.IntegerProperty;
import io.github.parubok.swingfx.beans.property.SimpleIntegerProperty;
import io.github.parubok.swingfx.beans.value.ChangeListener;

import javax.swing.JComboBox;
import java.awt.event.ItemListener;
import java.util.Objects;

import static io.github.parubok.fxprop.ClientProps.PROP_SELECTED_INDEX;

final class ComboBoxSelectedIndexPropertyImpl {
    private static final ItemListener ITEM_LISTENER = e -> {
        JComboBox<?> comboBox = (JComboBox<?>) e.getSource();
        IntegerProperty p = (IntegerProperty) comboBox.getClientProperty(PROP_SELECTED_INDEX);
        int index = comboBox.getSelectedIndex();
        if (index != p.get()) {
            p.set(index);
        }
    };

    private static final ChangeListener<Number> FX_PROP_LISTENER = (observable, oldValue, newValue) -> {
        IntegerProperty p = (IntegerProperty) observable;
        JComboBox<?> comboBox = (JComboBox<?>) p.getBean();
        int index = comboBox.getSelectedIndex();
        if (newValue.intValue() != index) {
            comboBox.setSelectedIndex(newValue.intValue());
        }
    };

    static IntegerProperty getProperty(JComboBox<?> comboBox) {
        Objects.requireNonNull(comboBox, "comboBox");
        IntegerProperty p = (IntegerProperty) comboBox.getClientProperty(PROP_SELECTED_INDEX);
        if (p == null) {
            p = new SimpleIntegerProperty(comboBox, "selectedIndex", comboBox.getSelectedIndex());
            comboBox.putClientProperty(PROP_SELECTED_INDEX, p);
            comboBox.addItemListener(ITEM_LISTENER);
            p.addListener(FX_PROP_LISTENER);
        }
        return p;
    }
}
