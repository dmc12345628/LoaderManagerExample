package fr.lr.iut.transportresolver.activities;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.thebluealliance.spectrum.SpectrumDialog;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import fr.lr.iut.transportresolver.R;
import fr.lr.iut.transportresolver.models.Transport;
import fr.lr.iut.transportresolver.providers.PC;

/**
 * The Main Activity
 *
 * @author Daniel Medina
 * @since 10/12/2017
 */

public class MainActivity extends AppCompatActivity {

    // widgets
    @BindView(R.id.edt_number)
    EditText edtNumber;
    @BindView(R.id.edt_capacity)
    EditText edtCapacity;
    @BindView(R.id.edt_color)
    EditText edtColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
    }

    @OnClick(R.id.edt_color)
    public void showColorPicker() {
        SpectrumDialog.Builder colorPicker = new SpectrumDialog.Builder(this);
        colorPicker.setColors(R.array.md_colors);
        colorPicker.setSelectedColorRes(R.color.md_blue_500);
        colorPicker.setOnColorSelectedListener(new SpectrumDialog.OnColorSelectedListener() {
            @Override
            public void onColorSelected(boolean positiveResult, int color) {
                edtColor.setText("#" + Integer.toHexString(color));
            }
        });
        colorPicker.build().show(getSupportFragmentManager(), "Color Picker");
    }

    // Declaration des méthodes pour accéder au fourniseur TransportProvider
    @OnClick(R.id.btn_add_a_new_transport)
    public void addTransport() {
        ContentValues contentValues = new ContentValues();

        contentValues.put("number", edtNumber.getText().toString());
        contentValues.put("capacity", edtCapacity.getText().toString());
        contentValues.put("color", edtColor.getText().toString());

        Uri uri = getContentResolver().insert(PC.Transport.TRANSPORT_ITEM, contentValues);

        Snackbar.make(findViewById(android.R.id.content), "Transport App : " +
                uri.toString() + " inserted", Toast.LENGTH_SHORT).show();

        edtNumber.setText("");
        edtCapacity.setText("");
        edtColor.setText("");
    }

    @OnClick(R.id.btn_show_all_transports)
    public void showAllTransports() {
        Cursor cursor = getContentResolver().query(PC.Transport.TRANSPORT_DIR, null, null, null, "number");
        String result = "Transport App Results: ";

        TransportsActivity.TRANSPORTS = new ArrayList<>();
        if (!cursor.moveToFirst())
            Snackbar.make(findViewById(android.R.id.content), result + " no content yet!",
                    Toast.LENGTH_SHORT).show();
        else
            do {
                String number = cursor.getString(0);
                int capacity = cursor.getInt(1);
                String color = cursor.getString(2);

                Transport transport = new Transport(number, capacity, color);
                TransportsActivity.TRANSPORTS.add(transport);
            } while (cursor.moveToNext());

        Intent intent = new Intent(this, TransportsActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.btn_delete_all_transports)
    public void deleteAllTransports() {
        int count = getContentResolver().delete(PC.Transport.TRANSPORT_DIR, null, null);
        String countNum = "Transport App : " + count + " records were deleted.";
        Snackbar.make(findViewById(android.R.id.content), countNum, Toast.LENGTH_SHORT).show();
    }
}
