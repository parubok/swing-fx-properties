package io.github.parubok.swingfx.collections;

import io.github.parubok.com.sun.swingfx.collections.ObservableMapWrapper;
import io.github.parubok.swingfx.beans.binding.ObjectBinding;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

class ObservableMapTest {
    @Test
    void valueAt_1() {
        ObservableMap<String, String> map = new ObservableMapWrapper<>(new HashMap<>());
        ObjectBinding<String> binding = map.valueAt("key1", "v1");
        Assertions.assertEquals("v1", binding.get());
        map.put("key1", "abc");
        Assertions.assertEquals("abc", binding.get());
        map.clear();
        Assertions.assertEquals("v1", binding.get());
    }

    @Test
    void valueAt_2() {
        ObservableMap<String, String> map = new ObservableMapWrapper<>(new HashMap<>());
        map.put("key1", null);
        ObjectBinding<String> b = map.valueAt("key1", "v1");
        Assertions.assertNull(b.get());
    }
}
