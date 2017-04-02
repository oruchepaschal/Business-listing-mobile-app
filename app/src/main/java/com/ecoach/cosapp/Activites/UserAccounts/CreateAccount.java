package com.ecoach.cosapp.Activites.UserAccounts;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ViewFlipper;

import com.ecoach.cosapp.R;

public class CreateAccount extends Activity {


    ViewFlipper viewFlipper;
    Button nextButton,backButton;

    Button indicator1,indicator2,indicator3,indicator4,indicator5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        initViews();
    }

    private void initViews() {

        setIndicatorsInit();


        viewFlipper=(ViewFlipper)findViewById(R.id.viewFlipper);

        indicator1.setBackgroundColor(CreateAccount.this.getResources().getColor(R.color.colorPrimary));
        nextButton=(Button)findViewById(R.id.nextButton);
        backButton=(Button)findViewById(R.id.backButton);
        backButton.setVisibility(View.INVISIBLE);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("view flipper",viewFlipper.getDisplayedChild() +"< displayed child  ||  all count >"+viewFlipper.getChildCount());

                if(viewFlipper.getDisplayedChild() == viewFlipper.getChildCount()){


                }else{

                    viewFlipper.showNext();
                }

            }
        });



        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(viewFlipper.getDisplayedChild() != 0){

                    viewFlipper.showPrevious();

                }

            }
        });

    }


    private void setIndicatorsInit(){

        indicator1=(Button)findViewById(R.id.indicatorOne);
        indicator2=(Button)findViewById(R.id.indicatorTwo);
        indicator3=(Button)findViewById(R.id.indicatorThree);
        indicator4=(Button)findViewById(R.id.indicatorFour);
        indicator5=(Button)findViewById(R.id.indicatorfice);

    }
}
