package com.example.amit.practiceinput;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.provider.CalendarContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setPlanetSpinner();
        setSwitchButton();
    }
    public void setPlanetSpinner(){
        Spinner spinner=findViewById(R.id.planet_spinner);
        ArrayAdapter<CharSequence> adapter= ArrayAdapter.createFromResource(this,R.array.planet_array,
                //android.R.layout.simple_spinner_item);
                R.layout.custom_spinner_item);
        // adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(R.layout.custom_spinner_layout);
        spinner.setAdapter(adapter);
    }
    public void showToast(String message){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();

    }
    public void setSwitchButton(){
        Switch sb=findViewById(R.id.switch_button);
        sb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    showToast("on");
                }else{
                    showToast("off");
                }
            }
        });
    }

    public void showTimePicker(View view){
       final Calendar c=Calendar.getInstance();
       int h=c.get(Calendar.HOUR_OF_DAY);
       int m=c.get(Calendar.MINUTE);
       TimePickerDialog tpd=new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    Button b=findViewById(R.id.time_picker);
                    Calendar c=Calendar.getInstance();
                    c.set(Calendar.HOUR_OF_DAY,hourOfDay);
                    c.set(Calendar.MINUTE,minute);
                    c.set(Calendar.SECOND,0);
                    Date date=c.getTime();
                    String formattedTime=java.text.DateFormat.getTimeInstance().format(date);
                    b.setText(formattedTime);
                    b.setBackgroundColor(0xfff);
                    b.setTextSize(20);
                }
            },h,m, DateFormat.is24HourFormat(this));
        tpd.show();
    }

    public void showDatePicker(View view){
       final Calendar c=Calendar.getInstance();
       int dd=c.get(Calendar.DAY_OF_MONTH);
       int mm=c.get(Calendar.MONTH);
       int yy=c.get(Calendar.YEAR);
       DatePickerDialog dpd=new DatePickerDialog(this,new DatePickerDialog.OnDateSetListener(){

           @Override
           public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
               Button b=findViewById(R.id.date_picker);
               Calendar c=Calendar.getInstance();
               c.set(year,month,dayOfMonth);
               Date date=c.getTime();
               String dateStr= java.text.DateFormat.getDateInstance().format(date);
               b.setText(dateStr);
               b.setBackgroundColor(0xfff);
               b.setTextSize(20);
           }
       },yy,mm,dd);
       dpd.show();
    }
}
