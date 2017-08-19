package br.com.moryta.myfirstapp.pets;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import br.com.moryta.myfirstapp.model.Pet;

/**
 * Created by moryta on 17/08/2017.
 */

public class PetsAdapter extends RecyclerView.Adapter<PetsAdapter.PetViewHolder> {

    private List<Pet> petList;

    public PetsAdapter(List<Pet> petList) {
        this.petList = petList;
    }

    @Override
    public PetViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(PetViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class PetViewHolder extends RecyclerView.ViewHolder {

        public PetViewHolder(View itemView) {
            super(itemView);
        }
    }
}
