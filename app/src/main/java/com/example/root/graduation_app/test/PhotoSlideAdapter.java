package com.example.root.graduation_app.test;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.root.graduation_app.R;
import uk.co.senab.photoview.PhotoView;

public class PhotoSlideAdapter extends PagerAdapter {

    private Activity activity;

    private static final int[] sDrawables = {R.drawable.wallpaper, R.drawable.wallpaper, R.drawable.wallpaper,
            R.drawable.wallpaper, R.drawable.wallpaper, R.drawable.wallpaper};

    public PhotoSlideAdapter(Activity context) {
        this.activity = context;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        View view = LayoutInflater.from(activity).inflate(R.layout.activity_photoview, null);
//        PhotoView photoView = new PhotoView(container.getContext());
//        photoView.setImageResource(sDrawables[position]);
        PhotoView photoView = view.findViewById(R.id.photo_view);
//        photoView.setOnViewTapListener(new PhotoViewAttacher.OnViewTapListener() {
//            @Override
//            public void onViewTap(View view, float v, float v1) {
//                activity.finish();
//            }
//        });

//        Drawable drawable = (Drawable) mList.get(position);

        Glide.with(activity)
                .load(sDrawables[position])
                .into(photoView);
        container.addView(photoView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        return photoView;
    }

    @Override
    public int getCount() {
        return sDrawables.length;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
