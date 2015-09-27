package com.satishdasyam.contactmanager;

import android.content.ContentValues;
import android.databinding.DataBindingUtil;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.satishdasyam.contactmanager.data.ContactDBAdapter;
import com.satishdasyam.contactmanager.databinding.ActivityCreateContactBinding;

public class CreateContactActivity extends AppCompatActivity {

    ActivityCreateContactBinding createContactBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createContactBinding = DataBindingUtil.
                setContentView(this, R.layout.activity_create_contact);
        setListeners();
    }

    private void setListeners() {
        createContactBinding.vbCreate.setOnClickListener(v -> {
            if (!createContactBinding.veName.getText().toString().isEmpty()
                    && !createContactBinding.veContactno.getText().toString().isEmpty())
                saveInSqLite();
            else {
                Toast.makeText(CreateContactActivity.this, "Please enter a name and number",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void saveInSqLite() {
        final String name = createContactBinding.veName.getText().toString();
        final String contactNo = createContactBinding.veContactno.getText().toString();
        final String email = createContactBinding.veEmail.getText().toString();
        final String address = createContactBinding.veAddress.getText().toString();
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                ContactDBAdapter dbAdapter =
                        new ContactDBAdapter(CreateContactActivity.this);
                ContentValues contactData = new ContentValues();
                contactData.put(ContactDBAdapter.NAME, name);
                contactData.put(ContactDBAdapter.EMAIL, email);
                contactData.put(ContactDBAdapter.CONTACT_NUMBER, contactNo);
                contactData.put(ContactDBAdapter.ADDRESS, address);
                dbAdapter.setContactData(contactData);
                dbAdapter.close();
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                finish();
            }
        }.execute();
    }
}
