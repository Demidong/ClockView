package com.dx.demi.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;

import com.dx.demi.R;
import com.dx.demi.adapter.EmojiAdapter;
import com.dx.demi.bean.EmojiData;

import java.util.ArrayList;

/**
 * Created by demi on 18/1/24.
 */

public class InputEmojiActivity extends Activity {
    private TextView send;
    private TextView content;
    private GridView emoji_grid;
    private EditText edit_input;
    private EmojiAdapter adapter;
    private ArrayList<String> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);
        send = (TextView) findViewById(R.id.send);
        content = (TextView) findViewById(R.id.content);
        emoji_grid = (GridView) findViewById(R.id.emoji_grid);
        edit_input = (EditText) findViewById(R.id.edit_input);
        list = EmojiData.initEmojiString();
        adapter = new EmojiAdapter(list, InputEmojiActivity.this);
        emoji_grid.setAdapter(adapter);
        emoji_grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                edit_input.getText().append(list.get(position));
            }
        });
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                content.setText(edit_input.getText());
                edit_input.setText("");
            }
        });
    }
}
