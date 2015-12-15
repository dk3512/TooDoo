package com.example.daniel.toodoo;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class TooDooMain extends AppCompatActivity {
    static final int ADD_VIEW = 0;
    private ArrayList<String> textList = new ArrayList<>();
    private GestureDetector gestureDetector;
    private LinearLayout layout;
    int counter1 = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_too_doo_main);
        setResult(RESULT_CANCELED);
        layout = (LinearLayout) findViewById(R.id.toodooMain);
        Log.i("CREAAAAAAAAAAAAAAAATE", "YYYAAAAAAAAAAAAAAAA");
        if (savedInstanceState != null) {
            int counter = PreferenceManager.getDefaultSharedPreferences(this).getInt("key2", 0);
            Log.i("counter on create", Integer.toString(counter));
            for (int i = 0; i < counter; i++) {
                layout.addView(createTooDoo(PreferenceManager.getDefaultSharedPreferences(this).getString(Integer.toString(i), "")), i);
            }
        }
    }

    protected void onPause() {
        super.onPause();
        SharedPreferences sharedPreferencesSettings2 = this.getSharedPreferences("PREFERENCE2", MODE_PRIVATE);
        SharedPreferences.Editor editor2 = sharedPreferencesSettings2.edit();
        editor2.putInt("key2", 0);
        Log.i("PAAAAAAAAAAUSE", Integer.toString(0));
        editor2.commit();
    }

    protected void onResume() {
        super.onResume();
        layout = (LinearLayout) findViewById(R.id.toodooMain);
        SharedPreferences prefs2 = this.getSharedPreferences("PREFERENCE2", MODE_PRIVATE);
        int counter = prefs2.getInt("key2", 0);
        Log.i("ONRESUME", Integer.toString(counter));
        SharedPreferences prefs = this.getSharedPreferences("PREFERENCE", MODE_PRIVATE);
        for (int i = 0; i < counter; i++) {
            Log.i("RESUME TEXTS!!!!", prefs.getString(Integer.toString(i), ""));
            layout.addView(createTooDoo(prefs.getString(Integer.toString(i), ""))); //here
                textList.add(prefs.getString(Integer.toString(i), ""));
        }
    }

    protected void onDestroy() {
        super.onDestroy();
        int counter = 0;
        SharedPreferences.Editor editor1 = getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit();
        for (String t: textList) {
            editor1.putString(Integer.toString(counter), t).commit();
            counter++;
        }
        editor1.commit();
        SharedPreferences sharedPreferencesSettings2 = this.getSharedPreferences("PREFERENCE2", MODE_PRIVATE);
        SharedPreferences.Editor editor2 = sharedPreferencesSettings2.edit();
        editor2.putInt("key2", counter);
        Log.i("DESSSSTROY", Integer.toString(counter));
        editor2.commit();
    }

    private TextView createTooDoo(String subject) {
        final TextView toodoo = new TextView(this);
        final LinearLayout layout = (LinearLayout) findViewById(R.id.toodooMain);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT , 180);
        toodoo.setLayoutParams(params);
        toodoo.setId(counter1);
        counter1++;
        params.setMargins(0, 1, 0, 1);
        toodoo.setText(subject);
        toodoo.setTextSize(18);
        toodoo.setTextColor(Color.WHITE);
        toodoo.setBackgroundColor(getResources().getColor(R.color.blue2));
        toodoo.setGravity(Gravity.CENTER_VERTICAL);
        toodoo.setPadding(25, 0, 0, 0);
        toodoo.setTypeface(null, Typeface.BOLD);
        toodoo.setOnTouchListener(new OnSwipeTouchListener(this, toodoo, layout) {
            @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
            public void onSwipeRight() {
                toodoo.animate().translationX(500).alpha(0f).setDuration(250).setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        Log.i("TESTINGANIMATOR", "AAAAAAAAAAAAA");
                        layout.removeView(toodoo);
                        Log.i("REMOVE111111111111111", Boolean.toString(textList.remove(toodoo.getText().toString())));
                    }
                })
                        .start();
            }
        });
        return toodoo;
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK) {
            final LinearLayout layout = (LinearLayout) findViewById(R.id.toodooMain);
            final TextView toodoo = new TextView(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT , 180);
            toodoo.setLayoutParams(params);
            params.setMargins(0, 1, 0, 1);
            String subject = data.getStringExtra("subject");
            String date = data.getStringExtra("date");
            if (date.equals("")) {
                toodoo.setText(subject);
            } else {
                toodoo.setText(date +"    " + subject);
            }
            toodoo.setId(counter1);
            counter1++;
            toodoo.setTextSize(18);
            toodoo.setTextColor(Color.WHITE);
            toodoo.setBackgroundColor(getResources().getColor(R.color.blue2));
            toodoo.setGravity(Gravity.CENTER_VERTICAL);
            toodoo.setPadding(25, 0, 0, 0);
            toodoo.setTypeface(null, Typeface.BOLD);
            toodoo.setOnTouchListener(new OnSwipeTouchListener(this, toodoo, layout) {
                @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
                public void onSwipeRight() {
                    toodoo.animate().translationX(500).alpha(0f).setDuration(250).setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            layout.removeView(toodoo);
                            Log.i("REMOVE111111111111111", Boolean.toString(textList.remove(toodoo.getText().toString())));
                        }
                    })
                    .start();
                }
            });
            Log.i("TOOOODOOOADDED", toodoo.getText().toString());
            textList.add(toodoo.getText().toString());
            Collections.sort(textList, new toodooComparator());
            int counter = 0;
            for (String t: textList) {
                if (t.equals(toodoo.getText().toString())) {
                    layout.addView(createTooDoo(t), counter);
                }
                counter++;
            }
        } else if (resultCode == RESULT_CANCELED) {
            startActivity(new Intent(this, TooDooMain.class));
        }
    }
    class toodooComparator implements Comparator<String> {
        @Override
        public int compare(String lhs, String rhs) {
            if (lhs.substring(0, 6).matches("([0-9]{2})/([0-9]{2})/")
                    & rhs.substring(0, 6).matches("([0-9]{2})/([0-9]{2})/")) {
                return lhs.substring(6).compareTo(
                        rhs.substring(6)
                );
            }
            return lhs.compareTo(rhs);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_too_doo_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch (item.getItemId()) {
            case R.id.addTooDoo:
                Intent intent = new Intent(this, TooDooAdd.class);
                startActivityForResult(intent, ADD_VIEW);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
