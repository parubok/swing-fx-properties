package swingfx;

import swingfx.beans.property.ObjectProperty;
import swingfx.beans.property.SimpleObjectProperty;
import swingfx.beans.value.ChangeListener;

import javax.swing.JComboBox;
import java.awt.event.ItemListener;
import java.util.Objects;

import static swingfx.ClientProps.PROP_SELECTED_ITEM;

final class SelectedItemPropertyImpl {
    private static final ItemListener ITEM_LISTENER = e -> {
        Object item = e.getItem();
        JComboBox<?> comboBox = (JComboBox) e.getSource();
        ObjectProperty p = (ObjectProperty) comboBox.getClientProperty(PROP_SELECTED_ITEM);
        if (!Objects.equals(item, p.get())) {
            p.set(item);
        }
    };

    private static final ChangeListener FX_PROP_LISTENER = (observable, oldValue, newValue) -> {
        ObjectProperty<?> p = (ObjectProperty) observable;
        JComboBox<?> comboBox = (JComboBox) p.getBean();
        if (!Objects.equals(newValue, comboBox.getSelectedItem())) {
            comboBox.setSelectedItem(newValue);
        }
    };

    static <E> ObjectProperty<E> getProperty(JComboBox<E> comboBox) {
        Objects.requireNonNull(comboBox, "comboBox");
        ObjectProperty<E> p = (ObjectProperty) comboBox.getClientProperty(PROP_SELECTED_ITEM);
        if (p == null) {
            p = new SimpleObjectProperty(comboBox, "selectedItem", comboBox.getSelectedItem());
            comboBox.putClientProperty(PROP_SELECTED_ITEM, p);
            comboBox.addItemListener(ITEM_LISTENER);
            p.addListener(FX_PROP_LISTENER);
        }
        return p;
    }
}
