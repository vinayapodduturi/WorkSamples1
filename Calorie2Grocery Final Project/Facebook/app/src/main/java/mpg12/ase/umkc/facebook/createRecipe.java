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
 * Created by Sravani on 4/12/2015.
 */
public class createRecipe extends FragmentActivity implements View.OnClickListener{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_recipe);

        Button callServiceButton = (Button) (findViewById(R.id.btncrtrep));

        final WebView webServiceView = (WebView) (findViewById(R.id.webViewCreateRec));
        webServiceView.getSettings().setJavaScriptEnabled(true);
        callServiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webServiceView.loadUrl("file:///android_asset/createrecipe.html");
            }
        });


        Button homeButton = (Button) (findViewById(R.id.btnhome));
        homeButton.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
         if(v.getId() == R.id.btnhome){

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


