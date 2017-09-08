package br.com.moryta.myfirstapp.events;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import br.com.moryta.myfirstapp.CustomOnItemClickListener;
import br.com.moryta.myfirstapp.Extras;
import br.com.moryta.myfirstapp.R;
import br.com.moryta.myfirstapp.events.detail.EventDetailActivity;
import br.com.moryta.myfirstapp.events.register.EventRegisterActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static android.app.Activity.RESULT_OK;
import static br.com.moryta.myfirstapp.events.EventsContract.RC_REGISTER_EVENT;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link EventsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link EventsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EventsFragment extends Fragment
        implements EventsContract.View
        , View.OnClickListener
        , CustomOnItemClickListener {

    private OnFragmentInteractionListener mListener;

    private EventsAdapter mEventsAdapter;
    private EventsContract.Presenter mEventsPresenter;

    @BindView(R.id.rvEvents)
    RecyclerView rvEvents;

    @BindView(R.id.fab)
    FloatingActionButton fab;

    private Unbinder unbinder;

    public EventsFragment() {
        // Required empty public constructor
    }


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment EventsFragment.
     */
    public static EventsFragment newInstance() {
        EventsFragment fragment = new EventsFragment();
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
        View view = inflater.inflate(R.layout.fragment_events, container, false);
        unbinder = ButterKnife.bind(EventsFragment.this, view);

        // Setting floating action button (register event button)
        fab.setOnClickListener(this);

        // Setting recycler view
        this.mEventsAdapter = new EventsAdapter(this.mEventsPresenter.fetchAllEvents(), this);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        DividerItemDecoration decoration = new DividerItemDecoration(getActivity()
                , DividerItemDecoration.VERTICAL);
        rvEvents.addItemDecoration(decoration);
        rvEvents.setLayoutManager(layoutManager);
        rvEvents.setHasFixedSize(true);
        rvEvents.setAdapter(mEventsAdapter);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
            this.mListener.onEventsFragmentInteraction();
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
    public void onStart() {
        super.onStart();
        this.mEventsAdapter.update(this.mEventsPresenter.fetchAllEvents());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab:
                Intent intent = new Intent();
                intent.setClass(getActivity(), EventRegisterActivity.class);
                startActivityForResult(intent, RC_REGISTER_EVENT);
                break;
            default:
        }
    }

    @Override
    public void onItemClick(Long eventId, View view) {
        Intent intent = new Intent(getActivity(), EventDetailActivity.class);
        // TODO: After greendao is removed, pass object through Intent
        intent.putExtra(Extras.EVENT_ID, eventId);

        Pair<View, String> sharedRootView =
                new Pair<>(view, getString(R.string.transition_event_detail));

        View titleView = view.findViewById(R.id.tvEventTitle);
        Pair<View, String> sharedTitleView =
                new Pair<>(titleView
                        , getString(R.string.transition_event_detail_title));

        View descriptionView = view.findViewById(R.id.tvEventDescription);
        Pair<View, String> sharedDescriptionView =
                new Pair<>(descriptionView
                        , getString(R.string.transition_event_detail_description));

        Pair<View, String>[] sharedViews = new Pair[]{
                sharedRootView
                , sharedTitleView
                , sharedDescriptionView};

        ActivityOptionsCompat options =
                ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity()
                        , sharedViews);
        ActivityCompat.startActivity(getActivity(), intent, options.toBundle());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case RC_REGISTER_EVENT:
                if (resultCode != RESULT_OK) {
                    Toast.makeText(
                            getActivity()
                            , getString(R.string.warning_message_operation_canceled)
                            , Toast.LENGTH_SHORT).show();
                    return;
                }
                mEventsAdapter.update(mEventsPresenter.fetchAllEvents());
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
        void onEventsFragmentInteraction();
    }

    @Override
    public void setPresenter(@NonNull EventsContract.Presenter presenter) {
        this.mEventsPresenter = checkNotNull(presenter, "presenter cannot be null!");
    }
}
