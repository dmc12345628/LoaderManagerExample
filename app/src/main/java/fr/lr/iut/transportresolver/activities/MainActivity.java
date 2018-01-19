package fr.lr.iut.transportresolver.activities;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import fr.lr.iut.transportresolver.R;
import fr.lr.iut.transportresolver.adapters.TransportCursorAdapter;
import fr.lr.iut.transportresolver.dialogs.RemoveDialog;
import fr.lr.iut.transportresolver.models.DBC;
import fr.lr.iut.transportresolver.models.Transport;
import fr.lr.iut.transportresolver.providers.PC;

/**
 * The Main Activity
 *
 * @author Jesús Daniel Medina Cruz
 * @since 10/12/2017
 */

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    final static String TAG = "MainActivity";
    public final static int ADD_TRANSPORT_REQ = 100;
    public final static int UPDATE_TRANSPORT_REQ = 101;
    public final static String REQ_TYPE = "req_type";
    public final static String TRANSPORT_ID = "transport_id";
    private static final int CUR_LOADER = 1;

    @BindView(R.id.lsv_transports)
    ListView lstTransports;
    private TransportCursorAdapter transportAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        transportAdapter = new TransportCursorAdapter(this, null);

        lstTransports.setAdapter(transportAdapter);

        getLoaderManager().initLoader(CUR_LOADER, null, this);
    }

    public void showTransportActivity(Transport transport) {
        Intent intent = new Intent(this,
                TransportActivity.class);
        intent.putExtra(REQ_TYPE, UPDATE_TRANSPORT_REQ);
        intent.putExtra(TRANSPORT_ID, transport.id);
        startActivityForResult(intent, UPDATE_TRANSPORT_REQ);
    }

    public void showRemoveDialog(final Transport transport) {
        final RemoveDialog dialog = new RemoveDialog(this);
        dialog.setRemoveAction(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getContentResolver().delete(PC.Transport.TRANSPORT_ITEM, "" + transport.id, null);
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    @OnClick(R.id.btn_add_a_new_transport)
    public void addANewTransport() {
        Intent intent = new Intent(this,
                TransportActivity.class);
        intent.putExtra(REQ_TYPE, ADD_TRANSPORT_REQ);

        startActivityForResult(intent, ADD_TRANSPORT_REQ);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_TRANSPORT_REQ) {
            if (resultCode == RESULT_OK) {
            } else if (resultCode == RESULT_CANCELED) {
            }
        } else if (requestCode == UPDATE_TRANSPORT_REQ) {
            if (resultCode == RESULT_OK) {
            } else if (resultCode == RESULT_CANCELED) {
            }
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int loaderId, Bundle bundle) {
        switch (loaderId) {
            case CUR_LOADER:
                CursorLoader cursorLoader = new CursorLoader(this,
                        PC.Transport.TRANSPORT_DIR,
                        DBC.Transport.PROJECTION,
                        null, null, null);
                return cursorLoader;
            default:
                return null;
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor c) {
        transportAdapter.changeCursor(c);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        transportAdapter.changeCursor(null);
    }
}
