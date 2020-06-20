/*
 * Copyright (c) 2011, 2013, Oracle and/or its affiliates. All rights reserved.
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
import com.sun.swingfx.collections.annotations.ReturnsUnmodifiableCollection;
import swingfx.beans.InvalidationListener;
import swingfx.beans.property.ReadOnlyBooleanProperty;
import swingfx.beans.property.ReadOnlyIntegerProperty;
import swingfx.beans.value.ObservableMapValue;
import swingfx.beans.value.ObservableValue;
import swingfx.collections.FXCollections;
import swingfx.collections.MapChangeListener;
import swingfx.collections.ObservableList;
import swingfx.collections.ObservableMap;

import java.util.*;

/**
 * A {@code MapExpression} is a
 * {@link ObservableMapValue} plus additional convenience
 * methods to generate bindings in a fluent style.
 * <p>
 * A concrete sub-class of {@code MapExpression} has to implement the method
 * {@link ObservableMapValue#get()}, which provides the
 * actual value of this expression.
 * <p>
 * If the wrapped list of a {@code MapExpression} is {@code null}, all methods implementing the {@code Map}
 * interface will behave as if they were applied to an immutable empty list.
 *
 * @param <K> the type of the key elements
 * @param <V> the type of the value elements
 * @since JavaFX 2.1
 */
public abstract class MapExpression<K, V> implements ObservableMapValue<K, V> {

    private static final ObservableMap EMPTY_MAP = new EmptyObservableMap();

    private static class EmptyObservableMap<K, V> extends AbstractMap<K, V> implements ObservableMap<K, V> {

        @Override
        public Set<Entry<K, V>> entrySet() {
            return Collections.emptySet();
        }

        @Override
        public void addListener(MapChangeListener<? super K, ? super V> mapChangeListener) {
            // no-op
        }

        @Override
        public void removeListener(MapChangeListener<? super K, ? super V> mapChangeListener) {
            // no-op
        }

        @Override
        public void addListener(InvalidationListener listener) {
            // no-op
        }

        @Override
        public void removeListener(InvalidationListener listener) {
            // no-op
        }
    }

    @Override
    public ObservableMap<K, V> getValue() {
        return get();
    }

    /**
     * Returns a {@code MapExpression} that wraps a
     * {@link ObservableMapValue}. If the
     * {@code ObservableMapValue} is already a {@code MapExpression}, it
     * will be returned. Otherwise a new
     * {@link swingfx.beans.binding.MapBinding} is created that is bound to
     * the {@code ObservableMapValue}.
     *
     * @param value
     *            The source {@code ObservableMapValue}
     * @return A {@code MapExpression} that wraps the
     *         {@code ObservableMapValue} if necessary
     * @throws NullPointerException
     *             if {@code value} is {@code null}
     */
    public static <K, V> MapExpression<K, V> mapExpression(final ObservableMapValue<K, V> value) {
        if (value == null) {
            throw new NullPointerException("Map must be specified.");
        }
        return value instanceof MapExpression ? (MapExpression<K, V>) value
                : new MapBinding<K, V>() {
            {
                super.bind(value);
            }

            @Override
            public void dispose() {
                super.unbind(value);
            }

            @Override
            protected ObservableMap<K, V> computeValue() {
                return value.get();
            }

            @Override
            @ReturnsUnmodifiableCollection
            public ObservableList<?> getDependencies() {
                return FXCollections.singletonObservableList(value);
            }
        };
    }

    /**
     * The size of the map
     */
    public int getSize() {
        return size();
    }

    /**
     * An integer property that represents the size of the map.
     * @return the property
     */
    public abstract ReadOnlyIntegerProperty sizeProperty();

    /**
     * A boolean property that is {@code true}, if the map is empty.
     */
    public abstract ReadOnlyBooleanProperty emptyProperty();

    /**
     * Creates a new {@link swingfx.beans.binding.ObjectBinding} that contains the mapping of the specified key.
     *
     * @param key the key of the mapping
     * @return the {@code ObjectBinding}
     */
    public swingfx.beans.binding.ObjectBinding<V> valueAt(K key) {
        return swingfx.beans.binding.Bindings.valueAt(this, key);
    }

    /**
     * Creates a new {@link swingfx.beans.binding.ObjectBinding} that contains the mapping of the specified key.
     *
     * @param key the key of the mapping
     * @return the {@code ObjectBinding}
     * @throws NullPointerException if {@code key} is {@code null}
     */
    public ObjectBinding<V> valueAt(ObservableValue<K> key) {
        return swingfx.beans.binding.Bindings.valueAt(this, key);
    }

