package com.nikita.popularmovies.posters;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.nightonke.boommenu.BoomButtons.ButtonPlaceEnum;
import com.nightonke.boommenu.BoomButtons.HamButton;
import com.nightonke.boommenu.BoomButtons.OnBMClickListener;
import com.nightonke.boommenu.BoomMenuButton;
import com.nightonke.boommenu.ButtonEnum;
import com.nightonke.boommenu.Piece.PiecePlaceEnum;
import com.nightonke.boommenu.Util;
import com.nikita.popularmovies.R;
import com.nikita.popularmovies.common.GetSavedMoviesTask;
import com.nikita.popularmovies.common.ResourcesUtils;
import com.nikita.popularmovies.common.models.MoviePreview;
import com.nikita.popularmovies.common.models.PageResponse;
import com.nikita.popularmovies.common.models.Poster;
import com.nikita.popularmovies.common.network.NetworkClasses;
import com.nikita.popularmovies.common.network.Request;
import com.nikita.popularmovies.common.views.ErrorView;
import com.nikita.popularmovies.details.MovieDetailsActivity;

import java.util.ArrayList;
import java.util.List;

public class PostersActivity extends AppCompatActivity {
  private static final String KEY_SELECTED_TOPIC = "selected_topic";
  private static final String KEY_LOADED_POSTERS = "loaded_posters";

  private RecyclerView contentView;
  private ContentLoadingProgressBar loadingView;
  private ErrorView errorView;

  private int spanCount;
  private PostersAdapter postersAdapter;
  private String selectedTopic = Request.POPULAR;
  private List<Poster> loadedPosters;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    spanCount = getResources().getInteger(R.integer.posters_layout_span_count);
    postersAdapter = new PostersAdapter(spanCount);
    if (savedInstanceState != null) {
      selectedTopic = savedInstanceState.getString(KEY_SELECTED_TOPIC);
    }

    setContentView(R.layout.posters_activity);

    loadingView = (ContentLoadingProgressBar) findViewById(R.id.progress_bar);
    errorView = (ErrorView) findViewById(R.id.error_view);
    initRecyclerView();
    initMenuButton();

    postersAdapter.posterClickAction = new PostersAdapter.PosterClickAction() {
      @Override
      public void onPosterClick(Poster poster) {
        if (poster instanceof MoviePreview) {
          startActivity(MovieDetailsActivity.getIntent(PostersActivity.this, (MoviePreview) poster));
        } else {
          throw new IllegalArgumentException();
        }
      }
    };

    if (savedInstanceState != null && savedInstanceState.containsKey(KEY_LOADED_POSTERS)) {
      loadedPosters = savedInstanceState.getParcelableArrayList(KEY_LOADED_POSTERS);
      setLoadedData();
    } else {
      loadMovies();
    }
  }

  private void initRecyclerView() {
    contentView = (RecyclerView) findViewById(R.id.recycler_view);
    contentView.setLayoutManager(new StaggeredGridLayoutManager(spanCount, StaggeredGridLayoutManager.VERTICAL));
    contentView.setAdapter(postersAdapter);
  }

  private void initMenuButton() {
    BoomMenuButton bmb = (BoomMenuButton) findViewById(R.id.bmb);
    bmb.setButtonEnum(ButtonEnum.Ham);
    bmb.setPiecePlaceEnum(PiecePlaceEnum.HAM_5);
    bmb.setButtonPlaceEnum(ButtonPlaceEnum.HAM_5);
    int iconPadding = Util.dp2px(16);
    Rect iconRect = new Rect(iconPadding, iconPadding, iconPadding, iconPadding);
    List<Integer> buttonColors = ResourcesUtils.getBoomButtonColors(this, Request.topics().size());
    for (final String topic : Request.topics()) {
      HamButton.Builder builder = new HamButton.Builder()
        .normalImageRes(ResourcesUtils.topicIconRes(topic))
        .imagePadding(iconRect)
        .normalTextRes(ResourcesUtils.topicTitleRes(topic))
        .normalColor(buttonColors.get(0))
        .buttonWidth(Util.dp2px(200))
        .listener(new OnBMClickListener() {
          @Override
          public void onBoomButtonClick(int index) {
            if (selectedTopic.equals(topic)) {
              return;
            }
            selectedTopic = topic;
            contentView.smoothScrollToPosition(0);
            loadMovies();
          }
        });
      bmb.addBuilder(builder);
      //next color
      buttonColors.remove(0);
    }
  }

  @Override
  protected void onSaveInstanceState(Bundle outState) {
    outState.putString(KEY_SELECTED_TOPIC, selectedTopic);
    outState.putParcelableArrayList(KEY_LOADED_POSTERS, new ArrayList<>(postersAdapter.getPosters()));
    super.onSaveInstanceState(outState);
  }

  private void loadMovies() {
    if (selectedTopic.equals(Request.SAVED)) {
      loadSaved();
    } else {
      loadFromNetwork();
    }
  }

  private void loadSaved() {
    errorView.hideError();
    loadingView.show();
    new GetSavedMoviesTask(this).execute(new NetworkClasses.DataCallback<List<MoviePreview>>() {
      @Override
      public void onResult(NetworkClasses.FetchResult<List<MoviePreview>> result) {
        loadingView.hide();
        if (result.error == null) {
          errorView.hideError();
          contentView.setVisibility(View.VISIBLE);

          List<Poster> posters = new ArrayList<>();
          posters.addAll(result.data);
          loadedPosters = posters;
          setLoadedData();
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

  private void loadFromNetwork() {
    errorView.hideError();
    loadingView.show();
    Request.movieList(selectedTopic, new NetworkClasses.DataCallback<PageResponse<MoviePreview>>() {
      @Override
      public void onResult(NetworkClasses.FetchResult<PageResponse<MoviePreview>> result) {
        loadingView.hide();
        if (result.error == null) {
          errorView.hideError();
          contentView.setVisibility(View.VISIBLE);

          List<Poster> posters = new ArrayList<>();
          posters.addAll(result.data.results);
          loadedPosters = posters;
          setLoadedData();
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

  private void setLoadedData() {
    postersAdapter.setPosters(loadedPosters);
    postersAdapter.setTopic(selectedTopic);
  }
}
