package com.example.sebastian.tindertp.chatTools;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.sebastian.tindertp.R;

import java.util.ArrayList;
import java.util.List;

/** Adaptador para los mensajes del Chat*/
public class ChatArrayAdapter extends ArrayAdapter<ChatMessage>{
    private TextView chatText;      /**< Texto del mensaje*/
    private List<ChatMessage> messageList = new ArrayList<ChatMessage>();
    private LinearLayout layout;
    private List<Boolean> failedList = new ArrayList<>() ;


    public ChatArrayAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public int size() {
        return messageList.size();
    }

    public void add(int index, ChatMessage object) {
        messageList.add(index, object);
        failedList.add(index,false);
        super.insert(object,index);
    }

    public void add(ChatMessage object) {
        messageList.add(object);
        failedList.add(false);
        super.add(object);
    }

    public void remove(ChatMessage chatMessage, int position ) {
        failedList.remove(position);
        messageList.remove(chatMessage);
        super.remove(chatMessage);
    }

    public int indexOf (ChatMessage chat) {
        return messageList.indexOf(chat);
    }

    public boolean isFailureMessage(int position) {
        return failedList.get(position);
    }

    /**Usado si hubo algun error */
    public void change(int position) {
        failedList.set(position,true);
    }

    public int getCount() {
        return this.messageList.size();
    }

    public ChatMessage getItem(int index) {
        return this.messageList.get(index);
    }

    public View getView(int position,View ConvertView, ViewGroup parent) {

        View v = ConvertView;
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.chat, parent, false);
        }

        layout = (LinearLayout) v.findViewById(R.id.Message1);
        ChatMessage messageObj = getItem(position);
        chatText = (TextView) v.findViewById(R.id.SingleMessage);

        chatText.setText(messageObj.message);
        //chatText.setBackgroundResource(messageObj.left ? R.drawable.blue_bubble : R.drawable.green_bubble);
        if (failedList.get(position)) {
            chatText.setBackgroundResource(R.drawable.grey_bubble);
        }else {
            chatText.setBackgroundResource(messageObj.left ? R.drawable.blue_bubble : R.drawable.green_bubble);
        }

        layout.setGravity(messageObj.left ? Gravity.START : Gravity.END);

        return v;


    }

}
