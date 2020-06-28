package com.example.toolstest;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CaptureRequest;
import android.os.Build;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.myservice.servicetoactivity;
import com.other.eventbuscalss;
import com.tools.Androidtool.AppLogger;
import com.tools.Javatool.interruptthread;
import com.tools.Javatool.mytimeoperate;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Handler;
import android.os.HandlerThread;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Surface;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;

import static com.example.toolstest.MainActivity.context;

public class secondactivity extends AppCompatActivity {
    private Button mButton;
    private Button meventservice, meventactivity, starth, intth, wait, stafrontcamere,staback;
    private TextView mTextView;
    Object obj;
    interruptthread mInterruptthread;
    private int mCurrentCamIndex;

    private int mCameraId = CameraCharacteristics.LENS_FACING_FRONT;
    private CameraManager mCameraManager; // 相机管理者
    private CameraDevice mCameraDevice; // 相机对象
    private Handler mBackgroundHandler;
    private HandlerThread mBackgroundThread;
    private CaptureRequest.Builder mCaptureRequestBuilder;
    private int PICK_FROM_CAMERA;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secondactivity);
        //视图初始化
        initview();


        obj = new Object();
        mInterruptthread = new interruptthread(obj);

        final Intent intent = new Intent(this, servicetoactivity.class);
        intent.setAction("com.jay.example.service.TEST_SERVICE1");
        AppLogger.v("TAG", "secondactivity");
        EventBus.getDefault().register(this);
        startBackgroundThread();
         mCameraManager = (CameraManager) context.getSystemService(Context.CAMERA_SERVICE);
        mCameraManager.registerAvailabilityCallback(new CameraManager.AvailabilityCallback() {
            @Override
            public void onCameraAvailable(@NonNull String cameraId) {
                super.onCameraAvailable(cameraId);
                AppLogger.v("TAG","onCameraAvailable");
            }

            @Override
            public void onCameraUnavailable(@NonNull String cameraId) {
                super.onCameraUnavailable(cameraId);
                AppLogger.v("TAG","onCameraUnavailable");
            }
        }, mBackgroundHandler);


        mCameraManager.registerTorchCallback(new CameraManager.TorchCallback() {
            @Override
            public void onTorchModeUnavailable(@NonNull String cameraId) {
                super.onTorchModeUnavailable(cameraId);
                AppLogger.v("TAG","onTorchModeUnavailable");
            }

            @Override
            public void onTorchModeChanged(@NonNull String cameraId, boolean enabled) {
                super.onTorchModeChanged(cameraId, enabled);
                AppLogger.v("TAG","onTorchModeChanged");
            }
        }, mBackgroundHandler);
        staback.setOnClickListener(new Button.OnClickListener() {

        			@Override
        			public void onClick(View v) {
        				// TODO Auto-generated method stub
                        Toast.makeText(secondactivity.this, "开启前置摄像头", Toast.LENGTH_SHORT).show();


                        //1
//                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                        intent.putExtra("camerasensortype", 1); // 调用前置摄像头
//                        intent.putExtra("autofocus", true); // 自动对焦
//                        intent.putExtra("fullScreen", false); // 全屏
//                        intent.putExtra("showActionIcons", false);
//
//                        startActivityForResult(intent, 1);


                        //2
//                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                        intent.putExtra("android.intent.extras.CAMERA_FACING_FRONT", 1);
//// 调用前置摄像头
//                        startActivityForResult(intent, 1);
                        //3
//                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                        intent.putExtra("android.intent.extras.CAMERA_FACING", 1); // 调用前置摄像头
//                        intent.putExtra("autofocus", true); // 自动对焦
//                        intent.putExtra("fullScreen", false); // 全屏
//                        intent.putExtra("showActionIcons", false);
//                        startActivityForResult(intent, 1);
                        //4

                        openfrontcamera();
                       //4
//                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                        intent.putExtra("camerasensortype", 2); // 调用前置摄像头
//                        intent.putExtra("autofocus", true); // 自动对焦
//                        intent.putExtra("fullScreen", false); // 全屏
//                        intent.putExtra("showActionIcons", false);
//                        startActivityForResult(intent, 1);
                        //5




                        }

        		});


        stafrontcamere.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
//                CameraManager mCameraManager = (CameraManager) context.getSystemService(Context.CAMERA_SERVICE);
                Toast.makeText(secondactivity.this, "打开相机", Toast.LENGTH_SHORT).show();
                openCamera();


            }

        });

//开始一个线程
        starth.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Toast.makeText(secondactivity.this, "开始线程", Toast.LENGTH_SHORT).show();
                mInterruptthread.start();


            }

        });

        wait.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Toast.makeText(secondactivity.this, "中断线程", Toast.LENGTH_SHORT).show();
                mInterruptthread.setflag();


            }

        });
