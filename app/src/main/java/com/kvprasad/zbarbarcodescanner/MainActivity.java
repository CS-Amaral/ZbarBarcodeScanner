package com.kvprasad.zbarbarcodescanner;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class MainActivity extends Activity {

    private Button scannerButton;
    private EditText editText;
    TTSManager ttsManager = null;
    private static String valor_alterado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ttsManager = new TTSManager();
        ttsManager.init(this);

        editText = (EditText) findViewById(R.id.input_text);
        scannerButton = (Button) findViewById(R.id.scannerButton);

        scannerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(v.getContext(), BarcodeScanner.class);
                startActivityForResult(intent, 12);
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode == 12){
            if (resultCode == Activity.RESULT_OK){
                String codigoDeBarra = data.getExtras().getString("Codigo_de_Barra");
                Log.i("BARRRRRAA", codigoDeBarra);
                ttsManager.initQueue(codigoDeBarra);
                try {
                    Thread.sleep(8000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Intent intent = new Intent(this, BarcodeScanner.class);
                startActivityForResult(intent, 12);



            }else {
                Log.i("Errrroooo","Voltou incorreto");
            }
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ttsManager.shutDown();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
