package com.hp_admin.proyecto_pedido;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.Firebase;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

	TextInputEditText editTextEmail, editTextPassword;
	Button iniciar_sesion;

	TextView registrarse;

	FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		EdgeToEdge.enable(this);
		setContentView(R.layout.activity_main);
		editTextEmail = findViewById(R.id.emailEditText);
		editTextPassword = findViewById(R.id.passwordEditText);
		iniciar_sesion = findViewById(R.id.iniciar_sesion);
		registrarse = findViewById(R.id.registrarse);

		registrarse.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, Registrarse.class);
				startActivity(intent);
				finish();
			}
		});

		iniciar_sesion.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String email, password;
				email=String.valueOf(editTextEmail.getText());
				password=String.valueOf(editTextPassword.getText());

				if(TextUtils.isEmpty(email)){
					Toast.makeText(MainActivity.this, "Enter email", Toast.LENGTH_SHORT).show();
					return;
				}
				if(TextUtils.isEmpty(password)){
					Toast.makeText(MainActivity.this, "enter password", Toast.LENGTH_SHORT).show();
					return;
				}
				firebaseAuth.signInWithEmailAndPassword(email, password)
						.addOnCompleteListener(new OnCompleteListener<AuthResult>() {
							@Override
							public void onComplete(@NonNull Task<AuthResult> task) {
								if (task.isSuccessful()) {
									Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
									Intent intent = new Intent(MainActivity.this, Home.class);
									startActivity(intent);
									finish();
								}
								else{
									Toast.makeText(MainActivity.this, "Autenticación fallida", Toast.LENGTH_SHORT).show();
								}
							}
						});
			}
		});
		ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
			Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
			v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
			return insets;
		});
	}

}