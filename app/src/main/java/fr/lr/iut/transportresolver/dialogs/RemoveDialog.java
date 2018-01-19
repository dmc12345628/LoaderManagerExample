package fr.lr.iut.transportresolver.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import fr.lr.iut.transportresolver.R;

/**
 * RemoveDialog is used to authenticate user before user modifications
 *
 * @author Jesus Daniel Medina Cruz
 * @since 05/01/2018
 */

public class RemoveDialog extends Dialog {

    // widgets
    private Button btnRemove, btnCancel;

    public RemoveDialog(@NonNull Context context) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_remove);
        setCancelable(false);

        init();
    }

    private void init() {
        btnRemove = findViewById(R.id.btn_remove);
        btnCancel = findViewById(R.id.btn_cancel);
        setRemoveAction(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        setCancelAction(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }

    // setters
    public void setRemoveAction(View.OnClickListener listener) {
        btnRemove.setOnClickListener(listener);
    }

    public void setCancelAction(View.OnClickListener listener) {
        btnCancel.setOnClickListener(listener);
    }
}
