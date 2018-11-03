package baking.training.udacity.com.bakingappproject.fragments;

import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import baking.training.udacity.com.bakingappproject.R;
import baking.training.udacity.com.bakingappproject.model.Step;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * Use the {@link StepDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StepDetailsFragment extends Fragment {
    private static final String TAG = StepDetailsFragment.class.getSimpleName();

    // the fragment initialization parameters, e.g. STEP_SELECTED
    public static final String STEP_SELECTED = "stepSelected";
    public static final String RECIPE_NAME = "recipeName";
    public static final String CURRENT_WINDOW_INDEX = "current_window_index";
    public static final String PLAYBACK_POSITION = "playback_position";
    public static final String AUTOPLAY = "autoplay";

    //Play position
    private int currentWindow;
    private long playbackPosition;

    // autoplay = false
    private boolean autoPlay = false;

    private SimpleExoPlayer exoPlayer;

    private Step mStep;

    private SimpleExoPlayerView playVideoFragment;

    private TextView textViewDetailDescription;

    private ImageView imageViewRecipeImageStepDetail;

    private LinearLayout linearLayoutStepDetail;

    public StepDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param stepDetail Parameter 1.
     * @return A new instance of fragment StepDetailsFragment.
     */
    public static StepDetailsFragment newInstance(Step stepDetail) {
        Log.i(TAG, "newInstance() inside method: " + stepDetail);

        StepDetailsFragment fragment = new StepDetailsFragment();

        Bundle args = new Bundle();

        args.putSerializable(STEP_SELECTED, stepDetail);

        fragment.setArguments(args);

        return fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate() inside method: " + savedInstanceState);

        if(savedInstanceState != null){
            playbackPosition = savedInstanceState.getLong(PLAYBACK_POSITION, 0);
            currentWindow = savedInstanceState.getInt(CURRENT_WINDOW_INDEX, 0);
        }

        if (getArguments() != null) {
            mStep = (Step) getArguments().getSerializable(STEP_SELECTED);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_step_detail, container, false);

        if (savedInstanceState != null) {
            mStep = (Step) savedInstanceState.getSerializable(STEP_SELECTED);
        }

        playVideoFragment = view.findViewById(R.id.playvideo_fragment);
        linearLayoutStepDetail = view.findViewById(R.id.step_detail_linearlayout);
        textViewDetailDescription = view.findViewById(R.id.text_step_detail_description);
        imageViewRecipeImageStepDetail = view.findViewById(R.id.default_recipe_image_step_detail);

        onSetStepsDetail();

        return view;

    }

    private void onSetStepsDetail() {

        boolean isVideo = TextUtils.isEmpty(this.mStep.getVideoURL());
        boolean isImage = TextUtils.isEmpty(this.mStep.getThumbnailURL());

        Log.i(TAG, "onSetStepsDetail() inside method - image" + isImage);
        Log.i(TAG, "onSetStepsDetail() inside method - video" + isVideo);

        //verify if has video to show or only a image.
        if (!isVideo) {

            // Configuring video
            playVideoFragment.setVisibility(View.VISIBLE);
            imageViewRecipeImageStepDetail.setVisibility(View.GONE);

            //Modify the orientation
            setOnFullScreen();

            if (exoPlayer == null) {

                initPlayer();

            }

        } else if (isImage) {

            imageViewRecipeImageStepDetail.setVisibility(View.VISIBLE);

            Glide.with(getContext())
                    .load(R.drawable.recipes_hat_icon)
                    .into(imageViewRecipeImageStepDetail);

        }

        textViewDetailDescription.setText(mStep.getDescription());

    }

    private void initPlayer() {

        exoPlayer = ExoPlayerFactory
                        .newSimpleInstance(getContext(),
                                new DefaultTrackSelector(),
                                new DefaultLoadControl());

        playVideoFragment.setPlayer(exoPlayer);

        exoPlayer.setPlayWhenReady(autoPlay);

        // resume playback position
        exoPlayer.seekTo(currentWindow, playbackPosition);

        //Data Source
        String userAgent = Util.getUserAgent(getActivity(), "Baking");

        MediaSource mediaSource = new ExtractorMediaSource(
                Uri.parse(this.mStep.getVideoURL()),
                new DefaultDataSourceFactory(getActivity(), userAgent),
                new DefaultExtractorsFactory(),
                null,
                null);

        exoPlayer.prepare(mediaSource);

    }

    private void setOnFullScreen() {

        int ori = getResources().getConfiguration().orientation;

        boolean isTableMode = getResources().getBoolean(R.bool.isTablet);

        if((ori == Configuration.ORIENTATION_LANDSCAPE) && (!isTableMode)) {

            linearLayoutStepDetail.setVisibility(View.GONE);
            textViewDetailDescription.setVisibility(View.GONE);

            playVideoFragment.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FILL);
            playVideoFragment.getLayoutParams().height = ViewGroup.LayoutParams.MATCH_PARENT;
            playVideoFragment.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;

            hideSystemUi();

        }
    }

    private void hideSystemUi() {

        View decorView = getActivity().getWindow().getDecorView();

        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                        | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                        | View.SYSTEM_UI_FLAG_IMMERSIVE);
    }

    private void releasePlayer(){

        if (exoPlayer != null) {

            // save the player state before releasing its resources
            playbackPosition = exoPlayer.getCurrentPosition();

            currentWindow = exoPlayer.getCurrentWindowIndex();

            autoPlay = exoPlayer.getPlayWhenReady();

            exoPlayer.release();

            exoPlayer = null;

        }

    }

    @Override
    public void onResume() {
        super.onResume();
        if (exoPlayer == null) {
            initPlayer();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        releasePlayer();
    }

    @Override
    public void onPause() {
        super.onPause();
        releasePlayer();
    }

    @Override
    public void onStop() {
        super.onStop();
        releasePlayer();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

        outState.putSerializable(STEP_SELECTED, mStep);

        if(exoPlayer == null){
            outState.putLong(PLAYBACK_POSITION, playbackPosition);
            outState.putInt(CURRENT_WINDOW_INDEX, currentWindow);
            outState.putBoolean(AUTOPLAY, autoPlay);
        }

        super.onSaveInstanceState(outState);
    }

    public void onButtonPressed(Uri uri) {

    }

}