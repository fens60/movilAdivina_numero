package com.pmdm1.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private EditText minRange, maxRange, guessInput;
    private TextView hintText, attemptsText;
    private Button startButton, guessButton, abortButton, restartButton;

    private int targetNumber, attempts, min, max;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Inicializar
        minRange = findViewById(R.id.minRange);
        maxRange = findViewById(R.id.maxRange);
        guessInput = findViewById(R.id.guessInput);
        hintText = findViewById(R.id.hintText);
        attemptsText = findViewById(R.id.attemptsText);
        startButton = findViewById(R.id.startButton);
        guessButton = findViewById(R.id.guessButton);
        abortButton = findViewById(R.id.abortButton);
        restartButton = findViewById(R.id.restartButton);

        //boton de iniciar partida
        startButton.setOnClickListener(v -> startGame());

        //boton de realizar intento
        guessButton.setOnClickListener(v -> checkGuess());

        //boton de abortar partida
        abortButton.setOnClickListener(v -> abortGame());

        //boton de reiniciar partida
        restartButton.setOnClickListener(v -> resetGame());
    }

    private void startGame() {
        try {
            min = Integer.parseInt(minRange.getText().toString());
            max = Integer.parseInt(maxRange.getText().toString());
            if (min >= max) {
                Toast.makeText(this, "El rango no es válido.", Toast.LENGTH_SHORT).show();
                return;
            }

            targetNumber = new Random().nextInt((max - min) + 1) + min;
            attempts = 0;
            updateUI(true);
            Toast.makeText(this, "Juego iniciado. ¡Buena suerte!", Toast.LENGTH_SHORT).show();
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Por favor, ingrese valores válidos para el rango.", Toast.LENGTH_SHORT).show();
        }
    }

    private void checkGuess() {
        try {
            int guess = Integer.parseInt(guessInput.getText().toString());
            attempts++;
            attemptsText.setText("Intentos: " + attempts);

            if (guess < targetNumber) {
                hintText.setText("Pista: El número es mayor");
            } else if (guess > targetNumber) {
                hintText.setText("Pista: El número es menor");
            } else {
                hintText.setText("¡Felicidades! Adivinaste el número.");
                updateUI(false);
            }
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Por favor, ingrese un número válido.", Toast.LENGTH_SHORT).show();
        }
    }

    private void abortGame() {
        Toast.makeText(this, "Juego abortado.", Toast.LENGTH_SHORT).show();
        updateUI(false);
    }

    private void resetGame() {
        minRange.setText("");
        maxRange.setText("");
        guessInput.setText("");
        hintText.setText("Pista: ");
        attemptsText.setText("Intentos: 0");
        updateUI(false);
    }

    private void updateUI(boolean isGameActive) {
        minRange.setEnabled(!isGameActive);
        maxRange.setEnabled(!isGameActive);
        startButton.setEnabled(!isGameActive);
        guessInput.setEnabled(isGameActive);
        guessButton.setEnabled(isGameActive);
        abortButton.setEnabled(isGameActive);
        restartButton.setEnabled(!isGameActive);
    }
}