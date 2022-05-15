package io.okkio.util;

import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public class CollectionUtil extends CollectionUtils{
    /**
     * Return {@code true} if the supplied Collection is {@code null} or empty.
     * Otherwise, return {@code false}.
     *
     * @param collection
     *            the Collection to check
     * @return whether the given Collection is empty
     */
    public static boolean isNotEmpty(Collection<?> collection) {
        return !CollectionUtils.isEmpty(collection);
    }

    public static <T> List<T> convert(Set<T> from) {
        if (CollectionUtils.isEmpty(from)) {
            return new ArrayList<>();
        }
        return new ArrayList<>(from);
    }
}
