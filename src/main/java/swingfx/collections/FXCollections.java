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

package swingfx.collections;

import com.sun.swingfx.collections.ObservableListWrapper;
import com.sun.swingfx.collections.ObservableSequentialListWrapper;
import com.sun.swingfx.collections.SourceAdapterChange;
import com.sun.swingfx.collections.annotations.ReturnsUnmodifiableCollection;
import swingfx.beans.InvalidationListener;
import swingfx.beans.Observable;
import swingfx.util.Callback;

import java.util.AbstractList;
import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.RandomAccess;
import java.util.Set;

/**
 * Utility class that consists of static methods that are 1:1 copies of java.util.Collections methods.
 * <br><br>
 * The wrapper methods (like synchronizedObservableList or emptyObservableList) has exactly the same
 * functionality as the methods in Collections, with exception that they return ObservableList and are
 * therefore suitable for methods that require ObservableList on input.
 * <br><br>
 * The utility methods are here mainly for performance reasons. All methods are optimized in a way that
 * they yield only limited number of notifications. On the other hand, java.util.Collections methods
 * might call "modification methods" on an ObservableList multiple times, resulting in a number of notifications.
 *
 * @since JavaFX 2.0
 */
public class FXCollections {
    /** Not to be instantiated. */
    private FXCollections() { }

    /**
     * Constructs an ObservableList that is backed by the specified list.
     * Mutation operations on the ObservableList instance will be reported
     * to observers that have registered on that instance.<br>
     * Note that mutation operations made directly to the underlying list are
     * <em>not</em> reported to observers of any ObservableList that
     * wraps it.
     *
     * @param list a concrete List that backs this ObservableList
     * @return a newly created ObservableList
     */
    public static <E> swingfx.collections.ObservableList<E> observableList(List<E> list) {
        if (list == null) {
            throw new NullPointerException();
        }
        return list instanceof RandomAccess ? new ObservableListWrapper<E>(list) :
                new ObservableSequentialListWrapper<E>(list);
    }

    /**
     * Constructs an ObservableList that is backed by the specified list.
     * Mutation operations on the ObservableList instance will be reported
     * to observers that have registered on that instance.<br>
     * Note that mutation operations made directly to the underlying list are
     * <em>not</em> reported to observers of any ObservableList that
     * wraps it.
     * <br>
     * This list also reports mutations of the elements in it by using <code>extractor</code>.
     * Observable objects returned by extractor (applied to each list element) are listened for changes
     * and transformed into "update" change of ListChangeListener.
     *
     * @param list a concrete List that backs this ObservableList
     * @param extractor element to Observable[] convertor
     * @since JavaFX 2.1
     * @return a newly created ObservableList
     */
    public static <E> swingfx.collections.ObservableList<E> observableList(List<E> list, Callback<E, Observable[]> extractor) {
        if (list == null || extractor == null) {
            throw new NullPointerException();
        }
        return list instanceof RandomAccess ? new ObservableListWrapper<E>(list, extractor) :
            new ObservableSequentialListWrapper<E>(list, extractor);
    }

    /**
     * Creates a new empty observable list that is backed by an arraylist.
     * @see #observableList(java.util.List)
     * @return a newly created ObservableList
     */
    @SuppressWarnings("unchecked")
    public static <E> swingfx.collections.ObservableList<E> observableArrayList() {
        return observableList(new ArrayList());
    }

    /**
     * Creates a new observable array list with {@code items} added to it.
     * @return a newly created observableArrayList
     * @param items the items that will be in the new observable ArrayList
     * @see #observableArrayList()
     */
    public static <E> swingfx.collections.ObservableList<E> observableArrayList(E... items) {
        swingfx.collections.ObservableList<E> list = observableArrayList();
        list.addAll(items);
        return list;
    }

    /**
     * Creates a new observable array list and adds a content of collection {@code col}
     * to it.
     * @param col a collection which content should be added to the observableArrayList
     * @return a newly created observableArrayList
     */
    public static <E> swingfx.collections.ObservableList<E> observableArrayList(Collection<? extends E> col) {
        swingfx.collections.ObservableList<E> list = observableArrayList();
        list.addAll(col);
        return list;
    }

