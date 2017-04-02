package com.ecoach.cosapp.Activites.CompanyDetailsTabbedActivities;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.ecoach.cosapp.Application.Application;
import com.ecoach.cosapp.ListViewAdapter.DetailsListViewAdapter;
import com.ecoach.cosapp.Models.Company;
import com.ecoach.cosapp.Models.DetailsItem;
import com.ecoach.cosapp.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Details extends Activity {

    ListView listView;
    List<DetailsItem> detailsItems = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        setViews();
    }



    void setViews(){




        Company company = Application.getSelectedCompanyObbject();




        detailsItems.add(new DetailsItem(Details.this.getResources().getString(R.string.fa_addressbook),company.getCompany_address()));
        detailsItems.add(new DetailsItem(Details.this.getResources().getString(R.string.fa_phone),company.getCompany_phoneOne()));
        detailsItems.add(new DetailsItem(Details.this.getResources().getString(R.string.fa_phone),company.getCompany_phonetwo()));

        detailsItems.add(new DetailsItem(Details.this.getResources().getString(R.string.fa_envelope),company.getCompany_email()));
        detailsItems.add(new DetailsItem(Details.this.getResources().getString(R.string.fa_globe),company.getCompany_website()));
      //  detailsItems.add(new DetailsItem(R.mipmap.ic_adressbook,"dsdsdsd"));


        listView =(ListView)findViewById(R.id.itemDetails);
        // get data from the table by the ListAdapter
        DetailsListViewAdapter customAdapter = new DetailsListViewAdapter(this, R.layout.itemsdetailslistcell, detailsItems);

        listView .setAdapter(customAdapter);

    }



}
