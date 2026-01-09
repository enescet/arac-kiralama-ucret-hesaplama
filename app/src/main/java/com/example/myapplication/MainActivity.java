package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    EditText etDays;
    RadioGroup rgCarClass, rgTransmission;
    RadioButton rbEco, rbVip, rbSuv, rbManual, rbAutomatic;
    CheckBox cbDriver, cbInsurance, cbGps;
    Button btnCalculate;
    TextView tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        etDays = findViewById(R.id.etDays);

        rgCarClass = findViewById(R.id.rgCarClass);
        rgTransmission = findViewById(R.id.rgTransmission);

        rbEco = findViewById(R.id.rbEco);
        rbVip = findViewById(R.id.rbVip);
        rbSuv = findViewById(R.id.rbSuv);

        rbManual = findViewById(R.id.rbManual);
        rbAutomatic = findViewById(R.id.rbAutomatic);

        cbDriver = findViewById(R.id.cbDriver);
        cbInsurance = findViewById(R.id.cbInsurance);
        cbGps = findViewById(R.id.cbGps);

        btnCalculate = findViewById(R.id.btnCalculate);
        tvResult = findViewById(R.id.tvResult);

        // Varsayılan seçimler
        rbEco.setChecked(true);
        rbManual.setChecked(true);

        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calculatePrice();
            }
        });
    }

    private void calculatePrice() {
        String dayString = etDays.getText().toString().trim();

        if (dayString.isEmpty()) {
            Toast.makeText(this, "Lütfen gün sayısı giriniz!", Toast.LENGTH_SHORT).show();
            tvResult.setText("Hata: Gün sayısı boş olamaz.");
            return;
        }

        int days;
        try {
            days = Integer.parseInt(dayString);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Geçersiz sayı girdiniz!", Toast.LENGTH_SHORT).show();
            tvResult.setText("Hata: Geçersiz sayı.");
            return;
        }

        if (days <= 0) {
            Toast.makeText(this, "Gün sayısı 1 veya daha büyük olmalı!", Toast.LENGTH_SHORT).show();
            tvResult.setText("Hata: Geçersiz gün sayısı.");
            return;
        }

        // 1) Araç sınıfına göre taban günlük ücret
        int baseDailyPrice;
        if (rbEco.isChecked()) {
            baseDailyPrice = 300;
        } else if (rbVip.isChecked()) {
            baseDailyPrice = 450;
        } else {
            baseDailyPrice = 550; // SUV
        }

        // 2) Vites türüne göre ek ücret
        int transmissionDaily = 0;
        if (rbAutomatic.isChecked()) {
            transmissionDaily = 50;
        }

        // 3) Ek hizmetler (günlük)
        int extrasDaily = 0;
        if (cbDriver.isChecked()) extrasDaily += 200;
        if (cbInsurance.isChecked()) extrasDaily += 80;
        if (cbGps.isChecked()) extrasDaily += 30;

        int dailyTotal = baseDailyPrice + transmissionDaily + extrasDaily;
        int totalPrice = dailyTotal * days;

        tvResult.setText("Günlük Ücret: " + dailyTotal + "₺\nToplam Tutar: " + totalPrice + "₺");
    }
}
