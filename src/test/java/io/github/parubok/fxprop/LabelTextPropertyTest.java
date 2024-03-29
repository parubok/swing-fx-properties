package io.github.parubok.fxprop;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import io.github.parubok.swingfx.beans.property.SimpleStringProperty;
import io.github.parubok.swingfx.beans.value.ObservableValue;

import javax.swing.SwingUtilities;

class LabelTextPropertyTest {
    @Test
    void chained_properties() {
        SwingUtilities.invokeLater(() -> {
            SimpleStringProperty p0 = new SimpleStringProperty("t");
            ObservableValue<String> p = p0;
            TestLabel label = null;
            for (int i = 0; i < 100; i++) {
                label = new TestLabel();
                label.textProperty().bind(p);
                p = label.textProperty();
            }
            Assertions.assertEquals("t", label.getText());
            for (int i = 0; i < 20; i++) {
                p0.set("d" + i);
                Assertions.assertEquals("d" + i, label.getText());
            }
        });
    }
}
