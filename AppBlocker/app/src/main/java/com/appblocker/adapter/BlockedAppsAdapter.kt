package com.appblocker.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.appblocker.databinding.ItemAppBinding
import com.appblocker.model.AppInfo

class BlockedAppsAdapter(
    private val apps: List<AppInfo>,
    private val onBlockToggle: (AppInfo, Boolean) -> Unit
) : RecyclerView.Adapter<BlockedAppsAdapter.AppViewHolder>() {

    inner class AppViewHolder(private val binding: ItemAppBinding) : 
        RecyclerView.ViewHolder(binding.root) {
        
        fun bind(appInfo: AppInfo) {
            binding.textAppName.text = appInfo.appName
            binding.textPackageName.text = appInfo.packageName
            binding.imageAppIcon.setImageDrawable(appInfo.icon)
            binding.switchBlock.isChecked = appInfo.isBlocked
            
            binding.switchBlock.setOnCheckedChangeListener { _, isChecked ->
                appInfo.isBlocked = isChecked
                onBlockToggle(appInfo, isChecked)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppViewHolder {
        val binding = ItemAppBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return AppViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AppViewHolder, position: Int) {
        holder.bind(apps[position])
    }

    override fun getItemCount() = apps.size
}
