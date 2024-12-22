package br.edu.ifsp.dmo.sitesfavoritos.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.view.menu.MenuView.ItemView
import androidx.recyclerview.widget.RecyclerView
import br.edu.ifsp.dmo.sitesfavoritos.R
import br.edu.ifsp.dmo.sitesfavoritos.databinding.ItemViewBinding
import br.edu.ifsp.dmo.sitesfavoritos.model.entity.Site
import br.edu.ifsp.dmo.sitesfavoritos.ui.listener.SiteItemClickListener

class SiteAdapter(val context: Context, val dataset: List<Site>, val listener: SiteItemClickListener) : RecyclerView.Adapter<SiteAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_view,
            parent,
            false
        )

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = dataset[position]
        holder.binding.textViewApelido.setText(item.apelido)
        holder.binding.textViewUrl.setText(item.url)

        if (item.favorito) {
            holder.binding.imgStar.setColorFilter(context.getColor(R.color.yellow))
        }
        else {
            holder.binding.imgStar.setColorFilter(context.getColor(R.color.gray))
        }

        holder.binding.layoutItem.setOnClickListener{listener.clickSiteItem(position)}
        holder.binding.imgStar.setOnClickListener{listener.clickFavoriteSiteItem(position)}
    }

    override fun getItemCount() = dataset.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding: ItemViewBinding = ItemViewBinding.bind(view)
    }
}