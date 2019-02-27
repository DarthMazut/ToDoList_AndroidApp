package pum.todolist;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;


public class MainActivity extends AppCompatActivity
{
    public static boolean isReorder = false;
    ListView listView;
    public static ArrayAdapter<listItem> adapter;
    public static List lista = new ArrayList();
    FloatingActionButton fab;
    Button button_reorder;

    TextToSpeech speaker;

    private void setReorder(boolean active)
    {
        if(active)
        {
            button_reorder.setVisibility(View.VISIBLE);
            isReorder = true;
            fab.setVisibility(View.GONE);
        }
        else
        {
            button_reorder.setVisibility(View.GONE);
            isReorder = false;
            fab.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.content_main);



        speaker = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
        @Override
        public void onInit(int status) {
            speaker.setLanguage(Locale.US);
        }
    });


        listView = (ListView) findViewById(R.id.listView);
        button_reorder = (Button) findViewById(R.id.button_reorder);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        adapter = new custom_adapter_class(this, lista);
        listView.setAdapter(adapter);


        registerForContextMenu(listView);



        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(MainActivity.this, Add_Edit.class);
                intent.putExtra("stan", 1);
                startActivityForResult(intent, 1);
            }
        });


        button_reorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setReorder(false);
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {
        setReorder(false);
        return super.onMenuOpened(featureId, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.del_all)
        {
            lista.clear();
            adapter.notifyDataSetChanged();
            Toast.makeText(MainActivity.this, "Usunięto wszystkie elementy listy",Toast.LENGTH_LONG).show();
            return true;
        }
        if(id == R.id.del_checked)
        {

            listItem obj = new listItem();
            for(int i = 0; i < lista.size(); i++)
            {
                obj = (listItem) lista.get(i);
                if(obj.isChecked)
                {
                    lista.remove(lista.indexOf(obj));
                    i--;
                }
            }
            adapter.notifyDataSetChanged();
            return true;
        }
        if(id == R.id.expand_all)
        {
            listItem obj = new listItem();
            for(int i = 0; i < lista.size(); i++)
            {
                obj = (listItem) lista.get(i);
                obj.isExpanded = true;
                lista.set(i,obj);
            }
            adapter.notifyDataSetChanged();
            return true;
        }
        if(id == R.id.wrap_all)
        {
            listItem obj = new listItem();
            for(int i = 0; i < lista.size(); i++)
            {
                obj = (listItem) lista.get(i);
                obj.isExpanded = false;
                lista.set(i,obj);
            }
            adapter.notifyDataSetChanged();
            return true;
        }
        if(id == R.id.stats)
        {
            int ile = lista.size();
            int checked = 0;


            listItem obj = new listItem();
            for(int i = 0; i < lista.size(); i++)
            {
                obj = (listItem) lista.get(i);
                if(obj.isChecked)checked++;
            }
            int toDo = ile - checked;
            Toast.makeText(MainActivity.this, "Pozycji na liście: "+String.valueOf(ile)+
                    "\nPozycji wykonanych: "+String.valueOf(checked)+"\nPozycji do wykonania: "
                    +String.valueOf(toDo),Toast.LENGTH_LONG).show();
            return true;
        }
        if(id == R.id.about)
        {
            Intent intent = new Intent(MainActivity.this, About_Activity.class);
            startActivity(intent);
            return true;
        }
        if(id == R.id.sort)
        {
            Intent intent = new Intent(MainActivity.this, Sort_activity.class);
            startActivity(intent);
            return true;
        }
        if(id == R.id.search)
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("Wyszukaj:");
            final EditText pole = new EditText(MainActivity.this);
            builder.setView(pole);
            builder.setPositiveButton("Szukaj", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                        adapter.getFilter().filter(pole.getText());

                }
            });
            builder.setNegativeButton("Anuluj", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                }
            });


            AlertDialog dialog = builder.create();
            dialog.show();
        }
        if(id == R.id.settings)
        {
            Random kostka = new Random();
            int r = kostka.nextInt(255);
            int g = kostka.nextInt(255);
            int b = kostka.nextInt(255);
            fab.setBackgroundTintList(ColorStateList.valueOf(Color.rgb(r,g,b)));
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)
    {
        super.onCreateContextMenu(menu, v, menuInfo);

        MenuInflater inflater = getMenuInflater();
        if(!isReorder)inflater.inflate(R.menu.context_menu, menu);
        if(isReorder)inflater.inflate(R.menu.context_menu_reorder, menu);


    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int nr = (int)info.id;
        switch (item.getItemId()) {
            case R.id.del:
                lista.remove(nr);
                adapter.notifyDataSetChanged();
                return true;
            case R.id.reorder:
               setReorder(true);
                return true;
            case R.id.put_top:
               // listItem itemE = new listItem();
                //itemE = (listItem) lista.get(nr);
                lista.add(0,lista.get(nr));
                lista.remove(nr+1);
                adapter.notifyDataSetChanged();
                return true;
            case R.id.put_bottom:
                lista.add(lista.get(nr));
                lista.remove(nr);
                adapter.notifyDataSetChanged();
                return true;
            case R.id.edit:
                Intent intent = new Intent(MainActivity.this, Add_Edit.class);
                intent.putExtra("stan", 2);
                intent.putExtra("itemID", nr);
                startActivityForResult(intent, 1);
                return true;
            case R.id.read:
                listItem Obiekt = (listItem) lista.get(nr);
                speaker.speak(Obiekt.header,TextToSpeech.QUEUE_ADD,Bundle.EMPTY,null);
                speaker.speak(Obiekt.desc,TextToSpeech.QUEUE_ADD,Bundle.EMPTY,null);
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if(resultCode == 1)
        {

            listItem zadanie = new listItem("A","Nie-przesłano-stringa","XXX",false,new Date(), false );
            zadanie.header = data.getStringExtra("header");
            if(zadanie.header.equals(""))zadanie.header = "Temu zadaniu nie przypisano nazwy";
            zadanie.desc = data.getStringExtra("desc");
            zadanie.priorytet = data.getStringExtra("prior");
            int Year = data.getIntExtra("mYear",2033);
            int Month = data.getIntExtra("mMonth",1);
            int Day = data.getIntExtra("mDay",1);
            int Hour = data.getIntExtra("mHour",0);
            int Minute = data.getIntExtra("mMinute",0);
            String dataString = String.valueOf(Day)+"/"+String.valueOf(Month)+"/"+String.valueOf(Year)+"/"+
                    String.valueOf(Hour)+"/"+String.valueOf(Minute);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy/HH/mm");
            Date dataDoPrzekazania = new Date();
            try
            {
                dataDoPrzekazania = simpleDateFormat.parse(dataString);
            }
            catch (ParseException e)
            {
                Toast.makeText(MainActivity.this,"Error - exception caught",Toast.LENGTH_LONG).show();
            }
            zadanie.data_deadline = dataDoPrzekazania;
            lista.add(zadanie);
            Toast.makeText(MainActivity.this,"Dodano zadnie",Toast.LENGTH_LONG).show();
        }
        else if(resultCode == 2)
        {
            listItem zadanie = new listItem("A","Nie-przesłano-stringa","XXX",false,new Date(), false );
            zadanie.header = data.getStringExtra("header");
            if(zadanie.header.equals(""))zadanie.header = "Temu zadaniu nie przypisano nazwy";
            zadanie.desc = data.getStringExtra("desc");
            zadanie.priorytet = data.getStringExtra("prior");
            int Year = data.getIntExtra("mYear",2033);
            int Month = data.getIntExtra("mMonth",1);
            int Day = data.getIntExtra("mDay",1);
            int Hour = data.getIntExtra("mHour",0);
            int Minute = data.getIntExtra("mMinute",0);
            String dataString = String.valueOf(Day)+"/"+String.valueOf(Month)+"/"+String.valueOf(Year)+"/"+
                    String.valueOf(Hour)+"/"+String.valueOf(Minute);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy/HH/mm");
            Date dataDoPrzekazania = new Date();
            try
            {
                dataDoPrzekazania = simpleDateFormat.parse(dataString);
            }
            catch (ParseException e)
            {
                Toast.makeText(MainActivity.this,"Error - exception caught",Toast.LENGTH_LONG).show();
            }
            zadanie.data_deadline = dataDoPrzekazania;
            listItem x;
            x = (listItem)lista.get(data.getIntExtra("itemID",0));
            zadanie.data_create = x.data_create;
            lista.set(data.getIntExtra("itemID",0),zadanie);
            Toast.makeText(MainActivity.this,"Zadanie zostało zedytowane",Toast.LENGTH_LONG).show();
        }
        adapter.notifyDataSetChanged();
    }
}
