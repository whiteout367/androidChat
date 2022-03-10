package kr.pe.jw.citychat;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainViewAdapter extends RecyclerView.Adapter<MainViewAdapter.Holder>{
    private Context context;
    private List<WordItemData> list = new ArrayList<>();
    private Intent intent;

    public MainViewAdapter(Context context, List<WordItemData> list) {
        this.context = context;
        this.list = list;
    }

    // ViewHolder 생성
    // row layout을 화면에 뿌려주고 holder에 연결
    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {

            View view1 = LayoutInflater.from(parent.getContext()).inflate(R.layout.item10, parent, false);

        Holder holder = new Holder(view1);
        return holder;
    }

    /*
     * Todo 만들어진 ViewHolder에 data 삽입 ListView의 getView와 동일
     *
     * */
    @Override
    public void onBindViewHolder(Holder holder, int position) {
        // 각 위치에 문자열 세팅
        int itemposition = position;
        holder.nn.setText(list.get(itemposition).nn);
        holder.age.setText(list.get(itemposition).age);
        holder.area.setText(list.get(itemposition).area);
        holder.sex.setText(list.get(itemposition).sex);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(v.getContext(), ChatActivity.class);
                v.getContext().startActivity(intent);


            }
        });
        Log.e("StudyApp", "onBindViewHolder" + itemposition);
    }
    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public long getItemId(int position){
        return position;
    }
    // 몇개의 데이터를 리스트로 뿌려줘야하는지 반드시 정의해줘야한다
    @Override
    public int getItemCount() {
        return list.size(); // RecyclerView의 size return
    }

    // ViewHolder는 하나의 View를 보존하는 역할을 한다
    public class Holder extends RecyclerView.ViewHolder{
        public TextView nn;
        public TextView age;
        public TextView area;
        public TextView sex;

        public Holder(View view){
            super(view);
            nn = (TextView) view.findViewById(R.id.nn);
            age = (TextView) view.findViewById(R.id.age);
            area = (TextView) view.findViewById(R.id.area);
            sex = (TextView) view.findViewById(R.id.sex);

        }
    }
}
