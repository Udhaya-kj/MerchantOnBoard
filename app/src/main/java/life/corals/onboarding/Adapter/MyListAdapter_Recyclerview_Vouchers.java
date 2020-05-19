package life.corals.onboarding.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import life.corals.onboarding.R;

public class MyListAdapter_Recyclerview_Vouchers extends RecyclerView.Adapter<MyListAdapter_Recyclerview_Vouchers.ViewHolder> {

    Context context;
    ArrayList<String> title_list, desc_list, bonus_list, a_date_list, a_time_list, e_date_list, e_time_list, act_dys_list;

    public MyListAdapter_Recyclerview_Vouchers(Context context, ArrayList<String> title_list, ArrayList<String> desc_list, ArrayList<String> bonus_list, ArrayList<String> a_date_list, ArrayList<String> a_time_list, ArrayList<String> e_date_list, ArrayList<String> e_time_list, ArrayList<String> act_dys_list) {
        this.context = context;
        this.title_list = title_list;
        this.desc_list = desc_list;
        this.bonus_list = bonus_list;

        this.a_date_list = a_date_list;
        this.a_time_list = a_time_list;
        this.e_date_list = e_date_list;
        this.e_time_list = e_time_list;
        this.act_dys_list = act_dys_list;
    }

    @Override
    public MyListAdapter_Recyclerview_Vouchers.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.redeem_voucher, parent, false);
        MyListAdapter_Recyclerview_Vouchers.ViewHolder viewHolder = new MyListAdapter_Recyclerview_Vouchers.ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyListAdapter_Recyclerview_Vouchers.ViewHolder holder, int position) {

        String startDate = null, endDate = null;
        DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat d = new SimpleDateFormat("yyyy-MMM-dd");
        try {
            Date convertedDate_S = inputFormat.parse(a_date_list.get(position));
            Date convertedDate_E = inputFormat.parse(e_date_list.get(position));
            startDate = d.format(convertedDate_S);
            endDate = d.format(convertedDate_E);

        } catch (ParseException e) {
            Log.d("Date--->", "" + e.getMessage());
        }

        holder.tl.setText(title_list.get(position));
        holder.bns.setText(bonus_list.get(position));
        holder.from_dt.setText(startDate);
        holder.to_dt.setText(endDate);
        holder.s_time.setText(a_time_list.get(position));
        holder.e_time.setText(e_time_list.get(position));

        String getActDays = act_dys_list.get(position);
        Log.d("ActDays===>", "" + getActDays);
        if (!TextUtils.isEmpty(getActDays)) {
            char get1 = getActDays.charAt(0);
            char get2 = getActDays.charAt(1);
            char get3 = getActDays.charAt(2);
            char get4 = getActDays.charAt(3);
            char get5 = getActDays.charAt(4);
            char get6 = getActDays.charAt(5);
            char get7 = getActDays.charAt(6);
            Log.d("ActDays===>", "" + get1 + "," + get2 + "," + get3 + "," + get4 + "," + get5 + "," + get6 + "," + get7);
            //Sunday
            if (String.valueOf(get1).equals("y")) {
                holder.textView_sunday.setBackground(context.getResources().getDrawable(R.drawable.circle_bg_green));
            } else {
                holder.textView_sunday.setBackground(context.getResources().getDrawable(R.drawable.circle_bg_red));
            }


            //Monday
            if (String.valueOf(get2).equals("y")) {
                holder.textView_monday.setBackground(context.getResources().getDrawable(R.drawable.circle_bg_green));
            } else {
                holder.textView_monday.setBackground(context.getResources().getDrawable(R.drawable.circle_bg_red));
            }

            //Tuesday
            if (String.valueOf(get3).equals("y")) {
                holder.textView_tuesday.setBackground(context.getResources().getDrawable(R.drawable.circle_bg_green));
            } else {
                holder.textView_tuesday.setBackground(context.getResources().getDrawable(R.drawable.circle_bg_red));
            }

            //Wednesday
            if (String.valueOf(get4).equals("y")) {
                holder.textView_wednesday.setBackground(context.getResources().getDrawable(R.drawable.circle_bg_green));
            } else {
                holder.textView_wednesday.setBackground(context.getResources().getDrawable(R.drawable.circle_bg_red));
            }

            //Thursday
            if (String.valueOf(get5).equals("y")) {
                holder.textView_thursday.setBackground(context.getResources().getDrawable(R.drawable.circle_bg_green));
            } else {
                holder.textView_thursday.setBackground(context.getResources().getDrawable(R.drawable.circle_bg_red));
            }

            //Friday
            if (String.valueOf(get6).equals("y")) {
                holder.textView_friday.setBackground(context.getResources().getDrawable(R.drawable.circle_bg_green));
            } else {
                holder.textView_friday.setBackground(context.getResources().getDrawable(R.drawable.circle_bg_red));
            }

            //Saturday
            if (String.valueOf(get7).equals("y")) {
                holder.textView_saturday.setBackground(context.getResources().getDrawable(R.drawable.circle_bg_green));
            } else {
                holder.textView_saturday.setBackground(context.getResources().getDrawable(R.drawable.circle_bg_red));
            }
        }
    }


    @Override
    public int getItemCount() {
        return title_list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public LinearLayout linearLayout;
        public TextView tl, bns, from_dt, to_dt, s_time, e_time, textView_sunday, textView_monday, textView_tuesday, textView_wednesday, textView_thursday, textView_friday, textView_saturday;

        public ViewHolder(View rowView) {
            super(rowView);

            linearLayout = (LinearLayout) rowView.findViewById(R.id.layout_voucher);
            tl = (TextView) rowView.findViewById(R.id.text_title);
            bns = (TextView) rowView.findViewById(R.id.text_bonus);
            from_dt = (TextView) rowView.findViewById(R.id.from_date);
            to_dt = (TextView) rowView.findViewById(R.id.to_date);
            s_time = (TextView) rowView.findViewById(R.id.start_time);
            e_time = (TextView) rowView.findViewById(R.id.end_time);

            textView_sunday = (TextView) rowView.findViewById(R.id.sunday);
            textView_monday = (TextView) rowView.findViewById(R.id.monday);
            textView_tuesday = (TextView) rowView.findViewById(R.id.tuesday);
            textView_wednesday = (TextView) rowView.findViewById(R.id.wednesday);
            textView_thursday = (TextView) rowView.findViewById(R.id.thursday);
            textView_friday = (TextView) rowView.findViewById(R.id.friday);
            textView_saturday = (TextView) rowView.findViewById(R.id.saturday);


        }
    }
}

