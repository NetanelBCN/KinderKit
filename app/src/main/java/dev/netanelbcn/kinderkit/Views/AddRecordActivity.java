package dev.netanelbcn.kinderkit.Views;

import android.os.Bundle;
import android.widget.DatePicker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.AppCompatEditText;

import com.google.android.material.button.MaterialButton;

import dev.netanelbcn.kinderkit.Models.ImmunizationRecord;
import dev.netanelbcn.kinderkit.Models.Kid;
import dev.netanelbcn.kinderkit.R;
import dev.netanelbcn.kinderkit.Uilities.DataManager;

public class AddRecordActivity extends AppCompatActivity {

    private AppCompatEditText AR_ET_vaccineName;
    private AppCompatEditText AR_ET_vaccinatorName;
    private AppCompatEditText AR_ET_HMOName;
    private AppCompatEditText AR_ET_creatorName;
    private DatePicker datePicker;
    private MaterialButton AR_MB_add_record;

    private int currentKidPosition;
    private DataManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_add_record);
        connectUI();
        getIntents();
        attachListeners();
    }
    private void getIntents() {
        currentKidPosition = getIntent().getIntExtra("kidPosition", -1);
        manager = DataManager.getInstance();
    }
    private void attachListeners() {
        AR_MB_add_record.setOnClickListener(v -> {
            String vaccineName = AR_ET_vaccineName.getText().toString();
            String vaccinatorName = AR_ET_vaccinatorName.getText() != null ? AR_ET_vaccinatorName.getText().toString() : "";
            String HMOName = AR_ET_HMOName.getText() != null ? AR_ET_HMOName.getText().toString() : "";
            String creatorName = AR_ET_creatorName.getText() != null ? AR_ET_creatorName.getText().toString() : "";
            if (!vaccineName.isEmpty()) {
                int day = datePicker.getDayOfMonth();
                int month = datePicker.getMonth();
                int year = datePicker.getYear();
                Kid kid = manager.getKids().get(currentKidPosition);
                int doseNum = kid.getDoseNumber(kid.getImmunizationRecords(), vaccineName);
                ImmunizationRecord iR = new ImmunizationRecord().setDoseNumber(doseNum).setVaccineName(vaccineName).setVaccinatorName(vaccinatorName).setHMOName(HMOName).setCreatorName(creatorName).setvdate(day, month, year);
                iR.initIrID();
                manager.addImmunizationRecord(iR, kid);
            }
            finish();
        });
    }

    private void connectUI() {
        AR_ET_vaccineName = findViewById(R.id.AR_ET_vaccineName);
        AR_ET_vaccinatorName = findViewById(R.id.AR_ET_vaccinatorName);
        AR_ET_HMOName = findViewById(R.id.AR_ET_HMOName);
        AR_ET_creatorName = findViewById(R.id.AR_ET_creatorName);
        datePicker = findViewById(R.id.datePicker);
        AR_MB_add_record = findViewById(R.id.AR_MB_add_record);
    }
}