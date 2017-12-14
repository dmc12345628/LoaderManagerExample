package fr.lr.iut.transportresolver.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import butterknife.BindView;
import butterknife.ButterKnife;
import fr.lr.iut.transportresolver.R;
import fr.lr.iut.transportresolver.adapters.TransportAdapter;
import fr.lr.iut.transportresolver.models.Transport;

import java.util.ArrayList;

/**
 * The Transports Activity
 *
 * Shows a transports list
 *
 * @author Jesús Daniel Medina Cruz
 * @since 10/12/2017
 */

public class TransportsActivity extends AppCompatActivity {

    public static ArrayList<Transport> TRANSPORTS;

    @BindView(R.id.lsv_transports)
    ListView lstTransports;
    private TransportAdapter transportAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transports);

        ButterKnife.bind(this);

        transportAdapter = new TransportAdapter(this, TRANSPORTS);

        lstTransports.setAdapter(transportAdapter);
    }
}
