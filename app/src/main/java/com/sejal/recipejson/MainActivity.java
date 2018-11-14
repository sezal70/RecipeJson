package com.sejal.recipejson;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private RecyclerView recyclerView;
    private List<Recipe> cartList;
    private RecipeListAdapter mAdapter;

    private ShimmerFrameLayout mShimmerViewContainer;

    //fetchRecipes() method fetches the JSON by making Volley’s http call. The JSON is parsed using Gson serializer.
    //Once the JSON is parsed and added to RecyclerView adapter, the list is rendered and ShimmerFrameLayout is hidden making the actual list visible on the screen.

    // URL to fetch menu json
    // this endpoint takes 2 sec before giving the response to add
    // some delay to test the Shimmer effect


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mShimmerViewContainer = findViewById(R.id.shimmer_view_container);

        String data = loadAssetsData("recipedata.json");
        RecipeList r = getFromGson(data);
        Log.e("Recipe", r.toString());

        recyclerView = findViewById(R.id.recycler_view);
       cartList = new ArrayList<>();
       mAdapter = new RecipeListAdapter(this, r.getRecipeList());

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
       recyclerView.addItemDecoration(new MyDividerItemDecoration(this, LinearLayoutManager.VERTICAL, 16));

        recyclerView.setAdapter(mAdapter);


    }

    public String loadAssetsData(String filename) {
        try {
            InputStream is = getAssets().open(filename);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            return new String(buffer, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        mShimmerViewContainer.stopShimmerAnimation();
        mShimmerViewContainer.setVisibility(View.GONE);

        return null;
    }

    public RecipeList getFromGson(String data) {
        Gson gson = new GsonBuilder().create();
        RecipeList r = gson.fromJson(data, RecipeList.class);
        // stop animating Shimmer and hide the layout

        return r;








    }




    /**
     * method make volley network call and parses json
     */

    @Override
     public void onResume(){
       super.onResume();
       mShimmerViewContainer.startShimmerAnimation();
   }

   @Override
    public void onPause(){
       super.onPause();
       mShimmerViewContainer.stopShimmerAnimation();
   }

}
