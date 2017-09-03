package br.com.moryta.myfirstapp.events.register;

import android.support.annotation.NonNull;

import com.google.common.base.Strings;

import org.greenrobot.greendao.database.Database;

import java.util.Date;
import java.util.List;

import br.com.moryta.myfirstapp.model.Address;
import br.com.moryta.myfirstapp.model.DaoSession;
import br.com.moryta.myfirstapp.model.Event;
import br.com.moryta.myfirstapp.model.Pet;
import br.com.moryta.myfirstapp.utils.DateUtil;
import br.com.moryta.myfirstapp.utils.TimeUtil;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by moryta on 24/08/2017.
 */

public class EventRegisterPresenter implements EventRegisterContract.Presenter {
    private static final String TAG = "EventRegisterPresenter";

    private EventRegisterContract.View view;
    private DaoSession daoSession;
    private Event mEvent;

    public EventRegisterPresenter(@NonNull EventRegisterContract.View view, @NonNull DaoSession daoSession) {
        this.view = checkNotNull(view, "view cannot be null!");
        this.daoSession = checkNotNull(daoSession, "daoSession cannot be null!");
    }

    public void start() {
        // Do nothing
    }

    @Override
    public List<Pet> fetchAllPets() {
        return this.daoSession.getPetDao().loadAll();
    }

    @Override
    public void loadEvent(Long eventId) {
        this.mEvent = this.daoSession.getEventDao().loadDeep(eventId);
        if (this.mEvent == null) {
            this.mEvent = new Event();
            this.mEvent.setAddress(new Address());
        }

        this.view.onEventLoaded(this.mEvent.getPetId()
                , this.mEvent.getTitle(), this.mEvent.getDescription()
                , this.mEvent.getDate(), this.mEvent.getTime());
    }

    @Override
    public Long insertOrUpdateEvent(Long petId, String title, String description
            , String date, String time, String contact, String state, String city
            , String street, String addressNumber, double latitude, double longitude) {
        
        this.mEvent.setPetId(petId);
        this.mEvent.setTitle(title);
        this.mEvent.setDescription(description);
        this.mEvent.setDate(date);
        this.mEvent.setTime(time);
        this.mEvent.setContact(contact);

        Address address = new Address();
        address.setState(state);
        address.setCity(city);
        address.setStreet(street);
        address.setNumber(addressNumber);
        address.setLatitude(latitude);
        address.setLongitude(longitude);

        Database db = daoSession.getDatabase();
        db.beginTransaction();
        try {
            Long addressId = daoSession.getAddressDao().insertOrReplace(address);
            address.setId(addressId);
            this.mEvent.setAddress(address);
            this.daoSession.getEventDao().insertOrReplace(this.mEvent);

            db.setTransactionSuccessful();
        } catch (Exception ex) {
        } finally {
            db.endTransaction();
        }

        this.daoSession.clear();
        return this.mEvent.getId();
    }

    @Override
    public boolean isEventDataFilled(String title, String date, String time) {
        return !Strings.isNullOrEmpty(title)
                && !Strings.isNullOrEmpty(date)
                && DateUtil.isValidFormat(date)
                && !Strings.isNullOrEmpty(time)
                && TimeUtil.isValidFormat(time);
    }

    @Override
    public String formatDate(Date date) {
        return DateUtil.format(date);
    }

    @Override
    public String formatTime(int hourOfDay, int minute) {
        return TimeUtil.format(hourOfDay, minute);
    }
}
