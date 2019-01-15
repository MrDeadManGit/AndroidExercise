package com.example.sunny.androidexercise;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rv_items;
    GridLayoutManager gridLayoutManager;
    MyAdapterDefault myAdapterDefault;
    MyAdapterCustom myAdapterCustom;

    List<Integer> itemList;

    int itemHeightDp = 80;
    int itemSpacingDp = 10;
    int itemCount = 20;
    int animDuration=500;

    int rowCount;

    DisplayMetrics displayMetrics;
    int screenHeight;
    int screenWidth;


    int adapterType=1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        screenHeight = displayMetrics.heightPixels;
        screenWidth = displayMetrics.widthPixels;
        animDuration=Integer.parseInt(getIntent().getStringExtra("duration"));
        itemHeightDp=Integer.parseInt(getIntent().getStringExtra("size"));
        itemSpacingDp=Integer.parseInt(getIntent().getStringExtra("spacing"));
        itemCount=Integer.parseInt(getIntent().getStringExtra("count"));

        adapterType=getIntent().getIntExtra("type",1);

        initRecyclerList();
      //  initGridView();


    }



    private void initRecyclerList() {
        itemList = new ArrayList<>();
        for (int i = 0; i < itemCount; i++) {
            itemList.add(i);
        }
        rv_items = (RecyclerView) findViewById(R.id.rv_items);


        rv_items.setVisibility(View.VISIBLE);
        rowCount = (int) (screenWidth / (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, itemHeightDp, getResources().getDisplayMetrics())));
        gridLayoutManager = new GridLayoutManager(this, rowCount);
        rv_items.setLayoutManager(gridLayoutManager);

        MyItemAnimator myItemAnimator=new MyItemAnimator(animDuration,rowCount);
     /*   myItemAnimator.setTotalCount(itemList.size());
        myItemAnimator.setDeletePosition(8);*/
        rv_items.setItemAnimator(myItemAnimator);


        if(adapterType==0){
            myAdapterDefault = new MyAdapterDefault(this, itemList, itemHeightDp, rowCount,animDuration,itemSpacingDp);
            rv_items.setAdapter(myAdapterDefault);
        }
        else {
            myAdapterCustom = new MyAdapterCustom(this, itemList, itemHeightDp, rowCount,animDuration,itemSpacingDp);
            rv_items.setAdapter(myAdapterCustom);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        menu.clear();
        getMenuInflater().inflate(R.menu.side_option, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.reload:
                initRecyclerList();
                break;
        }
        return true;

    }

}
