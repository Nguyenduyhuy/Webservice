package com.example.ndh.webservice;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import io.socket.emitter.Emitter;

public class ChatActivity extends AppCompatActivity {
    Toolbar toolbarChat;

    EditText edtInputChat;
    ImageButton btnSendChat;
    public static String mId;
    public static String itemName;
    //Recycler
    RecyclerView recyclerChat;
    ChatAdapter chatAdapter;
    LinearLayoutManager layoutManager;
    ArrayList<Message>messageArrayList=new ArrayList<>();
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        edtInputChat=findViewById(R.id.edtInputChat);
        btnSendChat=findViewById(R.id.btnSendChat);

        itemName = getIntent().getStringExtra("itemname");
        final String itemJd = getIntent().getStringExtra("itemId");
        final String mKey = getIntent().getStringExtra("mKey");
         mId =getIntent().getStringExtra("mId");
        toolbarChat=findViewById(R.id.toolbarChat);
        setSupportActionBar(toolbarChat);
        getSupportActionBar().setTitle(itemName);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        //Recycler
        recyclerChat=findViewById(R.id.recyclerChat);
        chatAdapter=new ChatAdapter(messageArrayList,itemName,mId);
        layoutManager = new LinearLayoutManager(this);
        recyclerChat.setHasFixedSize(true);
        recyclerChat.setLayoutManager(layoutManager);
        recyclerChat.setAdapter(chatAdapter);


        btnSendChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String inputMessage = edtInputChat.getText().toString().trim();
                if(!inputMessage.isEmpty()){
                    JSONObject o = new JSONObject();
                    try {
                        o.put("IDSEND",mId);
                        o.put("IDRECEIVE",itemJd);
                        o.put("CONTENT",inputMessage);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
//                    Log.d("ooo",o.toString());
//                    messageArrayList.add(new Message(inputMessage,mId));
//                    chatAdapter.notifyDataSetChanged();
                    Public.pSocket.connect();
                    Public.pSocket.emit("YEU_CAU_CHAT_TRONG_ROOM",o);

                    edtInputChat.setText("");

                }
            }
        });
        Public.pSocket.on("TIN_NHAN",loadMessage);
        RequestQueue queue = Volley.newRequestQueue(ChatActivity.this);
        StringRequest request = new StringRequest(Request.Method.POST, Public.urlGetInfo, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object=new JSONObject(response);
                    JSONArray jsonArray=object.getJSONArray("list");
                    messageArrayList.clear();
                    for(int i = 0 ;i<jsonArray.length();i++){

                        try {
                            JSONObject o = jsonArray.getJSONObject(i);
                            Log.d("aaa",o.getString("CONTENT"));
                            messageArrayList.add(new Message(o.getString("CONTENT"),o.getString("IDSEND")));
                            recyclerChat.scrollToPosition(messageArrayList.size()-1);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    chatAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(ChatActivity.this, "my error :"+error, Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> map = new HashMap<String, String>();
                map.put("IDSEND",mId);
                map.put("IDRECEIVE",itemJd);
                map.put("KEY",mKey);

                return map;
            }
        };
        queue.add(request);


    }
    private Emitter.Listener loadMessage=new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject o= null;
                    try {
                        o = new JSONObject((String) args[0]);
                        messageArrayList.add(new Message(o.getString("CONTENT"),o.getString("IDSEND")));
                        recyclerChat.scrollToPosition(messageArrayList.size()-1);

                        chatAdapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            });
        }
    };
}
