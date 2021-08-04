package com.example.coffeetime;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

public class OrderActivity extends AppCompatActivity {
    private TextView button;
    int quantity = 1;
    boolean hasWhippedCream;
    boolean hasChocolate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        button = findViewById(R.id.final_order);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater inflater = OrderActivity.this.getLayoutInflater();
                View v = inflater.inflate(R.layout.custom_popup, null);
                TextView textView = (TextView) v.findViewById(R.id.order2);
                int price = calculatePrice();
                EditText editText = findViewById(R.id.edit_text_view);
                String edit_Text_value = editText.getText().toString();
                TextUtils.isEmpty(edit_Text_value);
                if (edit_Text_value.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please Enter Your Name", Toast.LENGTH_SHORT).show();

                }

                String priceMessage = "Name: " + edit_Text_value + "\nAdd whipped cream?" + hasWhippedCream + "\nAdd Chocolate?" + hasChocolate
                        + "\nQuantity:" + quantity + "\nTotal :" + NumberFormat.getCurrencyInstance().format(price) + "\n" + getString(R.string.thank_you);
                textView.setText(priceMessage);
                AlertDialog.Builder builder = new AlertDialog.Builder(OrderActivity.this);
                builder.setView(v);
                builder.setTitle("Order Summary");
                builder.setPositiveButton("Pay", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(), "The payment has been successful...", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Toast.makeText(getApplicationContext(), "You clicked on Cancel", Toast.LENGTH_SHORT).show();
                        dialog.cancel();
                    }
                });
                builder.show();

            }


        });

    }

    public int calculatePrice() {
        int coffeePrice = 5;
        int whippedCream = 1;
        int chocolate = 2;
        CheckBox creamCheckBox = findViewById(R.id.cream_checkbox);
        hasWhippedCream = creamCheckBox.isChecked();
        CheckBox chocolateCheckBox = findViewById(R.id.chocolate_checkbox);
        hasChocolate = chocolateCheckBox.isChecked();
        int totalPrice = 0;
        if (creamCheckBox.isChecked() && chocolateCheckBox.isChecked())
            totalPrice = (coffeePrice + whippedCream + chocolate) * quantity;
        else if (chocolateCheckBox.isChecked())
            totalPrice = (coffeePrice + chocolate) * quantity;
        else if (creamCheckBox.isChecked())
            totalPrice = (coffeePrice + whippedCream) * quantity;
        else
            totalPrice = coffeePrice * quantity;
        return totalPrice;

    }
    public void increment(View view) {
        if (quantity == 100) {
            Toast toast = Toast.makeText(getApplicationContext(), "You cannot order more than 100 coffees", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }
        quantity = quantity + 1;
        displayQuantity(quantity);
    }

    public void decrement(View view) {
        if (quantity == 1) {
            Toast toast = Toast.makeText(getApplicationContext(), "You cannot order less than one coffee", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }
        quantity = quantity - 1;
        displayQuantity(quantity);

    }



    private void displayQuantity(int number) {
        TextView value = findViewById(R.id.value);
        value.setText("" + number);
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder= new AlertDialog.Builder(this);
        builder.setMessage("Are you sure that you want to Exit?").setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                OrderActivity.super.onBackPressed();
            }
        })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog= builder.create();
        alertDialog.show();

    }
}