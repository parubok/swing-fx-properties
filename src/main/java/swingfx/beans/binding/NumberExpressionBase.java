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

import java.util.Locale;

import swingfx.beans.value.ObservableDoubleValue;
import swingfx.beans.value.ObservableFloatValue;
import swingfx.beans.value.ObservableIntegerValue;
import swingfx.beans.value.ObservableLongValue;
import swingfx.beans.value.ObservableNumberValue;

import com.sun.swingfx.binding.StringFormatter;

/**
 * A {@code NumberExpressionBase} contains convenience methods to generate bindings in a fluent style,
 * that are common to all NumberExpression subclasses.
 * <p>
 * NumberExpressionBase serves as a place for common code of specific NumberExpression subclasses for the
 * specific number type.
 * @see swingfx.beans.binding.IntegerExpression
 * @see swingfx.beans.binding.LongExpression
 * @see swingfx.beans.binding.FloatExpression
 * @see swingfx.beans.binding.DoubleExpression
 * @since JavaFX 2.0
 */
public abstract class NumberExpressionBase implements NumberExpression {

    /**
     * Returns an {@code NumberExpressionBase} that wraps a
     * {@link ObservableNumberValue}. If the
     * {@code ObservableNumberValue} is already an instance of
     * {@code NumberExpressionBase}, it will be returned. Otherwise a new
     * {@link swingfx.beans.binding.NumberBinding} is created that is bound to
     * the {@code ObservableNumberValue}.
     *
     * @param value
     *            The source {@code ObservableNumberValue}
     * @return An {@code NumberExpressionBase} that wraps the
     *         {@code ObservableNumberValue} if necessary
     * @throws NullPointerException
     *             if {@code value} is {@code null}
     */
    public static <S extends Number> NumberExpressionBase numberExpression(
            final ObservableNumberValue value) {
        if (value == null) {
            throw new NullPointerException("Value must be specified.");
        }
        NumberExpressionBase result = (NumberExpressionBase) ((value instanceof NumberExpressionBase) ? value
                : (value instanceof ObservableIntegerValue) ? IntegerExpression
                        .integerExpression((ObservableIntegerValue) value)
                        : (value instanceof ObservableDoubleValue) ? DoubleExpression
                                .doubleExpression((ObservableDoubleValue) value)
                                : (value instanceof ObservableFloatValue) ? FloatExpression
                                        .floatExpression((ObservableFloatValue) value)
                                        : (value instanceof ObservableLongValue) ? LongExpression
                                                .longExpression((ObservableLongValue) value)
                                                : null);
        if (result != null) {
            return result;
        } else {
            throw new IllegalArgumentException("Unsupported Type");
        }
    }

    @Override
    public swingfx.beans.binding.NumberBinding add(final ObservableNumberValue other) {
        return swingfx.beans.binding.Bindings.add(this, other);
    }

    @Override
    public swingfx.beans.binding.NumberBinding subtract(final ObservableNumberValue other) {
        return swingfx.beans.binding.Bindings.subtract(this, other);
    }

    @Override
    public swingfx.beans.binding.NumberBinding multiply(final ObservableNumberValue other) {
        return swingfx.beans.binding.Bindings.multiply(this, other);
    }

    @Override
    public NumberBinding divide(final ObservableNumberValue other) {
        return swingfx.beans.binding.Bindings.divide(this, other);
    }

    // ===============================================================
    // IsEqualTo

    @Override
    public swingfx.beans.binding.BooleanBinding isEqualTo(final ObservableNumberValue other) {
        return swingfx.beans.binding.Bindings.equal(this, other);
    }

    @Override
    public swingfx.beans.binding.BooleanBinding isEqualTo(final ObservableNumberValue other,
                                                          double epsilon) {
        return swingfx.beans.binding.Bindings.equal(this, other, epsilon);
    }

    @Override
    public swingfx.beans.binding.BooleanBinding isEqualTo(final double other, double epsilon) {
        return swingfx.beans.binding.Bindings.equal(this, other, epsilon);
    }

    @Override
    public swingfx.beans.binding.BooleanBinding isEqualTo(final float other, double epsilon) {
        return swingfx.beans.binding.Bindings.equal(this, other, epsilon);
    }

    @Override
    public swingfx.beans.binding.BooleanBinding isEqualTo(final long other) {
        return swingfx.beans.binding.Bindings.equal(this, other);
    }

    @Override
    public swingfx.beans.binding.BooleanBinding isEqualTo(final long other, double epsilon) {
        return swingfx.beans.binding.Bindings.equal(this, other, epsilon);
    }

    @Override
    public swingfx.beans.binding.BooleanBinding isEqualTo(final int other) {
        return swingfx.beans.binding.Bindings.equal(this, other);
    }

    @Override
    public swingfx.beans.binding.BooleanBinding isEqualTo(final int other, double epsilon) {
        return swingfx.beans.binding.Bindings.equal(this, other, epsilon);
    }

    // ===============================================================
    // IsNotEqualTo

    @Override
    public swingfx.beans.binding.BooleanBinding isNotEqualTo(final ObservableNumberValue other) {
        return swingfx.beans.binding.Bindings.notEqual(this, other);
    }

    @Override
    public swingfx.beans.binding.BooleanBinding isNotEqualTo(final ObservableNumberValue other,
                                                             double epsilon) {
        return swingfx.beans.binding.Bindings.notEqual(this, other, epsilon);
    }

    @Override
    public swingfx.beans.binding.BooleanBinding isNotEqualTo(final double other, double epsilon) {
        return swingfx.beans.binding.Bindings.notEqual(this, other, epsilon);
    }

