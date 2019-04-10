package com.Marvel.stackquestion.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.jackandphantom.stackquestion.R;
import com.Marvel.stackquestion.model.TagData;
import java.util.List;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TagDataAdapter extends RecyclerView.Adapter<TagDataAdapter.ViewHolder> {

    private List<TagData> tagDataList;
    private LayoutInflater layoutInflater;
    private Context context;
    OnClickListener onClickListener;

    public interface OnClickListener {
        void onClick(String tag, int position);
    }

    public TagDataAdapter(List<TagData> tagDataList, Context context) {
        this.tagDataList = tagDataList;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.tag_list_layout, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
      holder.tagName.setText(tagDataList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return tagDataList.size();
    }

    public void setOnClickListener(OnClickListener  onClickListener) {
        this.onClickListener = onClickListener;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView tagName;
         ViewHolder(@NonNull View itemView) {
            super(itemView);
            tagName = itemView.findViewById(R.id.tag);
            tagName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onClickListener != null) {
                        onClickListener.onClick(tagDataList.get(getAdapterPosition()).getName(),
                                getAdapterPosition());
                    }
                }
            });
        }
    }
}
