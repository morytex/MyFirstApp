package br.com.moryta.myfirstapp.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;

import java.util.Date;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by moryta on 17/08/2017.
 */
@Entity
public class Pet {
    @Id
    private Long id;

    @NotNull
    private String name;

    private String breed;

    private Date birthDate;

    @Generated(hash = 4085478)
    public Pet(Long id, @NotNull String name, String breed, Date birthDate) {
        this.id = id;
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
}
