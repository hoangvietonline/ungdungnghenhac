package hoangviet.ndhv.ungdungnghenhac;

import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
TextView txtbaiHat,txtruntime,txtsumtime;
SeekBar seekBar;
ImageButton btnprev,btnplay,btnstop,btnnext;
ArrayList<song>songArrayList;
MediaPlayer mediaPlayer;
int position = 0 ;
Animation animation ;
ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        anhXa();
        addSong();
        animation = AnimationUtils.loadAnimation(this,R.anim.cd_rotate);

        khoiTaomediaPlayer();
        // button next
        btnnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                position++;
                if (position > songArrayList.size() - 1){
                    position = 0 ;
                }
                if (mediaPlayer.isPlaying()){
                    mediaPlayer.stop();
                }

                khoiTaomediaPlayer();
                mediaPlayer.start();
                btnplay.setImageResource(R.drawable.pause1);
                setTimetotal();
                updateTimeSong();
            }
        });
        btnprev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                position--;
                if (position < 0 ){
                    position = songArrayList.size() - 1 ;
                }
                if (mediaPlayer.isPlaying()){
                    mediaPlayer.stop();
                }
                khoiTaomediaPlayer();
                mediaPlayer.start();
                btnplay.setImageResource(R.drawable.pause1);
                setTimetotal();
                updateTimeSong();
            }
        });
        //button stop
        btnstop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.stop();
                mediaPlayer.release();
                btnplay.setImageResource(R.drawable.play);
                khoiTaomediaPlayer();
            }
        });

        btnplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer.isPlaying()){
                    mediaPlayer.pause();
                    btnplay.setImageResource(R.drawable.play);
                }
                else {
                    mediaPlayer.start();
                    btnplay.setImageResource(R.drawable.pause1);

                }
                setTimetotal();
                updateTimeSong();
                imageView.startAnimation(animation);
            }
        });
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.seekTo(seekBar.getProgress());
            }
        });

    }
    private void updateTimeSong(){

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                SimpleDateFormat dinhdanggio = new SimpleDateFormat("mm:ss");
                txtruntime.setText(dinhdanggio.format(mediaPlayer.getCurrentPosition()));
                seekBar.setProgress(mediaPlayer.getCurrentPosition());
                // kiểm tra thời gian của bài hát khi hết bài --->next
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        position++;
                        if (position > songArrayList.size() - 1){
                            position = 0 ;
                        }
                        if (mediaPlayer.isPlaying()){
                            mediaPlayer.stop();
                        }

                        khoiTaomediaPlayer();
                        mediaPlayer.start();
                        btnplay.setImageResource(R.drawable.pause1);
                        setTimetotal();
                        updateTimeSong();

                    }
                });

                handler.postDelayed(this,500);

            }
        },100);
    }

    private  void setTimetotal (){
        SimpleDateFormat dinhDangPhut = new SimpleDateFormat("mm:ss");
        txtsumtime.setText(dinhDangPhut.format(mediaPlayer.getDuration()));
        seekBar.setMax(mediaPlayer.getDuration());
    }
    private void addSong(){
        songArrayList.add(new song("Bạc phận",R.raw.bac_phan));
        songArrayList.add(new song("Đừng nói em điên",R.raw.dung_noi_toi_dien));
        songArrayList.add(new song("Em ngày xưa khác rồi",R.raw.em_ngay_xua_khac_roi));
        songArrayList.add(new song("Hồng nhan",R.raw.hong_nhan_jack));
        songArrayList.add(new song("Mây và núi",R.raw.may_va_nui_the_bells));
        songArrayList.add(new song("Rồi người thương cũng hóa người dưng",R.raw.roi_nguoi_thuong_cung_hoa_nguoi_dung));
    }

    private void anhXa() {
        txtbaiHat = (TextView)findViewById(R.id.textTenBaiHat);
        txtruntime = (TextView)findViewById(R.id.runtime);
        txtsumtime = (TextView)findViewById(R.id.sumtime);
        seekBar = (SeekBar)findViewById(R.id.seekBar);
        btnprev = (ImageButton) findViewById(R.id.imagebuttonprev);
        btnplay = (ImageButton) findViewById(R.id.imageButtonplay);
        btnstop = (ImageButton) findViewById(R.id.imageButtonstop);
        btnnext = (ImageButton) findViewById(R.id.imageButtonnext);
        songArrayList = new ArrayList<>();
        imageView = (ImageView)findViewById(R.id.imageView);
    }
    private void khoiTaomediaPlayer(){
        mediaPlayer = MediaPlayer.create(MainActivity.this,songArrayList.get(position).getFile());
        txtbaiHat.setText(songArrayList.get(position).getTittle());
    }
}
