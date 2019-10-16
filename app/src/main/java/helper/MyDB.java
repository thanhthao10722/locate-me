package helper;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import model.User;

public class MyDB
{
    private DatabaseReference mDatabase;

    public void writeNewUser(){
        mDatabase = FirebaseDatabase.getInstance().getReference();
        String userId = mDatabase.push().getKey();
        User user = new User("0963443189", "1806", "Thanh Thao", true, "", "", "");
        mDatabase.child("users").child(userId).setValue(user);
    }
}
