package kr.pe.jw.citychat;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Button button1 = (Button) findViewById(R.id.s_10);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //저장
                saveRoom(1,1);
                //이동
                Intent intent = new Intent(HomeActivity.this, UserListActivity.class) ;
                startActivity(intent) ;
            }
        });
    }
    public void saveRoom(int place,int age){
        SharedPreferences pref;
        SharedPreferences.Editor editor;
        pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);
        editor = pref.edit();
        editor.putInt("place", place);
        editor.putInt("age", age);
        editor.apply();
    }
}