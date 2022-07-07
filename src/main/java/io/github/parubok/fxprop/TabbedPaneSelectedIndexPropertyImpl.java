package io.github.parubok.fxprop;

import io.github.parubok.swingfx.beans.property.IntegerProperty;
import io.github.parubok.swingfx.beans.property.SimpleIntegerProperty;
import io.github.parubok.swingfx.beans.value.ChangeListener;

import javax.swing.JTabbedPane;
import java.util.Objects;

import static io.github.parubok.fxprop.ClientProps.PROP_SELECTED_INDEX;

final class TabbedPaneSelectedIndexPropertyImpl {
    private static final javax.swing.event.ChangeListener SWING_PROP_LISTENER = e -> {
        JTabbedPane tabbedPane = (JTabbedPane) e.getSource();
        SimpleIntegerProperty p = (SimpleIntegerProperty) tabbedPane.getClientProperty(PROP_SELECTED_INDEX);
        int newValue = tabbedPane.getSelectedIndex();
        if (!Objects.equals(newValue, p.get())) {
            p.set(newValue);
        }
    };

    private static final ChangeListener<? super Number> FX_PROP_LISTENER = (observable, oldValue, newValue) -> {
        SimpleIntegerProperty p = (SimpleIntegerProperty) observable;
        JTabbedPane tabbedPane = (JTabbedPane) p.getBean();
        if (newValue.intValue() != tabbedPane.getSelectedIndex()) {
            tabbedPane.setSelectedIndex(newValue.intValue());
        }
    };

    static IntegerProperty getProperty(JTabbedPane tabbedPane) {
        Objects.requireNonNull(tabbedPane, "tabbedPane");
        SimpleIntegerProperty p = (SimpleIntegerProperty) tabbedPane.getClientProperty(PROP_SELECTED_INDEX);
        if (p == null) {
            p = new SimpleIntegerProperty(tabbedPane, "selectedIndex", tabbedPane.getSelectedIndex());
            tabbedPane.putClientProperty(PROP_SELECTED_INDEX, p);
            tabbedPane.addChangeListener(SWING_PROP_LISTENER);
            p.addListener(FX_PROP_LISTENER);
        }
        return p;
    }
}
