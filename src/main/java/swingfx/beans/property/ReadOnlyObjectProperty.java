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

import swingfx.beans.binding.ObjectExpression;
import swingfx.beans.value.ObservableObjectValue;
import swingfx.collections.ObservableList;
import swingfx.collections.ObservableMap;
import swingfx.collections.ObservableSet;

/**
 * Super class for all readonly properties wrapping an arbitrary {@code Object}.
 *
 * For specialized implementations for {@link ObservableList}, {@link ObservableSet} and
 * {@link ObservableMap} that also report changes inside the collections, see
 * {@link ReadOnlyListProperty}, {@link ReadOnlySetProperty} and {@link ReadOnlyMapProperty}, respectively.
 *
 * @see ObservableObjectValue
 * @see ObjectExpression
 * @see swingfx.beans.property.ReadOnlyProperty
 *
 *
 * @param <T>
 *            the type of the wrapped {@code Object}
 * @since JavaFX 2.0
 */
public abstract class ReadOnlyObjectProperty<T> extends ObjectExpression<T>
        implements ReadOnlyProperty<T> {

    /**
     * The constructor of {@code ReadOnlyObjectProperty}.
     */
    public ReadOnlyObjectProperty() {
    }

    /**
     * Returns a string representation of this {@code ReadOnlyObjectProperty} object.
     * @return a string representation of this {@code ReadOnlyObjectProperty} object.
     */
    @Override
    public String toString() {
        final Object bean = getBean();
        final String name = getName();
        final StringBuilder result = new StringBuilder(
                "ReadOnlyObjectProperty [");
        if (bean != null) {
            result.append("bean: ").append(bean).append(", ");
        }
        if ((name != null) && !name.equals("")) {
            result.append("name: ").append(name).append(", ");
        }
        result.append("value: ").append(get()).append("]");
        return result.toString();
    }

}
