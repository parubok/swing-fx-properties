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

package io.github.parubok.swingfx.beans.property;

import io.github.parubok.com.sun.swingfx.binding.BidirectionalBinding;
import io.github.parubok.swingfx.beans.binding.Bindings;
import io.github.parubok.swingfx.beans.value.ObservableIntegerValue;
import io.github.parubok.swingfx.beans.value.ObservableValue;
import io.github.parubok.swingfx.beans.value.WritableIntegerValue;
import io.github.parubok.fxprop.misc.Logging;

import java.security.AccessControlContext;
import java.security.AccessController;
import java.security.PrivilegedAction;

/**
 * This class defines a {@link Property} wrapping an {@code int} value.
 * <p>
 * The value of an {@code IntegerProperty} can be get and set with {@link #get()},
 * {@link #getValue()}, {@link #set(int)}, and {@link #setValue(Number)}.
 * <p>
 * A property can be bound and unbound unidirectional with
 * {@link #bind(ObservableValue)} and {@link #unbind()}. Bidirectional bindings
 * can be created and removed with {@link #bindBidirectional(Property)} and
 * {@link #unbindBidirectional(Property)}.
 * <p>
 * The context of a {@code IntegerProperty} can be read with {@link #getBean()}
 * and {@link #getName()}.
 * <p>
 * Note: setting or binding this property to a null value will set the property to "0.0". See {@link #setValue(java.lang.Number) }.
 *
 * @see ObservableIntegerValue
 * @see WritableIntegerValue
 * @see ReadOnlyIntegerProperty
 * @see Property
 *
 * @since JavaFX 2.0
 */
public abstract class IntegerProperty extends ReadOnlyIntegerProperty implements
        Property<Number>, WritableIntegerValue {

    /**
     * {@inheritDoc}
     */
    @Override
    public void setValue(Number v) {
        if (v == null) {
            Logging.getLogger().fine("Attempt to set integer property to null, using default value instead.");
            set(0);
        } else {
            set(v.intValue());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void bindBidirectional(Property<Number> other) {
        Bindings.bindBidirectional(this, other);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void unbindBidirectional(Property<Number> other) {
        Bindings.unbindBidirectional(this, other);
    }

    /**
     * Returns a string representation of this {@code IntegerProperty} object.
     * @return a string representation of this {@code IntegerProperty} object.
     */
    @Override
    public String toString() {
        final Object bean = getBean();
        final String name = getName();
        final StringBuilder result = new StringBuilder(
                "IntegerProperty [");
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
     * Returns a {@code IntegerProperty} that wraps a
     * {@link Property} and is
     * bidirectionally bound to it.
     * Changing this property will result in a change of the original property.
     *
     * <p>
     * This is very useful when bidirectionally binding an ObjectProperty<Integer> and
     * a IntegerProperty.
     *
     * <blockquote><pre>
     *   IntegerProperty integerProperty = new SimpleIntegerProperty(1);
     *   ObjectProperty&lt;Integer&gt; objectProperty = new SimpleObjectProperty&lt;&gt;(2);
     *
     *   // Need to keep the reference as bidirectional binding uses weak references
     *   IntegerProperty objectAsInteger = IntegerProperty.integerProperty(objectProperty);
     *
     *   integerProperty.bindBidirectional(objectAsInteger);
     *
     * </pre></blockquote>
     *
     * Another approach is to convert the IntegerProperty to ObjectProperty using
     * {@link #asObject()} method.
     *
     * <p>
     * Note: null values in the source property will be interpreted as 0
     *
     * @param property
     *            The source {@code Property}
     * @return A {@code IntegerProperty} that wraps the
     *         {@code Property}
     * @throws NullPointerException
     *             if {@code property} is {@code null}
     * @see #asObject()
     * @since JavaFX 8.0
     */
     public static IntegerProperty integerProperty(final Property<Integer> property) {
        if (property == null) {
            throw new NullPointerException("Property cannot be null");
        }
        return new IntegerPropertyBase() {
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
     * Creates an {@link ObjectProperty}
     * that bidirectionally bound to this {@code IntegerProperty}. If the
     * value of this {@code IntegerProperty} changes, the value of the
     * {@code ObjectProperty} will be updated automatically and vice-versa.
     *
     * <p>
     * Can be used for binding an ObjectProperty to IntegerProperty.
     *
     * <blockquote><pre>
     *   IntegerProperty integerProperty = new SimpleIntegerProperty(1);
     *   ObjectProperty&lt;Integer&gt; objectProperty = new SimpleObjectProperty&lt;&gt;(2);
     *
     *   objectProperty.bind(integerProperty.asObject());
     * </pre></blockquote>
     *
     * @return the new {@code ObjectProperty}
     * @since JavaFX 8.0
     */
    @Override
    public ObjectProperty<Integer> asObject() {
        return new ObjectPropertyBase<Integer>() {
            private final AccessControlContext acc = AccessController.getContext();
            {
                BidirectionalBinding.bindNumber(this, IntegerProperty.this);
            }

            @Override
            public Object getBean() {
                return null; // Virtual property, does not exist on a bean
            }

            @Override
            public String getName() {
                return IntegerProperty.this.getName();
            }

            @Override
            protected void finalize() throws Throwable {
                try {
                    AccessController.doPrivileged((PrivilegedAction<Void>) () -> {
                        BidirectionalBinding.unbindNumber(this, IntegerProperty.this);
                        return null;
                    }, acc);
                } finally {
                    super.finalize();
                }
            }

        };
    }
}
