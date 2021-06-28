package com.technoship.appLab1.ui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.technoship.appLab1.R;
import com.technoship.appLab1.databinding.ActivityResultBinding;
import com.technoship.appLab1.models.ResultRequest;
import com.technoship.appLab1.utils.Constants;

import java.io.ByteArrayOutputStream;
import java.util.Objects;
import java.util.UUID;

public class ResultActivity extends AppCompatActivity {

//    private static final int LOCATION_REQUEST_CODE = 1111;
    private ActivityResultLauncher<Intent> activityResultLauncherCaptureImage, activityResultLauncherSelectImage;
//            , activityResultLauncherActivateGps;
    private Button attachButton;
    private String imgUrl;
    private TextInputLayout nameLayout, phoneLayout;
    private TextInputEditText nameEditText, phoneEditText, notesEditText;

//    private LocationRequest locationRequest;
//    private LocationCallback locationCallback;
//    private FusedLocationProviderClient fusedLocationProviderClient;

    private FirebaseFirestore db;

    private ProgressDialog pd;
    
    private ActivityResultBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityResultBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setTitle(getString(R.string.resultRequest_title));
        
        init();

        attachButton.setOnClickListener(view -> new AlertDialog.Builder(ResultActivity.this)
                .setTitle(R.string.sendingPictureTitle)
                .setMessage(R.string.sendingPictureMessage)
                .setPositiveButton(R.string.sendingPictureButton1, (dialogInterface, i) -> {
                    Intent intent = new Intent();
                    intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
                    activityResultLauncherCaptureImage.launch(intent);
                })
                .setNegativeButton(R.string.sendingPictureButton2, (dialogInterface, i) -> {
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    activityResultLauncherSelectImage.launch(intent);
                })
                .setNeutralButton(R.string.cancel, (dialogInterface, i) -> { })
                .create().show());

        activityResultLauncherCaptureImage = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        assert data != null;
                        Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                        uploadImage(bitmap);
                    }
                    else {
                        Toast.makeText(ResultActivity.this, getString(R.string.imageNotSelected), Toast.LENGTH_SHORT).show();
                    }
                });

        activityResultLauncherSelectImage = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        assert data != null;
                        uploadImage(data.getData());
                    }
                    else {
                        Toast.makeText(ResultActivity.this, getString(R.string.imageNotSelected), Toast.LENGTH_SHORT).show();
                    }
                });

//        activityResultLauncherActivateGps = registerForActivityResult(
//                new ActivityResultContracts.StartActivityForResult(),
//                result -> {
//                    if (result.getResultCode() == Activity.RESULT_OK) {
//                        submitButtonClicked(null);
//                    }
//                });
    }



    private void uploadImage(Bitmap bitmapImage) {
        pd.setTitle(getString(R.string.uploadingImage));
        pd.show();

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmapImage.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] imageBytes = byteArrayOutputStream.toByteArray();

        String randomKey = UUID.randomUUID().toString();
        StorageReference ref = FirebaseStorage.getInstance().getReference()
                .child(Constants.dateForFileStorageReference()+"/ResultRequest/"+randomKey);

        ref.putBytes(imageBytes)
                .addOnFailureListener(e -> {
                    pd.dismiss();
                    Constants.unExpectedError(ResultActivity.this, e);
                })
                .addOnProgressListener(snapshot -> {
                    double progressPercentage = (100.0 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                    pd.setMessage(getString(R.string.percentage)+ (int) progressPercentage +"%");
                })
                .addOnSuccessListener(taskSnapshot -> ref.getDownloadUrl().addOnSuccessListener(uri -> {
                    pd.dismiss();
                    attachButton.setTextColor(getApplication().getResources().getColor(R.color.yellowGold));
                    setImageUrl(uri);
                    Toast.makeText(ResultActivity.this, getString(R.string.imageUploaded), Toast.LENGTH_SHORT).show();
                }).addOnFailureListener(e -> {
                    pd.dismiss();
                    Constants.unExpectedError(ResultActivity.this, e);
                }));
    }

    private void uploadImage(Uri imageUri){
        pd.setTitle(R.string.uploadingImage);
        pd.show();

        String randomKey = UUID.randomUUID().toString();
        StorageReference ref = FirebaseStorage.getInstance().getReference()
                .child(Constants.dateForFileStorageReference()+"/ResultRequest/"+randomKey);

        ref.putFile(imageUri)
                .addOnFailureListener(e -> {
                    pd.dismiss();
                    Constants.unExpectedError(ResultActivity.this, e);
                })
                .addOnProgressListener(snapshot -> {
                    double progressPercentage = (100.0 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                    pd.setMessage(getString(R.string.percentage)+ (int) progressPercentage +"%");
                })
                .addOnSuccessListener(taskSnapshot -> ref.getDownloadUrl().addOnSuccessListener(uri -> {
                    pd.dismiss();
                    attachButton.setTextColor(getApplication().getResources().getColor(R.color.yellowGold));
                    setImageUrl(uri);
                    Snackbar snackBar = Snackbar.make(binding.getRoot(), getString(R.string.imageUploaded), Snackbar.LENGTH_LONG);
                    snackBar.setAction(getString(R.string.undo), view -> {
                        imgUrl = null;
                        attachButton.setTextColor(getApplication().getResources().getColor(R.color.white));
                        Snackbar.make(binding.getRoot(), getString(R.string.imageCanceled), Snackbar.LENGTH_LONG).show();
                        snackBar.dismiss();
                    });
                    snackBar.show();
                }).addOnFailureListener(e -> {
                    pd.dismiss();
                    Constants.unExpectedError(ResultActivity.this, e);
                }));
    }

    private void setImageUrl(Uri uri) {
        imgUrl = uri.toString();
    }

    private void init(){
        attachButton = binding.attachButton;
        db = FirebaseFirestore.getInstance();

        nameLayout = binding.inputLayoutName;
        phoneLayout = binding.inputLayoutPhone;
        nameEditText = binding.inputEditTextName;
        nameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                nameLayout.setError(null);
            }
        });
        phoneEditText = binding.inputEditTextPhone;
        phoneEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                phoneLayout.setError(null);
            }
        });
        notesEditText = binding.inputEditTextNotes;

        pd = new ProgressDialog(ResultActivity.this);
        pd.setCancelable(false);

