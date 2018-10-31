package com.hecticus.eleta.provider.detail;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;
import com.hecticus.eleta.R;
import com.hecticus.eleta.base.BaseActivity;
import com.hecticus.eleta.custom_views.CustomEditText;
import com.hecticus.eleta.home.HomeActivity;
import com.hecticus.eleta.internet.InternetManager;
import com.hecticus.eleta.model.StatusProvider;
import com.hecticus.eleta.model.response.providers.Provider;
import com.hecticus.eleta.model.response.providers.ProviderType;
import com.hecticus.eleta.model_new.SessionManager;
import com.hecticus.eleta.model_new.persistence.ManagerDB;
import com.hecticus.eleta.util.Constants;
import com.hecticus.eleta.util.FileUtils;
import com.hecticus.eleta.util.GlideApp;
import com.hecticus.eleta.util.PermissionUtil;
import com.hecticus.eleta.util.Util;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hugo.weaving.DebugLog;

public class ProviderDetailsActivity extends BaseActivity implements ProviderDetailsContract.View {

    private static final int REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS = 1;
    private Integer id;

    @BindView(R.id.provider_detail_text_edit_address)
    CustomEditText addressEditText;

    @BindView(R.id.provider_detail_text_edit_contact)
    CustomEditText contactEditText;

    @BindView(R.id.provider_detail_text_edit_dni)
    CustomEditText dniEditText;

    @BindView(R.id.provider_detail_text_edit_email)
    CustomEditText emailEditText;

    @BindView(R.id.provider_detail_text_edit_name)
    CustomEditText nameEditText;

    @BindView(R.id.provider_detail_text_edit_phone)
    CustomEditText phoneEditText;

    @BindView(R.id.custom_header_w_des_image_button)
    ImageButton selectImageButton;

    @BindView(R.id.custom_header_w_des_description_text_view)
    TextView descriptionHeaderTextView;

    @BindView(R.id.custom_header_w_des_title_text_view)
    TextView titleHeaderTextView;


    @BindView(R.id.custom_send_button)
    Button sendButton;

    private String takenOrPickedImagePath = null;
    private ProviderDetailsContract.Actions mPresenter;

    private boolean isForProviderCreation;
    private boolean fromProvidersList;
    private Provider initialProvider;

