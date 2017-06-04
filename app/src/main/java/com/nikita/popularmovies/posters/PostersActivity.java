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
import com.nikita.popularmovies.common.ResourcesUtils;
import com.nikita.popularmovies.common.models.MoviePreview;
import com.nikita.popularmovies.common.models.PageResponse;
import com.nikita.popularmovies.common.models.Poster;
import com.nikita.popularmovies.common.network.Network;
import com.nikita.popularmovies.common.network.Request;
import com.nikita.popularmovies.common.views.ErrorView;
import com.nikita.popularmovies.details.MovieDetailsActivity;

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

    loadMovies();
  }

  private void initRecyclerView() {
    contentView = (RecyclerView) findViewById(R.id.recycler_view);
    contentView.setLayoutManager(new StaggeredGridLayoutManager(spanCount, StaggeredGridLayoutManager.VERTICAL));
    contentView.setAdapter(postersAdapter);
  }

  private void initMenuButton() {
    BoomMenuButton bmb = (BoomMenuButton) findViewById(R.id.bmb);
    bmb.setButtonEnum(ButtonEnum.Ham);
    bmb.setPiecePlaceEnum(PiecePlaceEnum.HAM_4);
    bmb.setButtonPlaceEnum(ButtonPlaceEnum.HAM_4);
    int iconPadding = Util.dp2px(16);
    Rect iconRect = new Rect(iconPadding, iconPadding, iconPadding, iconPadding);
    List<Integer> buttonColors = ResourcesUtils.getBoomButtonColors(this, 4);
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
          posters.addAll(result.data.getResults());
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
