package io.github.parubok.fxprop;

import io.github.parubok.swingfx.beans.property.IntegerProperty;
import io.github.parubok.swingfx.beans.property.IntegerPropertyBase;

import javax.swing.JTabbedPane;
import java.util.Objects;

import static io.github.parubok.fxprop.ClientProps.PROP_SELECTED_INDEX;

final class TabbedPaneSelectedIndexPropertyImpl {

    private static class TabbedPaneSelectedIndexProperty extends IntegerPropertyBase {
        private final JTabbedPane tabbedPane;

        TabbedPaneSelectedIndexProperty(JTabbedPane tabbedPane) {
            super();
            this.tabbedPane = tabbedPane;
            super.set(tabbedPane.getSelectedIndex());
        }

        @Override
        public void set(int newValue) {
            if (tabbedPane.getSelectedIndex() != newValue) {
                tabbedPane.removeChangeListener(SWING_PROP_LISTENER);
                try {
                    tabbedPane.setSelectedIndex(newValue); // IndexOutOfBoundsException can be thrown here
                } finally {
                    tabbedPane.addChangeListener(SWING_PROP_LISTENER);
                }
            }
            super.set(newValue);
        }

        @Override
        public JTabbedPane getBean() {
            return tabbedPane;
        }

        @Override
        public String getName() {
            return "selectedIndex";
        }
    }

    private static final javax.swing.event.ChangeListener SWING_PROP_LISTENER = e -> {
        JTabbedPane tabbedPane = (JTabbedPane) e.getSource();
        TabbedPaneSelectedIndexProperty p = prop(tabbedPane);
        int newValue = tabbedPane.getSelectedIndex();
        if (newValue != p.get()) {
            p.set(newValue);
        }
    };

    private static TabbedPaneSelectedIndexProperty prop(JTabbedPane tabbedPane) {
        return (TabbedPaneSelectedIndexProperty) tabbedPane.getClientProperty(PROP_SELECTED_INDEX);
    }

    static IntegerProperty getProperty(JTabbedPane tabbedPane) {
        Objects.requireNonNull(tabbedPane, "tabbedPane");
        TabbedPaneSelectedIndexProperty p = prop(tabbedPane);
        if (p == null) {
            p = new TabbedPaneSelectedIndexProperty(tabbedPane);
            tabbedPane.putClientProperty(PROP_SELECTED_INDEX, p);
            tabbedPane.addChangeListener(SWING_PROP_LISTENER);
        }
        return p;
    }
}
