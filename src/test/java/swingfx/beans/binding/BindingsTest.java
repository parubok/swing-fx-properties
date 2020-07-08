package swingfx.beans.binding;

import com.sun.swingfx.collections.ObservableMapWrapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import swingfx.beans.property.SimpleIntegerProperty;
import swingfx.beans.property.SimpleStringProperty;
import swingfx.collections.ObservableMap;

import java.util.HashMap;

class BindingsTest {
    @Test
    void createObjectBinding_1() {
        SimpleStringProperty p1 = new SimpleStringProperty("abc123");
        SimpleIntegerProperty p2 = new SimpleIntegerProperty(3);
        ObjectBinding<String> b = Bindings.createObjectBinding(p1, p2, (v1, v2) -> v1.substring(v2.intValue()));
        Assertions.assertEquals("123", b.get());

        p2.setValue(4);
        Assertions.assertEquals("23", b.get());

        p1.setValue("HELLO!!!");
        Assertions.assertEquals("O!!!", b.get());
    }

    @Test
    void createObjectBinding_2() {
        SimpleStringProperty p1 = new SimpleStringProperty("abc123");
        SimpleIntegerProperty p2 = new SimpleIntegerProperty(3);
        ObjectBinding<String> b = Bindings.createObjectBinding(p1, p2, (v1, v2) -> {
            throw new RuntimeException("error!");
        });
        Assertions.assertThrows(BindingEvaluationException.class, () -> b.get());
    }

    @Test
    void stringValueAt_1() {
        ObservableMap<String, String> map = new ObservableMapWrapper<>(new HashMap<>());
        StringBinding b1 = Bindings.stringValueAt(map, "key1", "");
        StringBinding b2 = Bindings.stringValueAt(map, "key2");
        Assertions.assertEquals("", b1.get());
        Assertions.assertNull(b2.get());
    }
}
