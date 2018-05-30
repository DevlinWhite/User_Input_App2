package com.example.android.justjava2;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int qty = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    /**
     * This method is called when the plus button is clicked.
     */
    public void increment(View view) {
        if (qty == 100) {
            /** display toast message when qty is more than 100*/
            Toast.makeText(this, "You cannot order more than 100 coffees.", Toast.LENGTH_SHORT).show();
            return;
        }
        qty = qty + 1;
        displayQuanity(qty);
    }

    /**
     * This method is called when the minus button is clicked.
     */
    public void decrement(View view) {
        if (qty == 1) {
            /** display toast message when qty is less than 1*/
            Toast.makeText(this, "You cannot order less than 1 coffee.", Toast.LENGTH_SHORT).show();
            return;
        }
        qty = qty - 1;
        displayQuanity(qty);
    }

    /**
     * This method is called when the order button is clicked.
     * Includes name, if toppings were added or not, qty, price, and end greeting)
     */
    public void submitOrder(View view) {
        EditText nameField = (EditText) findViewById(R.id.name_field);
        String name = nameField.getText().toString();
        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();
        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate2_checkbox);
        boolean hasChocolate = chocolateCheckBox.isChecked();
        CheckBox caramelCheckBox = (CheckBox) findViewById(R.id.caramel2_checkbox);
        boolean hasCaramel = caramelCheckBox.isChecked();
        int price = calculatePrice(hasWhippedCream, hasChocolate, hasCaramel);
        String priceMessage = createOrderSummary(price, name, hasWhippedCream, hasChocolate, hasCaramel);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just Java Order for" + name);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    /**
     * This method displays the given quantity value on the screen.
     * It has been updated to include topping costs if added.
     */

    private int calculatePrice(boolean addWhippedCream, boolean addChocolate, boolean addCaramel) {
        int basePrice = 5;

        if (addWhippedCream) {
            basePrice = basePrice + 1;
        }
        if (addChocolate) {
            basePrice = basePrice + 2;
        }
        if (addCaramel) {
            basePrice = basePrice + 3;
        }
        return qty * basePrice;
    }

    /**
     * @param price           of the order
     * @param addWhippedCream whether or not user wants whipped cream topping
     * @return text summary
     */
    private String createOrderSummary(int price, String name, boolean addWhippedCream, boolean addChocolate, boolean addCaramel) {
        String priceMessage = "Name: " + name;
        priceMessage += "\n" + "Add Whipped Cream? " + addWhippedCream;
        priceMessage += "\n" + "Add Chocolate? " + addChocolate;
        priceMessage += "\n" + "Add Caramel? " + addCaramel;
        priceMessage = priceMessage + "\n" + "Quanity: " + qty;
        priceMessage = priceMessage + "\n"+ "Total = $" + calculatePrice(addWhippedCream, addChocolate, addCaramel);
        priceMessage = priceMessage + "\n" + "That completes your order!";
        return priceMessage;
    }


    private void displayQuanity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quanity_text_view);
        quantityTextView.setText("" + number);
    }
    /**
     * This method displays the given price on the screen.
     */


}

