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
import io.github.parubok.swingfx.beans.value.ObservableValue;
import io.github.parubok.swingfx.beans.value.WritableFloatValue;
import io.github.parubok.fxprop.misc.Logging;
import io.github.parubok.swingfx.beans.value.ObservableFloatValue;

import java.security.AccessControlContext;
import java.security.AccessController;
import java.security.PrivilegedAction;

/**
 * This class defines a {@link Property} wrapping a {@code float} value.
 * <p>
 * The value of a {@code FloatProperty} can be get and set with {@link #get()},
 * {@link #getValue()}, {@link #set(float)}, and {@link #setValue(Number)}.
 * <p>
 * A property can be bound and unbound unidirectional with
 * {@link #bind(ObservableValue)} and {@link #unbind()}. Bidirectional bindings
 * can be created and removed with {@link #bindBidirectional(Property)} and
 * {@link #unbindBidirectional(Property)}.
 * <p>
 * The context of a {@code FloatProperty} can be read with {@link #getBean()}
 * and {@link #getName()}.
 * <p>
 * Note: setting or binding this property to a null value will set the property to "0.0". See {@link #setValue(java.lang.Number) }.
 *
 * @see ObservableFloatValue
 * @see WritableFloatValue
 * @see ReadOnlyFloatProperty
 * @see Property
 *
 * @since JavaFX 2.0
 */
public abstract class FloatProperty extends ReadOnlyFloatProperty implements
        Property<Number>, WritableFloatValue {

    /**
     * {@inheritDoc}
     */
    @Override
    public void setValue(Number v) {
        if (v == null) {
            Logging.getLogger().fine("Attempt to set float property to null, using default value instead.");
            set(0.0f);
        } else {
            set(v.floatValue());
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
     * Returns a string representation of this {@code FloatProperty} object.
     * @return a string representation of this {@code FloatProperty} object.
     */
    @Override
    public String toString() {
        final Object bean = getBean();
        final String name = getName();
        final StringBuilder result = new StringBuilder(
                "FloatProperty [");
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
     * Returns a {@code FloatProperty} that wraps a
     * {@link Property} and is
     * bidirectionally bound to it.
     * Changing this property will result in a change of the original property.
     *
     * <p>
     * This is very useful when bidirectionally binding an ObjectProperty<Float> and
     * a FloatProperty.
     *
     * <blockquote><pre>
     *   FloatProperty floatProperty = new SimpleFloatProperty(1.0f);
     *   ObjectProperty&lt;Float&gt; objectProperty = new SimpleObjectProperty&lt;&gt;(2.0f);
     *
     *   // Need to keep the reference as bidirectional binding uses weak references
     *   FloatProperty objectAsFloat = FloatProperty.floatProperty(objectProperty);
     *
     *   floatProperty.bindBidirectional(objectAsFloat);
     *
     * </pre></blockquote>
     *
     * Another approach is to convert the FloatProperty to ObjectProperty using
     * {@link #asObject()} method.
     *
     * <p>
     * Note: null values in the source property will be interpreted as 0f
     *
     * @param property
     *            The source {@code Property}
     * @return A {@code FloatProperty} that wraps the
     *         {@code Property}
     * @throws NullPointerException
     *             if {@code property} is {@code null}
     * @see #asObject()
     * @since JavaFX 8.0
     */
     public static FloatProperty floatProperty(final Property<Float> property) {
        if (property == null) {
            throw new NullPointerException("Property cannot be null");
        }
        return new FloatPropertyBase() {
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
     * Creates an {@link ObjectProperty} that
     * bidirectionally bound to this {@code FloatProperty}. If the value of
     * this {@code FloatProperty} changes, the value of the
     * {@code ObjectProperty} will be updated automatically and vice-versa.
     *
     * <p>
     * Can be used for binding an ObjectProperty to FloatProperty.
     *
     * <blockquote><pre>
     *   FloatProperty floatProperty = new SimpleFloatProperty(1.0f);
     *   ObjectProperty&lt;Float&gt; objectProperty = new SimpleObjectProperty&lt;&gt;(2.0f);
     *
     *   objectProperty.bind(floatProperty.asObject());
     * </pre></blockquote>
     *
     * @return the new {@code ObjectProperty}
     * @since JavaFX 8.0
     */
    @Override
    public ObjectProperty<Float> asObject() {
        return new ObjectPropertyBase<Float>() {
            private final AccessControlContext acc = AccessController.getContext();
            {
                BidirectionalBinding.bindNumber(this, FloatProperty.this);
            }

            @Override
            public Object getBean() {
                return null; // Virtual property, does not exist on a bean
            }

            @Override
            public String getName() {
                return FloatProperty.this.getName();
            }

            @Override
            protected void finalize() throws Throwable {
                try {
                    AccessController.doPrivileged((PrivilegedAction<Void>) () -> {
                        BidirectionalBinding.unbindNumber(this, FloatProperty.this);
                        return null;
                    }, acc);
                } finally {
                    super.finalize();
                }
            }

        };
    }

}
