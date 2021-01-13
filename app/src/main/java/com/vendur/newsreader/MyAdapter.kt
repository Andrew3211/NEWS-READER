package com.vendur.newsreader

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.vendur.newsreader.models.Article
import java.text.SimpleDateFormat
import java.util.*

class MyAdapter(private val articles : List<Article>,private val context : Context) : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {
    var onItemClickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item, parent, false)
        return MyViewHolder(view, onItemClickListener)
    }

    interface OnItemClickListener {
        fun onItemClick(view: View?, position: Int)
    }

    @SuppressLint("CheckResult")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var (source, title, description, _, urlToImage, publishedAt) = articles[position]
        val requestOptions = RequestOptions()
        //варианты загрузки изображения
        requestOptions.placeholder(ColorDrawable(Color.parseColor("#ECA4A6")))
        requestOptions.error(ColorDrawable(Color.parseColor("#ECA4A6")))
        requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL)
        requestOptions.centerCrop()
        requestOptions.timeout(3000)
        //Glide - это фреймворк для загрузки изображений
        if(urlToImage == null){
            urlToImage = "https://ziggard.ru/img/pechat-gazet.jpg";
        }
        Glide.with(context)
            .load(urlToImage)
            .apply(requestOptions)
            .listener(object : RequestListener<Drawable?> {
                override fun onLoadFailed(e: GlideException?, model: Any, target: Target<Drawable?>, isFirstResource: Boolean): Boolean {
                    holder.progressBar.visibility = View.GONE
                    return false
                }

                override fun onResourceReady(resource: Drawable?, model: Any, target: Target<Drawable?>, dataSource: DataSource, isFirstResource: Boolean): Boolean {
                    holder.progressBar.visibility = View.GONE
                    return false
                }
            })
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(holder.imageView)

        //To remove the name of the media (do not repeat the name)
        val lastIndexOfTiret : Int = title.lastIndexOf("-")
        if(lastIndexOfTiret > 0){
            holder.title.text = title.subSequence(0,lastIndexOfTiret)
        } else {
            holder.title.text = title
        }
        holder.desc.text = description
        holder.source.text = source.name
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.ENGLISH)
        val datePublish: Date ?= simpleDateFormat.parse(publishedAt)
        val newFormat = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.ROOT)
        @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
        publishedAt = newFormat.format(datePublish)
        holder.published_ad.text = publishedAt
    }

    override fun getItemCount(): Int {
        return articles.size
    }

    inner class MyViewHolder(itemView : View,onItemClickListener : OnItemClickListener?) : RecyclerView.ViewHolder(itemView), View.OnClickListener{
        var title: TextView
        var desc: TextView
        var published_ad: TextView
        var source: TextView
        var imageView: ImageView
        var progressBar: ProgressBar
        var onItemClickListener: OnItemClickListener?

        override fun onClick(v: View) {
            onItemClickListener?.onItemClick(v, adapterPosition)
        }

        init {
            itemView.setOnClickListener(this)
            title = itemView.findViewById(R.id.title)
            desc = itemView.findViewById(R.id.desc)
            published_ad = itemView.findViewById(R.id.publishedAt)
            source = itemView.findViewById(R.id.source)
            imageView = itemView.findViewById(R.id.img)
            progressBar = itemView.findViewById(R.id.progress_load_photo)
            this.onItemClickListener = onItemClickListener
        }
    }
}