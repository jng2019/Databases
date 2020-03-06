package com.mistershorr.databases;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;

public class makeNewFriend extends AppCompatActivity {

    private EditText nameEditText;
    private SeekBar clumsinessSeekBar;
    private Switch awesomeSwitch;
    private SeekBar gymFreqSeekBar;
    private RatingBar trustworthinessRatingBar;
    private EditText editText_MoneyOwed;
    private Button makeFriendButton;



        private Friend friend;
    public static final String EXTRA_FRIEND_PACKAGE = "friend package";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_new_friend);

        wirewidgets();

        friend = getIntent().getParcelableExtra(FriendsListActivity.EXTRA_FRIEND_PACKAGE);
        if(friend != null){
            setStuff();
            makeFriendButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    friend.setName(nameEditText.getText().toString());
                    friend.setClumsiness(clumsinessSeekBar.getProgress());
                    friend.setAwesome(awesomeSwitch.isChecked());
                    friend.setGymFrequency(gymFreqSeekBar.getProgress());
                    friend.setTrustworthiness((int)trustworthinessRatingBar.getRating());
                    friend.setMoneyOwed(Double.parseDouble(editText_MoneyOwed.getText().toString()));

                    updateContact();


                }

            });
        }
        else{
            friend = new Friend();
            Toast.makeText(makeNewFriend.this, "made new friend", Toast.LENGTH_SHORT).show();

        }
//        makeFriendButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(name != null && clumsiness != 0 && gymFrequency!= 0 && trustworthiness!=0){
//                        contact.setName( name );
//                        contact.setClumsiness( clumsiness );
//                        contact.setGymFrequency( gymFrequency);
//                        contact.setAwesome( isAwesome);
//                        contact.setMoneyOwed(moneyOwed);
//                        contact.setTrustworthiness(trustworthiness);
//
//                        // save object asynchronously
//                        Backendless.Persistence.save( contact, new AsyncCallback<Friend>() {
//                            public void handleResponse( Friend response )
//                            {
//                                // new Contact instance has been saved
//                                Toast.makeText(makeNewFriend.this, "Friend " + contact.getName() + "has been created", Toast.LENGTH_SHORT).show();
//
//
//                            }
//
//                            public void handleFault( BackendlessFault fault )
//                            {
//                                // an error has occurred, the error code can be retrieved with fault.getCode()
//                            }
//                        });
//                    }
//                Toast.makeText(makeNewFriend.this, "Welcome " + contact.getName(), Toast.LENGTH_SHORT).show();
//
//                getInformation();
//
//            }
//
//        });




    }

    private void setStuff() {
            nameEditText.setText(friend.getName());
            clumsinessSeekBar.setProgress(friend.getClumsiness());
            awesomeSwitch.setChecked(friend.isAwesome());
            gymFreqSeekBar.setProgress((int) (friend.getGymFrequency()));
            trustworthinessRatingBar.setNumStars(friend.getTrustworthiness());
            editText_MoneyOwed.setText(Double.toString(friend.getMoneyOwed()));
            makeFriendButton.setText("Update Friend");
    }



    private void wirewidgets() {
        nameEditText = findViewById(R.id.name_editText);
        clumsinessSeekBar = findViewById(R.id.seekBar_Clumsiness);
        awesomeSwitch = findViewById(R.id.switch_Awesomeness);
        gymFreqSeekBar = findViewById(R.id.seekBar_gymFreq);
        trustworthinessRatingBar = findViewById(R.id.ratingBar_Trustworthiness);
        editText_MoneyOwed = findViewById(R.id.editText_moneyOwed);
        makeFriendButton = findViewById(R.id.button_MakeFriend);
    }

    public void updateContact(){
        Backendless.Persistence.save( friend, new AsyncCallback<Friend>() {
            public void handleResponse( Friend savedContact )
            {

                Backendless.Persistence.save( savedContact, new AsyncCallback<Friend>() {
                    @Override
                    public void handleResponse( Friend response )
                    {
                        // Contact instance has been updated
                    }
                    @Override
                    public void handleFault( BackendlessFault fault )
                    {
                        // an error has occurred, the error code can be retrieved with fault.getCode()
                    }
                } );
            }
            @Override
            public void handleFault( BackendlessFault fault )
            {
                // an error has occurred, the error code can be retrieved with fault.getCode()
            }
        });
    }

}

