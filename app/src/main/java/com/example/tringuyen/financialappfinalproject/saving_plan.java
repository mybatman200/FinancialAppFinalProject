package com.example.tringuyen.financialappfinalproject;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class saving_plan extends AppCompatActivity {

    private SQLiteDatabase db = null;
    private dataBaseHelper dbHelper = null;
    SimpleCursorAdapter myAdapter;
    ListView mlist;
    EditText elem;
    Cursor mCursor;
    String longClickString;
    android.app.AlertDialog actions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saving_plan);

        mlist = findViewById(R.id.listView);
        dbHelper = new dataBaseHelper(this);

        mlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(getApplicationContext(), "Touched " + ((TextView) view).getText(), Toast.LENGTH_SHORT).show();
                Object temp = myAdapter.getItem(position);
                String tempView = ((TextView) view).getText().toString();

                mCursor.moveToPosition(position);

                String tempPos = Integer.toString(position);
                //get values from database
                String savingID = mCursor.getString(mCursor.getColumnIndex(MainActivity._ID));
                String savingEventName = mCursor.getString( mCursor.getColumnIndex(MainActivity.SAVING_NAME));
                String savingAmount =  mCursor.getString( mCursor.getColumnIndex(MainActivity.SAVING_AMOUNT));
                String savingSoFar =  mCursor.getString( mCursor.getColumnIndex(MainActivity.SAVING_SO_FAR));
                String savingDate =  mCursor.getString( mCursor.getColumnIndex(MainActivity.SAVING_DATE));

            }
        });

        mlist.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(getApplicationContext(), "Long touched " + id, Toast.LENGTH_SHORT).show();

                String tempString = ((TextView) view).getText().toString();
                longClickString = Long.toString(id);

                actions.show();

                return true;
            }}
        );

        DialogInterface.OnClickListener actionListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        db.delete(dbHelper.NAME_SAVING, "_id=?", new String[]{longClickString});
                        Toast.makeText(getApplicationContext(), "Delete" + longClickString, Toast.LENGTH_LONG).show();

                        db = dbHelper.getWritableDatabase();
                        mCursor = db.query(dbHelper.NAME_SAVING, MainActivity.all_columns_saving, null, null, null, null, null);
                        myAdapter.swapCursor(mCursor);
                        mlist.setAdapter(myAdapter);
                        break;

                    default:
                        break;
                }
            }
        };

        android.app.AlertDialog.Builder builder = new
                android.app.AlertDialog.Builder(this);
        builder.setTitle("Are you sure you want to delete this item?");
        String[] options = {"Delete"};
        builder.setItems(options, actionListener);
        builder.setNegativeButton("Cancel", null);
        actions = builder.create();

    }


    private class LoadNewLists extends AsyncTask<Void, Void, Cursor> {
        @Override
        protected Cursor doInBackground(Void... params){
            db = dbHelper.getWritableDatabase();
            mCursor = db.query(dbHelper.NAME_SAVING, MainActivity.all_columns_saving, null, null, null, null,
                    null);
            return mCursor;
        }

        @Override
        protected void onPostExecute(Cursor result){
            super.onPostExecute(result);
            myAdapter = new SimpleCursorAdapter(saving_plan.this,
                    android.R.layout.simple_list_item_1,
                    mCursor,
                    new String[] {MainActivity.SAVING_NAME, MainActivity.SAVING_AMOUNT, MainActivity.SAVING_SO_FAR, MainActivity.SAVING_DATE },
                    new int[] { android.R.id.text1});

            mlist.setAdapter(myAdapter);
        }

    }

    public void onResume(){
        super.onResume();
        new LoadNewLists().execute();
        /*db = dbHelper.getWritableDatabase();
        mCursor = db.query(dbHelper.NAME_SAVING, MainActivity.all_columns_saving, null, null, null, null,
                null);
        myAdapter = new SimpleCursorAdapter(this,
                android.R.layout.simple_list_item_1,
                mCursor,
                new String[] {MainActivity.EVENT_NAME, MainActivity.SAVING_AMOUNT, MainActivity.SAVING_SO_FAR, MainActivity.SAVING_DATE },
                new int[] { android.R.id.text1});

        mlist.setAdapter(myAdapter);*/

    }

    public void onPause() {
        super.onPause();
        if (db != null) db.close();
        mCursor.close();
    }

    public void addSaving(View v){
        Intent intent = new Intent(this, saving_plan_2.class);
        startActivity(intent);
    }


}
