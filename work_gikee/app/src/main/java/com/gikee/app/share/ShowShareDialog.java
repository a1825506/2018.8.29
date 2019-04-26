package com.gikee.app.share;

import android.graphics.Bitmap;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import com.gikee.app.R;
import com.gikee.app.Utils.MyUtils;
import com.gikee.app.Utils.ToastUtils;
import com.gikee.app.datas.Commons;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.editorpage.ShareActivity;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

import java.io.File;


/**
 * Created by Sea Zhang on 2017/3/14.
 */

public class ShowShareDialog  {
    private FragmentActivity activity;
    private String TAG="ShowShareDialog";
    private Bitmap imgpath;
    public static int share_url=0;
    public static int share_img=1;
    public int type=-1;
    enum SHARE_TYPE {Type_WXSceneSession, Type_WXSceneTimeline}



    public void setImagePath(Bitmap filepath){
        this.imgpath = filepath;
    }

    public void setShareType(int share_type){
        this.type = share_type;
    }


    public void show(FragmentManager supportFragmentManager, FragmentActivity activity) {
        this.activity = activity;
        showShareDagger( supportFragmentManager);
    }

    public void close(){

    }


    private void showShareDagger(FragmentManager fragmentManager) {
        final ShareChoseFragment shareChoseFragment = ShareChoseFragment.instance();

        shareChoseFragment.setIShareListener(new ShareChoseFragment.IShareListener() {
            @Override
            public void ITalkListener() {
                if(type==1){
                    shareImg(SHARE_MEDIA.WEIXIN);
                }else
                    shareWeb(SHARE_MEDIA.WEIXIN);
            }

            @Override
            public void IFriendListener() {
                if(type==1){
                    shareImg(SHARE_MEDIA.WEIXIN_CIRCLE);
                }else
                    shareWeb(SHARE_MEDIA.WEIXIN_CIRCLE);


            }

            @Override
            public void IQQTalkListener() {
                if(type==1){
                    shareImg(SHARE_MEDIA.QQ);
                }else
                    shareWeb(SHARE_MEDIA.QQ);

            }

            @Override
            public void IOnweiBo() {
                if(type==1){
                    shareImg(SHARE_MEDIA.SINA);
                }else
                    shareWeb(SHARE_MEDIA.SINA);
            }

            @Override
            public void Cancel() {

            }

        });
        shareChoseFragment.show(fragmentManager, "sharedialog");
    }



    public void shareWeb(SHARE_MEDIA share_media){
        UMImage thumb =  new UMImage(activity, R.mipmap.app_share_logo);
        UMWeb  web = new UMWeb(Commons.share_gikee);
        web.setTitle(Commons.share_title);//标题
        web.setThumb(thumb);  //缩略图
        web.setDescription(activity.getString(R.string.share_description));//描述
        new ShareAction(activity)
                .setPlatform(share_media)//传入平台
                .withMedia(web)
                .setCallback(umShareListener)//回调监听器
                .share();
    }

    public void shareImg(SHARE_MEDIA share_media){


        UMImage image = new UMImage(activity, imgpath);//本地文件
        image.compressStyle = UMImage.CompressStyle.SCALE;//大小压缩，默认为大小压缩，适合普通很大的图
        image.compressStyle = UMImage.CompressStyle.QUALITY;//质量压缩，适合长图的分享
       // 压缩格式设置
        image.compressFormat = Bitmap.CompressFormat.PNG;//用户分享透明背景的图片可以设置这种方式，但是qq好友，微信朋友圈，不支持透明背景图片，会变成黑色

        new ShareAction(activity)
                .setPlatform(share_media)//传入平台
                .withMedia(image)
                .setCallback(umShareListener)//回调监听器
                .share();
    }






    private UMShareListener umShareListener = new UMShareListener() {
        /**
         * @descrption 分享开始的回调
         * @param platform 平台类型
         */
        @Override
        public void onStart(SHARE_MEDIA platform) {
           // ToastUtils.show("开始分享");
        }

        /**
         * @descrption 分享成功的回调
         * @param platform 平台类型
         */
        @Override
        public void onResult(SHARE_MEDIA platform) {
            ToastUtils.show(activity.getString(R.string.share_success));
        }

        /**
         * @descrption 分享失败的回调
         * @param platform 平台类型
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            ToastUtils.show(activity.getString(R.string.share_exception));
        }

        /**
         * @descrption 分享取消的回调
         * @param platform 平台类型
         */
        @Override
        public void onCancel(SHARE_MEDIA platform) {
            ToastUtils.show(activity.getString(R.string.share_cannel));
                }
    };


}
