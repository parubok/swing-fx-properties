/*
 * Copyright (c) 2010, 2013, Oracle and/or its affiliates. All rights reserved.
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

package swingfx.beans.binding;

import com.sun.swingfx.binding.StringFormatter;
import swingfx.beans.value.ObservableObjectValue;
import swingfx.collections.FXCollections;
import swingfx.collections.ObservableList;

import com.sun.swingfx.collections.annotations.ReturnsUnmodifiableCollection;
import java.util.Locale;

/**
 * A {@code ObjectExpression} is a
 * {@link ObservableObjectValue} plus additional convenience
 * methods to generate bindings in a fluent style.
 * <p>
 * A concrete sub-class of {@code ObjectExpression} has to implement the method
 * {@link ObservableObjectValue#get()}, which provides the
 * actual value of this expression.
 * @since JavaFX 2.0
 */
public abstract class ObjectExpression<T> implements ObservableObjectValue<T> {

    @Override
    public T getValue() {
        return get();
    }

    /**
     * Returns an {@code ObjectExpression} that wraps an
     * {@link ObservableObjectValue}. If the
     * {@code ObservableObjectValue} is already an {@code ObjectExpression}, it
     * will be returned. Otherwise a new
     * {@link swingfx.beans.binding.ObjectBinding} is created that is bound to
     * the {@code ObservableObjectValue}.
     *
     * @param value
     *            The source {@code ObservableObjectValue}
     * @return A {@code ObjectExpression} that wraps the
     *         {@code ObservableObjectValue} if necessary
     * @throws NullPointerException
     *             if {@code value} is {@code null}
     */
    public static <T> ObjectExpression<T> objectExpression(
            final ObservableObjectValue<T> value) {
        if (value == null) {
            throw new NullPointerException("Value must be specified.");
        }
        return value instanceof ObjectExpression ? (ObjectExpression<T>) value
                : new ObjectBinding<T>() {
                    {
                        super.bind(value);
                    }

                    @Override
                    public void dispose() {
                        super.unbind(value);
                    }

                    @Override
                    protected T computeValue() {
                        return value.get();
                    }

                    @Override
                    @ReturnsUnmodifiableCollection
                    public ObservableList<ObservableObjectValue<T>> getDependencies() {
                        return FXCollections.singletonObservableList(value);
                    }
                };
    }

    /**
     * Creates a new {@code BooleanExpression} that holds {@code true} if this and
     * another {@link ObservableObjectValue} are equal.
     *
     * @param other
     *            the other {@code ObservableObjectValue}
     * @return the new {@code BooleanExpression}
     * @throws NullPointerException
     *             if {@code other} is {@code null}
     */
    public swingfx.beans.binding.BooleanBinding isEqualTo(final ObservableObjectValue<?> other) {
        return swingfx.beans.binding.Bindings.equal(this, other);
    }

    /**
     * Creates a new {@code BooleanExpression} that holds {@code true} if this
     * {@code ObjectExpression} is equal to a constant value.
     *
     * @param other
     *            the constant value
     * @return the new {@code BooleanExpression}
     */
    public swingfx.beans.binding.BooleanBinding isEqualTo(final Object other) {
        return swingfx.beans.binding.Bindings.equal(this, other);
    }

    /**
     * Creates a new {@code BooleanExpression} that holds {@code true} if this and
     * another {@link ObservableObjectValue} are not equal.
     *
     * @param other
     *            the other {@code ObservableObjectValue}
     * @return the new {@code BooleanExpression}
     * @throws NullPointerException
     *             if {@code other} is {@code null}
     */
    public swingfx.beans.binding.BooleanBinding isNotEqualTo(final ObservableObjectValue<?> other) {
        return swingfx.beans.binding.Bindings.notEqual(this, other);
    }

    /**
     * Creates a new {@code BooleanExpression} that holds {@code true} if this
     * {@code ObjectExpression} is not equal to a constant value.
     *
     * @param other
     *            the constant value
     * @return the new {@code BooleanExpression}
     */
    public swingfx.beans.binding.BooleanBinding isNotEqualTo(final Object other) {
        return swingfx.beans.binding.Bindings.notEqual(this, other);
    }

    /**
     * Creates a new {@link swingfx.beans.binding.BooleanBinding} that holds {@code true} if this
     * {@code ObjectExpression} is {@code null}.
     *
     * @return the new {@code BooleanBinding}
     */
    public swingfx.beans.binding.BooleanBinding isNull() {
        return swingfx.beans.binding.Bindings.isNull(this);
    }

    /**
     * Creates a new {@link swingfx.beans.binding.BooleanBinding} that holds {@code true} if this
     * {@code ObjectExpression} is not {@code null}.
     *
     * @return the new {@code BooleanBinding}
     */
    public BooleanBinding isNotNull() {
        return swingfx.beans.binding.Bindings.isNotNull(this);
    }

    /**
     * Creates a {@link swingfx.beans.binding.StringBinding} that holds the value
     * of this {@code ObjectExpression} turned into a {@code String}. If the
     * value of this {@code ObjectExpression} changes, the value of the
     * {@code StringBinding} will be updated automatically.
     *
     * @return the new {@code StringBinding}
     * @since JavaFX 8.0
     */
    public swingfx.beans.binding.StringBinding asString() {
        return (swingfx.beans.binding.StringBinding) StringFormatter.convert(this);
    }

    /**
     * Creates a {@link swingfx.beans.binding.StringBinding} that holds the value
     * of the {@code ObjectExpression} turned into a {@code String}. If the
     * value of this {@code ObjectExpression} changes, the value of the
     * {@code StringBinding} will be updated automatically.
     * <p>
     * The result is formatted according to the formatting {@code String}. See
     * {@code java.util.Formatter} for formatting rules.
     *
     * @param format
     *            the formatting {@code String}
     * @return the new {@code StringBinding}
     * @since JavaFX 8.0
     */
    public swingfx.beans.binding.StringBinding asString(String format) {
        return (swingfx.beans.binding.StringBinding) swingfx.beans.binding.Bindings.format(format, this);
    }

    /**
     * Creates a {@link swingfx.beans.binding.StringBinding} that holds the value
     * of the {@code NumberExpression} turned into a {@code String}. If the
     * value of this {@code NumberExpression} changes, the value of the
     * {@code StringBinding} will be updated automatically.
     * <p>
     * The result is formatted according to the formatting {@code String} and
     * the passed in {@code Locale}. See {@code java.util.Formatter} for
     * formatting rules. See {@code java.util.Locale} for details on
     * {@code Locale}.
     *
     * @param format
     *            the formatting {@code String}
     * @return the new {@code StringBinding}
     * @since JavaFX 8.0
     */
    public swingfx.beans.binding.StringBinding asString(Locale locale, String format) {
        return (StringBinding) Bindings.format(locale, format, this);
    }
}
