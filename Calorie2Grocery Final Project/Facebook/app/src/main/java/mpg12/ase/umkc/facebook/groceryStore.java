package mpg12.ase.umkc.facebook;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;

/**
 * Created by Sravani on 4/28/2015.
 */
public class groceryStore   extends FragmentActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grocery_store);

        Button callServiceButton = (Button) (findViewById(R.id.btnGetStore));

        final WebView webServiceView = (WebView) (findViewById(R.id.webViewStore));
        webServiceView.getSettings().setBuiltInZoomControls(true);
        webServiceView.getSettings().setJavaScriptEnabled(true);

       callServiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // webServiceView.loadUrl("https://www.google.com/maps?q=grocery+stores");
                webServiceView.loadUrl("https://www.google.com/maps?q=grocery+stores");
            }
        });

        Button homeButton = (Button) (findViewById(R.id.btnhome7));
        homeButton.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btnhome7){

            Intent j = new Intent(this, Home.class);
            startActivity(j);
        }
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

