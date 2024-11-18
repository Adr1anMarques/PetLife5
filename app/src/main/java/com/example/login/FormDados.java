package com.example.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class FormDados extends AppCompatActivity {
    private TextView raca, idade, saude, nome, tosa, pagamento;
    String usuarioID;
    private Button bt_concluir;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_form_dados);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
       inciarlizarComponentes();

       bt_concluir.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent intent = new Intent(FormDados.this,FormInicio.class);
               startActivity(intent);
           }
       });

    }

    @Override
    protected void onStart() {
        super.onStart();

        usuarioID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        DocumentReference documentReference4 = db.collection("PAGAMENTO").document(usuarioID);
        DocumentReference documentReference3 = db.collection("TOSA").document(usuarioID);
        DocumentReference documentReference = db.collection("PETS").document(usuarioID);
        DocumentReference documentReference2 = db.collection("Usuarios").document(usuarioID);

        documentReference4.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if(value != null){
                    pagamento.setText(value.getString("pagamento"));
                }
            }
        });

        documentReference3.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if(value != null){
                    tosa.setText(value.getString("tosa"));
                }
            }
        });

        documentReference2.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if (value != null){
                    nome.setText(value.getString("nome"));
                }
            }
        });

        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                if (documentSnapshot != null){
                    raca.setText(documentSnapshot.getString("raça"));
                    idade.setText(documentSnapshot.getString("idade"));
                    saude.setText(documentSnapshot.getString("saude"));

                }

            }
        });
    }


    private void inciarlizarComponentes(){
        raca = findViewById(R.id.text_raca);
        idade = findViewById(R.id.text_idade);
        saude = findViewById(R.id.text_saude);
        nome = findViewById(R.id.text_nomeDono);
        tosa = findViewById(R.id.text_tosa);
        pagamento = findViewById(R.id.text_pagamento);
        bt_concluir = findViewById(R.id.bt_proximo);
    }
}