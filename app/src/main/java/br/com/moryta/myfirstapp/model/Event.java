package br.com.moryta.myfirstapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.ToOne;

/**
 * Created by moryta on 23/08/2017.
 */
@Entity
public class Event implements Parcelable {
    @Id(autoincrement = true)
    private Long id;

    @NotNull
    private Long petId;

    @ToOne(joinProperty = "petId")
    private Pet pet;

    @NotNull
    private String title;

    @NotNull
    private String date;

    @NotNull
    private String time;

    private Long addressId;

    @ToOne(joinProperty = "addressId")
    private Address address;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 1542254534)
    private transient EventDao myDao;

    public Event(Parcel source) {
        this.id = source.readLong();
        this.petId = source.readLong();
        this.title = source.readString();
        this.date = source.readString();
        this.time = source.readString();
        this.addressId = source.readLong();
    }

    public Event() {
    }

    public Event(Long id, @NotNull Long petId, @NotNull String title, @NotNull String date
            , @NotNull String time) {
        this.id = id;
        this.petId = petId;
        this.title = title;
        this.date = date;
        this.time = time;
    }

    @Generated(hash = 999592410)
    public Event(Long id, @NotNull Long petId, @NotNull String title, @NotNull String date,
            @NotNull String time, Long addressId) {
        this.id = id;
        this.petId = petId;
        this.title = title;
        this.date = date;
        this.time = time;
        this.addressId = addressId;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPetId() {
        return this.petId;
    }

    public void setPetId(Long petId) {
        this.petId = petId;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return this.time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Long getAddressId() {
        return this.addressId;
    }

    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }

    @Generated(hash = 1364068960)
    private transient Long pet__resolvedKey;

    /** To-one relationship, resolved on first access. */
    @Generated(hash = 1129644455)
    public Pet getPet() {
        Long __key = this.petId;
        if (pet__resolvedKey == null || !pet__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            PetDao targetDao = daoSession.getPetDao();
            Pet petNew = targetDao.load(__key);
            synchronized (this) {
                pet = petNew;
                pet__resolvedKey = __key;
            }
        }
        return pet;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 499588574)
    public void setPet(@NotNull Pet pet) {
        if (pet == null) {
            throw new DaoException(
                    "To-one property 'petId' has not-null constraint; cannot set to-one to null");
        }
        synchronized (this) {
            this.pet = pet;
            petId = pet.getId();
            pet__resolvedKey = petId;
        }
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }

    /** To-one relationship, resolved on first access. */
    @Generated(hash = 489389972)
    public Address getAddress() {
        Long __key = this.addressId;
        if (address__resolvedKey == null || !address__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            AddressDao targetDao = daoSession.getAddressDao();
            Address addressNew = targetDao.load(__key);
            synchronized (this) {
                address = addressNew;
                address__resolvedKey = __key;
            }
        }
        return address;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 607080948)
    public void setAddress(Address address) {
        synchronized (this) {
            this.address = address;
            addressId = address == null ? null : address.getId();
            address__resolvedKey = addressId;
        }
    }

    public static final Parcelable.Creator CREATOR = new Creator<Event>() {
        @Override
        public Event createFromParcel(Parcel source) {
            return new Event(source);
        }

        @Override
        public Event[] newArray(int size) {
            return new Event[size];
        }
    };

    @Generated(hash = 1156467801)
    private transient Long address__resolvedKey;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeLong(this.petId);
        dest.writeString(this.title);
        dest.writeString(this.date);
        dest.writeString(this.time);
        dest.writeLong(this.addressId);
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1459865304)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getEventDao() : null;
    }
}
