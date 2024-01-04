package io.github.parubok.fxprop;

import io.github.parubok.swingfx.beans.property.IntegerProperty;
import io.github.parubok.swingfx.beans.property.SimpleIntegerProperty;
import io.github.parubok.swingfx.beans.value.ChangeListener;

import javax.swing.JList;
import javax.swing.event.ListSelectionListener;
import java.util.Objects;

import static io.github.parubok.fxprop.ClientProps.PROP_SELECTED_INDEX;

/**
 * Selected index of {@link JList}.
 */
final class ListSelectedIndexPropertyImpl {
    private static final ListSelectionListener SELECTION_LISTENER = e -> {
        JList<?> list = (JList) e.getSource();
        int selectedIndex = list.getSelectedIndex();
        IntegerProperty p = (IntegerProperty) list.getClientProperty(PROP_SELECTED_INDEX);
        if (p.get() != selectedIndex) {
            p.set(selectedIndex);
        }
    };

    private static final ChangeListener<Number> FX_PROP_LISTENER = (observable, oldValue, newValue) -> {
        IntegerProperty p = (IntegerProperty) observable;
        JList<?> list = (JList) p.getBean();
        if (newValue.intValue() != list.getSelectedIndex()) {
            list.setSelectedIndex(newValue.intValue());
        }
    };

    static IntegerProperty getProperty(JList<?> list) {
        Objects.requireNonNull(list, "list");
        SimpleIntegerProperty p = (SimpleIntegerProperty) list.getClientProperty(PROP_SELECTED_INDEX);
        if (p == null) {
            p = new SimpleIntegerProperty(list, "selectedIndex", list.getSelectedIndex());
            list.putClientProperty(PROP_SELECTED_INDEX, p);
            list.addListSelectionListener(SELECTION_LISTENER);
            p.addListener(FX_PROP_LISTENER);
        }
        return p;
    }
}
