package com.ednerdaza.codigoriginal.redditapplication.mvc.controllers.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.ednerdaza.codigoriginal.redditapplication.R;
import com.ednerdaza.codigoriginal.redditapplication.classes.helpers.Helpers;
import com.ednerdaza.codigoriginal.redditapplication.classes.utilities.Config;
import com.ednerdaza.codigoriginal.redditapplication.mvc.controllers.adapters.AdapterItems;
import com.ednerdaza.codigoriginal.redditapplication.mvc.controllers.base.VolleyQueue;
import com.ednerdaza.codigoriginal.redditapplication.mvc.controllers.interfaces.DelegateItemAdapter;
import com.ednerdaza.codigoriginal.redditapplication.mvc.controllers.interfaces.ItemModelInterface;
import com.ednerdaza.codigoriginal.redditapplication.mvc.models.ItemModel;
import com.ednerdaza.codigoriginal.redditapplication.mvc.models.entities.Children;
import com.ednerdaza.codigoriginal.redditapplication.mvc.models.entities.DataRoot;
import com.ednerdaza.codigoriginal.redditapplication.mvc.models.entities.ItemEntityResponse;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by administrador on 16/02/17.
 */
public class MainActivity extends AppCompatActivity implements DelegateItemAdapter, View.OnClickListener {

    private RecyclerView mRecyclerView ;
    private LinearLayoutManager mLinearLayoutManager;
    private AdapterItems mAdapterItems;
    private Context mContext;
    private ArrayList<Children> mItemsEntity = new ArrayList<Children>();
    private CoordinatorLayout mCoordinatorLayout;
    private ItemModel mItemModel;
    private String mModHash;
    private List<Children> mChildrens;
    private String mAfter;
    private String mBefore;
    private TextView mTextviewTitleRoot, mTextviewModhash;
    private ImageButton mImageButtonBefore, mImageButtonAfter;
    private boolean mIsAppOnline = true;
    private DataRoot mResponseData;

    //region METODOS DEL CICLO DE VIDA

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            Log.v(Config.LOG_TAG, "// ON CREATE (savedInstanceState : "+savedInstanceState+") //\n"+this);

            mContext = this;
            Helpers.setContexto(mContext);
            Helpers.setActivity(this);

            //Se crea la cola de peticiones
            VolleyQueue.createQueue(getApplicationContext());

            setContentView(R.layout.activity_main);
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            mCoordinatorLayout = (CoordinatorLayout)findViewById(R.id.coordinatorLayout);

            mRecyclerView = (RecyclerView)findViewById(R.id.rv_root);
            mRecyclerView.setHasFixedSize(true);

            mLinearLayoutManager = new LinearLayoutManager(mContext);
            mRecyclerView.setLayoutManager(mLinearLayoutManager);

            mTextviewTitleRoot = (TextView) findViewById(R.id.textview_root);
            mTextviewModhash = (TextView) findViewById(R.id.textview_modhash);

            mImageButtonBefore = (ImageButton) findViewById(R.id.imagebutton_before);
            mImageButtonAfter = (ImageButton) findViewById(R.id.imagebutton_after);
            mImageButtonBefore.setOnClickListener(this);
            mImageButtonAfter.setOnClickListener(this);

            mAdapterItems = new AdapterItems(MainActivity.this, mItemsEntity);
            mRecyclerView.setAdapter(mAdapterItems);

