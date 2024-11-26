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

public class FormPagamento extends AppCompatActivity {
    private Button bt_concluirPedido;
    private RadioGroup rd_grupo2;
    String usuarioID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_form_pagamento);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        inciarlizarComponentes();

        rd_grupo2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton opcao = findViewById(i);
                String pagamento = opcao.getText().toString();

                FirebaseFirestore banco = FirebaseFirestore.getInstance();

                Map<String,Object> pagamentos = new HashMap<>();
                pagamentos.put("pagamento",pagamento);
                usuarioID = FirebaseAuth.getInstance().getCurrentUser().getUid();
                DocumentReference documentReference = banco.collection("PAGAMENTO").document(usuarioID);

                documentReference.set(pagamentos).addOnSuccessListener(new OnSuccessListener<Void>() {
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
        bt_concluirPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FormPagamento.this, FormInicio.class);
                startActivity(intent);
                finish();
            }
        });

    }
    private void inciarlizarComponentes(){
        bt_concluirPedido = findViewById(R.id.bt_concluir);
        rd_grupo2 = findViewById(R.id.radioGroup2);
    }
}