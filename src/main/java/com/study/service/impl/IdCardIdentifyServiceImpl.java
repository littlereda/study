package com.study.service.impl;

import com.study.service.IdCardIdentifyService;
import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.springframework.stereotype.Service;

/**
 * @author hbc
 * @version 1.0
 * @description 描述
 * @date 2023/4/18 15:02
 */
@Service
public class IdCardIdentifyServiceImpl implements IdCardIdentifyService {

    static
    {
        //在使用OpenCV前必须加载Core.NATIVE_LIBRARY_NAME类,否则会报错
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    @Override
    public void IdCardIdentify() {
        preHandle();
    }

    // 预处理
    private void preHandle(){
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        // 加载图片
        Mat inputImage = Imgcodecs.imread("C:\\Users\\csht\\Desktop\\images\\id2.jpg");

        // 转换为灰度图片
        Mat grayImage = new Mat();
        Imgproc.cvtColor(inputImage, grayImage, Imgproc.COLOR_BGR2GRAY);

        // 对图片进行高斯模糊，以去除噪声
        Mat blurredImage = new Mat();
        Imgproc.GaussianBlur(grayImage, blurredImage, new Size(5, 5), 0.5);

//        Mat imageMat = new Mat();
//        Imgproc.adaptiveThreshold(blurredImage, imageMat, 255, Imgproc.ADAPTIVE_THRESH_MEAN_C, Imgproc.THRESH_BINARY_INV, 5, 4);

        // 对图片进行二值化处理，以便提取出文字和图像等特征
        Mat binaryImage = new Mat();
        Imgproc.threshold(blurredImage, binaryImage, 0, 255, Imgproc.THRESH_BINARY + Imgproc.THRESH_OTSU);

        // Save preprocessed image to disk
        Imgcodecs.imwrite("C:\\Users\\csht\\Desktop\\images\\image.jpg", blurredImage);
    }

}