    /**
     * Creates and returns unmodifiable wrapper list on top of provided observable list.
     * @param list  an ObservableList that is to be wrapped
     * @return an ObserableList wrapper that is unmodifiable
     * @see Collections#unmodifiableList(java.util.List)
     */
    @ReturnsUnmodifiableCollection
    public static<E> swingfx.collections.ObservableList<E> unmodifiableObservableList(swingfx.collections.ObservableList<E> list) {
        if (list == null) {
            throw new NullPointerException();
        }
        return new UnmodifiableObservableListImpl<E>(list);
    }

    private static swingfx.collections.ObservableList EMPTY_OBSERVABLE_LIST = new EmptyObservableList();

    /**
     * Creates and empty unmodifiable observable list.
     * @return An empty unmodifiable observable list
     * @see Collections#emptyList()
     */
    @SuppressWarnings("unchecked")
    @ReturnsUnmodifiableCollection
    public static<E> swingfx.collections.ObservableList<E> emptyObservableList() {
        return EMPTY_OBSERVABLE_LIST;
    }

    /**
     * Creates an unmodifiable observable list with single element.
     * @param e the only elements that will be contained in this singleton observable list
     * @return a singleton observable list
     * @see Collections#singletonList(java.lang.Object)
     */
    @ReturnsUnmodifiableCollection
    public static<E> swingfx.collections.ObservableList<E> singletonObservableList(E e) {
        return new SingletonObservableList<E>(e);
    }

    private static swingfx.collections.ObservableSet EMPTY_OBSERVABLE_SET = new EmptyObservableSet();

    /**
     * Creates and empty unmodifiable observable set.
     * @return An empty unmodifiable observable set
     * @see Collections#emptySet()
     * @since JavaFX 8.0
     */
    @SuppressWarnings("unchecked")
    @ReturnsUnmodifiableCollection
    public static<E> swingfx.collections.ObservableSet<E> emptyObservableSet() {
        return EMPTY_OBSERVABLE_SET;
    }

    private static class EmptyObservableList<E> extends AbstractList<E> implements swingfx.collections.ObservableList<E> {

        private static final ListIterator iterator = new ListIterator() {

            @Override
            public boolean hasNext() {
                return false;
            }

            @Override
            public Object next() {
                throw new NoSuchElementException();
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }

            @Override
            public boolean hasPrevious() {
                return false;
            }

            @Override
            public Object previous() {
                throw new NoSuchElementException();
            }

            @Override
            public int nextIndex() {
                return 0;
            }

            @Override
            public int previousIndex() {
                return -1;
            }

            @Override
            public void set(Object e) {
                throw new UnsupportedOperationException();
            }

            @Override
            public void add(Object e) {
                throw new UnsupportedOperationException();
            }
        };

        public EmptyObservableList() {
        }

        @Override
        public final void addListener(InvalidationListener listener) {
        }

        @Override
        public final void removeListener(InvalidationListener listener) {
        }


        @Override
        public void addListener(swingfx.collections.ListChangeListener<? super E> o) {
        }

        @Override
        public void removeListener(swingfx.collections.ListChangeListener<? super E> o) {
        }

        @Override
        public int size() {
            return 0;
        }

        @Override
        public boolean contains(Object o) {
            return false;
        }

        @Override
        @SuppressWarnings("unchecked")
        public Iterator<E> iterator() {
            return iterator;
        }

        @Override
        public boolean containsAll(Collection<?> c) {
            return c.isEmpty();
        }

        @Override
        public E get(int index) {
            throw new IndexOutOfBoundsException();
        }

        @Override
        public int indexOf(Object o) {
            return -1;
        }

        @Override
        public int lastIndexOf(Object o) {
            return -1;
        }

        @Override
        @SuppressWarnings("unchecked")
        public ListIterator<E> listIterator() {
            return iterator;
        }

