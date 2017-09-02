package br.com.moryta.myfirstapp.pets;

import java.util.List;

import br.com.moryta.myfirstapp.BasePresenter;
import br.com.moryta.myfirstapp.BaseView;
import br.com.moryta.myfirstapp.model.Pet;

/**
 * Created by moryta on 17/08/2017.
 */

public interface PetsContract {
    int RC_REGISTER_PET = 1001;

    interface View extends BaseView<PetsContract.Presenter> {
    }

    interface Presenter extends BasePresenter {
        List<Pet> fetchAllPets();

        void delete(Pet pet);
    }
}
