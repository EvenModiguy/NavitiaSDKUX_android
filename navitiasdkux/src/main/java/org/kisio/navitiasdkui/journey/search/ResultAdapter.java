package org.kisio.navitiasdkui.journey.search;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.kisio.navitiasdkui.R;

import java.util.List;

public class ResultAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<ResultModel> resultModelList;
    private ClickListener clickListener;
    private Context context;

    interface ClickListener {
        void onSolutionClick(ResultModel solution);
    }

    public ResultAdapter(List<ResultModel> resultModelList, JourneySearchActivity activity) {
        this.resultModelList = resultModelList;
        this.context = activity;
        this.clickListener = activity;
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
                        .inflate(R.layout.listitem_header, parent, false);
                viewHolder = new HeaderViewHolder(headerView);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final ResultModel resultModel = resultModelList.get(holder.getAdapterPosition());

        switch (resultModel.getViewType()) {
            case 0:
                ((SolutionViewHolder) holder).fillView(resultModel, context);
                ((SolutionViewHolder) holder).setClickListener(new SolutionViewHolder.ClickListener() {
                    @Override
                    public void onClick() {
                        clickListener.onSolutionClick(resultModel);
                    }
                });
                break;
            case 1:
                ((HeaderViewHolder) holder).fillView();
                break;
        }
    }

    @Override
    public int getItemCount() {
        return resultModelList.size();
    }

}
