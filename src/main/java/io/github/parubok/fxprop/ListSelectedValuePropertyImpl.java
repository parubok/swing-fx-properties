package io.github.parubok.fxprop;

import io.github.parubok.swingfx.beans.property.ObjectProperty;
import io.github.parubok.swingfx.beans.property.SimpleObjectProperty;
import io.github.parubok.swingfx.beans.value.ChangeListener;

import javax.swing.JList;
import javax.swing.event.ListSelectionListener;
import java.util.Objects;

import static io.github.parubok.fxprop.ClientProps.PROP_SELECTED_VALUE;

/**
 * Selected value of {@link JList}.
 */
final class ListSelectedValuePropertyImpl {
    private static final ListSelectionListener SELECTION_LISTENER = e -> {
        JList list = (JList) e.getSource();
        Object selectedValue = list.getSelectedValue();
        ObjectProperty p = (ObjectProperty) list.getClientProperty(PROP_SELECTED_VALUE);
        if (!Objects.equals(selectedValue, p.get())) {
            p.set(selectedValue);
        }
    };

    private static final ChangeListener FX_PROP_LISTENER = (observable, oldValue, newValue) -> {
        ObjectProperty p = (ObjectProperty) observable;
        JList list = (JList) p.getBean();
        if (!Objects.equals(newValue, list.getSelectedValue())) {
            list.setSelectedValue(newValue, false);
        }
    };

    static <E> ObjectProperty<E> getProperty(JList<E> list) {
        Objects.requireNonNull(list, "list");
        ObjectProperty<E> p = (ObjectProperty) list.getClientProperty(PROP_SELECTED_VALUE);
        if (p == null) {
            p = new SimpleObjectProperty<>(list, "selectedValue", list.getSelectedValue());
            list.putClientProperty(PROP_SELECTED_VALUE, p);
            list.addListSelectionListener(SELECTION_LISTENER);
            p.addListener(FX_PROP_LISTENER);
        }
        return p;
    }
}
