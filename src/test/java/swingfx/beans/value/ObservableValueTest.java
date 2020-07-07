package swingfx.beans.value;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import swingfx.beans.binding.BindingEvaluationException;
import swingfx.beans.binding.BooleanBinding;
import swingfx.beans.binding.IntegerBinding;
import swingfx.beans.binding.StringExpression;
import swingfx.beans.property.SimpleBooleanProperty;
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

    @Test
    void asBoolean_1() {
        SimpleStringProperty stringP = new SimpleStringProperty("false");
        BooleanBinding booleanBinding = stringP.asBoolean(Boolean::parseBoolean);
        Assertions.assertFalse(booleanBinding.get());
        stringP.set("true");
        Assertions.assertTrue(booleanBinding.get());
    }

    @Test
    void asBoolean_2() {
        SimpleStringProperty stringP = new SimpleStringProperty("false");
        BooleanBinding booleanBinding = stringP.asBoolean(s -> {
            throw new RuntimeException("error!");
        });
        Assertions.assertThrows(BindingEvaluationException.class, () -> booleanBinding.get());
    }

    @Test
    void asStringExpression() {
        SimpleBooleanProperty b = new SimpleBooleanProperty(true);
        StringExpression ex = b.asStringExpression("ex:%s");
        Assertions.assertEquals("ex:true", ex.getValue());
        b.set(false);
        Assertions.assertEquals("ex:false", ex.getValue());
    }
}
