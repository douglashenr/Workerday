package br.com.dhsoftware.workerday.util.textWatcher;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.text.NumberFormat;
import java.util.Locale;

public class MoneyTextWatcher implements TextWatcher {

    private EditText editText;
    private String lastAmount = "";
    private int lastCursorPosition = -1;


    public MoneyTextWatcher(EditText editText) {
        super();
        this.editText = editText;
    }

    @Override
    public void onTextChanged(CharSequence amount, int start, int before, int count) {

        if (!amount.toString().equals(lastAmount)) {

            String cleanString = clearCurrencyToNumber(amount.toString());

            try {

                String formattedAmount = transformToCurrency(cleanString);
                editText.removeTextChangedListener(this);
                editText.setText(formattedAmount);
                editText.setSelection(formattedAmount.length());
                editText.addTextChangedListener(this);

                if (lastCursorPosition != lastAmount.length() && lastCursorPosition != -1) {
                    int lengthDelta = formattedAmount.length() - lastAmount.length();
                    int newCursorOffset = Math.max(0, Math.min(formattedAmount.length(), lastCursorPosition + lengthDelta));
                    editText.setSelection(newCursorOffset);
                }
            } catch (Exception e) {
                //log something
            }
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        String value = s.toString();
        if (!value.equals("")) {
            String cleanString = clearCurrencyToNumber(value);
            lastAmount = transformToCurrency(cleanString);
            lastCursorPosition = editText.getSelectionStart();
        }
    }

    private String clearCurrencyToNumber(String currencyValue) {
        String result = null;

        if (currencyValue == null) {
            result = "";
        } else {
            result = currencyValue.replaceAll("[(a-z)|(A-Z)|($,. )]", "");
        }
        return result;
    }

    /*private boolean isCurrencyValue(String currencyValue, boolean podeSerZero) {
        boolean result;

        if (currencyValue == null || currencyValue.length() == 0) {
            result = false;
        } else {
            result = podeSerZero || !currencyValue.equals("0,00");
        }
        return result;
    }*/

    private String transformToCurrency(String value) {
        double parsed = Double.parseDouble(value);
        String formatted = NumberFormat.getCurrencyInstance(new Locale("pt", "BR")).format((parsed / 100));
        formatted = formatted.replaceAll("[^(0-9)(.,)]", "");
        return formatted;
    }
}
