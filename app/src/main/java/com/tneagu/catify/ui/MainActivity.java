package com.tneagu.catify.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.tneagu.catify.R;
import com.tneagu.catify.net.ApiServices;
import com.tneagu.catify.net.RetrofitClient;
import com.tneagu.catify.net.models.Photo;
import com.tneagu.catify.net.models.PhotoResponse;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {
    private static final String SEARCH_TERM = "cat";

    Unbinder bind;
    @BindView(R.id.main_recycler_view)
    RecyclerView recyclerView;

    private RecyclerAdapter adapter = new RecyclerAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bind = ButterKnife.bind(this);

        setupRecyclerView();
        callWebService();
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(bind != null){
            bind.unbind();
        }
    }

    private void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    private void callWebService() {
        ApiServices apiServices = RetrofitClient.getClient().create(ApiServices.class);
        Single<PhotoResponse> wsResponse = apiServices.searchPhoto(SEARCH_TERM);

        wsResponse.subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleResults, this::handleError);
    }


    private void handleResults(PhotoResponse response) {
        Toast.makeText(this, "SUCCES",
                    Toast.LENGTH_LONG).show();

        List<String> photoUrls = new ArrayList<>();
        for(Photo photo : response.getPhotos()){
            String url = photo.getLinks().get("download");
            if(url != null){
                photoUrls.add(url);
            }
        }

        if(photoUrls.size() > 0){
            adapter.setPhotoUrls(photoUrls);
        }
    }

    private void handleError(Throwable t) {

        Toast.makeText(this, "ERROR IN FETCHING API RESPONSE. Try again",
                Toast.LENGTH_LONG).show();
    }
}
