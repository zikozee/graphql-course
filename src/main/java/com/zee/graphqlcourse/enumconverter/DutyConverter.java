package com.zee.graphqlcourse.enumconverter;

import com.zee.graphqlcourse.codegen.types.Duty;
import com.zee.graphqlcourse.exception.ProcessException;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Arrays;

/**
 * @author : Ezekiel Eromosei
 * @code @created : 06 Oct, 2024
 */

@Converter(autoApply = true)
public class DutyConverter implements AttributeConverter<Duty, String> {
    @Override
    public String convertToDatabaseColumn(Duty attribute) {
        return attribute.toString();
    }

    @Override
    public Duty convertToEntityAttribute(String dbData) {
        return Arrays.stream(Duty.values())
                .filter(duty -> duty.name().equalsIgnoreCase(dbData))
                .findFirst().orElseThrow(ProcessException::new);
    }
}
