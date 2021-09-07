package com.maciekgrzela.aplikacjaklubu;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.maciekgrzela.aplikacjaklubu.model.User;
import com.maciekgrzela.aplikacjaklubu.model.Worker;
import com.maciekgrzela.aplikacjaklubu.ui.home.HomeFragment;

import org.json.JSONArray;
import org.json.JSONObject;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class LoginFragment extends Fragment {

    private RequestQueue requestQueue;
    private TextView emailTextView;
    private TextView passwordTextView;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        return inflater.inflate(R.layout.login_fragment, container, false);
    }

    public void onViewCreated(@NonNull final View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle(R.string.login_fragment_toolbar_title);

        requestQueue = Volley.newRequestQueue(view.getContext());
        emailTextView = view.findViewById(R.id.email_login);
        passwordTextView = view.findViewById(R.id.password_login);


        view.findViewById(R.id.not_registered_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(LoginFragment.this)
                        .navigate(R.id.action_LoginFragment_to_Register);
            }
        });

        view.findViewById(R.id.button_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final boolean[] logged = {false};
                final List<User> userList = new ArrayList<User>();
                final List<Worker> workerList = new ArrayList<Worker>();
                JsonArrayRequest requestClient = new JsonArrayRequest(getString(R.string.api_url)+"/clients/read.php", new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        JSONObject jsonObject = null;
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                        for(int i = 0; i < response.length(); i++){
                            try {
                                jsonObject = response.getJSONObject(i);
                                User user = new User();
                                user.setIdentifier(Integer.parseInt(jsonObject.getString("client_ID")));
                                user.setFirstName(jsonObject.getString("first_name"));
                                user.setLastName(jsonObject.getString("last_name"));
                                user.setEmail(jsonObject.getString("email"));
                                user.setPassword(jsonObject.getString("user_password"));
                                user.setDateOfBirth(new SimpleDateFormat("yyyy-MM-dd").parse(jsonObject.getString("date_of_birth")));
                                user.setCreatedAt(LocalDateTime.parse(jsonObject.getString("create_date"), formatter));
                                userList.add(user);
                            }catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }

                        int loggedUserId = 0;
                        String loggedFirstName = "";
                        String loggedLastName = "";

                        for (User user: userList) {
                            if(user.getEmail().equals(emailTextView.getText().toString()) && user.getPassword().equals(MD5(passwordTextView.getText().toString()))){
                                logged[0] = true;
                                loggedUserId = user.getIdentifier();
                                loggedFirstName = user.getFirstName();
                                loggedLastName = user.getLastName();
                                break;
                            }
                        }
                        if(logged[0]){
                            openMainActivity(loggedUserId, loggedFirstName, loggedLastName);
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("RESPONSE ERROR", error.toString());
                    }
                });

                JsonArrayRequest requestWorker = new JsonArrayRequest(getString(R.string.api_url)+"/worker/read.php", new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        JSONObject jsonObject = null;
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                        for(int i = 0; i < response.length(); i++){
                            try {
                                jsonObject = response.getJSONObject(i);
                                Worker worker = new Worker();
                                worker.setIdentifier(Integer.parseInt(jsonObject.getString("worker_ID")));
                                worker.setFirstName(jsonObject.getString("first_name"));
                                worker.setLastName(jsonObject.getString("last_name"));
                                worker.setAge(Integer.parseInt(jsonObject.getString("age")));
                                worker.setNationality(jsonObject.getString("nationality"));
                                worker.setEmail(jsonObject.getString("mail"));
                                worker.setPassword(jsonObject.getString("worker_password"));
                                worker.setJournalist(Boolean.parseBoolean(jsonObject.getString("is_journalist")));
                                worker.setExecutive(Boolean.parseBoolean(jsonObject.getString("is_executive")));
                                worker.setStaff(Boolean.parseBoolean(jsonObject.getString("is_staff")));
                                worker.setDateOfBirth(new SimpleDateFormat("yyyy-MM-dd").parse(jsonObject.getString("worker_date_of_birth")));
                                worker.setCreatedAt(LocalDateTime.parse(jsonObject.getString("create_date"), formatter));
                                workerList.add(worker);
                            }catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }

                        int loggedUserId = 0;
                        String loggedFirstName = "";
                        String loggedLastName = "";

                        for (Worker worker: workerList) {
                            if(worker.getEmail().equals(emailTextView.getText().toString()) && worker.getPassword().equals(MD5(passwordTextView.getText().toString())) && worker.getIdentifier() != 0){
                                logged[0] = true;
                                loggedUserId = worker.getIdentifier();
                                loggedFirstName = worker.getFirstName();
                                loggedLastName = worker.getLastName();
                                break;
                            }
                        }
                        if(logged[0]){
                            openMainActivity(loggedUserId, loggedFirstName, loggedLastName);
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("RESPONSE ERROR", error.toString());
                    }
                });

                requestQueue = Volley.newRequestQueue(view.getContext());
                requestQueue.add(requestWorker);
                requestQueue.add(requestClient);
            }
        });

    }

    private void openMainActivity(int userid, String userFirstName, String userLastName){
        Intent intent = new Intent(getContext(), NewsDrawerActivity.class);
        intent.putExtra("userid", userid);
        intent.putExtra("userFirstName", userFirstName);
        intent.putExtra("userLastName", userLastName);
        startActivity(intent);
    }

    private String MD5(String string) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(StandardCharsets.UTF_8.encode(string));
            return String.format("%032x", new BigInteger(1, md5.digest()));
        }catch (Exception ex){
            return "";
        }
    }
}