//中断一个线程
        intth.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Toast.makeText(secondactivity.this, "中断线程", Toast.LENGTH_SHORT).show();
                mInterruptthread.interrupt();
                mInterruptthread.setflagfalse();


            }

        });


        mButton.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                SimpleDateFormat sdf = new SimpleDateFormat("mm-ss", Locale.CHINA);// 日期格式名定义
                String fname = sdf.format(new Date());
                AppLogger.v("TAG", "onStartCommand" + fname);
                mytimeoperate name = new mytimeoperate();
                String week = name.getWeek();
                AppLogger.v("TAG", "" + week);
            }

        });

        meventservice.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {

                // TODO Auto-generated method stub
                AppLogger.v("TAG", "meventservice");
                startService(intent);
            }

        });

        meventactivity.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub


            }

        });


    }

    public void openfrontcamera() {
        int cameraCount = 0;
        Camera cam = null;

        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        cameraCount = Camera.getNumberOfCameras(); // get cameras number

        for ( int camIdx = 0; camIdx < cameraCount;camIdx++ ) {
            Camera.getCameraInfo(camIdx, cameraInfo); // get camerainfo
            if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) { // 代表摄像头的方位，目前有定义值两个分别为CAMERA_FACING_FRONT前置和CAMERA_FACING_BACK后置
                try {
                    cam = Camera.open(camIdx);
                } catch (RuntimeException e) {
                    e.printStackTrace();
                }
            }
        }
    }



    private CameraDevice.StateCallback mStateCallback = new CameraDevice.StateCallback() {
        @Override
        public void onOpened(@NonNull CameraDevice camera) {
            mCameraDevice = camera;
            // 当相机成功打开时回调该方法，接下来可以执行创建预览的操作
            AppLogger.v("TAG","onOpened");


        }

        @Override
        public void onDisconnected(@NonNull CameraDevice camera) {
            // 当相机断开连接时回调该方法，应该在此执行释放相机的操作
            AppLogger.v("TAG","onDisconnected");
        }

        @Override
        public void onError(@NonNull CameraDevice camera, int error) {
            // 当相机打开失败时，应该在此执行释放相机的操作
            AppLogger.v("TAG","camereonError");
        }
    };

    private void startBackgroundThread() {
        mBackgroundThread = new HandlerThread("CameraBackground");
        mBackgroundThread.start();
        mBackgroundHandler = new Handler(mBackgroundThread.getLooper());
    }

    private void stopBackgroundThread() {
        mBackgroundThread.quitSafely();
        try {
            mBackgroundThread.join();
            mBackgroundThread = null;
            mBackgroundHandler = null;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

//    CameraManager cameraManager = (CameraManager) context.getSystemService(Context.CAMERA_SERVICE);




    public void openCamera() {
        try {
            // 前处理
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    Activity#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for Activity#requestPermissions for more details.
                    return;
                }
            }
            mCameraManager.openCamera(Integer.toString(mCameraId), mStateCallback, mBackgroundHandler);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

//    private void startPreview() {
////        SurfaceTexture mSurfaceTexture = mTextureView.getSurfaceTexture();
////        //设置TextureView的缓冲区大小
////        mSurfaceTexture.setDefaultBufferSize(mPreviewSize.getWidth(), mPreviewSize.getHeight());
//        //获取Surface显示预览数据
//        Surface mSurface = new Surface(mSurfaceTexture);
//        try {
//            //创建CaptureRequestBuilder，TEMPLATE_PREVIEW比表示预览请求
//            mCaptureRequestBuilder = mCameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
//            //设置Surface作为预览数据的显示界面
//            mCaptureRequestBuilder.addTarget(mSurface);
//            //创建相机捕获会话，第一个参数是捕获数据的输出Surface列表，第二个参数是CameraCaptureSession的状态回调接口，当它创建好后会回调onConfigured方法，第三个参数用来确定Callback在哪个线程执行，为null的话就在当前线程执行
//            mCameraDevice.createCaptureSession(Arrays.asList(mSurface), new CameraCaptureSession.StateCallback() {
//                @Override
//                public void onConfigured(CameraCaptureSession session) {
//                    try {
//                        //创建捕获请求
//                        mCaptureRequest = mCaptureRequestBuilder.build();
//                        mPreviewSession = session;
//                        //设置反复捕获数据的请求，这样预览界面就会一直有数据显示
//                        mPreviewSession.setRepeatingRequest(mCaptureRequest, mSessionCaptureCallback, null);
//                    } catch (CameraAccessException e) {
//                        e.printStackTrace();
//                    }
//                }
//
//                @Override
//                public void onConfigureFailed(CameraCaptureSession session) {
//
//                }
//            }, null);
//        } catch (CameraAccessException e) {
//            e.printStackTrace();
//        }
//    }

    private void initview() {
        mButton=(Button) findViewById(R.id.gettime);
        meventservice=(Button) findViewById(R.id.bteventser);
        meventactivity=(Button) findViewById(R.id.bteventac);
        stafrontcamere=(Button) findViewById(R.id.stafront);
        staback=(Button) findViewById(R.id.staback);
        starth=(Button) findViewById(R.id.starth);
        intth=(Button) findViewById(R.id.intth);
        wait=(Button) findViewById(R.id.wait);
        mTextView=(TextView) findViewById(R.id.TV1);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.POSTING,priority = 5,sticky = true)
    public void name(eventbuscalss eventbuscalss) {
        mTextView.setText(eventbuscalss.getMes());
        }


}
