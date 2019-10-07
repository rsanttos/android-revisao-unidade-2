package com.example.projetoderevisao

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.dialog_cadastro_usuario.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadCredenciais()

        btnLogin.setOnClickListener {
            var newIntent = Intent(this, ListPosts::class.java)
            startActivity(newIntent)
        }

        btnNovoCadastro.setOnClickListener {
            DialogUsuario.show(supportFragmentManager, object : DialogUsuario.OnTextListener {
                override fun onSetText(email : String, senha : String) {
                    saveCredenciais(email, senha)
                    loadCredenciais()
                }
            })
        }
    }

    private fun saveCredenciais(email : String, senha : String){
        val credenciais = getSharedPreferences(NOME_FILE_CREDENCIAIS, 0)
        val edit = credenciais.edit()
        edit.putString(KEY_EMAIL, email.toString())
        edit.putString(KEY_SENHA, senha.toString())
        edit.commit()
    }

    private fun loadCredenciais(){
        val credenciais = getSharedPreferences(NOME_FILE_CREDENCIAIS, 0)
        if(credenciais.contains(KEY_EMAIL) && credenciais.contains(KEY_SENHA)){
            val email = credenciais.getString(KEY_EMAIL, null)
            val senha = credenciais.getString(KEY_SENHA, null)
            edtEmail.setText(email)
            edtSenha.setText(senha)
        }
    }

    companion object {
        val NOME_FILE_CREDENCIAIS = "credenciais.txt"
        val KEY_EMAIL = "email"
        val KEY_SENHA = "senha"
    }
}
