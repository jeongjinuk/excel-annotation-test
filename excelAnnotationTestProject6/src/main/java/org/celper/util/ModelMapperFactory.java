package org.celper.util;

import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.modelmapper.convention.MatchingStrategies;

/**
 * The type Model mapper factory.
 */
public final class ModelMapperFactory {
    private ModelMapperFactory() {
        throw new IllegalStateException("Factory class");
    }

    /**
     * Default model mapper model mapper.
     *
     * @return the model mapper
     */
    public static ModelMapper defaultModelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        modelMapper.getConfiguration().setFieldMatchingEnabled(true);
        modelMapper.getConfiguration().setFieldAccessLevel(Configuration.AccessLevel.PRIVATE);
        addConverter(modelMapper);
        return modelMapper;
    }
    private static void addConverter(ModelMapper modelMapper) {
        modelMapper.addConverter(new Converters.DoubleToDate());
        modelMapper.addConverter(new Converters.DoubleToLocalDateTime());
        modelMapper.addConverter(new Converters.DoubleToLocalDate());
        modelMapper.addConverter(new Converters.DoubleToLocalTime());
        modelMapper.addConverter(new Converters.StringToDouble());
        modelMapper.addConverter(new Converters.StringToBoolean());
    }
}