        @Override
        @SuppressWarnings("unchecked")
        public ListIterator<E> listIterator(int index) {
            if (index != 0) {
                throw new IndexOutOfBoundsException();
            }
            return iterator;
        }

        @Override
        public List<E> subList(int fromIndex, int toIndex) {
            if (fromIndex != 0 || toIndex != 0) {
                throw new IndexOutOfBoundsException();
            }
            return this;
        }

        @Override
        public boolean addAll(E... elements) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean setAll(E... elements) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean setAll(Collection<? extends E> col) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean removeAll(E... elements) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean retainAll(E... elements) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void remove(int from, int to) {
            throw new UnsupportedOperationException();
        }
    }

    private static class SingletonObservableList<E> extends AbstractList<E> implements swingfx.collections.ObservableList<E> {

        private final E element;

        public SingletonObservableList(E element) {
            if (element == null) {
                throw new NullPointerException();
            }
            this.element = element;
        }

        @Override
        public boolean addAll(E... elements) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean setAll(E... elements) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean setAll(Collection<? extends E> col) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean removeAll(E... elements) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean retainAll(E... elements) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void remove(int from, int to) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void addListener(InvalidationListener listener) {
        }

        @Override
        public void removeListener(InvalidationListener listener) {
        }

        @Override
        public void addListener(swingfx.collections.ListChangeListener<? super E> o) {
        }

        @Override
        public void removeListener(swingfx.collections.ListChangeListener<? super E> o) {
        }

        @Override
        public int size() {
            return 1;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public boolean contains(Object o) {
            return element.equals(o);
        }

        @Override
        public E get(int index) {
            if (index != 0) {
                throw new IndexOutOfBoundsException();
            }
            return element;
        }

    }

    private static class UnmodifiableObservableListImpl<T> extends swingfx.collections.ObservableListBase<T> implements swingfx.collections.ObservableList<T> {

        private final swingfx.collections.ObservableList<T> backingList;
        private final swingfx.collections.ListChangeListener<T> listener;

        public UnmodifiableObservableListImpl(swingfx.collections.ObservableList<T> backingList) {
            this.backingList = backingList;
            listener = c -> {
                fireChange(new SourceAdapterChange<T>(UnmodifiableObservableListImpl.this, c));
            };
            this.backingList.addListener(new swingfx.collections.WeakListChangeListener<T>(listener));
        }

        @Override
        public T get(int index) {
            return backingList.get(index);
        }

        @Override
        public int size() {
            return backingList.size();
        }

        @Override
        public boolean addAll(T... elements) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean setAll(T... elements) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean setAll(Collection<? extends T> col) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean removeAll(T... elements) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean retainAll(T... elements) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void remove(int from, int to) {
            throw new UnsupportedOperationException();
        }

    }

    private static class SynchronizedList<T> implements List<T> {
        final Object mutex;
        private final List<T> backingList;

        SynchronizedList(List<T> list, Object mutex) {
            this.backingList = list;
            this.mutex = mutex;
        }

        @Override
        public int size() {
            synchronized(mutex) {
                return backingList.size();
            }
        }

        @Override
        public boolean isEmpty() {
            synchronized(mutex) {
                return backingList.isEmpty();
            }
        }

        @Override
        public boolean contains(Object o) {
            synchronized(mutex) {
                return backingList.contains(o);
            }
        }

        @Override
        public Iterator<T> iterator() {
            return backingList.iterator();
        }

        @Override
        public Object[] toArray() {
            synchronized(mutex)  {
                return backingList.toArray();
            }
        }

        @Override
        public <T> T[] toArray(T[] a) {
            synchronized(mutex) {
                return backingList.toArray(a);
            }
        }

        @Override
        public boolean add(T e) {
            synchronized(mutex) {
                return backingList.add(e);
            }
        }

        @Override
        public boolean remove(Object o) {
            synchronized(mutex) {
                return backingList.remove(o);
            }
        }

