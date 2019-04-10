package com.Marvel.stackquestion.UI.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.Marvel.stackquestion.model.OfflineData;
import com.jackandphantom.stackquestion.R;
import com.Marvel.stackquestion.Utils.AppDatabase;
import com.Marvel.stackquestion.adapter.OfflineList;

import java.util.List;

public class OfflineActivity extends AppCompatActivity {

    private List<OfflineData> offlineData;
    private AppDatabase appDatabase;
    private RecyclerView recyclerView;
    private TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offline);
         recyclerView = findViewById(R.id.offline_list);
         textView = findViewById(R.id.hide);
        recyclerView.setLayoutManager(new LinearLayoutManager(OfflineActivity.this));
        appDatabase  = AppDatabase.getAppDatabase(OfflineActivity.this);
        new Work().execute();

    }

     class Work extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            offlineData =  appDatabase.userDao().getAll();
            return null;
        }

         @Override
         protected void onPostExecute(Void aVoid) {
             super.onPostExecute(aVoid);
             textView.setVisibility(View.GONE);
             OfflineList offlineList = new OfflineList(offlineData, OfflineActivity.this);
             offlineList.setOnQuestionClickListener(new OfflineList.OnQuestionClickListener() {
                 @Override
                 public void onClick(final String url) {
                     AlertDialog.Builder alertDialog = new AlertDialog.Builder(OfflineActivity.this);

                     // Setting Dialog Title
                     alertDialog.setTitle("Usage Of Question...");

                     // Setting Dialog Message
                     alertDialog.setMessage("Either you can share it and save it?");

                     // Setting Icon to Dialog
                     alertDialog.setIcon(R.drawable.stack);

                     // Setting Positive "Yes" Button
                     alertDialog.setPositiveButton("cancel", new DialogInterface.OnClickListener() {
                         public void onClick(DialogInterface dialog,int which) {

                             // Write your code here to invoke YES event
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
             });
             recyclerView.setAdapter(offlineList);

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
     }
}
