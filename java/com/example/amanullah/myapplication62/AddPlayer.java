package com.example.amanullah.myapplication63;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

public class AddPlayer extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private String selectedHall;
    private String selectedRole;
    private OnFragmentInteractionListener mListener;
    private EditText firstName,lastName,price;
    private View inflatedView;
    private Button submitButton;
    private ImageView imageView;
    private Bundle savedInstance;
    private LayoutInflater inflater;
    private ViewGroup container2;
    private Spinner rolesSpinner, hallsSpinner;
    private MyProcessingDialog myProcessingDialog;
    final private int REQUEST_READ_EXTERNAL_STORAGE = 100,REQUEST_BROWSE_FOR_PHOTOS=101;

    public AddPlayer() {
        // Required empty public constructor
    }

    public static AddPlayer newInstance(String param1, String param2) {
        AddPlayer fragment = new AddPlayer();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.inflater = inflater;
        container2 = container;
        savedInstance = savedInstanceState;
        inflatedView = inflater.inflate(R.layout.fragment_add_player, container, false);
        init();
        setOnClickListeners();
        return inflatedView;
    }

    private void init(){
        firstName = inflatedView.findViewById(R.id.add_player_first_name);
        lastName = inflatedView.findViewById(R.id.add_player_last_name);
        price = inflatedView.findViewById(R.id.add_player_price);
        submitButton = inflatedView.findViewById(R.id.add_player_submit);
        rolesSpinner = inflatedView.findViewById(R.id.add_player_role_spinner);
        hallsSpinner=inflatedView.findViewById(R.id.add_player_hall_spinner);
        myProcessingDialog = new MyProcessingDialog(getContext(),"Please wait","Adding new player...");
    }

    //BEWARE: The following function contains nasty snippets.
    private void setOnClickListeners(){
        inflatedView.findViewById(R.id.add_player_submit).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Check for input errors, and if everything ok, submit the data to database.
                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                        if(checkFirstName()){
                            if(checkLastName()){
                                if(checkPrice()){
                                    if(checkRole()){
                                        if (checkHall()) {
                                            if(checkRoll()) {
                                                Player p = new Player(Integer.parseInt(getRoll()),getFirstName() + " " + getLastName(),
                                                        selectedHall,selectedRole,Integer.parseInt(getPrice()));
                                                writeNewPlayer(p);
                                            }
                                            else{
                                                new ShowMessage().showMessage(getContext(), "Error", "Inavlid roll", null);
                                            }
                                        }
                                        else{

                                        }
                                    }
                                    else{
                                        new ShowMessage().showMessage(getContext(), "Error", "Select a role", null);
                                    }
                                }
                                else{
                                    new ShowMessage().showMessage(getContext(), "Error", "Price error", null);
                                }
                            }
                            else{
                                new ShowMessage().showMessage(getContext(), "Error", "Last name error", null);
                            }
                        }
                        else{
                            new ShowMessage().showMessage(getContext(), "Error", "First name error", null);
                        }
                    }
                }
        );

        ArrayAdapter <CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),R.array.role_array,R.layout.support_simple_spinner_dropdown_item),
                adapter2 = ArrayAdapter.createFromResource(getContext(),R.array.halls_array,R.layout.support_simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        rolesSpinner.setAdapter(adapter);
        hallsSpinner.setAdapter(adapter2);

        rolesSpinner.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener(){

                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        selectedRole = new String(roles[position]);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        selectedRole = roles[0];
                    }
                }
        );
        hallsSpinner.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        selectedHall = new String(halls[position]);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        selectedHall = halls[0];
                    }
                }
        );
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
    private String getFirstName(){
        return firstName.getText().toString();
    }
    private String getLastName() {  return lastName.getText().toString(); }
    private String getPrice()   { return price.getText().toString(); }
    private String getRoll(){
        return ((EditText)inflatedView.findViewById(R.id.add_player_roll)).getText().toString();
    }
    private boolean checkFirstName(){
        String firstNameStr = getFirstName();
        if(!StringHandle.isNotNullAndEmpty(firstNameStr)){
            return false;
        }
        boolean result = true;

        for(int i = 0; i < firstNameStr.length(); i++){
            char ch = firstNameStr.charAt(i);
            if( !((ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z')) ){
                result = false;
            }
        }
        return result;
    }
    private boolean checkLastName(){
        String lastNameStr = getLastName();
        if(!StringHandle.isNotNullAndEmpty(lastNameStr)){
            return false;
        }
        boolean result = true;

        for(int i = 0; i < lastNameStr.length(); i++){
            char ch = lastNameStr.charAt(i);
            if( !((ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z')) ){
                result = false;
            }
        }
        return result;
    }
    private boolean checkPrice(){
        return StringHandle.isNotNullAndEmpty(getPrice());
    }
    private boolean checkHall(){
        if(selectedHall.equals(halls[0])){
            return false;
        }
        else return true;
    }
    private boolean checkRole(){
        return !selectedRole.equals(roles[0]);
    }
    private boolean checkRoll() {
        Integer roll;
        try{
            roll = Integer.parseInt(getRoll());
        }
        catch(Exception ex){
            return false;
        }
        if(roll >= 180701 && roll <= 1807120) return true;
        if(roll >= 170701 && roll <= 1707120) return true;
        if(roll >= 160701 && roll <= 1607120) return true;
        if(roll >= 150701 && roll <= 1507120) return true;
        else return false;
    }
    private void writeNewPlayer(Player p){
        myProcessingDialog.show();
        DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("players").push();
        p.setId(db.getKey());
        String playerJsonString = new Gson().toJson(p);
        db.setValue(playerJsonString).addOnCompleteListener(
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        firstName.setText("");
                        lastName.setText("");
                        price.setText("");
                        myProcessingDialog.dismiss();
                    }
                }
        );
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode == REQUEST_BROWSE_FOR_PHOTOS && resultCode == Activity.RESULT_OK){
            if(data == null){
                new ShowMessage().showMessage(getContext(), "Error", "An error occurred", null);
            }
            else {
                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                Cursor cursor = AdminHome.getContextOfApplication().getContentResolver().query(selectedImage,filePathColumn,null,null,null);
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String picturePath = cursor.getString(columnIndex);
                cursor.close();
                Bitmap bitmap = BitmapFactory.decodeFile(picturePath);
                Glide.with(getContext()).load(bitmap).apply(RequestOptions.circleCropTransform()).into((ImageView)inflatedView.findViewById(R.id.add_player_thumbnail));
            }
        }
        else {
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults){
        switch(requestCode) {
            case REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    startActivityForResult(intent, REQUEST_BROWSE_FOR_PHOTOS);
                } else {

                }
        }
        return;

    }
    final private String[] roles = {
            "Select a hall",
            "Batsman",
            "Batsman + WK",
            "Bowler",
            "All rounder",
            "Wicket Keeper"
    };
    private String[] halls = {
            "Select a role",
            "Khan Jahan Ali Hall",
            "Dr. M.A. Rashid Hall",
            "Bangabandhu Sheikh Mujibur Rahman Hall",
            "Fazlul Haque Hall",
            "Amar Ekushey Hall",
            "Lalan Shah Hall"
    };

}
