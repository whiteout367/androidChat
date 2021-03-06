package kr.pe.jw.citychat;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link List10Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class List10Fragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    private RecyclerView recyclerView;
    private MainViewAdapter adapter;
    private ArrayList<WordItemData> list = new ArrayList<>();
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public List10Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment List10Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static List10Fragment newInstance(String param1, String param2) {
        List10Fragment fragment = new List10Fragment();
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
        if(AppHelper.requestQueue == null)
            AppHelper.requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        getUserListRequest();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.fragment_list10, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);

        //list = WordItemData.createContactsList(5);
//        addItem("???","20","??????","???");
//        addItem("???","30","??????","???");
//        addItem("??????","40","??????","???");
        getUserListRequest();

        recyclerView.setHasFixedSize(true);
        adapter = new MainViewAdapter(getActivity(), list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);



        Log.e("Frag", "MainFragment");
        return rootView;
    }

    public void addItem(String nn, String age, String area, String sex){
        WordItemData item =  new WordItemData(nn,age,area,sex);
        list.add(item);


    }
    public void getUserListRequest() {

        String url;
        StringRequest request = new StringRequest(
                Request.Method.GET,
                url = "http://3.82.172.28/chatBack/get_user_list.php?age=1",

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);

                            for (int i=0; i < jsonArray.length(); i++) {
                                JSONObject subJsonObject = jsonArray.getJSONObject(i);
                                String nn = subJsonObject.getString("nn");
                                String age = subJsonObject.getString("age");
                                String area = subJsonObject.getString("place");
                                String sex = subJsonObject.getString("sex");


                                addItem(nn,age,area,sex);



                            }
                            adapter.notifyDataSetChanged();
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

        // ?????? ????????? ???????????? ?????? ??????
        request.setShouldCache(false);

        AppHelper.requestQueue.add(request);


    }
}