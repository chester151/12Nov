package com.example.yeohf.loginsystem;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.yeohf.loginsystem.Adapters.ChatListAdapter;
import com.example.yeohf.loginsystem.Entity.Chat;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    FloatingActionButton send;
    DatabaseReference listOfChat;
    ListView listofmessage;
    List<Chat> messageList;
    FirebaseAuth firebaseauth;
    String chatid, rentid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        chatid = extras.getString("chatid");
        rentid = extras.getString("rentid");

        messageList = new ArrayList<>();
        firebaseauth = FirebaseAuth.getInstance();
        send = findViewById(R.id.send);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText input = findViewById(R.id.textinputchat);
                FirebaseDatabase.getInstance().getReference("Chats").push().setValue(new Chat(chatid, rentid, input.getText().toString(), FirebaseAuth.getInstance().getCurrentUser().getEmail()));
                input.setText("");
            }
        });
        listofmessage = findViewById(R.id.listviewmessage);
        listOfChat = FirebaseDatabase.getInstance().getReference("Chats");
        listOfChat.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                messageList.clear();
                Iterable<DataSnapshot> child = dataSnapshot.getChildren();
                for (DataSnapshot uniquesnap : child) {
                    if (uniquesnap.child("rentId").getValue().equals(rentid)) {
                        Chat chats = uniquesnap.getValue(Chat.class);
                        messageList.add(chats);
                    }
                }
                ChatListAdapter adapter = new ChatListAdapter(ChatActivity.this, messageList);
                listofmessage.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.flatinfoicon:
                Toast.makeText(getApplicationContext(), "To be done!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.homeicon:
                Intent intent = new Intent(ChatActivity.this, ScreenActivity.class);
                startActivity(intent);
                break;
            case R.id.sellicon:
                Toast.makeText(getApplicationContext(), "To be done!", Toast.LENGTH_SHORT).show();
                break;
            default:
                return false;
        }
        return true;
    }
}
