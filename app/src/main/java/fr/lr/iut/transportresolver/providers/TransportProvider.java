package fr.lr.iut.transportresolver.providers;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import fr.lr.iut.transportresolver.models.DBC;
import fr.lr.iut.transportresolver.models.DBHelper;
import fr.lr.iut.transportresolver.models.Transport;
import fr.lr.iut.transportresolver.models.TransportDao;

import static fr.lr.iut.transportresolver.providers.PC.Transport.PROVIDER_NAME;
import static fr.lr.iut.transportresolver.providers.PC.Transport.TRANSPORT_DIR_MATCHER;
import static fr.lr.iut.transportresolver.providers.PC.Transport.TRANSPORT_ITEM_MATCHER;

/**
 * The content provider allow another apps to access to database
 *
 * @author Jesús Daniel Medina Cruz
 * @since 07/12/2017
 */

public class TransportProvider extends ContentProvider {

    // The URI matcher
    private static final UriMatcher MATCHER = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        MATCHER.addURI(PROVIDER_NAME, DBC.Transport.TABLE_NAME + "/*", TRANSPORT_DIR_MATCHER);
        MATCHER.addURI(PROVIDER_NAME, DBC.Transport.TABLE_NAME, TRANSPORT_ITEM_MATCHER);
    }

    @Override
    public boolean onCreate() {
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection,
                        @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        final int code = MATCHER.match(uri);
        if (code == TRANSPORT_DIR_MATCHER || code == TRANSPORT_ITEM_MATCHER) {
            final Context context = getContext();
            if (context == null) {
                return null;
            }
            TransportDao transportDao = DBHelper.getInstance(context).transportDao();
            final Cursor cursor;
            if (code == TRANSPORT_DIR_MATCHER) {
                cursor = transportDao.selectAll();
            } else {
                cursor = transportDao.selectById(Integer.parseInt(uri.getLastPathSegment()));
            }

            cursor.setNotificationUri(getContext().getContentResolver(), uri);
            return cursor;
        } else {
            throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (MATCHER.match(uri)) {
            case TRANSPORT_DIR_MATCHER:
                return "vnd.android.cursor.dir/" + PROVIDER_NAME + "." + DBC.Transport.TABLE_NAME;
            case TRANSPORT_ITEM_MATCHER:
                return "vnd.android.cursor.item/" + PROVIDER_NAME + "." + DBC.Transport.TABLE_NAME;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        switch (MATCHER.match(uri)) {
            case TRANSPORT_DIR_MATCHER:
                throw new IllegalArgumentException("Invalid URI, cannot insert: " + uri);
            case TRANSPORT_ITEM_MATCHER:
                final Context context = getContext();
                if (context == null) {
                    return null;
                }
                Transport transport = new Transport(contentValues);
                final long id = DBHelper.getInstance(context).transportDao().insert(transport);
                context.getContentResolver().notifyChange(uri, null);
                return ContentUris.withAppendedId(uri, id);
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String where, @Nullable String[] selectionArgs) {
        final Context context = getContext();
        int count = 0;
        switch (MATCHER.match(uri)) {
            case TRANSPORT_DIR_MATCHER:
                if (context == null) {
                    return 0;
                }
                count = DBHelper.getInstance(context).transportDao()
                        .deleteAll();
                context.getContentResolver().notifyChange(uri, null);
                return count;
            case TRANSPORT_ITEM_MATCHER:
                if (context == null) {
                    return 0;
                }
                count = DBHelper.getInstance(context).transportDao()
                        .deleteById(where);
                context.getContentResolver().notifyChange(uri, null);
                return count;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        switch (MATCHER.match(uri)) {
            case TRANSPORT_DIR_MATCHER:
                throw new IllegalArgumentException("Invalid URI, cannot update: " + uri);
            case TRANSPORT_ITEM_MATCHER:
                final Context context = getContext();
                if (context == null) {
                    return 0;
                }
                final Transport transport = new Transport(contentValues);
                final int count = DBHelper.getInstance(context).transportDao()
                        .update(transport);
                return count;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }
}
