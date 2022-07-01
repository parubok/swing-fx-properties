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

package io.github.parubok.swingfx.beans.binding;

import io.github.parubok.swingfx.beans.value.ObservableStringValue;
import io.github.parubok.swingfx.beans.value.ObservableValue;

import io.github.parubok.com.sun.swingfx.binding.StringFormatter;

/**
 * A {@code StringExpression} is a
 * {@link ObservableStringValue} plus additional convenience
 * methods to generate bindings in a fluent style.
 * <p>
 * A concrete sub-class of {@code StringExpression} has to implement the method
 * {@link ObservableStringValue#get()}, which provides the
 * actual value of this expression.
 * <p>
 * Note: all implementation of {@link BooleanBinding}
 * returned by the comparisons in this class consider a {@code String} that is
 * {@code null} equal to an empty {@code String}.
 * @since JavaFX 2.0
 */
public abstract class StringExpression implements ObservableStringValue {

    @Override
    public String getValue() {
        return get();
    }

    /**
     * Returns usually the value of this {@code StringExpression}. Only if the
     * value is {@code null} an empty {@code String} is returned instead.
     *
     * @return the value of this {@code StringExpression} or the empty
     *         {@code String}
     */
    public final String getValueSafe() {
        final String value = get();
        return value == null ? "" : value;
    }

    /**
     * Returns a {@code StringExpression} that wraps a
     * {@link ObservableValue}. If the
     * {@code ObservableValue} is already a {@code StringExpression}, it will be
     * returned. Otherwise a new {@link StringBinding} is
     * created that holds the value of the {@code ObservableValue} converted to
     * a {@code String}.
     *
     * @param value
     *            The source {@code ObservableValue}
     * @return A {@code StringExpression} that wraps the {@code ObservableValue}
     *         if necessary
     * @throws NullPointerException
     *             if {@code value} is {@code null}
     */
    public static StringExpression stringExpression(
            final ObservableValue<?> value) {
        if (value == null) {
            throw new NullPointerException("Value must be specified.");
        }
        return StringFormatter.convert(value);
    }

    /**
     * Returns a {@code StringExpression} that holds the value of this
     * {@code StringExpression} concatenated with another {@code Object}.
     * <p>
     * If the value of this {@code StringExpression} changes, the value of the
     * resulting {@code StringExpression} is updated automatically. Also if the
     * other {@code Object} is an implementation of
     * {@link ObservableValue}, changes in the other
     * {@code Object} are reflected automatically in the resulting
     * {@code StringExpression}.
     *
     * @param other
     *            the other {@code Object}
     * @return the new {@code StringExpression}
     */
    public StringExpression concat(Object other) {
        return Bindings.concat(this, other);
    }

    /**
     * Creates a new {@link BooleanBinding} that holds {@code true}
     * if this and another {@link ObservableStringValue} are
     * equal.
     * <p>
     * Note: In this comparison a {@code String} that is {@code null} is
     * considered equal to an empty {@code String}.
     *
     * @param other
     *            the constant value
     * @return the new {@code BooleanBinding}
     */
    public BooleanBinding isEqualTo(final ObservableStringValue other) {
        return Bindings.equal(this, other);
    }

    /**
     * Creates a new {@link BooleanBinding} that holds {@code true}
     * if this {@code StringExpression} is equal to a constant value.
     * <p>
     * Note: In this comparison a {@code String} that is {@code null} is
     * considered equal to an empty {@code String}.
     *
     * @param other
     *            the constant value
     * @return the new {@code BooleanBinding}
     */
    public BooleanBinding isEqualTo(final String other) {
        return Bindings.equal(this, other);
    }

    /**
     * Creates a new {@link BooleanBinding} that holds {@code true}
     * if this and another {@link ObservableStringValue} are
     * not equal.
     * <p>
     * Note: In this comparison a {@code String} that is {@code null} is
     * considered equal to an empty {@code String}.
     *
     * @param other
     *            the constant value
     * @return the new {@code BooleanBinding}
     */
    public BooleanBinding isNotEqualTo(final ObservableStringValue other) {
        return Bindings.notEqual(this, other);
    }

