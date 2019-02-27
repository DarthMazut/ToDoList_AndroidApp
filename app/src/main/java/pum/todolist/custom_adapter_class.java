package pum.todolist;


import android.app.Application;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SectionIndexer;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class custom_adapter_class extends ArrayAdapter<listItem> implements Filterable
{
    int defaultowyColor;
    ColorStateList defaultowyColor_timeleft;
    ViewGroup.LayoutParams defaultLayout;

    ArrayList<listItem> filterResultsData;


    String A;
    String B;
    String C;
    String D;
    String E;



    @Override
    public Filter getFilter()
    {

        return new Filter()
        {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence)
            {
                FilterResults results = new FilterResults();

                //If there's nothing to filter on, return the original data for your list
                if(charSequence == null || charSequence.length() == 0)
                {
                    results.values = MainActivity.lista;
                    results.count = MainActivity.lista.size();
                }
                else
                {
                    filterResultsData = new ArrayList<listItem>();

                    for(int i = 0; i < MainActivity.lista.size(); i++)
                    {
                        listItem obiekt = (listItem) MainActivity.lista.get(i);
                        //In this loop, you'll filter through originalData and compare each item to charSequence.
                        //If you find a match, add it to your new ArrayList
                        //I'm not sure how you're going to do comparison, so you'll need to fill out this conditional
                        if(obiekt.header.toLowerCase().contains(charSequence.toString().toLowerCase()))
                        {
                            filterResultsData.add(obiekt);
                        }
                    }

                    results.values = filterResultsData;
                    results.count = filterResultsData.size();
                }

                return results;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults)
            {

                Toast.makeText(getContext(), "Znaleziono "+String.valueOf(filterResults.count)+" pasujących zadań i przesunięto na szczyt listy.", Toast.LENGTH_LONG).show();

                for(listItem obiekt : filterResultsData)
                {
                    int nr = MainActivity.lista.indexOf(obiekt);
                    MainActivity.lista.add(0,MainActivity.lista.get(nr));
                    MainActivity.lista.remove(nr+1);
                }

                notifyDataSetChanged();
            }
        };
    }












    public custom_adapter_class(Context context, List<listItem> listaPrzekazana)
    {
        super(context,R.layout.item_row, listaPrzekazana);

         A = context.getResources().getString(R.string.prior_A);
         B = context.getResources().getString(R.string.prior_B);
         C = context.getResources().getString(R.string.prior_C);
         D = context.getResources().getString(R.string.prior_D);
         E = context.getResources().getString(R.string.prior_E);

    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {


        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.item_row, parent, false);

        final listItem biezaceZadanie = new listItem();
        biezaceZadanie.priorytet = getItem(position).priorytet;
        biezaceZadanie.header = getItem(position).header;
        biezaceZadanie.desc = getItem(position).desc;
        biezaceZadanie.isChecked = getItem(position).isChecked;
        biezaceZadanie.data_deadline = getItem(position).data_deadline;
        biezaceZadanie.data_create = getItem(position).data_create;
        biezaceZadanie.isExpanded = getItem(position).isExpanded;

        RelativeLayout relativeLayout = (RelativeLayout) view.findViewById(R.id.relativeLayout);
        defaultowyColor = relativeLayout.getDrawingCacheBackgroundColor();


        ImageView imageView_prior = (ImageView) view.findViewById(R.id.imageView_prior);
        final ImageView imageView_arrow = (ImageView) view.findViewById(R.id.imageView_arrow);
        final CheckBox checkBox = (CheckBox) view.findViewById(R.id.checkBox);
        ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        final TextView textView_header = (TextView) view.findViewById(R.id.textView_header);
        final TextView textView_desc = (TextView) view.findViewById(R.id.textView_desc);
        TextView textView_date_deadline = (TextView) view.findViewById(R.id.textView_date_deadline);
        TextView textView_date_created = (TextView) view.findViewById(R.id.textView_date_created);
        TextView textView_created = (TextView) view.findViewById(R.id.textView_created);
        TextView textView_deadline = (TextView) view.findViewById(R.id.textView_deadline);
        TextView textView_timeleft = (TextView) view.findViewById(R.id.textView_timeleft);
        TextView textView_timeleft_value = (TextView) view.findViewById(R.id.textView_timeleft_value);

        RelativeLayout relativeLayout_inner = (RelativeLayout) view.findViewById(R.id.relativeLayout_inner);
        RelativeLayout relativeLayout_arrows = (RelativeLayout) view.findViewById(R.id.relativeLayout_arrows);
        ImageView imageView_moveup = (ImageView) view.findViewById(R.id.imageView_moveup);
        ImageView imageView_movedown = (ImageView) view.findViewById(R.id.imageView_movedown);

        defaultowyColor_timeleft = textView_timeleft_value.getTextColors();
        defaultLayout = relativeLayout_inner.getLayoutParams();


        if(MainActivity.isReorder)
        {
            relativeLayout_arrows.setVisibility(View.VISIBLE);

        }
        else
        {
            relativeLayout_arrows.setVisibility(View.GONE);
        }

        imageView_moveup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(position != 0)
                {
                    listItem Item_current = (listItem) MainActivity.lista.get(position);
                    listItem Item_upper = (listItem) MainActivity.lista.get(position-1);

                    MainActivity.lista.set(position,Item_upper);
                    MainActivity.lista.set(position-1,Item_current);
                    MainActivity.adapter.notifyDataSetChanged();
                }
            }
        });

        imageView_movedown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(position != MainActivity.lista.size()-1)
                {
                    listItem Item_current = (listItem) MainActivity.lista.get(position);
                    listItem Item_down = (listItem) MainActivity.lista.get(position+1);

                    MainActivity.lista.set(position,Item_down);
                    MainActivity.lista.set(position+1,Item_current);
                    MainActivity.adapter.notifyDataSetChanged();
                }
            }
        });

        imageView_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(biezaceZadanie.isExpanded) biezaceZadanie.isExpanded = false;
                        else biezaceZadanie.isExpanded = true;

                MainActivity.adapter.notifyDataSetChanged();
            }
        });

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(checkBox.isChecked())biezaceZadanie.isChecked = true;
                else biezaceZadanie.isChecked = false;
                MainActivity.adapter.notifyDataSetChanged();
            }
        });

        textView_header.setText(biezaceZadanie.header);
        textView_desc.setText(biezaceZadanie.desc);
        if(biezaceZadanie.isChecked)
        {
            checkBox.setChecked(true);
            relativeLayout.setBackgroundColor(Color.rgb(69,129,82));
            progressBar.setVisibility(View.GONE);
            textView_timeleft.setVisibility(View.GONE);
            textView_timeleft_value.setVisibility(View.GONE);

        }
        else
        {
            checkBox.setChecked(false);
            relativeLayout.setBackgroundColor(defaultowyColor);
            progressBar.setVisibility(View.VISIBLE);
            textView_timeleft.setVisibility(View.VISIBLE);
            textView_timeleft_value.setVisibility(View.VISIBLE);
        }

        if(biezaceZadanie.isExpanded)
        {
            imageView_arrow.setImageResource(R.drawable.arrow_up);
            relativeLayout_inner.setLayoutParams(defaultLayout);
            //relativeLayout_inner.setVisibility(View.VISIBLE);
        }
        else
        {
            imageView_arrow.setImageResource(R.drawable.arrow_down);
            //relativeLayout_inner.setVisibility(View.GONE);
            defaultLayout.height = 0;
            relativeLayout_inner.setLayoutParams(defaultLayout);

        }


        if(biezaceZadanie.priorytet.equals(A))imageView_prior.setImageResource(R.drawable.a_letter);
        else if(biezaceZadanie.priorytet.equals(B)) imageView_prior.setImageResource(R.drawable.b_letter);
        else if(biezaceZadanie.priorytet.equals(C))imageView_prior.setImageResource(R.drawable.c_letter);
        else if(biezaceZadanie.priorytet.equals(D)) imageView_prior.setImageResource(R.drawable.d_letter);
        else if(biezaceZadanie.priorytet.equals(E))imageView_prior.setImageResource(R.drawable.e_letter);


        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy / HH:mm:ss (EEE)");
        String Created_fix = simpleDateFormat.format(biezaceZadanie.data_create);
        String Deadline_fix = simpleDateFormat.format(biezaceZadanie.data_deadline);


        long different = biezaceZadanie.data_deadline.getTime() - new Date().getTime();

        long elapsedDays = different / 86400000;
        different = different % 86400000;
        long elapsedHours = different / 3600000;
        different = different % 3600000;
        long elapsedMinutes = different / 60000;
        different = different % 60000;

        if(different < 0)
        {
            elapsedDays *= -1;
            elapsedHours *= -1;
            elapsedMinutes *= -1;
        }

        String timeLeft = String.valueOf(elapsedDays)+" dni, "+String.valueOf(elapsedHours)+" godz., "+String.valueOf(elapsedMinutes)+" min.";

        textView_date_created.setText(Created_fix);
        textView_date_deadline.setText(Deadline_fix);
        textView_timeleft_value.setText(timeLeft);

        long present = new Date().getTime();
        long end = biezaceZadanie.data_deadline.getTime();
        long start = biezaceZadanie.data_create.getTime();

        long pBarValue = (long)((double)(present-start)/(end-start)*100);
        if(pBarValue <= 100 && pBarValue >= 0)
        {
            progressBar.setProgress((int)pBarValue);
            textView_timeleft.setText("Pozostały czas: ");
            textView_timeleft_value.setTextColor(defaultowyColor_timeleft);
        }
        else
        {
            progressBar.setProgress(100);
            textView_timeleft.setText("Przekroczono czas o: ");
            textView_timeleft_value.setTextColor(Color.RED);
        }

        MainActivity.lista.set(position, biezaceZadanie);
        return view;

    }







}
