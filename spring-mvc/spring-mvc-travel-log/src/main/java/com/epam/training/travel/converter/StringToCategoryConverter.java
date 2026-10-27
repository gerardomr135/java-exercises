package com.epam.training.travel.converter;

import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;

import com.epam.training.travel.domain.Category;

public class StringToCategoryConverter implements Converter<String, Category> {
    @Override
    public Category convert(MappingContext<String, Category> context) {

        for (Category category : Category.values()) {
            if (category.getName().equals(context.getSource())) {
                return category;
            }
        }
        throw new IllegalArgumentException("Can not convert " + context.getSource() + " to Category");
    }
}
