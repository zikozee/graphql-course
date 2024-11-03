package com.zee.graphqlcourse.enumconverter;

import com.zee.graphqlcourse.codegen.types.BusinessType;
import com.zee.graphqlcourse.exception.ProcessException;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Arrays;

/**
 * @author : Ezekiel Eromosei
 * @code @created : 06 Oct, 2024
 */

@Converter(autoApply = true)
public class BusinessTypeConverter implements AttributeConverter<BusinessType, String> {
    @Override
    public String convertToDatabaseColumn(BusinessType attribute) {
        return attribute.name();
    }

    @Override
    public BusinessType convertToEntityAttribute(String dbData) {
        return Arrays.stream(BusinessType.values())
                .filter(type -> type.name().equalsIgnoreCase(dbData))
                .findFirst().orElseThrow(ProcessException::new);
    }
}
