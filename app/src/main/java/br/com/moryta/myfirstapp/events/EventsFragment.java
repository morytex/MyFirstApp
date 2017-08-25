package br.com.moryta.myfirstapp.events;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.com.moryta.myfirstapp.OnItemClickListener;
import br.com.moryta.myfirstapp.R;
import br.com.moryta.myfirstapp.events.detail.EventDetailActivity;
import br.com.moryta.myfirstapp.model.Event;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link EventsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link EventsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EventsFragment extends Fragment implements EventsContract.View {
    private static final int RC_REGISTER_EVENT = 1001;
    private OnFragmentInteractionListener mListener;

    private EventsAdapter mEventsAdapter;
    private EventsContract.Presenter mEventsPresenter;

    @BindView(R.id.rvEvents)
    RecyclerView rvEvents;

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

        OnItemClickListener onItemClickListener = new OnItemClickListener() {
            @Override
            public void onItemClick(Object item, View view) {
                Intent intent = new Intent(getActivity(), EventDetailActivity.class);
                intent.putExtra(Event.class.getName(), ((Event) item));

                String transitionName = getString(R.string.transition_event_detail);
                ActivityOptionsCompat options =
                        ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity()
                                , view
                                , transitionName);
                ActivityCompat.startActivity(getActivity(), intent, options.toBundle());
            }
        };

        // Setting recycler view
        this.mEventsAdapter = new EventsAdapter(this.mEventsPresenter.fetchAllEvents(), onItemClickListener);

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
    public void setPresenter(@NonNull EventsContract.Presenter presenter) {
        this.mEventsPresenter = checkNotNull(presenter, "presenter cannot be null!");
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
}
