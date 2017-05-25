package local.martic20.img;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
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
    private RatingBar rate;

    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private Toolbar myToolbar;

    private int ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description_);
        ID = getIntent().getIntExtra("ID", 0);

        name = (TextView) findViewById(R.id.name);
        desc = (TextView) findViewById(R.id.desc);
        type = (TextView) findViewById(R.id.type);
        price = (TextView) findViewById(R.id.price);
        img = (ImageView) findViewById(R.id.img);
        rate =(RatingBar) findViewById(R.id.rate);

        img.setImageResource(R.drawable.dish1);


        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

            }
        };

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("plats/" + String.valueOf(ID));

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                name.setText(dataSnapshot.child("name").getValue(String.class));
                desc.setText(dataSnapshot.child("desc").getValue(String.class));
                price.setText(dataSnapshot.child("price").getValue(String.class));
                type.setText(dataSnapshot.child("type").getValue(String.class));
                img.setImageResource(Elements.getImgId(ID));
                findViewById(R.id.loading).setVisibility(View.GONE);
                findViewById(R.id.img).setVisibility(View.VISIBLE);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(Description_Activity.this, "No internet connection!!",
                        Toast.LENGTH_SHORT).show();
            }
        });

        myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        myToolbar.inflateMenu(R.menu.toolbar_menu);
        myToolbar.setTitleTextColor(0xEEEEEEEE);



        DatabaseReference userName = database.getReference("users");
        userName.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                if (currentUser!=null) {
                    myToolbar.setTitle(dataSnapshot.child(currentUser.getUid()).child("name").getValue().toString());

                    rate.setRating(Float.parseFloat(dataSnapshot.child(currentUser.getUid()).child("valorations").child(String.valueOf(ID)).getValue().toString()));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(Description_Activity.this, "Database error: "+databaseError.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
        rate.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            public void onRatingChanged(RatingBar ratingBar, float rating,
                                        boolean fromUser) {
                FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                if (currentUser!=null) {
                    DatabaseReference firebase = FirebaseDatabase.getInstance().getReference("users/" +currentUser.getUid()+"/valorations");
                    firebase.child(String.valueOf(ID)).setValue(Math.round(rating));
                    if(fromUser){Toast.makeText(Description_Activity.this, "Valoration saved, "+Math.round(rating),
                            Toast.LENGTH_SHORT).show();}
                }else{
                    Toast.makeText(Description_Activity.this, "Error on saving valoration",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
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
            case R.id.about:
                startActivity(new Intent(Description_Activity.this,About.class));
                return true;
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

}
