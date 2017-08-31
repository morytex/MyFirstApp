package br.com.moryta.myfirstapp.pets;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import br.com.moryta.myfirstapp.R;
import br.com.moryta.myfirstapp.pets.register.PetRegisterActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static android.app.Activity.RESULT_OK;
import static br.com.moryta.myfirstapp.pets.PetsContract.RC_REGISTER_PET;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PetsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PetsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PetsFragment extends Fragment implements PetsContract.View {
    private OnFragmentInteractionListener mListener;

    private PetsAdapter mPetsAdapter;
    private PetsContract.Presenter mPetsPresenter;

    @BindView(R.id.fab)
    FloatingActionButton fab;

    @BindView(R.id.rvPets)
    RecyclerView rvPets;

    private Unbinder unbinder;

    public PetsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment PetsFragment.
     */
    public static PetsFragment newInstance() {
        PetsFragment fragment = new PetsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pets, container, false);
        unbinder = ButterKnife.bind(PetsFragment.this, view);

        // Setting floating action button (register pet button)
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                Intent intent = new Intent();
                intent.setClass(getActivity(), PetRegisterActivity.class);
                startActivityForResult(intent, RC_REGISTER_PET);
            }
        });

        // Setting recycler view
        this.mPetsAdapter = new PetsAdapter(this.mPetsPresenter.fetchAllPets());

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        DividerItemDecoration decoration = new DividerItemDecoration(getActivity()
                , DividerItemDecoration.VERTICAL);
        rvPets.addItemDecoration(decoration);
        rvPets.setLayoutManager(layoutManager);
        rvPets.setHasFixedSize(true);
        rvPets.setAdapter(mPetsAdapter);

        // Setting ItemTouchHelper on recycler view
        ItemTouchHelper.SimpleCallback callback =
                new ItemTouchHelper.SimpleCallback(
                        ItemTouchHelper.ACTION_STATE_IDLE
                        , ItemTouchHelper.LEFT) {

                    @Override
                    public boolean onMove(RecyclerView recyclerView
                            , RecyclerView.ViewHolder viewHolder
                            , RecyclerView.ViewHolder target) {

                        Toast.makeText(getActivity(), "On move"
                                , Toast.LENGTH_SHORT).show();
                        return mPetsAdapter.onItemMoved(viewHolder.getAdapterPosition()
                                , target.getAdapterPosition());
                    }

                    @Override
                    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                        mPetsPresenter.delete(
                                mPetsAdapter.onItemSwiped(viewHolder.getAdapterPosition()));
                    }
                };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(rvPets);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            this.mListener = (OnFragmentInteractionListener) context;
            this.mListener.onPetsFragmentAttach();
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void setPresenter(@NonNull PetsContract.Presenter presenter) {
        this.mPetsPresenter = checkNotNull(presenter);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case RC_REGISTER_PET:
                if (resultCode != RESULT_OK) {
                    Toast.makeText(
                            getActivity()
                            , getString(R.string.warning_message_operation_canceled)
                            , Toast.LENGTH_SHORT).show();
                    return;
                }
                mPetsAdapter.update(mPetsPresenter.fetchAllPets());
                break;
            default:
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        void onPetsFragmentAttach();
    }
}
