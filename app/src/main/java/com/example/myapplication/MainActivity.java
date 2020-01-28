package com.example.myapplication;

import android.content.Context;
import android.os.Bundle;

import com.example.myapplication.Activities.PaginationListener;
import com.example.myapplication.Adapters.Fecth_List_Adapter;
import com.example.myapplication.Interfaces.Countings;
import com.example.myapplication.Interfaces.Fetchlist_Interfaces;
import com.example.myapplication.POJO.Hits;
import com.example.myapplication.Server_Connections.Api_Connections;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReferenceArray;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.myapplication.Activities.PaginationListener.PAGE_START;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener  {

    RecyclerView rv_fetchList;
    Context context;
    LinearLayoutManager linearLayoutManager;
    Fecth_List_Adapter fecth_list_adapter;
    ArrayList<Hits>hits;
    SwipeRefreshLayout swipeRefresh;

    //TODO: Here Countings Interface used for fetching the count number...
    Countings countings=null;

    private int currentPage = PAGE_START;
    private boolean isLastPage = false;
    private int totalPage = 10;
    private boolean isLoading = false;
    int itemCount = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        context=this;



            FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        rv_fetchList=(RecyclerView)findViewById(R.id.rv_fetchList);
        linearLayoutManager=new LinearLayoutManager(context);
        rv_fetchList.setLayoutManager(linearLayoutManager);
        rv_fetchList.setAdapter(fecth_list_adapter);


        swipeRefresh=(SwipeRefreshLayout)findViewById(R.id.swipeRefresh);
        swipeRefresh.setOnRefreshListener(this);
        rv_fetchList.setHasFixedSize(true);


        /**
         * add scroll listener while user reach in bottom load more will call
         */
        rv_fetchList.addOnScrollListener(new PaginationListener(linearLayoutManager) {
            @Override
            protected void loadMoreItems() {
                isLoading = true;
                currentPage++;

            }
            @Override
            public boolean isLastPage() {
                return isLastPage;
            }
            @Override
            public boolean isLoading() {
                return isLoading;
            }
        });



        // use a linear layout manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rv_fetchList.setLayoutManager(layoutManager);
        countings=new Countings() {
            @Override
            public void counts(int position) {

            }
        };

        fetchList();
    }

    //TODO: Fetching List....

    public void fetchList() {

        String tags="story";
        String page="1";
        final Fetchlist_Interfaces fetchlist_interfaces= Api_Connections.getClient(context)
                .create(Fetchlist_Interfaces.class);
        Call<ArrayList<Hits>>call=fetchlist_interfaces.getsearch_by_date(tags,page);
        call.enqueue(new Callback<ArrayList<Hits>>() {
            @Override
            public void onResponse(Call<ArrayList<Hits>> call, Response<ArrayList<Hits>> response) {

                for(int index=0;index<response.body().size();index++){

                    if(response.body().size()==0){
                        Toast.makeText(context, "No Data Found", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    String titles=response.body().get(index).getTitle();
                    String creates=response.body().get(index).getCreated_at();
                    hits.add(new Hits(titles,creates));
                }

                fecth_list_adapter=new Fecth_List_Adapter(context,hits,countings);
                rv_fetchList.setAdapter(fecth_list_adapter);

            }

            @Override
            public void onFailure(Call<ArrayList<Hits>> call, Throwable t) {
              //  Toast.makeText(context, "Some problems generated, Please try Again..", Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRefresh() {

    }
}
