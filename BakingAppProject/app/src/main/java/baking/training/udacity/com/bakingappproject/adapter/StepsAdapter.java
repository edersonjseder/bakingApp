package baking.training.udacity.com.bakingappproject.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import baking.training.udacity.com.bakingappproject.R;
import baking.training.udacity.com.bakingappproject.listeners.OnItemClickListener;
import baking.training.udacity.com.bakingappproject.listeners.OnStepItemSelectedListener;
import baking.training.udacity.com.bakingappproject.model.Step;
import baking.training.udacity.com.bakingappproject.viewholder.StepsViewHolder;

public class StepsAdapter extends RecyclerView.Adapter<StepsViewHolder> {
    private static final String TAG = StepsAdapter.class.getSimpleName();

    private List<Step> stepList;
    private Context context;
    private String recipeName;

    public StepsAdapter(List<Step> stepList,
                         String recipeName,
                         Context context) {

        this.stepList = stepList;
        this.context = context;
        this.recipeName = recipeName;

    }

    @NonNull
    @Override
    public StepsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.recycler_view_steps_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);

        return new StepsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StepsViewHolder holder, int position) {

        final Step step = stepList.get(position);

        holder.getTextViewStepId().setText(String.valueOf(step.getId()));
        holder.getTextViewStepShortDescription().setText(step.getShortDescription());

        holder.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                ((OnStepItemSelectedListener)context).onStepItemSelected(step, recipeName, position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return (stepList != null) ? stepList.size() : 0;
    }

    public void updateRecipeData(List<Step> data) {
        this.stepList = data;
        notifyDataSetChanged();
    }
}
