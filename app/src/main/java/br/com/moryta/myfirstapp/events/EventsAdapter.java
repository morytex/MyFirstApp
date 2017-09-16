package br.com.moryta.myfirstapp.events;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import br.com.moryta.myfirstapp.BaseViewHolder;
import br.com.moryta.myfirstapp.CustomOnItemClickListener;
import br.com.moryta.myfirstapp.R;
import br.com.moryta.myfirstapp.model.Event;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by moryta on 23/08/2017.
 */

public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.EventViewHolder> {

    private List<Event> eventList;
    private CustomOnItemClickListener itemClickListener;

    public EventsAdapter(List<Event> eventList, CustomOnItemClickListener itemClickListener) {
        this.eventList = eventList;
        this.itemClickListener = itemClickListener;
    }

    @Override
    public EventViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View eventLayout = layoutInflater.inflate(R.layout.row_event, parent, false);

        return new EventViewHolder(eventLayout);
    }

    @Override
    public void onBindViewHolder(EventViewHolder holder, final int position) {
        Event event = this.eventList.get(position);

        holder.configure(event);

    }

    @Override
    public int getItemCount() {
        return this.eventList.size();
    }

    public void update(List<Event> eventList) {
        this.eventList = eventList;
        notifyDataSetChanged();
    }

    /**
     * View Holder
     */
    public class EventViewHolder extends RecyclerView.ViewHolder
            implements BaseViewHolder<Event>
            , View.OnClickListener
            , View.OnLongClickListener {

        @BindView(R.id.tvEventTitle)
        TextView tvEventTitle;

        @BindView(R.id.tvEventDescription)
        TextView tvEventDescription;

        @BindView(R.id.tvEventPetName)
        TextView tvEventPetName;

        @BindView(R.id.tvEventDate)
        TextView tvEventDate;

        private Event event;

        public EventViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(this);
        }

        @Override
        public void configure(Event model) {
            this.event = model;

            this.tvEventTitle.setText(this.event.getTitle());
            this.tvEventDescription.setText(this.event.getDescription());
            this.tvEventPetName.setText(this.event.getPet().getName());
            this.tvEventDate.setText(this.event.getDate());
        }

        @Override
        public void onClick(View v) {
            if (itemClickListener != null) {
                itemClickListener.onItemClick(this.event.getId(), v);
            }
        }

        @Override
        public boolean onLongClick(View v) {
            return false;
        }
    }
}
