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
import swingfx.beans.value.WritableListValue;
import swingfx.beans.value.ObservableListValue;
import swingfx.beans.value.ObservableValue;
import swingfx.collections.ObservableList;

/**
 * This class provides a full implementation of a {@link swingfx.beans.property.Property} wrapping a
 * {@link ObservableList}.
 *
 * The value of a {@code ListProperty} can be get and set with {@link #get()},
 * {@link #getValue()}, {@link #set(Object)}, and {@link #setValue(ObservableList)}.
 *
 * A property can be bound and unbound unidirectional with
 * {@link #bind(ObservableValue)} and {@link #unbind()}. Bidirectional bindings
 * can be created and removed with {@link #bindBidirectional(swingfx.beans.property.Property)} and
 * {@link #unbindBidirectional(swingfx.beans.property.Property)}.
 *
 * The context of a {@code ListProperty} can be read with {@link #getBean()}
 * and {@link #getName()}.
 *
 * @see ObservableList
 * @see ObservableListValue
 * @see WritableListValue
 * @see swingfx.beans.property.ReadOnlyListProperty
 * @see swingfx.beans.property.Property
 *
 * @param <E> the type of the {@code List} elements
 * @since JavaFX 2.1
 */
public abstract class ListProperty<E> extends ReadOnlyListProperty<E> implements
        swingfx.beans.property.Property<ObservableList<E>>, WritableListValue<E> {
    /**
     * {@inheritDoc}
     */
    @Override
    public void setValue(ObservableList<E> v) {
        set(v);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void bindBidirectional(swingfx.beans.property.Property<ObservableList<E>> other) {
        Bindings.bindBidirectional(this, other);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void unbindBidirectional(Property<ObservableList<E>> other) {
        Bindings.unbindBidirectional(this, other);
    }

    /**
     * Returns a string representation of this {@code ListProperty} object.
     * @return a string representation of this {@code ListProperty} object.
     */
    @Override
    public String toString() {
        final Object bean = getBean();
        final String name = getName();
        final StringBuilder result = new StringBuilder(
                "ListProperty [");
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
