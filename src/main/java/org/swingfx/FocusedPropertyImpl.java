package org.swingfx;

import swingfx.beans.property.ReadOnlyBooleanProperty;
import swingfx.beans.property.ReadOnlyBooleanPropertyBase;

import javax.swing.JComponent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.Objects;

import static org.swingfx.ClientProps.PROP_FOCUSED;

final class FocusedPropertyImpl {
    private static class ComponentFocusedProperty extends ReadOnlyBooleanPropertyBase implements FocusListener {
        private final JComponent component;
        private boolean value;

        ComponentFocusedProperty(JComponent component) {
            this.component = component;
            this.value = component.hasFocus();
            this.component.addFocusListener(this);
        }

        @Override
        public void focusGained(FocusEvent e) {
            valueChanged(true);
        }

        @Override
        public void focusLost(FocusEvent e) {
            valueChanged(false);
        }

        @Override
        public boolean get() {
            return value;
        }

        @Override
        public JComponent getBean() {
            return component;
        }

        @Override
        public String getName() {
            return "hasFocus";
        }

        void valueChanged(boolean newValue) {
            if (this.value != newValue) {
                this.value = newValue;
                fireValueChangedEvent();
            }
        }
    }

    static ReadOnlyBooleanProperty getProperty(JComponent component) {
        Objects.requireNonNull(component, "component");
        ComponentFocusedProperty p = (ComponentFocusedProperty) component.getClientProperty(PROP_FOCUSED);
        if (p == null) {
            p = new ComponentFocusedProperty(component);
            component.putClientProperty(PROP_FOCUSED, p);
        }
        return p;
    }
}
