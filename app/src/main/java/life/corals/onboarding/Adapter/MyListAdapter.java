package life.corals.onboarding.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import life.corals.onboarding.Activity.Redeem_Points_Activity;
import life.corals.onboarding.R;

public class MyListAdapter extends BaseAdapter {

    Context context;
    LayoutInflater inflter;
    ArrayList<String> title_list, desc_list, bonus_list, a_date_list, a_time_list, e_date_list, e_time_list, act_dys_list;
    //ArrayList<String> sharable_list;

    public MyListAdapter(@NonNull Context context, ArrayList<String> title_list, ArrayList<String> desc_list, ArrayList<String> bonus_list,
                         ArrayList<String> a_date_list, ArrayList<String> a_time_list, ArrayList<String> e_date_list, ArrayList<String> e_time_list, ArrayList<String> act_dys_list) {
        inflter = (LayoutInflater.from(context));
        this.context = context;
        this.title_list = title_list;
        this.desc_list = desc_list;
        this.bonus_list = bonus_list;

        this.a_date_list = a_date_list;
        this.a_time_list = a_time_list;
        this.e_date_list = e_date_list;
        this.e_time_list = e_time_list;
        this.act_dys_list = act_dys_list;
       // this.sharable_list = sharable_list;
    }

    @Override
    public int getCount() {
        return title_list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View rowView = inflter.inflate(R.layout.redeem_list_layout1, null);

        TextView tl = (TextView) rowView.findViewById(R.id.text_title);
        TextView bns = (TextView) rowView.findViewById(R.id.text_bonus);

        TextView from_dt = (TextView) rowView.findViewById(R.id.from_date);
        TextView to_dt = (TextView) rowView.findViewById(R.id.to_date);

        TextView s_time = (TextView) rowView.findViewById(R.id.start_time);
        TextView e_time = (TextView) rowView.findViewById(R.id.end_time);

        TextView textView_sunday = (TextView) rowView.findViewById(R.id.sunday);
        TextView textView_monday = (TextView) rowView.findViewById(R.id.monday);
        TextView textView_tuesday = (TextView) rowView.findViewById(R.id.tuesday);
        TextView textView_wednesday = (TextView) rowView.findViewById(R.id.wednesday);
        TextView textView_thursday = (TextView) rowView.findViewById(R.id.thursday);
        TextView textView_friday = (TextView) rowView.findViewById(R.id.friday);
        TextView textView_saturday = (TextView) rowView.findViewById(R.id.saturday);
        //final int count = position + 1;
        //tl_count.setText(String.valueOf(count));


        String startDate=null,endDate=null;
        DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat d= new SimpleDateFormat("yyyy-MMM-dd");
        try {
            Date convertedDate_S = inputFormat.parse(a_date_list.get(position));
            Date convertedDate_E = inputFormat.parse(e_date_list.get(position));
            startDate = d.format(convertedDate_S);
            endDate = d.format(convertedDate_E);

        }catch (ParseException e)
        {
            Log.d("Date--->",""+e.getMessage());
        }

        tl.setText(title_list.get(position));
        bns.setText(bonus_list.get(position));
        from_dt.setText(startDate);
        to_dt.setText(endDate);
        s_time.setText(a_time_list.get(position));
        e_time.setText(e_time_list.get(position));

        String getActDays=act_dys_list.get(position);
        Log.d("ActDays===>",""+getActDays);
        if(!TextUtils.isEmpty(getActDays)) {
            char get1 = getActDays.charAt(0);
            char get2 = getActDays.charAt(1);
            char get3 = getActDays.charAt(2);
            char get4 = getActDays.charAt(3);
            char get5 = getActDays.charAt(4);
            char get6 = getActDays.charAt(5);
            char get7 = getActDays.charAt(6);
            Log.d("ActDays===>",""+get1+","+get2 +","+get3+","+get4+","+get5+","+get6+","+get7);
            //Sunday
            if (String.valueOf(get1).equals("y")){
                textView_sunday.setBackground(context.getResources().getDrawable(R.drawable.circle_bg_green));
            }
            else {
                textView_sunday.setBackground(context.getResources().getDrawable(R.drawable.circle_bg_red));
            }


            //Monday
            if (String.valueOf(get2).equals("y")){
                textView_monday.setBackground(context.getResources().getDrawable(R.drawable.circle_bg_green));
            }
            else {
                textView_monday.setBackground(context.getResources().getDrawable(R.drawable.circle_bg_red));
            }

            //Tuesday
            if (String.valueOf(get3).equals("y")){
                textView_tuesday.setBackground(context.getResources().getDrawable(R.drawable.circle_bg_green));
            }
            else {
                textView_tuesday.setBackground(context.getResources().getDrawable(R.drawable.circle_bg_red));
            }

            //Wednesday
            if (String.valueOf(get4).equals("y")){
                textView_wednesday.setBackground(context.getResources().getDrawable(R.drawable.circle_bg_green));
            }
            else {
                textView_wednesday.setBackground(context.getResources().getDrawable(R.drawable.circle_bg_red));
            }

            //Thursday
            if (String.valueOf(get5).equals("y")){
                textView_thursday.setBackground(context.getResources().getDrawable(R.drawable.circle_bg_green));
            }
            else {
                textView_thursday.setBackground(context.getResources().getDrawable(R.drawable.circle_bg_red));
            }

            //Friday
            if (String.valueOf(get6).equals("y")){
                textView_friday.setBackground(context.getResources().getDrawable(R.drawable.circle_bg_green));
            }
            else {
                textView_friday.setBackground(context.getResources().getDrawable(R.drawable.circle_bg_red));
            }

            //Saturday
            if (String.valueOf(get7).equals("y")){
                textView_saturday.setBackground(context.getResources().getDrawable(R.drawable.circle_bg_green));
            }
            else {
                textView_saturday.setBackground(context.getResources().getDrawable(R.drawable.circle_bg_red));
            }
        }


        return rowView;
    }
}