        @Override
        public boolean containsAll(Collection<?> c) {
            synchronized(mutex) {
                return backingList.containsAll(c);
            }
        }

        @Override
        public boolean addAll(Collection<? extends T> c) {
            synchronized(mutex) {
                return backingList.addAll(c);
            }
        }

        @Override
        public boolean addAll(int index, Collection<? extends T> c) {
            synchronized(mutex) {
                return backingList.addAll(index, c);

            }
        }

        @Override
        public boolean removeAll(Collection<?> c) {
            synchronized(mutex) {
                return backingList.removeAll(c);
            }
        }

        @Override
        public boolean retainAll(Collection<?> c) {
            synchronized(mutex) {
                return backingList.retainAll(c);
            }
        }

        @Override
        public void clear() {
            synchronized(mutex) {
                backingList.clear();
            }
        }

        @Override
        public T get(int index) {
            synchronized(mutex) {
                return backingList.get(index);
            }
        }

        @Override
        public T set(int index, T element) {
            synchronized(mutex) {
                return backingList.set(index, element);
            }
        }

        @Override
        public void add(int index, T element) {
            synchronized(mutex) {
                backingList.add(index, element);
            }
        }

        @Override
        public T remove(int index) {
            synchronized(mutex) {
                return backingList.remove(index);
            }
        }

        @Override
        public int indexOf(Object o) {
            synchronized(mutex) {
                return backingList.indexOf(o);
            }
        }

        @Override
        public int lastIndexOf(Object o) {
            synchronized(mutex) {
                return backingList.lastIndexOf(o);
            }
        }

        @Override
        public ListIterator<T> listIterator() {
            return backingList.listIterator();
        }

        @Override
        public ListIterator<T> listIterator(int index) {
            synchronized(mutex) {
                return backingList.listIterator(index);
            }
        }

        @Override
        public List<T> subList(int fromIndex, int toIndex) {
            synchronized(mutex) {
                return new SynchronizedList<T>(backingList.subList(fromIndex, toIndex),
                        mutex);
            }
        }

        @Override
        public String toString() {
            synchronized(mutex) {
                return backingList.toString();
            }
        }

        @Override
        public int hashCode() {
            synchronized(mutex) {
                return backingList.hashCode();
            }
        }

        @Override
        public boolean equals(Object o) {
            synchronized(mutex) {
                return backingList.equals(o);
            }
        }

    }

    private static class EmptyObservableSet<E> extends AbstractSet<E> implements swingfx.collections.ObservableSet<E> {

        public EmptyObservableSet() {
        }

        @Override
        public void addListener(InvalidationListener listener) {
        }

        @Override
        public void removeListener(InvalidationListener listener) {
        }

        @Override
        public void addListener(swingfx.collections.SetChangeListener<? super E> listener) {
        }

        @Override
        public void removeListener(swingfx.collections.SetChangeListener<? super E> listener) {
        }

        @Override
        public int size() {
            return 0;
        }

        @Override
        public boolean isEmpty() {
            return true;
        }

        @Override
        public boolean contains(Object obj) {
            return false;
        }

        @Override
        public boolean containsAll(Collection<?> c) {
            return c.isEmpty();
        }

        @Override
        public Object[] toArray() {
            return new Object[0];
        }

        @Override
        public <E> E[] toArray(E[] a) {
            if (a.length > 0)
                a[0] = null;
            return a;
        }

        @Override
        public Iterator<E> iterator() {
            return new Iterator() {

                @Override
                public boolean hasNext() {
                    return false;
                }

                @Override
                public Object next() {
                    throw new NoSuchElementException();
                }

                @Override
                public void remove() {
                    throw new UnsupportedOperationException();
                }
            };
        }

    }

    private static class SynchronizedSet<E> implements Set<E> {
        final Object mutex;
        private final Set<E> backingSet;

        SynchronizedSet(Set<E> set, Object mutex) {
            this.backingSet = set;
            this.mutex = mutex;
        }

