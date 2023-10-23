/**
 * Created by Pratik Katariya on 04-09-2020
 */
package com.stetig.solitaire.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.stetig.solitaire.R;
import com.stetig.solitaire.activity.MainActivity;
import com.stetig.solitaire.api.Keys;
import com.stetig.solitaire.data.Event;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Pratik Katariya on 04-09-2020
 */
public class CalenderDetailRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {

    Context context;
    String[] time = {"0 AM ",  "12 AM", "1 AM", "2 AM", "3 AM", "4 AM", "5 AM", "6 AM", "7 AM", "8 AM", "9 AM", "10 AM", "11 AM", "12 PM", "1 PM", "2 PM", "3 PM", "4 PM", "5 PM", "6 PM", "7 PM", "8 PM", "9 PM", "10 PM", "11 PM",};
    ArrayList<Event> list;
    Map<String, ArrayList<Event>> eventMap = new HashMap<>();
    Map<Integer, Event> oppIds = new HashMap<>();

    private String dateCurr;
    private String dayCurr;
    ArrayList<Event> eventArrayList;

    public CalenderDetailRecyclerViewAdapter(Context context, ArrayList<Event> list, String dateCurr, String dayCurr) {
        this.context = context;
        this.list = list;

        this.dateCurr = dateCurr;
        this.dayCurr = dayCurr;

        eventArrayList = list;

        if (list != null) {
            for (Event e : list) {
                Date date = new Date(e.getTime());
                String hour = new SimpleDateFormat("h a").format(date);
                Log.e(getClass().getName(), "CalenderDetailRecyclerView: " + hour);

                if (eventMap.containsKey(hour)) {
                    eventMap.get(hour).add(e);
                } else {
                    ArrayList<Event> eventArrayList = new ArrayList<>();
                    eventArrayList.add(e);
                    eventMap.put(hour, eventArrayList);
                }
            }

        }

    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_event_details_activity, parent, false);
        RecyclerViewViewHolder recyclerViewViewHolder = new RecyclerViewViewHolder(view);
        return recyclerViewViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        RecyclerViewViewHolder recyclerViewViewHolder = (RecyclerViewViewHolder) holder;
        recyclerViewViewHolder.addViewToLinear.removeAllViews();
        if (position == 0) {
            recyclerViewViewHolder.linearLayout.setVisibility(View.VISIBLE);
            recyclerViewViewHolder.textTime.setVisibility(View.INVISIBLE);
            recyclerViewViewHolder.textViewWeek.setText(dayCurr + "");
            recyclerViewViewHolder.textDate.setText(dateCurr + "");
        } else {
            recyclerViewViewHolder.addViewToLinear.removeAllViews();
            recyclerViewViewHolder.linearLayout.setVisibility(View.GONE);
            recyclerViewViewHolder.textTime.setVisibility(View.VISIBLE);
            if (eventMap.containsKey(time[position])) {
                ArrayList<Event> list = eventMap.get(time[position]);
                for (Event s : list) {
                    recyclerViewViewHolder.addViewToLinear.addView(createTextLayout(position, s));
                }
            }

            recyclerViewViewHolder.textTime.setText(time[position]);
        }
    }

    public TextView createTextLayout(int id, Event ev) {
        TextView textView = new TextView(context);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(10, 0, 0, 0);
        textView.setLayoutParams(layoutParams);
        textView.setPadding(4, 4, 4, 4);
        textView.setText(ev.getDesc());
        textView.setId(id);
        oppIds.put(id, ev);
        textView.setOnClickListener(this);
        Typeface typeface = Typeface.createFromAsset(context.getAssets(), "fonts/myriad_pro_regular.ttf");
        textView.setTypeface(typeface);
        textView.setTextSize(10);

        if (ev.getDesc().toLowerCase().contains("follow")) {
            textView.setBackgroundColor(context.getResources().getColor(R.color.fu));
        } else if (ev.getDesc().toLowerCase().contains("site")) {
            textView.setBackgroundColor(context.getResources().getColor(R.color.sv));
        } else if (ev.getDesc().toLowerCase().contains("face")) {
            textView.setBackgroundColor(context.getResources().getColor(R.color.f2f));
        }
        textView.setTextColor(Color.WHITE);
        return textView;
    }

    @Override
    public int getItemCount() {
        return time.length;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() != -1) {
            try {
                Log.e(getClass().getName(), "onClick: " + oppIds.get(v.getId()).getOpportunityId());

                if (oppIds.get(v.getId()).getOpportunityId() == null) {
                    Toast.makeText(context, "no opportunity id present", Toast.LENGTH_SHORT).show();
                    return;
                }


                if (context instanceof MainActivity) {
                    Bundle bundle1 = new Bundle();
                    bundle1.putString(Keys.OPP_ID, oppIds.get(v.getId()).getOpportunityId());
                    ((MainActivity) context).getNavHostFragment().getNavController().navigate(R.id.action_calendarDetailFragment_to_opportunityDetailFragment, bundle1);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public static class RecyclerViewViewHolder extends RecyclerView.ViewHolder {

        TextView textTime;
        TextView textViewZero;
        LinearLayout linearLayout;
        LinearLayout addViewToLinear;

        TextView textViewWeek;
        TextView textDate;

        public RecyclerViewViewHolder(@NonNull View itemView) {
            super(itemView);
            linearLayout = itemView.findViewById(R.id.linearLayout);
            textTime = itemView.findViewById(R.id.textViewTime);
            addViewToLinear = itemView.findViewById(R.id.addViewToLinear);

            textViewWeek = itemView.findViewById(R.id.textViewWeekTextTop);
            textDate = itemView.findViewById(R.id.textViewDateTop);
        }
    }
}
