package fr.lr.iut.transportresolver.activities;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.thebluealliance.spectrum.SpectrumDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import fr.lr.iut.transportresolver.R;
import fr.lr.iut.transportresolver.models.DBC;
import fr.lr.iut.transportresolver.models.DBHelper;
import fr.lr.iut.transportresolver.models.Transport;
import fr.lr.iut.transportresolver.providers.PC;

/**
 * The Transports Activity
 * <p>
 * Shows a transports list
 *
 * @author Jesús Daniel Medina Cruz
 * @since 10/12/2017
 */

public class TransportActivity extends AppCompatActivity {

    // widgets
    // TextInputLayouts
    @BindView(R.id.til_number)
    TextInputLayout tilNumber;
    @BindView(R.id.til_capacity)
    TextInputLayout tilCapacity;
    @BindView(R.id.til_color)
    TextInputLayout tilColor;
    @BindView(R.id.edt_number)
    EditText edtNumber;
    @BindView(R.id.edt_capacity)
    EditText edtCapacity;
    @BindView(R.id.edt_color)
    EditText edtColor;

    private int reqType;
    private int transportId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        reqType = getIntent().getIntExtra(MainActivity.REQ_TYPE,
                MainActivity.ADD_TRANSPORT_REQ);

        setContentView(R.layout.activity_transport);

        ButterKnife.bind(this);

        if (reqType == MainActivity.UPDATE_TRANSPORT_REQ) {
            transportId = getIntent().getIntExtra(MainActivity.TRANSPORT_ID, 1);
            init(transportId);
        }

        addTextWatchers();
    }

    private void init(int transportId) {
        Cursor c = DBHelper.getInstance(this).transportDao().selectById(transportId);
        c.moveToFirst();
        Transport transport = new Transport(c);

        edtNumber.setText(transport.number);
        edtCapacity.setText("" + transport.capacity);
        edtColor.setText(transport.color);
    }

    private void addTextWatchers() {
        edtNumber.addTextChangedListener(new ValidationTextWatcher(edtNumber));
        edtCapacity.addTextChangedListener(new ValidationTextWatcher(edtCapacity));
        edtColor.addTextChangedListener(new ValidationTextWatcher(edtColor));
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

    @OnClick(R.id.imb_back)
    public void goBakc() {
        finish();
    }

    @OnClick(R.id.imb_save)
    public void addTransport() {
        if (isValidTransport()) {
            ContentValues contentValues = new ContentValues();

            contentValues.put(DBC.Transport.NUMBER, edtNumber.getText().toString());
            contentValues.put(DBC.Transport.CAPACITY, edtCapacity.getText().toString());
            contentValues.put(DBC.Transport.COLOR, edtColor.getText().toString());

            if (reqType == MainActivity.ADD_TRANSPORT_REQ) {
                Uri uri = getContentResolver().insert(PC.Transport.TRANSPORT_ITEM, contentValues);

                Toast.makeText(this, "Transport App : " +
                        uri.toString() + " inserted", Toast.LENGTH_SHORT).show();
            } else {
                contentValues.put(DBC.Transport.ID, transportId);

                getContentResolver().update(PC.Transport.TRANSPORT_ITEM, contentValues, "", new String[]{});
            }

            setResult(RESULT_OK);

            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
    }

    // methods
    private boolean isValidTransport() {
        if (!isValidNumber())
            return false;
        if (!isValidCapacity())
            return false;
        if (!isValidColor())
            return false;

        return true;
    }

    private boolean isValidNumber() {
        String number = edtNumber.getText().toString().trim();

        if (number.isEmpty()) {
            tilNumber.setError("Write a transport number");
            requestFocus(edtNumber);
            return false;
        } else {
            tilNumber.setErrorEnabled(false);
        }

        return true;
    }

    private boolean isValidCapacity() {
        String capacity = edtCapacity.getText().toString().trim();

        if (capacity.isEmpty()) {
            tilCapacity.setError("Write a transport capacity");
            requestFocus(edtCapacity);
            return false;
        } else {
            tilCapacity.setErrorEnabled(false);
        }

        return true;
    }

    private boolean isValidColor() {
        String color = edtColor.getText().toString().trim();

        if (color.isEmpty()) {
            tilColor.setError("Write a transport color");
            requestFocus(edtColor);
            return false;
        } else {
            tilColor.setErrorEnabled(false);
        }

        return true;
    }

    private void requestFocus(View v) {
        if (v.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private class ValidationTextWatcher implements TextWatcher {

        private View v;

        private ValidationTextWatcher(View v) {
            this.v = v;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            switch (v.getId()) {
                case R.id.edt_number:
                    isValidNumber();
                    break;
                case R.id.edt_capacity:
                    isValidCapacity();
                    break;
                case R.id.edt_color:
                    isValidColor();
                    break;
            }
        }
    }
}
