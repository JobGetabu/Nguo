package com.example.job.nguo;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.example.job.nguo.model.ProductEntry;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

     @BindView(R.id.recycleview_product)
     RecyclerView recyclerView;

    @BindView(R.id.bottom_nav_view)
    BottomNavigationView bottomNavigationItemView;

    @BindView(R.id.toolbar)Toolbar toolbar;


    public static final String TAG = MainActivity.class.getSimpleName();

    private ProductAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initializing all fields
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        List<ProductEntry> productEntries = readProductList();
        Log.d(TAG, "onCreate: List"+productEntries.toString());
        ProductEntry headerProduct = getHeaderProduct(productEntries);
        ImageRequester imageRequester = ImageRequester.getInstance(this);

        NetworkImageView networkImageViewAppBar = (NetworkImageView) findViewById(R.id.app_bar_image);
        ImageRequester.setImagFromUrlr(networkImageViewAppBar, headerProduct.getImageUrl());

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this,getResources().getInteger(R.integer.grid_count_2));
        recyclerView.setLayoutManager(layoutManager);
        adapter = new ProductAdapter(productEntries, imageRequester);
        recyclerView.setAdapter(adapter);

        bottomNavigationItemView.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {
                GridLayoutManager gridLayoutManager = (GridLayoutManager) recyclerView.getLayoutManager();
                gridLayoutManager.scrollToPositionWithOffset(0,0);
            }
        });
    }

    public ArrayList<ProductEntry> readProductList(){
        ArrayList<ProductEntry> test = null;
        InputStream stream = getApplicationContext().getResources().openRawResource(R.raw.product);
        Type productList = new TypeToken<ArrayList<ProductEntry>>(){}.getType();

        try {
            test = JsonReader.readJsonStream(stream,productList);
            Log.d(TAG, "readProductList: List is "+test);
            return test;
        } catch (IOException e) {
            Log.d(TAG, "readProductList: Error parsing json");
            e.printStackTrace();
            return new ArrayList<>();
        } catch (NullPointerException ex){
            Log.d(TAG, "readProductList: null :(");
            return test;
        }
    }

    public ProductEntry getHeaderProduct(List<ProductEntry>  productEntries){
        if (productEntries.size() == 0){
            throw new IllegalArgumentException("Error no products opps");
        }
        for (int i = 0; i < productEntries.size(); i++) {
            if (("WOMEN'S SINGLE-TRACK SHOE").equals(productEntries.get(i).getProductName())){
                return productEntries.get(i);
            }
        }
        return productEntries.get(0);
    }

    public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

        private List<ProductEntry> productList;
        private ImageRequester imageRequester;

        public ProductAdapter(List<ProductEntry> productEntries, ImageRequester imageRequester) {
            this.productList = productEntries;
            this.imageRequester = imageRequester;
            Log.d(TAG, "ProductAdapter: list"+productEntries.toString());
        }

        @Override
        public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getApplicationContext());
            View view = layoutInflater.inflate(R.layout.product_entry,parent, false);
            ProductViewHolder productViewHolder = new ProductViewHolder(view);
            return productViewHolder;
        }
        public String getProductImageRequester(List<ProductEntry>  productEntries, int position){
            if (productEntries.size() == 0){
                throw new IllegalArgumentException("Error no products opps");
            }
            for (int i = 0; i < productEntries.size(); i++) {
                   if(i ==  position){
                       return productEntries.get(position).getImageUrl();
                }

            }
                return productEntries.get(position).getImageUrl();
        }

        @Override
        public void onBindViewHolder(ProductViewHolder holder, int position) {
             //This is a much cleaner way of popularizing the recycle
             // holder.bind(productEntries.get(position),imageRequester);

            Log.d(TAG, "onBindViewHolder: value of product name +"+String.valueOf(productList.get(position).getProductName()));
            holder.textViewProductName.setText(String.valueOf(productList.get(position).getProductName()));
            holder.textViewPrice.setText(productList.get(position).getPrice());
            String url = getProductImageRequester(productList, position);
            ImageRequester.setImagFromUrlr(holder.imageViewProduct, url);
        }

        @Override
        public int getItemCount() {
            return productList.size();
        }

        public class ProductViewHolder extends RecyclerView.ViewHolder{
//            @BindView(R.id.price) TextView textViewPrice;
//            @BindView(R.id.product_image)ImageView imageViewProduct;
//            @BindView(R.id.product_name)TextView textViewProductName;
//            @BindView(R.id.favorite_button)ImageButton imageButtonFav;imageButtonFav

            TextView textViewPrice ;
            NetworkImageView imageViewProduct ;
            TextView textViewProductName ;
            ImageButton imageButtonFav ;

            public ProductViewHolder(View itemView) {
                super(itemView);

                 textViewPrice = (TextView) itemView.findViewById(R.id.price);
                 imageViewProduct = (NetworkImageView) itemView.findViewById(R.id.product_image);
                 textViewProductName = (TextView) itemView.findViewById(R.id.product_name);
                 imageButtonFav = (ImageButton) itemView.findViewById(R.id.fav_product);

                //ButterKnife.bind(this,itemView);
            }

            //currently not implemented
            void bind(ProductEntry productEntry,ImageRequester imageRequester){

                itemView.setTag(R.id.tag_product_entry,productEntry);
                imageViewProduct.setImageURI(Uri.parse(productEntry.getImageUrl()));
                textViewPrice.setText(productEntry.getPrice());
                textViewProductName.setText(productEntry.getProductName());

            }
        }
    }
}
