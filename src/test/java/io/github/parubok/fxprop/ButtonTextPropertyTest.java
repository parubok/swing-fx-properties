package io.github.parubok.fxprop;

import io.github.parubok.swingfx.beans.property.StringProperty;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.swing.JButton;
import javax.swing.SwingUtilities;

class ButtonTextPropertyTest {
    @Test
    void basicTest() {
        SwingUtilities.invokeLater(() -> {
            JButton button = new JButton("text1");
            StringProperty p = SwingPropertySupport.textProperty(button);
            p.set("text2");
            Assertions.assertEquals("text2", button.getText());
            button.setText("text3");
            Assertions.assertEquals("text3", p.get());
        });
    }
}
