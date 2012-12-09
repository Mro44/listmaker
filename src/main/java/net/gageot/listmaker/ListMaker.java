/*
 * This file is part of ListMaker.
 * 
 * Copyright (C) 2012 "David Gageot" <david@gageot.net>,
 * 
 * ListMaker is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * ListMaker is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * ListMaker. If not, see <http://www.gnu.org/licenses/>.
 */
package net.gageot.listmaker;

import static com.google.common.base.Preconditions.*;
import java.util.*;
import javax.annotation.Nullable;
import com.google.common.base.*;
import com.google.common.collect.*;
import com.google.common.primitives.*;

/**
 * ListMaker is a fluent interface list maker for use with Guava.<br/>
 * It makes it easy to start from an {@link Iterable} and apply transformations,
 * filtering and operations on it. Most of these can be combined.
 * 
 * @param <T>
 *            the type of elements returned by the iterator.
 * @author David Gageot
 * @since 1.0
 */
public final class ListMaker<T> implements Iterable<T> {
	private final Iterable<T> values;

	private ListMaker(Iterable<T> values) {
		checkNotNull(values);
		this.values = values;
	}

	/**
	 * Creates a ListMaker from an {@link Iterable}.
	 * <p/>
	 * <b>Note:</b> Trying to create a {@code ListMaker} from another
	 * {@code ListMaker} returns the original {@code ListMaker}.
	 * 
	 * @return a new {@code ListMaker} or the {@code ListMaker} passed as
	 *         {@code values}
	 */
	public static <T> ListMaker<T> with(Iterable<T> values) {
		checkNotNull(values);
		if (values instanceof ListMaker<?>) {
			return (ListMaker<T>) values;
		}
		return new ListMaker<T>(values);
	}

	/**
	 * Creates an empty {@code ListMaker}.
	 * 
	 * @return an empty {@code ListMaker}
	 */
	public static <T> ListMaker<T> with() {
		return new ListMaker<T>(Collections.<T> emptyList());
	}

	/**
	 * Creates a {@code ListMaker} from a list of values.
	 * 
	 * @return a new {@code ListMaker}
	 */
	public static <T> ListMaker<T> with(T... values) {
		checkNotNull(values);
		return new ListMaker<T>(Arrays.asList(values));
	}

	/**
	 * Creates a {@code ListMaker} from a list of ints.
	 * 
	 * @return a new {@code ListMaker}
	 * @since 1.1
	 */
	public static ListMaker<Integer> with(int[] values) {
		checkNotNull(values);
		return new ListMaker<Integer>(Ints.asList(values));
	}

	/**
	 * Creates a {@code ListMaker} from a list of chars.
	 * 
	 * @return a new {@code ListMaker}
	 * @since 1.1
	 */
	public static ListMaker<Character> with(char[] values) {
		checkNotNull(values);
		return new ListMaker<Character>(Chars.asList(values));
	}

	/**
	 * Creates a {@code ListMaker} from a list of booleans.
	 * 
	 * @return a new {@code ListMaker}
	 * @since 1.1
	 */
	public static ListMaker<Boolean> with(boolean[] values) {
		checkNotNull(values);
		return new ListMaker<Boolean>(Booleans.asList(values));
	}

	/**
	 * Creates a {@code ListMaker} from a list of longs.
	 * 
	 * @return a new {@code ListMaker}
	 * @since 1.1
	 */
	public static ListMaker<Long> with(long[] values) {
		checkNotNull(values);
		return new ListMaker<Long>(Longs.asList(values));
	}

	/**
	 * Creates a {@code ListMaker} from a list of floats.
	 * 
	 * @return a new {@code ListMaker}
	 * @since 1.1
	 */
	public static ListMaker<Float> with(float[] values) {
		checkNotNull(values);
		return new ListMaker<Float>(Floats.asList(values));
	}

