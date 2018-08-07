package com.hecticus.eleta.model_new.persistence;

import android.util.Log;

import com.google.gson.Gson;
import com.hecticus.eleta.model.request.invoice.InvoicePost;
import com.hecticus.eleta.model.request.invoice.ItemPost;
import com.hecticus.eleta.model.request.invoice.PurityPost;
import com.hecticus.eleta.model.response.farm.Farm;
import com.hecticus.eleta.model.response.invoice.Invoice;
import com.hecticus.eleta.model.response.invoice.InvoiceDetailPurity;
import com.hecticus.eleta.model.response.invoice.InvoiceDetails;
import com.hecticus.eleta.model.response.item.ItemType;
import com.hecticus.eleta.model.response.lot.Lot;
import com.hecticus.eleta.model.response.providers.Provider;
import com.hecticus.eleta.model.response.purity.Purity;
import com.hecticus.eleta.model.response.store.Store;
import com.hecticus.eleta.model_new.InvoiceDetail;
import com.hecticus.eleta.util.Constants;

import java.util.ArrayList;
import java.util.List;

import hugo.weaving.DebugLog;
import io.realm.Case;
import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

/**
 * Created by roselyn545 on 20/10/17.
 */

public class ManagerDB {

    @DebugLog
    public static boolean saveNewProvider(final Provider provider) {
        Realm realm = Realm.getDefaultInstance();

        if (provider.getIdProvider() == null) {
            Number minExistingProviderLocalId = realm.where(Provider.class).min("idProvider");

            int nextExistingInvoicePostId =
                    (minExistingProviderLocalId == null || minExistingProviderLocalId.intValue() >= 0) ? -1 :
                            minExistingProviderLocalId.intValue() - 1;

            provider.setIdProvider(nextExistingInvoicePostId);

            Log.d("BUG", "--->saveNewProvider patched local id:" + provider.getIdProvider());
        } else
            Log.d("BUG", "--->saveNewProvider already good id:" + provider.getIdProvider());

        try {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    /*Gson g = new Gson();
                    Log.d("DEBUG", g.toJson(provider));*/
                    realm.insertOrUpdate(provider);
                    Log.d("BUG", "--->saveNewProvider " + provider);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            realm.close();
            return false;
        }
        realm.close();
        return true;
    }

