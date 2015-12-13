package media_sci.com.utility;

import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import media_sci.com.shaklak_aklak.R;

public class CustomImageLoader {
    private static DisplayImageOptions options;
    private static CustomImageLoader CUSTOM_IMAGE_LOADER;
    private static ImageLoader currentImageLoader;

    public CustomImageLoader() {
        super();
    }

    public static CustomImageLoader getInstance() {
        if (CUSTOM_IMAGE_LOADER == null) {
            CUSTOM_IMAGE_LOADER = new CustomImageLoader();
        }
        return CUSTOM_IMAGE_LOADER;
    }

    public void loadImage(String imgURL, final ImageView currentImageView, ImageLoadingListener currentListner) {
        Log.d("Image URL", imgURL);
        if (options == null) {

            options = new DisplayImageOptions.Builder()
                    .showImageOnLoading(R.drawable.logo).showImageOnFail(R.drawable.logo)
                    .resetViewBeforeLoading(false).cacheInMemory(true).cacheOnDisk(true).imageScaleType(ImageScaleType.EXACTLY).bitmapConfig(Bitmap.Config.RGB_565).considerExifParams(false)
                    .displayer(new FadeInBitmapDisplayer(300))
                    .build();
//			options = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.splash_logo).showImageOnFail(imageOnFail).resetViewBeforeLoading(false).cacheOnDisk(true).imageScaleType(ImageScaleType.EXACTLY).bitmapConfig(Bitmap.Config.RGB_565).considerExifParams(true).displayer(new FadeInBitmapDisplayer(300)).build();
            // options = new
            // DisplayImageOptions.Builder().showImageOnLoading(R.drawable.ic_launcher).showImageForEmptyUri(R.drawable.ic_empty).showImageOnFail(R.drawable.ic_error).cacheInMemory(true).cacheOnDisk(true).considerExifParams(true).bitmapConfig(Bitmap.Config.RGB_565).build();

        }

        if (currentImageLoader == null) {
            currentImageLoader = ImageLoader.getInstance();
            currentImageLoader.init(ImageLoaderConfiguration.createDefault(currentImageView.getContext()));
        }
//		if (imageWidth != 0) {
//			currentImageView.getLayoutParams().width = imageWidth;
//			currentImageView.getLayoutParams().height = imageHeight;
//			currentImageView.setMaxWidth(imageWidth);
//			currentImageView.setMaxHeight(imageHeight);
//		}
        if (currentListner != null) {
            currentImageLoader.displayImage(imgURL, currentImageView, options, currentListner);

        } else {
            currentImageLoader.displayImage(imgURL, currentImageView, options, new ImageLoadingListener() {

                @Override
                public void onLoadingStarted(String arg0, View arg1) {
                    // TODO Auto-generated method stub

                }

                @Override
                public void onLoadingFailed(String arg0, View arg1, FailReason arg2) {
                    // TODO Auto-generated method stub

                }

                @Override
                public void onLoadingComplete(String arg0, View arg1, Bitmap arg2) {
                    currentImageView.invalidate();

                }

                @Override
                public void onLoadingCancelled(String arg0, View arg1) {
                    // TODO Auto-generated method stub

                }
            });
        }
    }

}
