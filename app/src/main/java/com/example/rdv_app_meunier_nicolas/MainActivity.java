package com.example.rdv_app_meunier_nicolas;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.ColorRes;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.view.Menu;
import android.view.MenuItem;

import java.sql.SQLException;

public class MainActivity extends AppCompatActivity {
    private DatabaseHelper myHelper;
    ListView lvMoments;
    LinearLayout layout;
    Boolean colori;

    SimpleCursorAdapter adapter;
    public void chargeData() {
        final String[] from = new String[]{DatabaseHelper._ID, DatabaseHelper.TITLE, DatabaseHelper.MDATE, DatabaseHelper.TIME, DatabaseHelper.ADDRESS, DatabaseHelper.PHONE, DatabaseHelper.CONTACT,DatabaseHelper.DONE};
        final int[] to = new int[]{R.id.tvId, R.id.tvTitle, R.id.tvDate, R.id.tvTime, R.id.tvAdresse, R.id.tvPhone, R.id.tvContact, R.id.tvDone};

        Cursor c = myHelper.getAllMoments();
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.moment_item_view, c, from, to, 0);
        adapter.notifyDataSetChanged();
        lvMoments.setAdapter(adapter);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);

        myHelper = new DatabaseHelper(this);
        try {
            myHelper.open();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        Log.d("Test", "chargeData");
        colori=true;
        lvMoments = (ListView) findViewById(R.id.lvMoments);
        lvMoments.setEmptyView(findViewById(R.id.tvEmpty));
        layout = findViewById(R.id.linearlayout);
        chargeData();
        registerForContextMenu(lvMoments);
        lvMoments.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String idItem = ((TextView) view.findViewById(R.id.tvId)).getText().toString();
                String titleItem = ((TextView) view.findViewById(R.id.tvTitle)).getText().toString();
                String timeItem = ((TextView) view.findViewById(R.id.tvTime)).getText().toString();
                String dateItem = ((TextView) view.findViewById(R.id.tvDate)).getText().toString();
                String addressItem = ((TextView) view.findViewById(R.id.tvAdresse)).getText().toString();
                String phoneItem = ((TextView) view.findViewById(R.id.tvPhone)).getText().toString();
                String contactItem = ((TextView) view.findViewById(R.id.tvContact)).getText().toString();
                String doneItem = ((TextView) view.findViewById(R.id.tvDone)).getText().toString();
                Moment pMoment = new Moment(Long.parseLong(idItem), titleItem, dateItem, timeItem, addressItem, phoneItem, contactItem,doneItem);
                Intent intent = new Intent(getApplicationContext(), MomentDetails.class);
                intent.putExtra("SelectedMoment", pMoment);
                intent.putExtra("fromAdd", false);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.moment_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item){
        switch (item.getItemId()) {
            case R.id.new_moment: {
                Intent intent = new Intent(this, MomentDetails.class);
                intent.putExtra("fromAdd", true);
                startActivity(intent);
                return true;
            }
            case R.id.search: {
                layout.setBackgroundColor(0xFF34EBD9);
                if (colori == true){layout.setBackgroundColor(0xFF6200EE);
                    colori=false;
                }
                else{layout.setBackgroundColor(0xFF000000);
                    colori=true; }
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu,menu);
    }
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info= (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();

        if (item.getItemId()==R.id.delete){
            myHelper.delete(info.id);
            chargeData();
            return true;
        }
        return super.onContextItemSelected(item);
    }
}