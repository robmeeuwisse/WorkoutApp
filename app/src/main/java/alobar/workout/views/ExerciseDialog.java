package alobar.workout.views;


import android.app.Dialog;
import android.app.DialogFragment;
import android.content.ContentValues;
import android.os.Bundle;
import android.app.Fragment;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import alobar.util.LineBuilder;
import alobar.workout.R;
import alobar.workout.provider.DatabaseContract;

/**
 * A simple {@link Fragment} subclass.
 */
public class ExerciseDialog extends DialogFragment implements View.OnClickListener {

    private EditText nameEdit;
    private EditText weightEdit;

    public ExerciseDialog() {
        // Required empty public constructor
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog result = super.onCreateDialog(savedInstanceState);
        result.setTitle(R.string.exercise_dialog_title);
        return result;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View result = inflater.inflate(R.layout.fragment_exercise_dialog, container, false);
        nameEdit = (EditText) result.findViewById(R.id.nameEdit);
        weightEdit = (EditText) result.findViewById(R.id.weightEdit);
        result.findViewById(R.id.saveButton).setOnClickListener(this);
        result.findViewById(R.id.cancelButton).setOnClickListener(this);
        return result;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.saveButton:
                if (validate()) {
                    save();
                    getDialog().dismiss();
                }
                break;
            case R.id.cancelButton:
                getDialog().cancel();
                break;
            default:
                throw new UnsupportedOperationException();
        }
    }

    private void save() {
        String name = nameEdit.getText().toString().trim();
        double weight = Double.parseDouble(weightEdit.getText().toString().trim());

        ContentValues values = new ContentValues();
        values.put(DatabaseContract.Exercise.NAME, name);
        values.put(DatabaseContract.Exercise.WEIGHT, weight);
        getActivity().getContentResolver().insert(DatabaseContract.Exercise.CONTENT_URI, values);
    }

    private boolean validate() {
        LineBuilder result = new LineBuilder();
        String nameText = nameEdit.getText().toString().trim();
        if (TextUtils.isEmpty(nameText)) {
            result.appendLine("Name is required");
        }
        String weightText = weightEdit.getText().toString().trim();
        if (TextUtils.isEmpty(weightText)) {
            result.appendLine("Weight is required");
        }

        if (result.lenght() > 0) {
            Toast.makeText(getActivity(), result.toString(), Toast.LENGTH_LONG).show();
            return false;
        } else {
            return true;
        }
    }
}
