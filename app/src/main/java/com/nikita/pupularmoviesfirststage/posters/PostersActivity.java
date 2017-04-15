package com.nikita.pupularmoviesfirststage.posters;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.nikita.pupularmoviesfirststage.R;
import com.nikita.pupularmoviesfirststage.common.models.MoviePreview;
import com.nikita.pupularmoviesfirststage.common.models.PageResponse;
import com.nikita.pupularmoviesfirststage.common.models.Poster;
import com.nikita.pupularmoviesfirststage.common.network.Network;
import com.nikita.pupularmoviesfirststage.common.network.Request;

import java.util.ArrayList;
import java.util.List;

public class PostersActivity extends AppCompatActivity {

  private final PostersAdapter postersAdapter = new PostersAdapter();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.posters_activity);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
    fab.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
          .setAction("Action", null).show();
      }
    });

    RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
    recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
    recyclerView.setAdapter(postersAdapter);

    Request.movieList(Request.POPULAR, new Network.DataCallback<PageResponse<MoviePreview>>() {
      @Override
      public void onResult(Network.FetchResult<PageResponse<MoviePreview>> result) {
        if (result.error == null) {
          postersAdapter.setTopic(Request.POPULAR);
          List<Poster> posters = new ArrayList<>();
          posters.addAll(result.data.results);
          postersAdapter.setPosters(posters);
        }
      }
    });
  }
}
