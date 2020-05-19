package life.corals.onboarding.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import life.corals.onboarding.Activity.Intro_Business_Activity;
import life.corals.onboarding.R;

public class MyListAdapter_Recyclerview extends RecyclerView.Adapter<MyListAdapter_Recyclerview.ViewHolder> {

    ArrayList<String> competitors_list;

    // RecyclerView recyclerView;
    public MyListAdapter_Recyclerview(ArrayList<String> competitors_list) {
        this.competitors_list = competitors_list;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.competitors_list_recyclerview, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.textView.setText(competitors_list.get(position));
    }


    @Override
    public int getItemCount() {
        return competitors_list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView textView;
        public ViewHolder(View itemView) {
            super(itemView);
            this.textView = (TextView) itemView.findViewById(R.id.text_competitors_recyclerview);
        }
    }
}
