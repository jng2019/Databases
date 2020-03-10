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
                    friend.setClumsiness(clumsinessSeekBar.getProgress() + 1);
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

            makeFriendButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!(nameEditText.getText().toString().equals("")) && clumsinessSeekBar.getProgress() != 0){
                        friend.setownerId(Backendless.UserService.CurrentUser().getObjectId());
                        if(editText_MoneyOwed.getText().toString().equals("")){
                            friend.setMoneyOwed(0);
                        }
                        else{
                            friend.setMoneyOwed(Double.parseDouble(editText_MoneyOwed.getText().toString()));
                        }
                        friend.setName(  nameEditText.getText().toString() );
                        friend.setClumsiness( clumsinessSeekBar.getProgress() + 1);
                        friend.setGymFrequency( gymFreqSeekBar.getProgress());
                        friend.setAwesome( awesomeSwitch.isChecked());
                        friend.setTrustworthiness((int) trustworthinessRatingBar.getRating());

                        // save object asynchronously
                        Backendless.Persistence.save( friend, new AsyncCallback<Friend>() {
                            public void handleResponse( Friend response )
                            {
                                // new Contact instance has been saved
                                Toast.makeText(makeNewFriend.this, "Friend " + friend.getName() + " has been created", Toast.LENGTH_SHORT).show();
                                finish();
                            }

                            public void handleFault( BackendlessFault fault )
                            {
                                Toast.makeText(makeNewFriend.this, fault.getMessage(), Toast.LENGTH_SHORT).show();
                                // an error has occurred, the error code can be retrieved with fault.getCode()
                            }
                        });
                    }
                    else {
                        if (nameEditText.getText().toString().equals("")){
                            Toast.makeText(makeNewFriend.this, "Please enter a name ", Toast.LENGTH_SHORT).show();

                        }
                        if (clumsinessSeekBar.getProgress() == 0){
                            Toast.makeText(makeNewFriend.this, "Please enter a clumsiness ", Toast.LENGTH_SHORT).show();

                        }
                    }
                }

            });
        }
    }

    private void setStuff() {
            nameEditText.setText(friend.getName());
            clumsinessSeekBar.setProgress(friend.getClumsiness());
            awesomeSwitch.setChecked(friend.isAwesome());
            gymFreqSeekBar.setProgress((int) (friend.getGymFrequency()));
            trustworthinessRatingBar.setRating(friend.getTrustworthiness());
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
            public void handleResponse( Friend savedContact ) {
                //toast and finsih
                Toast.makeText(makeNewFriend.this, "Friend "  + nameEditText.getText().toString() + " has been updated", Toast.LENGTH_SHORT).show();
                finish();
            }
            @Override
            public void handleFault( BackendlessFault fault )
            {
                // an error has occurred, the error code can be retrieved with fault.getCode()
            }
        });
    }

}

