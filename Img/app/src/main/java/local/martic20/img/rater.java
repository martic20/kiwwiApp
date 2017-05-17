package local.martic20.img;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

public class rater extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rater);
        Intent intent = getIntent();

        int imgDir = intent.getIntExtra("fg", 0);
        ImageView img = (ImageView) findViewById(R.id.imageView);

    }
}
