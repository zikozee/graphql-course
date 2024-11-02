package com.zee.graphqlcourse.search;

import com.zee.graphqlcourse.codegen.types.SortDirection;
import com.zee.graphqlcourse.codegen.types.SortInput;
import org.springframework.data.domain.Sort;

import java.text.MessageFormat;
import java.util.Collections;
import java.util.List;

/**
 * @author : Ezekiel Eromosei
 * @code @created : 02 Nov, 2024
 */

public final class SpecUtil {
    private SpecUtil() {
        throw new RuntimeException("cannot be instantiated");
    }

    public static String contains(String keyword) {
        return MessageFormat.format("%{0}%", keyword);
    }

    public static List<Sort.Order> buildSortOrder(List<SortInput> sortInputs) {
        if(sortInputs == null || sortInputs.isEmpty()) return Collections.emptyList();

        return sortInputs.stream()
                .map(srt ->
                        srt.getDirection().equals(SortDirection.ASCENDING)
                                ? Sort.Order.asc(srt.getField())
                                : Sort.Order.desc(srt.getField())
                )
                .toList();
    }
}
