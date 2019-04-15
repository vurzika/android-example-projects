package com.viktorija.coffeelist.main;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.viktorija.coffeelist.R;
import com.viktorija.coffeelist.database.DrinkEntity;
import com.viktorija.coffeelist.editor.EditorActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private DrinkRecyclerViewAdapter mAdapter;
    private List<DrinkEntity> mDrinkData = new ArrayList<>();
    private MainViewModel mViewModel;

    private Context mContext = MainActivity.this;

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        initViewModel();
        initRecyclerView(mViewModel);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {

            // Intent to open Editor Activity
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, EditorActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initRecyclerView(MainViewModel drinkViewModel) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);

        // Adapter populates the data into the RecyclerView
        mAdapter = new DrinkRecyclerViewAdapter(mContext, mDrinkData);
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.setHasFixedSize(true);

        DividerItemDecoration divider = new DividerItemDecoration(this, layoutManager.getOrientation());
        mRecyclerView.addItemDecoration(divider);
    }

    // Get a ViewModel from the ViewModelProvider
    // View model allows to survive configuration change
    private void initViewModel() {
        mViewModel = ViewModelProviders
                .of(MainActivity.this).get(MainViewModel.class);


        //Adding sample data automatically
        mViewModel.getDrinkList().observe(MainActivity.this, new Observer<List<DrinkEntity>>() {
            @Override
            public void onChanged(@Nullable List<DrinkEntity> drinkEntities) {
                mDrinkData.clear();
                if (drinkEntities != null) {
                    mDrinkData.addAll(drinkEntities);
                }
                mAdapter.notifyDataSetChanged();
            }
        });
    }

    // Menu related methods

    // Override onCreateOptionsMenu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Within onCreateOptionsMenu, use getMenuInflater().inflate to inflate the menu
        getMenuInflater().inflate(R.menu.menu_main, menu);
        // Return true to display your menu
        return true;
    }

    // Override onOptionsItemSelected
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemThatWasSelected = item.getItemId();

        if (itemThatWasSelected == R.id.action_add_sample_data) {
            mViewModel.addSampleData();
            return true;
        } else if (itemThatWasSelected == R.id.action_delete_all) {
            mViewModel.deleteAllDrinks();
            return true;
        }

        return false;
    }
}