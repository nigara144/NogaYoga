package com.example.nogayoga;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CalendarFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CalendarFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private CalendarView calendar;
    private TextView date_view;

    public CalendarFragment() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CalendarFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CalendarFragment newInstance(String param1, String param2) {
        CalendarFragment fragment = new CalendarFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
//        calendar = (CalendarView) getView().findViewById(R.id.calendar);
//        date_view = (TextView) getView().findViewById(R.id.date_view);
//
//        calendar
//                .setOnDateChangeListener(
//                        new CalendarView
//                                .OnDateChangeListener() {
//                            @Override
//                            // In this Listener have one method
//                            // and in this method we will
//                            // get the value of DAYS, MONTH, YEARS
//                            public void onSelectedDayChange(
//                                    @NonNull CalendarView view,
//                                    int year,
//                                    int month,
//                                    int dayOfMonth) {
//                                // Store the value of date with
//                                // format in String type Variable
//                                // Add 1 in month because month
//                                // index is start with 0
//                                String Date
//                                        = dayOfMonth + "-"
//                                        + (month + 1) + "-" + year;
//                                // set this date in TextView for Display
//                                date_view.setText(Date);
//                            }
//                        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);
        CalendarView calendarView= (CalendarView) view.findViewById(R.id.calendar);
        TextView textView= (TextView) view.findViewById(R.id.date_view);

//        calendar
//                .setOnDateChangeListener(
//                        new CalendarView
//                                .OnDateChangeListener() {
//                            @Override
//                            // In this Listener have one method
//                            // and in this method we will
//                            // get the value of DAYS, MONTH, YEARS
//                            public void onSelectedDayChange(
//                                    @NonNull CalendarView view,
//                                    int year,
//                                    int month,
//                                    int dayOfMonth) {
//                                // Store the value of date with
//                                // format in String type Variable
//                                // Add 1 in month because month
//                                // index is start with 0
//                                String Date
//                                        = dayOfMonth + "-"
//                                        + (month + 1) + "-" + year;
//                                // set this date in TextView for Display
//                                date_view.setText(Date);
//                            }
//                        });

        return view;
    }
}