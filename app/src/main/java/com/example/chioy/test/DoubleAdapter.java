package com.example.chioy.test;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class DoubleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private ArrayList<PersonInfo> personInfos;
    private OnItemClickListener onItemClickListener = null;
    private int Tag = 1;
    private int COMMENT_FIRST = 1;
    private int COMMENT_SECOND = 2;
    public DoubleAdapter(Context context,ArrayList<PersonInfo>  personInfos){
        this.context = context;
        this.personInfos = personInfos;
        notifyItemInserted(getItemCount());
    }

    public void setPersonInfos(ArrayList<PersonInfo> personInfos) {
        this.personInfos = personInfos;
    }

    public int getTag() {
        return Tag;
    }

    public void setTag(int tag) {
        Tag = tag;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        if (viewType == COMMENT_FIRST) {
            View viewOne = View.inflate(viewGroup.getContext(), R.layout.activity_info_card, null);
            return new MyViewHolder(viewOne);
        } else if (viewType == COMMENT_SECOND) {
            View viewTwo = View.inflate(viewGroup.getContext(), R.layout.info_table, null);
            return new MyViewHolder2(viewTwo);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        PersonInfo personInfo = null;
        if(viewHolder instanceof MyViewHolder){
            MyViewHolder myViewHolder = (MyViewHolder)viewHolder;
            personInfo = personInfos.get(i);
            myViewHolder.editTextId.setText(Integer.toString(personInfo.getId()));
            myViewHolder.editTextName.setText(personInfo.getName());
            myViewHolder.editTextSex.setText(personInfo.getSex());
            myViewHolder.editTextAge.setText(personInfo.getAge());
            myViewHolder.editTextHeight.setText(Double.toString(personInfo.getHeight()));
            myViewHolder.editTextWeight.setText(Double.toString(personInfo.getWeight()));
        }
        if (viewHolder instanceof MyViewHolder2){
            MyViewHolder2 myViewHolder2 = (MyViewHolder2)viewHolder;
            if (i==0||i==1){
                myViewHolder2.textViewId.setText("ID");
                myViewHolder2.textViewName.setText("姓名");
                myViewHolder2.textViewSex.setText("性别");
                myViewHolder2.textViewAge.setText("年龄");
                myViewHolder2.textViewHeight.setText("身高");
                myViewHolder2.textViewWeight.setText("体重");
            }else {
                personInfo = personInfos.get(i-2);
                myViewHolder2.textViewId.setText(Integer.toString(personInfo.getId()));
                myViewHolder2.textViewName.setText(personInfo.getName());
                myViewHolder2.textViewSex.setText(personInfo.getSex());
                myViewHolder2.textViewAge.setText(personInfo.getAge());
                myViewHolder2.textViewHeight.setText(Double.toString(personInfo.getHeight()));
                myViewHolder2.textViewWeight.setText(Double.toString(personInfo.getWeight()));
            }
        }
    }

    @Override
    public int getItemCount() {
        if (getTag() == 1)
        return personInfos != null ? personInfos.size():0;
        else
            return personInfos != null ? personInfos.size()+2:0;
    }

    @Override
    public int getItemViewType(int position) {
        if (getTag() == 1) {
            return COMMENT_FIRST;
        }
        if (getTag() == 2) {
            return COMMENT_SECOND;
        }
        return 0;
    }

    public interface OnItemClickListener{
        void onClick(View v,int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public EditText editTextId = null;
        public EditText editTextName = null;
        public EditText editTextSex = null;
        public EditText editTextAge = null;
        public EditText editTextHeight = null;
        public EditText editTextWeight = null;
        public MyViewHolder(View itemView) {
            super(itemView);
            editTextId = (EditText)itemView.findViewById(R.id.edtId);
                editTextName = (EditText)itemView.findViewById(R.id.edtName);
                editTextSex = (EditText)itemView.findViewById(R.id.edtSex);
                editTextAge = (EditText)itemView.findViewById(R.id.edtAge);
                editTextHeight = (EditText)itemView.findViewById(R.id.edtHeight);
                editTextWeight = (EditText)itemView.findViewById(R.id.edtWeight);
                editTextId.setEnabled(false);
                editTextName.setEnabled(false);
                editTextSex.setEnabled(false);
                editTextAge.setEnabled(false);
                editTextHeight.setEnabled(false);
                editTextWeight.setEnabled(false);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (onItemClickListener != null){
                onItemClickListener.onClick(view,getAdapterPosition());
            }
        }
    }
    class MyViewHolder2 extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView textViewId = null;
        public TextView textViewName = null;
        public TextView textViewSex = null;
        public TextView textViewAge = null;
        public TextView textViewHeight = null;
        public TextView textViewWeight = null;
        public MyViewHolder2(View itemView) {
            super(itemView);
            textViewId = (TextView)itemView.findViewById(R.id.txtId);
            textViewName = (TextView)itemView.findViewById(R.id.txtName);
            textViewSex = (TextView)itemView.findViewById(R.id.txtSex);
            textViewAge = (TextView)itemView.findViewById(R.id.txtAge);
            textViewHeight = (TextView)itemView.findViewById(R.id.txtHeight);
            textViewWeight = (TextView)itemView.findViewById(R.id.txtWeight);
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View view) {
            if (onItemClickListener != null){
                onItemClickListener.onClick(view,getAdapterPosition()-2);
            }
        }
    }
}
