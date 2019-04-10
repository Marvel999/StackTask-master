package com.Marvel.stackquestion.adapter;

import android.content.Context;
import android.os.Build;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jackandphantom.stackquestion.R;
import com.Marvel.stackquestion.model.OwnerData;
import com.Marvel.stackquestion.model.QuestionData;

import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

public class QuestionListAdapter extends RecyclerView.Adapter<QuestionListAdapter.ViewHolder> {

    private List<QuestionData> questionData;
    private LayoutInflater layoutInflater;
    private Context context;
    OnQuestionClickListener onQuestionClickListener;
    OnQuestionLongClick onQuestionLongClick;
    public interface OnQuestionClickListener {
        void onClick(String url);
    }

    public interface OnQuestionLongClick {
        void onLongClick(String url, String body, String title);
    }

    public QuestionListAdapter(List<QuestionData> questionData, Context context) {
        this.questionData = questionData;
        layoutInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.question_list_layout,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        QuestionData data = questionData.get(position);
        OwnerData ownerData = data.getOwnerData();
        Log.e("MY TAG", "OWENER DATA "+ownerData);
        holder.questionTitle.setText(data.getTitle());
        holder.questionDes.setText(stripHtml(data.getBody()));
        Glide.with(context).load(ownerData.getProfile_image()).into(holder.circleImageView);
        holder.userName.setText(data.getOwnerData().getDisplay_name());
        holder.postTime.setText(longToDate(data.getCreation_date()));
    }

    public void setOnQuestionClickListener(OnQuestionClickListener onQuestionClickListener) {
        this.onQuestionClickListener = onQuestionClickListener;
    }

    private String longToDate(long time) {
        return  DateFormat.format("dd/MM/yyyy", new Date(time)).toString();
    }

    private Spanned stripHtml(String html) {
        if (!TextUtils.isEmpty(html)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                return Html.fromHtml(html, Html.FROM_HTML_MODE_COMPACT);
            } else {
                return Html.fromHtml(html);
            }
        }
        return null;
    }
    @Override
    public int getItemCount() {
        return questionData.size();
    }

    public void setOnQuestionLongClick(OnQuestionLongClick onQuestionLongClick) {
        this.onQuestionLongClick = onQuestionLongClick;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        CircleImageView circleImageView;
        TextView questionTitle, questionDes, userName, postTime;
         ViewHolder(@NonNull View itemView) {
            super(itemView);
            circleImageView = itemView.findViewById(R.id.user_profile);
            questionTitle = itemView.findViewById(R.id.question_title);
             questionDes = itemView.findViewById(R.id.question_body);
             userName = itemView.findViewById(R.id.user_name);
             postTime = itemView.findViewById(R.id.time);

             questionTitle.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     if (onQuestionClickListener != null) {
                         onQuestionClickListener.onClick(questionData.get(getAdapterPosition()).getLink());
                     }
                 }
             });

             questionTitle.setOnLongClickListener(new View.OnLongClickListener() {
                 @Override
                 public boolean onLongClick(View v) {

                     if (onQuestionLongClick != null)
                         onQuestionLongClick.onLongClick(questionData.get(getAdapterPosition()).getLink(),
                                 questionData.get(getAdapterPosition()).getBody(),questionData.get(getAdapterPosition())
                         .getTitle());

                     return false;
                 }
             });

        }
    }

}