    @DebugLog
    public static boolean updateExistingProvider(final Provider provider) {

        Realm realm = Realm.getDefaultInstance();
        try {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    Provider existingProvider = realm.where(Provider.class).equalTo("idProvider", provider.getIdProvider()).findFirst();
                    if (existingProvider != null && !provider.getIdentificationDocProvider().equals(existingProvider.getIdentificationDocProvider())) {
                        existingProvider.deleteFromRealm();
                        Log.d("TEST", "--->deleteFromRealm in updateExistingProvider");
                    } else
                        Log.d("TEST", "--->NOT deleteFromRealm in updateExistingProvider");

                    realm.insertOrUpdate(provider);
                }
            });
        } catch (Exception e) {
            realm.close();
            return false;
        }
        realm.close();
        return true;
    }

    @DebugLog
    public static boolean updateStatusInvoice(final Invoice invoice) {

        Realm realm = Realm.getDefaultInstance();
        try {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    Invoice existing = realm.where(Invoice.class).equalTo("id", invoice.getInvoiceId()).findFirst();
                    if (existing != null && invoice.getInvoiceId()!=existing.getInvoiceId()) {
                        existing.deleteFromRealm();
                        Log.d("TEST", "--->deleteFromRealm in updateExistingProvider");
                    } else
                        Log.d("TEST", "--->NOT deleteFromRealm in updateExistingProvider");
                    invoice.setStatusInvo("Cerrada");
                    invoice.setClosed(true);
                    realm.insertOrUpdate(invoice);
                }
            });
        } catch (Exception e) {
            realm.close();
            return false;
        }
        realm.close();
        return true;
    }

    @DebugLog
    public static boolean deleteProvider(final Provider provider) {
        Realm realm = Realm.getDefaultInstance();
        try {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    if (provider.getIdProvider() < 0) {
                        Provider providerToDelete = realm.where(Provider.class).equalTo("unixtime", provider.getUnixtime()).findFirst();
                        if (providerToDelete != null) {
                            Log.d("TEST", "dni provider " + provider.getIdentificationDocProvider() + " dni saved" + providerToDelete.getIdentificationDocProvider());
                            providerToDelete.deleteFromRealm();
                            return;
                        }
                        providerToDelete = realm.where(Provider.class).equalTo("identificationDocProvider", provider.getIdentificationDocProvider()).findFirst();
                        if (providerToDelete != null) {
                            Log.d("TEST", "dni provider " + provider.getIdentificationDocProvider() + " dni saved" + providerToDelete.getIdentificationDocProvider());
                            providerToDelete.deleteFromRealm();
                            return;
                        }
                    } else {
                        provider.setDeleteOffline(true);
                        if (provider.getUnixtime() == -1)
                            provider.setUnixtime(System.currentTimeMillis() / 1000L);

                        realm.insertOrUpdate(provider);
                    }
                    Log.d("TEST", "isSaved ");
                }
            });
        } catch (Exception e) {
            realm.close();
            return false;
        }
        realm.close();
        return true;
    }

    @DebugLog
    public static List<Provider> getAllProvidersByType(int type) {
        return Realm.getDefaultInstance()
                .where(Provider.class)
                .equalTo("deleteOffline", false)
                .equalTo("idProviderType", type)
                .findAllSorted("fullNameProvider");
    }

    @DebugLog
    public static InvoiceDetails getInvoiceDetailById(int id) {
        return Realm.getDefaultInstance()
                .where(InvoiceDetails.class)
                .equalTo("id", id).findFirst();
    }

    @DebugLog
    public static List<InvoiceDetails> getInvoiceDetailsByInvoice(int invoice) {
        return Realm.getDefaultInstance()
                .where(InvoiceDetails.class)
                .equalTo("invoiceId", invoice)
                .findAllSorted("receiverName");
    }

    @DebugLog
    public static List<Provider> searchProvidersByTypeAndText(int type, String text) {
        ArrayList<Provider> finalProviderList = new ArrayList<>();
        List<Provider> addProviderList = Realm.getDefaultInstance().where(Provider.class).equalTo("deleteOffline", false).equalTo("idProviderType", type).contains("fullNameProvider", text, Case.INSENSITIVE).findAllSorted("fullNameProvider");
        finalProviderList.addAll(addProviderList);
        List<Provider> addProviderList2 = Realm.getDefaultInstance().where(Provider.class).equalTo("deleteOffline", false).equalTo("idProviderType", type).contains("identificationDocProvider", text, Case.INSENSITIVE).findAllSorted("fullNameProvider");
        finalProviderList.addAll(addProviderList2);

        return finalProviderList;
    }

    @DebugLog
    public static List<Provider> mixAndGetValids(int type, List<Provider> providerList, String text) {
        ArrayList<Provider> finalProviderList = new ArrayList<>(providerList);
        List<Provider> deletedProviderList = Realm.getDefaultInstance().where(Provider.class).equalTo("deleteOffline", true).equalTo("idProviderType", type).findAllSorted("fullNameProvider");
        for (Provider provider : deletedProviderList) {
            if (provider.getIdProvider() > -1) {
                int ind = provider.indexByIdIn(finalProviderList);
                if (ind != -1) {
                    finalProviderList.remove(ind);
                }
            }
        }
        List<Provider> editProviderList = Realm.getDefaultInstance().where(Provider.class).equalTo("deleteOffline", false).equalTo("editOffline", true).equalTo("addOffline", false).equalTo("idProviderType", type).findAllSorted("fullNameProvider");
        for (Provider provider : editProviderList) {
            if (provider.getIdProvider() > -1) {
                int ind = provider.indexByIdIn(finalProviderList);
                if (ind != -1) {
                    if (provider.getFullNameProvider().toLowerCase().contains(text)) {
                        finalProviderList.set(ind, provider);
                    } else {
                        finalProviderList.remove(ind);
                    }
                }
            }/* else {
                int ind = provider.indexByUnixtimeIn(finalProviderList);
                if (ind != -1) {
                    if (provider.getFullNameProvider().toLowerCase().contains(text)){
                        finalProviderList.set(ind,provider);
                    } else {
                        finalProviderList.remove(ind);
                    }
                }
            }*/
        }
        List<Provider> addProviderList = Realm.getDefaultInstance().where(Provider.class).equalTo("deleteOffline", false).equalTo("addOffline", true).contains("fullNameProvider", text, Case.INSENSITIVE).equalTo("idProviderType", type).findAllSorted("fullNameProvider");
        finalProviderList.addAll(addProviderList);
        List<Provider> addProviderList2 = Realm.getDefaultInstance().where(Provider.class).equalTo("deleteOffline", false).equalTo("addOffline", true).contains("identificationDocProvider", text, Case.INSENSITIVE).equalTo("idProviderType", type).findAllSorted("fullNameProvider");
        finalProviderList.addAll(addProviderList2);

        return finalProviderList;
    }

    public static List<InvoiceDetails> mixAndGetValidsInvoiceDetails( List<InvoiceDetails> invoiceDetailsList, int idInvoice) {
        ArrayList<InvoiceDetails> finalInvoiceDetailsList = new ArrayList<>(invoiceDetailsList);

        List<InvoiceDetails> deletedInvoiceDetailsList = Realm.getDefaultInstance().where(InvoiceDetails.class).equalTo("deleteOffline", true).equalTo("invoiceId", idInvoice).findAllSorted("startDate");
        Log.d("DEBUG", "control for delete"+ deletedInvoiceDetailsList.size());
        for (InvoiceDetails invoiceDetails : deletedInvoiceDetailsList) {
            Log.d("DEBUG111", "control for delete"+ deletedInvoiceDetailsList.size());
            if (invoiceDetails.getId() > -1) {
                int ind = invoiceDetails.indexByIdIn(finalInvoiceDetailsList);
                if (ind != -1) {
                    finalInvoiceDetailsList.remove(ind);
                }
            }
        }

        List<InvoiceDetails> editInvoiceDetailsList = Realm.getDefaultInstance().where(InvoiceDetails.class).equalTo("deleteOffline", false).equalTo("editOffline", true).equalTo("addOffline", false).equalTo("invoiceId", idInvoice).findAllSorted("startDate");
        for (InvoiceDetails invoiceDetails : editInvoiceDetailsList) {
            if (invoiceDetails.getId() > -1) {
                int ind = invoiceDetails.indexByIdIn(finalInvoiceDetailsList);
                if (ind != -1) {
                    finalInvoiceDetailsList.set(ind, invoiceDetails);
                }
            }
        }

        List<InvoiceDetails> addedInvoiceDetailsList = Realm.getDefaultInstance().where(InvoiceDetails.class).equalTo("deleteOffline", false).equalTo("addOffline", true).equalTo("invoiceId", idInvoice).findAllSorted("startDate");
        finalInvoiceDetailsList.addAll(addedInvoiceDetailsList);

        return finalInvoiceDetailsList;
    }

    @DebugLog
    public static List<Provider> mixAndGetValids(int type, List<Provider> providerList) {
        ArrayList<Provider> finalProviderList = new ArrayList<>(providerList);

        List<Provider> deletedProviderList = Realm.getDefaultInstance().where(Provider.class).equalTo("deleteOffline", true).equalTo("idProviderType", type).findAllSorted("fullNameProvider");
        for (Provider provider : deletedProviderList) {
            if (provider.getIdProvider() > -1) {
                int ind = provider.indexByIdIn(finalProviderList);
                if (ind != -1) {
                    finalProviderList.remove(ind);
                }
            }
        }

        List<Provider> editProviderList = Realm.getDefaultInstance().where(Provider.class).equalTo("deleteOffline", false).equalTo("editOffline", true).equalTo("addOffline", false).equalTo("idProviderType", type).findAllSorted("fullNameProvider");
        for (Provider provider : editProviderList) {
            if (provider.getIdProvider() > -1) {
                int ind = provider.indexByIdIn(finalProviderList);
                if (ind != -1) {
                    finalProviderList.set(ind, provider);
                }
            }/* else {
                int ind = provider.indexByUnixtimeIn(finalProviderList);
                if (ind != -1) {
                    finalProviderList.set(ind,provider);
                }
            }*/
        }

        List<Provider> addedProvidersList = Realm.getDefaultInstance().where(Provider.class).equalTo("deleteOffline", false).equalTo("addOffline", true).equalTo("idProviderType", type).findAllSorted("fullNameProvider");
        finalProviderList.addAll(addedProvidersList);

        return finalProviderList;
    }

    @DebugLog
    public static void updateProviders(List<Provider> providerList, final int idProviderType) {
        Realm realm = Realm.getDefaultInstance();
        try {
            for (final Provider provider : providerList) {
                Provider saved = realm.where(Provider.class).equalTo("identificationDocProvider", provider.getIdentificationDocProvider()).findFirst();
                if (saved != null && (saved.isDeleteOffline() || saved.isAddOffline() || saved.isEditOffline())){
                    continue;
                }

                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        Log.d("DEBUG", String.valueOf(provider.getProviderType()));
                        provider.setIdProviderType(idProviderType);//(provider.getProviderType().getIdProviderType());
                        realm.insertOrUpdate(provider);

                        //Log.d("Repository", "--->Inserted/Updated provider: " + provider.toString());

                    }
                });
            }
        } finally {
            realm.close();
            List<Provider> providers = getAllProvidersByType(2);
            Log.d("TEST", "--->updateProviders final providers " + providers);
        }
    }

    @DebugLog
    public static boolean saveNewFarms(List<Farm> farmList) {
        if (farmList.isEmpty()) return true;

        Realm realm = Realm.getDefaultInstance();
        try {
            final RealmList<Farm> realmList = new RealmList<>(farmList.toArray(new Farm[farmList.size()]));
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    realm.insertOrUpdate(realmList);
                }
            });
        } catch (Exception e) {
            realm.close();
            return false;
        }
        realm.close();
        return true;
    }

    @DebugLog
    public static List<Farm> getAllFarms() {
        return Realm.getDefaultInstance().where(Farm.class).findAllSorted("name");
    }

    @DebugLog
    public static boolean saveNewLots(List<Lot> lotList) {
        if (lotList.isEmpty()) return true;

        Realm realm = Realm.getDefaultInstance();
        try {
            for (final Lot lot : lotList) {
                if (lot.getFarm() != null) {
                    lot.setFarmId(lot.getFarm().getId());
                }
                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        realm.insertOrUpdate(lot);
                    }
                });
            }
        } catch (Exception e) {
            realm.close();
            return false;
        }
        realm.close();
        return true;
    }

    @DebugLog
    public static List<Lot> getAllLotsByFarm(int farm) {
        return Realm.getDefaultInstance().where(Lot.class).equalTo("farmId", farm).findAllSorted("name");
    }

    @DebugLog
    public static boolean saveNewItemsType(int providerType, List<ItemType> itemTypeList) {
        if (itemTypeList.isEmpty()) return true;

        Realm realm = Realm.getDefaultInstance();
        try {
            for (final ItemType itemType : itemTypeList) {
                itemType.setProviderType(providerType);
                if (itemType.getUnit() != null) {
                    itemType.setUnitName(itemType.getUnit().getName());
                }
                realm.executeTransaction(new Realm.Transaction() {
                    @DebugLog
                    @Override
                    public void execute(Realm realm) {
                        realm.insertOrUpdate(itemType);
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
            realm.close();
            return false;
        }
        realm.close();
        return true;
    }

    @DebugLog
    public static List<ItemType> getAllItemsType(int providerType) {
        return Realm.getDefaultInstance().copyFromRealm(Realm.getDefaultInstance().where(ItemType.class).equalTo("providerType", providerType).findAllSorted("name"));
    }

    @DebugLog
    public static boolean saveNewStores(List<Store> storeList) {
        if (storeList.isEmpty()) return true;

        Realm realm = Realm.getDefaultInstance();
        try {
            final RealmList<Store> realmList = new RealmList<Store>(storeList.toArray(new Store[storeList.size()]));
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    realm.insertOrUpdate(realmList);
                }
            });
        } catch (Exception e) {
            realm.close();
            return false;
        }
        realm.close();
        return true;
    }

    @DebugLog
    public static List<Store> getAllStores() {
        return Realm.getDefaultInstance().where(Store.class).findAllSorted("name");
    }

    @DebugLog
    public static boolean saveNewPurities(List<Purity> purityList) {
        if (purityList.isEmpty()) return true;

        Realm realm = Realm.getDefaultInstance();
        try {
            final RealmList<Purity> realmList = new RealmList<>(purityList.toArray(new Purity[purityList.size()]));
            realm.executeTransaction(new Realm.Transaction() {
                @DebugLog
                @Override
                public void execute(Realm realm) {
                    realm.insertOrUpdate(realmList);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            realm.close();
            return false;
        }
        realm.close();
        return true;
    }

    @DebugLog
    public static List<Purity> getAllPurities() {
        List<Purity> list = Realm.getDefaultInstance()
                .copyFromRealm(Realm.getDefaultInstance()
                        .where(Purity.class)
                        .findAllSorted("name"));

        for (Purity currentPurity : list) {
            currentPurity.setRateValueAndWeightString(null);
        }

        return list;
    }

    @DebugLog
    public static boolean saveNewInvoicesByType(final int providerType, List<Invoice> invoiceList) {
        Realm realm = Realm.getDefaultInstance();

        try {
            final List<Invoice> savedInvoices
                    = realm.where(Invoice.class)
                    .equalTo("type", providerType)
                    .equalTo("addOffline", false)
                    .equalTo("deleteOffline", false)
                    .equalTo("editOffline", false)
                    .equalTo("isClosed", false).findAll();
            if (savedInvoices != null) {
                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        Log.d("BUG", "--->saveNewInvoicesByType // Deleting invoices: " + savedInvoices.size());
                        for (Invoice invoice : savedInvoices) {
                            Log.d("BUG", "--->saveNewInvoicesByType // Deleting invoice: " + invoice);
                            invoice.deleteFromRealm();
                        }
                    }
                });
            } else
                Log.w("BUG", "--->saveNewInvoicesByType // Null invoices list to delete");

        } catch (Exception e) {
            e.printStackTrace();
        }

        try {

            for (final Invoice invoice : invoiceList) {
                Invoice saved = realm.where(Invoice.class).equalTo("id", invoice.getInvoiceId()).findFirst();
                if (saved != null && (saved.isDeleteOffline() || saved.isAddOffline() || saved.isEditOffline() || saved.isClosed()))
                    continue;

                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {

                        invoice.setType(providerType);
                        if (invoice.getProvider() != null) {
                            invoice.setProviderName(invoice.getProvider().getFullNameProvider());
                            invoice.setProviderId(invoice.getProvider().getIdProvider());
                            invoice.setIdentificationDocProvider(invoice.getProvider().getIdentificationDocProvider());
                        }
                        invoice.setStatusInvo(invoice.getInvoiceStatus().getName());
                        invoice.setDate(invoice.getInvoiceStartDate().split(" ")[0]);
                        invoice.setId2(invoice.getInvoiceId() + "-" + invoice.getLocalId());
                        realm.insertOrUpdate(invoice);
                        /*Gson g = new Gson();
                        Log.d("DEBUG", g.toJson(invoice));*/
                        Log.d("Repository", "--->Inserted " + invoice.toString());
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
            realm.close();
            return false;
        }
        realm.close();
        return true;
    }

    @DebugLog
    public static List<Invoice> getAllInvoicesByType(int providerType, String date) {

        return Realm.getDefaultInstance()
                .where(Invoice.class)
                .equalTo("type", providerType)
                .equalTo("date", date)
                .equalTo("deleteOffline", false)
                .findAllSorted("invoiceStartDate");
    }

    @DebugLog
    public static Provider getProviderById(int providerId) {
        return Realm.getDefaultInstance().where(Provider.class).equalTo("idProvider", providerId).findFirst();
    }

    @DebugLog
    public static Provider getProviderByIdentificationDoc(String identificationDoc) {
        return Realm.getDefaultInstance().where(Provider.class).equalTo("identificationDocProvider", identificationDoc).findFirst();
    }

    /*@DebugLog
    public static boolean saveNewHarvestsOrPurchasesOfDayById(final int invoiceId, List<HarvestOfDay> harvestOfDayList) { //aaa
        if (harvestOfDayList == null || harvestOfDayList.isEmpty()) return true;

        Realm realm = Realm.getDefaultInstance();
        try {
            for (final HarvestOfDay harvestOfDay : harvestOfDayList) {
                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        harvestOfDay.setInvoiceId(invoiceId);
                        harvestOfDay.setId(invoiceId + "-" + harvestOfDay.getStartDate());
                        realm.insertOrUpdate(harvestOfDay);
                        Log.d("Repository", "--->saveNewHarvestsOrPurchasesOfDayById Inserted HOD: " + harvestOfDay.toString());
                    }
                });
            }
        } catch (Exception e) {
            realm.close();
            return false;
        }
        realm.close();
        return true;
    }*/

    /*@DebugLog
    public static List<HarvestOfDay> getAllHarvestsOrPurchasesOfDayByInvoice(int invoiceId, int invoiceLocalId) {
        final List<HarvestOfDay> completeList =
                Realm.getDefaultInstance()
                        .where(HarvestOfDay.class)
                        .equalTo("deleteOffline", false)
                        .findAllSorted("startDate");

        Log.d("HOD", "--->getAllHarvestsOrPurchasesOfDayByInvoice ALL HOD IN DB: " + completeList.size());

        if (invoiceId != -1) {
            return Realm.getDefaultInstance()
                    .where(HarvestOfDay.class)
                    .equalTo("invoiceId", invoiceId)
                    .equalTo("deleteOffline", false)
                    .findAllSorted("startDate");
        } else {
            return Realm.getDefaultInstance()
                    .where(HarvestOfDay.class)
                    .equalTo("invoiceId", invoiceLocalId)
                    .equalTo("deleteOffline", false)
                    .findAllSorted("startDate");
        }
    }*/

    @DebugLog
    public static boolean saveDetailsOfInvoice(List<InvoiceDetails> invoiceDetailsList) {
        if (invoiceDetailsList.isEmpty()) return true;

        Realm realm = Realm.getDefaultInstance();
        try {

            for (final InvoiceDetails invoiceDetails : invoiceDetailsList) {
                InvoiceDetails saved;
                if(invoiceDetails.getId() != -1){
                    saved = realm.where(InvoiceDetails.class).equalTo("id", invoiceDetails.getId()).findFirst();//("wholeId", invoiceDetails.getWholeId()).findFirst();
                } else {
                    saved = realm.where(InvoiceDetails.class).equalTo("localId", invoiceDetails.getLocalId()).findFirst();//("wholeId", invoiceDetails.getWholeId()).findFirst();
                }
                //InvoiceDetails saved = realm.where(InvoiceDetails.class).equalTo("id", invoiceDetails.getId()).findFirst();//("wholeId", invoiceDetails.getWholeId()).findFirst();

                Log.d("DEBUG", "wholeId" + String.valueOf(invoiceDetails.getWholeId()));
                if (saved != null && (saved.isDeleteOffline() || saved.isAddOffline() || saved.isEditOffline())) {
                    Log.d("DEBUG", "is delete true");
                    continue;
                }
                Log.d("DEBUG", "is delete false");
                //todo imprimir
                realm.executeTransaction(new Realm.Transaction() {
                    @DebugLog
                    @Override
                    public void execute(Realm realm) {
                        if (invoiceDetails.getInvoice() == null)
                            return;

                        invoiceDetails.setWholeId(invoiceDetails.getId() + "-" + invoiceDetails.getLocalId());
                        Log.d("DEBUG", "save invoiceDetails for");
                        invoiceDetails.setInvoiceId(invoiceDetails.getInvoice().getInvoiceId());
                        if (invoiceDetails.getStore() != null) {
                            invoiceDetails.setStoreId(invoiceDetails.getStore().getId());
                        }
                        if (invoiceDetails.getLot() != null) {
                            invoiceDetails.setLotId(invoiceDetails.getLot().getId());
                        }
                        if (invoiceDetails.getItemType() != null) {
                            invoiceDetails.setItemTypeId(invoiceDetails.getItemType().getId());
                        }

                        if (invoiceDetails.getDetailPurities() != null) {
                            for (InvoiceDetailPurity detailPurity : invoiceDetails.getDetailPurities()) {
                                detailPurity.setLocalId(invoiceDetails.getWholeId() + "-" + detailPurity.getPurity().getId());
                                detailPurity.setDetailId(invoiceDetails.getWholeId());
                                detailPurity.setPurityId(detailPurity.getPurity().getId());

                                Log.d("PURITIES", "--->Saving InvoiceDetailPurity (saveDetailsOfInvoice): " + detailPurity);

                                realm.insertOrUpdate(detailPurity);
                            }
                        }
                        realm.insertOrUpdate(invoiceDetails);
                        Log.d("Repository", "--->Inserted or updated detail: " + invoiceDetails.toString());
                    }
                });
            }
        } catch (Exception e) {
            realm.close();
            return false;
        }
        realm.close();
        return true;
    }

    @DebugLog
    public static List<InvoiceDetails> getAllDetailsOfInvoiceByIdSortedByDate(int serverId, int localId, String date) {
        List<InvoiceDetails> detailsList;
        if (serverId != -1) {
            detailsList = Realm.getDefaultInstance()
                    .where(InvoiceDetails.class)
                    .equalTo("invoiceId", serverId).equalTo("startDate", date)
                    .findAllSorted("startDate");
        } else {
            detailsList = Realm.getDefaultInstance()
                    .where(InvoiceDetails.class)
                    .equalTo("invoiceId", localId).equalTo("startDate", date)
                    .findAllSorted("startDate");
        }
        if (detailsList != null)
            return Realm.getDefaultInstance().copyFromRealm(detailsList);

        return null;
    }

    @DebugLog
    public static List<InvoiceDetails> getAllDetailsOfInvoiceByIdUnsorted(int serverIdParam, int localIdParam, boolean isForHarvest) {
        List<InvoiceDetails> immutableInvoiceDetailsListFromRealm;
        List<InvoiceDetails> invoiceDetailsListToReturn = new ArrayList<>();

        int serverOrLocalIdForQuery;

        if (serverIdParam != -1) {
            Log.d("BUYERROR", "--->getAllDetailsOfInvoiceById invoiceId->serverId->" + serverIdParam);

            serverOrLocalIdForQuery = serverIdParam;

        } else {
            Log.d("BUYERROR", "--->getAllDetailsOfInvoiceById invoiceId->localId->" + localIdParam);

            serverOrLocalIdForQuery = localIdParam;
        }

        if (isForHarvest)
        {
            immutableInvoiceDetailsListFromRealm =
                    Realm.getDefaultInstance()
                            .where(InvoiceDetails.class)
                            .equalTo("invoiceId", serverOrLocalIdForQuery)

                            // It's for a harvest, we filter out invoice details that have purchase-only
                            // attributes, like the store id, as there is a chance we get details
                            // from a different type of invoice:
                            .equalTo("deleteOffline",false)
                            .equalTo("storeId", -1)

                            .findAllSorted("startDate");
        }
        else
        {
            immutableInvoiceDetailsListFromRealm =
                    Realm.getDefaultInstance()
                            .where(InvoiceDetails.class)
                            .equalTo("invoiceId", serverOrLocalIdForQuery)

                            // It's for a purchase, we filter out invoice details that have harvest-only
                            // attributes. like the lot id, as there is a chance we get details
                            // from a different type of invoice:
                            .equalTo("deleteOffline",false)
                            .equalTo("lotId", -1)

                            .findAllSorted("startDate");
        }

        invoiceDetailsListToReturn.addAll(Realm.getDefaultInstance().copyFromRealm(immutableInvoiceDetailsListFromRealm));

        // Set the lot and itemType attributes so they can be printed
        for (InvoiceDetails currentDetailsItem : invoiceDetailsListToReturn) {
            Log.d("PRINT", "--->currentDetailsItem: " + currentDetailsItem);

            // These two are for harvests
            if (currentDetailsItem.getStore() == null)
                currentDetailsItem.setLot(getLotById(currentDetailsItem.getLotId()));
            if (currentDetailsItem.getItemType() == null)
                currentDetailsItem.setItemType(getItemTypeById(currentDetailsItem.getItemTypeId()));

            // These two are for purchases
            if (currentDetailsItem.getStore() == null)
                currentDetailsItem.setStore(getStoreById(currentDetailsItem.getStoreId()));

            if (!currentDetailsItem.getWholeId().isEmpty()) {
                List<InvoiceDetailPurity> detailPuritiesList = getPuritiesByLocalDetailId(currentDetailsItem.getWholeId());
                currentDetailsItem.setDetailPurities(detailPuritiesList);
                Log.d("PRINT", "--->getAllDetailsOfInvoiceById setDetailPurities->" + detailPuritiesList);
            } else
                Log.d("PRINT", "--->getAllDetailsOfInvoiceById setDetailPurities->Can't. Empty wholeId for: " + currentDetailsItem);

        }

        if (invoiceDetailsListToReturn.isEmpty()) {
            Log.d("PRINT", "--->getAllDetailsOfInvoiceById isEmpty");
        }

        return invoiceDetailsListToReturn;
    }

    @DebugLog
    public static Lot getLotById(int id) {
        return Realm.getDefaultInstance().where(Lot.class).equalTo("id", id).findFirst();
    }

    @DebugLog
    public static ItemType getItemTypeById(int id) {
        return Realm.getDefaultInstance().where(ItemType.class).equalTo("id", id).findFirst();
    }

    @DebugLog
    public static Store getStoreById(int id) {

        Log.d("PRINT", "--->all stores: " + Realm.getDefaultInstance().where(Store.class).findAll());

        return Realm.getDefaultInstance().where(Store.class).equalTo("id", id).findFirst();
    }

    @DebugLog
    public static List<InvoiceDetailPurity> getPuritiesByLocalDetailId(String idDetail) {
        List<InvoiceDetailPurity> invoiceDetailPurities =
                Realm.getDefaultInstance()
                        .copyFromRealm(Realm.getDefaultInstance()
                                .where(InvoiceDetailPurity.class)
                                .equalTo("detailId", idDetail)
                                .findAll());

        for (InvoiceDetailPurity item : invoiceDetailPurities) {
            item.setPurity(Realm.getDefaultInstance()
                    .where(Purity.class)
                    .equalTo("id", item.getPurityId())
                    .findFirst());
        }
        return invoiceDetailPurities;
    }




    public static boolean saveNewInvoice1(final int type, final InvoicePost invoicePost) {
        Realm realm = Realm.getDefaultInstance();
        Invoice existingInvoice = realm.where(Invoice.class)
                .equalTo("providerId", invoicePost.getProviderId())
                .equalTo("date", invoicePost.getDate())
                .equalTo("isClosed", false)
                .equalTo("deleteOffline", false)
                .equalTo("statusInvo", "Abierta")
                .findFirst();

        if (existingInvoice == null) {
            Log.d("BUG", "--->saveNewInvoice (Not existing before)");

            existingInvoice = new Invoice(invoicePost);
            Number id = realm.where(Invoice.class).max("localId");
            int nextLocalInvoiceId = (id == null) ? 1 : id.intValue() + 1;

            Log.d("BUYERROR", "--->saveNewInvoice nextLocalInvoiceId: " + nextLocalInvoiceId);

            existingInvoice.setLocalId(nextLocalInvoiceId);
            existingInvoice.setAddOffline(true);
            existingInvoice.setId2(existingInvoice.getInvoiceId() + "-" + existingInvoice.getLocalId());
            existingInvoice.setStatusInvo("Abierta");

            try {
                final Invoice finalInvoiceToInsert = existingInvoice;
                realm.executeTransaction(new Realm.Transaction() {
                    @DebugLog
                    @Override
                    public void execute(Realm realm) {
                        invoicePost.setInvoiceLocalId(finalInvoiceToInsert.getId2());
                        Number maxExistingInvoicePostId = realm.where(InvoicePost.class).max("invoicePostLocalId");
                        int nextExistingInvoicePostId = (maxExistingInvoicePostId == null) ? 1 : maxExistingInvoicePostId.intValue() + 1;
                        invoicePost.setInvoicePostLocalId(nextExistingInvoicePostId);
                        invoicePost.setType(type);
                        float total = 0;
                        Log.d("OFFLINE", "--->saveNewInvoice execute total=0");
                        for (ItemPost item : invoicePost.getItems()) {
                            item.setInvoicePostLocalId(nextExistingInvoicePostId);
                            Number itemId = realm.where(ItemPost.class).max("itemPostLocalId");
                            int nextItemId = (itemId == null) ? 1 : itemId.intValue() + 1;
                            item.setItemPostLocalId(nextItemId);

                            Log.d("OFFLINE", "--->saveNewInvoice execute total=" + total + " + "
                                    + item.getAmount() + " = " + (total + item.getAmount()));
                            total += item.getAmount();
                            realm.insertOrUpdate(item);

                            Number detailsId = realm.where(InvoiceDetails.class).max("localId");
                            int nextDetailsId = (detailsId == null) ? 1 : detailsId.intValue() + 1;
                            InvoiceDetails details = new InvoiceDetails(item, invoicePost);
                            details.setInvoiceId(finalInvoiceToInsert.getInvoiceId() == -1 ? finalInvoiceToInsert.getLocalId() : finalInvoiceToInsert.getInvoiceId());
                            details.setLocalId(nextDetailsId);
                            details.setWholeId(details.getId() + "-" + details.getLocalId());
                            realm.insertOrUpdate(details);

                            if (type == Constants.TYPE_SELLER) {
                                for (PurityPost purityPost : item.getPurities()) {
                                    Number maxPurityPostId = realm.where(PurityPost.class).max("purityPostLocalId");
                                    int nextPurityPostId = (maxPurityPostId == null) ? 1 : maxPurityPostId.intValue() + 1;
                                    purityPost.setPurityPostLocalId(nextPurityPostId);
                                    purityPost.setItemPostLocalId(item.getItemPostLocalId());
                                    Log.d("PURITIES", "--->Saving purityPost (saveNewInvoice): " + purityPost);
                                    realm.insertOrUpdate(purityPost);

                                    InvoiceDetailPurity invoiceDetailPurity = new InvoiceDetailPurity();
                                    invoiceDetailPurity.setDetailId(details.getWholeId());
                                    invoiceDetailPurity.setRateValue(purityPost.getRateValue());
                                    invoiceDetailPurity.setLocalId(invoiceDetailPurity.getId() + "-" + invoiceDetailPurity.getDetailId());
                                    invoiceDetailPurity.setPurityId(purityPost.getPurityId());
                                    invoiceDetailPurity.setLocalId(details.getWholeId() + "-" + invoiceDetailPurity.getPurityId());

                                    Log.d("PURITIES", "--->Saving InvoiceDetailPurity (saveNewInvoice): " + invoiceDetailPurity);
                                    realm.insertOrUpdate(invoiceDetailPurity);
                                }
                            }
                        }

                        /*invoicePost.setTotal(total);
                        invoicePost.setStatusInvo("Abierta");*/
                        finalInvoiceToInsert.setInvoiceTotal(total);
                        finalInvoiceToInsert.setStatusInvo("Abierta");
                        realm.insertOrUpdate(invoicePost);

                        if (finalInvoiceToInsert.getInvoiceStartDate().equals(invoicePost.getStartDate())) {
                            Log.d("BUG", "--->Saved invoice is not an edition");
                        } else {
                            finalInvoiceToInsert.setEditOffline(true);
                            Log.d("BUG", "--->Saved invoice is an edition");
                        }

                        realm.insertOrUpdate(finalInvoiceToInsert);


                    /*HarvestOfDay harvestOfDay = new HarvestOfDay();
                    //TODO CHECK ALL OF THIS
                    int properInvoiceId = finalInvoiceToInsert.getInvoiceId() == -1 ? finalInvoiceToInsert.getLocalId() : finalInvoiceToInsert.getInvoiceId();
                    harvestOfDay.setInvoiceId(properInvoiceId);
                    String dateSuffix = invoicePost.getStartDate().endsWith(".0") ? "" : ".0";
                    harvestOfDay.setStartDate(invoicePost.getStartDate() + dateSuffix);
                    harvestOfDay.setId(properInvoiceId + "-" + harvestOfDay.getStartDate());
                    //harvestOfDay.setInvoiceLocalId(finalInvoice.getLocalId());
                    harvestOfDay.setAddOffline(true);
                    harvestOfDay.setTotalAmount(total);
                    realm.insertOrUpdate(harvestOfDay);*/

                        Log.d("OFFLINE", "--->Inserted* finalInvoice: " + finalInvoiceToInsert.toString());
                        Log.d("OFFLINE", "--->Inserted invoicePost: " + invoicePost.toString());
                        //Log.d("OFFLINE", "--->Inserted invoice HOD: " + harvestOfDay.toString());

                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
                realm.close();
                return false;
            }
        } else {
            //todo meto los invoiceDetails xq existe un invoice abierto indistinto si es online o offline
            Log.d("DEBUG", "deberia agregar los nuevos invoiceDetails");
            try {
                final Invoice finalInvoiceToInsert = existingInvoice;
                realm.executeTransaction(new Realm.Transaction() {
                    @DebugLog
                    @Override
                    public void execute(Realm realm) {
                        invoicePost.setInvoiceLocalId(finalInvoiceToInsert.getId2());
                        Number maxExistingInvoicePostId = realm.where(InvoicePost.class).max("invoicePostLocalId");
                        int nextExistingInvoicePostId = (maxExistingInvoicePostId == null) ? 1 : maxExistingInvoicePostId.intValue() + 1;
                        invoicePost.setInvoicePostLocalId(nextExistingInvoicePostId);
                        invoicePost.setType(type);
                        float total = 0;
                        Log.d("OFFLINE", "--->saveNewInvoice execute total=0");
                        for (ItemPost item : invoicePost.getItems()) {
                            item.setInvoicePostLocalId(nextExistingInvoicePostId);
                            Number itemId = realm.where(ItemPost.class).max("itemPostLocalId");
                            int nextItemId = (itemId == null) ? 1 : itemId.intValue() + 1;
                            item.setItemPostLocalId(nextItemId);

                            Log.d("OFFLINE", "--->saveNewInvoice execute total=" + total + " + "
                                    + item.getAmount() + " = " + (total + item.getAmount()));
                            total += item.getAmount();
                            realm.insertOrUpdate(item);

                            Number detailsId = realm.where(InvoiceDetails.class).max("localId");
                            int nextDetailsId = (detailsId == null) ? 1 : detailsId.intValue() + 1;
                            InvoiceDetails details = new InvoiceDetails(item, invoicePost);
                            details.setInvoiceId(finalInvoiceToInsert.getInvoiceId() == -1 ? finalInvoiceToInsert.getLocalId() : finalInvoiceToInsert.getInvoiceId());
                            details.setLocalId(nextDetailsId);
                            details.setWholeId(details.getId() + "-" + details.getLocalId());
                            realm.insertOrUpdate(details);

                            if (type == Constants.TYPE_SELLER) {
                                for (PurityPost purityPost : item.getPurities()) {
                                    Number maxPurityPostId = realm.where(PurityPost.class).max("purityPostLocalId");
                                    int nextPurityPostId = (maxPurityPostId == null) ? 1 : maxPurityPostId.intValue() + 1;
                                    purityPost.setPurityPostLocalId(nextPurityPostId);
                                    purityPost.setItemPostLocalId(item.getItemPostLocalId());
                                    Log.d("PURITIES", "--->Saving purityPost (saveNewInvoice): " + purityPost);
                                    realm.insertOrUpdate(purityPost);

                                    InvoiceDetailPurity invoiceDetailPurity = new InvoiceDetailPurity();
                                    invoiceDetailPurity.setDetailId(details.getWholeId());
                                    invoiceDetailPurity.setRateValue(purityPost.getRateValue());
                                    invoiceDetailPurity.setLocalId(invoiceDetailPurity.getId() + "-" + invoiceDetailPurity.getDetailId());
                                    invoiceDetailPurity.setPurityId(purityPost.getPurityId());
                                    invoiceDetailPurity.setLocalId(details.getWholeId() + "-" + invoiceDetailPurity.getPurityId());

                                    Log.d("PURITIES", "--->Saving InvoiceDetailPurity (saveNewInvoice): " + invoiceDetailPurity);
                                    realm.insertOrUpdate(invoiceDetailPurity);
                                }
                            }
                        }

                        /*invoicePost.setTotal(total);
                        invoicePost.setStatusInvo("Abierta");*/
                        finalInvoiceToInsert.setInvoiceTotal(total);
                        finalInvoiceToInsert.setStatusInvo("Abierta");
                        realm.insertOrUpdate(invoicePost);

                        if (finalInvoiceToInsert.getInvoiceStartDate().equals(invoicePost.getStartDate())) {
                            Log.d("BUG", "--->Saved invoice is not an edition");
                        } else {
                            finalInvoiceToInsert.setEditOffline(true);
                            Log.d("BUG", "--->Saved invoice is an edition");
                        }

                        realm.insertOrUpdate(finalInvoiceToInsert);


                    /*HarvestOfDay harvestOfDay = new HarvestOfDay();
                    //TODO CHECK ALL OF THIS
                    int properInvoiceId = finalInvoiceToInsert.getInvoiceId() == -1 ? finalInvoiceToInsert.getLocalId() : finalInvoiceToInsert.getInvoiceId();
                    harvestOfDay.setInvoiceId(properInvoiceId);
                    String dateSuffix = invoicePost.getStartDate().endsWith(".0") ? "" : ".0";
                    harvestOfDay.setStartDate(invoicePost.getStartDate() + dateSuffix);
                    harvestOfDay.setId(properInvoiceId + "-" + harvestOfDay.getStartDate());
                    //harvestOfDay.setInvoiceLocalId(finalInvoice.getLocalId());
                    harvestOfDay.setAddOffline(true);
                    harvestOfDay.setTotalAmount(total);
                    realm.insertOrUpdate(harvestOfDay);*/

                        Log.d("OFFLINE", "--->Inserted* finalInvoice: " + finalInvoiceToInsert.toString());
                        Log.d("OFFLINE", "--->Inserted invoicePost: " + invoicePost.toString());
                        //Log.d("OFFLINE", "--->Inserted invoice HOD: " + harvestOfDay.toString());

                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
                realm.close();
                return false;
            }



        }

        realm.close();
        return true;
    }

    @DebugLog
    public static boolean saveNewInvoice(final int type, final InvoicePost invoicePost) {
        Realm realm = Realm.getDefaultInstance();

        /*Log.d("Invoice", "--->Generated id for provider: "+providerId);

        invoicePost.setProviderId(providerId);*/

        Invoice existingInvoice = realm.where(Invoice.class)
                .equalTo("providerId", invoicePost.getProviderId())
                .equalTo("date", invoicePost.getDate())
                .equalTo("isClosed", false)
                .equalTo("deleteOffline", false)
                .equalTo("statusInvo", "Abierta")
                .findFirst();

        if (existingInvoice == null) {
            Log.d("BUG", "--->saveNewInvoice (Not existing before)");

            existingInvoice = new Invoice(invoicePost);
            Number id = realm.where(Invoice.class).max("localId");
            int nextLocalInvoiceId = (id == null) ? 1 : id.intValue() + 1;

            Log.d("BUYERROR", "--->saveNewInvoice nextLocalInvoiceId: " + nextLocalInvoiceId);

            existingInvoice.setLocalId(nextLocalInvoiceId);
            existingInvoice.setAddOffline(true);
            existingInvoice.setId2(existingInvoice.getInvoiceId() + "-" + existingInvoice.getLocalId());
            existingInvoice.setStatusInvo("Abierta");
        } else
            Log.d("BUG", "--->saveNewInvoice (It existed before):" + existingInvoice);

        try {
            final Invoice finalInvoiceToInsert = existingInvoice;
            realm.executeTransaction(new Realm.Transaction() {
                @DebugLog
                @Override
                public void execute(Realm realm) {
                    invoicePost.setInvoiceLocalId(finalInvoiceToInsert.getId2());
                    Number maxExistingInvoicePostId = realm.where(InvoicePost.class).max("invoicePostLocalId");
                    int nextExistingInvoicePostId = (maxExistingInvoicePostId == null) ? 1 : maxExistingInvoicePostId.intValue() + 1;
                    invoicePost.setInvoicePostLocalId(nextExistingInvoicePostId);
                    invoicePost.setType(type);
                    float total = 0;
                    Log.d("OFFLINE", "--->saveNewInvoice execute total=0");
                    for (ItemPost item : invoicePost.getItems()) {
                        item.setInvoicePostLocalId(nextExistingInvoicePostId);
                        Number itemId = realm.where(ItemPost.class).max("itemPostLocalId");
                        int nextItemId = (itemId == null) ? 1 : itemId.intValue() + 1;
                        item.setItemPostLocalId(nextItemId);

                        Log.d("OFFLINE", "--->saveNewInvoice execute total=" + total + " + "
                                + item.getAmount() + " = " + (total + item.getAmount()));
                        total += item.getAmount();
                        realm.insertOrUpdate(item);

                        Number detailsId = realm.where(InvoiceDetails.class).max("localId");
                        int nextDetailsId = (detailsId == null) ? 1 : detailsId.intValue() + 1;
                        InvoiceDetails details = new InvoiceDetails(item, invoicePost);
                        details.setInvoiceId(finalInvoiceToInsert.getInvoiceId() == -1 ? finalInvoiceToInsert.getLocalId() : finalInvoiceToInsert.getInvoiceId());
                        details.setLocalId(nextDetailsId);
                        details.setWholeId(details.getId() + "-" + details.getLocalId());
                        realm.insertOrUpdate(details);

                        if (type == Constants.TYPE_SELLER) {
                            for (PurityPost purityPost : item.getPurities()) {
                                Number maxPurityPostId = realm.where(PurityPost.class).max("purityPostLocalId");
                                int nextPurityPostId = (maxPurityPostId == null) ? 1 : maxPurityPostId.intValue() + 1;
                                purityPost.setPurityPostLocalId(nextPurityPostId);
                                purityPost.setItemPostLocalId(item.getItemPostLocalId());
                                Log.d("PURITIES", "--->Saving purityPost (saveNewInvoice): " + purityPost);
                                realm.insertOrUpdate(purityPost);

                                InvoiceDetailPurity invoiceDetailPurity = new InvoiceDetailPurity();
                                invoiceDetailPurity.setDetailId(details.getWholeId());
                                invoiceDetailPurity.setRateValue(purityPost.getRateValue());
                                invoiceDetailPurity.setLocalId(invoiceDetailPurity.getId() + "-" + invoiceDetailPurity.getDetailId());
                                invoiceDetailPurity.setPurityId(purityPost.getPurityId());
                                invoiceDetailPurity.setLocalId(details.getWholeId() + "-" + invoiceDetailPurity.getPurityId());

                                Log.d("PURITIES", "--->Saving InvoiceDetailPurity (saveNewInvoice): " + invoiceDetailPurity);
                                realm.insertOrUpdate(invoiceDetailPurity);
                            }
                        }
                    }

                    invoicePost.setTotal(total);
                    invoicePost.setStatusInvo("Abierta");
                    realm.insertOrUpdate(invoicePost);

                    if (finalInvoiceToInsert.getInvoiceStartDate().equals(invoicePost.getStartDate())) {
                        Log.d("BUG", "--->Saved invoice is not an edition");
                    } else {
                        finalInvoiceToInsert.setEditOffline(true);
                        Log.d("BUG", "--->Saved invoice is an edition");
                    }

                    realm.insertOrUpdate(finalInvoiceToInsert);


                    /*HarvestOfDay harvestOfDay = new HarvestOfDay();
                    //TODO CHECK ALL OF THIS
                    int properInvoiceId = finalInvoiceToInsert.getInvoiceId() == -1 ? finalInvoiceToInsert.getLocalId() : finalInvoiceToInsert.getInvoiceId();
                    harvestOfDay.setInvoiceId(properInvoiceId);
                    String dateSuffix = invoicePost.getStartDate().endsWith(".0") ? "" : ".0";
                    harvestOfDay.setStartDate(invoicePost.getStartDate() + dateSuffix);
                    harvestOfDay.setId(properInvoiceId + "-" + harvestOfDay.getStartDate());
                    //harvestOfDay.setInvoiceLocalId(finalInvoice.getLocalId());
                    harvestOfDay.setAddOffline(true);
                    harvestOfDay.setTotalAmount(total);
                    realm.insertOrUpdate(harvestOfDay);*/

                    Log.d("OFFLINE", "--->Inserted* finalInvoice: " + finalInvoiceToInsert.toString());
                    Log.d("OFFLINE", "--->Inserted invoicePost: " + invoicePost.toString());
                    //Log.d("OFFLINE", "--->Inserted invoice HOD: " + harvestOfDay.toString());

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            realm.close();
            return false;
        }
        realm.close();
        return true;
    }

    @DebugLog
    public static Invoice getInvoiceById(int id) {
        return Realm.getDefaultInstance().where(Invoice.class).equalTo("id", id).findFirst();
    }

    @DebugLog
    public static Invoice getInvoiceByIdLocal(int id) {
        return Realm.getDefaultInstance().where(Invoice.class).equalTo("localId", id).findFirst();
    }

    @DebugLog
    public static boolean invoiceHasOfflineOperation(Invoice invoice) {
        Realm realm = Realm.getDefaultInstance();

        Invoice savedInvoice = realm
                .where(Invoice.class)
                .equalTo("providerId", invoice.getProviderId())
                .equalTo("date", invoice.getDate())
                .equalTo("isClosed", invoice.isClosed())
                .equalTo("deleteOffline", false)
                .equalTo("statusInvo", invoice.getStatusInvo()) //todo nose
                .beginGroup()
                .equalTo("addOffline", true).
                        or()
                .equalTo("editOffline", true)
                .endGroup()
                .findFirst();

        return savedInvoice != null;
    }

    @DebugLog
    public static boolean invoiceHasOfflineOperation(InvoicePost invoicePost, boolean isAdd) {
        Realm realm = Realm.getDefaultInstance();

        Invoice savedInvoice;

        if (isAdd) {
            savedInvoice = realm
                    .where(Invoice.class)
                    .equalTo("providerId", invoicePost.getProviderId())
                    .equalTo("date", invoicePost.getDate())
                    .equalTo("isClosed", false)
                    .equalTo("deleteOffline", false)
                    .equalTo("statusInvo", "Abierta") //todo no se
                    .equalTo("addOffline", true)
                    .findFirst();


        } else {
            savedInvoice = realm
                    .where(Invoice.class)
                    .equalTo("providerId", invoicePost.getProviderId())
                    .equalTo("date", invoicePost.getDate())
                    .equalTo("isClosed", false)
                    .equalTo("statusInvo", "Abierta") //todo no se
                    .beginGroup()
                    .equalTo("addOffline", true).
                            or()
                    .equalTo("editOffline", true)
                    .endGroup()
                    .findFirst();

        }

        return savedInvoice != null;
    }

    @DebugLog
    public static boolean providerHasOfflineOperation(Provider provider) {
        Realm realm = Realm.getDefaultInstance();

        Provider existingProvider;
        if (provider.getIdProvider() == -1) {
            existingProvider = realm.where(Provider.class).equalTo("unixtime", provider.getUnixtime()).findFirst();
        } else {
            existingProvider = realm.where(Provider.class).equalTo("idProvider", provider.getIdProvider()).findFirst();
        }

        return (existingProvider != null && (existingProvider.isAddOffline() || existingProvider.isEditOffline() || existingProvider.isDeleteOffline()));
    }

    @DebugLog
    public static boolean updateInvoiceDetails1(final InvoiceDetails invoiceDetails/*, final List<InvoiceDetailPurity> detailsPuritiesListParam*/) {

        Realm realm = Realm.getDefaultInstance();
        try {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    InvoiceDetails existing = realm.where(InvoiceDetails.class).equalTo("id", invoiceDetails.getInvoiceId()).findFirst();
                    if (existing != null && invoiceDetails.getInvoiceId()!=existing.getInvoiceId()) {
                        existing.deleteFromRealm();
                        Log.d("TEST", "--->deleteFromRealm in updateExistingProvider");
                    } else
                        Log.d("TEST", "--->NOT deleteFromRealm in updateExistingProvider");

                    /*InvoiceDetails invoiceDetailsInsert = invoiceDetails;

                    invoiceDetailsInsert.setItemTypeId(invoiceDetails.getItemTypeId());
                    if(invoiceDetails.getItemTypeId()==1){
                        invoiceDetailsInsert.setStoreId(invoiceDetails.getStoreId());
                        invoiceDetailsInsert.setDispatcherName(invoiceDetails.getDispatcherName());
                        invoiceDetailsInsert.setTotalInvoiceDetail(invoiceDetails.getPriceItem()*invoiceDetails.getAmount());
                    } else {
                        invoiceDetailsInsert.setLotId(invoiceDetails.getLotId());
                        Lot lot = getLotById(invoiceDetails.getLotId());
                        invoiceDetailsInsert.setTotalInvoiceDetail(lot.getPrice()*invoiceDetails.getAmount());
                    }*/

                    invoiceDetails.setEditOffline(true);
                    realm.insertOrUpdate(invoiceDetails);
                }
            });
        } catch (Exception e) {
            realm.close();
            return false;
        }
        realm.close();
        return true;
    }


    @DebugLog
    public static boolean updateInvoiceDetails(final InvoicePost invoicePost, final List<InvoiceDetailPurity> detailsPuritiesListParam) {
        //TODO FIX UPDATE FOR LOT NUMBER AND SIMILAR (NOT WEIGHT)
        Realm realm = Realm.getDefaultInstance();

        Invoice savedInvoice = realm
                .where(Invoice.class)
                .equalTo("providerId", invoicePost.getProviderId())
                .equalTo("date", invoicePost.getDate())
                .equalTo("isClosed", false)
                .equalTo("statusInvo", "Abierta")
                //.lessThan("invoiceStatus", 3)
                .findFirst();

        if (savedInvoice != null) {

            final Invoice invoiceInRealm = realm.copyFromRealm(savedInvoice);

            Log.d("PURITIES", "--->updateInvoiceDetails trying");
            try {
                realm.executeTransaction(new Realm.Transaction() {
                    @DebugLog
                    @Override
                    public void execute(Realm realm) {
                        float newTotalAmount = 0;
                        List<ItemPost> newItems = invoicePost.getItems();
                        List<InvoiceDetails> oldInvoiceDetails = getAllDetailsOfInvoiceByIdSortedByDate(invoiceInRealm.getInvoiceId(), invoiceInRealm.getLocalId(), invoicePost.getStartDate());

                        for (int i = 0; i < oldInvoiceDetails.size(); i++) {

                            ItemPost itemWithNewVersion = null;

                            for (int j = 0; j < newItems.size(); j++) {
                                if (newItems.get(j).getItemTypeId() == oldInvoiceDetails.get(i).getItemTypeId()) {
                                    itemWithNewVersion = newItems.get(j);
                                    break;
                                }
                            }

                            boolean currentItemIsForUpdate = itemWithNewVersion != null;

                            if (currentItemIsForUpdate) {
                                Log.d("PURITIES", "--->currentItemIsForUpdate itemWithNewVersion: " + itemWithNewVersion);

                                newTotalAmount += itemWithNewVersion.getAmount();
                                realm.insertOrUpdate(itemWithNewVersion);

                                InvoiceDetails details = new InvoiceDetails(itemWithNewVersion, invoicePost);
                                details.setInvoiceId(invoicePost.getInvoiceId() == -1 ? invoicePost.getInvoicePostLocalId() : invoicePost.getInvoiceId());
                                details.setLocalId(itemWithNewVersion.getInvoiceDetailId());
                                details.setWholeId(oldInvoiceDetails.get(i).getWholeId());
                                Log.d("PURITIES", "--->currentItemIsForUpdate. Details to save: " + details);
                                realm.insertOrUpdate(details);
                                newItems.remove(itemWithNewVersion);
                                /*if (invoicePost.getType() == Constants.TYPE_SELLER) {

                                    Log.d("PURITIES", "--->TYPE_SELLER:" + detailsPuritiesListParam);
                                }*/

                            } else {
                                InvoiceDetails details = null;
                                if (invoicePost.getLot() != oldInvoiceDetails.get(i).getLotId()) {
                                    details = oldInvoiceDetails.get(i);
                                    details.setLotId(invoicePost.getLot());
                                }
                                if (invoicePost.getObservations().equals(oldInvoiceDetails.get(i).getObservation())) {
                                    if (details == null) {
                                        details = oldInvoiceDetails.get(i);
                                    }
                                    details.setObservation(invoicePost.getObservations());
                                }

                                Log.d("PURITIES", "--->currentItemIsNew. Details to save: " + details);

                                if (details != null) {
                                    realm.insertOrUpdate(details);
                                }
                                newTotalAmount += oldInvoiceDetails.get(i).getAmount();
                            }
                        }

                        if (invoiceInRealm.isAddOffline()) {

                            Log.d("PURITIES", "--->updateInvoiceDetails. " +
                                    "invoiceInRealm.isAddOffline()");

                            for (ItemPost newItem : newItems) {
                                newItem.setInvoicePostLocalId(invoicePost.getInvoicePostLocalId());
                                Number itemId = realm.where(ItemPost.class).max("itemPostLocalId");
                                int nextItemId = (itemId == null) ? 1 : itemId.intValue() + 1;
                                newItem.setItemPostLocalId(nextItemId);
                                newTotalAmount += newItem.getAmount();
                                realm.insertOrUpdate(newItem);

                                Number detailsId = realm.where(InvoiceDetails.class).max("localId");
                                int nextDetailsId = (detailsId == null) ? 1 : detailsId.intValue() + 1;
                                InvoiceDetails details = new InvoiceDetails(newItem, invoicePost);
                                details.setInvoiceId(invoicePost.getInvoiceId() == -1 ? invoicePost.getInvoicePostLocalId() : invoicePost.getInvoiceId());
                                details.setLocalId(nextDetailsId);
                                details.setWholeId(details.getId() + "-" + details.getLocalId());
                                Log.d("PURITIES", "--->updateInvoiceDetails detail: " + details);
                                realm.insertOrUpdate(details);
                            }
                        } else {
                            Log.d("PURITIES", "--->updateInvoiceDetails. " +
                                    "invoiceInRealm !isAddOffline()");
                        }

                        invoiceInRealm.setEditOffline(true);
                        realm.insertOrUpdate(invoiceInRealm);
                        Log.d("PURITIES", "--->updateInvoiceDetails Inserted/Updated invoiceInRealm: " + invoiceInRealm);

                        invoicePost.setTotal(newTotalAmount);
                        realm.insertOrUpdate(invoicePost);
                        Log.d("PURITIES", "--->updateInvoiceDetails Inserted/Updated invoicePost: " + invoicePost);

                        /*HarvestOfDay harvestOfDay = new HarvestOfDay();
                        harvestOfDay.setInvoiceId(invoicePost.getInvoiceId() == -1 ? invoicePost.getInvoicePostLocalId() : invoicePost.getInvoiceId());
                        String dateSuffix = invoicePost.getStartDate().endsWith(".0") ? "" : ".0";
                        harvestOfDay.setStartDate(invoicePost.getStartDate() + dateSuffix);
                        harvestOfDay.setId(invoicePost.getInvoiceId() + "-" + harvestOfDay.getStartDate());
                        harvestOfDay.setEditOffline(true);
                        harvestOfDay.setTotalAmount(newTotalAmount);
                        realm.insertOrUpdate(harvestOfDay);*/

                        //Log.d("PURITIES", "--->updateInvoiceDetails Inserted/Updated harvestOfDay: " + harvestOfDay);

                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
                realm.close();
                return false;
            }
            realm.close();
            return true;

        } else {
            Log.e("OFFLINE", "--->updateInvoiceDetail invoice not found: \n" + invoicePost);


            RealmResults<Invoice> invoicesListInRealm = realm
                    .where(Invoice.class)
                    .findAll();

            Object[] array = invoicesListInRealm.toArray();

            for (int i = 0; i < array.length; i++) {
                Log.e("OFFLINE", "--->Invoice found: " + array[i].toString());
            }

            return false;
        }

        /**
         Realm realm = Realm.getDefaultInstance();
         try {
         for (final Provider provider : providerList) {
         Provider saved = realm.where(Provider.class).equalTo("identificationDocProvider",provider.getIdentificationDocProvider()).findFirst();
         if (saved!= null && (saved.isDeleteOffline() || saved.isAddOffline() || saved.isEditOffline()))
         continue;

         realm.executeTransaction(new Realm.Transaction() {
        @Override public void execute(Realm realm) {
        provider.setProviderTypeId(provider.getProviderType().getIdProviderType());
        realm.insertOrUpdate(provider);

        Log.d("Repository", "--->Inserted/Updated provider: " + provider.toString());

        }
        });
         }
         } finally {
         realm.close();
         List<Provider> providers = getAllProvidersByType(2);
         Log.d("TEST","providers "+providers);
         }
         * */
    }

    /*@DebugLog
    public static boolean updateInvoiceDetails(final InvoicePost invoicePost, final List<InvoiceDetailPurity> detailsPuritiesListParam) {
        //TODO FIX UPDATE FOR LOT NUMBER AND SIMILAR (NOT WEIGHT)
        Realm realm = Realm.getDefaultInstance();

        Invoice savedInvoice = realm
                .where(Invoice.class)
                .equalTo("providerId", invoicePost.getProviderId())
                .equalTo("date", invoicePost.getDate())
                .equalTo("isClosed", false)
                .equalTo("statusInvo", "Open")
                //.lessThan("invoiceStatus", 3)
                .findFirst();

        if (savedInvoice != null) {

            final Invoice invoiceInRealm = realm.copyFromRealm(savedInvoice);

            Log.d("PURITIES", "--->updateInvoiceDetails trying");
            try {
                realm.executeTransaction(new Realm.Transaction() {
                    @DebugLog
                    @Override
                    public void execute(Realm realm) {
                        float newTotalAmount = 0;
                        List<ItemPost> newItems = invoicePost.getItems();
                        List<InvoiceDetails> oldInvoiceDetails = getAllDetailsOfInvoiceByIdSortedByDate(invoiceInRealm.getInvoiceId(), invoiceInRealm.getLocalId(), invoicePost.getStartDate());

                        for (int i = 0; i < oldInvoiceDetails.size(); i++) {

                            ItemPost itemWithNewVersion = null;

                            for (int j = 0; j < newItems.size(); j++) {
                                if (newItems.get(j).getItemTypeId() == oldInvoiceDetails.get(i).getItemTypeId()) {
                                    itemWithNewVersion = newItems.get(j);
                                    break;
                                }
                            }

                            boolean currentItemIsForUpdate = itemWithNewVersion != null;

                            if (currentItemIsForUpdate) {
                                Log.d("PURITIES", "--->currentItemIsForUpdate itemWithNewVersion: " + itemWithNewVersion);

                                newTotalAmount += itemWithNewVersion.getAmount();
                                realm.insertOrUpdate(itemWithNewVersion);

                                InvoiceDetails details = new InvoiceDetails(itemWithNewVersion, invoicePost);
                                details.setInvoiceId(invoicePost.getInvoiceId() == -1 ? invoicePost.getInvoicePostLocalId() : invoicePost.getInvoiceId());
                                details.setLocalId(itemWithNewVersion.getInvoiceDetailId());
                                details.setWholeId(oldInvoiceDetails.get(i).getWholeId());
                                Log.d("PURITIES", "--->currentItemIsForUpdate. Details to save: " + details);
                                realm.insertOrUpdate(details);
                                newItems.remove(itemWithNewVersion);
                                /*if (invoicePost.getType() == Constants.TYPE_SELLER) {

                                    Log.d("PURITIES", "--->TYPE_SELLER:" + detailsPuritiesListParam);
                                }*/

                            /*} else {
                                InvoiceDetails details = null;
                                if (invoicePost.getLot() != oldInvoiceDetails.get(i).getLotId()) {
                                    details = oldInvoiceDetails.get(i);
                                    details.setLotId(invoicePost.getLot());
                                }
                                if (invoicePost.getObservations().equals(oldInvoiceDetails.get(i).getObservation())) {
                                    if (details == null) {
                                        details = oldInvoiceDetails.get(i);
                                    }
                                    details.setObservation(invoicePost.getObservations());
                                }

                                Log.d("PURITIES", "--->currentItemIsNew. Details to save: " + details);

                                if (details != null) {
                                    realm.insertOrUpdate(details);
                                }
                                newTotalAmount += oldInvoiceDetails.get(i).getAmount();
                            }
                        }

                        if (invoiceInRealm.isAddOffline()) {

                            Log.d("PURITIES", "--->updateInvoiceDetails. " +
                                    "invoiceInRealm.isAddOffline()");

                            for (ItemPost newItem : newItems) {
                                newItem.setInvoicePostLocalId(invoicePost.getInvoicePostLocalId());
                                Number itemId = realm.where(ItemPost.class).max("itemPostLocalId");
                                int nextItemId = (itemId == null) ? 1 : itemId.intValue() + 1;
                                newItem.setItemPostLocalId(nextItemId);
                                newTotalAmount += newItem.getAmount();
                                realm.insertOrUpdate(newItem);

                                Number detailsId = realm.where(InvoiceDetails.class).max("localId");
                                int nextDetailsId = (detailsId == null) ? 1 : detailsId.intValue() + 1;
                                InvoiceDetails details = new InvoiceDetails(newItem, invoicePost);
                                details.setInvoiceId(invoicePost.getInvoiceId() == -1 ? invoicePost.getInvoicePostLocalId() : invoicePost.getInvoiceId());
                                details.setLocalId(nextDetailsId);
                                details.setWholeId(details.getId() + "-" + details.getLocalId());
                                Log.d("PURITIES", "--->updateInvoiceDetails detail: " + details);
                                realm.insertOrUpdate(details);
                            }
                        } else {
                            Log.d("PURITIES", "--->updateInvoiceDetails. " +
                                    "invoiceInRealm !isAddOffline()");
                        }

                        invoiceInRealm.setEditOffline(true);
                        realm.insertOrUpdate(invoiceInRealm);
                        Log.d("PURITIES", "--->updateInvoiceDetails Inserted/Updated invoiceInRealm: " + invoiceInRealm);

                        invoicePost.setTotal(newTotalAmount);
                        realm.insertOrUpdate(invoicePost);
                        Log.d("PURITIES", "--->updateInvoiceDetails Inserted/Updated invoicePost: " + invoicePost);

                        HarvestOfDay harvestOfDay = new HarvestOfDay();
                        harvestOfDay.setInvoiceId(invoicePost.getInvoiceId() == -1 ? invoicePost.getInvoicePostLocalId() : invoicePost.getInvoiceId());
                        String dateSuffix = invoicePost.getStartDate().endsWith(".0") ? "" : ".0";
                        harvestOfDay.setStartDate(invoicePost.getStartDate() + dateSuffix);
                        harvestOfDay.setId(invoicePost.getInvoiceId() + "-" + harvestOfDay.getStartDate());
                        harvestOfDay.setEditOffline(true);
                        harvestOfDay.setTotalAmount(newTotalAmount);
                        realm.insertOrUpdate(harvestOfDay);

                        Log.d("PURITIES", "--->updateInvoiceDetails Inserted/Updated harvestOfDay: " + harvestOfDay);

                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
                realm.close();
                return false;
            }
            realm.close();
            return true;

        } else {
            Log.e("OFFLINE", "--->updateInvoiceDetail invoice not found: \n" + invoicePost);


            RealmResults<Invoice> invoicesListInRealm = realm
                    .where(Invoice.class)
                    .findAll();

            Object[] array = invoicesListInRealm.toArray();

            for (int i = 0; i < array.length; i++) {
                Log.e("OFFLINE", "--->Invoice found: " + array[i].toString());
            }

            return false;
        }*/

        /**
         Realm realm = Realm.getDefaultInstance();
         try {
         for (final Provider provider : providerList) {
         Provider saved = realm.where(Provider.class).equalTo("identificationDocProvider",provider.getIdentificationDocProvider()).findFirst();
         if (saved!= null && (saved.isDeleteOffline() || saved.isAddOffline() || saved.isEditOffline()))
         continue;

         realm.executeTransaction(new Realm.Transaction() {
        @Override public void execute(Realm realm) {
        provider.setProviderTypeId(provider.getProviderType().getIdProviderType());
        realm.insertOrUpdate(provider);

        Log.d("Repository", "--->Inserted/Updated provider: " + provider.toString());

        }
        });
         }
         } finally {
         realm.close();
         List<Provider> providers = getAllProvidersByType(2);
         Log.d("TEST","providers "+providers);
         }
         * */
    //}

    @DebugLog
    public static boolean delete(int remoteInvoiceId, int invoiceLocalId) {
        Invoice invoice;
        if (remoteInvoiceId != -1) {
            invoice = Realm.getDefaultInstance()
                    .where(Invoice.class)
                    .equalTo("invoiceId", remoteInvoiceId)
                    .equalTo("deleteOffline", false)
                    .findFirst();
        } else {
            invoice = Realm.getDefaultInstance()
                    .where(Invoice.class)
                    .equalTo("localId", invoiceLocalId)
                    .equalTo("deleteOffline", false)
                    .findFirst();
        }
        if (invoice == null)
            return false;

        Realm realm = Realm.getDefaultInstance();
        try {
            final Invoice finalInvoice = invoice;
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    if (finalInvoice.isAddOffline()) {
                        finalInvoice.deleteFromRealm();
                    } else {
                        finalInvoice.setDeleteOffline(true);
                        realm.insertOrUpdate(finalInvoice);
                    }
                }
            });
        } catch (Exception e) {
            realm.close();
            return false;
        }
        realm.close();
        return true;
    }

    @DebugLog
    public static boolean delete(final String invoiceLocalId, String originalDate, final String harvestOfDayId) {
        Realm realm = Realm.getDefaultInstance();

        List<InvoicePost> preexistingList = realm.where(InvoicePost.class).findAll();

        Log.d("HODBUG", "--->Invoice posts before deleting: " + preexistingList.size() + ":\n" + preexistingList);

        // Remove ".0" from date if it has, so we can safely query for matches with and without it
        final String dateWithoutSuffix;
        if (originalDate.endsWith(".0"))
            dateWithoutSuffix = originalDate.substring(0, originalDate.length() - 2);
        else
            dateWithoutSuffix = originalDate;

        try {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    //TODO delete post tambien

                    /*HarvestOfDay harvestOfDay =
                            realm.where(HarvestOfDay.class)
                                    .equalTo("id", harvestOfDayId)
                                    .findFirst();

                    if (harvestOfDay != null) {

                        Log.d("HODBUG", "--->Item to delete has Of day: " + harvestOfDay);

                        List<InvoiceDetails> detailsList = realm.where(InvoiceDetails.class)
                                .equalTo("invoiceId", harvestOfDay.getInvoiceId())
                                .equalTo("startDate", harvestOfDay.getStartDate())
                                .findAll();

                        if (harvestOfDay.isAddOffline()) {
                            InvoicePost invoicePost = realm.where(InvoicePost.class)
                                    .equalTo("invoiceLocalId", invoiceLocalId)
                                    .beginGroup()
                                    .equalTo("startDate", dateWithoutSuffix)
                                    .or()
                                    .equalTo("startDate", dateWithoutSuffix + ".0")
                                    .endGroup()
                                    .findFirst();

                            Log.d("HODBUG", "--->Looking for invoicePost of item to delete (Params: invoiceLocalId=" + invoiceLocalId + ", dateWithoutSuffix:" + dateWithoutSuffix + ", dateWithSuffix: " + (dateWithoutSuffix + ".0") + ")");

                            if (invoicePost != null) {
                                Log.d("HODBUG", "--->Item to delete (is isAddOffline) has invoicePost: " + invoicePost);

                                int idInvoicePost = invoicePost.getInvoicePostLocalId();

                                invoicePost.deleteFromRealm();

                                List<ItemPost> itemPostList =
                                        realm.where(ItemPost.class)
                                                .equalTo("invoicePostLocalId", idInvoicePost)
                                                .findAll();
                                if (itemPostList != null && itemPostList.size() > 0) {
                                    for (ItemPost itemPost : itemPostList) {
                                        itemPost.deleteFromRealm();
                                    }
                                }
                            } else
                                Log.e("HODBUG", "--->Item to delete (is isAddOffline) has no invoicePost");

                            //harvestOfDay.deleteFromRealm();

                            for (InvoiceDetails detail : detailsList) {
                                detail.deleteFromRealm();
                            }
                        } else {
                            harvestOfDay.setDeleteOffline(true);

                            if (harvestOfDay.isEditOffline()) {

                                InvoicePost invoicePost = realm.where(InvoicePost.class)
                                        .equalTo("invoiceLocalId", invoiceLocalId)
                                        .beginGroup()
                                        .equalTo("startDate", dateWithoutSuffix)
                                        .or()
                                        .equalTo("startDate", dateWithoutSuffix + ".0")
                                        .endGroup()
                                        .findFirst();

                                if (invoicePost != null) {

                                    int idInvoicePost = invoicePost.getInvoicePostLocalId();
                                    invoicePost.deleteFromRealm();
                                    List<ItemPost> itemPostList = realm.where(ItemPost.class)
                                            .equalTo("invoicePostLocalId", idInvoicePost)
                                            .findAll();
                                    if (itemPostList != null && itemPostList.size() > 0) {
                                        for (ItemPost itemPost : itemPostList) {
                                            itemPost.deleteFromRealm();
                                        }
                                    }
                                }
                            }
                            realm.insertOrUpdate(harvestOfDay);

                            for (InvoiceDetails detail : detailsList) {
                                detail.setDeleteOffline(true);
                                realm.insertOrUpdate(detail);
                            }
                        }
                    } else
                        Log.e("HODBUG", "--->Item to delete has NO Of day");*/
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            realm.close();
            Log.d("HODBUG", "--->Invoice posts after deleting (Error): " + realm.where(InvoicePost.class).findAll().size());
            return false;
        }
        realm.close();
        Log.d("HODBUG", "--->Invoice posts after deleting: " + realm.where(InvoicePost.class).findAll().size());
        return true;
    }

    @DebugLog
    public static boolean deleteInvoiceDetails(final int remoteInvoiceDetailId, final int localInvoiceDetailId) {

        final boolean[] deleted = {false};

        Realm realm = Realm.getDefaultInstance();

        final InvoiceDetails invoiceInRealm;
        InvoiceDetails invoiceInRealm1;

        if (remoteInvoiceDetailId != -1) {
            invoiceInRealm = realm
                    .where(InvoiceDetails.class)
                    .equalTo("id", remoteInvoiceDetailId)
                    .equalTo("deleteOffline", false)
                    .findFirst();
            Log.d("DEBUG", "elimina invoiceD1"+ invoiceInRealm);

        } else {
            invoiceInRealm = realm
                    .where(InvoiceDetails.class)
                    .equalTo("localId", localInvoiceDetailId)
                    .equalTo("deleteOffline", false)
                    .findFirst();
            Log.d("DEBUG", "elimina invoiceD2");
        }
        Log.d("DEBUG", "elimina invoiceD3" + invoiceInRealm);
        if (invoiceInRealm != null) {

            Log.d("DEBUG", "elimina invoiceD4");
            realm.executeTransaction(new Realm.Transaction() {
                @DebugLog
                @Override
                public void execute(Realm realm) {

                    invoiceInRealm.setDeleteOffline(true);
                    realm.insertOrUpdate(invoiceInRealm);
                    Log.d("DEBUG", "elimina invoiceD5");
                    deleted[0] = true;
                }
            });
        }

        return deleted[0];
    }

    @DebugLog
    public static boolean deleteHarvestOrPurchaseInvoice(final int remoteInvoiceId, final int localInvoiceId) {

        final boolean[] deleted = {false};

        Realm realm = Realm.getDefaultInstance();

        final Invoice invoiceInRealm;

        int properInvoiceId;

        if (remoteInvoiceId != -1) {
            invoiceInRealm = realm
                    .where(Invoice.class)
                    .equalTo("id", remoteInvoiceId)
                    .equalTo("deleteOffline", false)
                    .findFirst();

            properInvoiceId = invoiceInRealm.getInvoiceId();

        } else {
            invoiceInRealm = realm
                    .where(Invoice.class)
                    .equalTo("localId", localInvoiceId)
                    .equalTo("deleteOffline", false)
                    .findFirst();

            properInvoiceId = invoiceInRealm.getLocalId();
        }

        if (invoiceInRealm != null) {
            Log.d("OFFLINE", "--->deleteHarvestInvoice found Invoice to delete: " + invoiceInRealm);

            final int finalProperInvoiceId = properInvoiceId;
            realm.executeTransaction(new Realm.Transaction() {
                @DebugLog
                @Override
                public void execute(Realm realm) {
                    invoiceInRealm.setDeleteOffline(true);

                    if (invoiceInRealm.isAddOffline()) {
                        deleteInvoiceItemsInTransaction(realm, finalProperInvoiceId);
                    }

                    deleted[0] = true;
                }
            });
        } else
            Log.d("OFFLINE", "--->deleteHarvestInvoice Invoice not found: " + remoteInvoiceId + "/" + localInvoiceId);

        final InvoicePost invoicePostInRealm = realm
                .where(InvoicePost.class)
                .equalTo("invoiceLocalId", invoiceInRealm.getId2())
                .findFirst();

        if (invoicePostInRealm != null) {
            Log.d("OFFLINE", "--->deleteHarvestInvoice found InvoicePost to delete: " + invoicePostInRealm);

            realm.executeTransaction(new Realm.Transaction() {
                @DebugLog
                @Override
                public void execute(Realm realm) {
                    invoicePostInRealm.deleteFromRealm();
                    deleted[0] = true;
                }
            });
        } else
            Log.d("OFFLINE", "--->deleteHarvestInvoice InvoicePost not found: " + invoiceInRealm.getId2());

        return deleted[0];
    }

    @DebugLog
    private static boolean deleteInvoiceItemsInTransaction(Realm realm, int invoiceId) {

        boolean deletedAnyHarvestOfDay = realm
                .where(InvoiceDetails.class)
                .equalTo("invoiceId", invoiceId).findAll().deleteAllFromRealm();

        Log.d("OFFLINE", "--->deletedAnyHarvestOfDay: " + deletedAnyHarvestOfDay);

        boolean deletedAnyInvoiceDetails = realm
                .where(InvoiceDetails.class)
                .equalTo("invoiceId", invoiceId).findAll().deleteAllFromRealm();

        Log.d("OFFLINE", "--->deletedAnyInvoiceDetails: " + deletedAnyInvoiceDetails);

        return (deletedAnyHarvestOfDay || deletedAnyInvoiceDetails);
        //return false; //borrar
    }

    @DebugLog
    public static List<InvoicePost> getPendingInvoicePostsList(boolean isForInvoicesToAdd) {
        Realm realm = Realm.getDefaultInstance();

        List<InvoicePost> invoicePostList = new ArrayList<>();

        List<Invoice> invoiceList;

        if (isForInvoicesToAdd) {
            invoiceList = realm.where(Invoice.class)
                    .equalTo("deleteOffline", false)
                    .equalTo("addOffline", true)
                    .findAllSorted("invoiceStartDate");
        } else {
            invoiceList = realm.where(Invoice.class)
                    .equalTo("deleteOffline", false)
                    .equalTo("addOffline", false)
                    .equalTo("editOffline", true)
                    .findAllSorted("invoiceStartDate");
        }

        Log.d("PURITIES", "--->getPendingInvoicePostsList invoiceList: " + invoiceList);

        if (invoiceList != null) {

            invoiceList = realm.copyFromRealm(invoiceList);

            for (Invoice invoice : invoiceList) {

                List<InvoicePost> invoicePostsOfInvoice;

                if (invoice.isAddOffline()) {
                    invoicePostsOfInvoice = realm.where(InvoicePost.class)
                            .equalTo("invoiceLocalId", invoice.getId2())
                            .findAllSorted("startDate");
                } else {
                    invoicePostsOfInvoice = realm.where(InvoicePost.class)
                            .equalTo("invoiceId", invoice.getInvoiceId())
                            .findAllSorted("startDate");
                }

                if (invoicePostsOfInvoice == null) {
                    Log.d("PURITIES", "--->getPendingInvoicePostsList invoicePostsOfInvoice NULL");
                    continue;
                } else {
                    Log.d("PURITIES", "--->getPendingInvoicePostsList invoicePostsOfInvoice: " + invoicePostsOfInvoice);
                }

                invoicePostsOfInvoice = realm.copyFromRealm(invoicePostsOfInvoice);

                for (InvoicePost invoicePost : invoicePostsOfInvoice) {
                    List<ItemPost> itemsPostsOfInvoice = realm.where(ItemPost.class).equalTo("invoicePostLocalId", invoicePost.getInvoicePostLocalId()).findAllSorted("itemTypeId");
                    if (itemsPostsOfInvoice != null) {
                        invoicePost.setItems(realm.copyFromRealm(itemsPostsOfInvoice));
                        for (ItemPost itemPost : invoicePost.getItems()) {

                            List<PurityPost> puritiesPostsOfInvoice = realm
                                    .where(PurityPost.class)
                                    .equalTo("itemPostLocalId", itemPost.getItemPostLocalId())
                                    .findAllSorted("purityId");

                            if (puritiesPostsOfInvoice != null) {
                                puritiesPostsOfInvoice = realm.copyFromRealm(puritiesPostsOfInvoice);
                                itemPost.setPurities(puritiesPostsOfInvoice);
                            }
                        }
                    }

                    invoicePostList.add(invoicePost);
                }
            }
            return invoicePostList;
        } else {
            return invoicePostList;
        }

    }

    @DebugLog
    public static boolean findAndDeleteLocalInvoicePost(InvoicePost invoicePostParam) {
        Realm realm = Realm.getDefaultInstance();

        InvoicePost foundInvoicePost = realm.where(InvoicePost.class)
                .equalTo("invoicePostLocalId", invoicePostParam.getInvoicePostLocalId())
                .findFirst();

        if (foundInvoicePost != null) {
            try {
                realm.beginTransaction();
                foundInvoicePost.deleteFromRealm();
                realm.commitTransaction();
                return true;
            } catch (Exception e) {
                Log.e("PURITIES", "Error deleting InvoicePost: " + e + "// InvoicePost: " + foundInvoicePost);
                e.printStackTrace();
            }
        }

        return false;
    }
}
