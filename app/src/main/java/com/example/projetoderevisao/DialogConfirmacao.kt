package com.example.projetoderevisao

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager

class DialogConfirmacao : DialogFragment() {

    private var listener : OnBtnListener? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity!!)
        builder.setTitle("Confirmação")

        builder.setPositiveButton("SIM") { dialogInterface, i ->
            if (listener != null) {
                listener!!.onConfirm()
            }
        }

        builder.setNegativeButton("NÃO") { dialogInterface, i -> dismiss() }

        val view = activity!!.layoutInflater.inflate(R.layout.dialog_confirmacao, null)
        builder.setView(view)
        return builder.create()
    }

    interface OnBtnListener {
        fun onConfirm()
    }

    companion object {
        fun show(fm: FragmentManager, listener: OnBtnListener) {
            val dialog = DialogConfirmacao()
            dialog.listener = listener
            dialog.show(fm, "textDialog")
        }
    }
}