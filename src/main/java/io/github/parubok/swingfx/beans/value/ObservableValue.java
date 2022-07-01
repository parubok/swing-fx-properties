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

package io.github.parubok.swingfx.beans.value;

import io.github.parubok.swingfx.beans.binding.BooleanBinding;
import io.github.parubok.swingfx.beans.binding.IntegerBinding;
import io.github.parubok.swingfx.beans.binding.ObjectBinding;
import io.github.parubok.swingfx.beans.binding.StringExpression;
import io.github.parubok.swingfx.beans.InvalidationListener;
import io.github.parubok.swingfx.beans.Observable;
import io.github.parubok.swingfx.beans.binding.Bindings;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.ToIntFunction;

/**
 * An {@code ObservableValue} is an entity that wraps a value and allows to
 * observe the value for changes. In general this interface should not be
 * implemented directly but one of its sub-interfaces (
 * {@code ObservableBooleanValue} etc.).
 * <p>
 * The value of the {@code ObservableValue} can be requested with
 * {@link #getValue()}.
 * <p>
 * An implementation of {@code ObservableValue} may support lazy evaluation,
 * which means that the value is not immediately recomputed after changes, but
 * lazily the next time the value is requested. All bindings and properties in
 * this library support lazy evaluation.
 * <p>
 * An {@code ObservableValue} generates two types of events: change events and
 * invalidation events. A change event indicates that the value has changed. An
 * invalidation event is generated, if the current value is not valid anymore.
 * This distinction becomes important, if the {@code ObservableValue} supports
 * lazy evaluation, because for a lazily evaluated value one does not know if an
 * invalid value really has changed until it is recomputed. For this reason,
 * generating change events requires eager evaluation while invalidation events
 * can be generated for eager and lazy implementations.
 * <p>
 * Implementations of this class should strive to generate as few events as
 * possible to avoid wasting too much time in event handlers. Implementations in
 * this library mark themselves as invalid when the first invalidation event
 * occurs. They do not generate anymore invalidation events until their value is
 * recomputed and valid again.
 * <p>
 * Two types of listeners can be attached to an {@code ObservableValue}:
 * {@link InvalidationListener} to listen to invalidation events and
 * {@link ChangeListener} to listen to change events.
 * <p>
 * Important note: attaching a {@code ChangeListener} enforces eager computation
 * even if the implementation of the {@code ObservableValue} supports lazy
 * evaluation.
 *
 * @param <T>
 *            The type of the wrapped value.
 *
 * @see ObservableBooleanValue
 * @see ObservableDoubleValue
 * @see ObservableFloatValue
 * @see ObservableIntegerValue
 * @see ObservableLongValue
 * @see ObservableNumberValue
 * @see ObservableObjectValue
 * @see ObservableStringValue
 *
 *
 * @since JavaFX 2.0
 */
public interface ObservableValue<T> extends Observable {

    /**
     * Adds a {@link ChangeListener} which will be notified whenever the value
     * of the {@code ObservableValue} changes. If the same listener is added
     * more than once, then it will be notified more than once. That is, no
     * check is made to ensure uniqueness.
     * <p>
     * Note that the same actual {@code ChangeListener} instance may be safely
     * registered for different {@code ObservableValues}.
     * <p>
     * The {@code ObservableValue} stores a strong reference to the listener
     * which will prevent the listener from being garbage collected and may
     * result in a memory leak. It is recommended to either unregister a
     * listener by calling {@link #removeListener(ChangeListener)
     * removeListener} after use or to use an instance of
     * {@link WeakChangeListener} avoid this situation.
     *
     * @see #removeListener(ChangeListener)
     *
     * @param listener
     *            The listener to register
     * @throws NullPointerException
     *             if the listener is null
     */
    void addListener(ChangeListener<? super T> listener);

    /**
     * Removes the given listener from the list of listeners, that are notified
     * whenever the value of the {@code ObservableValue} changes.
     * <p>
     * If the given listener has not been previously registered (i.e. it was
     * never added) then this method call is a no-op. If it had been previously
     * added then it will be removed. If it had been added more than once, then
     * only the first occurrence will be removed.
     *
     * @see #addListener(ChangeListener)
     *
     * @param listener
     *            The listener to remove
     * @throws NullPointerException
     *             if the listener is null
     */
    void removeListener(ChangeListener<? super T> listener);

    /**
     * Returns the current value of this {@code ObservableValue}
     *
     * @return The current value
     */
    T getValue();

    /**
     * Creates {@link ObjectBinding} which is bounded to this value via the specified mapping function.
     *
     * @param func Mapping function. Not null.
     * @since swing-fx-properties 1.2
     */
    default <K> ObjectBinding<K> asObject(Function<T, K> func) {
        Objects.requireNonNull(func);
        return Bindings.createObjectBinding(() -> func.apply(getValue()), this);
    }

    /**
     * Creates {@link IntegerBinding} which is bounded to this value via the specified mapping function.
     *
     * @param func Mapping function. Not null.
     * @since swing-fx-properties 1.7
     */
    default IntegerBinding asInteger(ToIntFunction<T> func) {
        Objects.requireNonNull(func);
        return Bindings.createIntegerBinding(() -> func.applyAsInt(getValue()), this);
    }

    /**
     * Creates {@link BooleanBinding} which is bounded to this value via the specified mapping predicate.
     *
     * @param predicate Mapping predicate. Not null.
     * @since swing-fx-properties 1.7
     */
    default BooleanBinding asBoolean(Predicate<T> predicate) {
        Objects.requireNonNull(predicate);
        return Bindings.createBooleanBinding(() -> predicate.test(getValue()), this);
    }

    /**
     * Creates a {@link StringExpression} that holds the value turned into a {@code String}. If the
     * value changes, the value of the {@link StringExpression} will be updated automatically.
     * <p>
     * The result is formatted according to the formatting {@code String}. See
     * {@code java.util.Formatter} for formatting rules.
     * </p>
     *
     * @param format
     *            the formatting {@code String}
     * @return the new {@link StringExpression}
     * @since swing-fx-properties 1.0
     */
    default StringExpression asStringExpression(String format) {
        return Bindings.format(format, this);
    }
}