    @Override
    public swingfx.beans.binding.BooleanBinding isNotEqualTo(final float other, double epsilon) {
        return swingfx.beans.binding.Bindings.notEqual(this, other, epsilon);
    }

    @Override
    public swingfx.beans.binding.BooleanBinding isNotEqualTo(final long other) {
        return swingfx.beans.binding.Bindings.notEqual(this, other);
    }

    @Override
    public swingfx.beans.binding.BooleanBinding isNotEqualTo(final long other, double epsilon) {
        return swingfx.beans.binding.Bindings.notEqual(this, other, epsilon);
    }

    @Override
    public swingfx.beans.binding.BooleanBinding isNotEqualTo(final int other) {
        return swingfx.beans.binding.Bindings.notEqual(this, other);
    }

    @Override
    public swingfx.beans.binding.BooleanBinding isNotEqualTo(final int other, double epsilon) {
        return swingfx.beans.binding.Bindings.notEqual(this, other, epsilon);
    }

    // ===============================================================
    // IsGreaterThan

    @Override
    public swingfx.beans.binding.BooleanBinding greaterThan(final ObservableNumberValue other) {
        return swingfx.beans.binding.Bindings.greaterThan(this, other);
    }

    @Override
    public swingfx.beans.binding.BooleanBinding greaterThan(final double other) {
        return swingfx.beans.binding.Bindings.greaterThan(this, other);
    }

    @Override
    public swingfx.beans.binding.BooleanBinding greaterThan(final float other) {
        return swingfx.beans.binding.Bindings.greaterThan(this, other);
    }

    @Override
    public swingfx.beans.binding.BooleanBinding greaterThan(final long other) {
        return swingfx.beans.binding.Bindings.greaterThan(this, other);
    }

    @Override
    public swingfx.beans.binding.BooleanBinding greaterThan(final int other) {
        return swingfx.beans.binding.Bindings.greaterThan(this, other);
    }

    // ===============================================================
    // IsLesserThan

    @Override
    public swingfx.beans.binding.BooleanBinding lessThan(final ObservableNumberValue other) {
        return swingfx.beans.binding.Bindings.lessThan(this, other);
    }

    @Override
    public swingfx.beans.binding.BooleanBinding lessThan(final double other) {
        return swingfx.beans.binding.Bindings.lessThan(this, other);
    }

    @Override
    public swingfx.beans.binding.BooleanBinding lessThan(final float other) {
        return swingfx.beans.binding.Bindings.lessThan(this, other);
    }

    @Override
    public swingfx.beans.binding.BooleanBinding lessThan(final long other) {
        return swingfx.beans.binding.Bindings.lessThan(this, other);
    }

    @Override
    public swingfx.beans.binding.BooleanBinding lessThan(final int other) {
        return swingfx.beans.binding.Bindings.lessThan(this, other);
    }

    // ===============================================================
    // IsGreaterThanOrEqualTo

    @Override
    public swingfx.beans.binding.BooleanBinding greaterThanOrEqualTo(final ObservableNumberValue other) {
        return swingfx.beans.binding.Bindings.greaterThanOrEqual(this, other);
    }

    @Override
    public swingfx.beans.binding.BooleanBinding greaterThanOrEqualTo(final double other) {
        return swingfx.beans.binding.Bindings.greaterThanOrEqual(this, other);
    }

    @Override
    public swingfx.beans.binding.BooleanBinding greaterThanOrEqualTo(final float other) {
        return swingfx.beans.binding.Bindings.greaterThanOrEqual(this, other);
    }

    @Override
    public swingfx.beans.binding.BooleanBinding greaterThanOrEqualTo(final long other) {
        return swingfx.beans.binding.Bindings.greaterThanOrEqual(this, other);
    }

    @Override
    public swingfx.beans.binding.BooleanBinding greaterThanOrEqualTo(final int other) {
        return swingfx.beans.binding.Bindings.greaterThanOrEqual(this, other);
    }

    // ===============================================================
    // IsLessThanOrEqualTo

    @Override
    public swingfx.beans.binding.BooleanBinding lessThanOrEqualTo(final ObservableNumberValue other) {
        return swingfx.beans.binding.Bindings.lessThanOrEqual(this, other);
    }

    @Override
    public swingfx.beans.binding.BooleanBinding lessThanOrEqualTo(final double other) {
        return swingfx.beans.binding.Bindings.lessThanOrEqual(this, other);
    }

    @Override
    public swingfx.beans.binding.BooleanBinding lessThanOrEqualTo(final float other) {
        return swingfx.beans.binding.Bindings.lessThanOrEqual(this, other);
    }

    @Override
    public swingfx.beans.binding.BooleanBinding lessThanOrEqualTo(final long other) {
        return swingfx.beans.binding.Bindings.lessThanOrEqual(this, other);
    }

    @Override
    public BooleanBinding lessThanOrEqualTo(final int other) {
        return swingfx.beans.binding.Bindings.lessThanOrEqual(this, other);
    }

    // ===============================================================
    // String conversions

    @Override
    public swingfx.beans.binding.StringBinding asString() {
        return (swingfx.beans.binding.StringBinding) StringFormatter.convert(this);
    }

    @Override
    public swingfx.beans.binding.StringBinding asString(String format) {
        return (swingfx.beans.binding.StringBinding) swingfx.beans.binding.Bindings.format(format, this);
    }

    @Override
    public swingfx.beans.binding.StringBinding asString(Locale locale, String format) {
        return (StringBinding) Bindings.format(locale, format, this);
    }

}
