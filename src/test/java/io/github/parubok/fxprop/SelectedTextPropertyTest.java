package io.github.parubok.fxprop;

import io.github.parubok.swingfx.beans.property.ReadOnlyStringProperty;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

public class SelectedTextPropertyTest {
    @Test
    public void basicTest() throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            JTextArea textArea = new JTextArea();
            ReadOnlyStringProperty p = SwingPropertySupport.selectedTextProperty(textArea);
            Assertions.assertEquals("", p.get());

            textArea.setText("abc");
            Assertions.assertEquals("", p.get());
            textArea.selectAll();
            Assertions.assertEquals("abc", p.get());

            textArea.setSelectionStart(1);
            Assertions.assertEquals("bc", p.get());

            textArea.setText("");
            Assertions.assertEquals("", p.get());
            textArea.selectAll();
            Assertions.assertEquals("", p.get());
        });
    }
}
