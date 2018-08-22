package com.somethings.nipuna.rdb.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.support.v7.widget.SearchView;

import com.somethings.nipuna.rdb.Adapters.AddressAdapter;
import com.somethings.nipuna.rdb.Class.AddressBook;
import com.somethings.nipuna.rdb.Load_Data.GetAddresBook;
import com.somethings.nipuna.rdb.R;

import java.util.ArrayList;

public class PhoneAddressBook extends AppCompatActivity{

    public RecyclerView recyclerView;
    ArrayList<AddressBook> arrayList;
    private AddressAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_book);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // perform whatever you want on back arrow click
                Intent intent = new Intent(PhoneAddressBook.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });


        recyclerView = (RecyclerView) findViewById(R.id.address_book_View);
        arrayList = GetAddresBook.arrayList;
        adapter = new AddressAdapter(arrayList, PhoneAddressBook.this);

        GetAddresBook backgrounTask = new GetAddresBook(PhoneAddressBook.this, this);
        backgrounTask.execute();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_menu, menu);
        MenuItem search = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(search);
        search(searchView);
        Log.d(String.valueOf(search),"uman");
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }

    private void search(SearchView searchView) {

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newetext) {
                newetext=newetext.toLowerCase();
                ArrayList<AddressBook> list=new ArrayList<>();
                if(newetext.length() > 2){
                    for (AddressBook addressBook:arrayList){

                        String getEmp_number =addressBook.getEmp_number().toLowerCase();
                        String getEmp_name =addressBook.getEmp_name().toLowerCase();
                        String getEmp_branch_name = addressBook.getEmp_branch_name().toLowerCase();
                        String getEmp_mobile_no = addressBook.getEmp_mobile_no().toLowerCase();
                        String getEmp_branch_code = addressBook.getEmp_branch_code().toLowerCase();

                            if(getEmp_number.contains(newetext)||
                                    getEmp_name.contains(newetext)||
                                    getEmp_branch_name.contains(newetext)||
                                    getEmp_mobile_no.contains(newetext)||
                                    getEmp_branch_code.contains(newetext)){
                                 list.add(addressBook);
                            }
                    }
                adapter.setFilter(list);
                recyclerView.setAdapter(adapter);
                  }else {
                    adapter = new AddressAdapter(arrayList, PhoneAddressBook.this);
                    recyclerView.setAdapter(adapter);
                }
                    return true;
            }
        });
    }
}
