package kr.pe.jw.citychat;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class ChatActivity extends AppCompatActivity {
    private RecyclerView mMessageRecycler;
    private MessageListAdapter mMessageAdapter;
    private List<Message> messageList = new ArrayList<Message>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        //샘플 데이터 생성
        addItem("white","너","2021","우리","나나나나나나나나");
        addItem("나","white","2021","우리","나나나나나나나나");
        mMessageRecycler = (RecyclerView) findViewById(R.id.reyclerview_message_list);
        mMessageAdapter = new MessageListAdapter(this, messageList);
        mMessageRecycler.setLayoutManager(new LinearLayoutManager(this));
        mMessageRecycler.setAdapter(mMessageAdapter);
        mMessageAdapter.notifyDataSetChanged();
        if(AppHelper.requestQueue == null)
            AppHelper.requestQueue = Volley.newRequestQueue(getApplicationContext());
        Timer timer = new Timer();

        TimerTask TT = new TimerTask() {
            @Override
            public void run() {
                // 반복실행할 구문
                getMessageRequest();
            }

        };
        //타이머에서 Task호출
        timer.schedule(TT, 0, 10000); //10초마다 Timer 실행

        //timer.cancel();//타이머 종료

        Button sButton = (Button)findViewById(R.id.button_chatbox_send);
        sButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                messageRequest();
            }
        });

        Button exitButton = (Button)findViewById(R.id.exitChat);
        exitButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }
    public void addItem(String sid, String rid, String mdt, String nn, String message){
        messageList.add(new Message(sid, rid, mdt, nn, message));


    }
    public void messageRequest() {
        EditText editText = (EditText)findViewById(R.id.edittext_chatbox);
        String message = editText.getText().toString();
        if(message.trim().length() == 0){

            return;
        }
        String sid = "white";//sf에 저장됨
        String rid = "test";//sf에 저장됨
        String msg = message;//php에 저장
        String url;
        StringRequest request = new StringRequest(
                Request.Method.GET,
                url = String.format("http://3.82.172.28/chatBack/messageInsert.php?sid=%s&rid=%s&msg=%s",sid,rid,msg),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Toast.makeText(ChatActivity.this, "됨", Toast.LENGTH_SHORT).show();


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                //params.put("key", "value");
                return params;
            }
        };

        // 이전 결과가 있더라도 새로 요청
        request.setShouldCache(false);

        AppHelper.requestQueue.add(request);


    }
    public void getMessageRequest() {
        messageList.clear();//기존에 있던 값 삭제
        String url;
        StringRequest request = new StringRequest(
                Request.Method.GET,
                url = "http://3.82.172.28/chatBack/get_message.php?id=white",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);

                            for (int i=0; i < jsonArray.length(); i++) {
                                JSONObject subJsonObject = jsonArray.getJSONObject(i);
                                String sid = subJsonObject.getString("sid");
                                String rid = subJsonObject.getString("rid");
                                String nn = subJsonObject.getString("nn");
                                String msg = subJsonObject.getString("msg");
                                String sdt = subJsonObject.getString("sdt");
                                addItem(sid,rid,sdt,nn,msg);
                            }
                            mMessageAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                //params.put("key", "value");
                return params;
            }
        };

        // 이전 결과가 있더라도 새로 요청
        request.setShouldCache(false);

        AppHelper.requestQueue.add(request);


    }
}