    /**
     * Creates a new {@link BooleanBinding} that holds {@code true}
     * if this {@code StringExpression} is not equal to a constant value.
     * <p>
     * Note: In this comparison a {@code String} that is {@code null} is
     * considered equal to an empty {@code String}.
     *
     * @param other
     *            the constant value
     * @return the new {@code BooleanBinding}
     */
    public BooleanBinding isNotEqualTo(final String other) {
        return Bindings.notEqual(this, other);
    }

    /**
     * Creates a new {@link BooleanBinding} that holds {@code true}
     * if this and another {@link ObservableStringValue} are
     * equal ignoring case.
     * <p>
     * Note: In this comparison a {@code String} that is {@code null} is
     * considered equal to an empty {@code String}.
     *
     * @param other
     *            the second {@code ObservableStringValue}
     * @return the new {@code BooleanBinding}
     */
    public BooleanBinding isEqualToIgnoreCase(final ObservableStringValue other) {
        return Bindings.equalIgnoreCase(this, other);
    }

    /**
     * Creates a new {@link BooleanBinding} that holds {@code true}
     * if this {@code StringExpression} is equal to a constant value ignoring
     * case.
     * <p>
     * Note: In this comparison a {@code String} that is {@code null} is
     * considered equal to an empty {@code String}.
     *
     * @param other
     *            the constant value
     * @return the new {@code BooleanBinding}
     */
    public BooleanBinding isEqualToIgnoreCase(final String other) {
        return Bindings.equalIgnoreCase(this, other);
    }

    /**
     * Creates a new {@link BooleanBinding} that holds {@code true}
     * if this and another {@link ObservableStringValue} are
     * not equal ignoring case.
     * <p>
     * Note: In this comparison a {@code String} that is {@code null} is
     * considered equal to an empty {@code String}.
     *
     * @param other
     *            the second {@code ObservableStringValue}
     * @return the new {@code BooleanBinding}
     */
    public BooleanBinding isNotEqualToIgnoreCase(
            final ObservableStringValue other) {
        return Bindings.notEqualIgnoreCase(this, other);
    }

    /**
     * Creates a new {@link BooleanBinding} that holds {@code true}
     * if this {@code StringExpression} is not equal to a constant value
     * ignoring case.
     * <p>
     * Note: In this comparison a {@code String} that is {@code null} is
     * considered equal to an empty {@code String}.
     *
     * @param other
     *            the constant value
     * @return the new {@code BooleanBinding}
     */
    public BooleanBinding isNotEqualToIgnoreCase(final String other) {
        return Bindings.notEqualIgnoreCase(this, other);
    }

    /**
     * Creates a new {@link BooleanBinding} that holds {@code true}
     * if this {@code StringExpression} is greater than another
     * {@link ObservableStringValue}.
     * <p>
     * Note: In this comparison a {@code String} that is {@code null} is
     * considered equal to an empty {@code String}.
     *
     * @param other
     *            the second {@code ObservableStringValue}
     * @return the new {@code BooleanBinding}
     */
    public BooleanBinding greaterThan(final ObservableStringValue other) {
        return Bindings.greaterThan(this, other);
    }

    /**
     * Creates a new {@link BooleanBinding} that holds {@code true}
     * if this {@code StringExpression} is greater than a constant value.
     * <p>
     * Note: In this comparison a {@code String} that is {@code null} is
     * considered equal to an empty {@code String}.
     *
     * @param other
     *            the constant value
     * @return the new {@code BooleanBinding}
     */
    public BooleanBinding greaterThan(final String other) {
        return Bindings.greaterThan(this, other);
    }

    /**
     * Creates a new {@link BooleanBinding} that holds {@code true}
     * if this {@code StringExpression} is less than another
     * {@link ObservableStringValue}.
     * <p>
     * Note: In this comparison a {@code String} that is {@code null} is
     * considered equal to an empty {@code String}.
     *
     * @param other
     *            the second {@code ObservableStringValue}
     * @return the new {@code BooleanBinding}
     */
    public BooleanBinding lessThan(final ObservableStringValue other) {
        return Bindings.lessThan(this, other);
    }

