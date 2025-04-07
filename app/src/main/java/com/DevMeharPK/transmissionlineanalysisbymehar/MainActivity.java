package com.DevMeharPK.transmissionlineanalysisbymehar;

import android.os.Bundle;
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

        // Voltage Dropdown
        String[] voltageOptions = {"132KV", "500KV"};
        ArrayAdapter<String> voltageAdapter = new ArrayAdapter<>(this, R.layout.dropdown_item, voltageOptions);
        voltageSpinner.setAdapter(voltageAdapter);
        voltageSpinner.setOnClickListener(v -> voltageSpinner.showDropDown());
        voltageSpinner.setOnItemClickListener((parent, view, position, id) ->
                Toast.makeText(this, "Voltage: " + voltageOptions[position], Toast.LENGTH_SHORT).show()
        );

        // Conductor Dropdown
        String[] conductorOptions = {"Alpha", "Beta"};
        ArrayAdapter<String> conductorAdapter = new ArrayAdapter<>(this, R.layout.dropdown_item, conductorOptions);
        codeOfConductorSpinner.setAdapter(conductorAdapter);
        codeOfConductorSpinner.setOnClickListener(v -> codeOfConductorSpinner.showDropDown());
        codeOfConductorSpinner.setOnItemClickListener((parent, view, position, id) -> {
            String selectedConductor = conductorOptions[position];
            Toast.makeText(this, "Conductor: " + selectedConductor, Toast.LENGTH_SHORT).show();

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

        // Weather Condition Dropdown
        String[] weatherOptions = {"Sunny", "Stormy"};
        ArrayAdapter<String> weatherAdapter = new ArrayAdapter<>(this, R.layout.dropdown_item, weatherOptions);
        weatherConditionSpinner.setAdapter(weatherAdapter);
        weatherConditionSpinner.setOnClickListener(v -> weatherConditionSpinner.showDropDown());
        weatherConditionSpinner.setOnItemClickListener((parent, view, position, id) ->
                Toast.makeText(this, "Weather: " + weatherOptions[position], Toast.LENGTH_SHORT).show()
        );

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
                    Toast.makeText(this, "Power Factor must be between 0 and 1!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, voltageSpinner.getText().toString() + " | " + powerValue + "MW | PF: " + pfValue
                                    + " | Line: " + lineLength + "km | Conductor: " + codeOfConductorSpinner.getText().toString()
                                    + " | Weather: " + weatherConditionSpinner.getText().toString()
                                    + " | Distances: AB=" + distAB + "m, BC=" + distBC + "m, AC=" + distAC + "m",
                            Toast.LENGTH_LONG).show();
                }
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Enter valid numbers for numeric fields!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean isFieldEmpty(TextInputEditText inputField, String fieldName) {
        if (inputField.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Please enter " + fieldName + "!", Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }

    private boolean isFieldEmpty(AutoCompleteTextView inputField, String fieldName) {
        if (inputField.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Please select " + fieldName + "!", Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }
}
