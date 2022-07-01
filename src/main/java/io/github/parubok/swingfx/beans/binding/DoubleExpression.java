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

import io.github.parubok.swingfx.beans.property.ObjectProperty;
import io.github.parubok.swingfx.beans.value.ObservableDoubleValue;
import io.github.parubok.swingfx.beans.value.ObservableNumberValue;
import io.github.parubok.swingfx.collections.FXCollections;
import io.github.parubok.swingfx.collections.ObservableList;
import io.github.parubok.com.sun.swingfx.collections.annotations.ReturnsUnmodifiableCollection;
import io.github.parubok.swingfx.beans.value.ObservableValue;

/**
 * A {@code DoubleExpression} is a
 * {@link ObservableDoubleValue} plus additional convenience
 * methods to generate bindings in a fluent style.
 * <p>
 * A concrete sub-class of {@code DoubleExpression} has to implement the method
 * {@link ObservableDoubleValue#get()}, which provides the
 * actual value of this expression.
 * @since JavaFX 2.0
 */
public abstract class DoubleExpression extends NumberExpressionBase implements
        ObservableDoubleValue {

    @Override
    public int intValue() {
        return (int) get();
    }

    @Override
    public long longValue() {
        return (long) get();
    }

    @Override
    public float floatValue() {
        return (float) get();
    }

    @Override
    public double doubleValue() {
        return get();
    }

    @Override
    public Double getValue() {
        return get();
    }

    /**
     * Returns a {@code DoubleExpression} that wraps a
     * {@link ObservableDoubleValue}. If the
     * {@code ObservableDoubleValue} is already a {@code DoubleExpression}, it
     * will be returned. Otherwise a new
     * {@link DoubleBinding} is created that is bound to
     * the {@code ObservableDoubleValue}.
     *
     * @param value
     *            The source {@code ObservableDoubleValue}
     * @return A {@code DoubleExpression} that wraps the
     *         {@code ObservableDoubleValue} if necessary
     * @throws NullPointerException
     *             if {@code value} is {@code null}
     */
    public static DoubleExpression doubleExpression(
            final ObservableDoubleValue value) {
        if (value == null) {
            throw new NullPointerException("Value must be specified.");
        }
        return (value instanceof DoubleExpression) ? (DoubleExpression) value
                : new DoubleBinding() {
                    {
                        super.bind(value);
                    }

                    @Override
                    public void dispose() {
                        super.unbind(value);
                    }

                    @Override
                    protected double computeValue() {
                        return value.get();
                    }

                    @Override
                    @ReturnsUnmodifiableCollection
                    public ObservableList<ObservableDoubleValue> getDependencies() {
                        return FXCollections.singletonObservableList(value);
                    }
                };
    }

    /**
     * Returns a {@code DoubleExpression} that wraps an
     * {@link ObservableValue}. If the
     * {@code ObservableValue} is already a {@code DoubleExpression}, it
     * will be returned. Otherwise a new
     * {@link DoubleBinding} is created that is bound to
     * the {@code ObservableValue}.
     *
     * <p>
     * Note: this method can be used to convert an {@link ObjectExpression} or
     * {@link ObjectProperty} of specific number type to DoubleExpression, which
     * is essentially an {@code ObservableValue<Number>}. See sample below.
     *
     * <blockquote><pre>
     *   DoubleProperty doubleProperty = new SimpleDoubleProperty(1.0);
     *   ObjectProperty&lt;Double&gt; objectProperty = new SimpleObjectProperty&lt;&gt;(2.0);
     *   BooleanBinding binding = doubleProperty.greaterThan(DoubleExpression.doubleExpression(objectProperty));
     * </pre></blockquote>
     *
     * Note: null values will be interpreted as 0.0
     *
     * @param value
     *            The source {@code ObservableValue}
     * @return A {@code DoubleExpression} that wraps the
     *         {@code ObservableValue} if necessary
     * @throws NullPointerException
     *             if {@code value} is {@code null}
     * @since JavaFX 8.0
     */
    public static <T extends Number> DoubleExpression doubleExpression(final ObservableValue<T> value) {
        if (value == null) {
            throw new NullPointerException("Value must be specified.");
        }
        return (value instanceof DoubleExpression) ? (DoubleExpression) value
                : new DoubleBinding() {
            {
                super.bind(value);
            }

            @Override
            public void dispose() {
                super.unbind(value);
            }

            @Override
            protected double computeValue() {
                final T val = value.getValue();
                return val == null ? 0.0 : val.doubleValue();
            }

            @Override
            @ReturnsUnmodifiableCollection
            public ObservableList<ObservableValue<T>> getDependencies() {
                return FXCollections.singletonObservableList(value);
            }
        };
    }

    @Override
    public DoubleBinding negate() {
        return (DoubleBinding) Bindings.negate(this);
    }

    @Override
    public DoubleBinding add(final ObservableNumberValue other) {
        return (DoubleBinding) Bindings.add(this, other);
    }

    @Override
    public DoubleBinding add(final double other) {
        return Bindings.add(this, other);
    }

    @Override
    public DoubleBinding add(final float other) {
        return (DoubleBinding) Bindings.add(this, other);
    }

    @Override
    public DoubleBinding add(final long other) {
        return (DoubleBinding) Bindings.add(this, other);
    }

    @Override
    public DoubleBinding add(final int other) {
        return (DoubleBinding) Bindings.add(this, other);
    }

    @Override
    public DoubleBinding subtract(final ObservableNumberValue other) {
        return (DoubleBinding) Bindings.subtract(this, other);
    }

    @Override
    public DoubleBinding subtract(final double other) {
        return Bindings.subtract(this, other);
    }

    @Override
    public DoubleBinding subtract(final float other) {
        return (DoubleBinding) Bindings.subtract(this, other);
    }

    @Override
    public DoubleBinding subtract(final long other) {
        return (DoubleBinding) Bindings.subtract(this, other);
    }

    @Override
    public DoubleBinding subtract(final int other) {
        return (DoubleBinding) Bindings.subtract(this, other);
    }

    @Override
    public DoubleBinding multiply(final ObservableNumberValue other) {
        return (DoubleBinding) Bindings.multiply(this, other);
    }

    @Override
    public DoubleBinding multiply(final double other) {
        return Bindings.multiply(this, other);
    }

    @Override
    public DoubleBinding multiply(final float other) {
        return (DoubleBinding) Bindings.multiply(this, other);
    }

    @Override
    public DoubleBinding multiply(final long other) {
        return (DoubleBinding) Bindings.multiply(this, other);
    }

    @Override
    public DoubleBinding multiply(final int other) {
        return (DoubleBinding) Bindings.multiply(this, other);
    }

    @Override
    public DoubleBinding divide(final ObservableNumberValue other) {
        return (DoubleBinding) Bindings.divide(this, other);
    }

    @Override
    public DoubleBinding divide(final double other) {
        return Bindings.divide(this, other);
    }

    @Override
    public DoubleBinding divide(final float other) {
        return (DoubleBinding) Bindings.divide(this, other);
    }

    @Override
    public DoubleBinding divide(final long other) {
        return (DoubleBinding) Bindings.divide(this, other);
    }

    @Override
    public DoubleBinding divide(final int other) {
        return (DoubleBinding) Bindings.divide(this, other);
    }

    /**
     * Creates an {@link ObjectExpression} that holds the value
     * of this {@code DoubleExpression}. If the
     * value of this {@code DoubleExpression} changes, the value of the
     * {@code ObjectExpression} will be updated automatically.
     *
     * @return the new {@code ObjectExpression}
     * @since JavaFX 8.0
     */
    public ObjectExpression<Double> asObject() {
        return new ObjectBinding<Double>() {
            {
                bind(DoubleExpression.this);
            }

            @Override
            public void dispose() {
                unbind(DoubleExpression.this);
            }

            @Override
            protected Double computeValue() {
                return DoubleExpression.this.getValue();
            }
        };
    }
}
