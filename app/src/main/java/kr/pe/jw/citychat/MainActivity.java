package kr.pe.jw.citychat;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

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
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    List<String> pcodeList = new ArrayList<String>();
    List<String> pvalueList = new ArrayList<String>();
    List<String> acodeList = new ArrayList<String>();
    List<String> avalueList = new ArrayList<String>();
    List<String> scodeList = new ArrayList<String>();
    List<String> svalueList = new ArrayList<String>();
    String pcode = "";
    String acode = "";
    String scode = "";
    String gid = "";
    TextView pText;
    TextView aText;
    TextView sText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        if(AppHelper.requestQueue == null)
            AppHelper.requestQueue = Volley.newRequestQueue(getApplicationContext());
        Button button1 = (Button) findViewById(R.id.bRegister) ;
        button1.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                memberInsertRequest();
            }
        });
        pText = (TextView) findViewById(R.id.place);

        pText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPlaceRequest();
            }
        });
        aText = (TextView) findViewById(R.id.age);

        aText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAgeRequest();
            }
        });
        sText = (TextView) findViewById(R.id.sex);

        sText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSexRequest();
            }
        });
    }


    public void memberInsertRequest() {
        EditText editText = (EditText)findViewById(R.id.id);
        gid = editText.getText().toString();
        if(gid.trim().length() == 0){
            alertId();
            return;
        }
        EditText editText1 = (EditText)findViewById(R.id.pw);
        String pw = editText1.getText().toString();
        if(pw.trim().length() == 0){
            alertpw();
            return;
        }
        EditText editText2 = (EditText)findViewById(R.id.nn);
        String nn = editText2.getText().toString();
        if(nn.trim().length() == 0){
            alertnn();
            return;
        }
        TextView textView = (TextView) findViewById(R.id.place);
        String place = textView.getText().toString();
        Toast.makeText(MainActivity.this, place, Toast.LENGTH_SHORT).show();
        if(place.trim().length() == 0){
            alertPlace();
            return;
        }
        TextView textView1 = (TextView) findViewById(R.id.age);
        String age = textView1.getText().toString();
        if(age.trim().length() == 0){
            alertAge();
            return;
        }
        TextView textView2 = (TextView) findViewById(R.id.sex);
        String sex = textView2.getText().toString();
        if(sex.trim().length() == 0){
            alertSex();
            return;
        }

        place = pcode;
        age = acode;
        sex = scode;



        String url;
        StringRequest request = new StringRequest(
                Request.Method.GET,
                url = String.format("http://3.82.172.28/chatBack/userInsert.php?id=%s&pw=%s&nickname=%s&age=%s&place=%s&sex=%s",gid,pw,nn,age,place,sex),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //대화상자
                        AlertDialog.Builder msgBuilder = new AlertDialog.Builder(MainActivity.this)
                                .setTitle("회원 가입")
                                .setMessage("회원가입 성공!!!")
                                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        //finish();
                                        //아이디 저장장
                                        SharedPreferences pref;
                                        SharedPreferences.Editor editor;
                                        pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);
                                        editor = pref.edit();
                                        editor.putString("id", gid);
                                        editor.apply();
                                        //홈 이동
                                        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                                        startActivity(intent);

                                    }
                                })
                                .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        //Toast.makeText(MainActivity.this, "안 끔", Toast.LENGTH_SHORT).show();
                                    }
                                });
                        AlertDialog msgDlg = msgBuilder.create();
                        msgDlg.show();


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        AlertDialog.Builder msgBuilder = new AlertDialog.Builder(MainActivity.this)
                                .setTitle("회원 가입")
                                .setMessage("회원가입 실패...")
                                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {



                                    }
                                })
                                .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        //Toast.makeText(MainActivity.this, "안 끔", Toast.LENGTH_SHORT).show();
                                    }
                                });
                        AlertDialog msgDlg = msgBuilder.create();
                        msgDlg.show();
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

        Toast.makeText(getApplicationContext(), "Request sent", Toast.LENGTH_SHORT).show();
    }
    public void getPlaceRequest() {

        String url;
        StringRequest request = new StringRequest(
                Request.Method.GET,
                url = "http://3.82.172.28/chatBack/get_place.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);

                            for (int i=0; i < jsonArray.length(); i++) {
                                JSONObject subJsonObject = jsonArray.getJSONObject(i);
                                String pcode = subJsonObject.getString("pCode");
                                String pvalue = subJsonObject.getString("pValue");

                                pcodeList.add(pcode);
                                pvalueList.add(pvalue);

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        pListClick(null);
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

        Toast.makeText(getApplicationContext(), "Request sent", Toast.LENGTH_SHORT).show();
    }
    public void getAgeRequest() {

        String url;
        StringRequest request = new StringRequest(
                Request.Method.GET,
                url = "http://3.82.172.28/chatBack/get_age.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);

                            for (int i=0; i < jsonArray.length(); i++) {
                                JSONObject subJsonObject = jsonArray.getJSONObject(i);
                                String acode = subJsonObject.getString("aCode");
                                String avalue = subJsonObject.getString("aValue");

                                acodeList.add(acode);
                                avalueList.add(avalue);

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        aRadioClick(null);
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

        Toast.makeText(getApplicationContext(), "Request sent", Toast.LENGTH_SHORT).show();
    }
    public void getSexRequest() {

        String url;
        StringRequest request = new StringRequest(
                Request.Method.GET,
                url = "http://3.82.172.28/chatBack/get_sex.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);

                            for (int i=0; i < jsonArray.length(); i++) {
                                JSONObject subJsonObject = jsonArray.getJSONObject(i);
                                String scode = subJsonObject.getString("sCode");
                                String svalue = subJsonObject.getString("sValue");

                                scodeList.add(scode);
                                svalueList.add(svalue);

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        sRadioClick(null);
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

        Toast.makeText(getApplicationContext(), "Request sent", Toast.LENGTH_SHORT).show();
    }
    public void println(String data){

    }
    //지역 다이얼 로그
    public void pListClick(View view) {
        new AlertDialog.Builder(this).setTitle("선택").setItems(pvalueList.toArray(new String[0]), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String value = pvalueList.get(which);
                pcode = pcodeList.get(which);
                Toast.makeText(MainActivity.this, "지역 : " + value, Toast.LENGTH_LONG).show();
                pText.setText(value);
            }
        }).setNeutralButton("닫기", null).setPositiveButton("확인", null).show();

    }
    //나이 다이얼 로그
    public void aRadioClick(View view) {
        new AlertDialog.Builder(this).setTitle("선택").setSingleChoiceItems(avalueList.toArray(new String[0]), -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String value = avalueList.get(which);
                acode = acodeList.get(which);

                Toast.makeText(MainActivity.this, "나이 : " + value, Toast.LENGTH_SHORT).show();
                aText.setText(value);
            }
        }).setNeutralButton("닫기",null).setPositiveButton("확인",null).setNegativeButton("취소", null).show();
    }
    //성별 다이얼 로그
    public void sRadioClick(View view) {
        new AlertDialog.Builder(this).setTitle("선택").setSingleChoiceItems(svalueList.toArray(new String[0]), -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String value =svalueList.get(which);
                scode = scodeList.get(which);
                Toast.makeText(MainActivity.this, "성별 : " + value, Toast.LENGTH_SHORT).show();
                sText.setText(value);
            }
        }).setNeutralButton("닫기",null).setPositiveButton("확인",null).setNegativeButton("취소", null).show();
    }
    void alertId() {
        AlertDialog.Builder msgBuilder = new AlertDialog.Builder(MainActivity.this)
                .setTitle("필수 항목")
                .setMessage("아이디는 필수항목입니다.")
                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //finish();
                    }
                })
                .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //Toast.makeText(MainActivity.this, "안 끔", Toast.LENGTH_SHORT).show();
                    }
                });
        AlertDialog msgDlg = msgBuilder.create();
        msgDlg.show();
    }
    void alertpw() {
        AlertDialog.Builder msgBuilder = new AlertDialog.Builder(MainActivity.this)
                .setTitle("필수 항목")
                .setMessage("패스워드는 필수항목입니다.")
                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //finish();
                    }
                })
                .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //Toast.makeText(MainActivity.this, "안 끔", Toast.LENGTH_SHORT).show();
                    }
                });
        AlertDialog msgDlg = msgBuilder.create();
        msgDlg.show();
    }
    void alertnn() {
        AlertDialog.Builder msgBuilder = new AlertDialog.Builder(MainActivity.this)
                .setTitle("필수 항목")
                .setMessage("닉네임은 필수항목입니다.")
                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //finish();
                    }
                })
                .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Toast.makeText(MainActivity.this, "안 끔", Toast.LENGTH_SHORT).show();
                    }
                });
        AlertDialog msgDlg = msgBuilder.create();
        msgDlg.show();
    }
    void alertPlace() {
        AlertDialog.Builder msgBuilder = new AlertDialog.Builder(MainActivity.this)
                .setTitle("필수 항목")
                .setMessage("지역은 필수항목입니다.")
                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // finish();
                    }
                })
                .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Toast.makeText(MainActivity.this, "안 끔", Toast.LENGTH_SHORT).show();
                    }
                });
        AlertDialog msgDlg = msgBuilder.create();
        msgDlg.show();
    }
    void alertAge() {
        AlertDialog.Builder msgBuilder = new AlertDialog.Builder(MainActivity.this)
                .setTitle("필수 항목")
                .setMessage("나이는 필수항목입니다.")
                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //finish();
                    }
                })
                .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //  Toast.makeText(MainActivity.this, "안 끔", Toast.LENGTH_SHORT).show();
                    }
                });
        AlertDialog msgDlg = msgBuilder.create();
        msgDlg.show();
    }
    void alertSex() {
        AlertDialog.Builder msgBuilder = new AlertDialog.Builder(MainActivity.this)
                .setTitle("필수 항목")
                .setMessage("성별는 필수항목입니다.")
                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // finish();
                    }
                })
                .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //Toast.makeText(MainActivity.this, "안 끔", Toast.LENGTH_SHORT).show();
                    }
                });
        AlertDialog msgDlg = msgBuilder.create();
        msgDlg.show();
    }
}