        SynchronizedSet(Set<E> set) {
            this(set, new Object());
        }

        @Override
        public int size() {
            synchronized(mutex) {
                return backingSet.size();
            }
        }

        @Override
        public boolean isEmpty() {
            synchronized(mutex) {
                return backingSet.isEmpty();
            }
        }

        @Override
        public boolean contains(Object o) {
            synchronized(mutex) {
                return backingSet.contains(o);
            }
        }

        @Override
        public Iterator<E> iterator() {
            return backingSet.iterator();
        }

        @Override
        public Object[] toArray() {
            synchronized(mutex) {
                return backingSet.toArray();
            }
        }

        @Override
        public <E> E[] toArray(E[] a) {
            synchronized(mutex) {
                return backingSet.toArray(a);
            }
        }

        @Override
        public boolean add(E e) {
            synchronized(mutex) {
                return backingSet.add(e);
            }
        }

        @Override
        public boolean remove(Object o) {
            synchronized(mutex) {
                return backingSet.remove(o);
            }
        }

        @Override
        public boolean containsAll(Collection<?> c) {
            synchronized(mutex) {
                return backingSet.containsAll(c);
            }
        }

        @Override
        public boolean addAll(Collection<? extends E> c) {
            synchronized(mutex) {
                return backingSet.addAll(c);
            }
        }

        @Override
        public boolean retainAll(Collection<?> c) {
            synchronized(mutex) {
                return backingSet.retainAll(c);
            }
        }

        @Override
        public boolean removeAll(Collection<?> c) {
            synchronized(mutex) {
                return backingSet.removeAll(c);
            }
        }

        @Override
        public void clear() {
            synchronized(mutex) {
                backingSet.clear();
            }
        }

        @Override
        public boolean equals(Object o) {
            if (o == this) {
                return true;
            }
            synchronized(mutex) {
                return backingSet.equals(o);
            }
        }

        @Override
        public int hashCode() {
            synchronized (mutex) {
                return backingSet.hashCode();
            }
        }
    }

    private static class EmptyObservableMap<K, V> extends AbstractMap<K, V> implements swingfx.collections.ObservableMap<K, V> {

        public EmptyObservableMap() {
        }

        @Override
        public void addListener(InvalidationListener listener) {
        }

        @Override
        public void removeListener(InvalidationListener listener) {
        }

        @Override
        public void addListener(swingfx.collections.MapChangeListener<? super K, ? super V> listener) {
        }

        @Override
        public void removeListener(swingfx.collections.MapChangeListener<? super K, ? super V> listener) {
        }

        @Override
        public int size() {
            return 0;
        }

        @Override
        public boolean isEmpty() {
            return true;
        }

        @Override
        public boolean containsKey(Object key) {
            return false;
        }

        @Override
        public boolean containsValue(Object value) {
            return false;
        }

        @Override
        public V get(Object key) {
            return null;
        }

        @Override
        public Set<K> keySet() {
            return emptyObservableSet();
        }

        @Override
        public Collection<V> values() {
            return emptyObservableSet();
        }

        @Override
        public Set<Map.Entry<K,V>> entrySet() {
            return emptyObservableSet();
        }

        @Override
        public boolean equals(Object o) {
            return (o instanceof Map) && ((Map<?,?>)o).isEmpty();
        }

        @Override
        public int hashCode() {
            return 0;
        }
    }

    private static class SynchronizedMap<K, V> implements Map<K, V> {
        final Object mutex;
        private final Map<K, V> backingMap;

        SynchronizedMap(Map<K, V> map, Object mutex) {
            backingMap = map;
            this.mutex = mutex;
        }

        SynchronizedMap(Map<K, V> map) {
            this(map, new Object());
        }

        @Override
        public int size() {
            synchronized (mutex) {
                return backingMap.size();
            }
        }

        @Override
        public boolean isEmpty() {
            synchronized (mutex) {
                return backingMap.isEmpty();
            }
        }

