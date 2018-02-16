package org.kisio.navitiasdkui.journey.search;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.kisio.navitiasdkui.R;
import org.kisio.navitiasdkui.journey.ListModel;
import org.kisio.navitiasdkui.journey.SolutionViewHolder;

import java.util.List;

import static org.kisio.navitiasdkui.util.Constant.VIEW_TYPE_EMPTY_STATE;
import static org.kisio.navitiasdkui.util.Constant.VIEW_TYPE_HEADER;
import static org.kisio.navitiasdkui.util.Constant.VIEW_TYPE_LOADING;
import static org.kisio.navitiasdkui.util.Constant.VIEW_TYPE_SOLUTION;

public class ResultAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<ListModel> listModelList;
    private ClickListener clickListener;
    private Context context;

    interface ClickListener {
        void onSolutionClick(ListModel solution);
    }

    ResultAdapter(List<ListModel> listModelList, JourneySearchActivity activity) {
        this.listModelList = listModelList;
        this.context = activity;
        this.clickListener = activity;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        switch (viewType) {
            case VIEW_TYPE_SOLUTION:
                View solutionView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.listitem_solution, parent, false);
                viewHolder = new SolutionViewHolder(solutionView);
                break;
            case VIEW_TYPE_HEADER:
                View headerView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.listitem_header, parent, false);
                viewHolder = new HeaderViewHolder(headerView);
                break;
            case VIEW_TYPE_LOADING:
                View loadingView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.listitem_loading, parent, false);
                viewHolder = new LoadingViewHolder(loadingView);
                break;
            case VIEW_TYPE_EMPTY_STATE:
                View emptyStateView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.listitem_empty_state, parent, false);
                viewHolder = new EmptyStateViewHolder(emptyStateView);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final ListModel listModel = listModelList.get(holder.getAdapterPosition());

        switch (listModel.getViewType()) {
            case VIEW_TYPE_SOLUTION:
                ((SolutionViewHolder) holder).fillView(listModel, context);
                ((SolutionViewHolder) holder).setClickListener(new SolutionViewHolder.ClickListener() {
                    @Override
                    public void onClick() {
                        clickListener.onSolutionClick(listModel);
                    }
                });
                break;
            case VIEW_TYPE_EMPTY_STATE:
                ((EmptyStateViewHolder) holder).fillView(listModel);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return listModelList.size();
    }

}
