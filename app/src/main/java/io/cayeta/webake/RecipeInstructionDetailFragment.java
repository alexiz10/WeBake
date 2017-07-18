package io.cayeta.webake;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.util.ArrayList;

import io.cayeta.webake.models.Instruction;

public class RecipeInstructionDetailFragment extends Fragment {

    private static final String ARGUMENT_INSTRUCTIONS_KEY = "instructions";
    private static final String ARGUMENT_SELECTED_INSTRUCTION_POSITION = "selected_instruction";

    private boolean mDualPane;

    private boolean mLandscape;

    private SimpleExoPlayer mExoPlayer;
    private SimpleExoPlayerView mPlayerView;

    private TextView mDescriptionTextView;

    public static RecipeInstructionDetailFragment newInstance(ArrayList<Instruction> instructions, int position) {
        RecipeInstructionDetailFragment recipeInstructionDetailFragment = new RecipeInstructionDetailFragment();

        Bundle args = new Bundle();
        args.putParcelableArrayList(ARGUMENT_INSTRUCTIONS_KEY, instructions);
        args.putInt(ARGUMENT_SELECTED_INSTRUCTION_POSITION, position);
        recipeInstructionDetailFragment.setArguments(args);

        return recipeInstructionDetailFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_recipe_instruction_detail, container, false);

        mDualPane = getResources().getBoolean(R.bool.isTablet);
        mLandscape = getResources().getBoolean(R.bool.isLandscape);

        Bundle bundle = getArguments();

        ArrayList<Instruction> instructions = bundle.getParcelableArrayList(ARGUMENT_INSTRUCTIONS_KEY);
        int currentPosition = bundle.getInt(ARGUMENT_SELECTED_INSTRUCTION_POSITION);

        mPlayerView = (SimpleExoPlayerView) view.findViewById(R.id.playerView);

        String videoURL = instructions.get(currentPosition).getVideoURL();
        String thumbnailURL = instructions.get(currentPosition).getThumbnailURL();

        Uri videoUri;

        if (!videoURL.equals("")) {
            videoUri = Uri.parse(videoURL);

            initializePlayer(videoUri);
        } else if (!thumbnailURL.equals("") && isMp4(thumbnailURL)) {
            videoUri = Uri.parse(thumbnailURL);

            initializePlayer(videoUri);
        } else {
            mPlayerView.setVisibility(View.GONE);
        }

        if (mDualPane || !mLandscape) {
            mDescriptionTextView = (TextView) view.findViewById(R.id.tv_instruction_description);
            mDescriptionTextView.setText(instructions.get(currentPosition).getDescription());
        }

        return view;
    }

    private boolean isMp4(String filename) {
        String fileType = filename.substring(filename.lastIndexOf(".") + 1, filename.length());

        return fileType.equalsIgnoreCase("mp4");
    }

    private void initializePlayer(Uri mediaUri) {
        if (mExoPlayer == null) {
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector, loadControl);
            mPlayerView.setPlayer(mExoPlayer);
            String userAgent = Util.getUserAgent(getContext(), "WeBake");
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri,
                    new DefaultDataSourceFactory(getContext(), userAgent),
                    new DefaultExtractorsFactory(), null, null);
            mExoPlayer.prepare(mediaSource);
            mExoPlayer.setPlayWhenReady(true);
        }
    }

    private void releasePlayer() {
        if (mExoPlayer != null) {
            mExoPlayer.stop();
            mExoPlayer.release();
            mExoPlayer = null;
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        releasePlayer();
    }
}