/*
 * This file is part of ListMaker.
 *
 * Copyright (C) 2012
 * "David Gageot" <david@gageot.net>,
 *
 * ListMaker is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * ListMaker is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with ListMaker. If not, see <http://www.gnu.org/licenses/>.
 */
package net.gageot.listmaker;

import com.google.common.base.*;
import com.google.common.collect.*;

import javax.annotation.*;
import java.util.*;

import static com.google.common.base.Preconditions.*;

/**
 * TODO.
 *
 * @param <T> the type of elements returned by the iterator.
 * @since 1.0
 */
public class ListMaker<T> implements Iterable<T> {
	private final Iterable<T> values;

	/**
	 * TODO.
	 */
	private ListMaker(Iterable<T> values) {
		checkNotNull(values);
		this.values = values;
	}

	/**
	 * TODO.
	 *
	 * @return TODO.
	 */
	public static <T> ListMaker<T> with(Iterable<T> values) {
		checkNotNull(values);
		if (values instanceof ListMaker<?>) {
			return (ListMaker<T>) values;
		}
		return new ListMaker<T>(values);
	}

	/**
	 * TODO.
	 *
	 * @return TODO.
	 */
	@SuppressWarnings("unchecked")
	public static <T> ListMaker<T> with() {
		return new ListMaker<T>(Collections.<T>emptyList());
	}

	/**
	 * TODO.
	 *
	 * @return TODO.
	 */
	public static <T> ListMaker<T> with(T... values) {
		checkNotNull(values);
		return new ListMaker<T>(Arrays.asList(values));
	}

	/**
	 * TODO.
	 *
	 * @return TODO.
	 */
	public static <V, T> Predicate<? super T> where(Function<? super T, ? extends V> transform, Predicate<? super V> condition) {
		checkNotNull(transform);
		checkNotNull(condition);
		return Predicates.compose(condition, transform);
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
	 * TODO.
	 *
	 * @return TODO.
	 */
	public ListMaker<T> only(Predicate<? super T> condition) {
		checkNotNull(condition);
		return new ListMaker<T>(Iterables.filter(values, condition));
	}

	/**
	 * TODO.
	 *
	 * @return TODO.
	 */
	public ListMaker<T> exclude(Predicate<? super T> condition) {
		checkNotNull(condition);
		return only(Predicates.not(condition));
	}

	/**
	 * TODO.
	 *
	 * @return TODO.
	 */
	public ListMaker<T> exclude(T... valuesToExclude) {
		checkNotNull(valuesToExclude);
		return exclude(Arrays.asList(valuesToExclude));
	}

	/**
	 * TODO.
	 *
	 * @return TODO.
	 */
	public ListMaker<T> exclude(Collection<? extends T> valuesToExclude) {
		checkNotNull(valuesToExclude);
		return exclude(Predicates.in(valuesToExclude));
	}

	/**
	 * TODO.
	 *
	 * @return TODO.
	 */
	public T first() {
		return values.iterator().next();
	}

	/**
	 * TODO.
	 *
	 * @return TODO.
	 */
	public T first(Predicate<? super T> condition) {
		checkNotNull(condition);
		return Iterables.find(values, condition);
	}

	/**
	 * TODO.
	 *
	 * @return TODO.
	 */
	public T firstOrDefault(@Nullable T defaultValue) {
		return Iterables.getFirst(values, defaultValue);
	}

	/**
	 * TODO.
	 *
	 * @return TODO.
	 */
	public T firstOrDefault(Predicate<? super T> condition, @Nullable T defaultValue) {
		checkNotNull(condition);
		return Iterables.find(values, condition, defaultValue);
	}

	/**
	 * TODO.
	 *
	 * @return TODO.
	 */
	public boolean contains(Predicate<? super T> condition) {
		checkNotNull(condition);
		return Iterables.any(values, condition);
	}

	/**
	 * TODO.
	 *
	 * @return TODO.
	 */
	public int count(Predicate<? super T> condition) {
		checkNotNull(condition);
		return Iterables.size(Iterables.filter(values, condition));
	}

	/**
	 * TODO.
	 *
	 * @return TODO.
	 */
	public ListMaker<T> sortOn(Ordering<? super T> ordering) {
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
	 * TODO.
	 *
	 * @return TODO.
	 */
	public ListMaker<T> notNulls() {
		return only(Predicates.<T>notNull());
	}

	/**
	 * TODO.
	 *
	 * @return TODO.
	 */
	public <R> ListMaker<R> to(Function<? super T, R> transform) {
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
	 */
	public T max(Ordering<? super T> ordering) {
		return ordering.max(values);
	}

	/**
	 * TODO.
	 *
	 * @return TODO.
	 */
	public T min(Ordering<? super T> ordering) {
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
	 * TODO.
	 *
	 * @return TODO.
	 */
	public List<T> toList() {
		return Lists.newArrayList(values);
	}

	/**
	 * TODO.
	 *
	 * @return TODO.
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
	 */
	public T[] toArray(Class<T> type) {
		checkNotNull(type);
		return Iterables.toArray(values, type);
	}

	/**
	 * TODO.
	 *
	 * @return TODO.
	 */
	public Set<T> toSet() {
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
	 * TODO.
	 *
	 * @return TODO.
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
	public <R> TreeSet<R> toTreeSet(Function<? super T, R> transform, Comparator<? super R> ordering) {
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
	 * TODO.
	 *
	 * @return TODO.
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
	public T getLast() {
		return Iterables.getLast(values);
	}

	/**
	 * TODO.
	 *
	 * @return TODO.
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
	 * TODO.
	 *
	 * @return TODO.
	 */
	@Override
	public Iterator<T> iterator() {
		return values.iterator();
	}

	/**
	 * TODO.
	 *
	 * @return TODO.
	 */
	public boolean isEmpty() {
		return Iterables.isEmpty(values);
	}

	/**
	 * TODO.
	 *
	 * @return TODO.
	 */
	@Override
	public boolean equals(Object o) {
		return this == o || o instanceof Iterable<?> && Iterables.elementsEqual(this, (Iterable<?>) o);
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
	 * TODO.
	 *
	 * @return TODO.
	 */
	@Override
	public String toString() {
		return values.toString();
	}
}
