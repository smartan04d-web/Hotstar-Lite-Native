package com.hotstarlite.nativeplayer;

import android.app.Activity;
import android.os.Bundle;
import android.graphics.Color;
import android.content.pm.ActivityInfo;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.media3.common.MediaItem;
import androidx.media3.common.Player;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.media3.ui.PlayerView;

/** Native player for media URLs the user owns or is authorized to access. */
public final class MainActivity extends Activity {
    private ExoPlayer player;
    private PlayerView playerView;
    private EditText urlInput;
    private TextView status;

    @Override public void onCreate(Bundle state) {
        super.onCreate(state);
        getWindow().setStatusBarColor(Color.rgb(13, 18, 30));
        getWindow().setNavigationBarColor(Color.BLACK);
        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setPadding(dp(16), dp(12), dp(16), dp(12));
        root.setBackgroundColor(Color.rgb(13, 18, 30));
        TextView title = new TextView(this);
        title.setText("Hotstar Lite Native • Demo Player");
        title.setTextColor(Color.WHITE);
        title.setTextSize(20);
        title.setPadding(0, 0, 0, dp(10));
        root.addView(title);
        TextView note = new TextView(this);
        note.setText("Native playback for your own or authorized HTTPS MP4, HLS (.m3u8), or DASH (.mpd) streams. This app does not provide JioHotstar catalog, account login, or protected streams.");
        note.setTextColor(Color.LTGRAY);
        root.addView(note);
        urlInput = new EditText(this);
        urlInput.setSingleLine(true);
        urlInput.setTextColor(Color.WHITE);
        urlInput.setHintTextColor(Color.LTGRAY);
        urlInput.setHint("https://example.org/video.mp4");
        urlInput.setInputType(android.text.InputType.TYPE_CLASS_TEXT | android.text.InputType.TYPE_TEXT_VARIATION_URI);
        root.addView(urlInput, new LinearLayout.LayoutParams(-1, dp(58)));
        LinearLayout actions = new LinearLayout(this);
        actions.setOrientation(LinearLayout.HORIZONTAL);
        Button play = new Button(this); play.setText("Play URL");
        play.setOnClickListener(v -> playUrl());
        actions.addView(play, new LinearLayout.LayoutParams(0, dp(52), 1));
        Button stop = new Button(this); stop.setText("Stop");
        stop.setOnClickListener(v -> { if (player != null) { player.stop(); player.clearMediaItems(); } status.setText("Stopped"); });
        actions.addView(stop, new LinearLayout.LayoutParams(0, dp(52), 1));
        root.addView(actions);
        playerView = new PlayerView(this);
        playerView.setUseController(true);
        playerView.setKeepScreenOn(true);
        root.addView(playerView, new LinearLayout.LayoutParams(-1, 0, 1));
        status = new TextView(this);
        status.setTextColor(Color.LTGRAY);
        status.setText("Ready • No media loaded");
        status.setGravity(Gravity.CENTER_VERTICAL);
        root.addView(status, new LinearLayout.LayoutParams(-1, dp(48)));
        setContentView(root);
    }
    private int dp(int value) { return Math.round(value * getResources().getDisplayMetrics().density); }
    private void ensurePlayer() {
        if (player != null) return;
        player = new ExoPlayer.Builder(this).build();
        playerView.setPlayer(player);
        player.addListener(new Player.Listener() {
            @Override public void onPlaybackStateChanged(int state) {
                if (state == Player.STATE_BUFFERING) status.setText("Buffering…");
                else if (state == Player.STATE_READY) status.setText("Playing / ready");
                else if (state == Player.STATE_ENDED) status.setText("Playback ended");
            }
            @Override public void onPlayerError(androidx.media3.common.PlaybackException error) {
                status.setText("Playback error: " + error.getErrorCodeName());
            }
        });
    }
    private void playUrl() {
        String url = urlInput.getText().toString().trim();
        if (!url.startsWith("https://")) {
            Toast.makeText(this, "Enter an authorized HTTPS media URL", Toast.LENGTH_LONG).show(); return;
        }
        try {
            ensurePlayer();
            player.setMediaItem(MediaItem.fromUri(url));
            player.prepare();
            player.play();
            status.setText("Loading…");
        } catch (Exception e) { status.setText("Unable to load media: " + e.getMessage()); }
    }
    @Override protected void onStop() { super.onStop(); if (player != null) player.pause(); }
    @Override protected void onDestroy() {
        if (player != null) { playerView.setPlayer(null); player.release(); player = null; }
        super.onDestroy();
    }
}
