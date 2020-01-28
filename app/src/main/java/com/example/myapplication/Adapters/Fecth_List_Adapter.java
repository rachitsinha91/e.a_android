package com.example.myapplication.Adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Interfaces.Countings;
import com.example.myapplication.POJO.Hits;
import com.example.myapplication.R;

import java.util.List;

public class Fecth_List_Adapter extends RecyclerView.Adapter<Fecth_List_Adapter.MyView> {

    Context context;
    List<Hits>hits;
    int count=0;

    // TODO: Here i can use interface call passing the count value into Activity..
    Countings countings;


    public Fecth_List_Adapter(Context context, List<Hits>hits,Countings countings){
        this.context=context;
        this.hits=hits;
        this.countings=countings;
    }


    @NonNull
    @Override



    public Fecth_List_Adapter.MyView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view=View.inflate(context, R.layout.fetch_list_adapter,null);
        MyView view1=new MyView(view);
        return view1;
    }

    @Override
    public void onBindViewHolder(@NonNull final Fecth_List_Adapter.MyView holder, int position) {

        holder.tv_title.setText(hits.get(position).getTitle());
        holder.tv_create.setText(hits.get(position).getCreated_at());


        holder.cv_cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        holder.sh_bar.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // The toggle is enabled
                    holder.sh_bar.setEnabled(true);
                    count++;

                    countings.counts(count);

                } else {
                    // The toggle is disabled
                    holder.sh_bar.setEnabled(false);
                    count--;
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return hits.size();
    }

    public class MyView extends RecyclerView.ViewHolder {

        TextView tv_title,tv_create;
        Switch sh_bar;
        CardView cv_cardview;
        public MyView(@NonNull View itemView) {
            super(itemView);
            tv_title=(TextView)itemView.findViewById(R.id.tv_title);
            tv_create=(TextView)itemView.findViewById(R.id.tv_create);
            sh_bar=(Switch)itemView.findViewById(R.id.sh_bar);
            cv_cardview=(CardView) itemView.findViewById(R.id.cv_cardview);
        }
    }
}
