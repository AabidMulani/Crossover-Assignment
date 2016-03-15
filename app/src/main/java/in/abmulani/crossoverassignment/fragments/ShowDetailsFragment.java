package in.abmulani.crossoverassignment.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import in.abmulani.crossoverassignment.R;
import in.abmulani.crossoverassignment.databases.UserData;
import in.abmulani.crossoverassignment.utils.AppConstants;
import in.abmulani.crossoverassignment.widgets.CustomTextView;

/**
 * Created by aabid-personal on 3/15/16.
 */
public class ShowDetailsFragment extends android.support.v4.app.DialogFragment {

    @Bind(R.id.fullName)
    CustomTextView fullName;
    @Bind(R.id.phoneTextView)
    CustomTextView phoneTextView;
    @Bind(R.id.emailTextView)
    CustomTextView emailTextView;
    @Bind(R.id.bloodGroupTextView)
    CustomTextView bloodGroupTextView;
    @Bind(R.id.isDonorTextView)
    CustomTextView isDonorTextView;
    private UserData currentUserData;
    private View parentView;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        parentView = inflater.inflate(R.layout.dialog_show_details, container, false);
        ButterKnife.bind(this, parentView);
        return parentView;
    }

    public void setCurrentUserData(UserData currentUserData) {
        this.currentUserData = currentUserData;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (currentUserData != null) {
            fullName.setText(currentUserData.getFirstname() + " " + currentUserData.getLastname());
            emailTextView.setText(currentUserData.getEmail());
            phoneTextView.setText(currentUserData.getPhone());
            bloodGroupTextView.setText(currentUserData.getBloodGroup());
            if (currentUserData.isDonor()) {
                isDonorTextView.setText("DONOR");
                isDonorTextView.setTextColor(AppConstants.COLOR_DONOR);
            } else {
                isDonorTextView.setTextColor(AppConstants.COLOR_PATIENT);
                isDonorTextView.setText("PATIENT");
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick(R.id.buttonOkay)
    public void onOkayClicked() {
        dismiss();
    }
}
