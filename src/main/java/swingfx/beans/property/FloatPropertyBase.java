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

package swingfx.beans.property;

import swingfx.beans.InvalidationListener;
import swingfx.beans.Observable;
import swingfx.beans.binding.FloatBinding;
import swingfx.beans.value.ChangeListener;
import swingfx.beans.value.ObservableValue;

import com.sun.swingfx.binding.ExpressionHelper;
import java.lang.ref.WeakReference;
import swingfx.beans.value.ObservableFloatValue;
import swingfx.beans.value.ObservableNumberValue;

/**
 * The class {@code FloatPropertyBase} is the base class for a property wrapping
 * a {@code float} value.
 *
 * It provides all the functionality required for a property except for the
 * {@link #getBean()} and {@link #getName()} methods, which must be implemented
 * by extending classes.
 *
 * @see swingfx.beans.property.FloatProperty
 *
 *
 * @since JavaFX 2.0
 */
public abstract class FloatPropertyBase extends FloatProperty {

    private float value;
    private ObservableFloatValue observable = null;;
    private InvalidationListener listener = null;
    private boolean valid = true;
    private ExpressionHelper<Number> helper = null;

    /**
     * The constructor of the {@code FloatPropertyBase}.
     */
    public FloatPropertyBase() {
    }

    /**
     * The constructor of the {@code FloatPropertyBase}.
     *
     * @param initialValue
     *            the initial value of the wrapped value
     */
    public FloatPropertyBase(float initialValue) {
        this.value = initialValue;
    }

    @Override
    public void addListener(InvalidationListener listener) {
        helper = ExpressionHelper.addListener(helper, this, listener);
    }

    @Override
    public void removeListener(InvalidationListener listener) {
        helper = ExpressionHelper.removeListener(helper, listener);
    }

    @Override
    public void addListener(ChangeListener<? super Number> listener) {
        helper = ExpressionHelper.addListener(helper, this, listener);
    }

    @Override
    public void removeListener(ChangeListener<? super Number> listener) {
        helper = ExpressionHelper.removeListener(helper, listener);
    }

    /**
     * Sends notifications to all attached
     * {@link InvalidationListener InvalidationListeners} and
     * {@link ChangeListener ChangeListeners}.
     *
     * This method is called when the value is changed, either manually by
     * calling {@link #set(float)} or in case of a bound property, if the
     * binding becomes invalid.
     */
    protected void fireValueChangedEvent() {
        ExpressionHelper.fireValueChangedEvent(helper);
    }

    private void markInvalid() {
        if (valid) {
            valid = false;
            invalidated();
            fireValueChangedEvent();
        }
    }

    /**
     * The method {@code invalidated()} can be overridden to receive
     * invalidation notifications. This is the preferred option in
     * {@code Objects} defining the property, because it requires less memory.
     *
     * The default implementation is empty.
     */
    protected void invalidated() {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public float get() {
        valid = true;
        return observable == null ? value : observable.get();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void set(float newValue) {
        if (isBound()) {
            throw new java.lang.RuntimeException((getBean() != null && getName() != null ?
                    getBean().getClass().getSimpleName() + "." + getName() + " : ": "") + "A bound value cannot be set.");
        }
        if (value != newValue) {
            value = newValue;
            markInvalid();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isBound() {
        return observable != null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void bind(final ObservableValue<? extends Number> rawObservable) {
        if (rawObservable == null) {
            throw new NullPointerException("Cannot bind to null");
        }

        ObservableFloatValue newObservable;
        if (rawObservable instanceof ObservableFloatValue) {
            newObservable = (ObservableFloatValue)rawObservable;
        } else if (rawObservable instanceof ObservableNumberValue) {
            final ObservableNumberValue numberValue = (ObservableNumberValue)rawObservable;
            newObservable = new FloatBinding() {
                {
                    super.bind(rawObservable);
                }

                @Override
                protected float computeValue() {
                    return numberValue.floatValue();
                }
            };
        } else {
            newObservable = new FloatBinding() {
                {
                    super.bind(rawObservable);
                }

                @Override
                protected float computeValue() {
                    final Number value = rawObservable.getValue();
                    return (value == null)? 0.0f : value.floatValue();
                }
            };
        }


        if (!newObservable.equals(observable)) {
            unbind();
            observable = newObservable;
            if (listener == null) {
                listener = new Listener(this);
            }
            observable.addListener(listener);
            markInvalid();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void unbind() {
        if (observable != null) {
            value = observable.get();
            observable.removeListener(listener);
            observable = null;
        }
    }

    /**
     * Returns a string representation of this {@code FloatPropertyBase} object.
     * @return a string representation of this {@code FloatPropertyBase} object.
     */
    @Override
    public String toString() {
        final Object bean = getBean();
        final String name = getName();
        final StringBuilder result = new StringBuilder("FloatProperty [");
        if (bean != null) {
            result.append("bean: ").append(bean).append(", ");
        }
        if ((name != null) && (!name.equals(""))) {
            result.append("name: ").append(name).append(", ");
        }
        if (isBound()) {
            result.append("bound, ");
            if (valid) {
                result.append("value: ").append(get());
            } else {
                result.append("invalid");
            }
        } else {
            result.append("value: ").append(get());
        }
        result.append("]");
        return result.toString();
    }

    private static class Listener implements InvalidationListener {

        private final WeakReference<FloatPropertyBase> wref;

        public Listener(FloatPropertyBase ref) {
            this.wref = new WeakReference<>(ref);
        }

        @Override
        public void invalidated(Observable observable) {
            FloatPropertyBase ref = wref.get();
            if (ref == null) {
                observable.removeListener(this);
            } else {
                ref.markInvalid();
            }
        }
    }
}
