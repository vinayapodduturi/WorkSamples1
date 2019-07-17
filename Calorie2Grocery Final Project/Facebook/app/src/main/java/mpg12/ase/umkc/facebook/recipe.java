package mpg12.ase.umkc.facebook;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by Sravani on 4/12/2015.
 */
public class recipe  extends FragmentActivity implements View.OnClickListener {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe);

        Button btnsrch = (Button) findViewById(R.id.btnsrchrecipe);
        btnsrch.setOnClickListener(this);

        Button btncreate = (Button) findViewById(R.id.btncreaterecipe);
        btncreate.setOnClickListener(this);

        Button btnshare = (Button) findViewById(R.id.btnsharerecipe);
        btnshare.setOnClickListener(this);

        Button btnUpd = (Button) findViewById(R.id.btnupdrecipe);
        btnUpd.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btnsrchrecipe:
                Intent j=new Intent(this,SearchRecipe.class);
                startActivity(j);
                break;
            case R.id.btncreaterecipe:
                Intent p=new Intent(this,createRecipe.class);
                startActivity(p);
                break;
            case R.id.btnsharerecipe:
                Intent q=new Intent(this,shareRecipe.class);
                startActivity(q);
                break;
            case R.id.btnupdrecipe:
                Intent z=new Intent(this,updateRecipe.class);
                startActivity(z);
                break;
            default:
                break;
        }
    }

}