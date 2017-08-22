package br.com.moryta.myfirstapp.pets.register;

import br.com.moryta.myfirstapp.BasePresenter;
import br.com.moryta.myfirstapp.BaseView;

/**
 * Created by moryta on 21/08/2017.
 */

public interface PetRegisterContract {
    interface View extends BaseView<PetRegisterContract.Presenter> {

    }

    interface Presenter extends BasePresenter {
        void addPet(String name, String breed, String birthDate);
        boolean isPetDataFilled(String name, String breed, String birthDate);
    }
}
