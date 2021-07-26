package sg.edu.rp.c346.id20008460.myndpsongs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.ArrayList;

public class ModifySongs extends AppCompatActivity {

    Button btnUpdate , btnDelete, btnCancel;
    EditText etTitle, etSinger , etYear;

    RadioGroup rgStar;
    RadioButton rbStar1 , rbStar2 , rbStar3 , rbStar4 , rbStar5 ;
    Song data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_songs);

        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);
        btnCancel = findViewById(R.id.buttonCancel);

        etTitle = findViewById(R.id.upTitle);
        etSinger = findViewById(R.id.upSingers);
        etYear = findViewById(R.id.upYear);

        rgStar = findViewById(R.id.updateRB);

        rbStar1 = findViewById(R.id.star1);
        rbStar2 = findViewById(R.id.star2);
        rbStar3 = findViewById(R.id.star3);
        rbStar4 = findViewById(R.id.star4);
        rbStar5 = findViewById(R.id.star5);

        Intent i = getIntent();
        data = (Song) i.getSerializableExtra("data");


        int stars = i.getIntExtra("star" ,0);


        if (stars == 1) {
            rbStar1.setChecked(true);
        } else if (stars == 2) {
            rbStar2.setChecked(true);
        } else if (stars == 3) {
            rbStar3.setChecked(true);
        } else if (stars == 4) {
            rbStar4.setChecked(true);
        } else if (stars == 5) {
            rbStar5.setChecked(true);
        }



        etTitle.setText(data.getTitle());
        etSinger.setText(data.getSingers());
        etYear.setText(Integer.toString(data.getYear()));


        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbh = new DBHelper(ModifySongs.this);
                data.setTitle(etTitle.getText().toString());
                data.setSingers(etSinger.getText().toString());
                data.setYear(Integer.parseInt(etYear.getText().toString()));

                int starRating = rgStar.getCheckedRadioButtonId();
                RadioButton radioButton = (RadioButton) findViewById(starRating);
                int stars = Integer.parseInt(radioButton.getText().toString());
                data.setStars(stars);

                dbh.updateSong(data);
                dbh.close();

                finish();

            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbh = new DBHelper(ModifySongs.this);
                dbh.deleteSong(data.get_id());

                finish();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ModifySongs.this,
                        ShowSongs.class);
                startActivity(i);
            }
        });

    }



}