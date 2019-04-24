package cn.wsgwz.basemodule.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import cn.wsgwz.basemodule.NetworkDataActivity
import cn.wsgwz.basemodule.R
import cn.wsgwz.basemodule.data.RequestData
import cn.wsgwz.basemodule.utilities.manager.NetworkDataManager

class NetworkDataAdapter(private val context: Context) :
    RecyclerView.Adapter<NetworkDataAdapter.ViewHolder>(), View.OnClickListener {

    private val layoutInflater = LayoutInflater.from(context)
    private val networkDataManager = NetworkDataManager.getInstance()



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(layoutInflater.inflate( R.layout.list_item_network_data, parent,false)).apply {
            parent_cl.setOnClickListener(this@NetworkDataAdapter)
        }
    }

    override fun getItemCount(): Int {
        return networkDataManager.getRequestData().size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        networkDataManager.getRequestData()[position].also { item ->
            holder.parent_cl.tag = item
            holder.simple_tv.text = item.uri.toString()
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.parent_cl -> {
                context.startActivity(Intent(context, NetworkDataActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    putExtra("requestData", v.tag as RequestData)
                })
            }
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val parent_cl = itemView.findViewById<ConstraintLayout>(R.id.parent_cl)
        val simple_tv = itemView.findViewById<TextView>(R.id.simple_tv)
    }

}