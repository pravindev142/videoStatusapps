package com.saispark.marathi_video_status.ui.fragement;


import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import androidx.fragment.app.Fragment;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.InterstitialAdListener;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.LoopingMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.leo.simplearcloader.SimpleArcLoader;
import com.like.LikeButton;
import com.like.OnLikeListener;
import com.saispark.marathi_video_status.ui.Activities.EncryptionSecurity;
import com.saispark.marathi_video_status.ui.Activities.MainActivity;
import com.saispark.marathi_video_status.ui.Activities.VideoActivity;
import com.squareup.picasso.Picasso;
import com.saispark.marathi_video_status.Adapters.CommentAdapter;
import com.saispark.marathi_video_status.App;
import com.saispark.marathi_video_status.Provider.DownloadStorage;
import com.saispark.marathi_video_status.Provider.FavoritesStorage;
import com.saispark.marathi_video_status.Provider.PrefManager;
import com.saispark.marathi_video_status.R;
import com.saispark.marathi_video_status.api.apiClient;
import com.saispark.marathi_video_status.api.apiRest;
import com.saispark.marathi_video_status.model.ApiResponse;
import com.saispark.marathi_video_status.model.Comment;
import com.saispark.marathi_video_status.model.Status;
import com.saispark.marathi_video_status.ui.Activities.LoginActivity;
import com.saispark.marathi_video_status.ui.Activities.UserActivity;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class PlayerFragment extends Fragment {

    private String TAG =  "PlayerFragment";

    private static final String SHARE_ID="com.android.all";
    private static final String DOWNLOAD_ID="com.android.download";

    private InterstitialAd admobInterstitialAd;
    private com.facebook.ads.InterstitialAd facebookInterstitialAd;

    private int id;
    private int userid;
    private String user;
    private String userimage;
    private String created;
    private boolean comment;
    private int comments  = 0;
    private int font =  1;
    private int copied = 0;
    private String from;
    private String title;
    private String description;
    private String kind;
    private String original;
    private String thumbnail;
    private String type;
    private String extension;
    private int downloads;
    private int shares;
    private int views;
    private String tags;
    private boolean review;

    private int like;
    private int love;
    private int haha;
    private int sad;
    private int angry;
    private int woow;

    private String color;

    private Boolean downloading =false;
    private String path;
    private String urlToDownload;
    private String local;
    private int  reaction_count = 0;


    private SimpleExoPlayerView simpleExoPlayerView;
    private SimpleExoPlayer player;

    private Timeline.Window window;
    private DataSource.Factory mediaDataSourceFactory;
    private DefaultTrackSelector trackSelector;
    private boolean shouldAutoPlay;
    private BandwidthMeter bandwidthMeter;
    private Boolean playing = false;
    private ImageView ivHideControllerButton;
    private String URL ="https://aio.digitalenter10.com/uploads/mp4/d447caf81671a5bbcf6cc9fa27fb5b30.mp4";
    private SimpleArcLoader simple_arc_loader_exo;
    private ImageView exo_pause;
    private View view;
    private TextView text_view_fragment_player_title;
    private ImageView image_view_fragment_player_back;
    private TextView text_view_fragment_player_username;
    private ImageView image_view_fragment_player_like;
    private TextView text_view_fragment_player_like;
    private ImageView image_view_fragment_player_comment;
    private TextView text_view_fragment_player_comment;
    private ImageView image_view_fragment_player_share;
    private TextView text_view_fragment_player_share;
    private ImageView image_view_fragment_player_download;
    private TextView text_view_fragment_player_download;

    private TextView text_view_sad_activity_video;
    private TextView text_view_angry_activity_video;
    private TextView text_view_haha_activity_video;
    private TextView text_view_love_activity_video;
    private TextView text_view_like_activity_video;
    private TextView text_view_woow_activity_video;

    private LikeButton like_button_sad_activity_video;
    private LikeButton like_button_angry_activity_video;
    private LikeButton like_button_woow_activity_video;
    private LikeButton like_button_like_activity_video;
    private LikeButton like_button_haha_activity_video;
    private LikeButton like_button_love_activity_video;

    private RelativeLayout relative_layout_comment_section;
    private EditText edit_text_comment_add;
    private ProgressBar progress_bar_comment_add;
    private ProgressBar progress_bar_comment_list;
    private ImageView image_button_comment_add;
    private RecyclerView recycle_view_comment;
    private ImageView imageView_empty_comment;
    private ImageView image_view_comment_box_close;
    private RelativeLayout relative_layout_wallpaper_comments;
    private TextView text_view_comment_box_count;


    private ArrayList<Comment> commentList= new ArrayList<>();
    private CommentAdapter commentAdapter;
    private LinearLayoutManager linearLayoutManagerCOmment;
    private TextView text_view_progress_activity_video;
    private RelativeLayout relative_layout_progress_activity_video;
    private ProgressBar progress_bar_activity_video;

    private PrefManager prefManager;
    private boolean first=false;
    private ImageView exo_play;
    private ImageView user_image;
    private LinearLayout linear_layout_reactions_loading;
    private CardView card_view_reactions;
    private ImageView image_view_fragment_player_fav;
    private ImageView image_view_report;
    private Integer code_selected;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (this.isVisible())
        {

            if (isVisibleToUser)
            {
                player.setPlayWhenReady(true);
            }else{
                player.setPlayWhenReady(false);
                //  Toast.makeText(getActivity(), " hide = "+title, Toast.LENGTH_SHORT).show();
                playing =  false;
            }
        }

    }
    public PlayerFragment() {

    }

    public void initView(View view){

        this.image_view_report=(ImageView) view.findViewById(R.id.image_view_report);
        this.card_view_reactions=(CardView) view.findViewById(R.id.card_view_reactions);
        this.linear_layout_reactions_loading=(LinearLayout) view.findViewById(R.id.linear_layout_reactions_loading);
        this.text_view_sad_activity_video=(TextView) view.findViewById(R.id.text_view_sad_activity_video);
        this.text_view_angry_activity_video=(TextView) view.findViewById(R.id.text_view_angry_activity_video);
        this.text_view_haha_activity_video=(TextView) view.findViewById(R.id.text_view_haha_activity_video);
        this.text_view_love_activity_video=(TextView) view.findViewById(R.id.text_view_love_activity_video);
        this.text_view_like_activity_video=(TextView) view.findViewById(R.id.text_view_like_activity_video);
        this.text_view_woow_activity_video=(TextView) view.findViewById(R.id.text_view_woow_activity_video);

        this.like_button_angry_activity_video=(LikeButton) view.findViewById(R.id.like_button_angry_activity_video);
        this.like_button_like_activity_video=(LikeButton) view.findViewById(R.id.like_button_like_activity_video);
        this.like_button_love_activity_video=(LikeButton) view.findViewById(R.id.like_button_love_activity_video);
        this.like_button_sad_activity_video=(LikeButton) view.findViewById(R.id.like_button_sad_activity_video);
        this.like_button_woow_activity_video=(LikeButton) view.findViewById(R.id.like_button_woow_activity_video);
        this.like_button_haha_activity_video=(LikeButton) view.findViewById(R.id.like_button_haha_activity_video);



        this.user_image=(ImageView) view.findViewById(R.id.user_image);
        this.progress_bar_activity_video=(ProgressBar) view.findViewById(R.id.progress_bar_activity_video);

        this.relative_layout_progress_activity_video=(RelativeLayout) view.findViewById(R.id.relative_layout_progress_activity_video);
        this.text_view_progress_activity_video=(TextView) view.findViewById(R.id.text_view_progress_activity_video);


        this.text_view_fragment_player_title =  (TextView) view.findViewById(R.id.text_view_fragment_player_title);
        this.image_view_fragment_player_back =  (ImageView) view.findViewById(R.id.image_view_fragment_player_back);

        this.text_view_fragment_player_username =  (TextView) view.findViewById(R.id.text_view_fragment_player_username);

        this.image_view_fragment_player_like =  (ImageView) view.findViewById(R.id.image_view_fragment_player_like);
        this.text_view_fragment_player_like =  (TextView) view.findViewById(R.id.text_view_fragment_player_like);

        this.image_view_fragment_player_comment =  (ImageView) view.findViewById(R.id.image_view_fragment_player_comment);
        this.text_view_fragment_player_comment =  (TextView) view.findViewById(R.id.text_view_fragment_player_comment);

        this.image_view_fragment_player_share =  (ImageView) view.findViewById(R.id.image_view_fragment_player_share);
        this.text_view_fragment_player_share =  (TextView) view.findViewById(R.id.text_view_fragment_player_share);

        this.image_view_fragment_player_fav =  (ImageView) view.findViewById(R.id.image_view_fragment_player_fav);
        this.image_view_fragment_player_download =  (ImageView) view.findViewById(R.id.image_view_fragment_player_download);
        this.text_view_fragment_player_download =  (TextView) view.findViewById(R.id.text_view_fragment_player_download);
        this.text_view_comment_box_count =  (TextView) view.findViewById(R.id.text_view_comment_box_count);



        this.linearLayoutManagerCOmment=  new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);

        this.relative_layout_comment_section=(RelativeLayout) view.findViewById(R.id.relative_layout_comment_section);
        this.edit_text_comment_add=(EditText) view.findViewById(R.id.edit_text_comment_add);
        this.progress_bar_comment_add=(ProgressBar) view.findViewById(R.id.progress_bar_comment_add);
        this.progress_bar_comment_list=(ProgressBar) view.findViewById(R.id.progress_bar_comment_list);
        this.image_button_comment_add=(ImageView) view.findViewById(R.id.image_button_comment_add);
        this.recycle_view_comment=(RecyclerView) view.findViewById(R.id.recycle_view_comment);
        this.commentAdapter = new CommentAdapter(commentList, getActivity().getApplication());
        this.recycle_view_comment.setHasFixedSize(true);
        this.recycle_view_comment.setAdapter(commentAdapter);
        this.recycle_view_comment.setLayoutManager(linearLayoutManagerCOmment);
        this.imageView_empty_comment=(ImageView) view.findViewById(R.id.imageView_empty_comment);
        this.image_view_comment_box_close=(ImageView) view.findViewById(R.id.image_view_comment_box_close);
        this.relative_layout_wallpaper_comments=(RelativeLayout) view.findViewById(R.id.relative_layout_wallpaper_comments);
        image_button_comment_add.setEnabled(false);

        shouldAutoPlay = true;
        bandwidthMeter = new DefaultBandwidthMeter();
        mediaDataSourceFactory = new DefaultDataSourceFactory(getActivity().getApplicationContext(), Util.getUserAgent(getActivity().getApplicationContext(), "mediaPlayerSample"));
        window = new Timeline.Window();
        ivHideControllerButton = (ImageView) view.findViewById(R.id.exo_controller_full);
        ivHideControllerButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_fullscreen_exit));
        simple_arc_loader_exo = (SimpleArcLoader) view.findViewById(R.id.simple_arc_loader_exo);
        exo_pause = (ImageView) view.findViewById(R.id.exo_pause);
        exo_play = (ImageView) view.findViewById(R.id.exo_play);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        this.view =  inflater.inflate(R.layout.fragment_player, container, false);
        this.prefManager= new PrefManager(getActivity().getApplicationContext());

        Bundle bundle = this.getArguments() ;

        this.id = bundle.getInt("id");
        this.title = bundle.getString("title");
        this.description = bundle.getString("description");
        this.thumbnail = bundle.getString("thumbnail");
        this.userid = bundle.getInt("userid");
        this.user = bundle.getString("user");
        this.userimage = bundle.getString("userimage");
        this.type = bundle.getString("type");
        this.original = bundle.getString("original");
        this.extension = bundle.getString("extension");
        this.comment = bundle.getBoolean("comment");
        this.downloads = bundle.getInt("downloads");
        this.views = bundle.getInt("views");
        this.tags = bundle.getString("tags");
        this.review = bundle.getBoolean("review");
        this.comments = bundle.getInt("comments");
        this.created = bundle.getString("created");
        this.local = bundle.getString("local");

        this.like = bundle.getInt("like");
        this.love  = bundle.getInt("love");
        this.woow = bundle.getInt("woow");
        this.angry = bundle.getInt("angry");
        this.sad = bundle.getInt("sad");
        this.haha = bundle.getInt("haha");

        this.kind = bundle.getString("kind");
        this.color = bundle.getString("color");
        this.first = bundle.getBoolean("first");



        if(!checkSUBSCRIBED()) {
            if (prefManager.getString("ADMIN_INTERSTITIAL_TYPE").equals("ADMOB")) {
                requestAdmobInterstitial();
            } else if (prefManager.getString("ADMIN_INTERSTITIAL_TYPE").equals("FACEBOOK")){
                requestFacebookInterstitial();
            } else if (prefManager.getString("ADMIN_INTERSTITIAL_TYPE").equals("BOTH")){
                requestAdmobInterstitial();
                requestFacebookInterstitial();
            }
        }

        initView(view);
        initAction();
        URL =  original;
        urlToDownload =  URL;
        setStatus();
        setReaction(prefManager.getString("reaction_"+id));

        initializePlayer();

        return view;
    }

    public boolean checkSUBSCRIBED(){
        PrefManager prefManager= new PrefManager(getActivity().getApplicationContext());
        if (!prefManager.getString("SUBSCRIBED").equals("TRUE")) {
            return false;
        }
        return true;
    }
    private void requestAdmobInterstitial() {
        if (admobInterstitialAd==null){
            PrefManager prefManager= new PrefManager(getActivity());
            admobInterstitialAd = new InterstitialAd(getActivity().getApplicationContext());
            admobInterstitialAd.setAdUnitId(prefManager.getString("ADMIN_INTERSTITIAL_ADMOB_ID"));
        }
        if (!admobInterstitialAd.isLoaded()){
            AdRequest adRequest = new AdRequest.Builder()
                    .build();
            admobInterstitialAd.loadAd(adRequest);
        }
    }
    private void requestFacebookInterstitial() {
        if (facebookInterstitialAd==null) {
            PrefManager prefManager= new PrefManager(getActivity());
            facebookInterstitialAd = new com.facebook.ads.InterstitialAd(getActivity(), prefManager.getString("ADMIN_INTERSTITIAL_FACEBOOK_ID"));
        }
        if (!facebookInterstitialAd.isAdLoaded()) {
            InterstitialAdListener interstitialAdListener = new InterstitialAdListener() {
                @Override
                public void onInterstitialDisplayed(Ad ad) {
                }

                @Override
                public void onInterstitialDismissed(Ad ad) {
                    selectOperation(code_selected);
                }

                @Override
                public void onError(Ad ad, AdError adError) {
                }

                @Override
                public void onAdLoaded(Ad ad) {
                }

                @Override
                public void onAdClicked(Ad ad) {
                }

                @Override
                public void onLoggingImpression(Ad ad) {
                }
            };
            facebookInterstitialAd.loadAd(
                    facebookInterstitialAd.buildLoadAdConfig()
                            .withAdListener(interstitialAdListener)
                            .build());
        }
    }

    private void setStatus() {

        this.reaction_count = haha + like + love + woow +  sad + angry ;


        text_view_fragment_player_title.setText(title);
        text_view_fragment_player_username.setText(user);
        text_view_fragment_player_like.setText(format(reaction_count));
        text_view_fragment_player_comment.setText(format(comments));
        text_view_fragment_player_share.setText(format(shares));
        text_view_fragment_player_download.setText(format(downloads));
        Picasso.with(getActivity()).load(userimage).placeholder(R.drawable.profile).into(user_image);

        this.text_view_like_activity_video.setText(format(like));
        this.text_view_love_activity_video.setText(format(love));
        this.text_view_angry_activity_video.setText(format(angry));
        this.text_view_haha_activity_video.setText(format(haha));
        this.text_view_woow_activity_video.setText(format(woow));
        this.text_view_sad_activity_video.setText(format(sad));

        final FavoritesStorage storageFavorites= new FavoritesStorage(getActivity().getApplicationContext());

        List<Status> favorites_list = storageFavorites.loadImagesFavorites();
        Boolean exist = false;
        if (favorites_list==null){
            favorites_list= new ArrayList<>();
        }
        for (int i = 0; i <favorites_list.size() ; i++) {
            if (favorites_list.get(i).getId().equals(id)){
                exist = true;
            }
        }
        if (exist  == false) {
            image_view_fragment_player_fav.setImageDrawable(getResources().getDrawable(R.drawable.ic_favorite_border));
        }else{
            image_view_fragment_player_fav.setImageDrawable(getResources().getDrawable(R.drawable.ic_favorite));
        }
    }

    private void initAction() {
        image_view_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Report();
            }
        });
        text_view_fragment_player_username.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),UserActivity.class);
                intent.putExtra("id",userid);
                intent.putExtra("name",user);
                intent.putExtra("image",userimage);
                startActivity(intent);
            }
        });
        image_view_fragment_player_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        this.image_view_fragment_player_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                card_view_reactions.setVisibility(View.VISIBLE);
            }
        });
        this.image_view_fragment_player_fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                favorite();
            }
        });
        this.like_button_like_activity_video.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
                addLike(id);
                card_view_reactions.setVisibility(View.GONE);
            }

            @Override
            public void unLiked(LikeButton likeButton) {
                deleteLike(id);
                setReaction("none");
                prefManager.setString("reaction_"+id,"none");
                card_view_reactions.setVisibility(View.GONE);

            }
        });

        this.like_button_love_activity_video.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
                addLove(id);
                card_view_reactions.setVisibility(View.GONE);

            }

            @Override
            public void unLiked(LikeButton likeButton) {
                deleteLove(id);
                setReaction("none");
                prefManager.setString("reaction_"+id,"none");
                card_view_reactions.setVisibility(View.GONE);

            }
        });
        this.like_button_woow_activity_video.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
                addWoow(id);
                card_view_reactions.setVisibility(View.GONE);


            }

            @Override
            public void unLiked(LikeButton likeButton) {
                deleteWoow(id);
                setReaction("none");
                prefManager.setString("reaction_"+id,"none");
                card_view_reactions.setVisibility(View.GONE);

            }
        });

        this.like_button_angry_activity_video.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
                addAngry(id);
                card_view_reactions.setVisibility(View.GONE);

            }

            @Override
            public void unLiked(LikeButton likeButton) {
                deleteAngry(id);
                setReaction("none");
                prefManager.setString("reaction_"+id,"none");
                card_view_reactions.setVisibility(View.GONE);

            }
        });
        this.like_button_sad_activity_video.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
                addSad(id);
                card_view_reactions.setVisibility(View.GONE);

            }

            @Override
            public void unLiked(LikeButton likeButton) {
                deleteSad(id);
                setReaction("none");
                prefManager.setString("reaction_"+id,"none");
                card_view_reactions.setVisibility(View.GONE);

            }
        });
        this.like_button_haha_activity_video.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
                addHaha(id);
                card_view_reactions.setVisibility(View.GONE);

            }

            @Override
            public void unLiked(LikeButton likeButton) {
                deleteHaha(id);
                setReaction("none");
                prefManager.setString("reaction_"+id,"none");
                card_view_reactions.setVisibility(View.GONE);

            }
        });

        this.image_view_fragment_player_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCommentBox();
            }
        });
        this.image_view_comment_box_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCommentBox();
            }
        });
        this.relative_layout_wallpaper_comments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCommentBox();
            }
        });
        this.image_button_comment_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addComment();
            }
        });

        this.edit_text_comment_add.addTextChangedListener(new CommentTextWatcher(this.edit_text_comment_add));
        this.image_view_fragment_player_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Operation(90006);
            }
        });
        this.image_view_fragment_player_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Operation(90007);
            }
        });
    }
    private class CommentTextWatcher implements TextWatcher {
        private View view;
        private CommentTextWatcher(View view) {
            this.view = view;
        }
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }
        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.edit_text_comment_add:
                    ValidateComment();
                    break;
            }
        }
    }

    public void addComment(){


        PrefManager prf= new PrefManager(getActivity().getApplicationContext());
        if (prf.getString("LOGGED").toString().equals("TRUE")){

            byte[] data = new byte[0];
            String comment_final ="";
            try {
                data = edit_text_comment_add.getText().toString().getBytes("UTF-8");
                comment_final = Base64.encodeToString(data, Base64.DEFAULT);

            } catch (UnsupportedEncodingException e) {
                comment_final = edit_text_comment_add.getText().toString();
                e.printStackTrace();
            }
            progress_bar_comment_add.setVisibility(View.VISIBLE);
            image_button_comment_add.setVisibility(View.GONE);
            Retrofit retrofit = apiClient.getClient();
            apiRest service = retrofit.create(apiRest.class);
            Call<ApiResponse> call = service.addCommentImage(prf.getString("ID_USER").toString(),id,comment_final);
            call.enqueue(new Callback<ApiResponse>() {
                @Override
                public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                    if (response.isSuccessful()){
                        if (response.body().getCode()==200){
                            comments++ ;
                            text_view_comment_box_count.setText(format(comments)+  " " +getActivity().getResources().getString(R.string.comments));
                            text_view_fragment_player_comment.setText(format(comments));
                            recycle_view_comment.setVisibility(View.VISIBLE);
                            imageView_empty_comment.setVisibility(View.GONE);
                            Toasty.success(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            edit_text_comment_add.setText("");
                            String id="";
                            String content="";
                            String user="";
                            String image="";

                            String trusted="false";

                            for (int i=0;i<response.body().getValues().size();i++){
                                if (response.body().getValues().get(i).getName().equals("id")){
                                    id=response.body().getValues().get(i).getValue();
                                }
                                if (response.body().getValues().get(i).getName().equals("content")){
                                    content=response.body().getValues().get(i).getValue();
                                }
                                if (response.body().getValues().get(i).getName().equals("user")){
                                    user=response.body().getValues().get(i).getValue();
                                }
                                if (response.body().getValues().get(i).getName().equals("trusted")){
                                    trusted=response.body().getValues().get(i).getValue();
                                }
                                if (response.body().getValues().get(i).getName().equals("image")){
                                    image=response.body().getValues().get(i).getValue();
                                }
                            }
                            Comment comment= new Comment();
                            comment.setId(Integer.parseInt(id));
                            comment.setUser(user);
                            comment.setContent(content);
                            comment.setImage(image);
                            comment.setEnabled(true);
                            comment.setTrusted(trusted);
                            comment.setCreated(getResources().getString(R.string.now_time));
                            commentList.add(comment);
                            commentAdapter.notifyDataSetChanged();

                        }else{
                            Toasty.error(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                    recycle_view_comment.scrollToPosition(recycle_view_comment.getAdapter().getItemCount()-1);
                    recycle_view_comment.scrollToPosition(recycle_view_comment.getAdapter().getItemCount()-1);
                    commentAdapter.notifyDataSetChanged();
                    progress_bar_comment_add.setVisibility(View.GONE);
                    image_button_comment_add.setVisibility(View.VISIBLE);
                }
                @Override
                public void onFailure(Call<ApiResponse> call, Throwable t) {
                    progress_bar_comment_add.setVisibility(View.VISIBLE);
                    image_button_comment_add.setVisibility(View.GONE);
                }
            });
        }else{
            Intent intent = new Intent(getActivity(),LoginActivity.class);
            startActivity(intent);
        }

    }
    private boolean ValidateComment() {
        String email = edit_text_comment_add.getText().toString().trim();
        if (email.isEmpty()) {
            image_button_comment_add.setEnabled(false);
            return false;
        }else{
            image_button_comment_add.setEnabled(true);
        }
        return true;
    }
    public void getComments(){
        progress_bar_comment_list.setVisibility(View.VISIBLE);
        recycle_view_comment.setVisibility(View.GONE);
        imageView_empty_comment.setVisibility(View.GONE);
        Retrofit retrofit = apiClient.getClient();
        apiRest service = retrofit.create(apiRest.class);
        Call<List<Comment>> call = service.getComments(id);
        call.enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
                if(response.isSuccessful()) {
                    commentList.clear();
                    comments = response.body().size();
                    text_view_fragment_player_comment.setText(format(comments));
                    text_view_comment_box_count.setText(format(comments) +" " + getActivity().getResources().getString(R.string.comments));
                    if (response.body().size() != 0) {
                        for (int i = 0; i < response.body().size(); i++) {
                            commentList.add(response.body().get(i));
                        }
                        commentAdapter.notifyDataSetChanged();

                        progress_bar_comment_list.setVisibility(View.GONE);
                        recycle_view_comment.setVisibility(View.VISIBLE);
                        imageView_empty_comment.setVisibility(View.GONE);


                    } else {
                        progress_bar_comment_list.setVisibility(View.GONE);
                        recycle_view_comment.setVisibility(View.GONE);
                        imageView_empty_comment.setVisibility(View.VISIBLE);

                    }
                }else{

                }
                recycle_view_comment.scrollToPosition(recycle_view_comment.getAdapter().getItemCount()-1);
                recycle_view_comment.scrollToPosition(recycle_view_comment.getAdapter().getItemCount()-1);
                recycle_view_comment.setNestedScrollingEnabled(false);

            }

            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {


            }
        });
    }
    public void showCommentBox(){
        getComments();
        if (relative_layout_wallpaper_comments.getVisibility() == View.VISIBLE)
        {
            Animation c= AnimationUtils.loadAnimation(getActivity().getApplicationContext(),
                    R.anim.slide_down);
            c.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }
                @Override
                public void onAnimationEnd(Animation animation) {
                    relative_layout_wallpaper_comments.setVisibility(View.GONE);
                }
                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            relative_layout_wallpaper_comments.startAnimation(c);


        }else{
            Animation c= AnimationUtils.loadAnimation(getActivity().getApplicationContext(),
                    R.anim.slide_up);
            c.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    relative_layout_wallpaper_comments.setVisibility(View.VISIBLE);
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            relative_layout_wallpaper_comments.startAnimation(c);

        }

    }

    private void initializePlayer() {

        // Create a default TrackSelector
        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        TrackSelection.Factory videoTrackSelectionFactory =
                new AdaptiveTrackSelection.Factory(bandwidthMeter);
        TrackSelector trackSelector =
                new DefaultTrackSelector(videoTrackSelectionFactory);

        //Initialize the player
        player = ExoPlayerFactory.newSimpleInstance(getActivity(), trackSelector);

        //Initialize simpleExoPlayerView
        simpleExoPlayerView = view.findViewById(R.id.video_view);
        simpleExoPlayerView.setPlayer(player);
        // Produces DataSource instances through which media data is loaded.
        DataSource.Factory dataSourceFactory =
                new DefaultDataSourceFactory(getActivity(), Util.getUserAgent(getActivity(), "CloudinaryExoplayer"));

        // Produces Extractor instances for parsing the media data.
        ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();

        // This is the MediaSource representing the media to be played.
        Uri videoUri = Uri.parse(URL);
        MediaSource videoSource = new ExtractorMediaSource(videoUri,
                dataSourceFactory, extractorsFactory, null, null);

        // Prepare the player with the source.
        LoopingMediaSource loopingSource = new LoopingMediaSource(videoSource);

        player.prepare(loopingSource);



        simple_arc_loader_exo.setVisibility(View.VISIBLE);

        exo_play.setVisibility(View.GONE);
        simpleExoPlayerView.setControllerShowTimeoutMs(1500);

        ivHideControllerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        player.addListener(new Player.EventListener() {


            @Override
            public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

            }

            @Override
            public void onLoadingChanged(boolean isLoading) {

            }

            @Override
            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                if (playbackState == ExoPlayer.STATE_READY){
                    simple_arc_loader_exo.setVisibility(View.GONE);
                    exo_pause.setVisibility(View.GONE);
                    exo_play.setVisibility(View.GONE);
                    playing = true;
                    exo_play.setColorFilter(ContextCompat.getColor(getActivity().getApplicationContext(), R.color.white), android.graphics.PorterDuff.Mode.SRC_IN);
                    addView();
                }
                if (playbackState == ExoPlayer.STATE_BUFFERING){
                    simple_arc_loader_exo.setVisibility(View.VISIBLE);
                    exo_pause.setVisibility(View.GONE);
                    exo_play.setVisibility(View.GONE);

                }
            }

            @Override
            public void onRepeatModeChanged(int repeatMode) {

            }

            @Override
            public void onPlayerError(ExoPlaybackException error) {

            }



            @Override
            public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

            }
        });
    }

    private void releasePlayer() {

        if (player != null) {
            Log.v("VideoPlayer","player not null");
            shouldAutoPlay = player.getPlayWhenReady();
            player.release();
            player = null;
            trackSelector = null;
        }
    }


    @Override
    public void onResume() {
        first = false;
        super.onResume();
        if ((player == null)) {
            if (playing)
                initializePlayer();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT > 23) {
            Log.v("VideoPlayer","onPause");
            releasePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            releasePlayer();
            Log.v("VideoPlayer","onStop");
        }
    }


    private static final NavigableMap<Long, String> suffixes = new TreeMap<>();
    static {
        suffixes.put(1_000L, "k");
        suffixes.put(1_000_000L, "M");
        suffixes.put(1_000_000_000L, "G");
        suffixes.put(1_000_000_000_000L, "T");
        suffixes.put(1_000_000_000_000_000L, "P");
        suffixes.put(1_000_000_000_000_000_000L, "E");
    }
    public static String format(long value) {
        //Long.MIN_VALUE == -Long.MIN_VALUE so we need an adjustment here
        if (value == Long.MIN_VALUE) return format(Long.MIN_VALUE + 1);
        if (value < 0) return "-" + format(-value);
        if (value < 1000) return Long.toString(value); //deal with easy case

        Map.Entry<Long, String> e = suffixes.floorEntry(value);
        Long divideBy = e.getKey();
        String suffix = e.getValue();

        long truncated = value / (divideBy / 10); //the number part of the output times 10
        boolean hasDecimal = truncated < 100 && (truncated / 10d) != (truncated / 10);
        return hasDecimal ? (truncated / 10d) + suffix : (truncated / 10) + suffix;
    }
    private void download() {
        Toasty.success(getActivity().getApplicationContext(), getResources().getString(R.string.images_downloaded), Toast.LENGTH_SHORT, true).show();
    }
    public void share(String path){
        Uri imageUri = FileProvider.getUriForFile(getActivity(), getActivity().getApplicationContext().getPackageName() + ".provider", new File(path));
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);


        final String final_text = getResources().getString(R.string.download_more_from_link);

        shareIntent.putExtra(Intent.EXTRA_TEXT,final_text );
        shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);

        shareIntent.setType(type);
        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        try {
            startActivity(Intent.createChooser(shareIntent,getResources().getString(R.string.share_via)+ " " + getResources().getString(R.string.app_name) ));
        } catch (android.content.ActivityNotFoundException ex) {
            Toasty.error(getActivity().getApplicationContext(),getResources().getString(R.string.app_not_installed) , Toast.LENGTH_SHORT, true).show();
        }
    }
    /**
     * Background Async Task to download file
     * */
    class DownloadFileFromURL extends AsyncTask<Object, String, String> {

        private int position;
        private String old = "-100";
        private boolean runing = true;
        private String share_app;

        /**
         * Before starting background thread
         * Show Progress Bar Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            setDownloading(true);
            Log.v("prepost","ok");
        }
        public boolean dir_exists(String dir_path)
        {
            boolean ret = false;
            File dir = new File(dir_path);
            if(dir.exists() && dir.isDirectory())
                ret = true;
            return ret;
        }
        @Override
        protected void onCancelled() {
            super.onCancelled();
            runing = false;
        }
        /**
         * Downloading file in background thread
         * */
        @Override
        protected String doInBackground(Object... f_url) {
            int count;
            try {
                java.net.URL url = new URL((String) f_url[0]);
                String title = (String) f_url[1];
                String extension = (String) f_url[2];
                this.position = (int) f_url[3];
                this.share_app = (String) f_url[4];
                Log.v("v",(String) f_url[0]);

                URLConnection conection = url.openConnection();
                conection.setRequestProperty("Accept-Encoding", "identity");
                conection.connect();

                int lenghtOfFile = conection.getContentLength();
                Log.v("lenghtOfFile",lenghtOfFile+"");

                // download the file
                InputStream input = new BufferedInputStream(url.openStream(), 8192);



                String dir_path = Environment.getExternalStorageDirectory().toString() + "/StatusVideos/";

                if (!dir_exists(dir_path)){
                    File directory = new File(dir_path);
                    if(directory.mkdirs()){
                        Log.v("dir","is created 1");
                    }else{
                        Log.v("dir","not created 1");

                    }
                    if(directory.mkdir()){
                        Log.v("dir","is created 2");
                    }else{
                        Log.v("dir","not created 2");

                    }
                }else{
                    Log.v("dir","is exist");
                }
                File file= new File(dir_path+title.toString().replace("/","_")+"_"+id+"."+extension);
                if(!file.exists()){
                    Log.v("dir","file is exist");
                    OutputStream output = new FileOutputStream(dir_path+title.toString().replace("/","_")+"_"+id+"."+extension);


                    byte data[] = new byte[1024];

                    long total = 0;


                    while ((count = input.read(data)) != -1) {
                        total += count;
                        // publishing the progress....
                        // After this onProgressUpdate will be called
                        publishProgress(""+(int)((total*100)/lenghtOfFile));
                        // writing data to file
                        output.write(data, 0, count);
                        if (!runing){
                            Log.v("v","not rurning");
                        }
                    }

                    output.flush();

                    output.close();
                    input.close();

                }
                MediaScannerConnection.scanFile(getActivity().getApplicationContext(), new String[] { dir_path+title.toString().replace("/","_")+"_"+id+"."+extension },
                        null,
                        new MediaScannerConnection.OnScanCompletedListener() {
                            @Override
                            public void onScanCompleted(String path, Uri uri) {

                            }
                        });
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    final Intent scanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                    final Uri contentUri = Uri.fromFile(new File(dir_path+title.toString().replace("/","_")+"_"+id+"."+extension));
                    scanIntent.setData(contentUri);
                    getActivity().sendBroadcast(scanIntent);
                } else {
                    final Intent intent = new Intent(Intent.ACTION_MEDIA_MOUNTED, Uri.parse("file://" + Environment.getExternalStorageDirectory()));
                    getActivity().sendBroadcast(intent);
                }
                path = dir_path+title.toString().replace("/","_")+"_"+id+"."+extension;
            } catch (Exception e) {
                //Log.v("ex",e.getMessage());
            }

            return null;
        }

        /**
         * Updating progress bar
         * */
        protected void onProgressUpdate(String... progress) {
            // setting progress percentage
            try {
                if(!progress[0].equals(old)){
                    old=progress[0];
                    Log.v("download",progress[0]+"%");
                    setDownloading(true);
                    setProgressValue(Integer.parseInt(progress[0]));
                }
            }catch (Exception e){

            }

        }

        /**
         * After completing background task
         * Dismiss the progress dialog
         * **/
        @Override
        protected void onPostExecute(String file_url) {

            setDownloading(false);
            if (path==null){
                if (local!=null){
                    switch (share_app) {
                        case SHARE_ID:
                            share(local);
                            break;
                        case DOWNLOAD_ID:
                            download();
                            break;
                    }
                }else {
                    try {
                        Toasty.error(App.getInstance(), getResources().getString(R.string.download_failed), Toast.LENGTH_SHORT, true).show();
                    }catch (Exception e){

                    }
                }

            }else {

                AddDownloadLocal(path);
                switch (share_app) {
                    case SHARE_ID:
                        share(path);
                        addShare(id);
                        break;
                    case DOWNLOAD_ID:
                        download();
                        addDownload(id);
                        break;
                }
            }
        }

        private void download() {
            Toasty.success(getActivity().getApplicationContext(), getResources().getString(R.string.images_downloaded), Toast.LENGTH_SHORT, true).show();
        }


        public void share(String path){
            Uri imageUri = FileProvider.getUriForFile(getActivity(), getActivity().getApplicationContext().getPackageName() + ".provider", new File(path));
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);


            final String final_text = getResources().getString(R.string.download_more_from_link);

            shareIntent.putExtra(Intent.EXTRA_TEXT,final_text );
            shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);

            shareIntent.setType(type);
            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            try {
                startActivity(Intent.createChooser(shareIntent,getResources().getString(R.string.share_via)+ " " + getResources().getString(R.string.app_name) ));
            } catch (android.content.ActivityNotFoundException ex) {
                Toasty.error(getActivity().getApplicationContext(),getResources().getString(R.string.app_not_installed) , Toast.LENGTH_SHORT, true).show();
            }
        }
    }
    public void setDownloading(Boolean downloading){
        if (downloading){
            relative_layout_progress_activity_video.setVisibility(View.VISIBLE);
        }else{
            relative_layout_progress_activity_video.setVisibility(View.GONE);
        }
        this.downloading = downloading;
    }
    public void setProgressValue(int progress){
        this.progress_bar_activity_video.setProgress(progress);
        this.text_view_progress_activity_video.setText(getResources().getString(R.string.downloading)+" "+progress+" %");
    }
    public void addShare(Integer id){
        final PrefManager prefManager = new PrefManager(getActivity());
        Integer id_user = 0;
        String key_user= "";
        if (prefManager.getString("LOGGED").toString().equals("TRUE")) {
            id_user=  Integer.parseInt(prefManager.getString("ID_USER"));
            key_user=  prefManager.getString("TOKEN_USER");
        }
        if (!prefManager.getString(id+"_share").equals("true")) {
            prefManager.setString(id+"_share", "true");
            Retrofit retrofit = apiClient.getClient();
            apiRest service = retrofit.create(apiRest.class);
            Call<Integer> call = service.addShare(id,id_user,key_user);
            call.enqueue(new Callback<Integer>() {
                @Override
                public void onResponse(Call<Integer> call, Response<Integer> response) {
                    if (response.isSuccessful())
                        text_view_fragment_player_share.setText(format(response.body()));
                }
                @Override
                public void onFailure(Call<Integer> call, Throwable t) {

                }
            });
        }
    }
    public void addDownload(Integer id){
        final PrefManager prefManager = new PrefManager(getActivity());
        Integer id_user = 0;
        String key_user= "";
        if (prefManager.getString("LOGGED").toString().equals("TRUE")) {
            id_user=  Integer.parseInt(prefManager.getString("ID_USER"));
            key_user=  prefManager.getString("TOKEN_USER");
        }
        if (!prefManager.getString(id+"_download").equals("true")) {
            prefManager.setString(id+"_download", "true");
            Retrofit retrofit = apiClient.getClient();
            apiRest service = retrofit.create(apiRest.class);
            Call<Integer> call = service.addShare(id,id_user,key_user);
            call.enqueue(new Callback<Integer>() {
                @Override
                public void onResponse(Call<Integer> call, Response<Integer> response) {
                    if (response.isSuccessful())
                        text_view_fragment_player_download.setText(format(response.body()));
                }
                @Override
                public void onFailure(Call<Integer> call, Throwable t) {

                }
            });
        }
    }




    public void AddDownloadLocal(String localpath){
        final DownloadStorage downloadStorage= new DownloadStorage(getActivity().getApplicationContext());
        List<Status> download_list = downloadStorage.loadImagesFavorites();
        Boolean exist = false;
        if (download_list==null){
            download_list= new ArrayList<>();
        }
        for (int i = 0; i <download_list.size() ; i++) {
            if (download_list.get(i).getId().equals(id)){
                exist = true;
            }
        }
        if (exist  == false) {
            ArrayList<Status> audios= new ArrayList<Status>();
            for (int i = 0; i < download_list.size(); i++) {
                audios.add(download_list.get(i));
            }
            Status StatusDownload = new Status();
            StatusDownload.setId(id);
            StatusDownload.setTitle(title);
            StatusDownload.setDescription(description);
            StatusDownload.setThumbnail(thumbnail);
            StatusDownload.setUserid(userid);
            StatusDownload.setUser(user);
            StatusDownload.setUserimage(userimage);
            StatusDownload.setType(type);
            StatusDownload.setOriginal(original);
            StatusDownload.setExtension(extension);
            StatusDownload.setComment(comment);
            StatusDownload.setDownloads(downloads);
            StatusDownload.setViews(views);
            StatusDownload.setFont(font);
            StatusDownload.setTags(tags);
            StatusDownload.setReview(review);
            StatusDownload.setComments( comments);
            StatusDownload.setCreated(created);
            StatusDownload.setLocal(localpath);

            StatusDownload.setLike(like);
            StatusDownload.setLove(love);
            StatusDownload.setWoow(woow);
            StatusDownload.setAngry(angry);
            StatusDownload.setSad(sad);
            StatusDownload.setHaha(haha);

            StatusDownload.setKind(kind);
            StatusDownload.setColor( color);
            audios.add(StatusDownload);
            downloadStorage.storeImage(audios);
        }
    }



    public void favorite(){
        final FavoritesStorage storageFavorites= new FavoritesStorage(getActivity().getApplicationContext());

        List<Status> favorites_list = storageFavorites.loadImagesFavorites();
        Boolean exist = false;
        if (favorites_list==null){
            favorites_list= new ArrayList<>();
        }
        for (int i = 0; i <favorites_list.size() ; i++) {
            if (favorites_list.get(i).getId().equals(id)){
                exist = true;
            }
        }
        if (exist  == false) {
            ArrayList<Status> audios= new ArrayList<Status>();
            for (int i = 0; i < favorites_list.size(); i++) {
                audios.add(favorites_list.get(i));
            }

            Status StatusDownload = new Status();
            StatusDownload.setId(id);
            StatusDownload.setTitle(title);
            StatusDownload.setDescription(description);
            StatusDownload.setThumbnail(thumbnail);
            StatusDownload.setUserid(userid);
            StatusDownload.setUser(user);
            StatusDownload.setUserimage(userimage);
            StatusDownload.setType(type);
            StatusDownload.setOriginal(original);
            StatusDownload.setExtension(extension);
            StatusDownload.setComment(comment);
            StatusDownload.setDownloads(downloads);
            StatusDownload.setViews(views);
            StatusDownload.setFont(font);
            StatusDownload.setTags(tags);
            StatusDownload.setReview(review);
            StatusDownload.setComments( comments);
            StatusDownload.setCreated(created);
            StatusDownload.setLocal(local);

            StatusDownload.setLike(like);
            StatusDownload.setLove(love);
            StatusDownload.setWoow(woow);
            StatusDownload.setAngry(angry);
            StatusDownload.setSad(sad);
            StatusDownload.setHaha(haha);

            StatusDownload.setKind(kind);
            StatusDownload.setColor( color);
            audios.add(StatusDownload);
            storageFavorites.storeImage(audios);
            image_view_fragment_player_fav.setImageDrawable(getResources().getDrawable(R.drawable.ic_favorite));
        }else{
            ArrayList<Status> new_favorites= new ArrayList<Status>();
            for (int i = 0; i < favorites_list.size(); i++) {
                if (!favorites_list.get(i).getId().equals(id)){
                    new_favorites.add(favorites_list.get(i));

                }
            }
            storageFavorites.storeImage(new_favorites);
            image_view_fragment_player_fav.setImageDrawable(getResources().getDrawable(R.drawable.ic_favorite_border));
        }
    }
    public void run(){
        player.setPlayWhenReady(true);
    }
    public void onBackPressed() {
        if(EncryptionSecurity.isOnline(getActivity())){
            requestAdmobInterstitial2();
        }
        else {
                if (relative_layout_wallpaper_comments.getVisibility() == View.VISIBLE) {
            relative_layout_wallpaper_comments.setVisibility(View.GONE);
        } else {
            getActivity().finish();
        }
        }

    }


    public void addLike(Integer id){
        removeReaction(prefManager.getString("reaction_"+id));
        reaction_count++;

        linear_layout_reactions_loading.setVisibility(View.VISIBLE);
        Retrofit retrofit = apiClient.getClient();
        apiRest service = retrofit.create(apiRest.class);
        Call<Integer> call = service.imageAddLike(id);
        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if(response.isSuccessful()){
                    like=response.body();
                    text_view_like_activity_video.setText(format(like));
                    setReaction("like");
                }
                linear_layout_reactions_loading.setVisibility(View.GONE);

            }
            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                linear_layout_reactions_loading.setVisibility(View.GONE);

            }
        });
    }
    public void addLove(Integer id){
        removeReaction(prefManager.getString("reaction_"+id));
        linear_layout_reactions_loading.setVisibility(View.VISIBLE);
        reaction_count++;

        Retrofit retrofit = apiClient.getClient();
        apiRest service = retrofit.create(apiRest.class);
        Call<Integer> call = service.imageAddLove(id);
        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if(response.isSuccessful()){
                    love=response.body();
                    text_view_love_activity_video.setText(format(love));
                    setReaction("love");
                }
                linear_layout_reactions_loading.setVisibility(View.GONE);

            }
            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                linear_layout_reactions_loading.setVisibility(View.GONE);

            }
        });
    }
    public void addSad(Integer id){
        removeReaction(prefManager.getString("reaction_"+id));
        linear_layout_reactions_loading.setVisibility(View.VISIBLE);
        reaction_count++;

        Retrofit retrofit = apiClient.getClient();
        apiRest service = retrofit.create(apiRest.class);
        Call<Integer> call = service.imageAddSad(id);
        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if(response.isSuccessful()){
                    sad=response.body();
                    text_view_sad_activity_video.setText(format(sad));
                    setReaction("sad");
                }
                linear_layout_reactions_loading.setVisibility(View.GONE);

            }
            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                linear_layout_reactions_loading.setVisibility(View.GONE);

            }
        });
    }
    public void addAngry(Integer id){
        removeReaction(prefManager.getString("reaction_"+id));
        linear_layout_reactions_loading.setVisibility(View.VISIBLE);
        reaction_count++;
        Retrofit retrofit = apiClient.getClient();
        apiRest service = retrofit.create(apiRest.class);
        Call<Integer> call = service.imageAddAngry(id);
        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if(response.isSuccessful()){
                    angry=response.body();
                    text_view_angry_activity_video.setText(format(angry));
                    setReaction("angry");
                }
                linear_layout_reactions_loading.setVisibility(View.GONE);

            }
            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                linear_layout_reactions_loading.setVisibility(View.GONE);

            }
        });
    }
    public void addHaha(Integer id){
        linear_layout_reactions_loading.setVisibility(View.VISIBLE);

        removeReaction(prefManager.getString("reaction_"+id));
        reaction_count++;

        Retrofit retrofit = apiClient.getClient();
        apiRest service = retrofit.create(apiRest.class);
        Call<Integer> call = service.imageAddHaha(id);
        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if(response.isSuccessful()){
                    haha=response.body();
                    text_view_haha_activity_video.setText(format(haha));
                    setReaction("haha");
                }
                linear_layout_reactions_loading.setVisibility(View.GONE);

            }
            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                linear_layout_reactions_loading.setVisibility(View.GONE);

            }
        });
    }
    public void addWoow(Integer id){
        linear_layout_reactions_loading.setVisibility(View.VISIBLE);

        removeReaction(prefManager.getString("reaction_"+id));
        reaction_count++;

        Retrofit retrofit = apiClient.getClient();
        apiRest service = retrofit.create(apiRest.class);
        Call<Integer> call = service.imageAddWoow(id);
        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if(response.isSuccessful()){
                    woow=response.body();
                    text_view_woow_activity_video.setText(format(woow));
                    setReaction("woow");
                }
                linear_layout_reactions_loading.setVisibility(View.GONE);
            }
            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                linear_layout_reactions_loading.setVisibility(View.GONE);
            }
        });
    }
    public void deleteWoow(Integer id){
        reaction_count--;

        Retrofit retrofit = apiClient.getClient();
        apiRest service = retrofit.create(apiRest.class);
        Call<Integer> call = service.imageDeleteWoow(id);
        linear_layout_reactions_loading.setVisibility(View.VISIBLE);

        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if(response.isSuccessful()){
                    woow=response.body();
                    text_view_woow_activity_video.setText(format(woow));
                }
                linear_layout_reactions_loading.setVisibility(View.GONE);
            }
            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                linear_layout_reactions_loading.setVisibility(View.GONE);
            }
        });
    }
    public void deleteLike(Integer id){
        reaction_count--;
        Retrofit retrofit = apiClient.getClient();
        apiRest service = retrofit.create(apiRest.class);
        linear_layout_reactions_loading.setVisibility(View.VISIBLE);

        Call<Integer> call = service.imageDeleteLike(id);
        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if(response.isSuccessful()){
                    like=response.body();
                    text_view_like_activity_video.setText(format(like));
                }
                linear_layout_reactions_loading.setVisibility(View.GONE);

            }
            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                linear_layout_reactions_loading.setVisibility(View.GONE);

            }
        });
    }
    public void deleteAngry(Integer id){
        reaction_count--;
        Retrofit retrofit = apiClient.getClient();
        apiRest service = retrofit.create(apiRest.class);
        linear_layout_reactions_loading.setVisibility(View.VISIBLE);

        Call<Integer> call = service.imageDeleteAngry(id);
        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if(response.isSuccessful()){
                    angry=response.body();
                    text_view_angry_activity_video.setText(format(angry));
                }
                linear_layout_reactions_loading.setVisibility(View.GONE);

            }
            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                linear_layout_reactions_loading.setVisibility(View.GONE);

            }
        });
    }
    public void deleteHaha(Integer id){
        reaction_count--;
        Retrofit retrofit = apiClient.getClient();
        apiRest service = retrofit.create(apiRest.class);
        linear_layout_reactions_loading.setVisibility(View.VISIBLE);

        Call<Integer> call = service.imageDeleteHaha(id);
        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if(response.isSuccessful()){
                    haha=response.body();
                    text_view_haha_activity_video.setText(format(haha));
                }
                linear_layout_reactions_loading.setVisibility(View.GONE);

            }
            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                linear_layout_reactions_loading.setVisibility(View.GONE);

            }
        });
    }
    public void deleteSad(Integer id){
        reaction_count--;

        linear_layout_reactions_loading.setVisibility(View.VISIBLE);

        Retrofit retrofit = apiClient.getClient();
        apiRest service = retrofit.create(apiRest.class);
        Call<Integer> call = service.imageDeleteSad(id);
        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if(response.isSuccessful()){
                    sad=response.body();
                    text_view_sad_activity_video.setText(format(sad));
                }
                linear_layout_reactions_loading.setVisibility(View.GONE);

            }
            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                linear_layout_reactions_loading.setVisibility(View.GONE);

            }
        });
    }
    public void deleteLove(Integer id){
        reaction_count--;

        linear_layout_reactions_loading.setVisibility(View.VISIBLE);

        Retrofit retrofit = apiClient.getClient();
        apiRest service = retrofit.create(apiRest.class);
        Call<Integer> call = service.imageDeleteLove(id);
        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if(response.isSuccessful()){
                    love=response.body();
                    text_view_love_activity_video.setText(format(love));
                }
                linear_layout_reactions_loading.setVisibility(View.GONE);

            }
            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                linear_layout_reactions_loading.setVisibility(View.GONE);

            }
        });
    }
    public void setReaction(String reaction){
        text_view_like_activity_video.setBackground(getResources().getDrawable(R.drawable.bg_card_count));
        text_view_love_activity_video.setBackground(getResources().getDrawable(R.drawable.bg_card_count));
        text_view_angry_activity_video.setBackground(getResources().getDrawable(R.drawable.bg_card_count));
        text_view_sad_activity_video.setBackground(getResources().getDrawable(R.drawable.bg_card_count));
        text_view_haha_activity_video.setBackground(getResources().getDrawable(R.drawable.bg_card_count));
        text_view_woow_activity_video.setBackground(getResources().getDrawable(R.drawable.bg_card_count));

        text_view_like_activity_video.setTextColor(getResources().getColor(R.color.primary_text));
        text_view_woow_activity_video.setTextColor(getResources().getColor(R.color.primary_text));
        text_view_love_activity_video.setTextColor(getResources().getColor(R.color.primary_text));
        text_view_sad_activity_video.setTextColor(getResources().getColor(R.color.primary_text));
        text_view_angry_activity_video.setTextColor(getResources().getColor(R.color.primary_text));
        text_view_haha_activity_video.setTextColor(getResources().getColor(R.color.primary_text));

        like_button_like_activity_video.setLiked(false);
        like_button_love_activity_video.setLiked(false);
        like_button_angry_activity_video.setLiked(false);
        like_button_haha_activity_video.setLiked(false);
        like_button_sad_activity_video.setLiked(false);
        like_button_woow_activity_video.setLiked(false);


        if (reaction.equals("like")){
            prefManager.setString("reaction_"+id,"like");
            text_view_like_activity_video.setBackground(getResources().getDrawable(R.drawable.bg_card_count_select));
            text_view_like_activity_video.setTextColor(getResources().getColor(R.color.white));
            like_button_like_activity_video.setLiked(true);

        }else if (reaction.equals("woow")){
            prefManager.setString("reaction_"+id,"woow");
            text_view_woow_activity_video.setBackground(getResources().getDrawable(R.drawable.bg_card_count_select));
            text_view_woow_activity_video.setTextColor(getResources().getColor(R.color.white));
            like_button_woow_activity_video.setLiked(true);

        }else if (reaction.equals("love")){
            prefManager.setString("reaction_"+id,"love");
            text_view_love_activity_video.setBackground(getResources().getDrawable(R.drawable.bg_card_count_select));
            text_view_love_activity_video.setTextColor(getResources().getColor(R.color.white));
            like_button_love_activity_video.setLiked(true);

        }else if (reaction.equals("angry")){
            prefManager.setString("reaction_"+id,"angry");
            text_view_angry_activity_video.setBackground(getResources().getDrawable(R.drawable.bg_card_count_select));
            text_view_angry_activity_video.setTextColor(getResources().getColor(R.color.white));
            like_button_angry_activity_video.setLiked(true);

        }else if (reaction.equals("sad")){
            prefManager.setString("reaction_"+id,"sad");
            text_view_sad_activity_video.setBackground(getResources().getDrawable(R.drawable.bg_card_count_select));
            text_view_sad_activity_video.setTextColor(getResources().getColor(R.color.white));
            like_button_sad_activity_video.setLiked(true);

        }else if (reaction.equals("haha")){
            prefManager.setString("reaction_"+id,"haha");
            text_view_haha_activity_video.setBackground(getResources().getDrawable(R.drawable.bg_card_count_select));
            text_view_haha_activity_video.setTextColor(getResources().getColor(R.color.white));
            like_button_haha_activity_video.setLiked(true);
        }
        this.text_view_fragment_player_like.setText(format(reaction_count));

    }

    public void removeReaction(String  reaction){
        if (reaction.equals("like")){
            deleteLike(id);
        }else if (reaction.equals("woow")){
            deleteWoow(id);
        }else if (reaction.equals("love")){
            deleteLove(id);
        }else if (reaction.equals("angry")){
            deleteAngry(id);
        }else if (reaction.equals("sad")){
            deleteSad(id);
        }else if (reaction.equals("haha")){
            deleteHaha(id);
        }
    }

    public void addView(){
        final PrefManager prefManager = new PrefManager(getActivity());
        Integer id_user = 0;
        String key_user= "";
        if (prefManager.getString("LOGGED").toString().equals("TRUE")) {
            id_user=  Integer.parseInt(prefManager.getString("ID_USER"));
            key_user=  prefManager.getString("TOKEN_USER");
        }
        if (!prefManager.getString(id+"_view").equals("true")) {
            prefManager.setString(id+"_view", "true");
            Retrofit retrofit = apiClient.getClient();
            apiRest service = retrofit.create(apiRest.class);
            String ishash="";
            try {
                ishash   = sha1((id+55463938)+"");
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            };
            Call<Integer> call = service.addView(ishash,id_user,key_user);
            call.enqueue(new Callback<Integer>() {
                @Override
                public void onResponse(Call<Integer> call, retrofit2.Response<Integer> response) {
                }
                @Override
                public void onFailure(Call<Integer> call, Throwable t) {
                }
            });
        }
    }
    public  static String sha1(String input) throws NoSuchAlgorithmException {
        String status_final = "";
        byte[] data = new byte[0];

        try {
            data =input.toString().getBytes("UTF-8");

            status_final = Base64.encodeToString(data, Base64.DEFAULT);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return status_final;
    }

    private void Report() {


        Dialog rateDialog = new Dialog(getActivity(), R.style.Theme_Dialog);

        rateDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        rateDialog.setCancelable(true);
        rateDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Window window = rateDialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        getActivity().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
        wlp.gravity = Gravity.BOTTOM;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        window.setAttributes(wlp);
        final   PrefManager prf= new PrefManager(getActivity().getApplicationContext());
        rateDialog.setCancelable(false);
        rateDialog.setContentView(R.layout.dialog_report);
        final Button buttun_send_report=(Button) rateDialog.findViewById(R.id.buttun_send_report);
        final Button button_cancel=(Button) rateDialog.findViewById(R.id.button_cancel);


        button_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rateDialog.dismiss();
            }
        });
        final EditText edit_text_feed_back=(EditText) rateDialog.findViewById(R.id.edit_text_feed_back);
        buttun_send_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addReport(id,edit_text_feed_back.getText().toString(),rateDialog);
            }
        });

        rateDialog.setOnKeyListener(new Dialog.OnKeyListener() {

            @Override
            public boolean onKey(DialogInterface arg0, int keyCode,
                                 KeyEvent event) {
                // TODO Auto-generated method stub
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    rateDialog.dismiss();
                }
                return true;

            }
        });
        rateDialog.show();
    }
    private void addReport(int id, String message,Dialog rateDialog) {
        ProgressDialog register_progress= ProgressDialog.show(getActivity(),null,getString(R.string.progress_login));
        Retrofit retrofit = apiClient.getClient();
        apiRest service = retrofit.create(apiRest.class);
        Call<ApiResponse> call = service.addReport(id,message);
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if(response.isSuccessful()){
                    Toasty.success(getActivity().getApplicationContext(), getResources().getString(R.string.message_sended), Toast.LENGTH_SHORT).show();
                }else{
                    Toasty.error(getActivity().getApplicationContext(), getString(R.string.no_connexion), Toast.LENGTH_SHORT).show();
                }
                register_progress.dismiss();
                rateDialog.dismiss();
            }
            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                register_progress.dismiss();
                rateDialog.dismiss();
                Toasty.error(getActivity().getApplicationContext(), getString(R.string.no_connexion), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void selectOperation(Integer code){
        switch (code){
            case 90006:
                DownloadFileBySocial(SHARE_ID);
                break;
            case 90007:
                DownloadFileBySocial(DOWNLOAD_ID);
                break;

        }
    }

    private void DownloadFileBySocial(String SOCIAL_ID) {
        if (!downloading) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
                new DownloadFileFromURL().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,urlToDownload, title,extension,0,SOCIAL_ID);
            else
                new DownloadFileFromURL().execute(AsyncTask.THREAD_POOL_EXECUTOR,urlToDownload, title,extension,0,SOCIAL_ID);
        }
    }


    public void Operation(Integer code){
        PrefManager prefManager= new PrefManager(getActivity());
        if(checkSUBSCRIBED()) {
            selectOperation(code);
        }else{
            if( prefManager.getString("ADMIN_INTERSTITIAL_TYPE").equals("ADMOB")) {
                requestAdmobInterstitial();
                if(prefManager.getInt("ADMIN_INTERSTITIAL_CLICKS")<=prefManager.getInt("ADMOB_INTERSTITIAL_COUNT_CLICKS")){
                    if (admobInterstitialAd.isLoaded()) {
                        prefManager.setInt("ADMOB_INTERSTITIAL_COUNT_CLICKS",0);
                        admobInterstitialAd.show();
                        admobInterstitialAd.setAdListener(new AdListener() {
                            @Override
                            public void onAdClosed() {
                                requestAdmobInterstitial();
                                selectOperation(code);
                            }
                        });
                    }else{
                        selectOperation(code);
                    }
                }else{
                    selectOperation(code);
                    prefManager.setInt("ADMOB_INTERSTITIAL_COUNT_CLICKS",prefManager.getInt("ADMOB_INTERSTITIAL_COUNT_CLICKS")+1);

                }
            }else if(prefManager.getString("ADMIN_INTERSTITIAL_TYPE").equals("FACEBOOK")){
                requestFacebookInterstitial();
                if(prefManager.getInt("ADMIN_INTERSTITIAL_CLICKS")<=prefManager.getInt("ADMOB_INTERSTITIAL_COUNT_CLICKS")) {
                    if (facebookInterstitialAd.isAdLoaded()) {
                        prefManager.setInt("ADMOB_INTERSTITIAL_COUNT_CLICKS",0);
                        facebookInterstitialAd.show();
                        code_selected = code;
                    }else{
                        selectOperation(code);
                    }
                }else{
                    selectOperation(code);
                    prefManager.setInt("ADMOB_INTERSTITIAL_COUNT_CLICKS",prefManager.getInt("ADMOB_INTERSTITIAL_COUNT_CLICKS")+1);

                }
            }else if(prefManager.getString("ADMIN_INTERSTITIAL_TYPE").equals("BOTH")) {
                requestAdmobInterstitial();
                requestFacebookInterstitial();

                if(prefManager.getInt("ADMIN_INTERSTITIAL_CLICKS")<=prefManager.getInt("ADMOB_INTERSTITIAL_COUNT_CLICKS")) {
                    if (prefManager.getString("AD_INTERSTITIAL_SHOW_TYPE").equals("ADMOB")){
                        if (admobInterstitialAd.isLoaded()) {
                            prefManager.setInt("ADMOB_INTERSTITIAL_COUNT_CLICKS",0);
                            prefManager.setString("AD_INTERSTITIAL_SHOW_TYPE","FACEBOOK");
                            admobInterstitialAd.show();
                            admobInterstitialAd.setAdListener(new AdListener(){
                                @Override
                                public void onAdClosed() {
                                    super.onAdClosed();
                                    selectOperation(code);
                                    requestFacebookInterstitial();
                                }
                            });
                        }else{
                            selectOperation(code);
                            requestFacebookInterstitial();
                        }
                    }else{
                        if (facebookInterstitialAd.isAdLoaded()) {
                            prefManager.setInt("ADMOB_INTERSTITIAL_COUNT_CLICKS",0);
                            prefManager.setString("AD_INTERSTITIAL_SHOW_TYPE","ADMOB");
                            facebookInterstitialAd.show();
                            code_selected =  code;
                        }else{
                            selectOperation(code);
                        }
                    }
                }else{
                    selectOperation(code);
                    prefManager.setInt("ADMOB_INTERSTITIAL_COUNT_CLICKS",prefManager.getInt("ADMOB_INTERSTITIAL_COUNT_CLICKS")+1);
                }
            }else{
                selectOperation(code);
            }
        }
    }


    private void requestAdmobInterstitial2() {

        admobInterstitialAd = new InterstitialAd(getActivity());
        admobInterstitialAd.setAdUnitId(prefManager.getString("ADMIN_INTERSTITIAL_ADMOB_ID"));
        AdRequest adRequest1 = new AdRequest.Builder()
                .build();
        admobInterstitialAd.loadAd(adRequest1);
        admobInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                showInterstitial();
            }

            @Override
            public void onAdClosed() {
                super.onAdClosed();
                if (relative_layout_wallpaper_comments.getVisibility() == View.VISIBLE) {
                    relative_layout_wallpaper_comments.setVisibility(View.GONE);
                } else {
                    getActivity().finish();
                }
            }
        });


    }

    private void showInterstitial() {
        if (admobInterstitialAd.isLoaded()) {
            admobInterstitialAd.show();
            // Constant.added_time =EncryptionSecurity.currenttimaddfiveminute(1);
        }
    }

}
