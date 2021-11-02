package com.firemix.storeapp.Stores.LoadsStores;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.ferfalk.simplesearchview.SimpleSearchView;
import com.firemix.storeapp.Adapters.Stores.AdapterStores;
import com.firemix.storeapp.R;
import com.firemix.storeapp.Stores.DetailsStore.DetailsModel;
import com.firemix.storeapp.Stores.Registers.RegisterStoreModel;

import java.util.ArrayList;
import java.util.List;

import Librarys.DB.DataBaseStores;
import Librarys.DB.Models.Stores;
import Librarys.Message;
import Librarys.Utils;

import static Librarys.Globals.LOGIN_ESTATE;

public class StoresModel extends StoresView implements Message.MessageActions, AdapterStores.StoreAction, View.OnClickListener {
    private final List<Stores> locales = new ArrayList<>();
    private final int REQUEST_ADD = 1345;
    private final int REQUEST_EDIT = 1346;
    private Utils utils;
    private DataBaseStores db;
    private AdapterStores adapter;
    private Message message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = DataBaseStores.getInstance(this);
        message = Message.getInstance(this, this);
        utils = Utils.getInstance(this);
        adapter = new AdapterStores(this, locales, this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        stores.setHasFixedSize(true);
        stores.setLayoutManager(linearLayoutManager);
        stores.setAdapter(adapter);
        LoadStores("");
        agregar.setOnClickListener(this);
        content.startRippleAnimation();
        setSupportActionBar(toolbar);
        nuevo.setOnClickListener(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        search.enableVoiceSearch(true);
        search.setVoiceSearchPrompt(getString(R.string.voice_store));
        search.setOnQueryTextListener(new SimpleSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                LoadStores(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                LoadStores(newText);
                return false;
            }

            @Override
            public boolean onQueryTextCleared() {
                LoadStores("");
                return false;
            }
        });

        search.setOnSearchViewListener(new SimpleSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {

            }

            @Override
            public void onSearchViewClosed() {
                LoadStores("");
            }

            @Override
            public void onSearchViewShownAnimation() {

            }

            @Override
            public void onSearchViewClosedAnimation() {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        search.setMenuItem(item);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    private void LoadStores(String nombre) {
        List<Stores> tiendas = db.getStores(nombre);
        locales.clear();
        if (tiendas.size() > 0) {
            mensajes.setVisibility(View.GONE);
            agregar.setVisibility(View.VISIBLE);
        } else {
            mensajes.setVisibility(View.VISIBLE);
            agregar.setVisibility(View.GONE);
        }
        locales.addAll(tiendas);
        adapter.notifyDataSetChanged();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != RESULT_OK || data == null) {
            return;
        }
        if (search.onActivityResult(requestCode, resultCode, data)) {
            return;
        }

        if (requestCode == REQUEST_ADD) {
            LoadStores("");
        }

        if (requestCode == REQUEST_EDIT) {
            LoadStores("");
        }
    }

    @Override
    public void AcceptMessage() {

    }

    @Override
    public void CancelMessage() {

    }

    @Override
    public void DeleteTienda(int EmpresaId, int position) {
        utils.DeleteFoto(adapter.Imagen(position));
        if (db.DeleteStore(EmpresaId)) {
            if (db.DeleteSchedule(adapter.StoresSchedule(position))) {
                locales.remove(position);
                if (db.CountStores("") > 0) {
                    adapter.notifyItemRemoved(position);
                    stores.setAdapter(adapter);
                } else {
                    LoadStores("");
                }
            } else {
                message.MessageBox(utils.ErrorsMessage(15, adapter.Nombre(position)), 1);
            }
        }
    }

    @Override
    public void UpdateTienda(int EmpresaId, int position) {
        Intent locationm = new Intent(this, RegisterStoreModel.class);
        locationm.putExtra("ID", EmpresaId);
        startActivityForResult(locationm, REQUEST_EDIT);
    }

    @Override
    public void editStore(int Id, int position) {
        message.BottomDialogUpdate(getString(R.string.update), String.format(getString(R.string.update_ask), adapter.Nombre(position)), Id, position);
    }

    @Override
    public void deleteStore(int Id, int position) {
        message.BottomDialogDelete(getString(R.string.delete), String.format(getString(R.string.delete_ask), adapter.Nombre(position)), Id, position);

    }

    @Override
    public void loadDetails(int Id) {
        Intent n = new Intent(this, DetailsModel.class);
        n.putExtra("ID", Id);
        startActivity(n);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.agregar) {
            Intent locationm = new Intent(this, RegisterStoreModel.class);
            startActivityForResult(locationm, REQUEST_ADD);
        }
        if (id == R.id.add) {
            Intent locationm = new Intent(this, RegisterStoreModel.class);
            startActivityForResult(locationm, REQUEST_ADD);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        sharedData.SaveData(LOGIN_ESTATE, "", false, 0, 1);
    }
}
