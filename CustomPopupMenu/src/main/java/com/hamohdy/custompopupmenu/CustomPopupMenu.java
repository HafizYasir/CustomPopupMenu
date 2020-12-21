package com.hamohdy.custompopupmenu;

import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.PopupWindow;

import androidx.annotation.MenuRes;

import com.hamohdy.custompopupmenu.databinding.MenuTextBinding;
import com.hamohdy.custompopupmenu.databinding.PopupMenuBinding;

public class CustomPopupMenu {

    public interface IMenuItemClickListener{
        void menuItemClicked(int id);
    }

    private View anchorView;
    private PopupWindow popupWindow;

    private final Context context;
    private final LayoutInflater inflater;
    private final PopupMenuBinding binding;

    private int xOffset, yOffset, gravity;
    private boolean shouldDrawGroupSeparator = true;

    private IMenuItemClickListener clickListener;

    private CustomPopupMenu(Context context){
        this.context = context;
        this.inflater = context.getSystemService(LayoutInflater.class);
        this.binding = PopupMenuBinding.inflate(inflater);
    }

    private CustomPopupMenu setAnchorAndParams(View anchor, int xOffset, int yOffset, int gravity){
        this.anchorView = anchor;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
        this.gravity = gravity;
        return this;
    }

    private CustomPopupMenu setSeparationEnabled(boolean enabled){
        this.shouldDrawGroupSeparator = enabled;
        return this;
    }

    private CustomPopupMenu setClickListener(IMenuItemClickListener listener){
        this.clickListener = listener;
        return this;
    }

    private CustomPopupMenu setMenuResources(Integer... menuResources){

        int i = 0;
        for (Integer resource:menuResources) {

            PopupMenu temporary = new PopupMenu(context, null);
            temporary.inflate(resource);
            addItemsToWindow(temporary.getMenu());

            if (shouldDrawGroupSeparator && i < menuResources.length-1) {
                View underline = inflater.inflate(R.layout.underline, binding.getRoot(), false);
                binding.getRoot().addView(underline);
            }
            ++i;
        }
        return this;
    }

    private CustomPopupMenu addItemsToWindow(Menu menu) {

        for (int i = 0; i < menu.size(); i++) {

            final MenuItem menuItem = menu.getItem(i);

            if (menuItem.getItemId() == R.id.cpm_add_underline) {
                View underline = (inflater.inflate(R.layout.underline, binding.getRoot(), false));
                binding.getRoot().addView(underline);
                continue;
            }

            final MenuTextBinding menuItemBinding = MenuTextBinding.inflate(inflater, binding.getRoot(), false);

            String itemTitle = menuItem.getTitle().toString();
            menuItemBinding.text.setText(itemTitle);
            menuItemBinding.text.setId(menuItem.getItemId());

            if (menuItem.getIcon() != null) menuItemBinding.icon.setImageDrawable(menuItem.getIcon());
            else menuItemBinding.icon.setVisibility(View.GONE);

            if(menuItem.hasSubMenu()) menuItemBinding.endIcon.setImageResource(R.drawable.ic_fluent_chevron_right_20_regular);
            else menuItemBinding.endIcon.setVisibility(View.GONE);

            menuItemBinding.getRoot().setOnClickListener(v -> {
                if (menuItem.hasSubMenu()) {
                    new MenuBuilder(context)
                            .setAnchorAndParams(anchorView, xOffset, yOffset, gravity)
                            .setSeparationEnabled(shouldDrawGroupSeparator)
                            .setClickListener(clickListener)
                            .setMenuResources().build()

                            .addItemsToWindow(menuItem.getSubMenu())
                            .showMenu();
                }
                else clickListener.menuItemClicked(menuItem.getItemId());

                popupWindow.dismiss();
            });

            binding.getRoot().addView(menuItemBinding.getRoot());
        }
        return this;
    }

    private int dpToPx(Context context){
        Resources r = context.getResources();
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 280, r.getDisplayMetrics());
    }

    public void showMenu(){

        popupWindow = new PopupWindow(binding.getRoot(), dpToPx(context), ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popupWindow.showAsDropDown(anchorView, xOffset, yOffset, gravity);
    }

    public static class MenuBuilder {

        private final Context context;

        private View anchorView;
        private Integer[] menuResources;
        private IMenuItemClickListener clickListener;

        private int xOffset, yOffset, gravity;
        private boolean shouldDrawGroupSeparator = true;

        public MenuBuilder(Context context) {
            this.context = context;
        }

        public MenuBuilder setAnchorAndParams(View anchor, int xOffset, int yOffset, int gravity){
            this.anchorView = anchor;
            this.xOffset = xOffset;
            this.yOffset = yOffset;
            this.gravity = gravity;
            return this;
        }

        public MenuBuilder setSeparationEnabled(boolean enabled){
            this.shouldDrawGroupSeparator = enabled;
            return this;
        }

        public MenuBuilder setClickListener(IMenuItemClickListener listener){
            this.clickListener = listener;
            return this;
        }

        public MenuBuilder setMenuResources(@MenuRes Integer... menuResources) {
            this.menuResources = menuResources;
            return this;
        }

        public CustomPopupMenu build() {

            return new CustomPopupMenu(context)
                    .setAnchorAndParams(anchorView, xOffset, yOffset, gravity)
                    .setSeparationEnabled(shouldDrawGroupSeparator)
                    .setClickListener(clickListener)
                    .setMenuResources(menuResources);
        }
    }

}