        @Override
        public boolean containsKey(Object key) {
            synchronized (mutex) {
                return backingMap.containsKey(key);
            }
        }

        @Override
        public boolean containsValue(Object value) {
            synchronized (mutex) {
                return backingMap.containsValue(value);
            }
        }

        @Override
        public V get(Object key) {
            synchronized (mutex) {
                return backingMap.get(key);
            }
        }

        @Override
        public V put(K key, V value) {
            synchronized (mutex) {
                return backingMap.put(key, value);
            }
        }

        @Override
        public V remove(Object key) {
            synchronized (mutex) {
                return backingMap.remove(key);
            }
        }

        @Override
        public void putAll(Map<? extends K, ? extends V> m) {
            synchronized (mutex) {
                backingMap.putAll(m);
            }
        }

        @Override
        public void clear() {
            synchronized (mutex) {
                backingMap.clear();
            }
        }

        private transient Set<K> keySet = null;
        private transient Set<Map.Entry<K,V>> entrySet = null;
        private transient Collection<V> values = null;

        @Override
        public Set<K> keySet() {
            synchronized(mutex) {
                if (keySet==null)
                    keySet = new SynchronizedSet<K>(backingMap.keySet(), mutex);
                return keySet;
            }
        }

        @Override
        public Collection<V> values() {
            synchronized(mutex) {
                if (values==null)
                    values = new SynchronizedCollection<V>(backingMap.values(), mutex);
                return values;
            }
        }

        @Override
        public Set<Entry<K, V>> entrySet() {
            synchronized(mutex) {
                if (entrySet==null)
                    entrySet = new SynchronizedSet<Map.Entry<K,V>>(backingMap.entrySet(), mutex);
                return entrySet;
            }
        }

        @Override
        public boolean equals(Object o) {
            if (o == this) {
                return true;
            }
            synchronized(mutex) {
                return backingMap.equals(o);
            }
        }

        @Override
        public int hashCode() {
            synchronized(mutex) {
                return backingMap.hashCode();
            }
        }

    }

    private static class SynchronizedCollection<E> implements Collection<E> {

        private final Collection<E> backingCollection;
        final Object mutex;

        SynchronizedCollection(Collection<E> c, Object mutex) {
            backingCollection = c;
            this.mutex = mutex;
        }

        SynchronizedCollection(Collection<E> c) {
            this(c, new Object());
        }

        @Override
        public int size() {
            synchronized (mutex) {
                return backingCollection.size();
            }
        }

        @Override
        public boolean isEmpty() {
            synchronized (mutex) {
                return backingCollection.isEmpty();
            }
        }

        @Override
        public boolean contains(Object o) {
            synchronized (mutex) {
                return backingCollection.contains(o);
            }
        }

        @Override
        public Iterator<E> iterator() {
            return backingCollection.iterator();
        }

        @Override
        public Object[] toArray() {
            synchronized (mutex) {
                return backingCollection.toArray();
            }
        }

        @Override
        public <T> T[] toArray(T[] a) {
            synchronized (mutex) {
                return backingCollection.toArray(a);
            }
        }

        @Override
        public boolean add(E e) {
            synchronized (mutex) {
                return backingCollection.add(e);
            }
        }

        @Override
        public boolean remove(Object o) {
            synchronized (mutex) {
                return backingCollection.remove(o);
            }
        }

        @Override
        public boolean containsAll(Collection<?> c) {
            synchronized (mutex) {
                return backingCollection.containsAll(c);
            }
        }

        @Override
        public boolean addAll(Collection<? extends E> c) {
            synchronized (mutex) {
                return backingCollection.addAll(c);
            }
        }

        @Override
        public boolean removeAll(Collection<?> c) {
            synchronized (mutex) {
                return backingCollection.removeAll(c);
            }
        }

        @Override
        public boolean retainAll(Collection<?> c) {
            synchronized (mutex) {
                return backingCollection.retainAll(c);
            }
        }

        @Override
        public void clear() {
            synchronized (mutex) {
                backingCollection.clear();
            }
        }
    }

}
