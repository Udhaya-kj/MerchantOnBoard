package life.corals.onboarding.Adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

import life.corals.onboarding.Activity.Intro_Business_Activity;
import life.corals.onboarding.R;

public class MyListAdapter_Competitors extends BaseAdapter {

    ArrayList<Boolean> positionArray;
    Context context;
    LayoutInflater inflter;
    ArrayList<String> competitors_list, id_list;
    boolean isChecked = false;
    ArrayList<String> arrayList = new ArrayList<>();

    public MyListAdapter_Competitors(@NonNull Context context, ArrayList<String> competitors_list, ArrayList<String> id_list) {
        inflter = (LayoutInflater.from(context));
        this.context = context;
        this.competitors_list = competitors_list;
        this.id_list = id_list;


        positionArray = new ArrayList<Boolean>();
        Log.d("biz_type_list--->", "" + Intro_Business_Activity.biz_type_list);
        for (int i = 0; i < Intro_Business_Activity.biz_type_list.size(); i++) {
            //positionArray.add(false);

            if (Intro_Business_Activity.biz_id_list.get(i).equals("00")) {
                positionArray.add(false);
            } else {
                positionArray.add(true);
            }
        }
        Log.d("position--->", "" + positionArray);
    }

    @Override
    public int getCount() {

        return competitors_list.size();
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
        View rowView = inflter.inflate(R.layout.custom_layout_competitors, null);

        LinearLayout linearLayout_comp = (LinearLayout) rowView.findViewById(R.id.layout_competitors);
        final CheckBox checkBox = (CheckBox) rowView.findViewById(R.id.checkbox_select_competitors);
        TextView tl = (TextView) rowView.findViewById(R.id.text_competitors);
        tl.setText(competitors_list.get(position));

        checkBox.setChecked(positionArray.get(position));

        linearLayout_comp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (checkBox.isChecked()) {

                    Intro_Business_Activity.biz_comp_list_data.remove(competitors_list.get(position));
                    Intro_Business_Activity.biz_comp_list_id.remove(id_list.get(position));
                    positionArray.set(position, false);
                    Intro_Business_Activity.biz_id_list.set(position, "00");
                    checkBox.setChecked(false);

                } else {

                    Intro_Business_Activity.biz_comp_list_data.add(competitors_list.get(position));
                    Intro_Business_Activity.biz_comp_list_id.add(id_list.get(position));

                    checkBox.setChecked(true);
                    positionArray.set(position, true);
                    Intro_Business_Activity.biz_id_list.set(position, String.valueOf(position));
                }
                Log.d("ListSize===>", "" + Intro_Business_Activity.biz_id_list);

            }
        });

        return rowView;
    }


}
