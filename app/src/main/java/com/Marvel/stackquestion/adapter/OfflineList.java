package com.Marvel.stackquestion.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.Marvel.stackquestion.model.OfflineData;
import com.jackandphantom.stackquestion.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class OfflineList extends RecyclerView.Adapter<OfflineList.ViewHolder> {

    private List<OfflineData> offlineDataList;
    private LayoutInflater inflater;
    OnQuestionClickListener onQuestionClickListener;

    public interface OnQuestionClickListener {
        void onClick(String url);
    }


    public OfflineList(List<OfflineData> offlineDataList, Context context) {
        this.offlineDataList = offlineDataList;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.offline_list, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.titleText.setText(offlineDataList.get(position).getQuestionTitle());
        holder.bodyText.setText(offlineDataList.get(position).getQuestionBody());
    }

    @Override
    public int getItemCount() {
        return offlineDataList.size();
    }

    public void setOnQuestionClickListener(OnQuestionClickListener onQuestionClickListener) {
        this.onQuestionClickListener = onQuestionClickListener;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView bodyText, titleText;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            bodyText = itemView.findViewById(R.id.question_body);
            titleText = itemView.findViewById(R.id.question_title);
            titleText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onQuestionClickListener != null) {
                       // onQuestionClickListener.onClick(offlineDataList.get(getAdapterPosition()));
                    }
                }
            });

        }
    }
}
