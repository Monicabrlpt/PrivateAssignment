package id.sch.smktelkom_mlg.privateassignment.xirpl519.monvie21;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;

import java.util.ArrayList;

import id.sch.smktelkom_mlg.privateassignment.xirpl519.monvie21.adapter.SourceAdapter;
import id.sch.smktelkom_mlg.privateassignment.xirpl519.monvie21.model.Result;
import id.sch.smktelkom_mlg.privateassignment.xirpl519.monvie21.model.SourcesResponse;
import id.sch.smktelkom_mlg.privateassignment.xirpl519.monvie21.service.GsonGetRequest;
import id.sch.smktelkom_mlg.privateassignment.xirpl519.monvie21.service.VolleySingleton;


/**
 * A simple {@link Fragment} subclass.
 */
public class TopRate extends Fragment {

    ArrayList<Result> mList = new ArrayList<>();
    SourceAdapter mAdapter;

    public TopRate() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_top_rate, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        RecyclerView recyclerView = (RecyclerView) getView().findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        mAdapter = new SourceAdapter(this.getActivity(), mList);
        recyclerView.setAdapter(mAdapter);

        downloadDataSources();
    }

    private void downloadDataSources() {
        String url = "https://api.themoviedb.org/3/movie/top_rated?api_key=b0914b6a0f0a11509d0ba42de114be55&language=en-US&";

        GsonGetRequest<SourcesResponse> myRequest = new GsonGetRequest<SourcesResponse>
                (url, SourcesResponse.class, null, new Response.Listener<SourcesResponse>() {

                    @Override
                    public void onResponse(SourcesResponse response) {
                        Log.d("FLOW", "onResponse: " + (new Gson().toJson(response)));

                        mList.addAll(response.results);
                        mAdapter.notifyDataSetChanged();

                    }

                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("FLOW", "onErrorResponse: ", error);
                    }
                });
        VolleySingleton.getInstance(this.getActivity()).addToRequestQueue(myRequest);
    }

}

