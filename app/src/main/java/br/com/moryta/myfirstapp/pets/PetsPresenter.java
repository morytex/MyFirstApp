package br.com.moryta.myfirstapp.pets;

import android.support.annotation.NonNull;

import java.util.List;

import br.com.moryta.myfirstapp.model.DaoSession;
import br.com.moryta.myfirstapp.model.Pet;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by moryta on 17/08/2017.
 */

public class PetsPresenter implements PetsContract.Presenter {
    private PetsContract.View mPetsView;
    private DaoSession daoSession;

    public PetsPresenter(@NonNull PetsContract.View view, @NonNull DaoSession daoSession) {
        this.mPetsView = checkNotNull(view, "view cannot be null!");
        this.mPetsView.setPresenter(this);
        this.daoSession = checkNotNull(daoSession, "daoSession cannot be null!");
    }

    @Override
    public void start() {
        // Do something
    }

    @Override
    public List<Pet> fetchAllPets() {
        return daoSession.getPetDao().loadAll();
    }

    @Override
    public void delete(Pet pet) {
        daoSession.getPetDao().delete(pet);
    }
}
