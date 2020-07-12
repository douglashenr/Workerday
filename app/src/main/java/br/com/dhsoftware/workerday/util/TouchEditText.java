package br.com.dhsoftware.workerday.util;

import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

public class TouchEditText implements View.OnTouchListener {


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        final int DRAWABLE_LEFT = 0;
        final int DRAWABLE_TOP = 1;
        final int DRAWABLE_RIGHT = 2;
        final int DRAWABLE_BOTTOM = 3;

        if (event.getAction() == MotionEvent.ACTION_UP) {
            EditText editText = (EditText) v;
            if (event.getRawX() >= (editText.getRight() - editText.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                editText.setText("");
            }
        }
        return false;
    }


}
