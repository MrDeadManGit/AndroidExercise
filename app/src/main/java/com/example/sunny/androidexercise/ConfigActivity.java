package com.example.sunny.androidexercise;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ConfigActivity extends AppCompatActivity {

    EditText et_anim_duration;
    EditText et_item_size;
    EditText et_item_spacing;
    EditText et_item_count;

    Button bt_custom;
    Button bt_default;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);
        initViews();
    }

    private void initViews() {
        et_anim_duration=(EditText)findViewById(R.id.et_anim_duration);
        et_item_size=(EditText)findViewById(R.id.et_item_size);
        et_item_spacing=(EditText)findViewById(R.id.et_item_spacing);
        et_item_count=(EditText)findViewById(R.id.et_item_count);

        bt_custom=(Button)findViewById(R.id.bt_custom);
        bt_custom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validateValues()){
                    Intent next=new Intent(ConfigActivity.this,MainActivity.class);
                    next.putExtra("duration",et_anim_duration.getText().toString().trim());
                    next.putExtra("size",et_item_size.getText().toString().trim());
                    next.putExtra("spacing",et_item_spacing.getText().toString().trim());
                    next.putExtra("count",et_item_count.getText().toString().trim());

                    next.putExtra("type",1);
                    startActivity(next);
                }
            }
        });

        bt_default=(Button)findViewById(R.id.bt_default);

        bt_default.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validateValues()){
                    Intent next=new Intent(ConfigActivity.this,MainActivity.class);
                    next.putExtra("duration",et_anim_duration.getText().toString().trim());
                    next.putExtra("size",et_item_size.getText().toString().trim());
                    next.putExtra("spacing",et_item_spacing.getText().toString().trim());
                    next.putExtra("count",et_item_count.getText().toString().trim());
                    next.putExtra("type",0);
                    startActivity(next);
                }
            }
        });
    }

    private boolean validateValues() {
        if(et_anim_duration.getText().toString().isEmpty()){
            return false;
        }
        if(et_item_size.getText().toString().isEmpty()){
            return false;
        }
        if (et_item_spacing.getText().toString().isEmpty()){
            return false;
        }
        if (et_item_count.getText().toString().isEmpty()){
            return false;
        }

        return true;
    }
}
