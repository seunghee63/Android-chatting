package com.song2.chatting.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

@BindingAdapter("setProfileImage")
fun setProfileImage(view: ImageView, profile: String) {
    Glide.with(view.context)
        .load(profile)
        .transform(RoundedCorners(180))
        .transition(DrawableTransitionOptions.withCrossFade())
        .into(view)
}


