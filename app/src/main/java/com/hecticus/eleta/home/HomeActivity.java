package com.hecticus.eleta.home;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.hecticus.eleta.R;
import com.hecticus.eleta.harvest.list.HarvestsListFragment;
import com.hecticus.eleta.internet.InternetManager;
import com.hecticus.eleta.login.LoginActivity;
import com.hecticus.eleta.model.response.harvest.Harvest;
import com.hecticus.eleta.model_new.GlobalRequests;
import com.hecticus.eleta.model_new.Provider;
import com.hecticus.eleta.model_new.SessionManager;
import com.hecticus.eleta.model_new.SyncManager;
import com.hecticus.eleta.model_new.persistence.Migrations;
import com.hecticus.eleta.provider.list.ProvidersListFragment;
import com.hecticus.eleta.purchases.list.PurchasesListFragment;
import com.hecticus.eleta.util.Constants;
import com.hecticus.eleta.util.RealmBackup;
import com.hecticus.eleta.util.Util;

import net.danlew.android.joda.JodaTimeAndroid;

import java.util.ArrayList;
import java.util.List;

import hugo.weaving.DebugLog;
import io.realm.Realm;
import io.realm.RealmConfiguration;

public class HomeActivity extends AppCompatActivity implements HomeContract.View {
//estas sobre la version que permite imprimir en cualquier momento del proceso de cosechas
    
    private SectionsPagerAdapter mSectionsPagerAdapter;

    private ViewPager mViewPager;
    private ProgressBar progressBar;
    public static HomeActivity INSTANCE;

    public static boolean reloadProviders;

    private HomePresenter mPresenter;

    @DebugLog
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        JodaTimeAndroid.init(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged_in);
        Realm.init(getApplicationContext());
        final RealmConfiguration configuration = new RealmConfiguration.Builder()
                                                        .name("coffeeleta.realm")
                                                        .schemaVersion(Constants.VERSION_DB_PROD)
                                                        //.migration(new Migrations())
                                                        /*.name("coffeEleta.realm")//
                                                        .schemaVersion(Constants.VERSION_DB_DEV)*/
                                                        .build();
        Realm.setDefaultConfiguration(configuration);

