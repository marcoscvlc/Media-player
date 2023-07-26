package descomplica.desenvolvimentomobile.mediaplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;

public class MainActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;
    private SeekBar seekVolume;
    private AudioManager audioManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mediaPlayer = MediaPlayer.create(getApplicationContext(),R.raw.teste);
        inicializarSeekBar();
    }
    private void inicializarSeekBar(){
        seekVolume = findViewById(R.id.seekVolume);

        //CONFIGURAR O AUDIO MANANGER;
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        //RECUPERANDO VALORES DE VOLUME MÁXIMO E VOLUME ATUAL;
        int volMax = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int volDefault = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        //CONFIGURAR VALORES MÁXIMOS PARA A SEEKBAR;
        seekVolume.setMax(volMax);
        //CONFIGURAR VALORES ATUAIS PARA A SEEKBAR;
        seekVolume.setProgress(volDefault);

        seekVolume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, i, 0);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


    }
    public void executarSom(View view){
        if (mediaPlayer != null){
            mediaPlayer.start();
        }
    }

    public void pausarSom(View view){
        if (mediaPlayer.isPlaying()){
            mediaPlayer.pause();
        }
    }

    public void pararSom(View view){
        if (mediaPlayer.isPlaying()){
            mediaPlayer.stop();
            mediaPlayer = MediaPlayer.create(getApplicationContext(),R.raw.teste);
        }


    }

    //PAUSAR MUSICA AO TROCAR DE APLICATIVO;
    @Override
    protected void onStop() {
        super.onStop();

        if (mediaPlayer.isPlaying()){
            mediaPlayer.pause();
        }
    }

    //ENCERRAR ATIVIDADES E LIMPAR MEMÓRIA AO FECHAR O APLICATIVO

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null && mediaPlayer.isPlaying()){
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}
