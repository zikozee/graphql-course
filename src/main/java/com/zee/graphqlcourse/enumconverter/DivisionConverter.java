package com.zee.graphqlcourse.enumconverter;

import com.zee.graphqlcourse.codegen.types.Division;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Arrays;

/**
 * @author : Ezekiel Eromosei
 * @code @created : 06 Oct, 2024
 */

@Converter(autoApply = true)
public class DivisionConverter implements AttributeConverter<Division, String> {
    @Override
    public String convertToDatabaseColumn(Division attribute) {
        return attribute.name();
    }

    @Override
    public Division convertToEntityAttribute(String dbData) {
        return Arrays.stream(Division.values())
                .filter(div -> div.name().equalsIgnoreCase(dbData))
                .findFirst().orElseThrow(IllegalArgumentException::new);
    }
}
