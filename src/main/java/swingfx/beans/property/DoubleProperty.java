/*
 * Copyright (c) 2011, 2017, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */

package swingfx.beans.property;

import com.sun.swingfx.binding.BidirectionalBinding;
import swingfx.beans.binding.Bindings;
import swingfx.beans.value.ObservableValue;
import swingfx.beans.value.WritableDoubleValue;
import org.swingfx.misc.Logging;
import swingfx.beans.value.ObservableDoubleValue;

import java.security.AccessControlContext;
import java.security.AccessController;
import java.security.PrivilegedAction;

/**
 * This class defines a {@link swingfx.beans.property.Property} wrapping a {@code double} value.
 * <p>
 * The value of a {@code DoubleProperty} can be get and set with {@link #get()},
 * {@link #getValue()}, {@link #set(double)}, and {@link #setValue(Number)}.
 * <p>
 * A property can be bound and unbound unidirectional with
 * {@link #bind(ObservableValue)} and {@link #unbind()}. Bidirectional bindings
 * can be created and removed with {@link #bindBidirectional(swingfx.beans.property.Property)} and
 * {@link #unbindBidirectional(swingfx.beans.property.Property)}.
 * <p>
 * The context of a {@code DoubleProperty} can be read with {@link #getBean()}
 * and {@link #getName()}.
 * <p>
 * Note: setting or binding this property to a null value will set the property to "0.0". See {@link #setValue(java.lang.Number) }.
 *
 * @see ObservableDoubleValue
 * @see WritableDoubleValue
 * @see swingfx.beans.property.ReadOnlyDoubleProperty
 * @see swingfx.beans.property.Property
 *
 * @since JavaFX 2.0
 */
public abstract class DoubleProperty extends ReadOnlyDoubleProperty implements
        swingfx.beans.property.Property<Number>, WritableDoubleValue {

    /**
     * {@inheritDoc}
     */
    @Override
    public void setValue(Number v) {
        if (v == null) {
            Logging.getLogger().fine("Attempt to set double property to null, using default value instead.");
            set(0.0);
        } else {
            set(v.doubleValue());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void bindBidirectional(swingfx.beans.property.Property<Number> other) {
        Bindings.bindBidirectional(this, other);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void unbindBidirectional(swingfx.beans.property.Property<Number> other) {
        Bindings.unbindBidirectional(this, other);
    }

    /**
     * Returns a string representation of this {@code DoubleProperty} object.
     * @return a string representation of this {@code DoubleProperty} object.
     */
    @Override
    public String toString() {
        final Object bean = getBean();
        final String name = getName();
        final StringBuilder result = new StringBuilder(
                "DoubleProperty [");
        if (bean != null) {
            result.append("bean: ").append(bean).append(", ");
        }
        if ((name != null) && (!name.equals(""))) {
            result.append("name: ").append(name).append(", ");
        }
        result.append("value: ").append(get()).append("]");
        return result.toString();
    }

    /**
     * Returns a {@code DoubleProperty} that wraps a
     * {@link swingfx.beans.property.Property} and is
     * bidirectionally bound to it.
     * Changing this property will result in a change of the original property.
     *
     * <p>
     * This is very useful when bidirectionally binding an ObjectProperty<Double> and
     * a DoubleProperty.
     *
     * <blockquote><pre>
     *   DoubleProperty doubleProperty = new SimpleDoubleProperty(1.0);
     *   ObjectProperty&lt;Double&gt; objectProperty = new SimpleObjectProperty&lt;&gt;(2.0);
     *
     *   // Need to keep the reference as bidirectional binding uses weak references
     *   DoubleProperty objectAsDouble = DoubleProperty.doubleProperty(objectProperty);
     *
     *   doubleProperty.bindBidirectional(objectAsDouble);
     *
     * </pre></blockquote>
     *
     * Another approach is to convert the DoubleProperty to ObjectProperty using
     * {@link #asObject()} method.
     * <p>
     * Note: null values in the source property will be interpreted as 0.0
     *
     * @param property
     *            The source {@code Property}
     * @return A {@code DoubleProperty} that wraps the
     *         {@code Property}
     * @throws NullPointerException
     *             if {@code property} is {@code null}
     * @see #asObject()
     * @since JavaFX 8.0
     */
    public static DoubleProperty doubleProperty(final Property<Double> property) {
        if (property == null) {
            throw new NullPointerException("Property cannot be null");
        }
        return new DoublePropertyBase() {
            private final AccessControlContext acc = AccessController.getContext();
            {
                BidirectionalBinding.bindNumber(this, property);
            }

            @Override
            public Object getBean() {
                return null; // Virtual property, no bean
            }

            @Override
            public String getName() {
                return property.getName();
            }

            @Override
            protected void finalize() throws Throwable {
                try {
                    AccessController.doPrivileged((PrivilegedAction<Void>) () -> {
                        BidirectionalBinding.unbindNumber(property, this);
                        return null;
                    }, acc);
                } finally {
                    super.finalize();
                }
            }
        };
    }

    /**
     * Creates an {@link swingfx.beans.property.ObjectProperty}
     * that bidirectionally bound to this {@code DoubleProperty}. If the
     * value of this {@code DoubleProperty} changes, the value of the
     * {@code ObjectProperty} will be updated automatically and vice-versa.
     *
     * <p>
     * Can be used for binding an ObjectProperty to DoubleProperty.
     *
     * <blockquote><pre>
     *   DoubleProperty doubleProperty = new SimpleDoubleProperty(1.0);
     *   ObjectProperty&lt;Double&gt; objectProperty = new SimpleObjectProperty&lt;&gt;(2.0);
     *
     *   objectProperty.bind(doubleProperty.asObject());
     * </pre></blockquote>
     *
     * @return the new {@code ObjectProperty}
     * @since JavaFX 8.0
     */
    @Override
    public ObjectProperty<Double> asObject() {
        return new ObjectPropertyBase<Double>() {
            private final AccessControlContext acc = AccessController.getContext();
            {
                BidirectionalBinding.bindNumber(this, DoubleProperty.this);
            }

            @Override
            public Object getBean() {
                return null; // Virtual property, does not exist on a bean
            }

            @Override
            public String getName() {
                return DoubleProperty.this.getName();
            }

            @Override
            protected void finalize() throws Throwable {
                try {
                    AccessController.doPrivileged((PrivilegedAction<Void>) () -> {
                        BidirectionalBinding.unbindNumber(this, DoubleProperty.this);
                        return null;
                    }, acc);
                } finally {
                    super.finalize();
                }
            }

        };
    }


}
