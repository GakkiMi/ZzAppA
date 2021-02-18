package com.huadingcloudpackage.www.adapter;

import android.os.Build;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.huadingcloudpackage.www.R;
import com.huadingcloudpackage.www.util.GlideUtils;
import com.luck.picture.lib.entity.LocalMedia;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ImageAddAdapter extends BaseQuickAdapter<LocalMedia, BaseViewHolder> {
    public static final int TYPE_CAMERA = 1;
    public static final int TYPE_PICTURE = 2;
    private int SelectNum = 9;

    public ImageAddAdapter(int layoutResId, @Nullable List<LocalMedia> data) {
        super(layoutResId, data);
    }


    public ImageAddAdapter(int layoutResId, int SelectNum) {
        super(layoutResId);
        this.SelectNum = SelectNum;
    }

    public ImageAddAdapter(int layoutResId) {
        super(layoutResId);

    }

    @Override
    protected void convert(BaseViewHolder helper, LocalMedia item) {
        int position=helper.getLayoutPosition();

        if (getItemViewType(helper.getLayoutPosition()) == TYPE_CAMERA) {
            ((ImageView) helper.getView(R.id.item_image_upload_iv_show)).setImageResource(R.mipmap.icon_img_add);
            helper.getView(R.id.item_image_upload_iv_delete).setVisibility(View.INVISIBLE);
        } else {
            if (TextUtils.isEmpty(item.getCompressPath()) && TextUtils.isEmpty(item.getCompressPath())) {
                if (!TextUtils.isEmpty(item.getPath())) {
                    GlideUtils.showGildeImg(getContext(), item.getPath(), (ImageView) helper.getView(R.id.item_image_upload_iv_show));
                    helper.getView(R.id.item_image_upload_iv_delete).setVisibility(View.VISIBLE);
                } else {
                    helper.setImageResource(R.id.item_image_upload_iv_show, R.mipmap.icon_img_add);
                    helper.getView(R.id.item_image_upload_iv_delete).setVisibility(View.GONE);
                }
            } else {
                if (!TextUtils.isEmpty(item.getPath()) && item.getPath().contains("http")) {
                    GlideUtils.showGildeImg(getContext(), item.getPath(), (ImageView) helper.getView(R.id.item_image_upload_iv_show));
                } else {
                    Glide.with(helper.itemView.getContext()).load(new File(item.getCompressPath())).into((ImageView) helper.getView(R.id.item_image_upload_iv_show));
                }
                helper.getView(R.id.item_image_upload_iv_delete).setVisibility(View.VISIBLE);
            }
        }
        helper.getView(R.id.item_image_upload_iv_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData().remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, getData().size() - 1);
            }
        });

    }

    private boolean isShowAddItem(int position) {
        int size = getData().size();
        return position == size;
    }

    @Override
    public int getItemViewType(int position) {
        if (isShowAddItem(position)) {
            return TYPE_CAMERA;
        } else {
            return TYPE_PICTURE;
        }
    }

    @Override
    public int getItemCount() {
        return getData().size();
    }


    public List<File> getListFile() {
        List<File> fileList = new ArrayList<File>();
        for (int i = 0; i < getData().size(); i++) {
            System.out.println("CompressPath：" + getData().get(i).getCompressPath());
            System.out.println("RealPath：" + getData().get(i).getRealPath());
            System.out.println("Path：" + getData().get(i).getPath());
            System.out.println("AndroidQPath：" + getData().get(i).getPath());
            if (!TextUtils.isEmpty(getData().get(i).getCompressPath())) {
                fileList.add(new File(getData().get(i).getCompressPath()));
            } else if (Build.VERSION.SDK_INT >= 29) {
                if (!TextUtils.isEmpty(getData().get(i).getAndroidQToPath())) {
                    fileList.add(new File(getData().get(i).getAndroidQToPath()));
                } else if (!TextUtils.isEmpty(getData().get(i).getRealPath())) {
                    fileList.add(new File(getData().get(i).getRealPath()));
                } else if (!TextUtils.isEmpty(getData().get(i).getPath()) && !getData().get(i).getPath().contains("http")) {
                    fileList.add(new File(getData().get(i).getPath()));
                }
            } else if (!TextUtils.isEmpty(getData().get(i).getRealPath())) {
                fileList.add(new File(getData().get(i).getRealPath()));
            } else if (!TextUtils.isEmpty(getData().get(i).getPath()) && !getData().get(i).getPath().contains("http")) {
                fileList.add(new File(getData().get(i).getPath()));
            }
        }
        return fileList;
    }


    public File getImgFile(LocalMedia localMedia) {
        System.out.println("CompressPath：" + localMedia.getCompressPath());
        System.out.println("RealPath：" + localMedia.getRealPath());
        System.out.println("Path：" + localMedia.getPath());
        System.out.println("AndroidQPath：" + localMedia.getPath());
        File file = null;
        if (!TextUtils.isEmpty(localMedia.getCompressPath())) {
            file = new File(localMedia.getCompressPath());
        } else if (Build.VERSION.SDK_INT >= 29) {
            if (!TextUtils.isEmpty(localMedia.getAndroidQToPath())) {
                file = new File(localMedia.getAndroidQToPath());
            } else if (!TextUtils.isEmpty(localMedia.getRealPath())) {
                file = new File(localMedia.getRealPath());
            } else if (!TextUtils.isEmpty(localMedia.getPath()) && !localMedia.getPath().contains("http")) {
                file = new File(localMedia.getPath());
            }
        } else if (!TextUtils.isEmpty(localMedia.getRealPath())) {
            file = new File(localMedia.getRealPath());
        } else if (!TextUtils.isEmpty(localMedia.getPath()) && !localMedia.getPath().contains("http")) {
            file = new File(localMedia.getPath());
        }
        return file;
    }
}
