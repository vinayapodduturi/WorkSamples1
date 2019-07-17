package mpg12.ase.umkc.facebook;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class Home extends ActionBarActivity implements View.OnClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        Button btnmygrocery = (Button) findViewById(R.id.btnmygroceries);
        btnmygrocery.setOnClickListener(this);

        Button btnrep = (Button) findViewById(R.id.btnrecipe);
        btnrep.setOnClickListener(this);

        Button btnshw = (Button) findViewById(R.id.btnShowNotify);
        btnshw.setOnClickListener(this);
        Button btngStore = (Button) findViewById(R.id.btnStore);
        btngStore.setOnClickListener(this);
        Button btnshop = (Button) findViewById(R.id.btnShoplist);
        btnshop.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btnmygroceries:
                Intent j=new Intent(this,MyGroceries.class);
                startActivity(j);
                break;
            case R.id.btnrecipe:
                Intent k=new Intent(this,recipe.class);
                startActivity(k);
                break;
            case R.id.btnShowNotify:
                Intent m=new Intent(this,CreateNotificationActivity.class);
                startActivity(m);
                break;
            case R.id.btnStore:
                Intent l=new Intent(this,groceryStore.class);
                startActivity(l);
                break;
            case R.id.btnShoplist:
                Intent p=new Intent(this,ShoppingList.class);
                startActivity(p);
                break;
            default:
                break;
        }
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
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
