package fr.lr.iut.transportresolver.adapters;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CursorAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

import fr.lr.iut.transportresolver.R;
import fr.lr.iut.transportresolver.activities.MainActivity;
import fr.lr.iut.transportresolver.adapters.viewsHolder.TransportHolder;
import fr.lr.iut.transportresolver.models.Transport;

/**
 * The Transport RecyclerView adapter
 *
 * @author Jesús Daniel Medina Cruz
 * @since 10/12/2017
 */

public class TransportCursorAdapter extends CursorAdapter {

    private MainActivity mainActivity;

    public TransportCursorAdapter(MainActivity mainActivity, Cursor c) {
        super(mainActivity, c, 0);
        this.mainActivity = mainActivity;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_row_transport, parent, false);
    }

    @Override
    public void bindView(final View v, Context context, Cursor cursor) {
        final Transport transport = new Transport(cursor);

        PorterDuffColorFilter porterDuffColorFilter = new PorterDuffColorFilter(Color.parseColor(transport.color),
                PorterDuff.Mode.SRC_ATOP);
        ImageView imvDirectionsBus = (ImageView) v.findViewById(R.id.imv_directions_bus);
        imvDirectionsBus.setColorFilter(porterDuffColorFilter);
        TextView txvNumber = (TextView) v.findViewById(R.id.txv_number);
        txvNumber.setText("Number : " + transport.number);
        TextView txvCapacity = (TextView) v.findViewById(R.id.txv_capacity);
        txvCapacity.setText("Capacity : " + transport.capacity);
        ImageView imvColor = (ImageView) v.findViewById(R.id.imv_color);
        imvColor.setColorFilter(porterDuffColorFilter);
        ImageButton imbMore = (ImageButton) v.findViewById(R.id.imb_more);
        imbMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(mainActivity, v);
                popupMenu.getMenuInflater().inflate(R.menu.menu_transport, popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        int id = menuItem.getItemId();
                        if (id == R.id.ite_edit) {
                            mainActivity.showTransportActivity(transport);
                        } else if (id == R.id.ite_delete) {
                            mainActivity.showRemoveDialog(transport);
                        }
                        return false;
                    }
                });

                popupMenu.show();
            }
        });

        v.setTag(transport);
    }
}
