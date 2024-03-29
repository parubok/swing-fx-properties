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

package io.github.parubok.swingfx.beans.property;

import io.github.parubok.swingfx.beans.binding.Bindings;
import io.github.parubok.swingfx.beans.value.WritableSetValue;
import io.github.parubok.swingfx.beans.value.ObservableSetValue;
import io.github.parubok.swingfx.beans.value.ObservableValue;
import io.github.parubok.swingfx.collections.ObservableSet;

/**
 * This class provides a full implementation of a {@link Property} wrapping a
 * {@link ObservableSet}.
 *
 * The value of a {@code SetProperty} can be get and set with {@link #get()},
 * {@link #getValue()}, {@link #set(Object)}, and {@link #setValue(ObservableSet)}.
 *
 * A property can be bound and unbound unidirectional with
 * {@link #bind(ObservableValue)} and {@link #unbind()}. Bidirectional bindings
 * can be created and removed with {@link #bindBidirectional(Property)} and
 * {@link #unbindBidirectional(Property)}.
 *
 * The context of a {@code SetProperty} can be read with {@link #getBean()}
 * and {@link #getName()}.
 *
 * @see ObservableSet
 * @see ObservableSetValue
 * @see WritableSetValue
 * @see ReadOnlySetProperty
 * @see Property
 *
 * @param <E> the type of the {@code Set} elements
 * @since JavaFX 2.1
 */
public abstract class SetProperty<E> extends ReadOnlySetProperty<E> implements
        Property<ObservableSet<E>>, WritableSetValue<E> {
    /**
     * {@inheritDoc}
     */
    @Override
    public void setValue(ObservableSet<E> v) {
        set(v);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void bindBidirectional(Property<ObservableSet<E>> other) {
        Bindings.bindBidirectional(this, other);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void unbindBidirectional(Property<ObservableSet<E>> other) {
        Bindings.unbindBidirectional(this, other);
    }

    /**
     * Returns a string representation of this {@code SetProperty} object.
     * @return a string representation of this {@code SetProperty} object.
     */
    @Override
    public String toString() {
        final Object bean = getBean();
        final String name = getName();
        final StringBuilder result = new StringBuilder(
                "SetProperty [");
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