            useOnlineJSON();
        }

    //endregion

    //region METODOS DEL MENU

        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            // Inflate the menu; this adds items to the action bar if it is present.
            getMenuInflater().inflate(R.menu.menu_main, menu);
            return true;
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            // Handle action bar item clicks here. The action bar will
            // automatically handle clicks on the Home/Up button, so long
            // as you specify a parent activity in AndroidManifest.xml.
            int id = item.getItemId();

            if (id == R.id.action_refresh) {
                useOnlineJSON();
                return true;
            }

            return super.onOptionsItemSelected(item);
        }

    //endregion

    //region METODOS DEL VIEW ON CLICK LISTENER

        @Override
        public void onClick(View view) {
            int id = view.getId();
            switch (id){
                case R.id.imagebutton_before:
                    Toast.makeText(getApplicationContext(), mBefore.trim(), Toast.LENGTH_SHORT).show();
                    break;
                case R.id.imagebutton_after:
                    Toast.makeText(getApplicationContext(), mAfter.trim(), Toast.LENGTH_SHORT).show();
                    break;
            }
        }

    //endregion

    //region METODOS DEL DELEGATE ITEM ADAPTER

        @Override
        public void onItemClicked(Children entity) {
            Log.v(Config.LOG_TAG, "HICE CLICK EN --> " + entity);
            Intent intent = new Intent(MainActivity.this, DetailActivity.class);
            intent.putExtra("children", (Serializable) entity);
            startActivity(intent);
            this.overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left);
        }

    //endregion

    //region METODOS PRIVADOS

        /**
         * METODO QUE DIBUJA VALORES PARA EL RECYCLEVIEW CUANDO ESTE LEE DEL SERVICIO
         * @param childrens
         */
        private void drawChildrens(List<Children> childrens) {
            Log.v(Config.LOG_TAG, "// responseDataItemsView( childrens : "+childrens+" ) //"+
                    "\nMETODO QUE DIBUJA VALORES PARA EL RECYCLEVIEW CUANDO ESTE LEE DEL SERVICIO\n"+this);
            mAdapterItems = new AdapterItems(MainActivity.this, childrens);
            mAdapterItems.setDelegate(this);
            mRecyclerView.setAdapter(mAdapterItems);
        }

        /**
         * METODO QUE CARGA EL JSON ONLINE SI HAY INTERNET, DE LO CONTRARIO CARGA EL JSON EN ASSETS
         */
        private void useOnlineJSON() {
            Log.v(Config.LOG_TAG, "// useOnlineJSON() //\n"+this);
            if(Helpers.testConectionInternet(mContext))
            {
                Log.v(Config.LOG_TAG, "-- mIsAppOnline : "+mIsAppOnline+" HAY RED \n"+this);
                if(mIsAppOnline) {
                    syncItems();
                }else{
                    // ABRIMOS UN DIALOG CON EL MENSAJE QUE VIENE DEL SERVICIO
                    Helpers.customDialogMessage(getResources().getString(R.string.offline_dialog)).show();
                }
            }else{
                Log.v(Config.LOG_TAG, "-- mIsAppOnline : "+mIsAppOnline+" NO HAY RED \n"+this);
                //syncItems(mIsAppOnline);
            }
            Log.v(Config.LOG_TAG, "\n...");
        }

        /**
         * METODO QUE LEE UN SERVICIO Y CARGA EL CONTENIDO DE Config.BASE_URL_JSON;
         */
        private void syncItems() {
            Log.v(Config.LOG_TAG, "// syncItems() //"+"\nMETODO QUE LEE UN SERVICIO Y CARGA EL CONTENIDO DE " +
                    "Config.BASE_URL_JSON\n"+this);

            // MUESTRO UN CARGANDO
            //progressDialogLoadingShow();
            Helpers.progressDialogLoadingShow("",getResources().getString(R.string.wait_loading));

            mItemModel.getItems(mContext, new ItemModelInterface<ItemEntityResponse>() {

                @Override
                public void completeSuccess(ItemEntityResponse entity) {
                    Log.v(Config.LOG_TAG, "-- EXITO SINCRONIZACION \n" + entity+"\n"+this);
                    if(!entity.getKind().equals("")){
                        mTextviewTitleRoot.setText(entity.getKind().trim());
                    }else{
                        mTextviewTitleRoot.setText(getResources().getString(R.string.title));
                    }

                    if(entity.getDataRoot() != null){
                        mResponseData = entity.getDataRoot();
                        responseDataItemsView();
                    }

                    // CIERRO EL CARGANDO
                    // progressDialogClose();
                    Helpers.customProgressDialogClose();

                }

                @Override
                public void completeFail(String message) {
                    //Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();

                    Log.v(Config.LOG_TAG, "-- EXITO SINCRONIZACION \n" + message+"\n"+this);
                    // CIERRO EL CARGANDO
                    //progressDialogClose();
                    Helpers.customProgressDialogClose();
                    // ABRIMOS UN DIALOG CON EL MENSAJE QUE VIENE DEL SERVICIO
                    Helpers.customDialogMessage(message).show();
                    //SINCRONIZAMOS DESDE ASSETS
                    //syncItems(mIsAppOnline);

                }
            });

        }

        /**
         * METODO QUE DIBUJA LA PRIMERA FASE DEL CONTENIDO
         */
        private void responseDataItemsView() {
            Log.v(Config.LOG_TAG, "// responseDataItemsView() //"+
                    "\nMETODO QUE DIBUJA LA PRIMERA FASE DEL CONTENIDO\n"+this);

            if(mResponseData != null){
                mModHash = mResponseData.getModhash();
                if(!mModHash.equals("")){
                    mTextviewModhash.setText(mModHash.trim());
                }else{
                    mTextviewModhash.setText(getResources().getString(R.string.modhash));
                }
                mChildrens = mResponseData.getChildren();
                if(mChildrens.size() > 0){
                    drawChildrens(mChildrens);
                }else{
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.no_data), Toast.LENGTH_LONG).show();
                }
                mBefore = mResponseData.getBefore();
                if(mBefore != null){
                    mImageButtonBefore.setEnabled(true);
                    mImageButtonBefore.setImageResource(R.drawable.ic_chevron_left_white_48dp);
                }else{
                    mImageButtonBefore.setEnabled(false);
                    mImageButtonBefore.setImageResource(R.drawable.ic_chevron_left_grey_50_48dp);
                }
                mAfter = mResponseData.getAfter();
                if(mAfter != null){
                    mImageButtonAfter.setEnabled(true);
                    mImageButtonAfter.setImageResource(R.drawable.ic_chevron_right_white_48dp);
                }else{
                    mImageButtonAfter.setEnabled(false);
                    mImageButtonAfter.setImageResource(R.drawable.ic_chevron_right_grey_50_48dp);
                }



            }
        }

    //endregion

}
