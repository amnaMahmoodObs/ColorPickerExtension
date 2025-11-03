package com.obsidiansoft.colorpicker;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.google.appinventor.components.annotations.*;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.runtime.AndroidNonvisibleComponent;
import com.google.appinventor.components.runtime.ComponentContainer;
import com.google.appinventor.components.runtime.EventDispatcher;

@DesignerComponent(
        version = 1,
        description = "A simple extension for picking colors.",
        category = ComponentCategory.EXTENSION,
        nonVisible = true,
        iconName = "https://i.postimg.cc/XYZ/color-picker.png")

@SimpleObject(external = true)
public class ColorPicker extends AndroidNonvisibleComponent {

    private Context context;
    private Activity activity;

    public ColorPicker(ComponentContainer container) {
        super(container.$form());
        this.activity = container.$context();
        this.context = container.$context();
    }

    @SimpleFunction(description = "Show a simple color picker dialog.")
    public void ShowColorPicker() {

        final int[] colors = {
                Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW,
                Color.MAGENTA, Color.CYAN, Color.BLACK, Color.WHITE,
                Color.GRAY, Color.DKGRAY, Color.LTGRAY, Color.parseColor("#FFA500")
        };

        final String[] colorNames = {
                "Red", "Green", "Blue", "Yellow",
                "Magenta", "Cyan", "Black", "White",
                "Gray", "Dark Gray", "Light Gray", "Orange"
        };

        final AlertDialog dialog = new AlertDialog.Builder(context).create();

        ScrollView scrollView = new ScrollView(context);
        LinearLayout mainLayout = new LinearLayout(context);
        mainLayout.setOrientation(LinearLayout.VERTICAL);
        scrollView.addView(mainLayout);

        for (int i = 0; i < colors.length; i++) {
            final int selectedColor = colors[i];
            final String selectedName = colorNames[i];

            Button btn = new Button(context);
            btn.setText(selectedName);
            btn.setBackgroundColor(selectedColor);
            btn.setTextColor(i == 7 ? Color.BLACK : Color.WHITE);
            btn.setPadding(10, 10, 10, 10);

            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "You picked: " + selectedName, Toast.LENGTH_SHORT).show();
                    GotColor(selectedColor);
                    dialog.dismiss(); 
                }
            });

            mainLayout.addView(btn);
        }

        dialog.setTitle("Pick a Color");
        dialog.setView(scrollView);
        dialog.show();
    }

    @SimpleEvent(description = "Triggered when a color is chosen.")
    public void GotColor(int colorValue) {
        EventDispatcher.dispatchEvent(this, "GotColor", colorValue);
    }
}