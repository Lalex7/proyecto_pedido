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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Registrarse extends AppCompatActivity {


	TextInputEditText editTextEmail, editTextPassword;
	Button sign_up;

	TextView sign_in;

	FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		EdgeToEdge.enable(this);
		setContentView(R.layout.activity_registrarse);

		editTextEmail = findViewById(R.id.email);
		editTextPassword = findViewById(R.id.password);
		sign_in = findViewById(R.id.sign_in);
		sign_up = findViewById(R.id.sign_up);

		sign_in.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Registrarse.this, MainActivity.class);
				startActivity(intent);
				finish();
			}
		});
		sign_up.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String email, password;
				email=String.valueOf(editTextEmail.getText());
				password=String.valueOf(editTextPassword.getText());

				if(TextUtils.isEmpty(email)){
					Toast.makeText(Registrarse.this, "Enter email", Toast.LENGTH_SHORT).show();
					return;
				}
				if(TextUtils.isEmpty(password)){
					Toast.makeText(Registrarse.this, "enter password", Toast.LENGTH_SHORT).show();
					return;
				}
				firebaseAuth.createUserWithEmailAndPassword(email, password)
						.addOnCompleteListener(new OnCompleteListener<AuthResult>() {
							@Override
							public void onComplete(@NonNull Task<AuthResult> task) {
								if(task.isSuccessful()){
									Toast.makeText(Registrarse.this, "Registro exitoso", Toast.LENGTH_SHORT).show();
									Intent intent = new Intent(Registrarse.this, MainActivity.class);
									finish();
								}
								else{
									Toast.makeText(Registrarse.this, "Registro Fallido", Toast.LENGTH_SHORT).show();
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