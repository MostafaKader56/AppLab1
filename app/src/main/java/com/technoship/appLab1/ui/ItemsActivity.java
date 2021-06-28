package com.technoship.appLab1.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.akexorcist.localizationactivity.ui.LocalizationActivity;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.technoship.appLab1.utils.Constants;
import com.technoship.appLab1.R;
import com.technoship.appLab1.adapter.HealthTipsAdapter;
import com.technoship.appLab1.adapter.announcementAdapter.AnnouncementAdapter;
import com.technoship.appLab1.adapter.announcementAdapter.AnnouncementAdapterAr;
import com.technoship.appLab1.adapter.branchesAdapter.BranchesAdapter;
import com.technoship.appLab1.adapter.branchesAdapter.BranchesAdapterAr;
import com.technoship.appLab1.adapter.ExaminationPrecautionsAdapter;
import com.technoship.appLab1.adapter.offersAdapter.OffersAdapter;
import com.technoship.appLab1.adapter.offersAdapter.OffersAdapterAr;
import com.technoship.appLab1.adapter.packagesAdapter.PackagesAdapter;
import com.technoship.appLab1.adapter.packagesAdapter.PackagesAdapterAr;
import com.technoship.appLab1.databinding.ActivityItemsBinding;
import com.technoship.appLab1.utils.LocalizationLanguageController;
import com.technoship.appLab1.models.Announcement;
import com.technoship.appLab1.models.Branch;
import com.technoship.appLab1.models.ExaminationPrecaution;
import com.technoship.appLab1.models.HealthTip;
import com.technoship.appLab1.models.LoadedHealthTip;
import com.technoship.appLab1.models.Offer;
import com.technoship.appLab1.models.Package;

import java.util.ArrayList;

public class ItemsActivity extends LocalizationActivity {

    private BranchesAdapterAr adapter21;
    private BranchesAdapter adapter22;
    private PackagesAdapterAr adapter41;
    private PackagesAdapter adapter42;
    private OffersAdapterAr adapter11;
    private OffersAdapter adapter12;
    private AnnouncementAdapterAr adapter51;
    private AnnouncementAdapter adapter52;
    private TextView emptyTip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        com.technoship.appLab1.databinding.ActivityItemsBinding binding = ActivityItemsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Bundle bundle = getIntent().getExtras();

        if (bundle == null) finish();

        setTitle(bundle.getString(Constants.TITLE_KEY_ITEMS_ACTIVITY));

