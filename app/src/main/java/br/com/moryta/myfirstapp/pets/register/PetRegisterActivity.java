package br.com.moryta.myfirstapp.pets.register;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;

import br.com.moryta.myfirstapp.DatePickerFragment;
import br.com.moryta.myfirstapp.MyApplication;
import br.com.moryta.myfirstapp.R;
import br.com.moryta.myfirstapp.utils.DateUtil;
import butterknife.BindView;
import butterknife.ButterKnife;

public class PetRegisterActivity extends AppCompatActivity
        implements PetRegisterContract.View
        , DatePickerFragment.DatePickerFragmentListener
        , View.OnClickListener
        , TextWatcher {
    private static final String TAG = "PetRegisterActivity";
    private static final int RC_REGISTER_PET = 1001;

    @BindView(R.id.etPetName)
    EditText etPetName;

    @BindView(R.id.etPetBreed)
    EditText etPetBreed;

    @BindView(R.id.tvPetBirthDate)
    TextView tvPetBirthDate;

    @BindView(R.id.ivPetBirthDate)
    ImageView ivPetBirthDate;

    @BindView(R.id.btnRegisterPet)
    Button btnRegisterPet;

    private PetRegisterContract.Presenter mPresenter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_register);

        ButterKnife.bind(this);

        // Adding watcher on fields to disable/enable register button
        btnRegisterPet.setEnabled(false);
        etPetName.addTextChangedListener(this);
        etPetBreed.addTextChangedListener(this);
        tvPetBirthDate.addTextChangedListener(this);

        // Setting click listener
        ivPetBirthDate.setOnClickListener(this);
        btnRegisterPet.setOnClickListener(this);

        // Instance of presenter
        this.mPresenter = new PetRegisterPresenter(PetRegisterActivity.this
                , ((MyApplication) getApplication()).getDaoSession());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivPetBirthDate:
                DialogFragment dialogFragment = DatePickerFragment.newInstance(this);
                dialogFragment.show(getSupportFragmentManager(), "petBirhDate");
                break;
            case R.id.btnRegisterPet:
                String name = etPetName.getText().toString();
                String breed = etPetBreed.getText().toString();
                String birthDate = tvPetBirthDate.getText().toString();
                mPresenter.addPet(name, breed, birthDate);
                setResult(RC_REGISTER_PET);
                finish();
                break;
            default:
        }
    }

    @Override
    public void onDateSet(Date date) {
        if (date == null) {
            Toast.makeText(this, "Date cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        this.tvPetBirthDate.setText(DateUtil.format(date));
    }

    @Override
    public void setPresenter(PetRegisterContract.Presenter presenter) {
        // Not necessary, because we instantiate presenter in this activity
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        // Do nothing
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        // Do nothing
    }

    @Override
    public void afterTextChanged(Editable s) {
        String name = etPetName.getText().toString();
        String breed = etPetBreed.getText().toString();
        String birthDate = tvPetBirthDate.getText().toString();

        btnRegisterPet.setEnabled(
                mPresenter.isPetDataFilled(name, breed, birthDate));
    }
}
