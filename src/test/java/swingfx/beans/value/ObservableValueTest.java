package swingfx.beans.value;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import swingfx.beans.binding.BindingEvaluationException;
import swingfx.beans.binding.IntegerBinding;
import swingfx.beans.property.SimpleStringProperty;

class ObservableValueTest {
    @Test
    void asInteger_1() {
        SimpleStringProperty p2 = new SimpleStringProperty("123");
        IntegerBinding integerBinding = p2.asInteger(Integer::parseInt);
        Assertions.assertEquals(123, integerBinding.get());

        p2.setValue("-20");
        Assertions.assertEquals(-20, integerBinding.get());
    }

    @Test
    void asInteger_2() {
        SimpleStringProperty p = new SimpleStringProperty("123");
        IntegerBinding integerBinding = p.asInteger(Integer::parseInt);
        Assertions.assertEquals(123, integerBinding.get());
        p.setValue("abc_dfg");
        Assertions.assertThrows(BindingEvaluationException.class, () -> integerBinding.get());
    }

    @Test
    void asInteger_3() {
        SimpleStringProperty stringP = new SimpleStringProperty("10");
        IntegerBinding integerBinding = stringP.asInteger(Integer::parseInt);
        Assertions.assertEquals(10, integerBinding.get());
        integerBinding.addListener((o, v1, v2) -> {}); // need listener to get around lazy evaluation
        Assertions.assertThrows(BindingEvaluationException.class, () -> stringP.setValue("string"));
    }
}
