package io.github.parubok.fxprop;

import io.github.parubok.swingfx.beans.property.BooleanProperty;
import io.github.parubok.swingfx.beans.property.ReadOnlyBooleanProperty;
import io.github.parubok.swingfx.beans.property.SimpleBooleanProperty;

import javax.swing.JComponent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Objects;

import static io.github.parubok.fxprop.ClientProps.PROP_MOUSE_OVER;

final class MouseOverPropertyImpl {
    private static final MouseListener MOUSE_LISTENER = new MouseListener() {
        @Override
        public void mouseClicked(MouseEvent e) {
            // do nothing
        }

        @Override
        public void mousePressed(MouseEvent e) {
            // do nothing
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            // do nothing
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            updateProp(e, true);
        }

        @Override
        public void mouseExited(MouseEvent e) {
            updateProp(e, false);
        }
    };

    private static void updateProp(MouseEvent e, boolean targetValue) {
        JComponent component = (JComponent) e.getSource();
        BooleanProperty p = (BooleanProperty) component.getClientProperty(ClientProps.PROP_MOUSE_OVER);
        if (p.get() != targetValue) {
            p.set(targetValue);
        }
    }

    static ReadOnlyBooleanProperty getProperty(JComponent component) {
        Objects.requireNonNull(component, "component");
        BooleanProperty p = (BooleanProperty) component.getClientProperty(PROP_MOUSE_OVER);
        if (p == null) {
            p = new SimpleBooleanProperty(component, "mouseOver", false);
            component.putClientProperty(PROP_MOUSE_OVER, p);
            component.addMouseListener(MOUSE_LISTENER);
        }
        return p;
    }
}
