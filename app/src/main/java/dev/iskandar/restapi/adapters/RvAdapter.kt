package dev.iskandar.restapi.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import dev.iskandar.restapi.databinding.ItemRvBinding
import dev.iskandar.restapi.models.GetTodoResponse

class RvAdapter(private var list: ArrayList<GetTodoResponse>, var rvAction: RvAction) :
    Adapter<RvAdapter.Vh>() {

    inner class Vh(private var itemRvBinding: ItemRvBinding) : ViewHolder(itemRvBinding.root) {
        fun onBind(getToDoResponse: GetTodoResponse, position: Int) {
            itemRvBinding.apply {
                buttonMore.setOnClickListener {
                    rvAction.moreClick(getToDoResponse, position, buttonMore)
                }
                textViewSarlavha.text = getToDoResponse.sarlavha
                textViewBatafsil.text = getToDoResponse.batafsil
                textViewOxirgiMuddat.text = getToDoResponse.oxirgi_muddat
                textViewZarurlik.text = getToDoResponse.zarurlik
                textViewBajarildi.text = getToDoResponse.bajarildi.toString()
                textViewSana.text = getToDoResponse.sana
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemRvBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(list[position], position)
    }

    interface RvAction {
        fun moreClick(getToDoResponse: GetTodoResponse, position: Int, image: ImageView)
    }
}