    /**
     * Creates a new {@link BooleanBinding} that holds {@code true}
     * if this {@code StringExpression} is less than a constant value.
     * <p>
     * Note: In this comparison a {@code String} that is {@code null} is
     * considered equal to an empty {@code String}.
     *
     * @param other
     *            the constant value
     * @return the new {@code BooleanBinding}
     */
    public BooleanBinding lessThan(final String other) {
        return Bindings.lessThan(this, other);
    }

    /**
     * Creates a new {@link BooleanBinding} that holds {@code true}
     * if this {@code StringExpression} is greater than or equal to another
     * {@link ObservableStringValue}.
     * <p>
     * Note: In this comparison a {@code String} that is {@code null} is
     * considered equal to an empty {@code String}.
     *
     * @param other
     *            the second {@code ObservableStringValue}
     * @return the new {@code BooleanBinding}
     */
    public BooleanBinding greaterThanOrEqualTo(final ObservableStringValue other) {
        return Bindings.greaterThanOrEqual(this, other);
    }

    /**
     * Creates a new {@link BooleanBinding} that holds {@code true}
     * if this {@code StringExpression} is greater than or equal to a constant
     * value.
     * <p>
     * Note: In this comparison a {@code String} that is {@code null} is
     * considered equal to an empty {@code String}.
     *
     * @param other
     *            the constant value
     * @return the new {@code BooleanBinding}
     */
    public BooleanBinding greaterThanOrEqualTo(final String other) {
        return Bindings.greaterThanOrEqual(this, other);
    }

    /**
     * Creates a new {@link BooleanBinding} that holds {@code true}
     * if this {@code StringExpression} is less than or equal to another
     * {@link ObservableStringValue}.
     * <p>
     * Note: In this comparison a {@code String} that is {@code null} is
     * considered equal to an empty {@code String}.
     *
     * @param other
     *            the second {@code ObservableStringValue}
     * @return the new {@code BooleanBinding}
     */
    public BooleanBinding lessThanOrEqualTo(final ObservableStringValue other) {
        return Bindings.lessThanOrEqual(this, other);
    }

    /**
     * Creates a new {@link BooleanBinding} that holds {@code true}
     * if this {@code StringExpression} is less than or equal to a constant
     * value.
     * <p>
     * Note: In this comparison a {@code String} that is {@code null} is
     * considered equal to an empty {@code String}.
     *
     * @param other
     *            the constant value
     * @return the new {@code BooleanBinding}
     */
    public BooleanBinding lessThanOrEqualTo(final String other) {
        return Bindings.lessThanOrEqual(this, other);
    }

    /**
     * Creates a new {@link BooleanBinding} that holds {@code true} if this
     * {@code StringExpression} is {@code null}.
     *
     * @return the new {@code BooleanBinding}
     */
    public BooleanBinding isNull() {
        return Bindings.isNull(this);
    }

    /**
     * Creates a new {@link BooleanBinding} that holds {@code true} if this
     * {@code StringExpression} is not {@code null}.
     *
     * @return the new {@code BooleanBinding}
     */
    public BooleanBinding isNotNull() {
        return Bindings.isNotNull(this);
    }

    /**
     * Creates a new {@link IntegerBinding} that holds the length of this
     * {@code StringExpression}.
     * <p>
     * Note: If the value of this {@code StringExpression} is {@code null},
     * the length is considered to be {@code 0}.
     *
     * @return the new {@code IntegerBinding}
     * @since JavaFX 8.0
     */
    public IntegerBinding length() {
        return Bindings.length(this);
    }

    /**
     * Creates a new {@link BooleanBinding} that holds {@code true} if this
     * {@code StringExpression} is empty.
     * <p>
     * Note: If the value of this {@code StringExpression} is {@code null},
     * it is considered to be empty.
     *
     * @return the new {@code BooleanBinding}
     * @since JavaFX 8.0
     */
    public BooleanBinding isEmpty() {
        return Bindings.isEmpty(this);
    }

    /**
     * Creates a new {@link BooleanBinding} that holds {@code true} if this
     * {@code StringExpression} is not empty.
     * <p>
     * Note: If the value of this {@code StringExpression} is {@code null},
     * it is considered to be empty.
     *
     * @return the new {@code BooleanBinding}
     * @since JavaFX 8.0
     */
    public BooleanBinding isNotEmpty() {
        return Bindings.isNotEmpty(this);
    }
}
