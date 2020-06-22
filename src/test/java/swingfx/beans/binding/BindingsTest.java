package swingfx.beans.binding;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import swingfx.beans.property.SimpleIntegerProperty;
import swingfx.beans.property.SimpleStringProperty;

class BindingsTest {
    @Test
    void createObjectBinding() {
        SimpleStringProperty p1 = new SimpleStringProperty("abc123");
        SimpleIntegerProperty p2 = new SimpleIntegerProperty(3);
        ObjectBinding<String> b = Bindings.createObjectBinding(p1, p2, (v1, v2) -> v1.substring(v2.intValue()));
        Assertions.assertEquals("123", b.get());

        p2.setValue(4);
        Assertions.assertEquals("23", b.get());

        p1.setValue("HELLO!!!");
        Assertions.assertEquals("O!!!", b.get());
    }
}
