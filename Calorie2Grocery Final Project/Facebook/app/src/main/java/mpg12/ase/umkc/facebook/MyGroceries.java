package mpg12.ase.umkc.facebook;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by Sravani on 4/12/2015.
 */
public class MyGroceries extends FragmentActivity implements View.OnClickListener {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_groceries);

        Button btnvwgrcy = (Button) findViewById(R.id.btnvwgrcylist);
        btnvwgrcy.setOnClickListener(this);

        Button btnaddgrcy = (Button) findViewById(R.id.btnaddgrocery);
        btnaddgrcy.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btnaddgrocery:
                Intent j=new Intent(this,addGrocery.class);
                startActivity(j);
                break;
           case R.id.btnvwgrcylist:
                Intent p=new Intent(this,viewGroceryList.class);
                startActivity(p);
                break;
            default:
                break;
        }
    }

}