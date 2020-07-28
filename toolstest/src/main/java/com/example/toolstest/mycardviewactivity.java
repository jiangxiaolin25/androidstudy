package com.example.toolstest;

import android.animation.AnimatorSet;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;



import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class mycardviewactivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;

    private MyAdapter myAdapter;


    @Override
    protected void onCreate( Bundle savedInstanceState )
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mymaincardview);

//        getActionBar().setTitle("那些年我们追的星女郎");

        // 拿到RecyclerView
        mRecyclerView = (RecyclerView) findViewById(R.id.list);
        // 设置LinearLayoutManager
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        // 设置ItemAnimatorw
        AnimatorSet animatorSet = new AnimatorSet();
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        // 设置固定大小
        mRecyclerView.setHasFixedSize(true);
        // 初始化自定义的适配器
        myAdapter = new MyAdapter(this);
        // 为mRecyclerView设置适配器
        mRecyclerView.setAdapter(myAdapter);

    }




}
class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>{


    private Context mContext;
    public MyAdapter(Context mContext){

        this.mContext = mContext;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = View.inflate(mContext, R.layout.mycardview, null);
        //设置条目的布局方式
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);
        MyViewHolder myViewHolder = new MyViewHolder(view);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder viewHolder, int i) {

        viewHolder.tv.setText("text " + i);
    }


    @Override
    public int getItemCount() {
        return 20;
    }

    class MyViewHolder extends RecyclerView.ViewHolder
    {

        TextView tv;
        public MyViewHolder(View view)
        {
            super(view);

            tv = (TextView) view.findViewById(R.id.info_text_one);

        }
    }



}