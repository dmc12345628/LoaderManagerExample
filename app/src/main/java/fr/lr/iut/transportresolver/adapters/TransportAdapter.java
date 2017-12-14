package fr.lr.iut.transportresolver.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

import fr.lr.iut.transportresolver.R;
import fr.lr.iut.transportresolver.adapters.viewsHolder.TransportHolder;
import fr.lr.iut.transportresolver.models.Transport;

/**
 * The Transport RecyclerView adapter
 *
 * @author Daniel Medina
 * @since 10/12/2017
 */

public class TransportAdapter extends BaseAdapter {

    private Context context;
    LayoutInflater inflater;
    private ArrayList<Transport> transports;

    public TransportAdapter(Context context, ArrayList<Transport> transports) {
        this.context = context;
        this.transports = transports;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return transports.size();
    }

    @Override
    public Object getItem(int position) {
        return transports.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View v, ViewGroup viewGroup) {
        final TransportHolder transportHolder;
        final Transport transport = transports.get(position);

        if (v == null) {
            v = inflater.inflate(R.layout.list_row_transport, null);
            transportHolder = new TransportHolder(v);
        } else {
            transportHolder = (TransportHolder) v.getTag();
        }

        transportHolder.bind(context, transport);

        v.setTag(transportHolder);
        return v;
    }
}

