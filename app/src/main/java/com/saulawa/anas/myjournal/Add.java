package com.saulawa.anas.myjournal;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.Date;

public class Add extends AppCompatActivity {

    public static final String EXTRA_ENTRY_ID = "extraTaskId";
    public static final String EXTRA_TASK = "extraEntryId";
    public static final String INSTANCE_ENTRY_ID = "instanceEntryid";
    EditText entrytitle, entrydescription;
    private Button saveentry;
    private RadioButton happy, angry, embrassed, crying, sad, surprised, inlove, hilarious, glad;
    private RadioButton thinking, kiss, sick, angrytwo, crazy, confused, bored, omg, favourite, cool;
    private static final int DEFAULT_ENTRY_ID = -1;
    private int mEntryId = DEFAULT_ENTRY_ID;
    private AppDatabase mDb;
    private Bitmap bm, happyb, angryb, cryingb, embarassedb, inloveb, sadb, surprisedb, gladb, hilariousb;
    private Bitmap thinkingb, kissb, sickb, angrytwob, crazyb, confusedb, boredb, omgb, coolb, favouriteb;

    private byte[] emoji;

    private FloatingActionButton fb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);


        entrytitle = findViewById(R.id.entrytitletxt);
        entrydescription = findViewById(R.id.entry_descriptiontxt);

        happy = findViewById(R.id.happyemoji);
        angry = findViewById(R.id.angryemoji);
        sad = findViewById(R.id.sademoji);
        embrassed = findViewById(R.id.embrassedemoji);
        surprised = findViewById(R.id.surprisedemoji);
        crying = findViewById(R.id.cryingemoji);
        inlove = findViewById(R.id.inloveemoji);
        hilarious = findViewById(R.id.hilariousemoji);
        thinking = findViewById(R.id.thinkingemoji);
        kiss = findViewById(R.id.kissemoji);
        sick = findViewById(R.id.sickemoji);
        angrytwo = findViewById(R.id.angrytwoemoji);
        crazy = findViewById(R.id.crazyemoji);
        confused = findViewById(R.id.confusedemoji);
        bored = findViewById(R.id.boredemoji);
        omg = findViewById(R.id.omgemoji);
        favourite = findViewById(R.id.favemoji);
        cool = findViewById(R.id.coolemoji);
        saveentry = findViewById(R.id.saveb);
        angry = findViewById(R.id.angryemoji);
        saveentry = findViewById(R.id.saveb);
        fb = findViewById(R.id.cancelfbadd);
        glad = findViewById(R.id.glademoji);
        happyb = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.happy);
        angryb = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.angry);
        cryingb = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.crying);
        sadb = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.sad);
        surprisedb = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.surprised);
        embarassedb = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.embarassed);
        inloveb = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.inlove);
        hilariousb = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.hilariuos);
        thinkingb = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.thinking);
        kissb = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.kiss);
        sickb = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.sick);
        angrytwob = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.angrytwo);
        crazyb = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.crazy);
        confusedb = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.confused);
        boredb = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.bored);
        omgb = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.omg);
        favouriteb = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.favourite);
        coolb = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.cool);
        gladb = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.glad);

        mDb = AppDatabase.getsInstance(getApplicationContext());

        if (savedInstanceState != null && savedInstanceState.containsKey(INSTANCE_ENTRY_ID)) {
            mEntryId = savedInstanceState.getInt(INSTANCE_ENTRY_ID, DEFAULT_ENTRY_ID);
        }

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(EXTRA_ENTRY_ID)) {
            saveentry.setText("Update");

            if (mEntryId == DEFAULT_ENTRY_ID) {
                mEntryId = intent.getIntExtra(EXTRA_ENTRY_ID, DEFAULT_ENTRY_ID);

//     final LiveData<JournalEntryModel> entry = mDb.entryDao().loadEntryById(mEntryId);

                AddEntryViewModelFactory factory = new AddEntryViewModelFactory(mDb, mEntryId);
                final AddEntryViewModel viewModel
                        = ViewModelProviders.of(this, factory).get(AddEntryViewModel.class);


                viewModel.getEntry().observe(this, new Observer<JournalEntryModel>() {
                    @Override
                    public void onChanged(@Nullable JournalEntryModel journalEntryModel) {

                        viewModel.getEntry().removeObserver(this);
                        if (journalEntryModel == null) {
                            return;
                        }
                        entrytitle.setText(journalEntryModel.getTitle());
                        entrydescription.setText(journalEntryModel.getDescription());

                    }
                });

            }

        }

        saveentry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSaveButtonClicked();
            }
        });


        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), Home.class);
                startActivity(i);
            }
        });
    }

    private void onSaveButtonClicked() {

        String title = entrytitle.getText().toString();
        String description = entrydescription.getText().toString();
        Date date = new Date();

        if (entrytitle.getText().toString().equals("") ||
                entrydescription.getText().toString().equals("")) {
            Toast.makeText(this, "Entry cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        if (happy.isChecked()) {
            emoji = emojibyte(happyb);
        } else if (angry.isChecked()) {
            emoji = emojibyte(angryb);
        } else if (embrassed.isChecked()) {
            emoji = emojibyte(embarassedb);
        } else if (sad.isChecked()) {
            emoji = emojibyte(sadb);
        } else if (crying.isChecked()) {
            emoji = emojibyte(cryingb);
        } else if (inlove.isChecked()) {
            emoji = emojibyte(inloveb);
        } else if (surprised.isChecked()) {
            emoji = emojibyte(surprisedb);
        } else if (hilarious.isChecked()) {
            emoji = emojibyte(hilariousb);
        } else if (omg.isChecked()) {
            emoji = emojibyte(omgb);
        } else if (crazy.isChecked()) {
            emoji = emojibyte(crazyb);
        } else if (angrytwo.isChecked()) {
            emoji = emojibyte(angrytwob);
        } else if (favourite.isChecked()) {
            emoji = emojibyte(favouriteb);
        } else if (thinking.isChecked()) {
            emoji = emojibyte(thinkingb);
        } else if (bored.isChecked()) {
            emoji = emojibyte(boredb);
        } else if (confused.isChecked()) {
            emoji = emojibyte(confusedb);
        } else if (kiss.isChecked()) {
            emoji = emojibyte(kissb);
        } else if (sick.isChecked()) {
            emoji = emojibyte(sickb);
        } else if (cool.isChecked()) {
            emoji = emojibyte(coolb);
        } else if (glad.isChecked()) {
            emoji = emojibyte(gladb);
        }


        final JournalEntryModel entry = new JournalEntryModel(title,
                description, emoji, date);
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                if (mEntryId == DEFAULT_ENTRY_ID) {
                    mDb.entryDao().insertEntry(entry);

                } else {
                    entry.setId(mEntryId);
                    mDb.entryDao().updateEntry(entry);
                }
                finish();
            }
        });
    }


    private byte[] emojibyte(Bitmap b) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        b.compress(Bitmap.CompressFormat.PNG, 0, bos);
        return bos.toByteArray();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(INSTANCE_ENTRY_ID, mEntryId);
        super.onSaveInstanceState(outState);
    }

    private Bitmap converttobitma(byte[] b) {
        return BitmapFactory.decodeByteArray(b, 0, b.length);
    }
}
