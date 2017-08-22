package br.com.moryta.myfirstapp.pets;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import br.com.moryta.myfirstapp.R;
import br.com.moryta.myfirstapp.model.Pet;
import br.com.moryta.myfirstapp.utils.DateUtil;
import butterknife.BindView;
import butterknife.ButterKnife;

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
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View petLayout = layoutInflater.inflate(R.layout.row_pet, parent, false);

        return new PetViewHolder(petLayout);
    }

    @Override
    public void onBindViewHolder(PetViewHolder holder, int position) {
        Pet pet = this.petList.get(position);
        String age;
        try {
            age = String.valueOf(DateUtil.getAge(pet.getBirthDate()));
        } catch (Exception e) {
            age = "?";
        }

        holder.tvPetName.setText(pet.getName());
        holder.tvPetBreed.setText(pet.getBreed());
        holder.tvPetAge.setText(age);

        // Set image by pet type
        switch (pet.getType()) {
            case DOG:
                holder.ivPetPhoto.setImageResource(R.drawable.default_dog_photo);
                break;
            case CAT:
                holder.ivPetPhoto.setImageResource(R.drawable.default_cat_photo);
                break;
            default:
                holder.ivPetPhoto.setImageResource(R.mipmap.ic_launcher_round);
        }
    }

    @Override
    public int getItemCount() {
        return this.petList.size();
    }

    public void update(List<Pet> petList) {
        this.petList = petList;
        notifyDataSetChanged();
    }

    public class PetViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ivPetPhoto)
        ImageView ivPetPhoto;

        @BindView(R.id.tvPetName)
        TextView tvPetName;

        @BindView(R.id.tvPetBreed)
        TextView tvPetBreed;

        @BindView(R.id.tvPetAge)
        TextView tvPetAge;

        public PetViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
