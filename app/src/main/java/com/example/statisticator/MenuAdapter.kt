package com.example.statisticator

import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.statisticator.models.schema.MenuModel
import com.example.statisticator.models.schema.MenuStyle
import java.io.File

class MenuAdapter(private var model: MenuModel,
                  private val iconsFolder: File,
                  var delegate: MenuFragmentDelegate? = null
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class FullViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val title: TextView = v.findViewById(R.id.title)
        val icon: ImageView = v.findViewById(R.id.icon)
    }

    class ShortViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val icon: ImageView = v.findViewById(R.id.icon)
    }

    private val typeToLayout = mapOf(
        MenuStyle.Full to R.layout.full_menu_item,
        MenuStyle.Short to R.layout.short_menu_item
    )

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutId = typeToLayout[model.style]
            ?: throw Exception("Menu adapter failed due to menu with unsupported type")
        val v = LayoutInflater.from(viewGroup.context).inflate(layoutId, viewGroup, false)

        return when (model.style) {
            MenuStyle.Full -> FullViewHolder(v)
            MenuStyle.Short -> ShortViewHolder(v)
        }
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        var icon: ImageView? = null
        when (viewHolder) {
            is FullViewHolder -> {
                val fullHolder = viewHolder as? FullViewHolder ?: return
                fullHolder.title.text = model.items[position].title
                icon = fullHolder.icon
            }

            is ShortViewHolder -> {
                val shortHolder = viewHolder as? ShortViewHolder ?: return
                icon = shortHolder.icon
            }
        }

        val iconName = model.items[position].icon
        if (iconName != null) {
            val iconFile = File(iconsFolder, iconName)
            val bitmap = BitmapFactory.decodeFile(iconFile.path)
            icon?.setImageBitmap(bitmap)
        }

        viewHolder.itemView.setOnClickListener {
            val item = model.items[position]
            delegate?.itemClick(item)
        }
    }

    override fun getItemCount() = model.items.size

    fun updateModel(model: MenuModel) {
        this.model = model
        notifyDataSetChanged()
    }
}