	/**
	 * Creates a {@code ListMaker} from a list of doubles.
	 * 
	 * @return a new {@code ListMaker}
	 * @since 1.1
	 */
	public static ListMaker<Double> with(double[] values) {
		checkNotNull(values);
		return new ListMaker<Double>(Doubles.asList(values));
	}

	/**
	 * Creates a {@code ListMaker} from a list of bytes.
	 * 
	 * @return a new {@code ListMaker}
	 * @since 1.1
	 */
	public static ListMaker<Byte> with(byte[] values) {
		checkNotNull(values);
		return new ListMaker<Byte>(Bytes.asList(values));
	}

	/**
	 * Creates a {@code ListMaker} from a list of shorts.
	 * 
	 * @return a new {@code ListMaker}
	 * @since 1.1
	 */
	public static ListMaker<Short> with(short[] values) {
		checkNotNull(values);
		return new ListMaker<Short>(Shorts.asList(values));
	}

	/**
	 * TODO.
	 * 
	 * @return TODO.
	 */
	public static <V, T> Predicate<? super T> where(Function<? super T, ? extends V> transform, Predicate<? super V> predicate) {
		checkNotNull(transform);
		checkNotNull(predicate);
		return Predicates.compose(predicate, transform);
	}

	/**
	 * TODO.
	 * 
	 * @return TODO.
	 */
	public static <V, T> Predicate<? super T> whereEquals(Function<? super T, ? extends V> transform, @Nullable V valueToCompareWith) {
		checkNotNull(transform);
		return where(transform, Predicates.equalTo(valueToCompareWith));
	}

	/**
	 * Returns a filtered {@code ListMaker} that keeps only the elements that
	 * satisfy a {@code predicate}.
	 * 
	 * @param predicate
	 *            the predicate to satisfy to be included
	 * @return a filtered {@code ListMaker}
	 */
	public ListMaker<T> only(Predicate<? super T> predicate) {
		return filter(predicate);
	}

	/**
	 * Returns a filtered {@code ListMaker} that keeps only the elements that
	 * satisfy a {@code predicate}.
	 * 
	 * @param predicate
	 *            the predicate to satisfy to be included
	 * @return a filtered {@code ListMaker}
	 */
	public ListMaker<T> filter(Predicate<? super T> predicate) {
		checkNotNull(predicate);
		return new ListMaker<T>(Iterables.filter(values, predicate));
	}

	/**
	 * Returns a filtered {@code ListMaker} that exclude the elements that
	 * satisfy a {@code predicate}.
	 * 
	 * @param predicate
	 *            the predicate to satisfy to be excluded
	 * @return a filtered {@code ListMaker}
	 */
	public ListMaker<T> exclude(Predicate<? super T> predicate) {
		checkNotNull(predicate);
		return only(Predicates.not(predicate));
	}

	/**
	 * Returns a filtered {@code ListMaker} that excludes given {@code values}.
	 * 
	 * @param valuesToExclude
	 *            the values to exclude
	 * @return a filtered {@code ListMaker}
	 */
	public ListMaker<T> exclude(T... valuesToExclude) {
		checkNotNull(valuesToExclude);
		return exclude(Arrays.asList(valuesToExclude));
	}

	/**
	 * Returns a filtered {@code ListMaker} that excludes given collection of
	 * {@code values}.
	 * 
	 * @param valuesToExclude
	 *            the collection of values to exclude
	 * @return a filtered {@code ListMaker}
	 */
	public ListMaker<T> exclude(Collection<? extends T> valuesToExclude) {
		checkNotNull(valuesToExclude);
		return exclude(Predicates.in(valuesToExclude));
	}

	/**
	 * Returns the first element in the {@code ListMaker}.
	 * 
	 * @return the first element in the {@code ListMaker}
	 * @throws NoSuchElementException
	 *             if the {@code ListMaker} is empty
	 */
	public T first() {
		return values.iterator().next();
	}

