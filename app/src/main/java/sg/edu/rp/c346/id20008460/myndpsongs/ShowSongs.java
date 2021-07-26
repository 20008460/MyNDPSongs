package sg.edu.rp.c346.id20008460.myndpsongs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cursoradapter.widget.CursorAdapter;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;


public class ShowSongs extends AppCompatActivity {


    Button btnFiveStars;

    ListView lvSong;
    //ArrayAdapter<Song> aa ;
    CustomAdapter customAdapter;
    ArrayAdapter<Integer> aaYears; // store all the years
    ArrayList<Song> alSong  , alDuplicate ;
    ArrayList<Integer> alYear ;
    Spinner spnYears;
    HashSet<Integer> hs;

    //
    private String filteredYear;
    private int intFilterYear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_songs);

        btnFiveStars = findViewById(R.id.button5stars);
        lvSong = findViewById(R.id.lvSongs);
        spnYears = findViewById(R.id.spinner);

        DBHelper dbh = new DBHelper(ShowSongs.this);

        alSong = new ArrayList<Song>();
        alDuplicate = new ArrayList<Song>();
        alYear = new ArrayList<Integer>();

        alSong.addAll(dbh.getAllSong());

        for (int i = 0; i < alSong.size(); i++) {
            alYear.add(alSong.get(i).getYear());
        }

        Collections.sort(alYear);


        hs = new HashSet();
        hs.addAll(alYear);

        alYear.clear();
        alYear.addAll(hs);

        customAdapter = new CustomAdapter(ShowSongs.this, R.layout.row , alSong);

        aaYears = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, alYear);
        spnYears.setAdapter(aaYears);

        lvSong.setAdapter(customAdapter);

        lvSong.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int
                    position, long identity) {
                Song data = alSong.get(position);
                Intent i = new Intent(ShowSongs.this,
                        ModifySongs.class);

                int starValue = alSong.get(position).getStars();
                i.putExtra("data", data);
                i.putExtra("star", starValue);
                startActivity(i);
            }
        });

        btnFiveStars.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbh = new DBHelper(ShowSongs.this);
                alSong.clear();
                alDuplicate.clear();
                int keyword = 5;

                alDuplicate.addAll(dbh.getAllSong(keyword));

                for (int i = 0 ; i < alDuplicate.size(); i++) {
                    if (alDuplicate.get(i).getYear() == intFilterYear ) {
                        alSong.add(alDuplicate.get(i));
                    }
                }

                customAdapter.notifyDataSetChanged();
            }
        });

        spnYears.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Spinner mySpinner = (Spinner) findViewById(R.id.spinner);
                String text = mySpinner.getSelectedItem().toString(); // get the year of the spinner selected
//                Toast.makeText(getApplicationContext(), position+1 + " : " + text , Toast.LENGTH_LONG).show();

                int yearCount = spnYears.getAdapter().getCount(); // how many unique years there are
                SharedPreferences prefs = getPreferences(MODE_PRIVATE);
                SharedPreferences.Editor prefeditor = prefs.edit();

                prefeditor.putInt("spnIndex" , position);


                int year = Integer.parseInt(text); // change it into int (year)
                DBHelper dbh = new DBHelper(ShowSongs.this);
                int keyword = year;

                for (int i = 0 ; i < yearCount ; i ++) {
                    if (position == i) {
                        alSong.clear();
                        alSong.addAll(dbh.filterYear(keyword));
                        filteredYear = Integer.toString(keyword);
                        intFilterYear = keyword;

                    }
                }

                prefeditor.putInt("year" , keyword );
                customAdapter.notifyDataSetChanged();
                prefeditor.commit();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences prefs = getPreferences(MODE_PRIVATE);
        int x = prefs.getInt("spnIndex" , 0);
        int year = prefs.getInt("year" , 0);

        spnYears.setSelection(x); // let it remain at the spinner selection

        alSong.clear();
        alDuplicate.clear();
        alYear.clear();

        DBHelper dbh = new DBHelper(ShowSongs.this);
        alSong.addAll(dbh.getAllSong());


        // to retrieve song years
        for (int i = 0 ; i < alSong.size() ; i ++) {
            alYear.add(alSong.get(i).getYear());
        }


        for (int t = 0 ; t < alSong.size() ; t ++) {
            if (alSong.get(t).getYear()  == year) {
                alDuplicate.add(alSong.get(t));
            }
        }
        alSong.clear();
        for (int h = 0 ; h < alDuplicate.size() ; h ++) {
            alSong.add(alDuplicate.get(h));
        }


        alDuplicate.clear();
        customAdapter.notifyDataSetChanged();

        hs.clear();
        hs.addAll(alYear);

        alYear.clear();
        alYear.addAll(hs);

        Collections.sort(alYear);

        aaYears.notifyDataSetChanged();


    }



}