        RecyclerView recyclerView = binding.recyclerview;
        emptyTip = binding.emptyTip;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        switch (bundle.getInt(Constants.ID_VIEW_KEY_ITEMS_ACTIVITY)){
            case 1:
                // Offers
                // DYNAMIC
                FirebaseFirestore db = FirebaseFirestore.getInstance();

                //Query
                Query query1 = db.collection(Constants.OFFERS_FIRESTORE).whereEqualTo(Constants.PUBLISHED_FIRESTORE, true);
                FirestoreRecyclerOptions<Offer> options1 = new FirestoreRecyclerOptions.Builder<Offer>()
                        .setQuery(query1, Offer.class)
                        .build();

                if (LocalizationLanguageController.isArabic()){
                    adapter11 = new OffersAdapterAr(options1);
                    adapter11.setOnItemClickListener(new OffersAdapterAr.OnItemClickListener() {
                        @Override
                        public void onEmpty(int itemsNum) {
                            if (itemsNum == 0){
                                emptyTip.setVisibility(View.VISIBLE);
                                emptyTip.setText(R.string.noOffersTip);
                            }
                            else {
                                emptyTip.setVisibility(View.GONE);
                            }
                        }

                        @Override
                        public void onClicked(Offer offer) {
                            Bundle offerBundle = new Bundle();
                            offerBundle.putString(Constants.TITLE_KEY_DETAILS_ACTIVITY, offer.getTitleAr());
                            offerBundle.putString(Constants.DETAILS_KEY_DETAILS_ACTIVITY, offer.getDetailsAr());
                            offerBundle.putString(Constants.IMAGE_LINK_KEY_DETAILS_ACTIVITY, offer.getImage());
                            if (offer.isOneTimeUse()){ offerBundle.putString(Constants.OFFER_ID_FOR_QR_CODE_DETAILS_ACTIVITY, offer.getOfferId()); }
                            startActivity(new Intent(ItemsActivity.this, DetailsActivity.class).putExtras(offerBundle));
                        }
                    });
                    recyclerView.setAdapter(adapter11);
                }
                else {
                    adapter12 = new OffersAdapter(options1);
                    adapter12.setOnItemClickListener(new OffersAdapter.OnItemClickListener() {
                        @Override
                        public void onEmpty(int itemsNum) {
                            if (itemsNum == 0){
                                emptyTip.setVisibility(View.VISIBLE);
                                emptyTip.setText(R.string.noOffersTip);
                            }
                            else {
                                emptyTip.setVisibility(View.GONE);
                            }
                        }

                        @Override
                        public void onClicked(Offer offer) {
                            Bundle offerBundle = new Bundle();
                            offerBundle.putString(Constants.TITLE_KEY_DETAILS_ACTIVITY, offer.getTitle());
                            offerBundle.putString(Constants.DETAILS_KEY_DETAILS_ACTIVITY, offer.getDetails());
                            offerBundle.putString(Constants.IMAGE_LINK_KEY_DETAILS_ACTIVITY, offer.getImage());
                            if (offer.isOneTimeUse()){ offerBundle.putString(Constants.OFFER_ID_FOR_QR_CODE_DETAILS_ACTIVITY, offer.getOfferId()); }
                            startActivity(new Intent(ItemsActivity.this, DetailsActivity.class).putExtras(offerBundle));
                        }
                    });


                    recyclerView.setAdapter(adapter12);
                }

                break;
            case 2:
                // Branches
                // DYNAMIC
                db = FirebaseFirestore.getInstance();

                //Query
                Query query2 = db.collection(Constants.BRANCHES_FIRESTORE).whereEqualTo(Constants.PUBLISHED_FIRESTORE, true);
                FirestoreRecyclerOptions<Branch> options2=new FirestoreRecyclerOptions.Builder<Branch>()
                        .setQuery(query2, Branch.class)
                        .build();

                if (LocalizationLanguageController.isArabic()){
                    adapter21 = new BranchesAdapterAr(options2);
                    adapter21.setOnItemClickListener(new BranchesAdapterAr.OnItemClickListener() {
                        @Override
                        public void onEmpty(int itemsNum) {
                            if (itemsNum == 0){
                                emptyTip.setVisibility(View.VISIBLE);
                                emptyTip.setText(R.string.noBranches_tip);
                            }
                            else {
                                emptyTip.setVisibility(View.GONE);
                            }
                        }

                        @Override
                        public void onMapButtonClicked(Branch branch, String mapMarketTitle) {
                            onBranchItemMapClicked(branch, mapMarketTitle);
                        }
                    });
                    recyclerView.setAdapter(adapter21);
                }
                else {
                    adapter22 = new BranchesAdapter(options2);
                    adapter22.setOnItemClickListener(new BranchesAdapter.OnItemClickListener() {
                        @Override
                        public void onEmpty(int itemsNum) {
                            if (itemsNum == 0){
                                emptyTip.setVisibility(View.VISIBLE);
                                emptyTip.setText(R.string.noBranches_tip);
                            }
                            else {
                                emptyTip.setVisibility(View.GONE);
                            }
                        }

                        @Override
                        public void onMapButtonClicked(Branch branch, String mapMarketTitle) {
                            onBranchItemMapClicked(branch, mapMarketTitle);
                        }
                    });
                    recyclerView.setAdapter(adapter22);
                }

                break;

            case 3:
                // Health Tips
                // Hybird

                db = FirebaseFirestore.getInstance();

                ArrayList<HealthTip> items3 = new ArrayList<>();
                items3.add(new HealthTip(getString(R.string.protect_fromCovid_title), getString(R.string.protect_fromCovid_desc), "https://firebasestorage.googleapis.com/v0/b/app-lab-el-hayah.appspot.com/o/HealthTips%20Images%2Fcovid.jpg?alt=media&token=86c9753a-3299-4deb-9bfc-e0a3f528f811"));
                items3.add(new HealthTip(getString(R.string.dangerOfObesity_title), getString(R.string.dangerOfObesity_desc), "https://firebasestorage.googleapis.com/v0/b/app-lab-el-hayah.appspot.com/o/HealthTips%20Images%2Fobesity.jpg?alt=media&token=08f74063-d4c8-4092-9981-4a0c25ea313c"));
                items3.add(new HealthTip(getString(R.string.foodAllergy_title), getString(R.string.foodAllergy_desc), "https://firebasestorage.googleapis.com/v0/b/app-lab-el-hayah.appspot.com/o/HealthTips%20Images%2FfoodAllergy.jpg?alt=media&token=a500dd90-98ba-4b5a-aa0a-6a9d50ed7028"));
                items3.add(new HealthTip(getString(R.string.bloodPicture_title), getString(R.string.bloodPicture_desc), "https://firebasestorage.googleapis.com/v0/b/app-lab-el-hayah.appspot.com/o/HealthTips%20Images%2FbloodPicture.jpg?alt=media&token=cb53deb8-f0a0-48ee-80f2-a224bb8138b5"));
                items3.add(new HealthTip(getString(R.string.breastCancer_title), getString(R.string.breastCancer_desc), "https://firebasestorage.googleapis.com/v0/b/app-lab-el-hayah.appspot.com/o/HealthTips%20Images%2FbreastCancer.jpg?alt=media&token=dd9101d5-d175-4fde-b148-1871cecc9b41"));
                items3.add(new HealthTip(getString(R.string.sensitivityOfInhalants_title), getString(R.string.sensitivityOfInhalants_desc), "https://firebasestorage.googleapis.com/v0/b/app-lab-el-hayah.appspot.com/o/HealthTips%20Images%2FsensitivityOfInhalants.jfif?alt=media&token=1bed3d64-5abc-4968-8b8d-35f531e4c98a"));
                items3.add(new HealthTip(getString(R.string.eatNuts_title), getString(R.string.eatNuts_desc), "https://firebasestorage.googleapis.com/v0/b/app-lab-el-hayah.appspot.com/o/HealthTips%20Images%2FeatNuts.jpg?alt=media&token=49cdf26d-4045-4bd3-8f82-7fd3d4b5fd1c"));
                items3.add(new HealthTip(getString(R.string.drinkSugar_title), getString(R.string.drinkSugar_desc), "https://firebasestorage.googleapis.com/v0/b/app-lab-el-hayah.appspot.com/o/HealthTips%20Images%2FdrinkSugar.jpg?alt=media&token=565c3027-0af6-4ec0-ba00-4b89f5a8f3c0"));
                items3.add(new HealthTip(getString(R.string.junkFood_title), getString(R.string.junkFood_desc), "https://firebasestorage.googleapis.com/v0/b/app-lab-el-hayah.appspot.com/o/HealthTips%20Images%2FjunkFood.jpg?alt=media&token=34ba2814-145d-4d93-b447-d2ddb5989a96"));
                items3.add(new HealthTip(getString(R.string.fearCoffe_title), getString(R.string.fearCoffe_desc), "https://firebasestorage.googleapis.com/v0/b/app-lab-el-hayah.appspot.com/o/HealthTips%20Images%2FfearCoffe.jpg?alt=media&token=204cdf8d-f963-4185-bc1a-62d76a9d9901"));
                items3.add(new HealthTip(getString(R.string.fattyFich_title), getString(R.string.fattyFich_desc), "https://firebasestorage.googleapis.com/v0/b/app-lab-el-hayah.appspot.com/o/HealthTips%20Images%2FfattyFich.jpg?alt=media&token=5ed1d4b8-5fcc-464a-9b26-83546c31cde6"));
                items3.add(new HealthTip(getString(R.string.enoughSleep_title), getString(R.string.enoughSleep_desc), "https://firebasestorage.googleapis.com/v0/b/app-lab-el-hayah.appspot.com/o/HealthTips%20Images%2FenoughSleep.jpg?alt=media&token=c7f0e290-5822-4392-bca5-bc1a36009cce"));
                HealthTipsAdapter adapter3 = new HealthTipsAdapter(items3);
                adapter3.setOnItemClickListener(healthTip -> {
                    Bundle healthTipBundle = new Bundle();
                    healthTipBundle.putString(Constants.TITLE_KEY_DETAILS_ACTIVITY, healthTip.getTitle());
                    healthTipBundle.putString(Constants.DETAILS_KEY_DETAILS_ACTIVITY, healthTip.getDetails());
                    healthTipBundle.putString(Constants.IMAGE_LINK_KEY_DETAILS_ACTIVITY, healthTip.getImage());
                    startActivity(new Intent(ItemsActivity.this, DetailsActivity.class).putExtras(healthTipBundle));
                });
                recyclerView.setAdapter(adapter3);

                db.collection(Constants.HEALTH_TIPS_FIRESTORE).whereEqualTo(Constants.PUBLISHED_FIRESTORE, true)
                        .get()
                        .addOnSuccessListener(queryDocumentSnapshots -> {
                            if (!queryDocumentSnapshots.isEmpty()){
                                ArrayList<HealthTip> healthTipsToAdd = new ArrayList<>();
                                if (LocalizationLanguageController.isArabic()){
                                    for (QueryDocumentSnapshot loadedHealthTipSnapshot: queryDocumentSnapshots){
                                        LoadedHealthTip loadedHealthTip = loadedHealthTipSnapshot.toObject(LoadedHealthTip.class);
                                        HealthTip healthTip = new HealthTip(loadedHealthTip.getTitleAr(), loadedHealthTip.getDetailsAr(), "https://i.ytimg.com/vi/R3unPcJDbCc/mqdefault.jpg");
                                        healthTipsToAdd.add(healthTip);
                                    }
                                }
                                else {
                                    for (QueryDocumentSnapshot loadedHealthTipSnapshot: queryDocumentSnapshots){
                                        LoadedHealthTip loadedHealthTip = loadedHealthTipSnapshot.toObject(LoadedHealthTip.class);
                                        HealthTip healthTip = new HealthTip(loadedHealthTip.getTitle(), loadedHealthTip.getDetails(), "https://i.ytimg.com/vi/R3unPcJDbCc/mqdefault.jpg");
                                        healthTipsToAdd.add(healthTip);
                                    }
                                }
                                adapter3.addToTop(healthTipsToAdd, recyclerView);
                            }
                        }).addOnFailureListener(e -> { });


                break;

            case 4:
                // Packages
                // DYNAMIC
                db = FirebaseFirestore.getInstance();

                //Query
                Query query4 = db.collection(Constants.PACKAGES_FIRESTORE).whereEqualTo(Constants.PUBLISHED_FIRESTORE, true);
                FirestoreRecyclerOptions<Package> options4=new FirestoreRecyclerOptions.Builder<Package>()
                        .setQuery(query4, Package.class)
                        .build();

                if (LocalizationLanguageController.isArabic()){
                    adapter41 = new PackagesAdapterAr(options4);
                    adapter41.setOnItemClickListener(new PackagesAdapterAr.OnItemClickListener() {
                        @Override
                        public void onEmpty(int itemsNum) {
                            if (itemsNum == 0){
                                emptyTip.setVisibility(View.VISIBLE);
                                emptyTip.setText(R.string.noPackages_tip);
                            }
                            else {
                                emptyTip.setVisibility(View.GONE);
                            }
                        }

                        @Override
                        public void onClicked(Package _package) {
                            Bundle packageBundle = new Bundle();
                            packageBundle.putString(Constants.TITLE_KEY_DETAILS_ACTIVITY, _package.getTitleAr());
                            packageBundle.putString(Constants.DETAILS_KEY_DETAILS_ACTIVITY, _package.getDetailsAr());
                            packageBundle.putString(Constants.IMAGE_LINK_KEY_DETAILS_ACTIVITY, _package.getImage());
                            startActivity(new Intent(ItemsActivity.this, DetailsActivity.class).putExtras(packageBundle));
                        }
                    });
                    recyclerView.setAdapter(adapter41);
                }
                else {
                    adapter42 = new PackagesAdapter(options4);
                    adapter42.setOnItemClickListener(new PackagesAdapter.OnItemClickListener() {
                        @Override
                        public void onEmpty(int itemsNum) {
                            if (itemsNum == 0){
                                emptyTip.setVisibility(View.VISIBLE);
                                emptyTip.setText(R.string.noPackages_tip);
                            }
                            else {
                                emptyTip.setVisibility(View.GONE);
                            }
                        }

                        @Override
                        public void onClicked(Package _package) {
                            Bundle packageBundle = new Bundle();
                            packageBundle.putString(Constants.TITLE_KEY_DETAILS_ACTIVITY, _package.getTitle());
                            packageBundle.putString(Constants.DETAILS_KEY_DETAILS_ACTIVITY, _package.getDetails());
                            packageBundle.putString(Constants.IMAGE_LINK_KEY_DETAILS_ACTIVITY, _package.getImage());
                            startActivity(new Intent(ItemsActivity.this, DetailsActivity.class).putExtras(packageBundle));
                        }
                    });
                    recyclerView.setAdapter(adapter42);
                }

                break;
            case 5:
                // Announcements
                // DYNAMIC
                db = FirebaseFirestore.getInstance();

                //Query
                Query query5 = db.collection(Constants.ANNOUNCEMENT_FIRESTORE).whereEqualTo(Constants.PUBLISHED_FIRESTORE, true);
                FirestoreRecyclerOptions<Announcement> options5 = new FirestoreRecyclerOptions.Builder<Announcement>()
                        .setQuery(query5, Announcement.class)
                        .build();

                if (LocalizationLanguageController.isArabic()){
                    adapter51 = new AnnouncementAdapterAr(options5);
                    adapter51.setOnItemClickListener(new AnnouncementAdapterAr.OnItemClickListener() {
                        @Override
                        public void onEmpty(int itemsNum) {
                            if (itemsNum == 0){
                                emptyTip.setVisibility(View.VISIBLE);
                                emptyTip.setText(R.string.noAnnouncements_tip);
                            }
                            else {
                                emptyTip.setVisibility(View.GONE);
                            }
                        }

                        @Override
                        public void onClicked(Announcement announcement) {
                            Bundle announcementsBundle = new Bundle();
                            announcementsBundle.putString(Constants.TITLE_KEY_DETAILS_ACTIVITY, announcement.getTitleAr());
                            announcementsBundle.putString(Constants.DETAILS_KEY_DETAILS_ACTIVITY, announcement.getDetailsAr());
                            announcementsBundle.putString(Constants.IMAGE_LINK_KEY_DETAILS_ACTIVITY, announcement.getImage());
                            startActivity(new Intent(ItemsActivity.this, DetailsActivity.class).putExtras(announcementsBundle));
                        }
                    });
                    recyclerView.setAdapter(adapter51);
                }
                else {
                    adapter52 = new AnnouncementAdapter(options5);
                    adapter52.setOnItemClickListener(new AnnouncementAdapter.OnItemClickListener() {
                        @Override
                        public void onEmpty(int itemsNum) {
                            if (itemsNum == 0){
                                emptyTip.setVisibility(View.VISIBLE);
                                emptyTip.setText(R.string.noAnnouncements_tip);
                            }
                            else {
                                emptyTip.setVisibility(View.GONE);
                            }
                        }

                        @Override
                        public void onClicked(Announcement announcement) {
                            Bundle announcementsBundle = new Bundle();
                            announcementsBundle.putString(Constants.TITLE_KEY_DETAILS_ACTIVITY, announcement.getTitle());
                            announcementsBundle.putString(Constants.DETAILS_KEY_DETAILS_ACTIVITY, announcement.getDetails());
                            announcementsBundle.putString(Constants.IMAGE_LINK_KEY_DETAILS_ACTIVITY, announcement.getImage());
                            startActivity(new Intent(ItemsActivity.this, DetailsActivity.class).putExtras(announcementsBundle));
                        }
                    });
                    recyclerView.setAdapter(adapter52);
                }

                break;

            case 6:
                // Examination Precaution
                // FIXED
                ArrayList<ExaminationPrecaution> items6 = new ArrayList<>();
                items6.add(new ExaminationPrecaution(getString(R.string.pre_exam_title_item1), getString(R.string.pre_exam_details_item1)));
                items6.add(new ExaminationPrecaution(getString(R.string.pre_exam_title_item2), getString(R.string.pre_exam_details_item2)));
                items6.add(new ExaminationPrecaution(getString(R.string.pre_exam_title_item3), getString(R.string.pre_exam_details_item3)));
                items6.add(new ExaminationPrecaution(getString(R.string.pre_exam_title_item4), getString(R.string.pre_exam_details_item4)));
                items6.add(new ExaminationPrecaution(getString(R.string.pre_exam_title_item5), getString(R.string.pre_exam_details_item5)));
                items6.add(new ExaminationPrecaution(getString(R.string.pre_exam_title_item6), getString(R.string.pre_exam_details_item6)));
                items6.add(new ExaminationPrecaution(getString(R.string.pre_exam_title_item7), getString(R.string.pre_exam_details_item7)));
                items6.add(new ExaminationPrecaution(getString(R.string.pre_exam_title_item8), getString(R.string.pre_exam_details_item8)));
                items6.add(new ExaminationPrecaution(getString(R.string.pre_exam_title_item9), getString(R.string.pre_exam_details_item9)));
                items6.add(new ExaminationPrecaution(getString(R.string.pre_exam_title_item10), getString(R.string.pre_exam_details_item10)));
                items6.add(new ExaminationPrecaution(getString(R.string.pre_exam_title_item11), getString(R.string.pre_exam_details_item11)));
                items6.add(new ExaminationPrecaution(getString(R.string.pre_exam_title_item12), getString(R.string.pre_exam_details_item12)));
                items6.add(new ExaminationPrecaution(getString(R.string.pre_exam_title_item13), getString(R.string.pre_exam_details_item13)));
                items6.add(new ExaminationPrecaution(getString(R.string.pre_exam_title_item14), getString(R.string.pre_exam_details_item14)));
                items6.add(new ExaminationPrecaution(getString(R.string.pre_exam_title_item15), getString(R.string.pre_exam_details_item15)));
                items6.add(new ExaminationPrecaution(getString(R.string.pre_exam_title_item16), getString(R.string.pre_exam_details_item16)));
                items6.add(new ExaminationPrecaution(getString(R.string.pre_exam_title_item17), getString(R.string.pre_exam_details_item17)));
                items6.add(new ExaminationPrecaution(getString(R.string.pre_exam_title_item18), getString(R.string.pre_exam_details_item18)));

                ExaminationPrecautionsAdapter adapter6 = new ExaminationPrecautionsAdapter(items6);
                adapter6.setOnItemClickListener(examinationPrecaution -> {
                    Bundle examinationPrecautionBundle = new Bundle();
                    examinationPrecautionBundle.putString(Constants.TITLE_KEY_DETAILS_ACTIVITY, examinationPrecaution.getTitle());
                    examinationPrecautionBundle.putString(Constants.DETAILS_KEY_DETAILS_ACTIVITY, examinationPrecaution.getDetails());
                    startActivity(new Intent(ItemsActivity.this, DetailsActivity.class).putExtras(examinationPrecautionBundle));
                });
                recyclerView.setAdapter(adapter6);

                break;
            default:
                Toast.makeText(this, "Not Defined Yet", Toast.LENGTH_LONG).show();
                break;
        }
    }

    private void onBranchItemMapClicked(Branch branch, String mapMarketTitle) {
        // View Branch Place on Map
        Constants.createMapDialog(ItemsActivity.this, branch.getLatitude(), branch.getLongitude(), mapMarketTitle);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (adapter21 != null){
            adapter21.startListening();
        }
        else if (adapter22 != null){
            adapter22.startListening();
        }
        else if (adapter41 != null){
            adapter41.startListening();
        }
        else if (adapter42 != null){
            adapter42.startListening();
        }
        else if (adapter11 != null){
            adapter11.startListening();
        }
        else if (adapter12 != null){
            adapter12.startListening();
        }
        else if (adapter51 != null){
            adapter51.startListening();
        }
        else if (adapter52 != null){
            adapter52.startListening();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (adapter21 != null){
            adapter21.stopListening();
        }
        else if (adapter22 != null){
            adapter22.stopListening();
        }
        else if (adapter41 != null){
            adapter41.stopListening();
        }
        else if (adapter42 != null){
            adapter42.stopListening();
        }
        else if (adapter11 != null){
            adapter11.stopListening();
        }
        else if (adapter12 != null){
            adapter12.stopListening();
        }
        else if (adapter51 != null){
            adapter51.stopListening();
        }
        else if (adapter52 != null){
            adapter52.stopListening();
        }
    }

//    @Override
//    protected void onStop() {
//        super.onStop();
//        if (adapter21 != null){
//            adapter21.stopListening();
//        }
//        else if (adapter22 != null){
//            adapter22.stopListening();
//        }
//        else if (adapter41 != null){
//            adapter41.stopListening();
//        }
//        else if (adapter42 != null){
//            adapter42.stopListening();
//        }
//        else if (adapter11 != null){
//            adapter11.stopListening();
//        }
//        else if (adapter12 != null){
//            adapter12.stopListening();
//        }
//    }
}
