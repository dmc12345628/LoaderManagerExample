package fr.lr.iut.transportresolver.providers;

import android.net.Uri;

/**
 * The Providers constants
 *
 * @author Daniel Medina
 * @since 07/12/2017
 */

public abstract class PC {
    public static abstract class Transport {
        public static final String
                PROVIDER_NAME = "fr.lr.iut.transportprovider.providers.Transport",
                URL = "content://" + PROVIDER_NAME + "/transports";
        public static final Uri TRANSPORT_ITEM = Uri.parse(URL);
        public static final Uri TRANSPORT_DIR = Uri.parse(URL + "/*");
    }
}
