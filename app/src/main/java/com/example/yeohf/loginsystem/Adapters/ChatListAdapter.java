package com.example.yeohf.loginsystem.Adapters;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.yeohf.loginsystem.Entity.Chat;
import com.example.yeohf.loginsystem.R;

import java.util.List;

public class ChatListAdapter extends ArrayAdapter<Chat> {

    private Activity context;
    private List<Chat> chatList;

    public ChatListAdapter(Activity context, List<Chat> chatList) {
        super(context, R.layout.activity_chatlist, chatList);
        this.context = context;
        this.chatList = chatList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.activity_chatlist, null, true);

        TextView username = listViewItem.findViewById(R.id.txtViewUsername);
        TextView timestamp = listViewItem.findViewById(R.id.txtViewTimestamp);
        TextView message = listViewItem.findViewById(R.id.txtViewMessage);

        Chat chat = chatList.get(position);

        username.setText(chat.getUserEmail());
        timestamp.setText(DateFormat.format("dd-MM-yyyy (HH:mm:ss)", chat.getTimeStamp()));
        message.setText(chat.getTxtMessage());

        return listViewItem;
    }
}
