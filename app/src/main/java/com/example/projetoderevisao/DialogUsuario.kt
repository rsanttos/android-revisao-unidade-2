package com.example.projetoderevisao

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager

class DialogUsuario : DialogFragment() {

    private var edtEmail : EditText? = null
    private var edtSenha : EditText? = null

    private var listener : OnTextListener? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity!!)
        builder.setTitle("Cadastrar novo usuário")

        builder.setPositiveButton("OK") { dialogInterface, i ->
            if (listener != null) {
                val email = edtEmail!!.text.toString()
                val senha = edtSenha!!.text.toString()
                listener!!.onSetText(email, senha)
            }
        }

        builder.setNegativeButton("Cancelar") { dialogInterface, i -> dismiss() }

        val view = activity!!.layoutInflater.inflate(R.layout.dialog_cadastro_usuario, null)
        edtEmail = view.findViewById(R.id.edtEmailNovoUsuario)
        edtSenha = view.findViewById(R.id.edtSenhaNovoUsuario)
        builder.setView(view)
        return builder.create()
    }

    interface OnTextListener {
        fun onSetText(email: String, senha: String)
    }

    companion object {
        fun show(fm: FragmentManager, listener: OnTextListener) {
            val dialog = DialogUsuario()
            dialog.listener = listener
            dialog.show(fm, "textDialog")
        }
    }
}