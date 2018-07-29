package com.hecticus.eleta.model_new.persistence;

import android.util.Log;

import com.hecticus.eleta.model.request.invoice.InvoicePost;
import com.hecticus.eleta.model.request.invoice.ItemPost;
import com.hecticus.eleta.model.request.invoice.PurityPost;
import com.hecticus.eleta.model.response.farm.Farm;
import com.hecticus.eleta.model.response.harvest.HarvestOfDay;
import com.hecticus.eleta.model.response.invoice.Invoice;
import com.hecticus.eleta.model.response.invoice.InvoiceDetailPurity;
import com.hecticus.eleta.model.response.invoice.InvoiceDetails;
import com.hecticus.eleta.model.response.item.ItemType;
import com.hecticus.eleta.model.response.lot.Lot;
import com.hecticus.eleta.model.response.providers.Provider;
import com.hecticus.eleta.model.response.purity.Purity;
import com.hecticus.eleta.model.response.store.Store;
import com.hecticus.eleta.util.Constants;

import java.util.ArrayList;
import java.util.List;

import hugo.weaving.DebugLog;
import io.realm.Case;
import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

public class DB {
}