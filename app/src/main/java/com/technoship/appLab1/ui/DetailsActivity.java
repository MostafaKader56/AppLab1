package com.technoship.appLab1.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;

import com.bumptech.glide.Glide;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.technoship.appLab1.utils.Constants;
import com.technoship.appLab1.R;
import com.technoship.appLab1.databinding.ActivityDetailsBinding;

public class DetailsActivity extends AppCompatActivity {

    private ActivityDetailsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setTitle(getString(R.string.detailsActivityTitle));

        Bundle bundle = getIntent().getExtras();

        if (bundle == null) finish();

        if (!bundle.containsKey(Constants.IMAGE_LINK_KEY_DETAILS_ACTIVITY) || bundle.getString(Constants.IMAGE_LINK_KEY_DETAILS_ACTIVITY) == null){
            binding.img.setVisibility(View.GONE);
        }
        else {
            Glide.with(this).load(bundle.getString(Constants.IMAGE_LINK_KEY_DETAILS_ACTIVITY))
                    .into(binding.img);
        }

        if (bundle.containsKey(Constants.OFFER_ID_FOR_QR_CODE_DETAILS_ACTIVITY)){
            itsOfferOneTimeUse(bundle.getString(Constants.OFFER_ID_FOR_QR_CODE_DETAILS_ACTIVITY)+Constants.getLabClientId(DetailsActivity.this));
        }

        binding.title.setText(bundle.getString(Constants.TITLE_KEY_DETAILS_ACTIVITY));
        binding.details.setText(bundle.getString(Constants.DETAILS_KEY_DETAILS_ACTIVITY));
    }

    private void itsOfferOneTimeUse(String offerId) {
//        this.offerId = offerId;
//        private String offerId;
        View qrCodeBlock = binding.qrCodeBlock;
        qrCodeBlock.setVisibility(View.VISIBLE);
        qrCodeBlock.setOnClickListener(view -> {
            DisplayMetrics displayMetrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            int dpWidth = 300 ;
            int dpHeight = 300 ;

            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            try {
                BitMatrix bitMatrix = qrCodeWriter.encode(offerId, BarcodeFormat.QR_CODE, dpWidth, dpHeight);
                Bitmap bitmap = Bitmap.createBitmap(dpWidth, dpHeight, Bitmap.Config.RGB_565);
                for (int x = 0; x<dpWidth; x++){
                    for (int y=0; y<dpHeight; y++){
                        bitmap.setPixel(x,y,bitMatrix.get(x,y)? Color.BLACK : Color.WHITE);
                    }
                }
                Constants.createQrCodeDialog(DetailsActivity.this, bitmap);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

}
