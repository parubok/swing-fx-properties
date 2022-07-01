package io.github.parubok.swingfx.beans.binding;

import io.github.parubok.com.sun.swingfx.collections.ObservableListWrapper;
import io.github.parubok.com.sun.swingfx.collections.ObservableMapWrapper;
import io.github.parubok.swingfx.beans.property.SimpleDoubleProperty;
import io.github.parubok.swingfx.beans.property.SimpleIntegerProperty;
import io.github.parubok.swingfx.beans.property.SimpleStringProperty;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import io.github.parubok.swingfx.collections.ObservableList;
import io.github.parubok.swingfx.collections.ObservableMap;

import java.util.ArrayList;
import java.util.Arrays;
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
    void stringValueAt_map_1() {
        ObservableMap<String, String> map = new ObservableMapWrapper<>(new HashMap<>());
        StringBinding b1 = Bindings.stringValueAt(map, "key1", "");
        StringBinding b2 = Bindings.stringValueAt(map, "key2");
        Assertions.assertEquals("", b1.get());
        Assertions.assertNull(b2.get());
    }

    @Test
    void valueAt_list_defaultValue_1() {
        ObservableList<String> list = new ObservableListWrapper<>(new ArrayList<>());
        ObjectBinding<String> b = Bindings.valueAt(list, 0, "def");
        Assertions.assertEquals("def", b.get());

        SimpleStringProperty p = new SimpleStringProperty();
        p.bind(b);

        list.add("val0");
        Assertions.assertEquals("val0", p.get());

        list.clear();
        Assertions.assertEquals("def", p.get());

        list.addAll("valA", "valB");
        Assertions.assertEquals("valA", p.get());
    }

    @Test
    void valueAt_list_1() {
        ObservableList<String> list = new ObservableListWrapper<>(new ArrayList<>());
        ObjectBinding<String> b = Bindings.valueAt(list, 0);
        Assertions.assertThrows(BindingEvaluationException.class, () -> b.get());

        list.add("val0");
        Assertions.assertEquals("val0", b.get());

        list.remove(0);
        Assertions.assertThrows(BindingEvaluationException.class, () -> b.get());
    }

    @Test
    void valueAt_list_observable_index() {
        ObservableList<String> list = new ObservableListWrapper<>(new ArrayList<>());
        SimpleIntegerProperty index = new SimpleIntegerProperty(1);
        ObjectBinding<String> b = Bindings.valueAt(list, index, "def");
        Assertions.assertEquals("def", b.get());
        list.add("v0");
        Assertions.assertEquals("def", b.get());
        list.add("v1");
        Assertions.assertEquals("v1", b.get());
        index.set(0);
        Assertions.assertEquals("v0", b.get());
        index.set(10);
        Assertions.assertEquals("def", b.get());

        index.set(-1);
        Assertions.assertThrows(BindingEvaluationException.class, () -> b.get());
    }

    @Test
    void valueAt_list_null_index() {
        ObservableList<String> list = new ObservableListWrapper<>(new ArrayList<>(Arrays.asList("s0")));
        SimpleIntegerProperty index = new SimpleIntegerProperty(1);
        ObjectBinding<String> b = Bindings.valueAt(list, index, "def");
        Assertions.assertEquals("def", b.get());

        index.setValue(null); // sets the property to 0
        Assertions.assertEquals("s0", b.get());
    }

    @Test
    void createObjectBinding_TriFunction_1() {
        SimpleStringProperty p1 = new SimpleStringProperty("abc");
        SimpleIntegerProperty p2 = new SimpleIntegerProperty(3);
        SimpleDoubleProperty p3 = new SimpleDoubleProperty(1.2);
        ObjectBinding<String> b = Bindings.createObjectBinding(p1, p2, p3, (v1, v2, v3) -> v1 + "_" + v2 + "_" + v3);
        Assertions.assertEquals("abc_3_1.2", b.get());

        p2.setValue(4);
        Assertions.assertEquals("abc_4_1.2", b.get());

        p1.setValue("H7");
        Assertions.assertEquals("H7_4_1.2", b.get());

        b.dispose();
    }

    @Test
    void createObjectBinding_TriFunction_error() {
        SimpleStringProperty p1 = new SimpleStringProperty("abc");
        SimpleIntegerProperty p2 = new SimpleIntegerProperty(3);
        SimpleDoubleProperty p3 = new SimpleDoubleProperty(1.2);
        ObjectBinding<String> b = Bindings.createObjectBinding(p1, p2, p3, (v1, v2, v3) -> {
            throw new RuntimeException("error!");
        });
        Assertions.assertThrows(BindingEvaluationException.class, () -> b.get());
        b.dispose();
    }
}
