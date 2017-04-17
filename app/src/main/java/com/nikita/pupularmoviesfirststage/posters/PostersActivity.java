package com.nikita.pupularmoviesfirststage.posters;

import android.os.Bundle;
import android.support.v4.content.pm.ActivityInfoCompat;
import android.support.v4.widget.ContentLoadingProgressBar;
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
import com.nikita.pupularmoviesfirststage.common.views.ErrorView;

import java.util.ArrayList;
import java.util.List;

public class PostersActivity extends AppCompatActivity {
  private static final String KEY_SELECTED_TOPIC = "selected_topic";

  private RecyclerView contentView;
  private ContentLoadingProgressBar loadingView;
  private ErrorView errorView;

  private int spanCount;
  private PostersAdapter postersAdapter;
  private String selectedTopic = Request.POPULAR;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    spanCount = getResources().getInteger(R.integer.posters_layout_span_count);
    postersAdapter = new PostersAdapter(spanCount);
    if (savedInstanceState != null) {
      selectedTopic = savedInstanceState.getString(KEY_SELECTED_TOPIC);
    }

    setContentView(R.layout.posters_activity);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    loadingView = (ContentLoadingProgressBar) findViewById(R.id.progress_bar);
    errorView = (ErrorView) findViewById(R.id.error_view);
    contentView = (RecyclerView) findViewById(R.id.recycler_view);
    contentView.setLayoutManager(new StaggeredGridLayoutManager(spanCount, StaggeredGridLayoutManager.VERTICAL));
    contentView.setAdapter(postersAdapter);

    loadMovies();
  }

  @Override
  protected void onSaveInstanceState(Bundle outState) {
    outState.putString(KEY_SELECTED_TOPIC, selectedTopic);
    super.onSaveInstanceState(outState);
  }

  private void loadMovies() {
    errorView.hideError();
    loadingView.show();
    Request.movieList(selectedTopic, new Network.DataCallback<PageResponse<MoviePreview>>() {
      @Override
      public void onResult(Network.FetchResult<PageResponse<MoviePreview>> result) {
        loadingView.hide();
        if (result.error == null) {
          errorView.hideError();
          contentView.setVisibility(View.VISIBLE);

          List<Poster> posters = new ArrayList<>();
          posters.addAll(result.data.results);
          postersAdapter.setPosters(posters);
          postersAdapter.setTopic(selectedTopic);
        } else {
          contentView.setVisibility(View.GONE);
          errorView.showError(result.error.getMessage(), new Runnable() {
            @Override
            public void run() {
              loadMovies();
            }
          });
        }
      }
    });
  }
}
