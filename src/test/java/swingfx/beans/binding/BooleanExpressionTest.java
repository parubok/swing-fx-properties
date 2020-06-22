package swingfx.beans.binding;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import swingfx.beans.property.SimpleBooleanProperty;

class BooleanExpressionTest {
    @Test
    void asStringExpression() {
        SimpleBooleanProperty b = new SimpleBooleanProperty(true);
        StringExpression ex = b.asStringExpression("ex:%s");
        Assertions.assertEquals("ex:true", ex.getValue());
        b.set(false);
        Assertions.assertEquals("ex:false", ex.getValue());
    }
}
