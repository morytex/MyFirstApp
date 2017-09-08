package br.com.moryta.myfirstapp.model.converters;

import org.greenrobot.greendao.converter.PropertyConverter;

import br.com.moryta.myfirstapp.enums.PetTypeEnum;

/**
 * Created by moryta on 19/08/2017.
 */

public class PetTypeConverter implements PropertyConverter<PetTypeEnum, Integer> {
    @Override
    public PetTypeEnum convertToEntityProperty(Integer databaseValue) {
        if (databaseValue == null) {
            return null;
        }
        for (PetTypeEnum petTypeEnum: PetTypeEnum.values()) {
            if (petTypeEnum.code == databaseValue) {
                return petTypeEnum;
            }
        }
        return PetTypeEnum.OTHER;
    }

    @Override
    public Integer convertToDatabaseValue(PetTypeEnum entityProperty) {
        return entityProperty == null ? null : entityProperty.code;
    }
}
