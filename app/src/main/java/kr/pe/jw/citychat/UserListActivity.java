package kr.pe.jw.citychat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class UserListActivity extends AppCompatActivity {
    Fragment fhome;
    Fragment fuserlist;
    Fragment fchatlist;
    Fragment f10;
    Fragment f20;
    Fragment f30;
    Fragment f40;
    Fragment f50;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);

        fhome = new HomeFragment();
        fuserlist = new UserListFragment();
        fchatlist = new ChattingListFragment();
        f10 = new List10Fragment();
        f20 = new List20Fragment();
        f30 = new List30Fragment();
        f40 = new List40Fragment();
        f50 = new List50Fragment();


        getSupportFragmentManager().beginTransaction().replace(R.id.container, f10).commit();
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom);
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.tab_10:
                                getSupportFragmentManager().beginTransaction().replace(R.id.container, f10).commit();
                                return true;

                            case R.id.tab_20:
                                getSupportFragmentManager().beginTransaction().replace(R.id.container, f20).commit();
                                return true;

                            case R.id.tab_30:
                                getSupportFragmentManager().beginTransaction().replace(R.id.container, f30).commit();
                                return true;
                            case R.id.tab_40:
                                getSupportFragmentManager().beginTransaction().replace(R.id.container, f40).commit();
                                return true;
                            case R.id.tab_50:
                                getSupportFragmentManager().beginTransaction().replace(R.id.container, f50).commit();
                                return true;
                        }
                        return false;
                    }
                });
    }

}