package com.somethings.nipuna.rdb.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.somethings.nipuna.rdb.Adapters.BranchAdapter;
import com.somethings.nipuna.rdb.Class.AddressBranch;
import com.somethings.nipuna.rdb.Load_Data.GetAddressBranch;
import com.somethings.nipuna.rdb.R;

import java.util.ArrayList;

public class BranchAddressActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    ArrayList<AddressBranch> arrayList;
    private BranchAdapter adapter;
    Button nearest_atm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_branch_address);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BranchAddressActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        recyclerView = (RecyclerView)findViewById(R.id.branch_address_View);
        arrayList = GetAddressBranch.arrayList;
        adapter = new BranchAdapter(BranchAddressActivity.this,arrayList);

        GetAddressBranch backgrounTask = new GetAddressBranch(BranchAddressActivity.this,this);
        backgrounTask.execute();


        nearest_atm=findViewById(R.id.nearest_atm);

        nearest_atm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String C="C";
                Intent intent = new Intent(BranchAddressActivity.this, NearestBranchATMActivity.class);
                intent.putExtra("C",C);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_menu, menu);
        MenuItem search = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(search);
        Search(searchView);
        Log.d(String.valueOf(search),"uman");
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }

    private void Search(SearchView searchView) {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                newText=newText.toLowerCase();
                ArrayList<AddressBranch> list=new ArrayList<>();
                if(newText.length() > 2){
                    for (AddressBranch addressBranch:arrayList){

                        String getBranch_code = addressBranch.getBranch_code().toLowerCase();
                        String getBranch_name =addressBranch.getBranch_name().toLowerCase();
                        String getBranch_address = addressBranch.getBranch_address().toLowerCase();

                        if(getBranch_code.contains(newText)|| getBranch_name.contains(newText)||
                                getBranch_address.contains(newText)){
                            list.add(addressBranch);
                        }

                    }
                    adapter.setFilter(list);
                    recyclerView.setAdapter(adapter);
                }else {
                    adapter = new BranchAdapter(BranchAddressActivity.this,arrayList);
                    recyclerView.setAdapter(adapter);
                }
                return true;
            }
        });
    }

}
