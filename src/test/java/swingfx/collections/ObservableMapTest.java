package swingfx.collections;

import com.sun.swingfx.collections.ObservableMapWrapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import swingfx.beans.binding.ObjectBinding;

import java.util.HashMap;

class ObservableMapTest {
    @Test
    void valueAt_1() {
        ObservableMap<String, String> map = new ObservableMapWrapper<>(new HashMap<>());
        ObjectBinding<String> b = map.valueAt("key1", "v1");
        Assertions.assertEquals("v1", b.get());
        map.put("key1", "abc");
        Assertions.assertEquals("abc", b.get());
    }

    @Test
    void valueAt_2() {
        ObservableMap<String, String> map = new ObservableMapWrapper<>(new HashMap<>());
        map.put("key1", null);
        ObjectBinding<String> b = map.valueAt("key1", "v1");
        Assertions.assertNull(b.get());
    }
}
