package com.Marvel.stackquestion.UI.fragment;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.Marvel.stackquestion.UI.activities.WebViewActivity;
import com.Marvel.stackquestion.model.OfflineData;
import com.Marvel.stackquestion.Interface.StackApi;
import com.jackandphantom.stackquestion.R;
import com.Marvel.stackquestion.Utils.AppDatabase;
import com.Marvel.stackquestion.adapter.QuestionListAdapter;
import com.Marvel.stackquestion.model.QuestionItemData;

/**
 * A simple {@link Fragment} subclass.
 */
public class QuestionFragment extends Fragment implements QuestionListAdapter.OnQuestionClickListener, QuestionListAdapter.OnQuestionLongClick {


    private RecyclerView recyclerView;
    private String type, tag;
    private Retrofit retrofit;


    public QuestionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_question, container, false);
        recyclerView = view.findViewById(R.id.question_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        Bundle bundle = getArguments();
        if (bundle != null) {
            type = bundle.getString("TYPE");
            tag = bundle.getString("TAG");
            Log.e("MY TAG", "TYPE OF "+tag);
        }
        retrofit = new Retrofit.Builder()
                .baseUrl("https://api.stackexchange.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e("MY TAG", "I AM IN THE RESUME");
        callForQuestion(1);
    }

    private void callForQuestion(int page) {
        retrofit.create(StackApi.class).stackQuestionCall(page, "desc", type, tag,"stackoverflow",
                "!9Z(-wwYGT").enqueue(new Callback<QuestionItemData>() {
            @Override
            public void onResponse(Call<QuestionItemData> call, Response<QuestionItemData> response) {
                if (response.isSuccessful()) {
                    QuestionItemData questionItemData = response.body();
                    QuestionListAdapter adapter = new QuestionListAdapter(questionItemData.getQuestionData(),
                            getActivity());
                    adapter.setOnQuestionClickListener(QuestionFragment.this);
                    adapter.setOnQuestionLongClick(QuestionFragment.this);
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<QuestionItemData> call, Throwable t) {
              t.printStackTrace();
            }
        });
    }

    @Override
    public void onClick(String url) {
        Intent intent = new Intent(getActivity(), WebViewActivity.class);
        intent.putExtra("URL", url);
        startActivity(intent);
    }

    @Override
    public void onLongClick(final String url, final String body, final String title) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());

        // Setting Dialog Title
        alertDialog.setTitle("Usage Of Question...");

        // Setting Dialog Message
        alertDialog.setMessage("Either you can share it and save it?");

        // Setting Icon to Dialog
        alertDialog.setIcon(R.drawable.stack);

        // Setting Positive "Yes" Button
        alertDialog.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {

                // Write your code here to invoke YES event
                saveQuestion(title, body);
            }
        });

        // Setting Negative "NO" Button
        alertDialog.setNegativeButton("Share", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                shareTextUrl(url);
                // Write your code here to invoke NO event
                dialog.cancel();
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }

    private void shareTextUrl(String url) {
        Intent share = new Intent(android.content.Intent.ACTION_SEND);
        share.setType("text/plain");
        share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);

        // Add data to the intent, the receiving app will decide
        // what to do with it.
        share.putExtra(Intent.EXTRA_SUBJECT, "StackOverFlow Question");
        share.putExtra(Intent.EXTRA_TEXT, url);

        startActivity(Intent.createChooser(share, "Share link!"));
    }

    private void saveQuestion(String title, String body) {
        final OfflineData offlineData = new OfflineData();
        offlineData.setQuestionBody(body);
        offlineData.setQuestionTitle(title);
        final AppDatabase appDatabase =  AppDatabase.getAppDatabase(getActivity());
        new  AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                appDatabase.userDao().insertAll(offlineData);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Toast.makeText(getActivity(), "Data has been Saved", Toast.LENGTH_SHORT).show();
            }
        }.execute();
    }
}
