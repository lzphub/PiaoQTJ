package cn.dankal.basiclib.util;

import android.graphics.Color;

public class TitleColorUtil {

    public static String setTitleTextAlpha(int t, int oldt) {
        String color="#333333";
        if (oldt < 500) {
            if (155 > t && t > 150 && t > oldt) {
                color="#11333333";
            }
            if (160 > t && t > 155 && t > oldt) {
                color="#22333333";
            }
            if (165 > t && t > 170 && t > oldt) {
                color="#33333333";
            }
            if (180 > t && t > 175 && t > oldt) {
                color="#44333333";
            }
            if (185 > t && t > 190 && t > oldt) {
                color="#55333333";
            }
            if (200 > t && t > 195 && t > oldt) {
                color="#66333333";
            }
            if (205 > t && t > 210 && t > oldt) {
                color="#77333333";
            }
            if (220 > t && t > 215 && t > oldt) {
                color="#88333333";
            }
            if (225 > t && t > 230 && t > oldt) {
                color="#99333333";
            }
            if (240 > t && t > 235 && t > oldt) {
                color="#aa333333";
            }
            if (245 > t && t > 250 && t > oldt) {
                color="#bb333333";
            }
            if (260 > t && t > 255 && t > oldt) {
                color="#cc333333";
            }
            if (265 > t && t > 270 && t > oldt) {
                color="#dd333333";
            }
            if (280 > t && t > 275 && t > oldt) {
                color="#ee333333";
            }
            if (t > 285 && t > oldt) {
                color="#333333";
            }
            if (280 > t && t < 285 && t < oldt) {
                color="#ee333333";
            }
            if (270 > t && t < 275 && t < oldt) {
                color="#dd333333";
            }
            if (260 > t && t < 265 && t < oldt) {
                color="#cc333333";
            }
            if (250 > t && t < 255 && t < oldt) {
                color="#bb333333";
            }
            if (240 > t && t < 245 && t < oldt) {
                color="#aa333333";
            }
            if (230 > t && t < 235 && t < oldt) {
                color="#99333333";
            }
            if (220 > t && t < 225 && t < oldt) {
                color="#88333333";
            }
            if (210 > t && t < 215 && t < oldt) {
                color="#77333333";
            }
            if (200 > t && t < 205 && t < oldt) {
                color="#66333333";
            }
            if (190 > t && t < 195 && t < oldt) {
                color="#55333333";
            }
            if (180 > t && t < 185 && t < oldt) {
                color="#44333333";
            }
            if (170 > t && t < 175 && t < oldt) {
                color="#33333333";
            }
            if (160 > t && t < 165 && t < oldt) {
                color="#22333333";
            }
            if (150 > t && t < 155 && t < oldt) {
                color="#11333333";
            }
            if (140 > t && t < 145 && t < oldt) {
                color="#00333333";
            }
        }
        return color;
    }

}
