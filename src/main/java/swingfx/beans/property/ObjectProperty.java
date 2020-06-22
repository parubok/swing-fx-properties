/*
 * Copyright (c) 2011, 2013, Oracle and/or its affiliates. All rights reserved.
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

import swingfx.beans.binding.Bindings;
import swingfx.beans.value.ObservableValue;
import swingfx.beans.value.WritableObjectValue;
import swingfx.beans.value.ObservableObjectValue;

/**
 * This class provides a full implementation of a {@link swingfx.beans.property.Property} wrapping an
 * arbitrary {@code Object}.
 *
 * The value of a {@code ObjectProperty} can be get and set with {@link #get()},
 * {@link #getValue()}, {@link #set(Object)}, and {@link #setValue(Object)}.
 *
 * A property can be bound and unbound unidirectional with
 * {@link #bind(ObservableValue)} and {@link #unbind()}. Bidirectional bindings
 * can be created and removed with {@link #bindBidirectional(swingfx.beans.property.Property)} and
 * {@link #unbindBidirectional(swingfx.beans.property.Property)}.
 *
 * The context of a {@code ObjectProperty} can be read with {@link #getBean()}
 * and {@link #getName()}.
 *
 * For specialized implementations for {@link swingfx.collections.ObservableList}, {@link swingfx.collections.ObservableSet} and
 * {@link swingfx.collections.ObservableMap} that also report changes inside the collections, see
 * {@link ListProperty}, {@link SetProperty} and {@link MapProperty}, respectively.
 *
 * @see ObservableObjectValue
 * @see WritableObjectValue
 * @see swingfx.beans.property.ReadOnlyObjectProperty
 * @see swingfx.beans.property.Property
 *
 *
 * @param <T>
 *            the type of the wrapped {@code Object}
 * @since JavaFX 2.0
 */
public abstract class ObjectProperty<T> extends ReadOnlyObjectProperty<T>
        implements swingfx.beans.property.Property<T>, WritableObjectValue<T> {

    /**
     * {@inheritDoc}
     */
    @Override
    public void setValue(T v) {
        set(v);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void bindBidirectional(swingfx.beans.property.Property<T> other) {
        Bindings.bindBidirectional(this, other);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void unbindBidirectional(Property<T> other) {
        Bindings.unbindBidirectional(this, other);
    }

    /**
     * Returns a string representation of this {@code ObjectProperty} object.
     * @return a string representation of this {@code ObjectProperty} object.
     */
    @Override
    public String toString() {
        final Object bean = getBean();
        final String name = getName();
        final StringBuilder result = new StringBuilder(
                "ObjectProperty [");
        if (bean != null) {
            result.append("bean: ").append(bean).append(", ");
        }
        if ((name != null) && (!name.equals(""))) {
            result.append("name: ").append(name).append(", ");
        }
        result.append("value: ").append(get()).append("]");
        return result.toString();
    }
}
