package br.com.moryta.myfirstapp.enums;

/**
 * Created by moryta on 17/08/2017.
 */

public enum PetTypeEnum {
    DOG(1),
    CAT(2),
    OTHER(3);

    public final int code;

    PetTypeEnum(Integer code) {
        this.code = code;
    }
}
