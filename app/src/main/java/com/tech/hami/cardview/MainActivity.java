package com.tech.hami.cardview;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AlbumsAdapterListener{

    private AlbumsAdapter adapter;
    private List<Album> albumList;
    private InterstitialAd mInterstitialAd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MobileAds.initialize(this, "ca-app-pub-2741866017910510~2098723578");
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-2741866017910510/2274995156");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                // Load the next interstitial.
                mInterstitialAd.loadAd(new AdRequest.Builder().build());
            }

        });
        int PERMISSION_ALL = 1;
        String[] PERMISSIONS = {
                Manifest.permission.INTERNET,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.ACCESS_FINE_LOCATION
        };
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(!hasPermissions(this, PERMISSIONS)){
                ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
            }
        }

        initCollapsingToolbar();

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        AlbumsAdapterListener listener = this;
        albumList = new ArrayList<>();
        adapter = new AlbumsAdapter(this, albumList, listener);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        prepareAlbums();

        try {
            Glide.with(this).load(R.drawable.coverpic2).into((ImageView) findViewById(R.id.backdrop));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Initializing collapsing toolbar
     * Will show and hide the toolbar title on scroll
     */
    private void initCollapsingToolbar() {
        final CollapsingToolbarLayout collapsingToolbar =
                findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(" ");
        AppBarLayout appBarLayout = findViewById(R.id.appbar);
        appBarLayout.setExpanded(true);

        // hiding & showing the title when toolbar expanded & collapsed
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbar.setTitle(getString(R.string.cover_title));
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbar.setTitle(" ");
                    isShow = false;
                }
            }
        });
    }

    /**
     * Adding few albums for testing
     */
    private void prepareAlbums() {
        int[] covers = new int[]{
                R.drawable.alb1,
                R.drawable.a2,
                R.drawable.a3,
                R.drawable.a4,
                R.drawable.a5,
                R.drawable.a6,
                R.drawable.a7,
                R.drawable.a8,
                R.drawable.a9,
                R.drawable.a10,
                R.drawable.wordpress,
                R.drawable.copywrite,
                R.drawable.emailmarketing,
                R.drawable.leapforce,
                R.drawable.fbmarketing,
                R.drawable.teespring,
                R.drawable.mobileapp,
                R.drawable.etsy,
                R.drawable.cpa,
                R.drawable.amazon,
                R.drawable.videoediting,
                R.drawable.viralwebsite,
                R.drawable.ratemusic,
                R.drawable.dropshipping,
                R.drawable.shopping,
                R.drawable.evaluator,
                R.drawable.qa,
                R.drawable.usabilitytesting,
                R.drawable.microjob,
                R.drawable.photography,
                R.drawable.domain,
                R.drawable.chatbot
                };

        Album a = new Album("Create a Youtube Channel", 13, covers[0]);
        albumList.add(a);

        a = new Album("Start a Blog", 8, covers[1]);
        albumList.add(a);

        a = new Album("Affiliate Marketing", 11, covers[2]);
        albumList.add(a);

        a = new Album("Create Niche websites", 12, covers[3]);
        albumList.add(a);

        a = new Album("Publish a kindle eBook", 14, covers[4]);
        albumList.add(a);

        a = new Album("Create a Membership site", 1, covers[5]);
        albumList.add(a);

        a = new Album("Sell websites on Flippa", 11, covers[6]);
        albumList.add(a);

        a = new Album("Sell video course on Udemy", 14, covers[7]);
        albumList.add(a);

        a = new Album("Sell service on Fiverr", 11, covers[8]);
        albumList.add(a);

        a = new Album("Resell Items on Ebay", 17, covers[9]);
        albumList.add(a);

        a = new Album("Create wordpress themes", 17, covers[10]);
        albumList.add(a);
        a = new Album("Start Copywriting", 17, covers[11]);
        albumList.add(a);
        a = new Album("Do Email Marketing", 17, covers[12]);
        albumList.add(a);
        a = new Album("Join Leapforce", 17, covers[13]);
        albumList.add(a);
        a = new Album("Do Facebook Marketing", 17, covers[14]);
        albumList.add(a);
        a = new Album("Start TeeSpring Campaigns", 17, covers[15]);
        albumList.add(a);
        a = new Album("Become an Amazon Associate", 17, covers[19]);
        albumList.add(a);
        a = new Album("Develop Mobile Apps", 17, covers[16]);
        albumList.add(a);
        a = new Album("Sell Art and Crafts on Etsy", 17, covers[17]);
        albumList.add(a);
        a = new Album("Do CPA Marketing", 17, covers[18]);
        albumList.add(a);
        a = new Album("Build Chat Bots", 17, covers[31]);
        albumList.add(a);
        a = new Album("Video Creation and Editing", 17, covers[20]);
        albumList.add(a);
        a = new Album("Create a Viral Website", 17, covers[21]);
        albumList.add(a);
        a = new Album("Listen and Rate Music", 17, covers[22]);
        albumList.add(a);
        a = new Album("Start Dropshipping Bussiness", 17, covers[23]);
        albumList.add(a);
        a = new Album("Become a Telephone Mystery Shopper", 17, covers[24]);
        albumList.add(a);
        a = new Album("Become a social media evaluator", 17, covers[25]);
        albumList.add(a);
        a = new Album("Join Q&A sites", 17, covers[26]);
        albumList.add(a);
        a = new Album("Participate in Usability Tesing", 17, covers[27]);
        albumList.add(a);
        a = new Album("Join Micro job sites", 17, covers[28]);
        albumList.add(a);
        a = new Album("Earn with Smart phone photography", 17, covers[29]);
        albumList.add(a);
        a = new Album("Buy and Sell Domain Names", 17, covers[30]);
        albumList.add(a);



        adapter.notifyDataSetChanged();
    }

    @Override
    public void onAddToFavoriteSelected(int position) {

    }

    @Override
    public void onPlayNextSelected(int position) {

    }

    @Override
    public void onCardSelected(int position, ImageView thumbnail) {
       if (position==0){


           if (mInterstitialAd.isLoaded()) {
               mInterstitialAd.show();
           }

           Intent intent=new Intent(this,youtube.class);
           startActivity(intent);

       }
       else if (position==1){
           if (mInterstitialAd.isLoaded()) {
               mInterstitialAd.show();
           }

           Intent intent=new Intent(this,blog.class);
           startActivity(intent);

       }
        else if(position==2) {
           if (mInterstitialAd.isLoaded()) {
               mInterstitialAd.show();
           }

           Intent intent=new Intent(this,afilliate.class);
           startActivity(intent);

       }
        else if (position==3){

           if (mInterstitialAd.isLoaded()) {
               mInterstitialAd.show();
           }
           Intent intent=new Intent(this,niche.class);
           startActivity(intent);

       }      else if (position==4){
           if (mInterstitialAd.isLoaded()) {
               mInterstitialAd.show();
           }
           Intent intent=new Intent(this,kindle.class);
           startActivity(intent);

       }
        else if (position==5){
           if (mInterstitialAd.isLoaded()) {
               mInterstitialAd.show();
           }
           Intent intent=new Intent(this,membership.class);
           startActivity(intent);

       }
        else if (position==6){
           if (mInterstitialAd.isLoaded()) {
               mInterstitialAd.show();
           }
           Intent intent=new Intent(this,flippa.class);
           startActivity(intent);

       }
        else if (position==7){
           if (mInterstitialAd.isLoaded()) {
               mInterstitialAd.show();
           }
           Intent intent=new Intent(this,udemy.class);
           startActivity(intent);
       }
        else if (position==8){
           if (mInterstitialAd.isLoaded()) {
               mInterstitialAd.show();
           }
           Intent intent=new Intent(this,Fiverr.class);
           startActivity(intent);

       }
        else if (position==9){
           if (mInterstitialAd.isLoaded()) {
               mInterstitialAd.show();
           }
           Intent intent=new Intent(this,eBay.class);
           startActivity(intent);

       }
        else if (position==10){
           if (mInterstitialAd.isLoaded()) {
               mInterstitialAd.show();
           }
           Intent intent=new Intent(this,wordpress.class);
           startActivity(intent);

       }
       else if (position==11){
           if (mInterstitialAd.isLoaded()) {
               mInterstitialAd.show();
           }
           Intent intent=new Intent(this,copywriting.class);
           startActivity(intent);

       }
       else if (position==12){
           if (mInterstitialAd.isLoaded()) {
               mInterstitialAd.show();
           }
           Intent intent=new Intent(this,emailmarketing.class);
           startActivity(intent);

       }
       else if (position==13){
           if (mInterstitialAd.isLoaded()) {
               mInterstitialAd.show();
           }
           Intent intent=new Intent(this,leapforce.class);
           startActivity(intent);

       }
       else if (position==14){
           if (mInterstitialAd.isLoaded()) {
               mInterstitialAd.show();
           }
           Intent intent=new Intent(this,fbmarketing.class);
           startActivity(intent);

       }
       else if (position==15){
           if (mInterstitialAd.isLoaded()) {
               mInterstitialAd.show();
           }
           Intent intent=new Intent(this,teespring.class);
           startActivity(intent);

       }
       else if (position==16){
           if (mInterstitialAd.isLoaded()) {
               mInterstitialAd.show();
           }
           Intent intent=new Intent(this,amazon.class);
           startActivity(intent);

       }
       else if (position==17){
           if (mInterstitialAd.isLoaded()) {
               mInterstitialAd.show();
           }
           Intent intent=new Intent(this,mobileapp.class);
           startActivity(intent);

       }
       else if (position==18){
           if (mInterstitialAd.isLoaded()) {
               mInterstitialAd.show();
           }
           Intent intent=new Intent(this,etsy.class);
           startActivity(intent);

       }
       else if (position==19){
           if (mInterstitialAd.isLoaded()) {
               mInterstitialAd.show();
           }
           Intent intent=new Intent(this,cpa.class);
           startActivity(intent);

       }
       else if (position==20){
           if (mInterstitialAd.isLoaded()) {
               mInterstitialAd.show();
           }
           Intent intent=new Intent(this,chatbot.class);
           startActivity(intent);

       }
       else if (position==21){
           if (mInterstitialAd.isLoaded()) {
               mInterstitialAd.show();
           }
           Intent intent=new Intent(this,videoediting.class);
           startActivity(intent);

       }
       else if (position==22){
           if (mInterstitialAd.isLoaded()) {
               mInterstitialAd.show();
           }
           Intent intent=new Intent(this,viralwebsite.class);
           startActivity(intent);

       }
       else if (position==23){
           if (mInterstitialAd.isLoaded()) {
               mInterstitialAd.show();
           }
           Intent intent=new Intent(this,ratemusic.class);
           startActivity(intent);

       }
       else if (position==24){
           if (mInterstitialAd.isLoaded()) {
               mInterstitialAd.show();
           }
           Intent intent=new Intent(this,dropshipping.class);
           startActivity(intent);

       }
       else if (position==25){
           if (mInterstitialAd.isLoaded()) {
               mInterstitialAd.show();
           }
           Intent intent=new Intent(this,mysteryshopper.class);
           startActivity(intent);

       }
       else if (position==26){
           if (mInterstitialAd.isLoaded()) {
               mInterstitialAd.show();
           }

           Intent intent=new Intent(this,socialmediaevaluator.class);
           startActivity(intent);

       }
       else if (position==27){
           if (mInterstitialAd.isLoaded()) {
               mInterstitialAd.show();
           }
           Intent intent=new Intent(this,qasites.class);
           startActivity(intent);

       }
       else if (position==28){
           if (mInterstitialAd.isLoaded()) {
               mInterstitialAd.show();
           }
           Intent intent=new Intent(this,usabilitytesting.class);
           startActivity(intent);

       }
       else if (position==29){
           if (mInterstitialAd.isLoaded()) {
               mInterstitialAd.show();
           }
           Intent intent=new Intent(this,microjob.class);
           startActivity(intent);

       }
       else if (position==30){
           if (mInterstitialAd.isLoaded()) {
               mInterstitialAd.show();
           }
           Intent intent=new Intent(this,photography.class);
           startActivity(intent);

       }
       else if (position==31){
           if (mInterstitialAd.isLoaded()) {
               mInterstitialAd.show();
           }
           Intent intent=new Intent(this,domain.class);
           startActivity(intent);

       }



    }

    /**
     * RecyclerView item decoration - give equal margin around grid item
     */
    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx() {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, r.getDisplayMetrics()));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_favorite) {
            Intent intent = new Intent(this, help.class);
            startActivity(intent);
            if (mInterstitialAd.isLoaded()) {
                mInterstitialAd.show();
            }

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public static boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

}