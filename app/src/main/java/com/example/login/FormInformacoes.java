package com.example.login;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class FormInformacoes extends AppCompatActivity {
    private Button bt_salvar;
    private EditText edit_raca, edit_idade, edit_saude;
    String usuarioID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_form_informacoes);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        iniciarComponentes();
        bt_salvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SalvarDados();
            }
        });
    }
    public void SalvarDados(){
        String raca = edit_raca.getText().toString();
        String idade = edit_idade.getText().toString();
        String saude = edit_saude.getText().toString();

        FirebaseFirestore banco = FirebaseFirestore.getInstance();

        Map<String,Object> usuarios = new HashMap<>();
        usuarios.put("raça",raca);
        usuarios.put("idade",idade);
        usuarios.put("saude",saude);

        usuarioID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DocumentReference documentReference = banco.collection("PETS").document(usuarioID);
        documentReference.set(usuarios).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d("banco","Dados salvos com SUCESSO");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("banco_error", "Erro ao Salvar"+ e.toString());
                    }
                });
    }
    public void iniciarComponentes(){
        bt_salvar= findViewById(R.id.bt_infPet);
        edit_raca = findViewById(R.id.edit_raca);
        edit_idade = findViewById(R.id.edit_idade);
        edit_saude = findViewById(R.id.edit_saude);
    }
}

