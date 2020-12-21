package com.hamohdy.custompopupmenudemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Gravity;
import android.widget.Toast;

import com.hamohdy.custompopupmenu.CustomPopupMenu;

public class MainActivity extends AppCompatActivity implements CustomPopupMenu.IMenuItemClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.show_menu).setOnClickListener(v -> new CustomPopupMenu.MenuBuilder(this)
                .setAnchorAndParams(v, 0,0, Gravity.NO_GRAVITY)
                .setClickListener(this).build().showMenu());
    }

    @Override
    public void menuItemClicked(int id) {
        Toast.makeText(this, String.format("Item with %s menu item id was clicked", id), Toast.LENGTH_SHORT).show();
    }
}