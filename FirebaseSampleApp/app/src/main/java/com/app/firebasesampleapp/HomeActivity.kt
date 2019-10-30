package com.app.firebasesampleapp

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*
import org.w3c.dom.Comment

class HomeActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "HomeActivity"
    }

    private lateinit var database: DatabaseReference


    private val childEventListener = object : ChildEventListener {
        override fun onCancelled(databaseError: DatabaseError) {
            Log.w(TAG, "postComments:onCancelled", databaseError.toException())
            Toast.makeText(
                applicationContext, "Failed to load comments.",
                Toast.LENGTH_SHORT
            ).show()
        }

        override fun onChildAdded(dataSnapshot: DataSnapshot, previousChildName: String?) {
            Log.d(TAG, "onChildAdded:" + dataSnapshot.key!!)

            // A new comment has been added, add it to the displayed list
            //val comment = dataSnapshot.getValue(Comment::class.java)

            // ...
        }

        override fun onChildChanged(dataSnapshot: DataSnapshot, previousChildName: String?) {
            Log.d(TAG, "onChildChanged: ${dataSnapshot.key}")

            // A comment has changed, use the key to determine if we are displaying this
            // comment and if so displayed the changed comment.
            //val newComment = dataSnapshot.getValue(Comment::class.java)
            //val commentKey = dataSnapshot.key

            // ...
        }

        override fun onChildRemoved(dataSnapshot: DataSnapshot) {
            Log.d(TAG, "onChildRemoved:" + dataSnapshot.key!!)

            // A comment has changed, use the key to determine if we are displaying this
            // comment and if so remove it.
            //val commentKey = dataSnapshot.key

            // ...
        }

        override fun onChildMoved(dataSnapshot: DataSnapshot, previousChildName: String?) {
            Log.d(TAG, "onChildMoved:" + dataSnapshot.key!!)

            // A comment has changed position, use the key to determine if we are
            // displaying this comment and if so move it.
            val movedComment = dataSnapshot.getValue(Comment::class.java)
            val commentKey = dataSnapshot.key

            // ...
        }


    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val instance = FirebaseDatabase.getInstance()
        instance.setPersistenceEnabled(true)
        database = instance.reference;

        var reference = database.database.getReference("copyright")
        reference.setValue("@2019, All rights reserved.")


        val user = User("Ramesh", 30, "abc@gmail.com")

        reference = instance.getReference("users")
        val key = reference.push().key
        //key?.let { reference.child(it).setValue(user) }

        if (key != null) {
            reference.child("users").child(key).setValue(user)
            reference.child(key).addChildEventListener(childEventListener)
        }


    }

}