	/**
	 * Returns the first element in the {@code ListMaker} that satisfies the
	 * given {@code predicate}.
	 * 
	 * @param predicate
	 *            the predicate to satisfy
	 * @return the first element in the {@code ListMaker} that satisfies the
	 *         {@code predicate}
	 * @throws NoSuchElementException
	 *             if no element satisfies the {@code predicate}
	 */
	public T first(Predicate<? super T> predicate) {
		checkNotNull(predicate);
		return Iterables.find(values, predicate);
	}

	/**
	 * Returns the first element in the {@code ListMaker} or
	 * {@code defaultValue} if the {@code ListMaker} is empty.
	 * 
	 * @param defaultValue
	 *            the default value to return if the {@code ListMaker} is empty
	 * @return the first element in the {@code ListMaker} or the default value
	 */
	public T firstOrDefault(@Nullable T defaultValue) {
		return Iterables.getFirst(values, defaultValue);
	}

	/**
	 * Returns the first element in the {@code ListMaker} that satisfies the
	 * given {@code predicate} or {@code defaultValue} if no element satisfies
	 * the {@code predicate}.
	 * 
	 * @param predicate
	 *            the predicate to satisfy
	 * @param defaultValue
	 *            the default value to return if no element satisfies the
	 *            {@code predicate}
	 * @return the first element in the {@code ListMaker} that satisfies the
	 *         {@code predicate} or the default value
	 */
	public T firstOrDefault(Predicate<? super T> predicate, @Nullable T defaultValue) {
		checkNotNull(predicate);
		return Iterables.find(values, predicate, defaultValue);
	}

	/**
	 * TODO.
	 * 
	 * @return TODO.
	 */
	public boolean contains(Predicate<? super T> predicate) {
		checkNotNull(predicate);
		return Iterables.any(values, predicate);
	}

	/**
	 * Returns the number of elements in the {@code ListMaker} that satisfies
	 * the given {@code predicate}.
	 * 
	 * @param predicate
	 *            the predicate to satisfy
	 * @return the number of elements in the {@code ListMaker} that satisfies
	 *         the {@code predicate}
	 */
	public int count(Predicate<? super T> predicate) {
		checkNotNull(predicate);
		return Iterables.size(Iterables.filter(values, predicate));
	}

	/**
	 * TODO.
	 * 
	 * @return TODO.
	 */
	public ListMaker<T> sortOn(Ordering<? super T> ordering) {
		checkNotNull(ordering);
		return new ListMaker<T>(ordering.sortedCopy(values));
	}

	/**
	 * TODO.
	 * 
	 * @return TODO.
	 */
	public ListMaker<T> sortOn(Comparator<? super T> comparator) {
		checkNotNull(comparator);
		return sortOn(Ordering.from(comparator));
	}

	/**
	 * TODO.
	 * 
	 * @return TODO.
	 */
	public ListMaker<T> sortOn(Function<? super T, ? extends Comparable<?>> transform) {
		checkNotNull(transform);
		return sortOn(Ordering.natural().onResultOf(transform));
	}

	/**
	 * Returns a filtered {@code ListMaker} that excludes {@code null} values.
	 * 
	 * @return a filtered {@code ListMaker}
	 */
	public ListMaker<T> notNulls() {
		return only(Predicates.<T> notNull());
	}

	/**
	 * TODO.
	 * 
	 * @return TODO.
	 */
	public <R> ListMaker<R> to(Function<? super T, R> transform) {
		return map(transform);
	}

	/**
	 * TODO.
	 * 
	 * @return TODO.
	 */
	public <R> ListMaker<R> map(Function<? super T, R> transform) {
		checkNotNull(transform);
		return new ListMaker<R>(Iterables.transform(values, transform));
	}

	/**
	 * TODO.
	 * 
	 * @return TODO.
	 */
	public <R, C extends Iterable<R>> ListMaker<R> flatMap(Function<? super T, C> transform) {
		checkNotNull(transform);
		return new ListMaker<R>(Iterables.concat(Iterables.transform(values, transform)));
	}

