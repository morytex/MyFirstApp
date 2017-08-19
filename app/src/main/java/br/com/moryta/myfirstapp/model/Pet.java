package br.com.moryta.myfirstapp.model;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;

import java.util.Date;

import br.com.moryta.myfirstapp.enums.PetTypeEnum;
import br.com.moryta.myfirstapp.model.converters.PetTypeConverter;

/**
 * Created by moryta on 17/08/2017.
 */
@Entity
public class Pet {
    @Id
    private Long id;

    @NotNull
    @Convert(converter = PetTypeConverter.class, columnType = Integer.class)
    private PetTypeEnum type;

    @NotNull
    private String name;

    private String breed;

    private Date birthDate;

    @Generated(hash = 1202112675)
    public Pet(Long id, @NotNull PetTypeEnum type, @NotNull String name, String breed, Date birthDate) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.breed = breed;
        this.birthDate = birthDate;
    }

    @Generated(hash = 1478286243)
    public Pet() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBreed() {
        return this.breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public Date getBirthDate() {
        return this.birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public PetTypeEnum getType() {
        return this.type;
    }

    public void setType(PetTypeEnum type) {
        this.type = type;
    }
}
