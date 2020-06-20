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

import swingfx.beans.value.ObservableIntegerValue;
import swingfx.collections.FXCollections;
import swingfx.collections.ObservableList;
import com.sun.swingfx.collections.annotations.ReturnsUnmodifiableCollection;
import swingfx.beans.value.ObservableValue;
import swingfx.beans.property.ObjectProperty;

/**
 * A {@code IntegerExpression} is a
 * {@link ObservableIntegerValue} plus additional convenience
 * methods to generate bindings in a fluent style.
 * <p>
 * A concrete sub-class of {@code IntegerExpression} has to implement the method
 * {@link ObservableIntegerValue#get()}, which provides the
 * actual value of this expression.
 * @since JavaFX 2.0
 */
public abstract class IntegerExpression extends NumberExpressionBase implements
        ObservableIntegerValue {

    @Override
    public int intValue() {
        return get();
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
        return (double) get();
    }

    @Override
    public Integer getValue() {
        return get();
    }

    /**
     * Returns a {@code IntegerExpression} that wraps a
     * {@link ObservableIntegerValue}. If the
     * {@code ObservableIntegerValue} is already a {@code IntegerExpression}, it
     * will be returned. Otherwise a new
     * {@link swingfx.beans.binding.IntegerBinding} is created that is bound to
     * the {@code ObservableIntegerValue}.
     *
     * @param value
     *            The source {@code ObservableIntegerValue}
     * @return A {@code IntegerExpression} that wraps the
     *         {@code ObservableIntegerValue} if necessary
     * @throws NullPointerException
     *             if {@code value} is {@code null}
     */
    public static IntegerExpression integerExpression(
            final ObservableIntegerValue value) {
        if (value == null) {
            throw new NullPointerException("Value must be specified.");
        }
        return (value instanceof IntegerExpression) ? (IntegerExpression) value
                : new swingfx.beans.binding.IntegerBinding() {
                    {
                        super.bind(value);
                    }

                    @Override
                    public void dispose() {
                        super.unbind(value);
                    }

                    @Override
                    protected int computeValue() {
                        return value.get();
                    }

                    @Override
                    @ReturnsUnmodifiableCollection
                    public ObservableList<ObservableIntegerValue> getDependencies() {
                        return FXCollections.singletonObservableList(value);
                    }
                };
    }

    /**
     * Returns an {@code IntegerExpression} that wraps an
     * {@link ObservableValue}. If the
     * {@code ObservableValue} is already a {@code IntegerExpression}, it
     * will be returned. Otherwise a new
     * {@link swingfx.beans.binding.IntegerBinding} is created that is bound to
     * the {@code ObservableValue}.
     *
     * <p>
     * Note: this method can be used to convert an {@link swingfx.beans.binding.ObjectExpression} or
     * {@link ObjectProperty} of specific number type to IntegerExpression, which
     * is essentially an {@code ObservableValue<Number>}. See sample below.
     *
     * <blockquote><pre>
     *   IntegerProperty integerProperty = new SimpleIntegerProperty(1);
     *   ObjectProperty&lt;Integer&gt; objectProperty = new SimpleObjectProperty&lt;&gt;(2);
     *   BooleanBinding binding = integerProperty.greaterThan(IntegerExpression.integerExpression(objectProperty));
     * </pre></blockquote>
     *
     * Note: null values will be interpreted as 0
     *
     * @param value
     *            The source {@code ObservableValue}
     * @return A {@code IntegerExpression} that wraps the
     *         {@code ObservableValue} if necessary
     * @throws NullPointerException
     *             if {@code value} is {@code null}
     * @since JavaFX 8.0
     */
    public static <T extends Number> IntegerExpression integerExpression(final ObservableValue<T> value) {
        if (value == null) {
            throw new NullPointerException("Value must be specified.");
        }
        return (value instanceof IntegerExpression) ? (IntegerExpression) value
                : new swingfx.beans.binding.IntegerBinding() {
            {
                super.bind(value);
            }

            @Override
            public void dispose() {
                super.unbind(value);
            }

            @Override
            protected int computeValue() {
                final T val = value.getValue();
                return val == null ? 0 : val.intValue();
            }

            @Override
            @ReturnsUnmodifiableCollection
            public ObservableList<ObservableValue<T>> getDependencies() {
                return FXCollections.singletonObservableList(value);
            }
        };
    }


    @Override
    public swingfx.beans.binding.IntegerBinding negate() {
        return (swingfx.beans.binding.IntegerBinding) swingfx.beans.binding.Bindings.negate(this);
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
    public swingfx.beans.binding.IntegerBinding add(final int other) {
        return (swingfx.beans.binding.IntegerBinding) swingfx.beans.binding.Bindings.add(this, other);
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
    public swingfx.beans.binding.IntegerBinding subtract(final int other) {
        return (swingfx.beans.binding.IntegerBinding) swingfx.beans.binding.Bindings.subtract(this, other);
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
    public swingfx.beans.binding.IntegerBinding multiply(final int other) {
        return (swingfx.beans.binding.IntegerBinding) swingfx.beans.binding.Bindings.multiply(this, other);
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
        return (LongBinding) swingfx.beans.binding.Bindings.divide(this, other);
    }

    @Override
    public swingfx.beans.binding.IntegerBinding divide(final int other) {
        return (IntegerBinding) Bindings.divide(this, other);
    }

    /**
     * Creates an {@link swingfx.beans.binding.ObjectExpression} that holds the value
     * of this {@code IntegerExpression}. If the
     * value of this {@code IntegerExpression} changes, the value of the
     * {@code ObjectExpression} will be updated automatically.
     *
     * @return the new {@code ObjectExpression}
     * @since JavaFX 8.0
     */
    public ObjectExpression<Integer> asObject() {
        return new ObjectBinding<Integer>() {
            {
                bind(IntegerExpression.this);
            }

            @Override
            public void dispose() {
                unbind(IntegerExpression.this);
            }

            @Override
            protected Integer computeValue() {
                return IntegerExpression.this.getValue();
            }
        };
    }
}
