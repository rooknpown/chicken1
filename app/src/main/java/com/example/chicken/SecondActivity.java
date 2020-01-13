package com.example.chicken;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SecondActivity extends AppCompatActivity {
    private ArrayList<Second_order_info> order_array;
    private RecyclerView order;
    private LinearLayoutManager linearLayoutManager;
    private SecondAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Intent intent = getIntent();
        order_array = (ArrayList<Second_order_info>)intent.getExtras().getSerializable("order_array");
        order = (RecyclerView)findViewById(R.id.order);
        linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        order.setLayoutManager(linearLayoutManager);
        adapter = new SecondAdapter(order_array);
        order.setAdapter(adapter);

        Button button = (Button)findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ThirdActivity.class);
                intent.putExtra("order_array", order_array);
                startActivity(intent);
            }
        });
    }

}