	/**
	 * TODO.
	 * 
	 * @return TODO.
	 * @since 1.1
	 */
	public ListMaker<T> concat(Iterable<T> tail) {
		checkNotNull(tail);
		return with(Iterables.concat(this, tail));
	}

	/**
	 * TODO.
	 * 
	 * @return TODO.
	 * @since 1.1
	 */
	public ListMaker<T> concat(T... tail) {
		checkNotNull(tail);
		return with(Iterables.concat(this, Arrays.asList(tail)));
	}

	/**
	 * TODO.
	 * 
	 * @return TODO.
	 */
	public T max(@Nullable Ordering<? super T> ordering) {
		return ordering.max(values);
	}

	/**
	 * TODO.
	 * 
	 * @return TODO.
	 */
	public T min(@Nullable Ordering<? super T> ordering) {
		return ordering.min(values);
	}

	/**
	 * TODO.
	 * 
	 * @return TODO.
	 */
	public <V extends Comparable<V>> T maxOnResultOf(Function<? super T, V> transform) {
		checkNotNull(transform);
		return Ordering.natural().onResultOf(transform).max(values);
	}

	/**
	 * TODO.
	 * 
	 * @return TODO.
	 */
	public <V> V reduce(@Nullable V initialValue, Accumulator<V, T> operator) {
		checkNotNull(operator);
		V accumulator = initialValue;
		for (T value : this) {
			accumulator = operator.apply(accumulator, value);
		}

		return accumulator;
	}

	/**
	 * TODO.
	 * 
	 * @return TODO.
	 */
	public <V extends Comparable<V>> T minOnResultOf(Function<? super T, V> transform) {
		checkNotNull(transform);
		return Ordering.natural().onResultOf(transform).min(values);
	}

	/**
	 * TODO.
	 * 
	 * @return TODO.
	 */
	public <C extends Collection<T>> C copyTo(C destination) {
		checkNotNull(destination);
		Iterables.addAll(destination, values);
		return destination;
	}

	/**
	 * Returns a new {@code ArrayList} that contains all the values in the
	 * {@code ListMaker}.
	 * 
	 * @return the list
	 */
	public ArrayList<T> toList() {
		return Lists.newArrayList(values);
	}

	/**
	 * Returns a new {@code ImmutableList} that contains all the values in the
	 * {@code ListMaker}.
	 * 
	 * @return the list
	 */
	public ImmutableList<T> toImmutableList() {
		return ImmutableList.copyOf(values);
	}

	/**
	 * TODO.
	 * 
	 * @return TODO.
	 */
	public String join(@Nullable String separator) {
		return Joiner.on(separator).join(values);
	}

	/**
	 * TODO.
	 * 
	 * @return TODO.
	 * @since 1.1
	 */
	public String join() {
		return join("");
	}

	/**
	 * TODO.
	 * 
	 * @return TODO.
	 */
	public T[] toArray(Class<T> type) {
		checkNotNull(type);
		return Iterables.toArray(values, type);
	}

	/**
	 * Returns a new {@code HashSet} that contains all the values in the
	 * {@code ListMaker}.
	 * 
	 * @return the set
	 */
	public HashSet<T> toSet() {
		return Sets.newHashSet(values);
	}

	/**
	 * TODO.
	 * 
	 * @return TODO.
	 */
	public <R> Set<R> toSet(Function<? super T, R> transform) {
		checkNotNull(transform);
		return Sets.newHashSet(Iterables.transform(values, transform));
	}

	/**
	 * Returns a new {@code TreeSet} that contains all the values in the
	 * {@code ListMaker}.
	 * 
	 * @return the set
	 */
	public TreeSet<T> toTreeSet() {
		return copyTo(new TreeSet<T>());
	}

	/**
	 * TODO.
	 * 
	 * @return TODO.
	 */
	public TreeSet<T> toTreeSet(Comparator<? super T> comparator) {
		checkNotNull(comparator);
		return copyTo(new TreeSet<T>(comparator));
	}

