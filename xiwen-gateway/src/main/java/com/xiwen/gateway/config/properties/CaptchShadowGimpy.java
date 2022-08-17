package com.xiwen.gateway.config.properties;

import com.google.code.kaptcha.GimpyEngine;
import com.google.code.kaptcha.NoiseProducer;
import com.google.code.kaptcha.util.Configurable;
import com.jhlabs.image.RippleFilter;
import com.jhlabs.image.ShadowFilter;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.util.Random;

/**
 * @BelongsProject: xiwen
 * @BelongsPackage: com.xiwen.gateway.config.properties
 * @Author: cuiqichao
 * @CreateTime: 2022-08-17  20:37
 * @Description: 自定义的验证码的水纹实现
 * @Version: 1.0
 */
public class CaptchShadowGimpy extends Configurable implements GimpyEngine {
    public CaptchShadowGimpy() {
    }

    public BufferedImage getDistortedImage(BufferedImage baseImage) {
        NoiseProducer noiseProducer = this.getConfig().getNoiseImpl();
        BufferedImage distortedImage = new BufferedImage(baseImage.getWidth(), baseImage.getHeight(), 2);
        Graphics2D graph = (Graphics2D)distortedImage.getGraphics();
        ShadowFilter shadowFilter = new ShadowFilter();
//        shadowFilter.setRadius(10.0F);
//        shadowFilter.setDistance(5.0F);
//        shadowFilter.setOpacity(1.0F);
        Random rand = new Random();
        RippleFilter rippleFilter = new RippleFilter();
//        rippleFilter.setWaveType(0);
//        rippleFilter.setXAmplitude(7.6F);
//        rippleFilter.setYAmplitude(rand.nextFloat() + 1.0F);
//        rippleFilter.setXWavelength((float)(rand.nextInt(7) + 8));
//        rippleFilter.setYWavelength((float)(rand.nextInt(3) + 2));
//        rippleFilter.setEdgeAction(1);
        BufferedImage effectImage = rippleFilter.filter(baseImage, (BufferedImage)null);
        effectImage = shadowFilter.filter(effectImage, (BufferedImage)null);
        graph.drawImage(baseImage, 0, 0, (Color)null, (ImageObserver)null);
        graph.dispose();
        noiseProducer.makeNoise(distortedImage, 0.1F, 0.1F, 0.25F, 0.25F);
        noiseProducer.makeNoise(distortedImage, 0.1F, 0.25F, 0.5F, 0.9F);
        return distortedImage;
    }
}
