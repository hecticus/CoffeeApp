package com.hecticus.eleta.model_new.persistence;

import com.hecticus.eleta.util.Constants;

import io.realm.DynamicRealm;
import io.realm.RealmMigration;
import io.realm.RealmObjectSchema;
import io.realm.RealmSchema;

public class Migrations implements RealmMigration {

    @Override
    public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {
        final RealmSchema schema = realm.getSchema();

        if (oldVersion == (Constants.VERSION_DB-1)) {
            /*final RealmObjectSchema userSchema = schema.get("Farm");
            userSchema.removeField("a", String.class);
            userSchema.addField("b", String.class);*/


        }
    }


}
