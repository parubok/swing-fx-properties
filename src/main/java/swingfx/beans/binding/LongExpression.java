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

import swingfx.beans.value.ObservableLongValue;
import swingfx.collections.FXCollections;
import swingfx.collections.ObservableList;
import org.swingfx.misc.ReturnsUnmodifiableCollection;
import swingfx.beans.value.ObservableValue;
import swingfx.beans.property.ObjectProperty;

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
     * be returned. Otherwise a new {@link swingfx.beans.binding.LongBinding} is
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
                : new swingfx.beans.binding.LongBinding() {
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
     * {@link swingfx.beans.binding.LongBinding} is created that is bound to
     * the {@code ObservableValue}.
     *
     * <p>
     * Note: this method can be used to convert an {@link swingfx.beans.binding.ObjectExpression} or
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
                : new swingfx.beans.binding.LongBinding() {
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
    public swingfx.beans.binding.LongBinding negate() {
        return (swingfx.beans.binding.LongBinding) swingfx.beans.binding.Bindings.negate(this);
    }

    @Override
    public swingfx.beans.binding.DoubleBinding add(final double other) {
        return swingfx.beans.binding.Bindings.add(this, other);
    }

    @Override
    public swingfx.beans.binding.FloatBinding add(final float other) {
        return (swingfx.beans.binding.FloatBinding) swingfx.beans.binding.Bindings.add(this, other);
    }

    @Override
    public swingfx.beans.binding.LongBinding add(final long other) {
        return (swingfx.beans.binding.LongBinding) swingfx.beans.binding.Bindings.add(this, other);
    }

    @Override
    public swingfx.beans.binding.LongBinding add(final int other) {
        return (swingfx.beans.binding.LongBinding) swingfx.beans.binding.Bindings.add(this, other);
    }

    @Override
    public swingfx.beans.binding.DoubleBinding subtract(final double other) {
        return swingfx.beans.binding.Bindings.subtract(this, other);
    }

    @Override
    public swingfx.beans.binding.FloatBinding subtract(final float other) {
        return (swingfx.beans.binding.FloatBinding) swingfx.beans.binding.Bindings.subtract(this, other);
    }

    @Override
    public swingfx.beans.binding.LongBinding subtract(final long other) {
        return (swingfx.beans.binding.LongBinding) swingfx.beans.binding.Bindings.subtract(this, other);
    }

    @Override
    public swingfx.beans.binding.LongBinding subtract(final int other) {
        return (swingfx.beans.binding.LongBinding) swingfx.beans.binding.Bindings.subtract(this, other);
    }

    @Override
    public swingfx.beans.binding.DoubleBinding multiply(final double other) {
        return swingfx.beans.binding.Bindings.multiply(this, other);
    }

    @Override
    public swingfx.beans.binding.FloatBinding multiply(final float other) {
        return (swingfx.beans.binding.FloatBinding) swingfx.beans.binding.Bindings.multiply(this, other);
    }

    @Override
    public swingfx.beans.binding.LongBinding multiply(final long other) {
        return (swingfx.beans.binding.LongBinding) swingfx.beans.binding.Bindings.multiply(this, other);
    }

    @Override
    public swingfx.beans.binding.LongBinding multiply(final int other) {
        return (swingfx.beans.binding.LongBinding) swingfx.beans.binding.Bindings.multiply(this, other);
    }

    @Override
    public DoubleBinding divide(final double other) {
        return swingfx.beans.binding.Bindings.divide(this, other);
    }

    @Override
    public swingfx.beans.binding.FloatBinding divide(final float other) {
        return (FloatBinding) swingfx.beans.binding.Bindings.divide(this, other);
    }

    @Override
    public swingfx.beans.binding.LongBinding divide(final long other) {
        return (swingfx.beans.binding.LongBinding) swingfx.beans.binding.Bindings.divide(this, other);
    }

    @Override
    public swingfx.beans.binding.LongBinding divide(final int other) {
        return (LongBinding) Bindings.divide(this, other);
    }

    /**
     * Creates an {@link swingfx.beans.binding.ObjectExpression} that holds the value
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
