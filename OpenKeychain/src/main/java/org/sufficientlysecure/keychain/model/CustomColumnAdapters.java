package org.sufficientlysecure.keychain.model;


import java.util.Date;

import android.support.annotation.NonNull;

import com.squareup.sqldelight.ColumnAdapter;
import org.sufficientlysecure.keychain.model.AutocryptPeer.GossipOrigin;



public final class CustomColumnAdapters {

    private CustomColumnAdapters() { }

    static final ColumnAdapter<Date,Long> DATE_ADAPTER = new ColumnAdapter<Date,Long>() {
        @NonNull
        @Override
        public Date decode(Long databaseValue) {
            // Both SQLite and OpenPGP prefer a second granularity for timestamps - so we'll translate here
            return new Date(databaseValue * 1000);
        }

        @Override
        public Long encode(@NonNull Date value) {
            return value.getTime() / 1000;
        }
    };

    static final ColumnAdapter<GossipOrigin,Long> GOSSIP_ORIGIN_ADAPTER = new ColumnAdapter<GossipOrigin,Long>() {
        @NonNull
        @Override
        public GossipOrigin decode(Long databaseValue) {
            switch (databaseValue.intValue()) {
                case 0: return GossipOrigin.GOSSIP_HEADER;
                case 10: return GossipOrigin.SIGNATURE;
                case 20: return GossipOrigin.DEDUP;
                default: throw new IllegalArgumentException("Unhandled database value!");
            }
        }

        @Override
        public Long encode(@NonNull GossipOrigin value) {
            switch (value) {
                case GOSSIP_HEADER: return 0L;
                case SIGNATURE: return 10L;
                case DEDUP: return 20L;
                default: throw new IllegalArgumentException("Unhandled database value!");
            }
        }
    };
}
