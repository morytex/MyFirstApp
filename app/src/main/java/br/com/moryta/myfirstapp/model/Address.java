package br.com.moryta.myfirstapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by moryta on 25/08/2017.
 */
@Entity
public class Address implements Parcelable {
    @Id
    private Long id;

    @NotNull
    private String state;

    @NotNull
    private String city;

    @NotNull
    private String street;

    @NotNull
    private String number;

    @NotNull
    private double latitude;

    @NotNull
    private double longitude;

    public Address(Parcel source) {
        this.id = source.readLong();
        this.state = source.readString();
        this.city = source.readString();
        this.street = source.readString();
        this.number = source.readString();
        this.latitude = source.readDouble();
        this.longitude = source.readDouble();
    }

    @Generated(hash = 334294566)
    public Address(Long id, @NotNull String state, @NotNull String city,
            @NotNull String street, @NotNull String number, double latitude,
            double longitude) {
        this.id = id;
        this.state = state;
        this.city = city;
        this.street = street;
        this.number = number;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Generated(hash = 388317431)
    public Address() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.state);
        dest.writeString(this.city);
        dest.writeString(this.street);
        dest.writeString(this.number);
        dest.writeDouble(this.latitude);
        dest.writeDouble(this.longitude);
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getState() {
        return this.state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return this.city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return this.street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getNumber() {
        return this.number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public double getLatitude() {
        return this.latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return this.longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public static final Parcelable.Creator CREATOR = new Creator<Address>(){

        @Override
        public Address createFromParcel(Parcel source) {
            return new Address(source);
        }

        @Override
        public Address[] newArray(int size) {
            return new Address[size];
        }
    };
}
