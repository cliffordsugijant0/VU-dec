package node8.valetuncle;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import node8.valetuncle.R;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


public class DetailActivity extends AppCompatActivity {


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
    }
}
