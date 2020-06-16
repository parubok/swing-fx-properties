/*
 * Copyright (c) 2010, 2014, Oracle and/or its affiliates. All rights reserved.
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

package com.sun.swingfx.collections;

import java.util.AbstractMap;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;

import swingfx.beans.InvalidationListener;
import swingfx.collections.MapChangeListener;
import swingfx.collections.MapChangeListener.Change;
import swingfx.collections.ObservableMap;
import swingfx.collections.WeakMapChangeListener;

import org.swingfx.misc.ReturnsUnmodifiableCollection;

/**
 * ObservableMap wrapper that does not allow changes to the underlying container.
 */
public class UnmodifiableObservableMap<K, V> extends AbstractMap<K, V>
        implements ObservableMap<K, V>
{
    private com.sun.swingfx.collections.MapListenerHelper<K, V> listenerHelper;
    private final ObservableMap<K, V> backingMap;
    private final MapChangeListener<K, V> listener;

    private Set<K> keyset;
    private Collection<V> values;
    private Set<Entry<K, V>> entryset;

    public UnmodifiableObservableMap(ObservableMap<K, V> map) {
        this.backingMap = map;
        listener = c -> {
            callObservers(new MapAdapterChange<K, V>(UnmodifiableObservableMap.this, c));
        };
        this.backingMap.addListener(new WeakMapChangeListener<K, V>(listener));
    }

    private void callObservers(Change<? extends K,? extends V> c) {
        com.sun.swingfx.collections.MapListenerHelper.fireValueChangedEvent(listenerHelper, c);
    }

    @Override
    public void addListener(InvalidationListener listener) {
        listenerHelper = com.sun.swingfx.collections.MapListenerHelper.addListener(listenerHelper, listener);
    }

    @Override
    public void removeListener(InvalidationListener listener) {
        listenerHelper = com.sun.swingfx.collections.MapListenerHelper.removeListener(listenerHelper, listener);
    }

    @Override
    public void addListener(MapChangeListener<? super K, ? super V> observer) {
        listenerHelper = com.sun.swingfx.collections.MapListenerHelper.addListener(listenerHelper, observer);
    }

    @Override
    public void removeListener(MapChangeListener<? super K, ? super V> observer) {
        listenerHelper = MapListenerHelper.removeListener(listenerHelper, observer);
    }

    @Override
    public int size() {
        return backingMap.size();
    }

    @Override
    public boolean isEmpty() {
        return backingMap.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return backingMap.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return backingMap.containsValue(value);
    }

    @Override
    public V get(Object key) {
        return backingMap.get(key);
    }

    @Override @ReturnsUnmodifiableCollection
    public Set<K> keySet() {
        if (keyset == null) {
            keyset = Collections.unmodifiableSet(backingMap.keySet());
        }
        return keyset;
    }

    @Override @ReturnsUnmodifiableCollection
    public Collection<V> values() {
        if (values == null) {
            values = Collections.unmodifiableCollection(backingMap.values());
        }
        return values;
    }

    @Override @ReturnsUnmodifiableCollection
    public Set<Entry<K,V>> entrySet() {
        if (entryset == null) {
            entryset = Collections.unmodifiableMap(backingMap).entrySet();
        }
        return entryset;
    }
}
