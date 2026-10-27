package com.epam.training.travel.converter;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Component;

import com.epam.training.travel.domain.Travelling;
import com.epam.training.travel.model.TravellingModel;

@Component
public class TravellingConverter {

    private final ModelMapper modelMapper = new ModelMapper();

    public TravellingConverter() {
        modelMapper.addConverter(new StringToCategoryConverter());
        modelMapper.addConverter(new CategoryToStringConverter());
    }

    public Travelling convert(TravellingModel travellingModel) {
        return modelMapper.map(travellingModel, Travelling.class);
    }

    public TravellingModel convert(Travelling travelling) {
        return modelMapper.map(travelling, TravellingModel.class);
    }

    public List<TravellingModel> convert(List<Travelling> travellings) {
        return modelMapper.map(travellings, new TypeToken<List<TravellingModel>>() {
        }.getType());
    }
}