        try {
            /*Crashlytics.setUserEmail(SessionManager.getUserEmail(this));
            Crashlytics.setUserName(SessionManager.getUserName(this));*/
            Crashlytics.setUserIdentifier("12345");
            Crashlytics.setUserEmail("ingbrayanmendoza@gmail.com");
            Crashlytics.setUserName("Test User Brayan");
            Log.d("DEBUG", "init crashlytics success");
        } catch (Exception e) {
            Log.e("BUG", "--->Exception setting user data for Crashlytics: " + e);
            e.printStackTrace();
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        mSectionsPagerAdapter.addFragment(new HarvestsListFragment(), getString(R.string.harvests));
        mSectionsPagerAdapter.addFragment(new PurchasesListFragment(), getString(R.string.purchases));
        mSectionsPagerAdapter.addFragment(new ProvidersListFragment(), getString(R.string.providers));

        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        progressBar = (ProgressBar) findViewById(R.id.progress_bar);

        TabLayout tabs = (TabLayout) findViewById(R.id.result_tabs);
        tabs.setupWithViewPager(mViewPager);

        mViewPager.setOffscreenPageLimit(3);

        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            public void onPageScrollStateChanged(int state) {
            }

            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            public void onPageSelected(int position) {
                if (position == 2 /*&& reloadProviders*/) {
                    reloadProviders = false;
                    Fragment page = getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.container + ":" + mViewPager.getCurrentItem());
                    if (page != null && page instanceof ProvidersListFragment) {
                        ((ProvidersListFragment) page).refreshList();
                    }
                } else {
                    if (position == 1 ){
                        Fragment page = getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.container + ":" + mViewPager.getCurrentItem());
                        if (page != null && page instanceof PurchasesListFragment) {
                            ((PurchasesListFragment) page).refreshList();
                        }
                    } else {
                        if (position == 0 ){
                            Fragment page = getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.container + ":" + mViewPager.getCurrentItem());
                            if (page != null && page instanceof HarvestsListFragment) {
                                ((HarvestsListFragment) page).refreshList();
                            }
                        }
                    }
                }
            }
        });

        INSTANCE = this;
        new GlobalRequests(this);
        mPresenter = new HomePresenter(this, this);

        try {
            PackageInfo pInfo = this.getPackageManager().getPackageInfo(getPackageName(), 0);
            String version = pInfo.versionName;
            setTitle(getTitle() + " " + version);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Fragment page = getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.container + ":" + mViewPager.getCurrentItem());
        if (page != null && page instanceof HarvestsListFragment) {
            ((HarvestsListFragment) page).refreshList();
        }
        if (page != null && page instanceof ProvidersListFragment) {
            ((ProvidersListFragment) page).refreshList();
        }
        if (page != null && page instanceof PurchasesListFragment) {
            ((PurchasesListFragment) page).refreshList();
        }
    }

    @DebugLog
    public void goToLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_logged_in, menu);
        return true;
    }

    @DebugLog
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
        mViewPager.setEnabled(false);
    }

    @DebugLog
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
        mViewPager.setEnabled(true);
    }

    @DebugLog
    public void syncFailed(String errorMessage/*SyncManager.SyncErrorType syncErrorType*/) {
        hideProgress();

        Toast.makeText(this, "Error de sincronización:\n" + errorMessage, Toast.LENGTH_LONG).show();
        /*Toast.makeText(this, "Error de sincronización:\n" + errorMessage, Toast.LENGTH_LONG).show();
        Toast.makeText(this, "Error de sincronización:\n" + errorMessage, Toast.LENGTH_LONG).show();*/


        /*if (syncErrorType == SyncManager.SyncErrorType.DELETING_PROVIDER_WITH_OPEN_INVOICES)
            Toast.makeText(this, "Sincronización incompleta. Se intento borrar un(os) proveedor(es) con facturas aun no cerradas", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(this, "Error en sincronización. Quedaron elementos pendientes, intente nuevamente", Toast.LENGTH_LONG).show();
        */
    }

    @DebugLog
    public void syncPartial() {
        hideProgress();
        Toast.makeText(this, "!Sincronización de manera parcial!", Toast.LENGTH_LONG).show();
        Intent reloadAllTabsIntent = new Intent();
        reloadAllTabsIntent.putExtra("reloadThreeTabs", true);
        onNewIntent(reloadAllTabsIntent);
    }

    @DebugLog
    public void syncSuccessful(int failedImageUploadsCount) {
        hideProgress();
        if (failedImageUploadsCount == 0)
            Toast.makeText(this, "!Sincronización exitosa!", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(this, failedImageUploadsCount + " imágen(es) no se pudieron subir. \nEl resto de la información se sincronizó exitosamente",
                    Toast.LENGTH_LONG).show();

        Intent reloadAllTabsIntent = new Intent();
        reloadAllTabsIntent.putExtra("reloadThreeTabs", true);
        onNewIntent(reloadAllTabsIntent);
    }

    @DebugLog
    public void onNothingToSync() {
        hideProgress();
        Toast.makeText(this, getString(R.string.everything_is_synced), Toast.LENGTH_LONG).show();
    }

    @DebugLog
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_logout) {
            onLogOutClicked();
            return true;
        }
        if (id == R.id.action_sync) {
            if (InternetManager.isConnected(this)) {
                showProgress();
                new SyncManager().startSync(progressBar);
            } else {
                Toast.makeText(this, getString(R.string.no_internet_connection), Toast.LENGTH_LONG).show();
            }
            return true;
        }
        if (id == R.id.action_export_data) {
            if (InternetManager.isConnected(this)) {
                new RealmBackup(HomeActivity.this).backup();
            } else {
                Toast.makeText(this, getString(R.string.no_internet_connection), Toast.LENGTH_LONG).show();
            }


            return true;
        }
         //todo comentar
        /*if (id == R.id.action_import_data) {
            new RealmBackup(HomeActivity.this).restore();
            return true;
        }*/
        return super.onOptionsItemSelected(item);
    }

    @DebugLog
    @Override
    public void onLogOutClicked() {
        mPresenter.logOut();
    }

    @DebugLog
    @Override
    public void onLogoutSuccess() {
        goToLoginActivity();
    }

    @DebugLog
    @Override
    public void onLogoutError() {
        // TODO MAYBE SAVE OFFLINE REQUEST
        Toast.makeText(this, getString(R.string.logout_request_failed), Toast.LENGTH_SHORT).show();
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //Toast.makeText(HomeActivity.this, "se cerro la app", Toast.LENGTH_SHORT).show();
    }

    @DebugLog
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        // If this is true, tabPageNumber will increase so it checks all tabs, otherwise
        // we'll only try to reload the currently selected one if it matches the desired type
        if (intent.getBooleanExtra("reloadThreeTabs", false)) {

            Log.d("RELOAD", "--->onNewIntent ALL");

            for (Fragment fragment : getSupportFragmentManager().getFragments()) {
                if (fragment != null) {
                    if (fragment instanceof HarvestsListFragment) {
                        Log.d("RELOAD", "--->HarvestsListFragment refreshList");
                        ((HarvestsListFragment) fragment).refreshList();
                    } else if (fragment instanceof PurchasesListFragment) {
                        Log.d("RELOAD", "--->PurchasesListFragment refreshList");
                        ((PurchasesListFragment) fragment).refreshList();
                    } else if (fragment instanceof ProvidersListFragment) {
                        Log.d("RELOAD", "--->ProvidersListFragment refreshList");
                        ((ProvidersListFragment) fragment).refreshList();
                    }
                }
            }
        } else {

            Log.d("RELOAD", "--->onNewIntent ONE");

            if (intent.getBooleanExtra("reloadHarvests", false)) {
                Fragment page = getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.container + ":" + mViewPager.getCurrentItem());
                if (page != null && page instanceof HarvestsListFragment) {
                    ((HarvestsListFragment) page).refreshList();
                }
                return;
            }

            if (intent.getBooleanExtra("reloadPurchases", false)) {
                Fragment page = getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.container + ":" + mViewPager.getCurrentItem());
                if (page != null && page instanceof PurchasesListFragment) {
                    ((PurchasesListFragment) page).refreshList();
                }
                return;
            }

            if (intent.getBooleanExtra("reloadProviders", false)) {
                Fragment page = getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.container + ":" + mViewPager.getCurrentItem());
                if (page != null && page instanceof ProvidersListFragment) {
                    ((ProvidersListFragment) page).refreshList();
                    if (intent.getBooleanExtra("providerSaved", false)) {
                        ((ProvidersListFragment) page).showMessage(getString(R.string.provider_saved));
                    }
                }
                return;
            }
        }

        if (intent.getBooleanExtra("invalidToken", false)) {
            goToLoginActivity();
        }
    }







}
