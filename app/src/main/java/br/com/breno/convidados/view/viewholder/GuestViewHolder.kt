package br.com.breno.convidados.view.viewholder

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.breno.convidados.R
import br.com.breno.convidados.service.model.GuestModel
import br.com.breno.convidados.view.listener.GuestListener
import kotlinx.android.synthetic.main.row_guest.view.*

class GuestViewHolder(itemView : View, private val listener : GuestListener) : RecyclerView.ViewHolder(itemView) {

    fun bind(guest: GuestModel) {
        val txtName = itemView.findViewById<TextView>(R.id.txtName)
        txtName.text = guest.name

        txtName.setOnClickListener {
            listener.onClick(guest.id)
        }
    }
}