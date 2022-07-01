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
import io.github.parubok.swingfx.beans.value.ObservableLongValue;
import io.github.parubok.swingfx.collections.FXCollections;
import io.github.parubok.swingfx.collections.ObservableList;
import io.github.parubok.com.sun.swingfx.collections.annotations.ReturnsUnmodifiableCollection;
import io.github.parubok.swingfx.beans.value.ObservableValue;

/**
 * A {@code LongExpression} is a {@link ObservableLongValue}
 * plus additional convenience methods to generate bindings in a fluent style.
 * <p>
 * A concrete sub-class of {@code LongExpression} has to implement the method
 * {@link ObservableLongValue#get()}, which provides the
 * actual value of this expression.
 * @since JavaFX 2.0
 */
public abstract class LongExpression extends NumberExpressionBase implements
        ObservableLongValue {

    @Override
    public int intValue() {
        return (int) get();
    }

    @Override
    public long longValue() {
        return get();
    }

    @Override
    public float floatValue() {
        return (float) get();
    }

    @Override
    public double doubleValue() {
        return (double) get();
    }

    @Override
    public Long getValue() {
        return get();
    }

    /**
     * Returns a {@code LongExpression} that wraps a
     * {@link ObservableLongValue}. If the
     * {@code ObservableLongValue} is already a {@code LongExpression}, it will
     * be returned. Otherwise a new {@link LongBinding} is
     * created that is bound to the {@code ObservableLongValue}.
     *
     * @param value
     *            The source {@code ObservableLongValue}
     * @return A {@code LongExpression} that wraps the
     *         {@code ObservableLongValue} if necessary
     * @throws NullPointerException
     *             if {@code value} is {@code null}
     */
    public static LongExpression longExpression(final ObservableLongValue value) {
        if (value == null) {
            throw new NullPointerException("Value must be specified.");
        }
        return (value instanceof LongExpression) ? (LongExpression) value
                : new LongBinding() {
                    {
                        super.bind(value);
                    }

                    @Override
                    public void dispose() {
                        super.unbind(value);
                    }

                    @Override
                    protected long computeValue() {
                        return value.get();
                    }

                    @Override
                    @ReturnsUnmodifiableCollection
                    public ObservableList<ObservableLongValue> getDependencies() {
                        return FXCollections.singletonObservableList(value);
                    }
        };
    }

    /**
     * Returns a {@code LongExpression} that wraps an
     * {@link ObservableValue}. If the
     * {@code ObservableValue} is already a {@code LongExpression}, it
     * will be returned. Otherwise a new
     * {@link LongBinding} is created that is bound to
     * the {@code ObservableValue}.
     *
     * <p>
     * Note: this method can be used to convert an {@link ObjectExpression} or
     * {@link ObjectProperty} of specific number type to LongExpression, which
     * is essentially an {@code ObservableValue<Number>}. See sample below.
     *
     * <blockquote><pre>
     *   LongProperty longProperty = new SimpleLongProperty(1L);
     *   ObjectProperty&lt;Long&gt; objectProperty = new SimpleObjectProperty&lt;&gt;(2L);
     *   BooleanBinding binding = longProperty.greaterThan(LongExpression.longExpression(objectProperty));
     * </pre></blockquote>
     *
     * Note: null values will be interpreted as 0L
     *
     * @param value
     *            The source {@code ObservableValue}
     * @return A {@code LongExpression} that wraps the
     *         {@code ObservableValue} if necessary
     * @throws NullPointerException
     *             if {@code value} is {@code null}
     * @since JavaFX 8.0
     */
    public static <T extends Number> LongExpression longExpression(final ObservableValue<T> value) {
        if (value == null) {
            throw new NullPointerException("Value must be specified.");
        }
        return (value instanceof LongExpression) ? (LongExpression) value
                : new LongBinding() {
            {
                super.bind(value);
            }

            @Override
            public void dispose() {
                super.unbind(value);
            }

            @Override
            protected long computeValue() {
                final T val = value.getValue();
                return val == null ? 0L : val.longValue();
            }

            @Override
            @ReturnsUnmodifiableCollection
            public ObservableList<ObservableValue<T>> getDependencies() {
                return FXCollections.singletonObservableList(value);
            }
        };
    }


    @Override
    public LongBinding negate() {
        return (LongBinding) Bindings.negate(this);
    }

    @Override
    public DoubleBinding add(final double other) {
        return Bindings.add(this, other);
    }

    @Override
    public FloatBinding add(final float other) {
        return (FloatBinding) Bindings.add(this, other);
    }

    @Override
    public LongBinding add(final long other) {
        return (LongBinding) Bindings.add(this, other);
    }

    @Override
    public LongBinding add(final int other) {
        return (LongBinding) Bindings.add(this, other);
    }

    @Override
    public DoubleBinding subtract(final double other) {
        return Bindings.subtract(this, other);
    }

    @Override
    public FloatBinding subtract(final float other) {
        return (FloatBinding) Bindings.subtract(this, other);
    }

    @Override
    public LongBinding subtract(final long other) {
        return (LongBinding) Bindings.subtract(this, other);
    }

    @Override
    public LongBinding subtract(final int other) {
        return (LongBinding) Bindings.subtract(this, other);
    }

    @Override
    public DoubleBinding multiply(final double other) {
        return Bindings.multiply(this, other);
    }

    @Override
    public FloatBinding multiply(final float other) {
        return (FloatBinding) Bindings.multiply(this, other);
    }

    @Override
    public LongBinding multiply(final long other) {
        return (LongBinding) Bindings.multiply(this, other);
    }

    @Override
    public LongBinding multiply(final int other) {
        return (LongBinding) Bindings.multiply(this, other);
    }

    @Override
    public DoubleBinding divide(final double other) {
        return Bindings.divide(this, other);
    }

    @Override
    public FloatBinding divide(final float other) {
        return (FloatBinding) Bindings.divide(this, other);
    }

    @Override
    public LongBinding divide(final long other) {
        return (LongBinding) Bindings.divide(this, other);
    }

    @Override
    public LongBinding divide(final int other) {
        return (LongBinding) Bindings.divide(this, other);
    }

    /**
     * Creates an {@link ObjectExpression} that holds the value
     * of this {@code LongExpression}. If the
     * value of this {@code LongExpression} changes, the value of the
     * {@code ObjectExpression} will be updated automatically.
     *
     * @return the new {@code ObjectExpression}
     * @since JavaFX 8.0
     */
    public ObjectExpression<Long> asObject() {
        return new ObjectBinding<Long>() {
            {
                bind(LongExpression.this);
            }

            @Override
            public void dispose() {
                unbind(LongExpression.this);
            }

            @Override
            protected Long computeValue() {
                return LongExpression.this.getValue();
            }
        };
    }
}
