package br.com.moryta.myfirstapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;

import br.com.moryta.myfirstapp.enums.PetTypeEnum;
import br.com.moryta.myfirstapp.model.converters.PetTypeConverter;

/**
 * Created by moryta on 17/08/2017.
 */
@Entity
public class Pet implements Parcelable{
    @Id(autoincrement = true)
    private Long id;

    @NotNull
    @Convert(converter = PetTypeConverter.class, columnType = Integer.class)
    private PetTypeEnum type;

    @NotNull
    private String name;

    private String breed;

    private String birthDate;

    @Generated(hash = 4535995)
    public Pet(Long id, @NotNull PetTypeEnum type, @NotNull String name, String breed, String birthDate) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.breed = breed;
        this.birthDate = birthDate;
    }

    public Pet(Parcel source) {
        this.id = source.readLong();
        this.type = (PetTypeEnum) source.readSerializable();
        this.name = source.readString();
        this.breed = source.readString();
        this.birthDate = source.readString();
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

    public String getBirthDate() {
        return this.birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public PetTypeEnum getType() {
        return this.type;
    }

    public void setType(PetTypeEnum type) {
        this.type = type;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeSerializable(this.type);
        dest.writeString(this.name);
        dest.writeString(this.breed);
        dest.writeString(this.birthDate);
    }

    public static final Parcelable.Creator CREATOR = new Creator<Pet>(){

        @Override
        public Pet createFromParcel(Parcel source) {
            return new Pet(source);
        }

        @Override
        public Pet[] newArray(int size) {
            return new Pet[size];
        }
    };
}
