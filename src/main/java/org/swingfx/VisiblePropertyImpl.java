package org.swingfx;

import swingfx.beans.property.BooleanProperty;
import swingfx.beans.property.SimpleBooleanProperty;
import swingfx.beans.value.ChangeListener;

import javax.swing.JComponent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.Objects;

final class VisiblePropertyImpl {
    private static final ComponentListener COMPONENT_LISTENER_VISIBLE = new ComponentAdapter() {
        private void updateProp(ComponentEvent e) {
            JComponent component = (JComponent) e.getComponent();
            BooleanProperty p = (BooleanProperty) component.getClientProperty(ClientProps.PROP_VISIBLE);
            boolean visible = component.isVisible();
            if (visible != p.get()) {
                p.set(visible);
            }
        }

        @Override
        public void componentShown(ComponentEvent e) {
            updateProp(e);
        }

        @Override
        public void componentHidden(ComponentEvent e) {
            updateProp(e);
        }
    };

    private static final ChangeListener<Boolean> FX_PROP_LISTENER_VISIBLE = (observable, oldValue, newValue) -> {
        BooleanProperty p = (BooleanProperty) observable;
        JComponent component = (JComponent) p.getBean();
        if (newValue.booleanValue() != component.isVisible()) {
            component.setVisible(newValue.booleanValue());
        }
    };

    public static BooleanProperty getProperty(JComponent component) {
        Objects.requireNonNull(component, "component");
        BooleanProperty p = (BooleanProperty) component.getClientProperty(ClientProps.PROP_VISIBLE);
        if (p == null) {
            p = new SimpleBooleanProperty(component, "visible", component.isVisible());
            component.putClientProperty(ClientProps.PROP_VISIBLE, p);
            component.addComponentListener(COMPONENT_LISTENER_VISIBLE);
            p.addListener(FX_PROP_LISTENER_VISIBLE);
        }
        return p;
    }
}
