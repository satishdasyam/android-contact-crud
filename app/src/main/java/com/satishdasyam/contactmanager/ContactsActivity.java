package com.satishdasyam.contactmanager;

import android.content.Intent;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import com.satishdasyam.contactmanager.data.ContactDBAdapter;
import com.satishdasyam.contactmanager.databinding.ActivityContactsBinding;
import com.satishdasyam.contactmanager.databinding.ListRowBinding;
import com.satishdasyam.contactmanager.models.ContactModel;
import com.satishdasyam.contactmanager.utils.AppUtils;

import java.util.ArrayList;
import java.util.List;

public class ContactsActivity extends AppCompatActivity {
    /**
     * https://developer.android.com/tools/data-binding/guide.html
     */

    ActivityContactsBinding binding;
    List<ContactModel> contactList = new ArrayList<>();
    ContactAdapter contactAdapter = new ContactAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_contacts);
        setLayoutManager();
        binding.recyclerView.setAdapter(contactAdapter);
        setListeners();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getContacts();
    }

    private void setLayoutManager() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        binding.recyclerView.setLayoutManager(layoutManager);
    }

    private void setListeners() {
        binding.vbAdd.setOnClickListener(v -> {
            startActivity(new Intent(ContactsActivity.this, CreateContactActivity.class));
        });

        binding.veName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                contactAdapter.getFilter().filter(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    class ContactAdapter extends RecyclerView.Adapter<ViewHolder> implements Filterable {

        private List<ContactModel> adapterList = new ArrayList<>();

        private List<ContactModel> getList() {
            return adapterList;
        }

        private void setAdapterList(List<ContactModel> adpList) {
            // if (adapterList != null) adapterList.clear();
            adapterList = null;
            this.adapterList = adpList;
            notifyDataSetChanged();
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(ContactsActivity.this).
                    inflate(R.layout.list_row, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {
            holder.listRowBinding.tvContactName.
                    setText(adapterList.get(position).getName());
            holder.listRowBinding.tvContactEmail.
                    setText("Email:" + adapterList.get(position).getEmail() + "\nContactNo:" +
                            adapterList.get(position).getContactNumber() + "\nAddress:" +
                            adapterList.get(position).getAddress());
           /* holder.listRowBinding.tvContactNumber.
                    setText(adapterList.get(position).getContactNumber());
            holder.listRowBinding.tvContactAddress.
                    setText(adapterList.get(position).getAddress());*/
            String text = String.valueOf(adapterList.get(position).
                    getName().subSequence(0, 1).charAt(0));
            holder.listRowBinding.parentView.
                    setBackgroundColor(AppUtils.getColor(text, getResources()));
            holder.listRowBinding.ivContactDelete.setOnClickListener(v -> {
                deleteContact(adapterList.get(position).getRowId());

            });
        }

        @Override
        public int getItemCount() {
            return adapterList.size();
        }

        @Override
        public Filter getFilter() {
            return new FilterList();
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        ListRowBinding listRowBinding;

        public ViewHolder(View itemView) {
            super(itemView);
            listRowBinding = DataBindingUtil.bind(itemView);
        }
    }

    class FilterList extends Filter {
        List<ContactModel> filteredList = new ArrayList<>();

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            filteredList.clear();
            final FilterResults results = new FilterResults();

            if (constraint.length() == 0) {
                filteredList.addAll(contactList);
            } else {
                //final String filterPattern = constraint.toString().toLowerCase().trim();

                for (final ContactModel user : contactList) {
                    if (user.getName().contains(constraint) ||
                            user.getContactNumber().contains(constraint) ||
                            user.getEmail().contains(constraint)) {
                        filteredList.add(user);
                    }
                }
            }
            results.values = filteredList;
            results.count = filteredList.size();
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            contactAdapter.setAdapterList(filteredList);
        }
    }

    private void getContacts() {
        contactList.clear();
        new Thread(new Runnable() {
            @Override
            public void run() {
                ContactDBAdapter dbAdapter = new ContactDBAdapter(ContactsActivity.this);
                Cursor cursor = dbAdapter.getContactCursor();
                if (cursor == null) return;
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    ContactModel model = new ContactModel();
                    model.setName(cursor.getString(cursor.getColumnIndex(ContactDBAdapter.NAME)));
                    model.setEmail(cursor.getString(cursor.getColumnIndex(ContactDBAdapter.EMAIL)));
                    model.setAddress(cursor.getString(cursor.getColumnIndex(ContactDBAdapter.ADDRESS)));
                    model.setContactNumber(cursor.getString(cursor.
                            getColumnIndex(ContactDBAdapter.CONTACT_NUMBER)));
                    model.setRowId(cursor.getString(cursor.
                            getColumnIndex(ContactDBAdapter.ROW_ID)));
                    cursor.moveToNext();
                    contactList.add(model);
                }
                cursor.close();
                dbAdapter.close();
                runOnUiThread(() -> contactAdapter.setAdapterList(contactList));
            }
        }).start();

    }

    private void deleteContact(final String rowId) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                ContactDBAdapter dbAdapter = new ContactDBAdapter(ContactsActivity.this);
                dbAdapter.deleteTableRow(rowId);
                dbAdapter.close();
                getContacts();
                return null;
            }
        }.execute();

    }
}