	/**
	 * TODO.
	 * 
	 * @return TODO.
	 */
	public <R> TreeSet<R> toTreeSet(Function<? super T, R> transform, @Nullable Comparator<? super R> ordering) {
		checkNotNull(transform);
		TreeSet<R> set = new TreeSet<R>(ordering);
		Iterables.addAll(set, Iterables.transform(values, transform));
		return set;
	}

	/**
	 * TODO.
	 * 
	 * @return TODO.
	 */
	public <R extends Comparable<R>> TreeSet<R> toTreeSet(Function<? super T, R> transform) {
		checkNotNull(transform);
		return toTreeSet(transform, null);
	}

	/**
	 * Returns a new {@code ImmutableSet} that contains all the values in the
	 * {@code ListMaker}.
	 * 
	 * @return the set
	 */
	public ImmutableSet<T> toImmutableSet() {
		return ImmutableSet.copyOf(values);
	}

	/**
	 * TODO.
	 * 
	 * @return TODO.
	 */
	public <R> ImmutableSet<R> toImmutableSet(Function<? super T, R> transform) {
		checkNotNull(transform);
		return ImmutableSet.copyOf(Iterables.transform(values, transform));
	}

	/**
	 * TODO.
	 * 
	 * @return TODO.
	 */
	public <K> ListMultimap<K, T> groupBy(Function<? super T, K> toKey) {
		checkNotNull(toKey);
		ListMultimap<K, T> map = ArrayListMultimap.create();

		for (T value : values) {
			map.put(toKey.apply(value), value);
		}

		return map;
	}

	/**
	 * Returns the last element of the {@code ListMaker}.
	 * 
	 * @return the last element of the {@code ListMaker}
	 * @throws NoSuchElementException
	 *             if the {@code ListMaker} is empty
	 */
	public T getLast() {
		return Iterables.getLast(values);
	}

	/**
	 * Returns the number of elements in the {@code ListMaker}.
	 * 
	 * @return the number of elements
	 */
	public int size() {
		return Iterables.size(values);
	}

	/**
	 * TODO.
	 * 
	 * @return TODO.
	 */
	public <K> Map<K, T> indexBy(Function<? super T, ? extends K> toKey) {
		checkNotNull(toKey);
		Map<K, T> map = Maps.newHashMap();

		for (T value : values) {
			map.put(toKey.apply(value), value);
		}

		return map;
	}

	/**
	 * Returns an iterator over a set of elements of type T.
	 * 
	 * @return an {@link Iterator}
	 */
	@Override
	public Iterator<T> iterator() {
		return values.iterator();
	}

	/**
	 * Returns {@code true} if the {@code ListMaker} contains no value.
	 * 
	 * @return {@code true} if the {@code ListMaker} is empty
	 */
	public boolean isEmpty() {
		return Iterables.isEmpty(values);
	}

	/**
	 * Execute the given {@code action} on each element of type T.
	 */
	public void forEach(Action<? super T> action) {
		checkNotNull(action);
		for (T value : values) {
			action.execute(value);
		}
	}

	/**
	 * Execute the given {@code action} on each element of type T.
	 */
	public void forEach(ActionWithIndex<? super T> action) {
		checkNotNull(action);
		int index = 0;
		for (T value : values) {
			action.execute(index++, value);
		}
	}

	/**
	 * TODO.
	 * 
	 * @return TODO.
	 */
	@Override
	public boolean equals(Object o) {
		return (this == o) || ((o instanceof Iterable<?>) && Iterables.elementsEqual(this, (Iterable<?>) o));
	}

	/**
	 * TODO.
	 * 
	 * @return TODO.
	 */
	@Override
	public int hashCode() {
		return values.hashCode();
	}

	/**
	 * Returns a string representation of the {@code ListMaker} elements, with
	 * the format {@code [e1, e2, ..., en]}.
	 * 
	 * @return a string representation of the {@code ListMaker} elements
	 */
	@Override
	public String toString() {
		return values.toString();
	}
}
