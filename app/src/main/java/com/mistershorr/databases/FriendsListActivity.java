package com.mistershorr.databases;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.InputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class FriendsListActivity extends AppCompatActivity {

    private FloatingActionButton floatingActionButton;
    private ListView listView;
    private List<Friend> friendList;
    private friendAdapter friendAdapter;


    public static final String TAG = FriendsListActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends_list);

        useBackEndLess();
        floatingActionButton = findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent makeFriends = new Intent(FriendsListActivity.this, makeNewFriend.class);
                startActivity(makeFriends);
                if()

            }
        });

        // verify that it read everything properly
        listView = findViewById(R.id.friendList_listView);

    }


    private class friendAdapter extends ArrayAdapter<Friend> {
        // make an instance variable to keep track of the hero list
        private List<Friend> friendList;

        public friendAdapter(List<Friend> friendList) {
            super(FriendsListActivity.this, -1, friendList);
            this.friendList = friendList;
        }
        // the goal of the adapter is to link your list to the listview
        // and tell the listview where each aspect of the list item goes
        // so we override a method called
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // 1. inflate a layout
            LayoutInflater inflater = getLayoutInflater();

            // check if convertView is null
            // if so we'll replace it
            if(convertView == null){
                convertView = inflater.inflate(R.layout.friend_hero, parent, false);
            }

            // 2. wire widgets and link the hero to those widgets
            //instead of calling it from the activity class level
            // we're calling it from the inflated layout to find those widgets
            TextView textViewName = convertView.findViewById(R.id.textView_friendHero_name);
            TextView textViewMoney = convertView.findViewById(R.id.textView_friendHero_money);
            TextView textViewClumsiness = convertView.findViewById(R.id.textView_friendHero_clumsiness);

            // to get the hero you need out of the list
            Friend friend = friendList.get(position);
            // and set the values for the widgets
            textViewName.setText(String.valueOf(friend.getName()));
            textViewMoney.setText(String.valueOf(friend.getMoneyOwed()));
            textViewClumsiness.setText(String.valueOf(friend.getClumsiness()));

            // 3. return inflated view, use the position parameter variable
            return convertView;
        }
    }
    private void useBackEndLess(){
        Backendless.Data.of(Friend.class).find(new AsyncCallback<List<Friend>>(){
            @Override
            public void handleResponse( List<Friend> friendList )
            {
                // all Contact instances have been found
                Log.d(TAG, "handleResponse: " + friendList.toString());
                friendAdapter = new friendAdapter(friendList);
                listView.setAdapter(friendAdapter);

            }
            @Override
            public void handleFault( BackendlessFault fault )
            {
                // an error has occurred, the error code can be retrieved with fault.getCode()
            }
        });
    }

}