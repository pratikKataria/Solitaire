/**
 * Created by Pratik Katariya on 03-09-2020
 */
package com.stetig.solitaire.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.stetig.callsync.base.BaseFragment;
import com.stetig.solitaire.R;
import com.stetig.solitaire.activity.MainActivity;
import com.stetig.solitaire.data.Event;
import com.stetig.solitaire.utils.DateUtils;
import com.google.gson.Gson;
//import com.kizitonwose.calendarview.CalendarView;
//import com.kizitonwose.calendarview.model.CalendarDay;
//import com.kizitonwose.calendarview.model.CalendarMonth;
//import com.kizitonwose.calendarview.model.DayOwner;
//import com.kizitonwose.calendarview.ui.DayBinder;
//import com.kizitonwose.calendarview.ui.MonthHeaderFooterBinder;
//import com.kizitonwose.calendarview.ui.ViewContainer;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.YearMonth;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


public class CalenderFragment extends BaseFragment {

    ArrayList<Event> eventArrayList = new ArrayList<>();
    String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
    TextView monthTextView;

    public static final String F2F = "f2f";
    public static final String SV = "sv";
    public static final String FU = "fu";

    Map<String, ArrayList<Event>> eventMap = new HashMap<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);
        monthTextView = view.findViewById(R.id.textViewMonth);

//        eventArrayList = dbHelper.getAllEvents();

        SharedPreferences sharedPreferences = getMActivity().getSharedPreferences("calendarEvents", Context.MODE_PRIVATE);
        Map<String, String> localEvent = (Map<String, String>) sharedPreferences.getAll();


        eventArrayList.clear();
        eventMap.clear();
        for (String key : localEvent.keySet()) {
            Event event = new Gson().fromJson(localEvent.get(key), Event.class);
            eventArrayList.add(event);
        }

        for (Event e : eventArrayList) {
            Date date = new Date(e.getTime());
            String event = new SimpleDateFormat("dd-MM").format(date);
            if (eventMap.containsKey(event)) {
                eventMap.get(event).add(e);
                Log.e(getClass().getName(), "onCreateView: " + event);
            } else {
                ArrayList<Event> eventArrayList = new ArrayList<Event>();
                eventArrayList.add(e);
                Log.e(getClass().getName(), "onCreateView: " + event);
                eventMap.put(event, eventArrayList);
            }
        }

        Log.e(getClass().getName(), "onCreateView: " + eventMap);

//        CalendarView calendarView = view.findViewById(R.id.exFiveCalendar);
//        calendarView.setDayBinder(binder);
//        YearMonth yearMonth = YearMonth.now();
//        YearMonth firstMonth = yearMonth.minusMonths(10);
//        YearMonth lastMonth = yearMonth.plusMonths(10);
//        DayOfWeek week = WeekFields.of(Locale.getDefault()).getFirstDayOfWeek();
//        calendarView.setup(firstMonth, lastMonth, week);
//        calendarView.scrollToMonth(yearMonth);
//        calendarView.setMonthHeaderBinder(montViewContainerMonthHeaderFooterBinder);
//        calendarView.setMonthScrollListener(calendarMonth -> {
//            monthTextView.setText(months[calendarMonth.getMonth() - 1]);
//            return null;
//        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void initView(@Nullable View rootView) {

    }

//    class DayViewContainer extends ViewContainer {
//        View view;
////        CalendarDay day;
//
////        public DayViewContainer(@NotNull View view) {
////            super(view);
////            this.view = view;
////            view.setOnClickListener(n -> {
////                if (day.getOwner() == DayOwner.THIS_MONTH) {
////
////                    Date date = DateUtils.getDate(day.getDate());
////                    String onDate = new SimpleDateFormat("dd-MM").format(date);
////
////                    if (getContext() instanceof MainActivity) {
////                        Bundle intent = new Bundle();
////                        intent.putParcelableArrayList("key", eventMap.get(onDate));
////                        intent.putString("month", monthTextView.getText().toString());
////                        intent.putString("date", date.getDate() + "");
////                        intent.putString("week", day.getDate().getDayOfWeek().toString().substring(0, 3));
////
////                        ((MainActivity) getContext()).getNavHostFragment().getNavController().navigate(R.id.action_projectCalendar_to_calendarDetailFragment, intent);
////                    }
////                }
////            });
////        }
//
//    }

//    DayBinder<DayViewContainer> binder = new DayBinder<DayViewContainer>() {
//        @NotNull
//        @Override
//        public DayViewContainer create(@NotNull View view) {
//            return new DayViewContainer(view);
//        }
//
//        @Override
//        public void bind(@NotNull DayViewContainer container, @NotNull CalendarDay day) {
//            container.day = day;
//            Date date = DateUtils.getDate(day.getDate());
//            TextView textView = container.view.findViewById(R.id.exFiveDayText);
//            TextView textViewssv = container.view.findViewById(R.id.label_sv);
//            TextView textViewfu = container.view.findViewById(R.id.label_fu);
//            TextView textViewf2f = container.view.findViewById(R.id.label_f2f);
//
//            LinearLayout linearLayout = container.view.findViewById(R.id.exFiveDayLayout);
//            textView.setText(date.getDate() + " ");
//
//
//            String onDate = new SimpleDateFormat("dd-MM").format(date);
////            Log.e(getClass().getName(), "bind: " + onDate );
//
//            int countSV = 0;
//            int countF2F = 0;
//            int countFU = 0;
//            if (day.getOwner() == DayOwner.THIS_MONTH) {
//                if (eventMap.containsKey(onDate)) {
//                    ArrayList<Event> events = eventMap.get(onDate);
//                    for (Event ev : events) {
//                        if (ev.getDesc().toLowerCase().contains("follow")) {
//                            countFU++;
//                            textViewfu.setVisibility(View.VISIBLE);
//                            textViewfu.setText(countFU + " FU");
//                        } else if (ev.getDesc().toLowerCase().contains("face")) {
//                            countF2F++;
//                            textViewf2f.setVisibility(View.VISIBLE);
//                            textViewf2f.setText(countF2F + " F2F");
//                        } else if (ev.getDesc().toLowerCase().contains("site")) {
//                            countSV++;
//                            textViewssv.setVisibility(View.VISIBLE);
//                            textViewssv.setText(countSV + " SV");
//                        }
//                    }
//                }
//            }
//
//            if (!(day.getOwner() == DayOwner.THIS_MONTH)) {
//                textView.setTextColor(getResources().getColor(R.color.grey));
//            }
//        }
//    };

//    static class MontViewContainer extends ViewContainer {
//
//        public MontViewContainer(@NotNull View view) {
//
//            super(view);
//        }
//    }

//    MonthHeaderFooterBinder<MontViewContainer> montViewContainerMonthHeaderFooterBinder = new MonthHeaderFooterBinder<MontViewContainer>() {
//        @Override
//        public void bind(@NotNull MontViewContainer container, @NotNull CalendarMonth month) {
//
//        }
//
//        @NotNull
//        @Override
//        public MontViewContainer create(@NotNull View view) {
//            return new MontViewContainer(view);
//        }
//    };

}