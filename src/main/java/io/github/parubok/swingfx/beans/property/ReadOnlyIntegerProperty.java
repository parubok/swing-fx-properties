/*
 * Copyright (c) 2011, 2014, Oracle and/or its affiliates. All rights reserved.
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

import io.github.parubok.swingfx.beans.InvalidationListener;
import io.github.parubok.swingfx.beans.WeakInvalidationListener;
import io.github.parubok.swingfx.beans.binding.IntegerExpression;
import io.github.parubok.swingfx.beans.value.ObservableIntegerValue;

/**
 * Super class for all readonly properties wrapping an {@code int}.
 *
 * @see ObservableIntegerValue
 * @see IntegerExpression
 * @see ReadOnlyProperty
 *
 * @since JavaFX 2.0
 */
public abstract class ReadOnlyIntegerProperty extends IntegerExpression
        implements ReadOnlyProperty<Number> {

    /**
     * The constructor of {@code ReadOnlyIntegerProperty}.
     */
    public ReadOnlyIntegerProperty() {
    }


    /**
     * Returns a string representation of this {@code ReadOnlyIntegerProperty} object.
     * @return a string representation of this {@code ReadOnlyIntegerProperty} object.
     */
    @Override
    public String toString() {
        final Object bean = getBean();
        final String name = getName();
        final StringBuilder result = new StringBuilder(
                "ReadOnlyIntegerProperty [");
        if (bean != null) {
            result.append("bean: ").append(bean).append(", ");
        }
        if ((name != null) && !name.equals("")) {
            result.append("name: ").append(name).append(", ");
        }
        result.append("value: ").append(get()).append("]");
        return result.toString();
    }

    /**
     * Returns a {@code ReadOnlyIntegerProperty} that wraps a
     * {@link ReadOnlyProperty}. If the
     * {@code ReadOnlyProperty} is already a {@code ReadOnlyIntegerProperty}, it
     * will be returned. Otherwise a new
     * {@code ReadOnlyIntegerProperty} is created that is bound to
     * the {@code ReadOnlyProperty}.
     *
     * Note: null values will be interpreted as 0
     *
     * @param property
     *            The source {@code ReadOnlyProperty}
     * @return A {@code ReadOnlyIntegerProperty} that wraps the
     *         {@code ReadOnlyProperty} if necessary
     * @throws NullPointerException
     *             if {@code property} is {@code null}
     * @since JavaFX 8.0
     */
    public static <T extends Number> ReadOnlyIntegerProperty readOnlyIntegerProperty(final ReadOnlyProperty<T> property) {
        if (property == null) {
            throw new NullPointerException("Property cannot be null");
        }

        return property instanceof ReadOnlyIntegerProperty ? (ReadOnlyIntegerProperty) property:
           new ReadOnlyIntegerPropertyBase() {
            private boolean valid = true;
            private final InvalidationListener listener = observable -> {
                if (valid) {
                    valid = false;
                    fireValueChangedEvent();
                }
            };

            {
                property.addListener(new WeakInvalidationListener(listener));
            }

            @Override
            public int get() {
                valid = true;
                final T value = property.getValue();
                return value == null ? 0 : value.intValue();
            }

            @Override
            public Object getBean() {
                return null; // Virtual property, no bean
            }

            @Override
            public String getName() {
                return property.getName();
            }
        };
    }

    /**
     * Creates a {@link ReadOnlyObjectProperty} that holds the value
     * of this {@code ReadOnlyIntegerProperty}. If the
     * value of this {@code ReadOnlyIntegerProperty} changes, the value of the
     * {@code ReadOnlyObjectProperty} will be updated automatically.
     *
     * @return the new {@code ReadOnlyObjectProperty}
     * @since JavaFX 8.0
     */
    @Override
    public ReadOnlyObjectProperty<Integer> asObject() {
        return new ReadOnlyObjectPropertyBase<Integer>() {

            private boolean valid = true;
            private final InvalidationListener listener = observable -> {
                if (valid) {
                    valid = false;
                    fireValueChangedEvent();
                }
            };

            {
                ReadOnlyIntegerProperty.this.addListener(new WeakInvalidationListener(listener));
            }

            @Override
            public Object getBean() {
                return null; // Virtual property, does not exist on a bean
            }

            @Override
            public String getName() {
                return ReadOnlyIntegerProperty.this.getName();
            }

            @Override
            public Integer get() {
                valid = true;
                return ReadOnlyIntegerProperty.this.getValue();
            }
        };
    };

}
