package com.example.login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

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

public class FormTosa extends AppCompatActivity {
   private Button bt_salvar;
   private RadioGroup rd_grupo;
   String usuarioID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_form_tosa);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        iniciarComponentes();


        rd_grupo.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton opcao = findViewById(i);
                String tosa = opcao.getText().toString();
                FirebaseFirestore banco = FirebaseFirestore.getInstance();

                Map<String,Object> tosas = new HashMap<>();
                tosas.put("tosa",tosa);
                usuarioID = FirebaseAuth.getInstance().getCurrentUser().getUid();
                DocumentReference documentReference = banco.collection("TOSA").document(usuarioID);

                documentReference.set(tosas).addOnSuccessListener(new OnSuccessListener<Void>() {
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
        });


        bt_salvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FormTosa.this, FormPagamento.class);
                startActivity(intent);
                finish();

            }
        });
    }



    public void iniciarComponentes(){
        bt_salvar= findViewById(R.id.bt_concluir);
        rd_grupo = findViewById(R.id.radioGruop);
    }
}