    @DebugLog
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider_details);
        ButterKnife.bind(this);

        isForProviderCreation = getIntent().getBooleanExtra("isForProviderCreation", false);
        boolean canEdit = getIntent().getBooleanExtra("canEdit", false);
        boolean isHarvester = getIntent().getBooleanExtra("isHarvester", false);
        fromProvidersList = getIntent().getBooleanExtra("fromProvidersList", true);

        /*if (getIntent().getStringExtra("provider") != null) {//(getIntent().getIntExtra("provider",-1) != -1) {//
            //initialProvider = ManagerDB.getProviderById(getIntent().getIntExtra("provider",-1));//new Gson().fromJson(getIntent().getStringExtra("provider"), Provider.class);
            //mPresenter.
            initialProvider = new Gson().fromJson(getIntent().getStringExtra("provider"), Provider.class);
            //LogDataBase.d("DEBUG intent 2",  initialProvider.getFullNameProvider());
        }*/


        try {
            if (InternetManager.isConnected(this) && !getIntent().getBooleanExtra("control", false)) {
                if (getIntent().getStringExtra("provider") != null) {
                    initialProvider = new Gson().fromJson(getIntent().getStringExtra("provider"), Provider.class);
                }
            } else {
                if (getIntent().getIntExtra("provider", -1) != -1) {
                    initialProvider = ManagerDB.getProviderById(getIntent().getIntExtra("provider", -1));

                    /*details.add(ManagerDB.getInvoiceDetailById(getIntent().getIntExtra("details", -1)));
                    LogDataBase.d("DEBUG", "lote" + String.valueOf(details.get(0).getLotId()));
                    details.get(0).setLot(ManagerDB.getLotById(details.get(0).getLotId()));
                    details.get(0).setItemType(new ItemType(details.get(0).getItemTypeId()));*/
                } else {
                    initialProvider = ManagerDB.getProviderByIdentificationDoc(getIntent().getStringExtra("providerLocal"));

                    /*details.add(ManagerDB.getInvoiceDetailByIdLocal(getIntent().getIntExtra("providerLocal", -1)));
                    LogDataBase.d("DEBUG", "lote" + String.valueOf(details.get(0).getLotId()));
                    details.get(0).setLot(ManagerDB.getLotById(details.get(0).getLotId()));
                    details.get(0).setItemType(new ItemType(details.get(0).getItemTypeId()));*/
                }
            }
        }catch (Exception e){

        }





        mPresenter = new ProviderDetailsPresenter(this, this, initialProvider, isForProviderCreation, canEdit, isHarvester);
        initViews();
        if (initialProvider != null)
            //loadProviderImage(initialProvider.getPhotoProvider());todo img
            try {
                loadProviderImage(initialProvider.getMultimediaProfile().getMultimediaCDN().getUrl(), initialProvider.getMediaBase64());
            }catch (Exception e){}
        mPresenter.initFields();
    }

    @DebugLog
    private void loadProviderImage(String imageUrl, String base64) {
        if(!InternetManager.isConnected(this)){
            Util.loadImageFromBase64(base64, selectImageButton);
        } else {
            GlideApp
                    .with(this)
                    .load(imageUrl)
                    .error(R.mipmap.picture)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .centerCrop()
                    .circleCrop()
                    .into(selectImageButton);
        }
    }

    @DebugLog
    @Override
    public void initViews() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (mPresenter.isHarvester()) {
            dniEditText.initWithTypeAndDescription(CustomEditText.Type.DNI, getString(R.string.DNI));
            nameEditText.initWithDescription(getString(R.string.full_name));
            contactEditText.init();
            contactEditText.setVisibility(View.GONE);
        } else {
            dniEditText.initWithTypeAndDescription(CustomEditText.Type.RUC, getString(R.string.RUC));
            nameEditText.initWithDescription(getString(R.string.name));
            contactEditText.initWithDescription(getString(R.string.contact));
        }

        addressEditText.initWithDescription(getString(R.string.address));
        phoneEditText.initWithTypeAndDescription(CustomEditText.Type.PHONE, getString(R.string.phone));
        emailEditText.initWithTypeAndDescription(CustomEditText.Type.EMAIL, getString(R.string.email));

        initString();
    }

    @Override
    public void initString() {
        if (isForProviderCreation) {
            descriptionHeaderTextView.setText(getString(R.string.create));
            titleHeaderTextView.setText(getString(R.string.new_provider));
        } else {
            descriptionHeaderTextView.setText(getString(R.string.edit));
            titleHeaderTextView.setText(getString(R.string.old_provider));
        }
        sendButton.setText(getString(R.string.save));
    }

    @Override
    public void showWorkingIndicator() {
        mainLinearLayout.setEnabled(false);
        mainLinearLayout.setAlpha(0.5f);
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideWorkingIndicator() {
        mainLinearLayout.setEnabled(true);
        mainLinearLayout.setAlpha(1f);
        progressBar.setVisibility(View.GONE);
    }

    @DebugLog
    @Override
    public void onProviderSaved(Provider providerParam) {
        if (isForProviderCreation)
            returnProvider(providerParam);
        else {
            showMessage(getString(R.string.provider_saved));
            updateFields(providerParam);
            hideWorkingIndicator();
            updateMenuOptions();
            finish();
        }
    }



    /*@Override
    public void fin(){
        hideWorkingIndicator();
        finish();
    }*/

    @Override
    public void showMessage(String message) {
        if (mainLinearLayout != null)
            Snackbar.make(mainLinearLayout, message, Snackbar.LENGTH_LONG).setAction("Action", null).show();
    }

    @DebugLog
    @Override
    public void enableEdition(boolean enabled) {
        dniEditText.setEnable(enabled);
        nameEditText.setEnable(enabled);
        contactEditText.setEnable(enabled);
        addressEditText.setEnable(enabled);
        phoneEditText.setEnable(enabled);
        emailEditText.setEnable(enabled);

        selectImageButton.setVisibility(enabled ? View.VISIBLE : View.GONE);
    }

    @DebugLog
    @Override
    public Provider getProviderFromFields() {
        Provider maybeModifiedProvider = new Provider();

        if (initialProvider != null)
            maybeModifiedProvider.setIdentificationDocProviderChange(initialProvider.getIdentificationDocProvider());

        //maybeModifiedProvider.setProviderType();

        if(dniEditText.getText().trim().isEmpty()) {
            //int numero = (int) (Math.random() * 99) + 1;
            maybeModifiedProvider.setIdentificationDocProvider(SessionManager.getUserId(ProviderDetailsActivity.this)+"-"+ Util.parseDateDni(Calendar.getInstance().getTime()) /*+numero*/);
        }else{
            maybeModifiedProvider.setIdentificationDocProvider(dniEditText.getText().trim());
        }

        maybeModifiedProvider.setFullNameProvider(nameEditText.getText().trim());

        maybeModifiedProvider.setAddressProvider(addressEditText.getText().trim());

        maybeModifiedProvider.setPhoneNumberProvider(phoneEditText.getText().trim());

        maybeModifiedProvider.setEmailProvider(emailEditText.getText().trim());

        if (mPresenter.isHarvester()) {
            maybeModifiedProvider.setContactNameProvider(maybeModifiedProvider.getFullNameProvider());
            maybeModifiedProvider.setProviderType(new ProviderType(Constants.TYPE_HARVESTER));
            maybeModifiedProvider.setIdProviderType(Constants.TYPE_HARVESTER);
        } else {
            maybeModifiedProvider.setProviderType(new ProviderType(Constants.TYPE_SELLER));
            maybeModifiedProvider.setIdProviderType(Constants.TYPE_SELLER);
            maybeModifiedProvider.setContactNameProvider(contactEditText.getText().trim());
        }


        maybeModifiedProvider.setStatusProvider(new StatusProvider(41, false, "Activa", null));

        return maybeModifiedProvider;
    }

    @DebugLog
    @Override
    public void updateFields(Provider provider) {
        try {
            loadProviderImage(provider.getMultimediaProfile().getMultimediaCDN().getUrl(), provider.getMediaBase64());
            id = provider.getMultimediaProfile().getId();
        }catch (Exception e){
            Log.e("DEBUGERROR", "la url esta null");
        }

        dniEditText.setText(provider.getIdentificationDocProvider());
        nameEditText.setText(provider.getFullNameProvider());
        addressEditText.setText(provider.getAddressProvider());
        phoneEditText.setText(provider.getPhoneNumberProvider());
        emailEditText.setText(provider.getEmailProvider());
        contactEditText.setText(provider.getContactNameProvider());
    }

    @Override
    public void updateMenuOptions() {
        //TODO
    }

    @OnClick(R.id.custom_send_button)
    @DebugLog
    @Override
    public void onClickSaveChangesButtonClicked() {

        if (validFields()) {
            mPresenter.saveProvider(getProviderFromFields(), takenOrPickedImagePath, id);
        }
    }

    @DebugLog
    private boolean validFields() {

        if (dniEditText.getText().trim().isEmpty()) {
            //todo dni no obligatorio
            if (mPresenter.isHarvester()) {
                /*int numero = (int) (Math.random() * 99) + 1;
                dniEditText.setText(numero + Util.parseDateDni(Calendar.getInstance().getTime()));
                Log.d("DEBUG", numero + Util.parseDateDni(Calendar.getInstance().getTime()));
                //showMessage(getString(R.string.dni_empty));*/
            } else {
                showMessage(getString(R.string.ruc_empty));
                return false;
            }

        }


        if (nameEditText.getText().trim().isEmpty()) {
            showMessage(getString(R.string.name_empty));
            return false;
        }


        /*if (addressEditText.getText().trim().isEmpty()) {
            showMessage(getString(R.string.address_empty));
            return false;
        }*/


        /*if (phoneEditText.getText().trim().isEmpty()) {
            showMessage(getString(R.string.phone_empty));
            return false;
        }*/


        if (!mPresenter.isHarvester() && contactEditText.getText().trim().isEmpty()) {
            showMessage(getString(R.string.contact_empty));
            return false;
        }

        /*if (emailEditText.getText().trim().isEmpty()) {
            showMessage(getString(R.string.email_empty));
            return false;
        }*/
        if(!emailEditText.getText().trim().isEmpty()) {
            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(emailEditText.getText()).matches()) {
                showMessage(getString(R.string.email_invalid));
                return false;
            }
        }

        return true;
    }

    @OnClick(R.id.custom_header_w_des_image_button)
    @Override
    public void onClickSelectImage() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            List<String> permissionsNeeded = new ArrayList<>();
            final List<String> permissionsList = new ArrayList<>();


                        /*if (!addPermission(permissionsList, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                            permissionsNeeded.add("Read storage");
                            LogDataBase.d("DEBUG", "policia pide permisos1");
                        }
                        if (!addPermission(permissionsList, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                            permissionsNeeded.add("Write storage");
                            LogDataBase.d("DEBUG", "policia pide permisos2");
                        }
                        if(!addPermission(permissionsList, Manifest.permission.CAMERA)) {
                            permissionsNeeded.add("Camera");
                            LogDataBase.d("DEBUG", "policia pide permisos3");
                        }
                        if (permissionsNeeded.size() > 0) {
                            requestPermissions(permissionsList.toArray(new String[permissionsList.size()]),
                                    REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);
                        } else {
                            openImageIntent();
                        }
                         */

            Log.d("DEBUG", "policia pide permisos0");
            if (addPermission(permissionsList, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                permissionsNeeded.add("Read storage");
                Log.d("DEBUG", "policia pide permisos1");
            } else {
                if (!addPermission(permissionsList, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    permissionsNeeded.add("Read storage");
                    Log.d("DEBUG", "policia pide permisos1.1");
                }
            }
            if (addPermission(permissionsList, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                permissionsNeeded.add("Write storage");
                Log.d("DEBUG", "policia pide permisos2");
            } else {
                if (!addPermission(permissionsList, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    permissionsNeeded.add("Write storage");
                    Log.d("DEBUG", "policia pide permisos2");
                }
            }
            if(addPermission(permissionsList, Manifest.permission.CAMERA)) {
                permissionsNeeded.add("Camera");
                Log.d("DEBUG", "policia pide permisos3");
            } else {
                if(!addPermission(permissionsList, Manifest.permission.CAMERA)) {
                    permissionsNeeded.add("Camera");
                    Log.d("DEBUG", "policia pide permisos3");
                }
            }

            if (permissionsNeeded.size() > 0) {
                try {
                    Log.d("DEBUG", "permissionsNeeded.size()" + permissionsNeeded.size());
                    requestPermissions(permissionsList.toArray(new String[permissionsList.size()]),
                            REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);
                }catch (Exception e){
                    CharSequence options[] = new CharSequence[]{getResources().getString(R.string.camera),
                            getResources().getString(R.string.gallery)};

                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle(getResources().getString(R.string.image_source));
                    builder.setItems(options, new DialogInterface.OnClickListener() {
                        @DebugLog
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (which == 0) {
                                takePicture();
                            } else if (which == 1) {

                                pickGalleryImage();
                            }
                        }
                    });
                    builder.show();
                }
            } else {
                CharSequence options[] = new CharSequence[]{getResources().getString(R.string.camera),
                        getResources().getString(R.string.gallery)};

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle(getResources().getString(R.string.image_source));
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @DebugLog
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if (which == 0) {
                            takePicture();
                        } else if (which == 1) {

                            pickGalleryImage();
                        }
                    }
                });
                builder.show();
            }
        } else {
            CharSequence options[] = new CharSequence[]{getResources().getString(R.string.camera),
                    getResources().getString(R.string.gallery)};

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(getResources().getString(R.string.image_source));
            builder.setItems(options, new DialogInterface.OnClickListener() {
                @DebugLog
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    if (which == 0) {
                            takePicture();
                    } else if (which == 1) {

                            pickGalleryImage();
                    }
                }
            });
            builder.show();
        }



        /*CharSequence options[] = new CharSequence[]{getResources().getString(R.string.camera),
                getResources().getString(R.string.gallery)};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getResources().getString(R.string.image_source));
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @DebugLog
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if (which == 0) {
                    if (Build.VERSION.SDK_INT >= 23) {
                        if (PermissionUtil.isPermissionGranted(ProviderDetailsActivity.this, Manifest.permission.CAMERA) &&
                                PermissionUtil.isPermissionGranted(ProviderDetailsActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                            takePicture();
                        } else {
                            PermissionUtil.requestCameraAndStoragePermissions(ProviderDetailsActivity.this);
                        }
                    } else {
                        takePicture();
                    }
                } else if (which == 1) {
                    if (Build.VERSION.SDK_INT >= 23) {
                        if (PermissionUtil.isPermissionGranted(ProviderDetailsActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                            pickGalleryImage();
                        } else {
                            PermissionUtil.requestStoragePermission(ProviderDetailsActivity.this);
                        }
                    } else {
                        pickGalleryImage();
                    }
                }
            }
        });
        builder.show();*/
    }

    @TargetApi(Build.VERSION_CODES.M)
    private boolean addPermission(List<String> permissionsList, String permission) {
        if (ProviderDetailsActivity.this.checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
            permissionsList.add(permission);
            // Check for Rationale Option
            if (!shouldShowRequestPermissionRationale(permission)) return false;
        }
        return true;
    }

    @DebugLog
    private void pickGalleryImage() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        galleryIntent.setType("image/*");
        try {
            startActivityForResult(galleryIntent, PermissionUtil.GALLERY_IMAGE_REQUEST_CODE);
        } catch (ActivityNotFoundException anfe) {
            showMessage(getString(R.string.no_gallery_app));
            anfe.printStackTrace();
        }
    }

    @DebugLog
    private void takePicture() {
        Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File tempFolder = FileUtils.getTempFolder();

        if (tempFolder.exists() || tempFolder.mkdirs()) {
            Uri output =
                    FileProvider.getUriForFile(
                            ProviderDetailsActivity.this,
                            getPackageName() + ".provider", new File(mPresenter.getNextProviderImagePath()/*FileUtils.getTempImagePath()*/));

            takePhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT, output);
            takePhotoIntent.putExtra("android.intent.extras.CAMERA_FACING", 1);

            try {
                startActivityForResult(takePhotoIntent, PermissionUtil.TAKE_PHOTO_REQUEST_CODE);
            } catch (ActivityNotFoundException anfe) {
                showMessage(getString(R.string.no_camera_app));
                anfe.printStackTrace();
            }
        } else {
            Log.e("PHOTO", "--->Could not create " + tempFolder.getAbsolutePath());
            showMessage(getString(R.string.error_during_operation));
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case PermissionUtil.CAMERA_AND_STORAGE_PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 &&
                        grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                        grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    takePicture();
                } else {
                    showMessage(getString(R.string.camera_and_storage_permission_needed));
                }
                break;
            case PermissionUtil.STORAGE_PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    pickGalleryImage();
                } else {
                    showMessage(getString(R.string.storage_permission_needed));
                }
                break;
        }
    }

    @DebugLog
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent receivedIntent) {

        if ((requestCode == PermissionUtil.GALLERY_IMAGE_REQUEST_CODE || requestCode == PermissionUtil.TAKE_PHOTO_REQUEST_CODE)
                && resultCode == Activity.RESULT_OK) {

            if (requestCode == PermissionUtil.TAKE_PHOTO_REQUEST_CODE) {
                takenOrPickedImagePath = mPresenter.getNextProviderImagePath();//FileUtils.getTempImagePath();
                Log.d("PHOTO", "--->Photo taken: " + takenOrPickedImagePath);
            } else {
                Uri imgUri = receivedIntent.getData();
                if (imgUri == null) {
                    Log.w("PHOTO", "--->No data from intent?");
                    showMessage(getResources().getString(R.string.no_image_selected));
                    return;
                }
                takenOrPickedImagePath = FileUtils.getLocalPathGivenUriAndContext(imgUri, this);
                Log.d("PHOTO", "--->Image picked from gallery: " + takenOrPickedImagePath);
            }
            if(!InternetManager.isConnected(this)){
                Util.loadThumbnailsImageFromPath(takenOrPickedImagePath, selectImageButton);
            } else {
                GlideApp
                        .with(this)
                        .load(takenOrPickedImagePath)
                        .error(R.mipmap.picture)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true)
                        .centerCrop()
                        .circleCrop()
                        .into(selectImageButton);
            }

        } else
            Log.w("PHOTO", "--->No photo activity result?");

        super.onActivityResult(requestCode, resultCode, receivedIntent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            doBack();
        return true;
    }

    @Override
    public void onBackPressed() {
        if (!fromProvidersList) {
            finish();
        } else {
            Intent intent = new Intent(ProviderDetailsActivity.this, HomeActivity.class);
            intent.putExtra("reloadProviders", mPresenter.isUpdated());
            startActivity(intent);
            finish();
        }
    }

    public void doBack() {
        if (!fromProvidersList) {
            finish();
        } else {
            Intent mIntent = new Intent(ProviderDetailsActivity.this, HomeActivity.class);
            mIntent.putExtra("reloadProviders", mPresenter.isUpdated());
            startActivity(mIntent);
            finish();
        }
    }

    @DebugLog
    @Override
    public void returnProvider(Provider provider) {
        if (!fromProvidersList) {
            try {
                HomeActivity.reloadProviders = true;
                Intent intent = new Intent();
                ManagerDB.saveNewProvider(provider);
                intent.putExtra("provider",Util.getGson().toJson(provider));
                intent.putExtra("idProvider", provider.getIdProvider());//Util.getGson().toJson(provider));
                setResult(RESULT_OK, intent);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } finally {
                finish();
            }
        } else {
            Intent mIntent = new Intent(ProviderDetailsActivity.this, HomeActivity.class);
            mIntent.putExtra("reloadProviders", mPresenter.isUpdated());
            mIntent.putExtra("providerSaved", true);
            startActivity(mIntent);
            finish();
        }
    }

    @Override
    public void invalidToken() {
        Intent intent = new Intent(this, HomeActivity.class);
        intent.putExtra("invalidToken", true);
        startActivity(intent);
        finish();
    }

}
