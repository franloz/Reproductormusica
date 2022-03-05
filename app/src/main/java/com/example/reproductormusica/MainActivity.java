package com.example.reproductormusica;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.reproductormusica.modelos.Cancion;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener{
    private ImageButton imageButtonPlay;
    private ImageButton imageButtonRewind;
    private ImageButton imageButtonForward;
    private TextView textView;
    private TextView textView2;
    private TextView textView3;
    private SeekBar seekBar;
    private double startTime=0;//donde inicia la cancion
    private double finalTime=0;//donde termina

    public static int oneTimeOnly=0;

    private Handler myhandler=new Handler();

    //int canciones[]={R.raw.forelisa,R.raw.tristan,R.raw.scottbuckley_titan,R.raw.vocal};


    ArrayList<Cancion> canciones = new ArrayList();

    private Context context=this;

    private Thread hilo;
   // String nomcanciones[]={"forelisa","tristan","scott","vocal"};
    int index=0;

    private int cont=0;

    private MediaPlayer mediaPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageButtonPlay=(ImageButton)findViewById(R.id.imageButtonPlay);
        imageButtonRewind=(ImageButton)findViewById(R.id.imageButtonRewind);
        imageButtonForward=(ImageButton)findViewById(R.id.imageButtonForward);
        textView=(TextView)findViewById(R.id.textView);
        textView2=(TextView)findViewById(R.id.textView2);
        textView3=(TextView)findViewById(R.id.textView3);
        seekBar=(SeekBar) findViewById(R.id.seekBar);

        //meto en array las canciones
        canciones.add(new Cancion(R.raw.forelisa,"forelisa"));
        canciones.add(new Cancion(R.raw.scottbuckley_titan,"scott"));
        canciones.add(new Cancion(R.raw.tristan,"tristan"));
        canciones.add(new Cancion(R.raw.vocal,"vocal"));

        mediaPlayer = MediaPlayer.create(this,canciones.get(0).getRefecancion());//creo el mediaplayer y cargo en él la primera cancion
        textView.setText(canciones.get(0).getNomcancion());
        imageButtonPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finalTime=mediaPlayer.getDuration();
                startTime=mediaPlayer.getCurrentPosition();
                calcularduracioncancion(finalTime,startTime);
                //seekBar.setProgress((int) startTime);
                myhandler.postDelayed(UpdateSongTime,100);
                //progreso();
                if(cont%2==0){
                    mediaPlayer.start();

                    if(oneTimeOnly==0){
                        seekBar.setMax((int) finalTime);//asigna el maximo a seekbar
                        oneTimeOnly=1;
                    }
                    imageButtonPlay.setImageResource(R.drawable.baseline_pause_24);
                    cont++;

                }
                else{
                    mediaPlayer.pause();//en este caso es pause
                    //mediaPlayer.release();esto se debe poner en el metodo onstop de la actividad
                    //mediaPlayer = null;esto se debe poner en el metodo onstop de la actividad si la musica no continua en segundo plano
                    imageButtonPlay.setImageResource(R.drawable.baseline_play_arrow_24);
                    cont++;
                }

            }
        });

        imageButtonRewind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                index--;
                if(index<0){
                    index=3;
                    mediaPlayer.stop();
                    mediaPlayer=null;
                    cont=2;
                    imageButtonPlay.setImageResource(R.drawable.baseline_play_arrow_24);
                    mediaPlayer = MediaPlayer.create(context,canciones.get(index).getRefecancion());
                    textView.setText(canciones.get(index).getNomcancion());
                    finalTime=mediaPlayer.getDuration();
                    startTime=mediaPlayer.getCurrentPosition();
                    calcularduracioncancion(finalTime,startTime);
                }else{
                    mediaPlayer.stop();
                    mediaPlayer=null;
                    cont=2;
                    imageButtonPlay.setImageResource(R.drawable.baseline_play_arrow_24);
                    mediaPlayer = MediaPlayer.create(context,canciones.get(index).getRefecancion());
                    textView.setText(canciones.get(index).getNomcancion());
                    finalTime=mediaPlayer.getDuration();
                    startTime=mediaPlayer.getCurrentPosition();
                    calcularduracioncancion(finalTime,startTime);
                }




            }
        });

        imageButtonForward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                index++;
                if(index>3){
                    index=0;
                    mediaPlayer.stop();
                    mediaPlayer=null;
                    cont=2;
                    imageButtonPlay.setImageResource(R.drawable.baseline_play_arrow_24);
                    mediaPlayer = MediaPlayer.create(context,canciones.get(index).getRefecancion());
                    textView.setText(canciones.get(index).getNomcancion());
                    finalTime=mediaPlayer.getDuration();
                    startTime=mediaPlayer.getCurrentPosition();
                    calcularduracioncancion(finalTime,startTime);
                }else{
                    mediaPlayer.stop();
                    mediaPlayer=null;
                    cont=2;
                    imageButtonPlay.setImageResource(R.drawable.baseline_play_arrow_24);
                    mediaPlayer = MediaPlayer.create(context,canciones.get(index).getRefecancion());
                    textView.setText(canciones.get(index).getNomcancion());
                    finalTime=mediaPlayer.getDuration();
                    startTime=mediaPlayer.getCurrentPosition();
                    calcularduracioncancion(finalTime,startTime);
                }



            }
        });
    }

   /* public void progreso() {

        hilo = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(100);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            startTime= mediaPlayer.getCurrentPosition();
                            textView2.setText(String.format("%d:%d",
                                    TimeUnit.MILLISECONDS.toMinutes((long) startTime),
                                    TimeUnit.MILLISECONDS.toSeconds((long) startTime) -
                                            TimeUnit.MINUTES.toSeconds((TimeUnit.MILLISECONDS.toMinutes((long) startTime)))));
                            seekBar.setProgress((int) startTime);
                        }
                    });

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }) ;

        hilo.start();
    }*/

    //falta lo de q cuando termine cancion pase a otra, lo de aleatorio, repetir y mover seekbar


    private Runnable UpdateSongTime=new Runnable() {
        @Override
        public void run() {
            startTime= mediaPlayer.getCurrentPosition();
            textView2.setText(String.format("%d:%d",
                    TimeUnit.MILLISECONDS.toMinutes((long) startTime),
                    TimeUnit.MILLISECONDS.toSeconds((long) startTime) -
                            TimeUnit.MINUTES.toSeconds((TimeUnit.MILLISECONDS.toMinutes((long) startTime)))));
            seekBar.setProgress((int) startTime);
            myhandler.postDelayed(this,100);
        }

    };

    private void calcularduracioncancion(double finalTime,double startTime){
        textView3.setText(String.format("%d:%d",
                TimeUnit.MILLISECONDS.toMinutes((long) finalTime),
                TimeUnit.MILLISECONDS.toSeconds((long) finalTime) -
                        TimeUnit.MINUTES.toSeconds((TimeUnit.MILLISECONDS.toMinutes((long) finalTime)))));
    }


    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        mediaPlayer.pause();
        seekBar.setProgress(i);
        textView2.setText(i);


        mediaPlayer.seekTo(i);
        mediaPlayer.start();
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}