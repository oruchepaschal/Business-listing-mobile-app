package com.ecoach.cosapp.Activites.Company;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ViewFlipper;

import com.ecoach.cosapp.Activites.MainActivity;
import com.ecoach.cosapp.Activites.SearchActivity;
import com.ecoach.cosapp.R;

public class Addcompany extends AppCompatActivity {

    ViewFlipper viewFlipper;
    Button nextButton,backButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addcompany);

        if (getSupportActionBar() != null){

            getSupportActionBar().setTitle("Add Company");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }


        initViews();
        setactionButtons();

    }

    private void initViews() {

        viewFlipper=(ViewFlipper)findViewById(R.id.viewFlipper);

    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }



    private void setactionButtons() {

        // setIndicatorsInit();


        viewFlipper=(ViewFlipper)findViewById(R.id.viewFlipper);

        //indicator1.setBackgroundColor(CreateAccount.this.getResources().getColor(R.color.colorPrimary));
        nextButton=(Button)findViewById(R.id.nextButton);
        backButton=(Button)findViewById(R.id.backButton);
        backButton.setVisibility(View.INVISIBLE);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(nextButton.getText().equals("Get Started")){

                    // Toast.makeText(CreateAccount.this,"You need to Save",Toast.LENGTH_LONG).show();

                }


                if(viewFlipper.getDisplayedChild() == 2){

                    nextButton.setText("Get Started");



                }


                if(viewFlipper.getDisplayedChild() == 2){


                }else{

                    backButton.setVisibility(View.VISIBLE);
                    viewFlipper.showNext();
                }

            }
        });



        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(viewFlipper.getDisplayedChild() !=2){

                    nextButton.setText("Next");
                }

                if(viewFlipper.getDisplayedChild() == 1){

                    backButton.setVisibility(View.INVISIBLE);
                }


                if(viewFlipper.getDisplayedChild() != 0){

                    viewFlipper.showPrevious();

                }

            }
        });

    }
}
