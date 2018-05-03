package com.example.ndh.webservice;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.ArrayList;

import io.socket.client.IO;
import io.socket.emitter.Emitter;

public class MainActivity extends AppCompatActivity {
    public static String userId;
    String key;
    private Toolbar mainToolbar;
    ListView lv;
    UserAdapter userAdapter;
    ArrayList<User> userArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv = findViewById(R.id.lvUsers);

        Bundle bundle = getIntent().getBundleExtra("USERBUNDLE");
        userId = bundle.getString("ID");
        boolean sex = bundle.getBoolean("SEX");
        String username = bundle.getString("USERNAME");
        key = bundle.getString("KEY");


        JSONObject idKey = new JSONObject();
        try {
            idKey.put("ID",userId);
            idKey.put("KEY",key);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        try {
            Public.pSocket = IO.socket(Public.root);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        Public.pSocket.connect();


        Public.pSocket.emit("YEU_CAU_LIEN_KET_TAI_KHOAN",idKey);
        Public.pSocket.on("TRA_LOI_YEU_CAU_USER_LIST",LoadUser);
        //Toolbars
        mainToolbar=findViewById(R.id.toolbarMain);
        setSupportActionBar(mainToolbar);
        getSupportActionBar().setTitle("Users");
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        userArrayList = new ArrayList<>();

        userAdapter = new UserAdapter(MainActivity.this,R.layout.custom_listview_user,userArrayList);
        lv.setAdapter(userAdapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent chatInten = new Intent(MainActivity.this,ChatActivity.class);
                String name = userArrayList.get(i).getName();
                chatInten.putExtra("itemname",name);
                chatInten.putExtra("itemId",userArrayList.get(i).getId());
                chatInten.putExtra("mKey",key);
                chatInten.putExtra("mId",userId);
                startActivity(chatInten);
            }
        });
    }

    private Emitter.Listener LoadUser = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    //[{"ID":"0","USERNAME":"Chat Chung","SEX":true},{"ID":"1523609369286","USERNAME":"R27","SEX":false}]
                    try {
                        JSONObject object= new JSONObject((String) args[0]);
                        JSONArray jsonArray=object.getJSONArray("list");

                        Log.d("kqcc","sasa"+jsonArray.toString());
                        userArrayList.clear();
                        for(int i = 0 ;i<jsonArray.length();i++){

                            try {
                                JSONObject o = jsonArray.getJSONObject(i);
                                if(!userId.equals(o.getString("ID"))){
                                    userArrayList.add(new User(o.getString("ID"),o.getString("USERNAME"),o.getBoolean("SEX")));
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        userAdapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            });
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
      getMenuInflater().inflate(R.menu.loguot,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.menuLogout){
            Public.pSocket.disconnect();
            Intent intent = new Intent(MainActivity.this,StartActivity.class);
            startActivity(intent);
        }
        if(item.getItemId()== R.id.menuChangePass){
            Intent intent = new Intent(MainActivity.this,ChangePass.class);
            intent.putExtra("mKey",key);
            intent.putExtra("mId",userId);
            startActivity(intent);
        }
        return true;
    }
}
