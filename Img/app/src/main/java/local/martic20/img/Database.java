package local.martic20.img;

import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by marti.casas on 29/05/17.
 */

public class Database {
    private static FirebaseDatabase mDatabase;

    public static FirebaseDatabase getDatabase() {
        if (mDatabase == null) {
            mDatabase = FirebaseDatabase.getInstance();
            mDatabase.setPersistenceEnabled(true);
        }
        return mDatabase;
    }

}

