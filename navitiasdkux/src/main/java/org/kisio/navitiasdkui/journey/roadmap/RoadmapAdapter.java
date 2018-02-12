package org.kisio.navitiasdkui.journey.roadmap;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.kisio.navitiasdkui.R;
import org.kisio.navitiasdkui.journey.ListModel;
import org.kisio.navitiasdkui.journey.SolutionViewHolder;

import java.util.List;

public class RoadmapAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<ListModel> listModels;
    private Context context;

    public RoadmapAdapter(List<ListModel> listModels, RoadmapActivity activity) {
        this.listModels = listModels;
        this.context = activity;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        switch (viewType) {
            case 0:
                View solutionView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.listitem_solution, parent, false);
                viewHolder = new SolutionViewHolder(solutionView);
                break;
            case 1:
                View headerView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.listitem_departure_arrival, parent, false);
                viewHolder = new DepartureArrivalViewHolder(headerView);
                break;
            case 2:
                View transferView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.listitem_transfer, parent, false);
                viewHolder = new TransferViewHolder(transferView);
                break;
            case 3:
                View transitView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.listitem_transit, parent, false);
                viewHolder = new TransitViewHolder(transitView);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final ListModel listModel = listModels.get(holder.getAdapterPosition());

        switch (listModel.getViewType()) {
            case 0:
                ((SolutionViewHolder) holder).fillView(listModel, context);
                break;
            case 1:
                ((DepartureArrivalViewHolder) holder).fillView(listModel);
                break;
            case 2:
                ((TransferViewHolder) holder).fillView(listModel);
                break;
            case 3:
                ((TransitViewHolder) holder).fillView(listModel, context);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return listModels.size();
    }
}
