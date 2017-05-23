package local.martic20.img;

import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Description_Activity extends AppCompatActivity {

    private TextView type;
    private TextView name;
    private TextView desc;
    private TextView price;
    private ImageView img;

    private FirebaseDatabase database ;
    private DatabaseReference myRef;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private Toolbar myToolbar;

    private int ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description_);
        ID =  getIntent().getIntExtra("ID",0);
        Toast.makeText(Description_Activity.this, String.valueOf(ID),
                Toast.LENGTH_SHORT).show();
        name=(TextView)findViewById(R.id.name);
        desc=(TextView)findViewById(R.id.desc);
        type=(TextView)findViewById(R.id.type);
        price=(TextView)findViewById(R.id.price);





        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
            }
        };

         database = FirebaseDatabase.getInstance();
         myRef = database.getReference("plats/"+String.valueOf(ID));

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                name.setText(dataSnapshot.child("name").getValue(String.class));
                desc.setText(dataSnapshot.child("desc").getValue(String.class));
                price.setText(dataSnapshot.child("price").getValue(String.class));
                type.setText(dataSnapshot.child("type").getValue(String.class));

                //the very very cutre implementation
                //quick work
                switch((ID+1)){
                    case 1:
                        img.setImageResource(R.drawable.dish1);
                        break;
                    case 2:
                        img.setImageResource(R.drawable.dish2);
                        break;
                    case 3:
                        img.setImageResource(R.drawable.dish3);
                        break;
                    case 4:
                        img.setImageResource(R.drawable.dish4);
                        break;

                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(Description_Activity.this, "No internet connection!!",
                        Toast.LENGTH_SHORT).show();
                }
        });

        myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        myToolbar.setNavigationIcon(R.drawable.mlogo);
        myToolbar.inflateMenu(R.menu.toolbar_menu);
        myToolbar.setTitleTextColor(0xEEEEEEEE);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    public void logOut() {
        mAuth.signOut();
        finish();
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {

        } else {
            Toast.makeText(Description_Activity.this, "You need to be registered.",
                    Toast.LENGTH_SHORT).show();
            System.exit(0);
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
                logOut();
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

}
