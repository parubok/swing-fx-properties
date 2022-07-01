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
import io.github.parubok.swingfx.beans.value.ObservableFloatValue;
import io.github.parubok.swingfx.collections.FXCollections;
import io.github.parubok.swingfx.collections.ObservableList;
import io.github.parubok.com.sun.swingfx.collections.annotations.ReturnsUnmodifiableCollection;
import io.github.parubok.swingfx.beans.value.ObservableValue;

/**
 * A {@code FloatExpression} is a
 * {@link ObservableFloatValue} plus additional convenience
 * methods to generate bindings in a fluent style.
 * <p>
 * A concrete sub-class of {@code FloatExpression} has to implement the method
 * {@link ObservableFloatValue#get()}, which provides the
 * actual value of this expression.
 * @since JavaFX 2.0
 */
public abstract class FloatExpression extends NumberExpressionBase implements
        ObservableFloatValue {

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
        return get();
    }

    @Override
    public double doubleValue() {
        return (double) get();
    }

    @Override
    public Float getValue() {
        return get();
    }

    /**
     * Returns a {@code FloatExpression} that wraps a
     * {@link ObservableFloatValue}. If the
     * {@code ObservableFloatValue} is already a {@code FloatExpression}, it
     * will be returned. Otherwise a new
     * {@link FloatBinding} is created that is bound to the
     * {@code ObservableFloatValue}.
     *
     * @param value
     *            The source {@code ObservableFloatValue}
     * @return A {@code FloatExpression} that wraps the
     *         {@code ObservableFloatValue} if necessary
     * @throws NullPointerException
     *             if {@code value} is {@code null}
     */
    public static FloatExpression floatExpression(
            final ObservableFloatValue value) {
        if (value == null) {
            throw new NullPointerException("Value must be specified.");
        }
        return (value instanceof FloatExpression) ? (FloatExpression) value
                : new FloatBinding() {
                    {
                        super.bind(value);
                    }

                    @Override
                    public void dispose() {
                        super.unbind(value);
                    }

                    @Override
                    protected float computeValue() {
                        return value.get();
                    }

                    @Override
                    @ReturnsUnmodifiableCollection
                    public ObservableList<ObservableFloatValue> getDependencies() {
                        return FXCollections.singletonObservableList(value);
                    }
                };
    }

    /**
     * Returns a {@code FloatExpression} that wraps an
     * {@link ObservableValue}. If the
     * {@code ObservableValue} is already a {@code FloatExpression}, it
     * will be returned. Otherwise a new
     * {@link FloatBinding} is created that is bound to
     * the {@code ObservableValue}.
     *
     * <p>
     * Note: this method can be used to convert an {@link ObjectExpression} or
     * {@link ObjectProperty} of specific number type to FloatExpression, which
     * is essentially an {@code ObservableValue<Number>}. See sample below.
     *
     * <blockquote><pre>
     *   FloatProperty floatProperty = new SimpleFloatProperty(1.0f);
     *   ObjectProperty&lt;Float&gt; objectProperty = new SimpleObjectProperty&lt;&gt;(2.0f);
     *   BooleanBinding binding = floatProperty.greaterThan(FloatExpression.floatExpression(objectProperty));
     * </pre></blockquote>
     *
     *  Note: null values will be interpreted as 0f
     *
     * @param value
     *            The source {@code ObservableValue}
     * @return A {@code FloatExpression} that wraps the
     *         {@code ObservableValue} if necessary
     * @throws NullPointerException
     *             if {@code value} is {@code null}
     * @since JavaFX 8.0
     */
    public static <T extends Number> FloatExpression floatExpression(final ObservableValue<T> value) {
        if (value == null) {
            throw new NullPointerException("Value must be specified.");
        }
        return (value instanceof FloatExpression) ? (FloatExpression) value
                : new FloatBinding() {
            {
                super.bind(value);
            }

            @Override
            public void dispose() {
                super.unbind(value);
            }

            @Override
            protected float computeValue() {
                final T val = value.getValue();
                return val == null ? 0f :  val.floatValue();
            }

            @Override
            @ReturnsUnmodifiableCollection
            public ObservableList<ObservableValue<T>> getDependencies() {
                return FXCollections.singletonObservableList(value);
            }
        };
    }


    @Override
    public FloatBinding negate() {
        return (FloatBinding) Bindings.negate(this);
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
    public FloatBinding add(final long other) {
        return (FloatBinding) Bindings.add(this, other);
    }

    @Override
    public FloatBinding add(final int other) {
        return (FloatBinding) Bindings.add(this, other);
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
    public FloatBinding subtract(final long other) {
        return (FloatBinding) Bindings.subtract(this, other);
    }

    @Override
    public FloatBinding subtract(final int other) {
        return (FloatBinding) Bindings.subtract(this, other);
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
    public FloatBinding multiply(final long other) {
        return (FloatBinding) Bindings.multiply(this, other);
    }

    @Override
    public FloatBinding multiply(final int other) {
        return (FloatBinding) Bindings.multiply(this, other);
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
    public FloatBinding divide(final long other) {
        return (FloatBinding) Bindings.divide(this, other);
    }

    @Override
    public FloatBinding divide(final int other) {
        return (FloatBinding) Bindings.divide(this, other);
    }

    /**
     * Creates an {@link ObjectExpression} that holds the value
     * of this {@code FloatExpression}. If the
     * value of this {@code FloatExpression} changes, the value of the
     * {@code ObjectExpression} will be updated automatically.
     *
     * @return the new {@code ObjectExpression}
     * @since JavaFX 8.0
     */
    public ObjectExpression<Float> asObject() {
        return new ObjectBinding<Float>() {
            {
                bind(FloatExpression.this);
            }

            @Override
            public void dispose() {
                unbind(FloatExpression.this);
            }

            @Override
            protected Float computeValue() {
                return FloatExpression.this.getValue();
            }
        };
    }
}
