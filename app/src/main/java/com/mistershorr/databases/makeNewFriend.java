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

        private int clumsiness;
        private double gymFrequency;
        private boolean isAwesome;
        private double moneyOwed;
        private String name;
        private int trustworthiness;

        private Friend contact;
    public static final String EXTRA_FRIEND_PACKAGE = "friend package";
    private boolean update = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_new_friend);

        wirewidgets();

        contact = getIntent().getParcelableExtra(FriendsListActivity.EXTRA_FRIEND_PACKAGE);
        if(contact != null){
            setStuff();
            makeFriendButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Backendless.Persistence.save( contact, new AsyncCallback<Friend>() {
                        public void handleResponse( Friend savedContact )
                        {
                            savedContact.setTitle( "Most favorite" );
                            savedContact.setPhone( "666-666-666" );

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
            });
        }
        else{
            contact = new Friend();
            Toast.makeText(makeNewFriend.this, "mike and ike ", Toast.LENGTH_SHORT).show();

        }
        makeFriendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(name != null && clumsiness != 0 && gymFrequency!= 0 && trustworthiness!=0){
                        contact.setName( name );
                        contact.setClumsiness( clumsiness );
                        contact.setGymFrequency( gymFrequency);
                        contact.setAwesome( isAwesome);
                        contact.setMoneyOwed(moneyOwed);
                        contact.setTrustworthiness(trustworthiness);

                        // save object asynchronously
                        Backendless.Persistence.save( contact, new AsyncCallback<Friend>() {
                            public void handleResponse( Friend response )
                            {
                                // new Contact instance has been saved
                                Toast.makeText(makeNewFriend.this, "Friend " + contact.getName() + "has been created", Toast.LENGTH_SHORT).show();


                            }

                            public void handleFault( BackendlessFault fault )
                            {
                                // an error has occurred, the error code can be retrieved with fault.getCode()
                            }
                        });
                    }
                Toast.makeText(makeNewFriend.this, "Welcome " + contact.getName(), Toast.LENGTH_SHORT).show();

                getInformation();

            }

        });




    }

    private void setStuff() {
            nameEditText.setText(contact.getName());
            clumsinessSeekBar.setProgress(contact.getClumsiness());
            awesomeSwitch.setChecked(contact.isAwesome());
            gymFreqSeekBar.setProgress((int) (contact.getGymFrequency()));
            trustworthinessRatingBar.setNumStars(contact.getTrustworthiness());
            editText_MoneyOwed.setText(Double.toString(contact.getMoneyOwed()));
            makeFriendButton.setText("Update Friend");
    }

    private void getInformation() {
        name = nameEditText.getText().toString();
        clumsiness = clumsinessSeekBar.getProgress();
         isAwesome = awesomeSwitch.isChecked();
         gymFrequency = gymFreqSeekBar.getProgress();
         trustworthiness = trustworthinessRatingBar.getNumStars();
         moneyOwed = Double.parseDouble(editText_MoneyOwed.getText().toString());


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
}
