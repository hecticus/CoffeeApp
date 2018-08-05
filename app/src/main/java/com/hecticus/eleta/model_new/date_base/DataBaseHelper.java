package com.hecticus.eleta.model_new.date_base;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.hecticus.eleta.model_new.Farm;
import com.hecticus.eleta.model_new.Invoice;
import com.hecticus.eleta.model_new.InvoiceDetail;
import com.hecticus.eleta.model_new.InvoiceDetailPurity;
import com.hecticus.eleta.model_new.ItemType;
import com.hecticus.eleta.model_new.Lot;
import com.hecticus.eleta.model_new.MultimediaCDN;
import com.hecticus.eleta.model_new.MultimediaProfile;
import com.hecticus.eleta.model_new.Provider;
import com.hecticus.eleta.model_new.ProviderType;
import com.hecticus.eleta.model_new.Purity;
import com.hecticus.eleta.model_new.Store;
import com.hecticus.eleta.model_new.Unit;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;

public class DataBaseHelper extends OrmLiteSqliteOpenHelper {

    //todo control de version para produccion
    //private static final String DATABASE_NAME ="fituservice.db";
    //private static final int DATABASE_VERSION = 1;

    //todo control de version para desarrollo
    private static final String DATABASE_NAME ="coffe_eleta.db";
    private static final int DATABASE_VERSION = 1;


    private Dao<Provider, Long> providerDAO = null;
    private Dao<ProviderType, Long> providerTypeDAO = null;
    private Dao<Farm, Long> farmDAO = null;
    private Dao<Invoice, Long> invoiceDAO = null;
    private Dao<InvoiceDetail, Long> invoiceDetailDAO = null;
    private Dao<InvoiceDetailPurity, Long> invoiceDetailPurityDAO = null;
    private Dao<ItemType, Long> itemTypeDAO = null;
    private Dao<Lot, Long> lotDAO = null;
    private Dao<MultimediaCDN, Long> multimediaCDNDAO = null;
    private Dao<MultimediaProfile, Long> DAO = null;
    private Dao<Purity, Long> purityDAO = null;
    private Dao<Store, Long> storeDAO = null;
    private Dao<Unit, Long> unitDAO = null;






    public DataBaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION/*, R.raw.ormlite_config*/);
    }


    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {

    }
}
