package com.mistershorr.databases;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
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
import com.backendless.persistence.DataQueryBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class FriendsListActivity extends AppCompatActivity{

    private FloatingActionButton floatingActionButton;
    private ListView listView;
    private List<Friend> friendList;
    private friendAdapter friendAdapter;
    public static final String EXTRA_FRIEND_PACKAGE = "friend package";


    // TODO THE SET SEEKBARS TO WORK
    // TODO  THE LOGOUT STUFF WORK AND BACK BUTTON


    public static final String TAG = FriendsListActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends_list);

        String userId = Backendless.UserService.CurrentUser().getObjectId();
        String whereClause = "ownerId = '" + userId + "'";
        DataQueryBuilder queryBuilder = DataQueryBuilder.create();
        queryBuilder.setWhereClause( whereClause);

        useBackEndless(queryBuilder);


        floatingActionButton = findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent makeFriends = new Intent(FriendsListActivity.this, makeNewFriend.class);
                makeFriends.putExtra(EXTRA_FRIEND_PACKAGE, false);
                startActivity(makeFriends);

            }
        });

        // verify that it read everything properly
        listView = findViewById(R.id.friendList_listView);
        registerForContextMenu(listView);


        // search only for friends that have ownerIds that match the users objectId
    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)
    {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.delete_menu, menu);
    }
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.call:
            deleteContact(friendAdapter.friendList.remove(info.position));
            friendAdapter.notifyDataSetChanged();
             return true;
            default:
                return super.onContextItemSelected(item);
        }
    }
    @Override
    public void onResume(){
        super.onResume();
        String userId = Backendless.UserService.CurrentUser().getObjectId();
        String whereClause = "ownerId = '" + userId + "'";
        DataQueryBuilder queryBuilder = DataQueryBuilder.create();
        queryBuilder.setWhereClause( whereClause);

        useBackEndless(queryBuilder);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_friendlist_sorting, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_heroeslist_sort_moneyowed:
                sortByRank();
                friendAdapter.notifyDataSetChanged();
                Log.d(TAG, "onOptionsItemSelected: ");
                return true;
            case R.id.action_heroesList_sort_by_name:
                sortByName();
                friendAdapter.notifyDataSetChanged();
                Log.d(TAG, "onOptionsItemSelected: ");
                return true;
            case R.id.logout_friendlist:
                lougout();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void lougout(){

        Backendless.UserService.logout( new AsyncCallback<Void>()
        {
            public void handleResponse( Void response )
            {
                // user has been logged out.
                Toast.makeText(FriendsListActivity.this, "Logged out", Toast.LENGTH_SHORT).show();
                Intent loggedout = new Intent(FriendsListActivity.this, LoginActivity.class);
                startActivity(loggedout);
                finish();
            }

            public void handleFault( BackendlessFault fault )
            {
                // something went wrong and logout failed, to get the error code call fault.getCode()
            }
        });
    }

    private void sortByRank() {
        // extract the list from the adapter
        // heroAdapter.heroesList instead of thingList
        Collections.sort(friendAdapter.friendList, new Comparator<Friend>() {
            @Override
            public int compare(Friend hero, Friend t1) {
                // negative number if thing comes before t1
                // 0 if thing and t1 are the same
                // postive number if thing comes after t1
                return (int) (t1.getMoneyOwed() - hero.getMoneyOwed());
            }
        });
        // the data in the adapter has changed, but it isn't aware
        // call the method notifyDataSetChanged on the adapter.
        Toast.makeText(this, "Sorted by Money", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "sortByRank: sortTheRank");

    }

    private void sortByName() {
        // extract the list from the adapter
        // heroAdapter.heroesList instead of thingList
        Collections.sort(friendAdapter.friendList, new Comparator<Friend>() {
            @Override
            public int compare(Friend thing, Friend t1) {
                Log.d(TAG, "compare: sdsafsdfafafa");
                return thing.getName().toLowerCase()
                        .compareTo(t1.getName().toLowerCase());

            }
        });
        // the data in the adapter has changed, but it isn't aware
        // call the method notifyDataSetChanged on the adapter.
        Toast.makeText(this, "Sorted by Name", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "sortByName: fadf");
    }


    private void useBackEndless(DataQueryBuilder queryBuilder) {
        Backendless.Data.of(Friend.class).find(queryBuilder, new AsyncCallback<List<Friend>>(){
            @Override
            public void handleResponse(final List<Friend> friendList )
            {
                // all Contact instances have been found
                Log.d(TAG, "handleResponse: " + friendList.toString());
                friendAdapter = new friendAdapter(friendList);
                listView.setAdapter(friendAdapter);

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        Intent targetIntent = new Intent(FriendsListActivity.this, makeNewFriend.class);//first is from where we are coming from, second one is where we are going
                        targetIntent.putExtra(EXTRA_FRIEND_PACKAGE, friendList.get(position));
                        // launch the new activity
                        startActivity(targetIntent);


                    }
                });

            }
            @Override
            public void handleFault( BackendlessFault fault )
            {
                // an error has occurred, the error code can be retrieved with fault.getCode()
            }
        });
    }

    private void deleteContact(Friend deleteFriend){
          Backendless.Persistence.of( Friend.class ).remove( deleteFriend, new AsyncCallback<Long>()
                    {
                        public void handleResponse( Long response )
                        {
                            // Contact has been deleted. The response is the
                            // time in milliseconds when the object was deleted
                        }
                        public void handleFault( BackendlessFault fault )
                        {
                            // an error has occurred, the error code can be
                            // retrieved with fault.getCode()
                        }
                    } );

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
            textViewMoney.setText("$" + String.valueOf(friend.getMoneyOwed()));
            textViewClumsiness.setText("Clumsiness : " + String.valueOf(friend.getClumsiness()));

            // 3. return inflated view, use the position parameter variable
            return convertView;
        }
    }


}
