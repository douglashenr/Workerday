package br.com.dhsoftware.workerday.util.textWatcher;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

public class PercentEditTextWatcher implements TextWatcher {

    EditText editText;

    public PercentEditTextWatcher(EditText editText) {
        super();
        this.editText = editText;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        //editText.setText(editText.getText().toString() + "%");
    }
}
