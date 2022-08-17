package com.example.aplikasisqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.aplikasisqlite.databinding.ActivityAddEditBinding;
import com.example.aplikasisqlite.helper.DbHelper;

public class AddEditActivity extends AppCompatActivity {

    private ActivityAddEditBinding binding;
    DbHelper SQLite = new DbHelper(this);
    String id, name, address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddEditBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        id = getIntent().getStringExtra(MainActivity.TAG_ID);
        name = getIntent().getStringExtra(MainActivity.TAG_NAME);
        address = getIntent().getStringExtra(MainActivity.TAG_ADDRESS);

        if (id == null || id == "") {
            setTitle("Add Data");
        } else {
            setTitle("Edit Data");
            binding.txtId.setText(id);
            binding.txtName.setText(name);
            binding.txtAddress.setText(address);
        }

        binding.btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (binding.txtId.getText().toString().equals("")) {
                        save();
                    } else {
                        edit();
                    }
                } catch (Exception e){
                    Log.e("Submit", e.toString());
                }
            }
        });

        binding.btnCancel.setOnClickListener(v -> {
            blank();
            finish();
        });
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                blank();
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void blank() {
        binding.txtName.requestFocus();
        binding.txtId.setText(null);
        binding.txtName.setText(null);
        binding.txtAddress.setText(null);
    }

    private void save() {
        if (String.valueOf(binding.txtName.getText()).equals(null) || String.valueOf(binding.txtName.getText()).equals("") ||
                String.valueOf(binding.txtAddress.getText()).equals(null) || String.valueOf(binding.txtAddress.getText()).equals("")) {
            Toast.makeText(getApplicationContext(),
                    "Please input name or address ...", Toast.LENGTH_SHORT).show();
        } else {
            SQLite.insert(binding.txtName.getText().toString().trim(), binding.txtAddress.getText().toString().trim());
            blank();
            finish();
        }
    }
    // Update data kedalam Database SQLite
    private void edit() {
        if (String.valueOf(binding.txtName.getText()).equals("") || String.valueOf(binding.txtName.getText()).equals("")) {
            Toast.makeText(getApplicationContext(),
                    "Please input name or address ...", Toast.LENGTH_SHORT).show();
        } else {
            SQLite.update(Integer.parseInt(binding.txtId.getText().toString().trim()), binding.txtName.getText().toString().trim(),
                    binding.txtAddress.getText().toString().trim());
            blank();
            finish();
        }
    }
}