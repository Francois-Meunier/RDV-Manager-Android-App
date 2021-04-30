package com.example.rdv_app_meunier_nicolas;

import androidx.appcompat.app.AppCompatActivity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.sql.SQLException;
import java.util.Calendar;


public class MomentDetails extends AppCompatActivity{
    EditText etTitle,etDate;
    TextView tvId, tvDone;
    int day,month,year,minute,hour;
    Button btnPickDate,btnPickTime,btnCall, btnDone;
    EditText etTime;
    EditText etAdresse, etPhone,etContact;
    DatePickerDialog.OnDateSetListener onDate;
    TimePickerDialog.OnTimeSetListener onTime;
    DatabaseHelper myHelper;
    boolean fromAdd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moment_details);



        etTitle = (EditText) findViewById(R.id.etTitle);
        etDate = (EditText) findViewById(R.id.etDate);
        etTime = (EditText) findViewById(R.id.etTime);
        tvId = (TextView) findViewById(R.id.tvId);
        tvDone = (TextView) findViewById(R.id.tvDone);
        myHelper = new DatabaseHelper(this);
        try {
            myHelper.open();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        etContact = (EditText) findViewById(R.id.etContact);
        etAdresse = (EditText) findViewById(R.id.etAdresse);
        etPhone = findViewById(R.id.etPhone);
        btnCall = findViewById(R.id.btnCall);
        btnPickDate=(Button)findViewById(R.id.btnPickDate);
        btnDone=(Button)findViewById(R.id.btnDone);

        btnPickTime=(Button)findViewById(R.id.btnPickTime);

        Intent intent = getIntent();
        fromAdd= intent.getBooleanExtra("fromAdd",false);
        if(!fromAdd){
            Bundle b= intent.getExtras();
            Moment selectedMoment= b.getParcelable("SelectedMoment");
            tvId.setText(String.valueOf(selectedMoment.getId()));
            etTitle.setText(selectedMoment.getTitle());
            etDate.setText(selectedMoment.getDate());
            etTime.setText(selectedMoment.getTime());
            etAdresse.setText(selectedMoment.getAdress());
            etPhone.setText(selectedMoment.getPhone());
            etContact.setText(selectedMoment.getContact());
            tvDone.setText(selectedMoment.getDone());
        }



        btnCall.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                String phone = "tel:" + etPhone.getText().toString();
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse(phone));
                startActivity(intent);
            }
        });
        onDate = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                year = year;
                month = month;
                day = dayOfMonth;
                etDate.setText(new StringBuilder().append(month + 1).append("-").append(day).append("-").append(year).append(" "));

            }
        };
        onTime = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int minute, int hour) {
                hour = hour;
                minute = minute;
                etTime.setText(new StringBuilder().append(minute).append(":").append(hour).append(" "));

            }
        };
    }
// pour changer le textView pour Fait/a faire
    public void onDone(View v) {
        String done = tvDone.getText().toString();
        if(done.equals("TO DO")) {tvDone.setText(new String("DONE"));}
        if(done.equals("DONE")) {tvDone.setText(new String("TO DO"));}
        if(done.equals("A FAIRE")) {tvDone.setText(new String("FAIT"));}
        if(done.equals("FAIT")) {tvDone.setText(new String("A FAIRE"));}
    }

//Pour appeler le numéro
    public void onClick(View v){

        String phone = "tel:" + etPhone.getText().toString();
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse(phone));
        startActivity(intent);
    }
    //Pour lancer la map google map
    public void launchMaps(View view) {
        String map = "http://maps.google.co.in/maps?q=" + etAdresse.getText() ;
        Uri gmmIntentUri = Uri.parse(map);
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        String adresse2 = etAdresse.getText().toString();
        if (adresse2.isEmpty()== false) {
            startActivity(mapIntent);
        }
        else { Toast.makeText(getApplicationContext(),"Merci d'écrire une adresse!",Toast.LENGTH_SHORT).show();}
    }

    public void pickDate(View v){
        showDatePicker();
    }

    public void pickTime(View v){
        showTimePicker();
    }
    // pour activer le fragment pour l'heure
    private void showTimePicker() {

        TimePickerFragment time= new TimePickerFragment();

        final Calendar c = Calendar.getInstance();
        minute = c.get(Calendar.MINUTE);
        hour = c.get(Calendar.HOUR);

        Bundle args = new Bundle();
        args.putInt("minute",minute);
        args.putInt("hour",hour);

        time.setArguments(args);
        time.setCallBack(onTime);
        time.show(getSupportFragmentManager(),"Time Picker");
    }
// pour activer le fragment pour la Date
    private void showDatePicker() {

        DatePickerFragment date= new DatePickerFragment();

        final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);

        Bundle args = new Bundle();
        args.putInt("year",year);
        args.putInt("month",month);
        args.putInt("day",day);

        date.setArguments(args);
        date.setCallBack(onDate);
        date.show(getSupportFragmentManager(),"Date Picker");
    }
    //Pour le bouton Retour
    public void onCancelClick(View v){
//Intent intent = new Intent(this,MainActivity.class);
        // startActivity(intent);
        finish();
    }
//Sauvegarder les éléments du rendez-vous
    public void saveMoment(View view) {
        String title= etTitle.getText().toString();
        String date=etDate.getText().toString();
        String time = etTime.getText().toString();
        String adress = etAdresse.getText().toString();
        String phone = etPhone.getText().toString();
        String contact = etContact.getText().toString();
        String done = tvDone.getText().toString();
        if(fromAdd) {
            Moment moment = new Moment(title,date,time,adress,phone,contact,done);
            myHelper.add(moment);

            Intent main = new Intent(this,MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(main);
        }
        else {
            Long id = Long.parseLong(tvId.getText().toString());
            Moment moment = new Moment(id,title,date,time,adress,phone,contact,done);
            int n = myHelper.update(moment);

            Intent main = new Intent(this,MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(main);
        }

    }
//Methode pour partager
    public void shareMethod(View view) {
        String title= etTitle.getText().toString();
        String date=etDate.getText().toString();
        String time = etTime.getText().toString();
        String adress = etAdresse.getText().toString();
        String phone = etPhone.getText().toString();
        String contact = etContact.getText().toString();
        String done = tvDone.getText().toString();

        String shareContent = done + "\n" + "RDV : " + title + '\n' + "Date : " + date + " at " + time + '\n' + "Address : " + adress + '\n' + contact + '\n' + phone;

        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, shareContent);
        sendIntent.setType("text/plain");
        //startActivity(sendIntent);
        startActivity(Intent.createChooser(sendIntent, "Share App"));
    }
}
