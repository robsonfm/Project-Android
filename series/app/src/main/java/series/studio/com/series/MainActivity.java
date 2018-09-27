package series.studio.com.series;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.SeekBar;

public class MainActivity extends AppCompatActivity {

    private SeekBar seekBar;
    private ImageView imagem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        seekBar = (SeekBar) findViewById(R.id.seekBarID);
        imagem = (ImageView) findViewById(R.id.imagemID);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int ValorProgresso = progress;

                if(ValorProgresso == 1){
                    imagem.setImageResource(R.drawable.pouco);
                }else if (ValorProgresso == 2){
                    imagem.setImageResource(R.drawable.medio);
                }else if(ValorProgresso == 3){
                    imagem.setImageResource(R.drawable.muito);
                }else if (ValorProgresso == 4){
                    imagem.setImageResource(R.drawable.susto);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}
