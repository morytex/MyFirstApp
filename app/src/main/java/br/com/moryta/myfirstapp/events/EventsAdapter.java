package br.com.moryta.myfirstapp.events;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import br.com.moryta.myfirstapp.OnItemClickListener;
import br.com.moryta.myfirstapp.R;
import br.com.moryta.myfirstapp.SimpleItemTouchHelperAdapter;
import br.com.moryta.myfirstapp.model.Event;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by moryta on 23/08/2017.
 */

public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.EventViewHolder>
    implements SimpleItemTouchHelperAdapter {

    private List<Event> eventList;
    private OnItemClickListener itemClickListener;

    public EventsAdapter(List<Event> eventList, OnItemClickListener itemClickListener) {
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

        holder.tvEventTitle.setText(event.getTitle());
        holder.tvEventPetName.setText(event.getPet().getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemClickListener != null) {
                    itemClickListener.onItemClick(eventList.get(position), v);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.eventList.size();
    }

    public void update(List<Event> eventList) {
        this.eventList = eventList;
        notifyDataSetChanged();
    }



    @Override
    public boolean onItemMoved(int fromPosition, int toPosition) {
        return false;
    }

    @Override
    public Event onItemSwiped(int position) {
        Event removedEvent = this.eventList.remove(position);
        notifyDataSetChanged();
        return removedEvent;
    }

    public class EventViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvEventTitle)
        TextView tvEventTitle;

        @BindView(R.id.tvEventPetName)
        TextView tvEventPetName;

        public EventViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
