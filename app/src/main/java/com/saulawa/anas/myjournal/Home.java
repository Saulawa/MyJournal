package com.saulawa.anas.myjournal;

import android.app.Activity;
import android.app.ActivityOptions;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.transition.Explode;
import android.transition.Slide;
import android.transition.TransitionInflater;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Home extends AppCompatActivity implements EntryAdapter.ItemClickListener {
    RecyclerView preview;
    FloatingActionButton addb;
    EntryAdapter mAdapter;
    private AppDatabase mDb;
    JournalEntryModel journalEntryModel;
    private static final String DATE_FORMAT = "dd/MM/yyy";
    private SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.getDefault());
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        preview = findViewById(R.id.recyclerviewitempreview);
        addb = findViewById(R.id.floatingActionButton);
        preview.setLayoutManager(new LinearLayoutManager(this));

        mAdapter = new EntryAdapter(this, this);

        preview.setAdapter(mAdapter);

        DividerItemDecoration decoration = new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL);
        preview.addItemDecoration(decoration);

//        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
//                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
//            @Override
//            public boolean onMove(RecyclerView recyclerView,
//                                  RecyclerView.ViewHolder viewHolder,
//                                  RecyclerView.ViewHolder target) {
//                return false;
//            }
//
//            @Override
//            public void onSwiped(final RecyclerView.ViewHolder viewHolder, int direction) {
//
//                AppExecutors.getInstance().diskIO().execute(new Runnable() {
//                    @Override
//                    public void run() {
//                        int position = viewHolder.getAdapterPosition();
//                        List<JournalEntryModel> entries = mAdapter.getEntries();
//                        mDb.entryDao().deleteEntry(entries.get(position));
//
//                    }
//                });
//
//            }
//        }).attachToRecyclerView(preview);

        addb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent addentryintent = new Intent(Home.this, Add.class);

                startActivity(addentryintent);

            }
        });

        mDb = AppDatabase.getsInstance(getApplicationContext());
        setupViewModel();
    }


    @Override
    protected void onResume() {
        super.onResume();
//        mAdapter.setEntries(mDb.entryDao().loadAllEntries());
    }

    private void setupViewModel() {

        MainViewModel viewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        viewModel.getEntries().observe(this, new Observer<List<JournalEntryModel>>() {
            @Override
            public void onChanged(@Nullable List<JournalEntryModel> journalEntryModels) {
                mAdapter.setEntries(journalEntryModels);
            }
        });
    }

    @Override
    public void onItemClickListener(String title, String description, byte[] icon, Date date) {

        Intent detail = new Intent(this, DetailActivity.class);
        detail.putExtra("title", title);
        detail.putExtra("detail", description);
        detail.putExtra("image", icon);
        String dateinstring = dateFormat.format(date);

        detail.putExtra("date",dateinstring);

        startActivity(detail);

    }

    @Override
    public void onMenuAction(int i, MenuItem item) {

        if (item.getItemId() == R.id.delete) {
            deleteEnty(i);
        } else if (item.getItemId() == R.id.edit) {
            upDateEntries(i);
        }
    }

    public void upDateEntries(int position) {
        Intent detail = new Intent(this, Add.class);
        detail.putExtra(Add.EXTRA_ENTRY_ID, position);

        startActivity(detail);
    }

    public void deleteEnty(final int position) {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {

                List<JournalEntryModel> entries = mAdapter.getEntries();
                mDb.entryDao().deleteEntry(entries.get(position));

            }
        });
    }

}