//        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
//        locationRequest = LocationRequest.create();
//        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
//        locationRequest.setInterval(20 * 1000);
    }

    public void submitButtonClicked(View view) {
        String name = Objects.requireNonNull(nameEditText.getText()).toString().trim();
        String phone = Objects.requireNonNull(phoneEditText.getText()).toString().trim();
        String notes = Objects.requireNonNull(notesEditText.getText()).toString().trim();

        if (name.isEmpty()){
            nameLayout.setError(getString(R.string.fieldRequired));
            return;
        }
        else if (phone.isEmpty()){
            phoneLayout.setError(getString(R.string.fieldRequired));
            return;
        }
        else if (phone.length() < 11){
            phoneLayout.setError(getString(R.string.wrongPhone));
            return;
        }

        ResultRequest resultRequest = new ResultRequest(name, phone, notes, imgUrl);
        pd.setMessage(getString(R.string.loadingTextSendOrder));
        pd.show();
        startSend(resultRequest);

//        if (ActivityCompat.checkSelfPermission(ResultActivity.this,
//                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
//
//            if (!Constants.isGPS_TurnedOn(ResultActivity.this)){
//                final AlertDialog alert = new AlertDialog.Builder(this)
//                        .setTitle(R.string.gpsDisabledTitle)
//                        .setMessage(R.string.gpsDisabledMessage)
//                        .setPositiveButton(R.string.yes, (dialog, id) -> {
//                            activityResultLauncherActivateGps.launch(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
//                            Toast.makeText(this, getString(R.string.enableGps), Toast.LENGTH_LONG).show(); })
//                        .setNegativeButton(R.string.no, (dialogInterface, i) -> { }).create();
//                alert.show();
//            }
//            else {
//                pd.show();
//                pd.setMessage(getString(R.string.loadingTextGetLocation));
//
//                locationCallback = new LocationCallback() {
//                    @Override
//                    public void onLocationResult(@NonNull LocationResult locationResult) {
//                        fusedLocationProviderClient.removeLocationUpdates(locationCallback);
//                        Location location = locationResult.getLastLocation();
//                        resultRequest.setLat(location.getLatitude());
//                        resultRequest.setLng(location.getLongitude());
//                        pd.setMessage(getString(R.string.loadingTextSendOrder));
//                    }
//                };
//
//                fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper());
//            }
//        }
//        else {
//            AlertDialog dialog = new AlertDialog.Builder(ResultActivity.this)
//                    .setTitle(R.string.location)
//                    .setMessage(R.string.gpsNotAllowedMessage)
//                    .setPositiveButton(R.string.ok, (dialogInterface, i) -> { })
//                    .setOnDismissListener(dialogInterface -> ActivityCompat.requestPermissions(ResultActivity.this,
//                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_REQUEST_CODE))
//                    .create();
//            dialog.show();
//        }
    }

    private void startSend(ResultRequest homeVisit) {
        db.collection(Constants.RESULT_REQUEST_FIRESTORE).document(Constants.dateForId()).set(homeVisit)
                .addOnSuccessListener(ResultActivity.this, aVoid -> {
                    pd.dismiss();
                    Intent intent = new Intent();
                    intent.putExtra("DONE", true);
                    setResult(RESULT_OK, intent);
                    Constants.hideKeyboard(ResultActivity.this);
                    onBackPressed();
                });
    }

    public void attachButtonClicked(View view) {
        new AlertDialog.Builder(this)
                .setTitle(R.string.sendPictureTitle)
                .setMessage(R.string.sendPictureMessage)
                .setPositiveButton(R.string.sendPictureButton1, (dialogInterface, i) -> {
                    Intent intent = new Intent();
                    intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
                    activityResultLauncherCaptureImage.launch(intent);
                })
                .setNegativeButton(R.string.sendPictureButton2, (dialogInterface, i) -> {
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    activityResultLauncherSelectImage.launch(intent);
                })
                .setNeutralButton(R.string.cancel, (dialogInterface, i) -> { })
                .create().show();
    }
}