package fr.lr.iut.transportresolver.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ListView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import fr.lr.iut.transportresolver.R;
import fr.lr.iut.transportresolver.adapters.TransportAdapter;
import fr.lr.iut.transportresolver.models.Transport;

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