    /**
     * Creates a new {@link swingfx.beans.binding.BooleanBinding} that holds {@code true} if this map is equal to
     * another {@link ObservableMap}.
     *
     * @param other
     *            the other {@code ObservableMap}
     * @return the new {@code BooleanBinding}
     * @throws NullPointerException
     *             if {@code other} is {@code null}
     */
    public swingfx.beans.binding.BooleanBinding isEqualTo(final ObservableMap<?, ?> other) {
        return swingfx.beans.binding.Bindings.equal(this, other);
    }

    /**
     * Creates a new {@link swingfx.beans.binding.BooleanBinding} that holds {@code true} if this map is not equal to
     * another {@link ObservableMap}.
     *
     * @param other
     *            the other {@code ObservableMap}
     * @return the new {@code BooleanBinding}
     * @throws NullPointerException
     *             if {@code other} is {@code null}
     */
    public swingfx.beans.binding.BooleanBinding isNotEqualTo(final ObservableMap<?, ?> other) {
        return swingfx.beans.binding.Bindings.notEqual(this, other);
    }

    /**
     * Creates a new {@link swingfx.beans.binding.BooleanBinding} that holds {@code true} if the wrapped map is {@code null}.
     *
     * @return the new {@code BooleanBinding}
     */
    public swingfx.beans.binding.BooleanBinding isNull() {
        return swingfx.beans.binding.Bindings.isNull(this);
    }

    /**
     * Creates a new {@link swingfx.beans.binding.BooleanBinding} that holds {@code true} if the wrapped map is not {@code null}.
     *
     * @return the new {@code BooleanBinding}
     */
    public BooleanBinding isNotNull() {
        return Bindings.isNotNull(this);
    }

    /**
     * Creates a {@link swingfx.beans.binding.StringBinding} that holds the value
     * of the {@code MapExpression} turned into a {@code String}. If the
     * value of this {@code MapExpression} changes, the value of the
     * {@code StringBinding} will be updated automatically.
     *
     * @return the new {@code StringBinding}
     */
    public swingfx.beans.binding.StringBinding asString() {
        return (StringBinding) StringFormatter.convert(this);
    }

    @Override
    public int size() {
        final ObservableMap<K, V> map = get();
        return (map == null)? EMPTY_MAP.size() : map.size();
    }

    @Override
    public boolean isEmpty() {
        final ObservableMap<K, V> map = get();
        return (map == null)? EMPTY_MAP.isEmpty() : map.isEmpty();
    }

    @Override
    public boolean containsKey(Object obj) {
        final ObservableMap<K, V> map = get();
        return (map == null)? EMPTY_MAP.containsKey(obj) : map.containsKey(obj);
    }

    @Override
    public boolean containsValue(Object obj) {
        final ObservableMap<K, V> map = get();
        return (map == null)? EMPTY_MAP.containsValue(obj) : map.containsValue(obj);
    }

    @Override
    public V put(K key, V value) {
        final ObservableMap<K, V> map = get();
        return (map == null)? (V) EMPTY_MAP.put(key, value) : map.put(key, value);
    }

    @Override
    public V remove(Object obj) {
        final ObservableMap<K, V> map = get();
        return (map == null)? (V) EMPTY_MAP.remove(obj) : map.remove(obj);
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> elements) {
        final ObservableMap<K, V> map = get();
        if (map == null) {
            EMPTY_MAP.putAll(elements);
        } else {
            map.putAll(elements);
        }
    }

    @Override
    public void clear() {
        final ObservableMap<K, V> map = get();
        if (map == null) {
            EMPTY_MAP.clear();
        } else {
            map.clear();
        }
    }

    @Override
    public Set<K> keySet() {
        final ObservableMap<K, V> map = get();
        return (map == null)? EMPTY_MAP.keySet() : map.keySet();
    }

    @Override
    public Collection<V> values() {
        final ObservableMap<K, V> map = get();
        return (map == null)? EMPTY_MAP.values() : map.values();
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        final ObservableMap<K, V> map = get();
        return (map == null)? EMPTY_MAP.entrySet() : map.entrySet();
    }

    @Override
    public V get(Object key) {
        final ObservableMap<K, V> map = get();
        return (map == null)? (V) EMPTY_MAP.get(key) : map.get(key);
    }

}
