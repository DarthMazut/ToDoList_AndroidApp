package pum.todolist;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;


public class Add_Edit extends AppCompatActivity {


    String[] priorTab = new String[5];

    String[] months = {"Styczeń","Luty","Marzec","Kwiecień","Maj","Czerwiec","Lipiec","Sierpień","Wrzesień","Październik",
    "Listopad","Grudzień"};

    Spinner spinner;
    Button choose_date;
    EditText header;
    EditText desc;
    ImageView prior;
    Button apply;
    Button cancel;
    Button choose_time;
    TextView title;

    listItem obiektTemp;

    private int mYear = 2033, mMonth = 1, mDay = 1, mHour = 0, mMinute = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__edit);



        priorTab[0] = getResources().getString(R.string.prior_A);
        priorTab[1] = getResources().getString(R.string.prior_B);
        priorTab[2] = getResources().getString(R.string.prior_C);
        priorTab[3] = getResources().getString(R.string.prior_D);
        priorTab[4] = getResources().getString(R.string.prior_E);

        title = (TextView) findViewById(R.id.textView_title);
        spinner = (Spinner) findViewById(R.id.spinner);
        choose_date = (Button) findViewById(R.id.editText_deadline);
        header = (EditText) findViewById(R.id.editText_title);
        desc = (EditText) findViewById(R.id.editText_desc);
        prior = (ImageView) findViewById(R.id.imageView_prior);
        apply = (Button) findViewById(R.id.button_apply);
        cancel = (Button) findViewById(R.id.button_cancel);
        choose_time = (Button) findViewById(R.id.button_time);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_custom, priorTab );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(parent.getItemAtPosition(position) == priorTab[0]) prior.setImageResource(R.drawable.a_letter);
                else if(parent.getItemAtPosition(position) == priorTab[1]) prior.setImageResource(R.drawable.b_letter);
                else if(parent.getItemAtPosition(position) == priorTab[2]) prior.setImageResource(R.drawable.c_letter);
                else if(parent.getItemAtPosition(position) == priorTab[3]) prior.setImageResource(R.drawable.d_letter);
                else if(parent.getItemAtPosition(position) == priorTab[4]) prior.setImageResource(R.drawable.e_letter);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        if(getIntent().getIntExtra("stan",2) == 2)
        {
            title.setText(getString(R.string.add_edit_edit));

            int nrObiektu = getIntent().getIntExtra("itemID", 0);
            obiektTemp = (listItem) MainActivity.lista.get(nrObiektu);
            header.setText(obiektTemp.header);
            desc.setText(obiektTemp.desc);


            if(obiektTemp.priorytet.equals(priorTab[0]))spinner.setSelection(0);
            else if(obiektTemp.priorytet.equals(priorTab[1]))spinner.setSelection(1);
            else if(obiektTemp.priorytet.equals(priorTab[2]))spinner.setSelection(2);
            else if(obiektTemp.priorytet.equals(priorTab[3]))spinner.setSelection(3);
            else spinner.setSelection(4);

            Calendar cal = Calendar.getInstance();
            cal.setTime(obiektTemp.data_deadline);

            mYear = cal.get(Calendar.YEAR);
            mMonth = cal.get(Calendar.MONTH)+1;
            mDay = cal.get(Calendar.DAY_OF_MONTH);
            mHour = cal.get(Calendar.HOUR_OF_DAY);
            mMinute = cal.get(Calendar.MINUTE);

            choose_date.setText(mDay + " " + months[(mMonth)] + " " + mYear);
            if(mMinute < 10) choose_time.setText(mHour + " : 0" + mMinute);
            else choose_time.setText(mHour + " : " + mMinute);

        }







        choose_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(Add_Edit.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                choose_date.setText(dayOfMonth + " " + months[(monthOfYear)] + " " + year);
                                mYear = year;
                                mMonth = monthOfYear + 1;
                                mDay = dayOfMonth;
                            }
                        }, mYear, mMonth, mDay);

                datePickerDialog.getDatePicker().setMinDate(new Date().getTime());
                datePickerDialog.show();

            }
        });

        choose_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Get Current Time
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(Add_Edit.this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                if(minute < 10) choose_time.setText(hourOfDay + " : 0" + minute);
                                    else choose_time.setText(hourOfDay + " : " + minute);
                                    mHour = hourOfDay;
                                    mMinute = minute;
                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();

            }
        });


        header.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if(hasFocus == true && header.getText().toString().equals(getString(R.string.add_edit_title_temp)))
                {
                    header.setText("");
                }
                else if(hasFocus == false && header.getText().toString().equals(""))
                {
                    header.setText(getString(R.string.add_edit_title_temp));
                }
            }
        });

        desc.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if(hasFocus == true && desc.getText().toString().equals(getString(R.string.add_edit_desc_temp)))
                {
                    desc.setText("");
                }
                else if(hasFocus == false && desc.getText().toString().equals(""))
                {
                    desc.setText(getString(R.string.add_edit_desc_temp));
                }

            }
        });

        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();

                intent.putExtra("header", header.getText().toString());
                intent.putExtra("desc", desc.getText().toString());
                intent.putExtra("prior", priorTab[spinner.getSelectedItemPosition()]);
                intent.putExtra("mYear", mYear);
                intent.putExtra("mMonth", mMonth);
                intent.putExtra("mDay",mDay);
                intent.putExtra("mHour", mHour);
                intent.putExtra("mMinute", mMinute);
                if(getIntent().getIntExtra("stan",1) == 1)
                {
                    setResult(1, intent);
                }
                else
                {

                    intent.putExtra("itemID", intent.getIntExtra("itemID",0));
                    setResult(2, intent);
                }
                finish();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();

                setResult(0, intent);
                finish();
            }
        });

    }
}
