package in.abmulani.crossoverassignment.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Spinner;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import in.abmulani.crossoverassignment.R;
import in.abmulani.crossoverassignment.databases.UserData;
import in.abmulani.crossoverassignment.interfaces.MapInteractionListener;
import in.abmulani.crossoverassignment.widgets.CustomEditText;

public class AddNewUserDetails extends DialogFragment {

    @Bind(R.id.firstNameEditText)
    CustomEditText firstNameEditText;
    @Bind(R.id.fisrtNameTextInputLayout)
    TextInputLayout fisrtNameTextInputLayout;
    @Bind(R.id.lastNameEditText)
    CustomEditText lastNameEditText;
    @Bind(R.id.lastNameTextInputLayout)
    TextInputLayout lastNameTextInputLayout;
    @Bind(R.id.emailEditText)
    CustomEditText emailEditText;
    @Bind(R.id.emailTextInputLayout)
    TextInputLayout emailTextInputLayout;
    @Bind(R.id.phoneEditText)
    CustomEditText phoneEditText;
    @Bind(R.id.phoneTextInputLayout)
    TextInputLayout phoneTextInputLayout;
    @Bind(R.id.addBloodGroupEditText)
    CustomEditText addBloodGroupEditText;
    @Bind(R.id.addBloodGroupTextViewInput)
    TextInputLayout addBloodGroupTextViewInput;
    @Bind(R.id.userTypeSpinner)
    Spinner userTypeSpinner;


    private double latitude;
    private double longitude;
    String firstname;
    String lastname;
    String email;
    String phone;
    String bloodGroup;
    boolean donor;
    private MapInteractionListener mapInteractionListener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);

        // request a window without the title
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View parentView = inflater.inflate(R.layout.dialog_add_data, container, false);
        ButterKnife.bind(this, parentView);
        return parentView;
    }


    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setMapInteractionListener(MapInteractionListener mapInteractionListener) {
        this.mapInteractionListener = mapInteractionListener;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private boolean doValidation() {

        if (!validateFirstName(firstNameEditText.getText().toString().trim())) {
            firstNameEditText.setError("Enter First Name");
            return false;
        }

        if (!validateLastName(lastNameEditText.getText().toString().trim())) {
            lastNameEditText.setError("Enter Last Name");
            return false;
        }

        if (!validateEmail(emailEditText.getText().toString().trim())) {
            emailEditText.setError("Enter Valid Email");
            return false;
        }

        if (!validatePhone(phoneEditText.getText().toString().trim())) {
            phoneEditText.setError("Enter Phone");
            return false;
        }

        if (!validateBloodGroup(addBloodGroupEditText.getText().toString().trim())) {
            addBloodGroupEditText.setError("Enter Blood Group");
            return false;
        }

        firstname = firstNameEditText.getText().toString().trim();
        lastname = lastNameEditText.getText().toString().trim();
        email = emailEditText.getText().toString().trim();
        phone = phoneEditText.getText().toString().trim();
        bloodGroup = addBloodGroupEditText.getText().toString().trim();
        donor = userTypeSpinner.getSelectedItemPosition() == 0;
        return true;
    }


    public boolean validateFirstName(String firstname) {
        return firstname.length() != 0;
    }


    public boolean validateLastName(String lastname) {
        return lastname.length() != 0;
    }

    public boolean validatePhone(String phone) {
        return phone.length() != 0;
    }

    public boolean validateBloodGroup(String bloodGroup) {
        return bloodGroup.length() != 0;
    }

    public boolean validateEmail(String email) {
        return !(email.length() == 0 || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick(R.id.saveButton)
    public void onSaveClicked() {
        if (doValidation()) {
            UserData.addThisData(firstname, lastname, email, phone, bloodGroup, donor, latitude, longitude);
            mapInteractionListener.onNewDataAdded();
            dismiss();
        }
    }


}
