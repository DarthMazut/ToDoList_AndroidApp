package pum.todolist;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Comparator;

public class Sort_activity extends AppCompatActivity {

    String[] wgTab = {"Tytuł", "Priorytet", "Data utworzenia", "Termin"};
    String[] typTab = {"Rosnąco","Malejąco"};


    Button apply;
    Button cancel;
    Spinner spinnerWG;
    Spinner spinnerTYP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sort);

        apply = (Button) findViewById(R.id.button_apply);
        cancel = (Button) findViewById(R.id.button_cancel);
        spinnerWG = (Spinner) findViewById(R.id.spinnerWG);
        spinnerTYP  = (Spinner) findViewById(R.id.spinnerTYP);

        ArrayAdapter<String> adapterWG = new ArrayAdapter<String>(this, R.layout.spinner_custom, wgTab );
        adapterWG.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerWG.setAdapter(adapterWG);




        ArrayAdapter<String> adapterTYP = new ArrayAdapter<String>(this, R.layout.spinner_custom, typTab );
        adapterTYP.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTYP.setAdapter(adapterTYP);



        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(spinnerWG.getSelectedItem() == wgTab[0])
                {

                    if(spinnerTYP.getSelectedItem() == typTab[0])
                    {
                        MainActivity.adapter.sort(new Comparator<listItem>() {
                            @Override
                            public int compare(listItem o1, listItem o2) {
                                return o1.header.compareToIgnoreCase(o2.header);
                            }
                        });
                    }
                    else
                    {
                        MainActivity.adapter.sort(new Comparator<listItem>() {
                            @Override
                            public int compare(listItem o1, listItem o2) {
                                return o2.header.compareToIgnoreCase(o1.header);
                            }
                        });
                    }
                }

                if(spinnerWG.getSelectedItem() == wgTab[1])
                {

                    if(spinnerTYP.getSelectedItem() == typTab[0])
                    {
                        MainActivity.adapter.sort(new Comparator<listItem>() {
                            @Override
                            public int compare(listItem o1, listItem o2) {
                                return o1.priorytet.compareToIgnoreCase(o2.priorytet);
                            }
                        });
                    }
                    else
                    {
                        MainActivity.adapter.sort(new Comparator<listItem>() {
                            @Override
                            public int compare(listItem o1, listItem o2) {
                                return o2.priorytet.compareToIgnoreCase(o1.priorytet);
                            }
                        });
                    }
                }

                if(spinnerWG.getSelectedItem() == wgTab[2])
                {

                    if(spinnerTYP.getSelectedItem() == typTab[0])
                    {
                        MainActivity.adapter.sort(new Comparator<listItem>() {
                            @Override
                            public int compare(listItem o1, listItem o2) {
                                return o1.data_create.compareTo(o2.data_create);
                            }
                        });
                    }
                    else
                    {
                        MainActivity.adapter.sort(new Comparator<listItem>() {
                            @Override
                            public int compare(listItem o1, listItem o2) {
                                return o2.data_create.compareTo(o1.data_create);
                            }
                        });
                    }
                }

                if(spinnerWG.getSelectedItem() == wgTab[3])
                {

                    if(spinnerTYP.getSelectedItem() == typTab[0])
                    {
                        MainActivity.adapter.sort(new Comparator<listItem>() {
                            @Override
                            public int compare(listItem o1, listItem o2) {
                                return o1.data_deadline.compareTo(o2.data_deadline);
                            }
                        });
                    }
                    else
                    {
                        MainActivity.adapter.sort(new Comparator<listItem>() {
                            @Override
                            public int compare(listItem o1, listItem o2) {
                                return o2.data_deadline.compareTo(o1.data_deadline);
                            }
                        });
                    }
                }

                Toast.makeText(Sort_activity.this,"Sortowanie zakończone sukcesem.",Toast.LENGTH_LONG).show();
                finish();

            }
        });


    }
}
