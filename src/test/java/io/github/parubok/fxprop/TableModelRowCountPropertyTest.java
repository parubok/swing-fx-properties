package io.github.parubok.fxprop;

import io.github.parubok.swingfx.beans.property.ReadOnlyIntegerProperty;
import io.github.parubok.swingfx.beans.value.ChangeListener;
import io.github.parubok.swingfx.beans.value.ObservableValue;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TableModelRowCountPropertyTest {
    @Test
    public void add_row() throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            DefaultTableModel model = new DefaultTableModel(3, 2);
            ReadOnlyIntegerProperty rowCountProperty = SwingPropertySupport.modelRowCountProperty(model);
            Assertions.assertNotNull(rowCountProperty);
            final List<Number> values = new ArrayList<>();
            rowCountProperty.addListener(new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                    values.add(oldValue);
                    values.add(newValue);
                }
            });
            Assertions.assertEquals(3, rowCountProperty.get());
            model.addRow(new Object[]{"v1", "v2"});
            Assertions.assertEquals(4, rowCountProperty.get());
            Assertions.assertEquals(Arrays.asList(3, 4), values);
        });
    }

    @Test
    public void remove_row() throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            DefaultTableModel model = new DefaultTableModel(3, 2);
            ReadOnlyIntegerProperty rowCountProperty = SwingPropertySupport.modelRowCountProperty(model);
            Assertions.assertNotNull(rowCountProperty);
            final List<Number> values = new ArrayList<>();
            rowCountProperty.addListener(new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                    values.add(oldValue);
                    values.add(newValue);
                }
            });
            Assertions.assertEquals(3, rowCountProperty.get());
            model.removeRow(1);
            Assertions.assertEquals(2, rowCountProperty.get());
            Assertions.assertEquals(Arrays.asList(3, 2), values);
        });
    }

    @Test
    public void move_row() throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            DefaultTableModel model = new DefaultTableModel(10, 2);
            ReadOnlyIntegerProperty rowCountProperty = SwingPropertySupport.modelRowCountProperty(model);
            Assertions.assertNotNull(rowCountProperty);
            final List<Number> values = new ArrayList<>();
            rowCountProperty.addListener(new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                    values.add(oldValue);
                    values.add(newValue);
                }
            });
            Assertions.assertEquals(10, rowCountProperty.get());
            model.moveRow(0, 2, 5);
            Assertions.assertEquals(10, rowCountProperty.get());
            Assertions.assertEquals(Collections.emptyList(), values);
        });
    }

    @Test
    public void week_ref() throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            DefaultTableModel model1 = new DefaultTableModel(10, 2);
            DefaultTableModel model2 = new DefaultTableModel(20, 2);
            ReadOnlyIntegerProperty p1 = SwingPropertySupport.modelRowCountProperty(model1);
            ReadOnlyIntegerProperty p2 = SwingPropertySupport.modelRowCountProperty(model2);
            for (int i = 0; i < 100; i++) {
                Assertions.assertSame(p1, SwingPropertySupport.modelRowCountProperty(model1));
                Assertions.assertSame(p2, SwingPropertySupport.modelRowCountProperty(model2));
                Assertions.assertNotSame(p1, p2);
                System.gc();
            }
        });
    }
}
