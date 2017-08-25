package br.com.moryta.myfirstapp.pets.register;

import android.support.annotation.NonNull;

import com.google.common.base.Strings;

import java.util.Date;

import br.com.moryta.myfirstapp.enums.PetTypeEnum;
import br.com.moryta.myfirstapp.model.Address;
import br.com.moryta.myfirstapp.model.AddressDao;
import br.com.moryta.myfirstapp.model.DaoSession;
import br.com.moryta.myfirstapp.model.Event;
import br.com.moryta.myfirstapp.model.EventDao;
import br.com.moryta.myfirstapp.model.Pet;
import br.com.moryta.myfirstapp.model.PetDao;
import br.com.moryta.myfirstapp.utils.DateUtil;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by moryta on 21/08/2017.
 */

public class PetRegisterPresenter implements PetRegisterContract.Presenter {
    private PetRegisterContract.View view;
    private DaoSession daoSession;

    public PetRegisterPresenter(@NonNull PetRegisterContract.View view
            , @NonNull DaoSession daoSession) {
        this.view = checkNotNull(view, "view cannot be null!");
        this.daoSession = checkNotNull(daoSession, "daoSession cannot be null!");
    }

    @Override
    public void start() {
        // Do nothing
    }

    @Override
    public void addPet(String name, String breed, String birthDate) {
        Pet pet = new Pet(null, PetTypeEnum.DOG, name, breed, birthDate);
        PetDao petDao = this.daoSession.getPetDao();
        // TODO: Validate if pet already exists (verify tuple name + breed)
        long petId = petDao.insert(pet);

        // TODO: Remove when registration of event is completed
        Address address = new Address(null, "SP", "São Paulo", "Av. Paulista", "1100", -23.564149, -46.652484);
        AddressDao addressDao = this.daoSession.getAddressDao();
        Long addressId = addressDao.insert(address);
        Event event = new Event(null, petId, "Default Event", "Default Description", pet.getBirthDate(), "16:20", addressId);
        event.setAddress(address);
        EventDao eventDao = this.daoSession.getEventDao();
        eventDao.insert(event);
    }

    @Override
    public boolean isPetDataFilled(String name, String breed, String birthDate) {
        return !Strings.isNullOrEmpty(name)
                && !Strings.isNullOrEmpty(breed)
                && !Strings.isNullOrEmpty(birthDate)
                && DateUtil.isValidFormat(birthDate);
    }

    @Override
    public String formatDate(Date date) {
        return DateUtil.format(date);
    }
}
