package fr.lr.iut.transportresolver.adapters.viewsHolder;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import fr.lr.iut.transportresolver.R;
import fr.lr.iut.transportresolver.models.Transport;


/**
 * The Transport Adapter View holder
 *
 * @author Daniel Medina
 * @since /12/2017
 */

public class TransportHolder {

    @BindView(R.id.imv_directions_bus)
    ImageView imvDirectionsBus;
    @BindView(R.id.txv_number)
    TextView txvNumber;
    @BindView(R.id.txv_capacity)
    TextView txvCapacity;
    @BindView(R.id.imv_color)
    ImageView imvColor;

    public TransportHolder(View itemView) {
        ButterKnife.bind(this, itemView);
    }

    public void bind(Context context, Transport transport) {
        PorterDuffColorFilter porterDuffColorFilter = new PorterDuffColorFilter(Color.parseColor(transport.getColor()),
                PorterDuff.Mode.SRC_ATOP);

        imvDirectionsBus.setColorFilter(porterDuffColorFilter);
        txvNumber.setText("Number : " + transport.getNumber());
        txvCapacity.setText("Capacity : " + transport.getCapacity());
        imvColor.setColorFilter(porterDuffColorFilter);
    }
}
