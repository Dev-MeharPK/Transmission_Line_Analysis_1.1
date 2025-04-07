package com.DevMeharPK.transmissionlineanalysisbymehar;

import android.os.Bundle;
import android.os.Handler;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity {

    private AutoCompleteTextView voltageSpinner, codeOfConductorSpinner, weatherConditionSpinner;
    private TextInputEditText powerInput, powerFactorInput, lengthOfLineInput;
    private TextInputEditText distanceABInput, distanceBCInput, distanceACInput;
    private TextInputEditText temperatureInput, pressureInput;
    private TextInputEditText conductorSpacingInput, weightOfConductorInput, spanInput, tensionInput;
    private TextView radiusValue, areaValue, resistanceValue;
    private Button calculateButton;

    private Toast currentToast = null; // Toast tracking variable

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize UI elements
        voltageSpinner = findViewById(R.id.voltageSpinner);
        codeOfConductorSpinner = findViewById(R.id.codeOfConductorSpinner);
        weatherConditionSpinner = findViewById(R.id.weatherConditionSpinner);
        powerInput = findViewById(R.id.powerInput);
        powerFactorInput = findViewById(R.id.powerFactorInput);
        lengthOfLineInput = findViewById(R.id.lengthOfLineInput);
        distanceABInput = findViewById(R.id.distanceABInput);
        distanceBCInput = findViewById(R.id.distanceBCInput);
        distanceACInput = findViewById(R.id.distanceACInput);
        temperatureInput = findViewById(R.id.temperatureInput);
        pressureInput = findViewById(R.id.pressureInput);
        conductorSpacingInput = findViewById(R.id.conductorSpacingInput);
        weightOfConductorInput = findViewById(R.id.weightOfConductorInput);
        spanInput = findViewById(R.id.spanInput);
        tensionInput = findViewById(R.id.tensionInput);
        calculateButton = findViewById(R.id.calculateButton);

        radiusValue = findViewById(R.id.radiusValue);
        areaValue = findViewById(R.id.areaValue);
        resistanceValue = findViewById(R.id.resistanceValue);

        // Setup dropdowns
        setupDropdown(voltageSpinner, new String[]{"132KV", "500KV"}, "Voltage");
        setupDropdown(weatherConditionSpinner, new String[]{"Sunny", "Stormy"}, "Weather");

        // Conductor Dropdown with value logic
        String[] conductorOptions = {"Alpha", "Beta"};
        ArrayAdapter<String> conductorAdapter = new ArrayAdapter<>(this, R.layout.dropdown_item, conductorOptions);
        codeOfConductorSpinner.setAdapter(conductorAdapter);
        codeOfConductorSpinner.setOnClickListener(v -> codeOfConductorSpinner.showDropDown());
        codeOfConductorSpinner.setOnItemClickListener((parent, view, position, id) -> {
            String selectedConductor = conductorOptions[position];
            showToast("Conductor: " + selectedConductor);  // Show toast here

            if (selectedConductor.equals("Alpha")) {
                radiusValue.setText("Radius: 1 cm");
                areaValue.setText("Area: 1 m²");
                resistanceValue.setText("Resistance: 1 Ω/km");
            } else if (selectedConductor.equals("Beta")) {
                radiusValue.setText("Radius: 2 cm");
                areaValue.setText("Area: 2 m²");
                resistanceValue.setText("Resistance: 2 Ω/km");
            }
        });

        // Handle Calculate button click
        calculateButton.setOnClickListener(v -> {
            if (isFieldEmpty(voltageSpinner, "Voltage")) return;
            if (isFieldEmpty(codeOfConductorSpinner, "Code of Conductor")) return;
            if (isFieldEmpty(weatherConditionSpinner, "Weather Condition")) return;
            if (isFieldEmpty(powerInput, "Power")) return;
            if (isFieldEmpty(powerFactorInput, "Power Factor")) return;
            if (isFieldEmpty(lengthOfLineInput, "Length of Line")) return;
            if (isFieldEmpty(distanceABInput, "Distance Between A and B")) return;
            if (isFieldEmpty(distanceBCInput, "Distance Between B and C")) return;
            if (isFieldEmpty(distanceACInput, "Distance Between A and C")) return;
            if (isFieldEmpty(temperatureInput, "Temperature")) return;
            if (isFieldEmpty(pressureInput, "Pressure")) return;
            if (isFieldEmpty(conductorSpacingInput, "Conductor Spacing")) return;
            if (isFieldEmpty(weightOfConductorInput, "Weight of Conductor")) return;
            if (isFieldEmpty(spanInput, "Span")) return;
            if (isFieldEmpty(tensionInput, "Tension")) return;

            try {
                double powerValue = Double.parseDouble(powerInput.getText().toString().trim());
                double pfValue = Double.parseDouble(powerFactorInput.getText().toString().trim());
                double lineLength = Double.parseDouble(lengthOfLineInput.getText().toString().trim());
                double distAB = Double.parseDouble(distanceABInput.getText().toString().trim());
                double distBC = Double.parseDouble(distanceBCInput.getText().toString().trim());
                double distAC = Double.parseDouble(distanceACInput.getText().toString().trim());

                if (pfValue < 0 || pfValue > 1) {
                    showToast("Power Factor must be between 0 and 1!");
                } else {
                    showToast(voltageSpinner.getText().toString() + " | " + powerValue + "MW | PF: " + pfValue
                            + " | Line: " + lineLength + "km | Conductor: " + codeOfConductorSpinner.getText().toString()
                            + " | Weather: " + weatherConditionSpinner.getText().toString()
                            + " | Distances: AB=" + distAB + "m, BC=" + distBC + "m, AC=" + distAC + "m");
                }
            } catch (NumberFormatException e) {
                showToast("Enter valid numbers for numeric fields!");
            }
        });
    }

    private void showToast(String message) {
        if (currentToast != null) {
            currentToast.cancel();  // Cancel the previous Toast
        }

        currentToast = Toast.makeText(this, message, Toast.LENGTH_SHORT);  // Default duration is SHORT
        currentToast.show();

        // Use a Handler to cancel the Toast earlier than the default duration
        new Handler().postDelayed(() -> {
            if (currentToast != null) {
                currentToast.cancel();
            }
        }, 1000);  // Custom duration (in milliseconds), 1000ms = 1 second
    }

    private void setupDropdown(AutoCompleteTextView dropdown, String[] options, String label) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.dropdown_item, options);
        dropdown.setAdapter(adapter);
        dropdown.setOnClickListener(v -> dropdown.showDropDown());  // Keep this so it works properly
        dropdown.setOnItemClickListener((parent, view, position, id) ->
                showToast(label + ": " + options[position])
        );
    }

    private boolean isFieldEmpty(TextInputEditText inputField, String fieldName) {
        if (inputField.getText().toString().trim().isEmpty()) {
            showToast("Please enter " + fieldName + "!");
            return true;
        }
        return false;
    }

    private boolean isFieldEmpty(AutoCompleteTextView inputField, String fieldName) {
        if (inputField.getText().toString().trim().isEmpty()) {
            showToast("Please select " + fieldName + "!");
            return true;
        }
        return false;
    }
}
